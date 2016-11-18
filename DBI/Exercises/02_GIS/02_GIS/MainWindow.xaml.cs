using _02_GIS.Data;
using System;
using System.Data.OleDb;
using System.Threading;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Threading;
using _02_GIS.Models;
using _02_GIS.Threads;

namespace _02_GIS
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private SchneefallAlarm alarm;
        private Thread alarmThread;

        public MainWindow()
        {
            InitializeComponent();
            cmbConnections.ItemsSource = Database.Instance.ConnectionsAddresses;
            alarm = new SchneefallAlarm();
            alarm.CallbackMessage += SchneefallAlarm_Message;
        }

        private async void btnConnect_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                Database db = Database.Instance;
                btnConnect.IsEnabled = false;
                db.ConnectToDatabase(this.cmbConnections.SelectedItem.ToString());
                await RefreshGui();
                btnDisconnect.IsEnabled = true;
                btnStraßenübersicht.IsEnabled = true;
                btnSchneeüberwachungStarten.IsEnabled = true;
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error happend: " + ex.Message, "Error:", MessageBoxButton.OK);
                btnConnect.IsEnabled = true;
            }
        }

        private async void btnRäumungsauftrag_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                if (this.dataGridRäumungsabschnitte.SelectedItem == null || this.dataGridMitarbeiter.SelectedItem == null)
                    throw new Exception("Sie müssen Räumungsabschnitt und Mitarbeiter auswählen!");

                String dateRA = this.GetDate();
                if (dateRA != null)
                {
                    Räumungsabschnitt räumungsabschnitt = this.dataGridRäumungsabschnitte.SelectedItem as Räumungsabschnitt;
                    Mitarbeiter mitarbeiter = this.dataGridMitarbeiter.SelectedItem as Mitarbeiter;

                    await Database.Instance.AddRäumungsauftrag(räumungsabschnitt, mitarbeiter, DateTime.Parse(dateRA));
                    await this.RefreshGui();
                }
                else
                {
                    MessageBox.Show("Räumungsauftrag abgebrochen!");
                }
            }
            catch (OleDbException oledbExcpetion)
            {
                MessageBox.Show(oledbExcpetion.Message);
            }
            catch (Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
        }

        private void btnDisconnect_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                btnDisconnect.IsEnabled = false;
                btnSchneeüberwachungStarten.IsEnabled = false;
                btnStraßenübersicht.IsEnabled = false;
                btnRäumungsauftrag.IsEnabled = false;
                Database.Instance.DisconnectFromDatabase();
                dataGridRäumungsabschnitte.ItemsSource = null;
                dataGridRäumungsabschnitte.Items.Clear();
                dataGridRäumungsaufträge.ItemsSource = null;
                dataGridRäumungsaufträge.Items.Clear();
                dataGridStraßenteilstrecken.ItemsSource = null;
                dataGridStraßenteilstrecken.Items.Clear();
                dataGridMitarbeiter.ItemsSource = null;
                dataGridMitarbeiter.Items.Clear();
                btnConnect.IsEnabled = true;
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error happend: " + ex.Message, "Error:", MessageBoxButton.OK);
                btnDisconnect.IsEnabled = true;
            }
        }

        private async Task RefreshGui()
        {
            Database db = Database.Instance;
            await db.GetRäumungsabschnitte();
            dataGridRäumungsabschnitte.ItemsSource = db.Räumungsabschnitte;
            dataGridRäumungsabschnitte.Columns[0].Header = "Abschnitts-ID";
            dataGridRäumungsabschnitte.Columns[1].Header = "Räumungsabschnitt";
            dataGridRäumungsabschnitte.Columns[2].Header = "Länge in Meter";
            await db.GetRäumungsaufträge();
            dataGridRäumungsaufträge.ItemsSource = db.Räumungsaufträge;
            dataGridRäumungsaufträge.Columns[0].Header = "Auftrags-Nr";
            dataGridRäumungsaufträge.Columns[1].Header = "Abschnitts-ID";
            dataGridRäumungsaufträge.Columns[2].Header = "Mitarbeitername";
            dataGridRäumungsaufträge.Columns[3].Visibility = Visibility.Collapsed;

        }

        private void dataGridRäumungsabschnitte_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (e.AddedItems.Count > 0)
            {
                GetDataForRäumungsabschnitt((Räumungsabschnitt) dataGridRäumungsabschnitte.SelectedItem);
            }
        }

        private async void GetDataForRäumungsabschnitt(Räumungsabschnitt ra)
        {
            Database db = Database.Instance;
            await db.GetStraßenteilstrecken(ra);
            dataGridStraßenteilstrecken.ItemsSource = db.Straßenteilstrecken;
            dataGridStraßenteilstrecken.Columns[0].Header = "Teilstrecke";
            await db.GetMitarbeiter();
            dataGridMitarbeiter.ItemsSource = db.Mitarbeiter;
            dataGridMitarbeiter.Columns[2].Visibility = Visibility.Collapsed;
            btnRäumungsauftrag.IsEnabled = true;
        }

        private String GetDate()
        {
            DatePickerRA datePickerRAWindow = new DatePickerRA();
            datePickerRAWindow.ShowDialog();
            return datePickerRAWindow.dateRA;
        }

        private void Window_Closed(object sender, EventArgs e)
        {
            this.alarm.IsFinished = true;
        }

        private void SchneefallAlarm_Message(string msg)
        {
            Application.Current.Dispatcher.BeginInvoke(DispatcherPriority.Background,
                 new Action(() => this.lbMessages.Items.Add(msg)));
        }

        private void btnSchneeüberwachungStarten_Click(object sender, RoutedEventArgs e)
        {
                btnSchneeüberwachungStarten.IsEnabled = false;
            alarmThread = new Thread(alarm.StartAlarm) {IsBackground = true};
            alarmThread.Start();
        }

        private void btnStraßenübersicht_Click(object sender, RoutedEventArgs e)
        {
            new Straßenübersicht().Show();
        }
    }
}
