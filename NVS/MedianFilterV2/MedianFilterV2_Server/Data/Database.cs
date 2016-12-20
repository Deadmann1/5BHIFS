using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Drawing;
using System.Threading.Tasks;
using MedianFilterV2_Server.Data.Models;

namespace MedianFilterV2_Server.Data
{
    class Database
    {
        private static Database _instance;
        public Bitmap ImageBefore;
        public Bitmap ImageAfter;
        public ObservableCollection<Client> Clients;
        public List<string> Filters = new List<string>() { "Red", "Blue", "Green", "MedianFilter"};

        public Database()
        {
            Clients = new ObservableCollection<Client>();
        }

        public static Database Instance
        {
            get
            {
                if (_instance == null)
                {
                    _instance = new Database();
                    
                }
                return _instance;
            }
        }

        public List<Bitmap> GetSplitImages()
        {
            List<Bitmap> images = new List<Bitmap>();
            
            //Only 2 Halfs right now, need Algorithm to split in x (selectable) parts =( :/
            Bitmap originalImage = Database.Instance.ImageBefore;

            Rectangle rect = new Rectangle(0, 0, originalImage.Width / 2, originalImage.Height);
            Bitmap firstHalf = originalImage.Clone(rect, originalImage.PixelFormat);

            rect = new Rectangle(originalImage.Width / 2, 0, originalImage.Width / 2, originalImage.Height);
            Bitmap secondHalf = originalImage.Clone(rect, originalImage.PixelFormat);

            images.Add(firstHalf);
            images.Add(secondHalf);

            return images;
        }
    }
}
