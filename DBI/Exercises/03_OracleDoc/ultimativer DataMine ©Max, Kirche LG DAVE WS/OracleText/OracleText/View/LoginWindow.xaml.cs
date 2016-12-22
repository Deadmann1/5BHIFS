using OracleText.Model;
using System;
using System.Collections.Generic;
using System.Data.OleDb;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace OracleText.View
{
    /// <summary>
    /// Interaction logic for LoginWindow.xaml
    /// </summary>
    public partial class LoginWindow : Window
    {
        public LoginWindow()
        {
            InitializeComponent();
        }

        private void BtnConnect_Click(object sender, RoutedEventArgs e)
        {
            String ipAdresse = TxtIpAdresse.Text;
            String username = TxtUsername.Text;
            String password = PboxPassword.Password;
            String service = TxtService.Text;

            try
            {
                OracleTextDatabaseManager oracleTextDatabaseManager = new OracleTextDatabaseManager(ipAdresse, username, password, service);

                App.Current.Exit += (object sender2, ExitEventArgs e2) =>
                {
                    oracleTextDatabaseManager.Dispose();
                };

                new MainWindow(oracleTextDatabaseManager).Show();

                Close();
            }
            catch(OleDbException exception)
            {
                MessageBox.Show(exception.Message, "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }
    }
}
