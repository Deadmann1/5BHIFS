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
using System.Windows.Shapes;

namespace _02_GIS
{
    /// <summary>
    /// Interaction logic for DatePickerRA.xaml
    /// </summary>
    public partial class DatePickerRA : Window
    {
        public string dateRA { get; set; }
        public DatePickerRA()
        {
            InitializeComponent();
        }

        private void btnAuftragErstellen_Click(object sender, RoutedEventArgs e)
        {
            if (this.dateAuftrag.SelectedDate != null)
            {
                dateRA = this.dateAuftrag.SelectedDate.Value.ToShortDateString();
                this.Close();
            }
            else
            {
                MessageBox.Show("Bitte wählen sie ein Datum aus!");
            }
        }
    }
}
