using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace _02_GIS.Models
{
    class Straßenteilstrecke
    {
        public string AId { get; }
        public float Länge { get; }



        public Straßenteilstrecke(string aId, float länge)
        {
            this.AId = aId;
            this.Länge = länge;
        }
    }
}
