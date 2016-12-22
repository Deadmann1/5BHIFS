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

namespace OracleText.View
{
    /// <summary>
    /// Interaction logic for DisplayHtmlFileWindow.xaml
    /// </summary>
    public partial class DisplayHtmlFileWindow : Window
    {
        public DisplayHtmlFileWindow(String htmlFileFullPathAndName)
        {
            InitializeComponent();

            wbrwsrHtmlFile.Navigate("file://" + htmlFileFullPathAndName);
        }
    }
}
