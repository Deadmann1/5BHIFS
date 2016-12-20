using MedianFilterV2_Shared;
using MedianFilterV2_Shared.Helpers;
using SimpleTCP;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using MedianFilterV2_Client;
using MedianFilterV2_Shared.Commands;

namespace Chat
{
    /// <summary>
    /// Interaction logic for ChatWindowClient.xaml
    /// </summary>
    public partial class ChatWindowClient : Window
    {
        SimpleTcpClient client;
        public ChatWindowClient()
        {
            InitializeComponent();
        }

        private void btnConnect_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                btnConnect.IsEnabled = false;
                if (CheckRegex(txtIP.Text, @"\b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b") != true)
                {
                    throw new Exception("You need to enter a valid IP like [127.0.0.1]");
                }
                else if (CheckRegex(txtPort.Text, @"\d{4}") != true)
                {
                    throw new Exception("You need to enter a valid Port like [8090]");
                }
                else if(String.IsNullOrEmpty(txtUsername.Text))
                {
                    throw new Exception("Please enter a username");
                }
                else
                {
                    client = new SimpleTcpClient().Connect(txtIP.Text, int.Parse(txtPort.Text));
                    NetworkMessage msg = new NetworkMessage();
                    RegisterClientCommand  cmd = new RegisterClientCommand();
                    cmd.ClientName = txtUsername.Text;
                    string externalip = new WebClient().DownloadString("http://icanhazip.com");
                    cmd.ClientAdress = externalip;
                    msg.Command = cmd;
                    SendMessageToServer(msg);
                    client.DataReceived += (se, msg2) =>
                    {
                        this.Dispatcher.Invoke(() =>
                        {
                            this.HandleServerMessage(msg2);
                        });
                    };
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error happend: " + ex.Message, "Error:", MessageBoxButton.OK);
                btnConnect.IsEnabled = true;
            }
        }

        private void HandleServerMessage(Message msg)
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
                    //HandleUnregisterClientCommand(clientMessage);
                    break;
            }

        }

        private async void HandleFilterCommand(NetworkMessage msg)
        {
            if (msg.Command.ThisServerVersion.Equals(Command.ServerVersion))
            {
                if (((FilterCommand) msg.Command).Type == FilterCommand.FilterType.MedianFilter)
                {
                    FilterCommand cmd = (FilterCommand) msg.Command;
                    Bitmap bmp;
                    using (var ms = new MemoryStream(cmd.Payload))
                    {
                        bmp = new Bitmap(ms);
                    }
                    string ret;
                    cmd.Arguments.TryGetValue("MedianSize", out ret);
                    Bitmap result = await FilterFactory.DoMedianFilter(bmp, Int32.Parse(ret));
                    NetworkMessage msg2 = new NetworkMessage();
                    FilterCommand cmd2 = new FilterCommand();
                    MemoryStream stream = new MemoryStream();
                    result.Save(stream, System.Drawing.Imaging.ImageFormat.Bmp);
                    Byte[] bytes = stream.ToArray();
                    cmd2.Payload = bytes;
                    cmd2.Status = FilterCommand.FilterCommandStatus.FilterSuccess;
                    msg2.Command = cmd2;
                    SendMessageToServer(msg2);
                }
                else
                {
                    //Implement Colour Filter
                }
            }
            else
            {
                //Implements Broadcast to client that server is outdated
            }
        }

        private void HandleRegisterClientCommand(NetworkMessage msg)
        {
            RegisterClientCommand cmd = (RegisterClientCommand)msg.Command;
            if (cmd.Status == RegisterClientCommand.RegisterClientStatus.ConnectionSuccess)
            {
                lblMessages.Items.Add("Connected to " + txtIP.Text + " on port " + txtPort.Text);
            }
            else
            {
                client.Disconnect();
                btnConnect.IsEnabled = true;
                lblMessages.Items.Add("Error: Could not connect to " + txtIP.Text + " on port " + txtPort.Text + " Reason: " + cmd.Error);
            }
        }

        private void SendMessageToServer(NetworkMessage msg)
        {
            client.Write(BinaryHelper.ObjectToByteArray(msg));
        }
        private bool CheckRegex(string str, string regex)
        {
            Regex reg = new Regex(regex);
            MatchCollection result = reg.Matches(str);
            return result[0].Success;
        }

        private void btnSend_Click(object sender, RoutedEventArgs e)
        {
            SendMsg();
        }


        private void SendMsg()
        {
            try
            {
                client.WriteLine(txtUsername.Text + " said: ");
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error happend: " + ex.Message, "Error:", MessageBoxButton.OK);
                btnConnect.IsEnabled = true;
            }
        }

    }
}
