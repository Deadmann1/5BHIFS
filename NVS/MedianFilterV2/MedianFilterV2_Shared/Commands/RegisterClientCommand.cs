using System;
using System.Runtime.CompilerServices;

namespace MedianFilterV2_Shared.Commands
{
    [Serializable]
    public class RegisterClientCommand : Command
    {
        public RegisterClientStatus Status { get; set; }
        public RegisterClientFailures Error { get; set; }
        public string ClientAdress { get; set; }
        public string ClientName { get; set; }

        public RegisterClientCommand()
        {
            this.CommandType = CommandType.RegisterClientCommand;
        }

        public enum RegisterClientStatus
        {
            Connecting,
            ConnectionFailure,
            ConnectionSuccess,
        }
        public enum RegisterClientFailures
        {
            ClientVersionOutdated,
            ServerVersionOutdated,
            ClientNameDuplicate,
            MiscError
        }
    }

   
}
