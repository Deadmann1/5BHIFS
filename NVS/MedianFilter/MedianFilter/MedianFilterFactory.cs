using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Imaging;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;

namespace MedianFilter
{
    public static class MedianFilterFactory
    {
        public static Bitmap DoMedianFilter(this Bitmap sourceBitmap,
                                               int matrixSize,
                                                 int bias = 0,
                                        bool grayscale = false)
        {
            BitmapData sourceData =
                       sourceBitmap.LockBits(new Rectangle(0, 0,
                       sourceBitmap.Width, sourceBitmap.Height),
                       ImageLockMode.ReadOnly,
                       PixelFormat.Format32bppArgb);

            byte[] pixelBuffer = new byte[sourceData.Stride *
                                          sourceData.Height];

            byte[] resultBuffer = new byte[sourceData.Stride *
                                           sourceData.Height];

            Marshal.Copy(sourceData.Scan0, pixelBuffer, 0,
                                       pixelBuffer.Length);

            sourceBitmap.UnlockBits(sourceData);

            if (grayscale == true)
            {
                float rgb = 0;

                for (int k = 0; k < pixelBuffer.Length; k += 4)
                {
                    rgb = pixelBuffer[k] * 0.11f;
                    rgb += pixelBuffer[k + 1] * 0.59f;
                    rgb += pixelBuffer[k + 2] * 0.3f;


                    pixelBuffer[k] = (byte)rgb;
                    pixelBuffer[k + 1] = pixelBuffer[k];
                    pixelBuffer[k + 2] = pixelBuffer[k];
                    pixelBuffer[k + 3] = 255;
                }
            }

            int filterOffset = (matrixSize - 1) / 2;
            int calcOffset = 0;

            int byteOffset = 0;

            List<int> neighbourPixels = new List<int>();
            byte[] middlePixel;

            for (int offsetY = filterOffset; offsetY <
                sourceBitmap.Height - filterOffset; offsetY++)
            {
                for (int offsetX = filterOffset; offsetX <
                    sourceBitmap.Width - filterOffset; offsetX++)
                {
                    byteOffset = offsetY *
                                 sourceData.Stride +
                                 offsetX * 4;

                    neighbourPixels.Clear();

                    for (int filterY = -filterOffset;
                        filterY <= filterOffset; filterY++)
                    {
                        for (int filterX = -filterOffset;
                            filterX <= filterOffset; filterX++)
                        {

                            calcOffset = byteOffset +
                                         (filterX * 4) +
                                         (filterY * sourceData.Stride);

                            neighbourPixels.Add(BitConverter.ToInt32(
                                             pixelBuffer, calcOffset));
                        }
                    }

                    neighbourPixels.Sort();

                    middlePixel = BitConverter.GetBytes(
                                       neighbourPixels[filterOffset]);

                    resultBuffer[byteOffset] = middlePixel[0];
                    resultBuffer[byteOffset + 1] = middlePixel[1];
                    resultBuffer[byteOffset + 2] = middlePixel[2];
                    resultBuffer[byteOffset + 3] = middlePixel[3];
                }
            }

            Bitmap resultBitmap = new Bitmap(sourceBitmap.Width,
                                             sourceBitmap.Height);

            BitmapData resultData =
                       resultBitmap.LockBits(new Rectangle(0, 0,
                       resultBitmap.Width, resultBitmap.Height),
                       ImageLockMode.WriteOnly,
                       PixelFormat.Format32bppArgb);

            Marshal.Copy(resultBuffer, 0, resultData.Scan0,
                                       resultBuffer.Length);

            resultBitmap.UnlockBits(resultData);

            return resultBitmap;
        }
        public static Bitmap DoMedianFilter2(Bitmap Image, int Size)
   {
       System.Drawing.Bitmap TempBitmap = Image;
       System.Drawing.Bitmap NewBitmap = new System.Drawing.Bitmap(TempBitmap.Width, TempBitmap.Height);
        System.Drawing.Graphics NewGraphics = System.Drawing.Graphics.FromImage(NewBitmap);
        NewGraphics.DrawImage(TempBitmap, new System.Drawing.Rectangle(0, 0, TempBitmap.Width, TempBitmap.Height), new System.Drawing.Rectangle(0, 0, TempBitmap.Width, TempBitmap.Height), GraphicsUnit.Pixel);
        NewGraphics.Dispose();
        Random TempRandom = new Random();
      int ApetureMin = -(Size / 2);
      int ApetureMax = (Size / 2);
       for (int x = 0; x<NewBitmap.Width; ++x)
      {
          for (int y = 0; y<NewBitmap.Height; ++y)
           {
              List<int> RValues = new List<int>();
             List<int> GValues = new List<int>();
              List<int> BValues = new List<int>();
              for (int x2 = ApetureMin; x2<ApetureMax; ++x2)
               {
                   int TempX = x + x2;
                   if (TempX >= 0 && TempX<NewBitmap.Width)
                {
                       for (int y2 = ApetureMin; y2<ApetureMax; ++y2)
                       {
                          int TempY = y + y2;
                         if (TempY >= 0 && TempY<NewBitmap.Height)
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
   }
    }
}
