using System;

namespace MedianFilterV2_Shared.Commands
{
    [Serializable]
    public class FilterCommand : Command
    {
        public FilterCommandStatus Status { get; set; }
        public FilterType Type { get; set; }
        public byte[] Payload { get; set; }

        public enum FilterType
        {
            MedianFilter,
            ColourFilter
        }

        public enum FilterCommandStatus
        {
            Filtering,
            FilterSuccess,
            FilterFailure
        }
    }
}