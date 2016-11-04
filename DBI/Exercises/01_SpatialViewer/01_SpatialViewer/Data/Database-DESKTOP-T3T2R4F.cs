using Oracle.ManagedDataAccess.Client;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace _01_SpatialViewer.Data
{
    class Database
    {
        private static Database instance;

        private Database() { }

        public static Database Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new Database();
                }
                return instance;
            }
        }

        public List<GeoPoint> GetGeoInfo()
        {
            string connectionString =
            "Data Source=192.168.128.152/ora11g;User Id=d5b12;Password=d5b";
            string queryString =
                "select s.id,t.x, t.y from strassennetz s, TABLE(SDO_UTIL.GETVERTICES(s.shape)) t";
            using (OracleConnection connection =
                       new OracleConnection(connectionString))
            {
                OracleCommand command = connection.CreateCommand();
                command.CommandText = queryString;

                try
                {
                    connection.Open();
                    OracleDataReader reader = command.ExecuteReader();
                    reader.
                    Console.WriteLine("Id{0}",reader[0].ToString());
                }
                catch (Exception ex)
                {
                    Console.WriteLine(ex.Message);
                }
            }

            return null;
        }



    }
}
