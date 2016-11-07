using SimpleTCP;
using System;
using System.Collections.Generic;
using System.Linq;
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
            txtMsg.KeyDown += new KeyEventHandler(tb_KeyDown);
        }

        private void btnConnect_Click(object sender, RoutedEventArgs e)
        {
            try
            {
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
                    lbChat.Items.Add("Connected to " + txtIP.Text + " on port " + txtPort.Text);
                    btnConnect.IsEnabled = false;
                    client.Delimiter = 0x13;
                    client.DelimiterDataReceived += (se, msg) =>
                    {
                        this.Dispatcher.Invoke(() =>
                        {
                            lbChat.Items.Add(msg.MessageString);
                        });
                    };
                    txtMsg.IsEnabled = true;
                    btnSend.IsEnabled = true;
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error happend: " + ex.Message, "Error:", MessageBoxButton.OK);
                btnConnect.IsEnabled = true;
            }
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
                client.WriteLine(txtUsername.Text + " said: " + txtMsg.Text);
                txtMsg.Text = "";
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error happend: " + ex.Message, "Error:", MessageBoxButton.OK);
                btnConnect.IsEnabled = true;
            }
        }

        void tb_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.Key == Key.Enter)
            {
                SendMsg();
            }
        }
    }
}
