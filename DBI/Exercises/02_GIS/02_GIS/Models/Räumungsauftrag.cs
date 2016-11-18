using System;
using System.Windows.Controls;

namespace _02_GIS.Models
{
    public class Räumungsauftrag
    {
        public int Nr { get; }
        public string AId { get; }
        public int MitarbeiterNr { get; }
        public DateTime Datum { get; }

        public Räumungsauftrag(int nr, string aId, int mitarbeiterNr, DateTime datum)
        {
            Nr = nr;
            AId = aId;
            MitarbeiterNr = mitarbeiterNr;
            Datum = datum;
        }


    }
}