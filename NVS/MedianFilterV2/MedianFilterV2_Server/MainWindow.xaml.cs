using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text.RegularExpressions;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using MedianFilterV2_Server.Data;
using MedianFilterV2_Server.Data.Models;
using MedianFilterV2_Shared;
using MedianFilterV2_Shared.Commands;
using MedianFilterV2_Shared.Helpers;
using Microsoft.Win32;
using SimpleTCP;

namespace MedianFilterV2_Server
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        SimpleTcpServer server;

        public MainWindow()
        {
            InitializeComponent();
            this.comboBoxFilter.ItemsSource = Database.Instance.Filters;
            this.comboBoxFilter.SelectedIndex = 0;
            this.txtSize.Visibility = Visibility.Hidden;
            this.lblSize.Visibility = Visibility.Hidden;
            this.lblTime.Visibility = Visibility.Hidden;
            this.lbClients.ItemsSource = Database.Instance.Clients;
            //Database.Instance.Clients.Add(new Client("test","test"));
        }

        private void MenuItemExit_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }
        private void MenuItemLoad_Click(object sender, RoutedEventArgs e)
        {
            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.Title = "Please select an image for filtering!";
            openFileDialog.Filter = "Image Files|*.jpg;*.jpeg;*.png;*.gif;*.tif;...";
            openFileDialog.DefaultExt = ".png";
            if (openFileDialog.ShowDialog() == true)
            {
                Bitmap img = new Bitmap(openFileDialog.FileName);
                Database.Instance.ImageBefore = img;
                this.imageBefore.Source = BitmapToImageSource(img);
            }
        }
        private void btnFilter_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                if (this.server.IsStarted)
                {
                    if (Database.Instance.Clients.Count > 0)
                    {
                        if (Database.Instance.Clients.First(c => c.IsBusy == false) != null)
                        {
                            lblTime.Content = "";
                            if (Database.Instance.ImageBefore == null)
                            {
                                throw new Exception("You should select an Image before filtering!");
                            }
                            else
                            {
                                List<Bitmap> images = Database.Instance.GetSplitImages();
                                NetworkMessage msg1 = new NetworkMessage();
                                Client client = Database.Instance.Clients.First(c => c.IsBusy == false);
                                client.IsBusy = true;
                                msg1.Receivers = new List<string>() {client.Name};
                                FilterCommand cmd = new FilterCommand();

                                switch (comboBoxFilter.SelectedItem.ToString())
                                {
                                    case "Red":
                                        cmd.Type = FilterCommand.FilterType.ColourFilter;
                                        cmd.Arguments.Add("FilterColour", "Red");
                                        break;
                                    case "Blue":
                                        cmd.Type = FilterCommand.FilterType.ColourFilter;
                                        cmd.Arguments.Add("FilterColour", "Blue");
                                        break;
                                    case "Green":
                                        cmd.Type = FilterCommand.FilterType.ColourFilter;
                                        cmd.Arguments.Add("FilterColour", "Green");
                                        break;
                                    case "MedianFilter":
                                        cmd.Type = FilterCommand.FilterType.MedianFilter;
                                        cmd.Arguments.Add("MedianSize", this.txtSize.Text);
                                        break;

                                }
                                msg1.Command = cmd;
                                server.Broadcast(BinaryHelper.ObjectToByteArray(msg1));
                            }
                        }
                        else
                        {
                            throw new Exception("Currently all clients are busy, please try again later!");
                        }
                    }
                    else
                    {
                        throw new Exception("You need to register with clients to perform filtering actions!");
                    }
                }
                else
                {
                    throw new Exception("You need to start the server!");
                }
            }
            catch (Exception ex)
            {
                if (ex.Message != "You need to wait for the first filtering to end!")
                {
                }
                MessageBox.Show("Error happend: " + ex.Message, "Error:", MessageBoxButton.OK);
            }
        }
        private BitmapImage BitmapToImageSource(Bitmap bitmap)
        {
            using (MemoryStream memory = new MemoryStream())
            {
                bitmap.Save(memory, System.Drawing.Imaging.ImageFormat.Bmp);
                memory.Position = 0;
                BitmapImage bitmapimage = new BitmapImage();
                bitmapimage.BeginInit();
                bitmapimage.StreamSource = memory;
                bitmapimage.CacheOption = BitmapCacheOption.OnLoad;
                bitmapimage.EndInit();

                return bitmapimage;
            }
        }
        private void MenuItemSave_Click(object sender, RoutedEventArgs e)
        {
            if (Database.Instance.ImageAfter == null)
            {
                MessageBox.Show("You should select an Image and filter it before trying to save!", "Error:",
                    MessageBoxButton.OK);
            }
            else
            {
                SaveFileDialog dialog = new SaveFileDialog();
                dialog.Filter = "Images|*.png;*.bmp;*.jpg";
                ImageFormat format = ImageFormat.Png;
                if (dialog.ShowDialog() == true)
                {
                    string ext = System.IO.Path.GetExtension(dialog.FileName);
                    switch (ext)
                    {
                        case ".jpg":
                            format = ImageFormat.Jpeg;
                            break;
                        case ".bmp":
                            format = ImageFormat.Bmp;
                            break;
                    }
                    Database.Instance.ImageAfter.Save(dialog.FileName, format);
                }
            }
        }
        private void comboBoxFilter_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (comboBoxFilter.SelectedItem.ToString() == "MedianFilter")
            {
                this.txtSize.Visibility = Visibility.Visible;
                this.lblSize.Visibility = Visibility.Visible;
                this.lblTime.Visibility = Visibility.Visible;
            }
            else
            {
                this.txtSize.Visibility = Visibility.Hidden;
                this.lblSize.Visibility = Visibility.Hidden;
                this.lblTime.Visibility = Visibility.Hidden;
            }
        }
        private void NumberValidationTextBox(object sender, TextCompositionEventArgs e)
        {
            Regex regex = new Regex("[^0-9]+");
            e.Handled = regex.IsMatch(e.Text);
        }
        private void btnStart_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                if (CheckRegex(txtPort.Text, @"\d{4}") != true)
                {
                    throw new Exception("You need to enter a valid Port like [8090]");
                }
                else
                {
                    server = new SimpleTcpServer().Start(int.Parse(txtPort.Text));
                    lbMessages.Items.Add("Server listening on port: " + txtPort.Text);
                    btnStart.IsEnabled = false;
                    btnFilter.IsEnabled = true;
                    server.DataReceived += (se, msg) =>
                    {
                        this.Dispatcher.Invoke(() =>
                        {
                            HandleClientMessage(msg);
                        });
                    };
                    btnStop.IsEnabled = true;
                }

            }
            catch (Exception ex)
            {
                MessageBox.Show("Error happend: " + ex.Message, "Error:", MessageBoxButton.OK);
                btnStart.IsEnabled = true;
            }
        }
        private void HandleClientMessage(Message msg)
        {
            NetworkMessage clientMessage = (NetworkMessage)BinaryHelper.ByteArrayToObject(msg.Data);
            switch (clientMessage.Command.CommandType)
            {
                case CommandType.FilterCommand:
                    HandleFilterCommand(clientMessage);
                    break;
                case CommandType.RegisterClientCommand:
                    HandleRegisterClientCommand(clientMessage);
                    break;
                case CommandType.UnregisterClientCommand:
                    HandleUnregisterClientCommand(clientMessage);
                    break;
            }

        }
        private void HandleFilterCommand(NetworkMessage msg)
        {
            if (msg.Command.ThisServerVersion.Equals(Command.ServerVersion))
            {
                FilterCommand cmd = (FilterCommand)msg.Command;
                switch (cmd.Status)
                {
                    case FilterCommand.FilterCommandStatus.Filtering:
                        break;
                    case FilterCommand.FilterCommandStatus.FilterSuccess:
                        Bitmap bmp;
                        using (var ms = new MemoryStream(cmd.Payload))
                        {
                            bmp = new Bitmap(ms);
                        }
                        Database.Instance.ImageAfter = bmp;
                        imageAfter.Source = BitmapToImageSource(Database.Instance.ImageAfter);
                        break;
                }
            }
            else
            {
                //Implements Broadcast to client that server is outdated
            }
        }
        private void HandleRegisterClientCommand(NetworkMessage msg)
        {
            NetworkMessage msg2 = new NetworkMessage();
            msg2.Command = new RegisterClientCommand();
            RegisterClientCommand cmd = (RegisterClientCommand)msg.Command;
            Client c = new Client(cmd.ClientName, cmd.ClientAdress);
            if (msg.Command.ThisServerVersion.Equals(Command.ServerVersion))
            {
                
                if (!Database.Instance.Clients.Contains(c))
                {
                    Database.Instance.Clients.Add(c);
                    ((RegisterClientCommand)msg2.Command).Status = RegisterClientCommand.RegisterClientStatus.ConnectionSuccess;
                }
                else
                {
                    ((RegisterClientCommand) msg2.Command).Status =
                        RegisterClientCommand.RegisterClientStatus.ConnectionFailure;
                    ((RegisterClientCommand)msg2.Command).Error = RegisterClientCommand.RegisterClientFailures.ClientNameDuplicate;
                }
            }
            else
            {
                ((RegisterClientCommand)msg2.Command).Status =
                        RegisterClientCommand.RegisterClientStatus.ConnectionFailure;
                ((RegisterClientCommand)msg2.Command).Error = RegisterClientCommand.RegisterClientFailures.ServerVersionOutdated;
            }
            BroadCastToSpecificClient(c, msg2);
        }
        private void BroadCastToSpecificClient(Client c, NetworkMessage msg)
        {
            msg.Receivers = new List<string>() { c.Name};
            server.Broadcast(BinaryHelper.ObjectToByteArray(msg));
        }
        private void HandleUnregisterClientCommand(NetworkMessage msg)
        {
        }
        private void btnStop_Click(object sender, RoutedEventArgs e)
        {
            btnStop.IsEnabled = false;
            server?.Stop();
            lbMessages.Items.Add("Server stopped listening on port: " + txtPort.Text);
            btnStart.IsEnabled = true;
        }
        private bool CheckRegex(string str, string regex)
        {
            Regex reg = new Regex(regex);
            MatchCollection result = reg.Matches(str);
            return result[0].Success;
        }
        private void MenuItemLocalAddress_OnClick(object sender, RoutedEventArgs e)
        {
            try
            {
                IPHostEntry host;
                string localIP = "?";
                host = Dns.GetHostEntry(Dns.GetHostName());
                foreach (IPAddress ip in host.AddressList)
                {
                    if (ip.AddressFamily == AddressFamily.InterNetwork)
                    {
                        MessageBox.Show("Local IP Address: " + ip.ToString(), "Success:", MessageBoxButton.OK); ;
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error happend: " + ex.Message, "Error:", MessageBoxButton.OK);
            }
        }
        private void MenuItemGlobalAddress_OnClick(object sender, RoutedEventArgs e)
        {
            try
            {
                string externalip = new WebClient().DownloadString("http://icanhazip.com");
                MessageBox.Show("Global IP Address: " + externalip, "Success:", MessageBoxButton.OK); ;
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error happend: " + ex.Message, "Error:", MessageBoxButton.OK);
            }
        }
    }
}
