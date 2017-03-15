using System;
using System.Collections.Generic;
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
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.DirectoryServices.AccountManagement;
using System.DirectoryServices.ActiveDirectory;
using System.DirectoryServices.Protocols;
using System.Windows.Resources;

namespace LDAP_CSharp
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void button_Click(object sender, RoutedEventArgs e)
        {
            using (PrincipalContext pc = new PrincipalContext(ContextType.Domain, "192.168.128.253", "OU=USERS,DC=htl-vil,DC=local"))
            {
                // validate the credentials
                if (pc.ValidateCredentials(txtName.Text, txtPassword.Password))
                {
                    label2.Content = "Funktioniert!";
                    Console.WriteLine("Funktioniert!");
                    Console.Read();
                }
                else
                {
                    label2.Content = "Falsch!";
                    Console.WriteLine("Falsch!");
                }
                Console.Read();
            }
        }
    }
}
