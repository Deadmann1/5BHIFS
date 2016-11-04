using AForge.Imaging.Filters;
using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
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

namespace MedianFilter
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        bool isRunning = false;
        public MainWindow()
        {
            InitializeComponent();
            this.comboBoxFilter.ItemsSource = Database.Instance.Filters;
            this.comboBoxFilter.SelectedIndex = 0;
            this.txtSize.Visibility = Visibility.Hidden;
            this.lblSize.Visibility = Visibility.Hidden;
            this.lblTime.Visibility = Visibility.Hidden;
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

        private async void btnFilter_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                if (!isRunning)
                {
                    lblTime.Content = "";
                    isRunning = true;
                    if (Database.Instance.ImageBefore == null)
                    {
                        throw new Exception("You should select an Image before filtering!");
                    }
                    else
                    {
                        ChannelFiltering filter = new ChannelFiltering();
                        bool colourFilter = false;
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
                            case "MedianFilter":
                                colourFilter = false;
                                break;
                        }
                        Bitmap tmp = Database.Instance.ImageBefore;
                        if (colourFilter)
                        {
                            Database.Instance.ImageAfter = filter.Apply(tmp);
                        }
                        else
                        {
                            if (this.txtSize.Text == null || int.Parse(this.txtSize.Text) <= 1)
                            {
                                throw new Exception("You need to input an Matrix size of 2-99 for the MedianFilter !");
                            }
                            
                            this.progessBar.Maximum = tmp.Width;
                            var progressHandler = new Progress<int>(value =>
                            {
                                progessBar.Value = value;
                            });
                            var progress = progressHandler as IProgress<int>;
                            var watch = System.Diagnostics.Stopwatch.StartNew();
                            Database.Instance.ImageAfter = await MedianFilterFactory.DoMedianFilter2(tmp, int.Parse(this.txtSize.Text), progress);
                            watch.Stop();
                            var ms = watch.ElapsedMilliseconds;
                            lblTime.Content = "" + ms + " milliseconds or " + (ms / 1000) + " seconds";
                        }
                        imageAfter.Source = BitmapToImageSource(Database.Instance.ImageAfter);
                        isRunning = false;
                    }
                }
                else
                {
                    throw new Exception("You need to wait for the first filtering to end!");
                }
            }
            catch (Exception ex)
            {
                if(ex.Message != "You need to wait for the first filtering to end!")
                {
                    isRunning = false;
                }
                MessageBox.Show("Error happend: " + ex.Message, "Error:", MessageBoxButton.OK);
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

        private void comboBoxFilter_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if(comboBoxFilter.SelectedItem.ToString() == "MedianFilter")
            {
                this.txtSize.Visibility = Visibility.Visible;
                this.lblSize.Visibility = Visibility.Visible;
                this.lblTime.Visibility = Visibility.Visible;
            }
            else
            {
                this.txtSize.Visibility = Visibility.Hidden;
                this.lblSize.Visibility = Visibility.Hidden;
                this.lblTime.Visibility = Visibility.Hidden;
            }
        }

        private void NumberValidationTextBox(object sender, TextCompositionEventArgs e)
        {
            Regex regex = new Regex("[^0-9]+");
            e.Handled = regex.IsMatch(e.Text);
        }
    }
}
