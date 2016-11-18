namespace _02_GIS.UI
{
    public class Coords
    {
        public int x1 { get; set; }
        public int y1 { get; set; }
        public int x2 { get; set; }
        public int y2 { get; set; }

        public bool alarm { get; set; }

        public Coords(int x, int y, bool alarm)
        {
            x1 = x;
            y1 = y;
            this.alarm = alarm;
        }
    }
}