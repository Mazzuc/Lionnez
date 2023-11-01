using MySql.Data.MySqlClient;
using Zoologico.Models;
using ConfigurationManager = System.Configuration.ConfigurationManager;

namespace Zoologico
{
    public class MySQLConfig
    {
        private readonly MySqlConnection conexao = new MySqlConnection(ConfigurationManager.ConnectionStrings["conexao"].ConnectionString);
        private readonly MySqlCommand cmd = new MySqlCommand();

        public void Open()
        {
            if (conexao.State == System.Data.ConnectionState.Closed)
                conexao.Open();
        }
        public void Close()
        {
            if (conexao.State == System.Data.ConnectionState.Open)
                conexao.Close();
        }

        public MySqlDataReader ExecuteReadSql(string strQuery)
        {
            cmd.CommandText = strQuery;
            cmd.Connection = conexao;
            MySqlDataReader Leitor = cmd.ExecuteReader();
            return Leitor;
        }
        public void ExecuteNowdSql(string strQuery)
        {
            cmd.CommandText = strQuery;
            cmd.Connection = conexao;
            cmd.ExecuteNonQuery();
        }

    }
}
