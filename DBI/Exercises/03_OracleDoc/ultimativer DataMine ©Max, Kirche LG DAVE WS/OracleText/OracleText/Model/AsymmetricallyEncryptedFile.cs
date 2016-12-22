using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace OracleText.Model
{
    [Serializable]
    class AsymmetricallyEncryptedFile
    {
        public byte[] Content
        {
            get;
            set;
        }

        public RSAParametersSerializable RsaParameters
        {
            get;
            set;
        }

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
    }
}
