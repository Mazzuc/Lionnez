using MySql.Data.MySqlClient;
using System;
using System.Configuration;
using System.Data;
using ConfigurationManager = System.Configuration.ConfigurationManager;

namespace Zoologico
{
    public class MySQLConfiguration
    {
        private readonly MySqlConnection conexao = new MySqlConnection(ConfigurationManager.ConnectionStrings["MySqlConnection"].ConnectionString);
        private readonly MySqlCommand cmd = new MySqlCommand();

        public MySQLConfiguration(string v)
        {
        }

        public MySQLConfiguration()
        {
        }

        public void Open()
        {
            if (conexao.State == ConnectionState.Closed)
                conexao.Open();
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

        public string ExecuteScalarSql(string strQuery)
        {
            cmd.CommandText = strQuery;
            cmd.Connection = conexao;
            string strRetorno = Convert.ToString(cmd.ExecuteScalar());
            return strRetorno;
        }

        public void Close()
        {
            if (conexao.State == ConnectionState.Open)
                conexao.Close();
        }
    }
}
