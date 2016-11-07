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
    /// Interaction logic for ChatWindowServer.xaml
    /// </summary>
    public partial class ChatWindowServer : Window
    {
        SimpleTcpServer server;
        public ChatWindowServer()
        {
            InitializeComponent();
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
                    lbChat.Items.Add("Server listening on port: " + txtPort.Text);
                    btnStart.IsEnabled = false;
                    server.Delimiter = 0x13;
                    server.DelimiterDataReceived += (se, msg) => {
                        this.Dispatcher.Invoke(() =>
                        {
                            lbChat.Items.Add( msg.MessageString);
                        });
                        server.BroadcastLine(msg.MessageString);
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

        private bool CheckRegex(string str, string regex)
        {
            Regex reg = new Regex(regex);
            MatchCollection result = reg.Matches(str);
            return result[0].Success;
        }

        private void btnStop_Click(object sender, RoutedEventArgs e)
        {
            btnStop.IsEnabled = false;
            if (server != null)
            {
                server.Stop();
            }
            lbChat.Items.Add("Server stoped listening on port: " + txtPort.Text);
            btnStart.IsEnabled = true;
        }
    }
}
