using MySql.Data.MySqlClient;
using Zoologico.Models;
using ConfigurationManager = System.Configuration.ConfigurationManager;

namespace Zoologico.DAO
{
    public class ConsultaDAO
    {
         MySQLConfig db = new MySQLConfig();

        private readonly MySqlConnection conexao = new MySqlConnection(ConfigurationManager.ConnectionStrings["conexao"].ConnectionString);
        private readonly MySqlCommand cmd = new MySqlCommand();


        public void InsertConsulta(Consulta consulta, int Id)
        {
            Double buffer = Convert.ToDouble(consulta.Peso);

            conexao.Open();
            cmd.CommandText = ("call spInsertHistorico(@IdProntuario, @Alergia, @Descricao, @Peso);");
            cmd.Parameters.Add("@IdProntuario", MySqlDbType.Int64).Value = Id;
            cmd.Parameters.Add("@Alergia", MySqlDbType.VarChar).Value = consulta.Alergia;
            cmd.Parameters.Add("@Descricao", MySqlDbType.VarChar).Value = consulta.DescricaoHistorico;
            cmd.Parameters.Add("@Peso", MySqlDbType.Double).Value = buffer;

            cmd.Connection = conexao;
            cmd.ExecuteNonQuery();
            conexao.Close();
        }

        public List<Consulta> SelectListConsulta(int Id)
        {
            db.Open();
            string strQuery = "call spSelectConsulta(" + Id + ");";
            MySqlDataReader leitor = db.ExecuteReadSql(strQuery);
            return ReaderListConsulta(leitor);
        }

        private List<Consulta> ReaderListConsulta(MySqlDataReader DR)
        {
            List<Consulta> list = new List<Consulta>();
            while (DR.Read())
            {
                var TempConsultas = new Consulta()
                {
                    IdProntuario = int.Parse(DR["IdProntuario"].ToString()),
                    Peso = Double.Parse(DR["Peso"].ToString()),
                    Alergia = DR["Alergia"].ToString(),
                    DescricaoHistorico = DR["Descrição"].ToString(),
                    DataCadas = DateTime.Parse(DR["Data"].ToString()),
                };
                list.Add(TempConsultas);
            }

            DR.Close();
            db.Close();
            return list;
        }
    }
}
