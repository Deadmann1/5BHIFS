using System.Collections.Generic;
using System.Runtime.CompilerServices;

namespace MedianFilterV2_Server.Data.Models
{
    public class Client
    {
        public string Name { get; set; }
        public string Address { get; set; }
        public bool IsBusy { get; set; }
        public Client(string name, string address)
        {
            Name = name;
            Address = address;
            IsBusy = false;
        }


        private sealed class NameEqualityComparer : IEqualityComparer<Client>
        {
            public bool Equals(Client x, Client y)
            {
                if (ReferenceEquals(x, y)) return true;
                if (ReferenceEquals(x, null)) return false;
                if (ReferenceEquals(y, null)) return false;
                if (x.GetType() != y.GetType()) return false;
                return string.Equals(x.Name, y.Name);
            }

            public int GetHashCode(Client obj)
            {
                return (obj.Name != null ? obj.Name.GetHashCode() : 0);
            }
        }

        private static readonly IEqualityComparer<Client> NameComparerInstance = new NameEqualityComparer();

        public static IEqualityComparer<Client> NameComparer
        {
            get { return NameComparerInstance; }
        }

        public override string ToString()
        {
            return $"{nameof(Name)}: {Name}, {nameof(Address)}: {Address}, {nameof(IsBusy)}: {IsBusy}";
        }
    }
}