﻿using System;
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
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.IO.Ports;

namespace EinAus
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private SerialPort sp;
        public MainWindow()
        {
            InitializeComponent();
            sp = new SerialPort();
        }

        private void btnEin_Click(object sender, RoutedEventArgs e)
        {
            //sp.Open();
            //sp.WriteLine("TEST");
            //string st = sp.ReadLine();
            //sp.Close();
            Console.WriteLine("TESTEST");
        }
    }
}
