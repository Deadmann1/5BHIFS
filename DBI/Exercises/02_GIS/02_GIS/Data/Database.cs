using _02_GIS.Models;
using Oracle.ManagedDataAccess.Client;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;
using System.Data.Common;
using System.Data.OleDb;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;
using _02_GIS.UI;

namespace _02_GIS.Data
{
    class Database
    {
        #region Fields

        private static Database instance;
        private OracleConnection Connection;
        public List<Mitarbeiter> Mitarbeiter;
        public List<Straßenteilstrecke> Straßenteilstrecken;
        public List<Einsatzzuordnung> Einsatzzuordnungen;
        public List<Räumungsabschnitt> Räumungsabschnitte;
        public List<Räumungsauftrag> Räumungsaufträge;
        public List<string> ConnectionsAddresses;
        public List<Schneestraße> Schneestraßen;
        public List<MitarbeiterImUmfeld> MitarbeiterImUmfeld = new List<MitarbeiterImUmfeld>();
        public List<Räumungsabschnitt> TeilstreckenOhneAuftrag = new List<Räumungsabschnitt>();
        public List<Straßenteilstrecke> Abschnitte = new List<Straßenteilstrecke>();
        public Dictionary<String, Coords> Koordinaten;
        #endregion

        #region Constructor

        private Database()
        {
            ConnectionsAddresses = new List<string>() { "212.152.179.117", "192.168.128.152" };
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

        public async Task AddRäumungsauftrag(Räumungsabschnitt räumungsabschnitt, Mitarbeiter mitarbeiter, DateTime dateRA)
        {
            try
            {
                this.Mitarbeiter = new List<Mitarbeiter>();
                string sql = @"insertRaeumungsAuftrag";
                using (OracleCommand cmd = new OracleCommand(sql, Connection))
                {
                    await Task.Run(() =>
                    {
                        cmd.CommandType = CommandType.StoredProcedure;
                        cmd.Parameters.Add("@P1", räumungsabschnitt.Nr);
                        cmd.Parameters.Add("@P2", mitarbeiter.MitarbeiterNr);
                        cmd.Parameters.Add(dateRA.ToString("dd-MMM-yyyy"), OracleDbType.Date).Value = dateRA;
                        cmd.ExecuteNonQuery();
                    }).ConfigureAwait(false);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex);
                throw new Exception("Räumungsauftrag konnte nicht erstellt werden, bitte überprüfen sie ihre Eingaben!");
            }
        }

        public async Task GetRäumungsaufträge()
        {
            this.Räumungsaufträge = new List<Räumungsauftrag>();
            string sql = @"Select * from R_AUFTRAG";
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

        public async Task GetSchnee()
        {
            try
            {
                Schneestraßen = new List<Schneestraße>();
                string sql = "SELECT * FROM SCHNEEFALL";
                using (OracleCommand cmd = new OracleCommand(sql, Connection))
                {
                    await Task.Run(() =>
                    {
                        using (DbDataReader reader = cmd.ExecuteReader())
                        {
                            while (reader.Read())
                            {
                                string aid = reader.GetString(0);
                                int höhe = (int)reader.GetDecimal(1);

                                Schneestraße so = new Schneestraße(aid, höhe);
                                Schneestraßen.Add(so);
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

        public async Task GetKoordinaten()
        {
            try
            {
                Koordinaten = new Dictionary<String, Coords>();
                string sql = "SELECT ts.id, t.X, t.Y " +
                                "FROM teilstrecke ts, " +
                                "TABLE(SDO_UTIL.GETVERTICES(ts.shape)) t " +
                                "order by ts.id asc";
                using (OracleCommand cmd = new OracleCommand(sql, Connection))
                {
                    await Task.Run(() =>
                    {
                        using (DbDataReader reader = cmd.ExecuteReader())
                        {
                            while (reader.Read())
                            {
                                string id = reader.GetString(0);
                                int x = (int)reader.GetDecimal(1);
                                int y = (int)reader.GetDecimal(2);
                                if (Koordinaten.Keys.Contains(id))
                                {
                                    Koordinaten[id].x2 = x / 2;
                                    Koordinaten[id].y2 = y / 2;
                                }
                                else
                                {
                                    Koordinaten.Add(id, new Coords(x / 2, y / 2, false));
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

        public async Task GetMitarbeiterImUmfeld(int x, int y, int r)
        {
            try
            {
                Koordinaten = new Dictionary<String, Coords>();
                string sql = "SELECT RA_STRECKE.AID, MITARBEITER.MA_NAME, R_AUFTRAG.RDATE FROM R_AUFTRAG INNER JOIN RA_STRECKE ON (R_AUFTRAG.RA_NR = RA_STRECKE.RA_NR) INNER JOIN MITARBEITER ON (MITARBEITER.MA_NR = R_AUFTRAG.MA_NR) INNER JOIN TEILSTRECKE ts ON (RA_STRECKE.AID = ts.ID) WHERE SDO_ANYINTERACT(ts.shape, sdo_geom.sdo_geometry(2003, NULL, NULL,  SDO_ELEM_INFO_ARRAY(1,1003,4), SDO_ORDINATE_ARRAY(:a, :b, :c, :d, :e, :f))) = 'TRUE'";

                using (OracleCommand cmd = new OracleCommand(sql, Connection))
                {
                    cmd.Parameters.Add(":a", x);
                    cmd.Parameters.Add(":b", y - r);
                    cmd.Parameters.Add(":c", x + r);
                    cmd.Parameters.Add(":d", y);
                    cmd.Parameters.Add(":e", x);
                    cmd.Parameters.Add(":f", y + r);
                    await Task.Run(() =>
                    {
                        using (DbDataReader reader = cmd.ExecuteReader())
                        {
                            while (reader.Read())
                            {
                                string aid = reader.GetString(0);
                                string ma_name = reader.GetString(1);
                                DateTime date = reader.GetDateTime(2);
                                MitarbeiterImUmfeld.Add(new MitarbeiterImUmfeld(aid, ma_name, date));
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

        public async Task loadTeilStreckenOhneAuftrag()
        {
            try
            {
                Koordinaten = new Dictionary<String, Coords>();
                string sql = "select distinct(ts.id), faktor, SDO_GEOM.SDO_LENGTH(ts.shape, m.diminfo) km, t.X, t.Y, s.aid1 abschnitt from teilstrecke ts, user_sdo_geom_metadata m, TABLE(SDO_UTIL.GETVERTICES(ts.shape)) t, strassennetz s where ts.id not in (select distinct(aid) from ra_strecke) AND ts.id = s.aid2 order by ts.id asc";

                using (OracleCommand cmd = new OracleCommand(sql, Connection))
                {
                    await Task.Run(() =>
                    {
                        using (DbDataReader reader = cmd.ExecuteReader())
                        {
                            TeilstreckenOhneAuftrag.Clear();
                            while (reader.Read())
                            {
                                string aid = reader.GetString(0);
                                int km = (int)reader.GetDecimal(2);
                                int x = (int)reader.GetDecimal(3);
                                int y = (int)reader.GetDecimal(4);
                                string abschnitt = reader.GetString(5);
                                if (Koordinaten.Keys.Contains(aid))
                                {
                                    Koordinaten[aid].x2 = x / 2;
                                    Koordinaten[aid].y2 = y / 2;
                                }
                                else
                                {
                                    Koordinaten.Add(aid, new Coords(x / 2, y / 2, true));
                                }
                                Räumungsabschnitt t = new Räumungsabschnitt(aid, abschnitt, km);
                                Console.WriteLine(t);
                                bool outcome = false;
                                foreach (Räumungsabschnitt t1 in TeilstreckenOhneAuftrag)
                                {
                                    if (t1.AId.Equals(t.AId))
                                    {
                                        outcome = true;
                                    }
                                }


                                if (!outcome)
                                {
                                    TeilstreckenOhneAuftrag.Add(t);
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

    }
}

