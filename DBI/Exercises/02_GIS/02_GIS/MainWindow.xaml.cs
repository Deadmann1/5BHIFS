using _02_GIS.Data;
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
using _02_GIS.Models;

namespace _02_GIS
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
            cmbConnections.ItemsSource = Database.Instance.ConnectionsAddresses;
        }

        private void btnConnect_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                Database db = Database.Instance;
                btnConnect.IsEnabled = false;
                db.ConnectToDatabase(this.cmbConnections.SelectedItem.ToString());
                RefreshGui();
                btnDisconnect.IsEnabled = true;
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error happend: " + ex.Message, "Error:", MessageBoxButton.OK);
                btnConnect.IsEnabled = true;
            }
        }

        private void btnRäumungsauftrag_Click(object sender, RoutedEventArgs e)
        {

        }

        private void btnDisconnect_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                btnDisconnect.IsEnabled = false;
                Database.Instance.DisconnectFromDatabase();
                btnConnect.IsEnabled = true;
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error happend: " + ex.Message, "Error:", MessageBoxButton.OK);
                btnDisconnect.IsEnabled = true;
            }
        }

        private async void RefreshGui()
        {
            Database db = Database.Instance;
            await db.GetRäumungsabschnitte();
            dataGridRäumungsabschnitte.ItemsSource = db.Räumungsabschnitte;
        }

        private void dataGridRäumungsabschnitte_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            GetDataForRäumungsabschnitt((Räumungsabschnitt)dataGridRäumungsabschnitte.SelectedItem);
        }

        private async void GetDataForRäumungsabschnitt(Räumungsabschnitt ra)
        {
            Database db = Database.Instance;
            await db.GetStraßenteilstrecken(ra);
            dataGridStraßenteilstrecken.ItemsSource = db.Straßenteilstrecken;
            await db.GetMitarbeiter();
            dataGridMitarbeiter.ItemsSource = db.Mitarbeiter;
            /*
            await db.GetRäumungsaufträge();
            dataGridRäumungsaufträge.ItemsSource = db.Räumungsaufträge;
            */

        }
    }
}
