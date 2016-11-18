using System;

namespace _02_GIS.Models
{
    public class MitarbeiterImUmfeld
    {
        public String AID { get; set; }
        public String MA_NAME { get; set; }
        public DateTime RA_DATE { get; set; }

        public MitarbeiterImUmfeld(string aid, string ma_name, DateTime ra_date)
        {
            this.AID = aid;
            this.MA_NAME = ma_name;
            this.RA_DATE = ra_date;
        }
    }
}