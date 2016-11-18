using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace _02_GIS.Models
{
    class Mitarbeiter
    {
        public int MitarbeiterNr { get; set; }

        public string MitarbeiterName { get; set; }

        public int Arbeitspensum { get; set; }

        public Mitarbeiter(int maNr, string maName, int ap)
        {
            MitarbeiterNr = maNr;
            MitarbeiterName = maName;
            Arbeitspensum = ap;
        }

        public override string ToString()
        {
            return $"{nameof(Arbeitspensum)}: {Arbeitspensum}, {nameof(MitarbeiterNr)}: {MitarbeiterNr}, {nameof(MitarbeiterName)}: {MitarbeiterName}";
        }
    }
}
