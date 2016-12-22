using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;
using System.Data.OleDb;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OracleText.Model
{
    public class OracleTextDatabaseManager : DatabaseManager
    {
        public OracleTextDatabaseManager(String ipAdresse, String username, String password, String service) : base("Provider=OraOLEDB.Oracle; Data Source=" + ipAdresse + "/" + service + ";User Id=" + username + ";Password=" + password + ";OLEDB.NET=True;")
        {
            
        }

        public IEnumerable<Document> GetDocumentsContainingString(string searchString)
        {
            List<Document> documents = new List<Document>();

            ExecuteNonQuery("DELETE FROM docsresult_5b2016");
            ExecuteProcedure("markup_docs_5b2016", new String[] { "suchstr" }, new OleDbType[] { OleDbType.VarChar}, new ParameterDirection[] {ParameterDirection.Input}, new Object[] { searchString });

            using (IDataReader readerFilteredDocuments = ExecuteQuery("SELECT id, title, text FROM docsresult_5B2016"))
            {
                while (readerFilteredDocuments.Read())
                {

                    int id = Convert.ToInt32(readerFilteredDocuments.GetValue(0));
                    String title = readerFilteredDocuments.GetString(1);
                    String content = readerFilteredDocuments.GetString(2);

                    documents.Add(new Document(id, title, content));
                }
            }
            
            return documents;
        }
    }
}
