using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OracleText.Model
{
    [Serializable]
    class RSAParametersSerializable
    {
        //
        // Summary:
        //     Represents the D parameter for the System.Security.Cryptography.RSA algorithm.
        public byte[] D;
        //
        // Summary:
        //     Represents the DP parameter for the System.Security.Cryptography.RSA algorithm.
        public byte[] DP;
        //
        // Summary:
        //     Represents the DQ parameter for the System.Security.Cryptography.RSA algorithm.
        public byte[] DQ;
        //
        // Summary:
        //     Represents the Exponent parameter for the System.Security.Cryptography.RSA algorithm.
        public byte[] Exponent;
        //
        // Summary:
        //     Represents the InverseQ parameter for the System.Security.Cryptography.RSA algorithm.
        public byte[] InverseQ;
        //
        // Summary:
        //     Represents the Modulus parameter for the System.Security.Cryptography.RSA algorithm.
        public byte[] Modulus;
        //
        // Summary:
        //     Represents the P parameter for the System.Security.Cryptography.RSA algorithm.
        public byte[] P;
        //
        // Summary:
        //     Represents the Q parameter for the System.Security.Cryptography.RSA algorithm.
        public byte[] Q;
    }
}
