namespace _02_GIS.Models
{
    public class Räumungsabschnitt
    {
        public string Nr { get; }
        public string AId { get; }
        public int Länge { get; }

        public Räumungsabschnitt(string nr, string aId, int länge)
        {
            Nr = nr;
            AId = aId;
            this.Länge = länge;
        }
    }
}