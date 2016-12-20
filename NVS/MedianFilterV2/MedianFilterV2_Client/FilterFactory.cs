using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;

namespace MedianFilterV2_Client
{
    public static class FilterFactory
    {
        public static async Task<Bitmap> DoMedianFilter(Bitmap Image, int Size)
        {
            var ret = await Task.Run(() =>
            {
                System.Drawing.Bitmap TempBitmap = Image;
                System.Drawing.Bitmap NewBitmap = new System.Drawing.Bitmap(TempBitmap.Width, TempBitmap.Height);
                System.Drawing.Graphics NewGraphics = System.Drawing.Graphics.FromImage(NewBitmap);
                NewGraphics.DrawImage(TempBitmap, new System.Drawing.Rectangle(0, 0, TempBitmap.Width, TempBitmap.Height), new System.Drawing.Rectangle(0, 0, TempBitmap.Width, TempBitmap.Height), GraphicsUnit.Pixel);
                NewGraphics.Dispose();
                Random TempRandom = new Random();
                int ApetureMin = -(Size / 2);
                int ApetureMax = (Size / 2);
                for (int x = 0; x < NewBitmap.Width; ++x)
                {
                    for (int y = 0; y < NewBitmap.Height; ++y)
                    {
                        List<int> RValues = new List<int>();
                        List<int> GValues = new List<int>();
                        List<int> BValues = new List<int>();
                        for (int x2 = ApetureMin; x2 < ApetureMax; ++x2)
                        {
                            int TempX = x + x2;
                            if (TempX >= 0 && TempX < NewBitmap.Width)
                            {
                                for (int y2 = ApetureMin; y2 < ApetureMax; ++y2)
                                {
                                    int TempY = y + y2;
                                    if (TempY >= 0 && TempY < NewBitmap.Height)
                                    {
                                        Color TempColor = TempBitmap.GetPixel(TempX, TempY);
                                        RValues.Add(TempColor.R);
                                        GValues.Add(TempColor.G);
                                        BValues.Add(TempColor.B);
                                    }
                                }
                            }
                        }
                        RValues.Sort();
                        GValues.Sort();
                        BValues.Sort();
                        Color MedianPixel = Color.FromArgb(RValues[RValues.Count / 2],
                        GValues[GValues.Count / 2],
                        BValues[BValues.Count / 2]);
                        NewBitmap.SetPixel(x, y, MedianPixel);
                    }
                }
                return NewBitmap;
            });
            return ret;
        }
        /*
        public static async Task<Bitmap> DoRedFilter(Bitmap Image)
        {
        }
        public static async Task<Bitmap> DoBlueFilter(Bitmap Image)
        {
        }
        public static async Task<Bitmap> Do(Bitmap Image)
        {

        }

        private static async Task<Bitmap> DoColourFilter(Bitmap Image)
        {
            /*
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
                        */
            /*if (colourFilter)
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
        }
    */
    }
}
