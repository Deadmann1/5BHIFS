using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
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

namespace Warteschlange
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        //Worst Code EU
        public  bool IsRunning = false;
        public  int Zeitraffer = 1;
        public  int Kunden = 0;
        public  int Bedienzeit = 0;
        public  int Ankunftszeit = 0;
        public int GesamtWartezeit = 0;
        public  int GesamtWartezeiten = 0;
        public  int GesamtKunden = 0;

        private Thread ServiceThread = null;
        private Thread AnkunftsThread = null;
        private int MittlereWartezeit = 0;
        private int MittlereWarteschlangenLänge = 0;

       

        public MainWindow()
        {
            InitializeComponent();
        }

        private void btnStart_Click(object sender, RoutedEventArgs e)
        {
            Console.WriteLine("test ein");
            if(IsRunning)
            {

            }
            else
            {
                if(string.IsNullOrEmpty(txtAnkunftszeit.Text) || string.IsNullOrEmpty(txtBedienzeit.Text))
                {
                    MessageBoxResult result = MessageBox.Show("Bitte geben Sie gültige Werte in die Felder ein!", "Fehler", MessageBoxButton.OK);
                }
                else
                {
                    IsRunning = true;


                    AnkunftsThread = new Thread(this.RunAnkunft);
                    ServiceThread = new Thread(this.RunService);
                    AnkunftsThread.Start();
                    ServiceThread.Start();
                }
            }
        }

        private void btnStop_Click(object sender, RoutedEventArgs e)
        {
            Console.WriteLine("test aus");
            if (IsRunning)
            {
                IsRunning = false;
            }
            else
            {
                
            }
        }

        private void slZeitraffer_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            Zeitraffer = Convert.ToInt32(((Slider)sender).Value);
            Console.WriteLine("Value:" + Convert.ToInt32(((Slider)sender).Value));
        }

        private void txtAnkunftszeit_TextChanged(object sender, TextChangedEventArgs e)
        {
            if (string.IsNullOrEmpty(txtAnkunftszeit.Text))
            {
                MessageBoxResult result = MessageBox.Show("Bitte geben Sie einen gültigen Wert in das Feld ein!", "Fehler", MessageBoxButton.OK);

            }
            else
            {
                Ankunftszeit = Convert.ToInt32(txtAnkunftszeit.Text);
            }
        }

        private void txtBedienzeit_TextChanged(object sender, TextChangedEventArgs e)
        {
            if (string.IsNullOrEmpty(txtBedienzeit.Text))
            {
                MessageBoxResult result = MessageBox.Show("Bitte geben Sie einen gültigen Wert in das Feld ein!", "Fehler", MessageBoxButton.OK);
            }
            else
            {
                Bedienzeit = Convert.ToInt32(txtBedienzeit.Text);
            }
        }

        private void UpdateGui()
        {
            try
            {
                Console.WriteLine("Kunden: " + Kunden);
                Console.WriteLine("Mittlere Wartezeit: " + (GesamtWartezeit / GesamtWartezeiten));
                
                Console.WriteLine("Mittlere Warteschlangenlänge: " +  0);
            }
            catch (Exception ex) {
                Console.WriteLine(ex.Message);
            }
        }

        public void RunService()
        {
            while (IsRunning)
            {
                if (Kunden > 0)
                {
                    int time = Bedienzeit / Zeitraffer;
                    GesamtWartezeit = GesamtWartezeit + time;
                    GesamtWartezeiten++;
                    Thread.Sleep( new TimeSpan(0, 0, time));
                    Kunden--;
                    UpdateGui();
                }
            }
        }

        public void RunAnkunft()
        {
            while (IsRunning)
            {
                Random rngJesus = new Random();
                int waitTime = Convert.ToInt32((-Math.Log(1 - rngJesus.NextDouble()) / Ankunftszeit) / Zeitraffer);
                Thread.Sleep(new TimeSpan(0, 0, waitTime));
                Kunden++;
                GesamtKunden++;
                UpdateGui();
            }
        }

    }
}
