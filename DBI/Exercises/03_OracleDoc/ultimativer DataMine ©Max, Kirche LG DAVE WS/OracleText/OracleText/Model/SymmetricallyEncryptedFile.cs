using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OracleText.Model
{
    [Serializable]
    class SymmetricallyEncryptedFile
    {
        public byte[] Key
        {
            get;
            set;
        }

        public byte[] IV
        {
            get;
            set;
        }

        public byte[] Content
        {
            get;
            set;
        }
    }
}
