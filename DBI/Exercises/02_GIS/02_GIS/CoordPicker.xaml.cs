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
    public partial class CoordPicker : Window
    {

        public Boolean Finished { get; set; }
        public Double X { get; set; }
        public Double Y { get; set; }

        public Double Radius { get; set; }

        public CoordPicker()
        {
            InitializeComponent();
            this.Finished = false;
        }

        private void btnSuchradiusLaden_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                if (String.IsNullOrEmpty(this.txtRadius.Text) || String.IsNullOrEmpty(this.txtX.Text) || String.IsNullOrEmpty(this.txtY.Text))
                    throw new Exception("Bitte füllen sie alle Felder aus!");

                this.X = Convert.ToDouble(this.txtX.Text);
                this.Y = Convert.ToDouble(this.txtY.Text);
                this.Radius = Convert.ToDouble(this.txtRadius.Text);
                this.Finished = true;
                this.Close();
            }
            catch (Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
        }
    }
}
