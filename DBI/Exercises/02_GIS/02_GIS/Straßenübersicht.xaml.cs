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
using _02_GIS.Data;
using _02_GIS.Models;
using _02_GIS.UI;

namespace _02_GIS
{
    /// <summary>
    /// Interaction logic for Straßenübersicht.xaml
    /// </summary>
    public partial class Straßenübersicht : Window
    {
        private readonly double factor = 0.85;
        public Straßenübersicht()
        {
            InitializeComponent();
        }


        private async void btnLadenInRadius_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                CoordPicker picker = new CoordPicker();
                picker.ShowDialog();
                if (picker.Finished)
                {
                    this.canvas.Children.Clear();
                    int x = (int)picker.X;
                    int y = (int)picker.X;
                    int r = (int)picker.X;
                    await Database.Instance.GetKoordinaten();
                    foreach (KeyValuePair<string, Coords> entry in Database.Instance.Koordinaten)
                    {
                        Line line = new Line();
                        line.Stroke = Brushes.LightSteelBlue;

                        line.X1 = entry.Value.x1 * factor;
                        line.X2 = entry.Value.x2 * factor;
                        line.Y1 = canvas.ActualHeight - entry.Value.y1 * factor;
                        line.Y2 = canvas.ActualHeight - entry.Value.y2 * factor;

                        line.StrokeThickness = 2;
                        canvas.Children.Add(line);
                    }
                    Ellipse el = new Ellipse();
                    el.Width = r / 2 * factor;
                    el.Height = r / 2 * factor;
                    el.SetValue(Canvas.LeftProperty, (Double)x / 2);
                    el.SetValue(Canvas.BottomProperty, (Double)y / 2);
                    el.Stroke = Brushes.Red;
                    canvas.Children.Add(el);
                    await Database.Instance.GetMitarbeiterImUmfeld(x, y, r);
                    dataGridMitarbeiter.ItemsSource = null;
                    dataGridMitarbeiter.ItemsSource = Database.Instance.MitarbeiterImUmfeld;
                    Console.WriteLine(Database.Instance.MitarbeiterImUmfeld.Count());
                }

            }
            catch (Exception exception)
            {
                MessageBox.Show($"Error: {exception.Message}");
            }
        }

        private async void btnLadenOhneAuftrag_Click(object sender, RoutedEventArgs e)
        {
            canvas.Children.Clear();
            await PaintStraßennetz();
            await Database.Instance.loadTeilStreckenOhneAuftrag();
            foreach (KeyValuePair<string, Coords> entry in Database.Instance.Koordinaten)
            {
                Line line = new Line();
                if (entry.Value.alarm) line.Stroke = Brushes.Red;

                line.X1 = entry.Value.x1 * factor;
                line.X2 = entry.Value.x2 * factor;
                line.Y1 = canvas.ActualHeight - (entry.Value.y1) * factor;
                line.Y2 = canvas.ActualHeight - (entry.Value.y2) * factor;

                line.StrokeThickness = 2;
                canvas.Children.Add(line);
            }

            dataGridTeilstrecken.ItemsSource = null;
            dataGridTeilstrecken.ItemsSource = Database.Instance.teilstreckeOhneAuftrag;
        }

        private async void btnLoadStraßennetz_Click(object sender, RoutedEventArgs e)
        {
            await PaintStraßennetz();
        }

        private async Task PaintStraßennetz()
        {
            try
            {
                await Database.Instance.GetKoordinaten();
                foreach (KeyValuePair<string, Coords> entry in Database.Instance.Koordinaten)
                {
                    Line line = new Line();
                    line.Stroke = Brushes.LightSteelBlue;

                    line.X1 = entry.Value.x1 * factor;
                    line.X2 = entry.Value.x2 * factor;
                    line.Y1 = canvas.ActualHeight - entry.Value.y1 * factor;
                    line.Y2 = canvas.ActualHeight - entry.Value.y2 * factor;

                    line.StrokeThickness = 2;
                    canvas.Children.Add(line);
                }

            }
            catch (Exception exception)
            {
                MessageBox.Show($"Error: {exception.Message}");
            }
        }
    }
}
