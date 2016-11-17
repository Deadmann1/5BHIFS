using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace _02_GIS.Models
{
    class Mitarbeiter
    {
        private int mirarbeiterNr;
        private string mitarbeiterName;
        private int arbeitspensum;

       

        public int MirarbeiterNr
        {
            get
            {
                return mirarbeiterNr;
            }

            set
            {
                mirarbeiterNr = value;
            }
        }

        public string MitarbeiterName
        {
            get
            {
                return mitarbeiterName;
            }

            set
            {
                mitarbeiterName = value;
            }
        }
        public int Arbeitspensum
        {
            get
            {
                return arbeitspensum;
            }

            set
            {
                arbeitspensum = value;
            }
        }
        public Mitarbeiter(int maNr, string maName, int ap)
        {
            mirarbeiterNr = maNr;
            mitarbeiterName = maName;
            arbeitspensum = ap;
        }

        public override string ToString()
        {
            return $"{nameof(Arbeitspensum)}: {Arbeitspensum}, {nameof(MirarbeiterNr)}: {MirarbeiterNr}, {nameof(MitarbeiterName)}: {MitarbeiterName}";
        }
    }
}
