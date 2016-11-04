using _02_GIS.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace _02_GIS.Data
{
    class Database
    {
        #region Fields
        private static Database instance;
        private List<Mitarbeiter> mitarbeiter;
        private List<Strassenabschnitt> strassenabschnitte;
        private List<Strassennetz> strassennetze;
        private List<Teilstrecke> teilstrecken;
        private List<Einsatzzuordnung> einsatzzuordnungen;
        #endregion

        #region Constructor
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
        #endregion

        #region Methods
        public void ConnectToDb()
        {

        }
        #endregion
    }
}
