using System.ComponentModel.DataAnnotations;
using System.ComponentModel;
using ConfigurationManager = System.Configuration.ConfigurationManager;
using MySql.Data.MySqlClient;

namespace Zoologico.Models
{
    public class Consulta
    {
        [ReadOnly(true)]
        [DisplayName("Código")]
        public int IdProntuario { get; set; }

        [DisplayName("Alergia")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no máximo 50 caracteres")]
        [Required(ErrorMessage = "Informe sobre a alergia")]
        public string Alergia { get; set; }

        [DisplayName("Peso")]
        [Required(ErrorMessage = "Informe o peso")]
        public double Peso { get; set; }

        [DisplayName("Descrição")]
        [DataType(DataType.MultilineText)]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no máximo 50 caracteres")]
        [Required(ErrorMessage = "A descrição é obrigatória")]
        public string DescricaoHistorico { get; set; }

        [DisplayName("Data da Consulta")]
        public DateTime DataCadas { get; set; }


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

        public MySqlDataReader ExecuteReadSql(string strQuery)
        {
            cmd.CommandText = strQuery;
            cmd.Connection = conexao;
            MySqlDataReader Leitor = cmd.ExecuteReader();
            return Leitor;
        }

        public List<Consulta> SelectListConsulta(int Id)
        {
            conexao.Open();
            string strQuery = "call spSelectConsulta(" + Id + ");";
            MySqlDataReader leitor = ExecuteReadSql(strQuery);
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
            conexao.Close();
            return list;
        }

    }
}
