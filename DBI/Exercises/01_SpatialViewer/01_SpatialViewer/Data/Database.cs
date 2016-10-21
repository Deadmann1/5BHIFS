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
            "Data Source=D5B12/D5B@//192.168.128.152:1521/ora11g.D5B12";
            string queryString =
                "SELECT * FROM straßennetz";
            using (OracleConnection connection =
                       new OracleConnection(connectionString))
            {
                OracleCommand command = connection.CreateCommand();
                command.CommandText = queryString;

                try
                {
                    connection.Open();

                    OracleDataReader reader = command.ExecuteReader();

                    while (reader.Read())
                    {
                        Console.WriteLine("ID = {0} Name = {1} Von = {2} Nach = {3} Geotype = {4} Shape = {5}",
                            reader[0], reader[1], reader[2], reader[3], reader[4], reader[5]);
                    }
                    reader.Close();
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
