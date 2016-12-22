using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.OleDb;
using System.Data;

using System.Windows;

namespace OracleText.Model
{
    public class DatabaseManager : IDisposable
    {
        private OleDbConnection Connection;
        private OleDbTransaction Transaction;

        public DatabaseManager(String connectionString)
        {
            Connection = new OleDbConnection(connectionString);

            Connection.Open();
        }

        private OleDbType GetOleDbType (Object obj)
        {
            Type type = obj.GetType();
            OleDbType oleDbType = OleDbType.Empty;

            if (type == typeof(int))
            {
                oleDbType = OleDbType.Integer; 
            }
            else if (type == typeof(double))
            {
                oleDbType = OleDbType.Double;
            }
            else if (type == typeof(String))
            {
                oleDbType = OleDbType.VarChar;
            }
            else if (type == typeof(DateTime))
            {
                oleDbType = OleDbType.Date;
            }
            else if (type == typeof(bool))
            {
                oleDbType = OleDbType.Boolean;
            }
            else if (type == typeof(char))
            {
                oleDbType = OleDbType.Char;
            }
            else if (type == typeof(Guid))
            {
                oleDbType = OleDbType.Guid;
            }

            return oleDbType;
        }

        protected IDataReader ExecuteQuery (String sqlCommand, IEnumerable<Object> parameters = null)
        {
            OleDbCommand command = new OleDbCommand (sqlCommand, Connection);

            command.Transaction = Transaction;

            parameters = (parameters == null ? new List<Object>() : parameters);
            foreach (var parameter in parameters)
            {
                command.Parameters.Add(Guid.NewGuid().ToString(), GetOleDbType(parameter));
                command.Parameters.Add(parameter);
            }
            
            return command.ExecuteReader();
        }

        protected int ExecuteNonQuery(String sqlCommand, IEnumerable<Object> parameters = null)
        {
            OleDbCommand command = new OleDbCommand(sqlCommand, Connection);

            command.Transaction = Transaction;

            parameters = parameters == null ? new List<Object>() : parameters;
            foreach (var parameter in parameters)
            {
                command.Parameters.Add(Guid.NewGuid().ToString(), GetOleDbType(parameter));
                command.Parameters.Add(parameter.ToString());
            }

            return command.ExecuteNonQuery();
        }

        protected void ExecuteProcedure (String procedureName, IList<String> parameterNames, IList<OleDbType> parameterTypes, IList<ParameterDirection> parameterDirections, IList<Object> parameterValues)
        {
            OleDbCommand command = new OleDbCommand(procedureName, Connection);
            int numOfParameters = parameterNames.Count;

            command.Transaction = Transaction;
            command.CommandType = CommandType.StoredProcedure;

            for (int i = 0; i < numOfParameters; i++)
            {
                command.Parameters.Add (parameterNames[i], parameterTypes[i]);
                command.Parameters[parameterNames[i]].Direction = parameterDirections[i];
                command.Parameters[parameterNames[i]].Value = parameterValues[i];
            }

            try
            {
                command.ExecuteNonQuery();
            }
            catch (OleDbException error)
            {
                System.Windows.MessageBox.Show(error.Message, "Error", System.Windows.MessageBoxButton.OK, System.Windows.MessageBoxImage.Error);
            }
        }

        public void BeginTransaction (IsolationLevel isolationLevel)
        {
            Transaction = Connection.BeginTransaction(isolationLevel);
        }

        public void CommitTransaction()
        {
            Transaction.Commit();
            Transaction = null;
        }

        public void RollbackTransaction()
        {
            Transaction.Rollback();
            Transaction = null;
        }

        public bool TransactionActive()
        {
            return Transaction == null ? false : true;
        }

        public void Dispose()
        {
            Connection.Dispose();
        }
    }
}
