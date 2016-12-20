using System;
using System.CodeDom;
using System.Collections.Generic;

namespace MedianFilterV2_Shared.Commands
{
    [Serializable]
    public abstract class Command
    {
        public const string ServerVersion = "1.0";
        public const string ClientVersion = "1.0";

        public readonly string ThisServerVersion = "1.0";
        public readonly string ThisClientVersion = "1.0";
        public CommandType CommandType { get; set; }
        public Dictionary<string, string> Arguments = new Dictionary<string, string>();

    }


    public enum CommandType
    {
        RegisterClientCommand,
        UnregisterClientCommand,
        FilterCommand
    }
}