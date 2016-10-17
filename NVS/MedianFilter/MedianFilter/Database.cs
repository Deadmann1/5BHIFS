using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;
using System.Windows.Media.Imaging;

namespace MedianFilter
{
    class Database
    {
        private static Database _instance;
        public Bitmap ImageBefore;
        public Bitmap ImageAfter;
        public List<string> Filters = new List<string>() { "Red", "Blue", "Green" };

        public Database() { }

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
    }
}
