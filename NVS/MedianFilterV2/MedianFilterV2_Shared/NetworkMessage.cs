using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MedianFilterV2_Shared.Commands;

namespace MedianFilterV2_Shared
{
    [Serializable]
    public class NetworkMessage
    {
        public List<String> Receivers { get; set; }
        public string Message { get; set; }
        public Command Command { get; set; }
    }
}
