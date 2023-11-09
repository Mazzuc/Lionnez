using MySql.Data.MySqlClient;
using System.Security.Cryptography;
using Zoologico.Models;
using ConfigurationManager = System.Configuration.ConfigurationManager;

namespace Zoologico
{
    public class MySQLConfig
    {
        MySqlConnection conexao = new MySqlConnection(ConfigurationManager.ConnectionStrings["conexao"].ConnectionString);
        MySqlCommand cmd = new MySqlCommand();

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
