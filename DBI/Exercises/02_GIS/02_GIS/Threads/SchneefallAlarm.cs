using System.Threading;
using System.Threading.Tasks;
using _02_GIS.Data;
using _02_GIS.Models;

namespace _02_GIS.Threads
{
    public class SchneefallAlarm
    {
        public delegate void Callback_Message(string msg);

        public Callback_Message CallbackMessage { get; set; }

        public bool IsFinished { get; set; }

        public async void StartAlarm()
        {
            IsFinished = false;
            while (!IsFinished)
            {
                await Database.Instance.GetSchnee();
                foreach (Schneestraße str in Database.Instance.Schneestraßen)
                {
                    if (str.Höhe >= 10)
                    {
                        CallbackMessage("Achtung bitte eine neuen Räumungsauftrag für: " + str.AId +
                                        " mit einer Schneehöhe von: " + str.Höhe);
                    }
                }
                Thread.Sleep(10000);
            }
        }
    }
}