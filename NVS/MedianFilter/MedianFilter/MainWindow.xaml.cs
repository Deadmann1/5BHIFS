using AForge.Imaging.Filters;
using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
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

namespace MedianFilter
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
            this.comboBoxFilter.ItemsSource = Database.Instance.Filters;
            this.comboBoxFilter.SelectedIndex = 0;
        }

        private void MenuItemExit_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void MenuItemLoad_Click(object sender, RoutedEventArgs e)
        {
            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.Title = "Please select an image for filtering!";
            openFileDialog.Filter = "Image Files|*.jpg;*.jpeg;*.png;*.gif;*.tif;...";
            openFileDialog.DefaultExt = ".png";
            if (openFileDialog.ShowDialog() == true)
            {
                Bitmap img = new Bitmap(openFileDialog.FileName);
                Database.Instance.ImageBefore = img;
                this.imageBefore.Source = BitmapToImageSource(img);
            }
        }

        private void btnFilter_Click(object sender, RoutedEventArgs e)
        {
            if (Database.Instance.ImageBefore == null)
            {
                MessageBox.Show("You should select an Image before filtering!", "Error:", MessageBoxButton.OK);
            }
            else
            {
                ChannelFiltering filter = new ChannelFiltering();
                bool colourFilter = false;
                int medianFilterMatrixSize = 0;
                switch (comboBoxFilter.SelectedItem.ToString())
                {
                    case "Red":
                        filter.Red = new AForge.IntRange(0, 0);
                        filter.Blue = new AForge.IntRange(0, 255);
                        filter.Green = new AForge.IntRange(0, 255);
                        colourFilter = true;
                        break;
                    case "Blue":
                        filter.Red = new AForge.IntRange(0, 255);
                        filter.Blue = new AForge.IntRange(0, 0);
                        filter.Green = new AForge.IntRange(0, 255);
                        colourFilter = true;
                        break;
                    case "Green":
                        filter.Red = new AForge.IntRange(0, 255);
                        filter.Blue = new AForge.IntRange(0, 255);
                        filter.Green = new AForge.IntRange(0, 0);
                        colourFilter = true;
                        break;
                    case "MedianFilter 20x20":
                        colourFilter = false;
                        medianFilterMatrixSize = 20;
                        break;
                    case "MedianFilter 11x11":
                        colourFilter = false;
                        medianFilterMatrixSize = 11;
                        break;
                    case "MedianFilter 7x7":
                        colourFilter = false;
                        medianFilterMatrixSize = 7;
                        break;
                }
                Bitmap tmp = Database.Instance.ImageBefore;
                if (colourFilter) { 
                    Database.Instance.ImageAfter = filter.Apply(tmp);
                }
                else
                {
                    Database.Instance.ImageAfter = MedianFilterFactory.DoMedianFilter2(tmp, medianFilterMatrixSize);
                }
                imageAfter.Source = BitmapToImageSource(Database.Instance.ImageAfter);
            }
        }

        private BitmapImage BitmapToImageSource(Bitmap bitmap)
        {
            using (MemoryStream memory = new MemoryStream())
            {
                bitmap.Save(memory, System.Drawing.Imaging.ImageFormat.Bmp);
                memory.Position = 0;
                BitmapImage bitmapimage = new BitmapImage();
                bitmapimage.BeginInit();
                bitmapimage.StreamSource = memory;
                bitmapimage.CacheOption = BitmapCacheOption.OnLoad;
                bitmapimage.EndInit();

                return bitmapimage;
            }
        }

        private void MenuItemSave_Click(object sender, RoutedEventArgs e)
        {
            if(Database.Instance.ImageAfter == null)
            {
                MessageBox.Show("You should select an Image and filter it before trying to save!", "Error:", MessageBoxButton.OK);
            }
            else
            {
                SaveFileDialog dialog = new SaveFileDialog();
                dialog.Filter = "Images|*.png;*.bmp;*.jpg";
                ImageFormat format = ImageFormat.Png;
                if (dialog.ShowDialog() == true)
                {
                    string ext = System.IO.Path.GetExtension(dialog.FileName);
                    switch (ext)
                    {
                        case ".jpg":
                            format = ImageFormat.Jpeg;
                            break;
                        case ".bmp":
                            format = ImageFormat.Bmp;
                            break;
                    }
                    Database.Instance.ImageAfter.Save(dialog.FileName, format);
                }
            }
        }
    }
}
