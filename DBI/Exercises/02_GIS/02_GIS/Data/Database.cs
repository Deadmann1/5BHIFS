using _02_GIS.Models;
using Oracle.ManagedDataAccess.Client;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Common;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;

namespace _02_GIS.Data
{
    class Database
    {
        #region Fields

        private static Database instance;
        private OracleConnection Connection;
        public List<Mitarbeiter> Mitarbeiter;
        public List<Strassennetz> Strassennetze;
        public List<Straßenteilstrecke> Straßenteilstrecken;
        public List<Einsatzzuordnung> Einsatzzuordnungen;
        public List<Räumungsabschnitt> Räumungsabschnitte;
        public List<Räumungsauftrag> Räumungsaufträge;
        public List<string> ConnectionsAddresses;

        #endregion

        #region Constructor

        private Database()
        {
            ConnectionsAddresses = new List<string>() { "212.152.179.117", "192.168.128.151" };
        }

        public static Database Instance => instance ?? (instance = new Database());

        #endregion

        #region Methods

        public void ConnectToDatabase(string connection)
        {
            try
            {
                Connection = new OracleConnection();
                Connection.ConnectionString = "user id=d5b12;password=d5b;data source=" +
                                              "(DESCRIPTION=(ADDRESS=(PROTOCOL=tcp)" +
                                              "(HOST=" + connection + ")(PORT=1521))(CONNECT_DATA=" +
                                              "(SERVICE_NAME=ora11g)))";
                Connection.Open();
                Console.WriteLine("Connected to Oracle" + Connection.ServerVersion);
            }
            catch (Exception)
            {
                throw (new Exception("Connection could not be established"));
            }
        }

        public void DisconnectFromDatabase()
        {
            try
            {
                Connection.Close();
            }
            catch (Exception)
            {
                throw (new Exception("Connection could not be closed"));
            }
        }

        public async Task GetMitarbeiter()
        {
            this.Mitarbeiter = new List<Mitarbeiter>();
            string sql = @"Select * from Mitarbeiter";
            using (OracleCommand cmd = new OracleCommand(sql, Connection))
            {
                await Task.Run(() =>
                {
                    using (DbDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            int nr = reader.GetInt16(0);
                            string name = reader.GetString(1);
                            int ap = reader.GetInt32(2);
                            this.Mitarbeiter.Add(new Mitarbeiter(nr, name, ap));
                        }
                    }
                }).ConfigureAwait(false);
            }
        }

        public async Task GetRäumungsaufträge()
        {
            this.Räumungsaufträge = new List<Räumungsauftrag>();
            string sql = @"Select * from R_Auftrag";
            using (OracleCommand cmd = new OracleCommand(sql, Connection))
            {
                await Task.Run(() =>
                {
                    using (DbDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            int rNr = reader.GetInt16(0);
                            string aid = reader.GetString(1);
                            int mNr = reader.GetInt32(2);
                            DateTime date = reader.GetDateTime(3);
                            this.Räumungsaufträge.Add(new Räumungsauftrag(rNr, aid, mNr, date));
                        }
                    }
                }).ConfigureAwait(false);
            }
        }

        public async Task GetStraßenteilstrecken(Räumungsabschnitt ra)
        {
            Database.Instance.Straßenteilstrecken = new List<Straßenteilstrecke>();
            try
            {
                await Task.Run(() =>
                {
                    string sql = @"GETTEILSTRECKE";
                    using (OracleCommand cmd2 = new OracleCommand(sql, Connection))
                    {
                        cmd2.CommandType = CommandType.StoredProcedure;
                        cmd2.Parameters.Add("var_aid", OracleDbType.Varchar2).Value = ra.Nr;
                        cmd2.ExecuteNonQuery();
                    }
                    sql = @"Select * from tempteilstrecken";
                    using (OracleCommand cmd3 = new OracleCommand(sql, Connection))
                    {
                        using (DbDataReader reader2 = cmd3.ExecuteReader())
                        {
                            while (reader2.Read())
                            {
                                string aid = reader2.GetString(0);
                                int länge = reader2.GetInt32(1);
                                this.Straßenteilstrecken.Add(new Straßenteilstrecke(aid, länge));
                            }
                        }
                    }
                }).ConfigureAwait(false);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

        public async Task GetRäumungsabschnitte()
        {
            try
            {
                this.Räumungsabschnitte = new List<Räumungsabschnitt>();
                string sql = @"Select * from Strassenabschnitt";
                using (OracleCommand cmd = new OracleCommand(sql, Connection))
                {
                    await Task.Run(() =>
                    {
                        using (DbDataReader reader = cmd.ExecuteReader())
                        {
                            while (reader.Read())
                            {
                                string aid = reader.GetString(0);
                                string name = reader.GetString(1);
                                sql = @"GETTEILSTRECKE";
                                using (OracleCommand cmd2 = new OracleCommand(sql, Connection))
                                {
                                    cmd2.CommandType = CommandType.StoredProcedure;
                                    cmd2.Parameters.Add("var_aid", OracleDbType.Varchar2).Value = aid;
                                    cmd2.ExecuteNonQuery();
                                }
                                sql = @"Select Round(Sum(km)) from tempteilstrecken";
                                using (OracleCommand cmd3 = new OracleCommand(sql, Connection))
                                {
                                    using (DbDataReader reader2 = cmd3.ExecuteReader())
                                    {
                                        try
                                        {
                                            reader2.Read();
                                            int länge = reader2.GetInt32(0);
                                            this.Räumungsabschnitte.Add(new Räumungsabschnitt(aid, name, länge));
                                            reader2.Close();
                                        }
                                        catch (Exception)
                                        {
                                        }
                                    }
                                }
                            }
                        }
                    }).ConfigureAwait(false);

                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

        #endregion
    }
}
    
