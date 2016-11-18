namespace _02_GIS.Models
{
    public class Schneestraße
    {
        public string AId { get; set; }
        public int Höhe { get; set; }

        public Schneestraße(string aid, int höhe)
        {
            this.AId = aid;
            this.Höhe = höhe;
        }
    }
}