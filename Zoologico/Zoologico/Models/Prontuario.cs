using System.ComponentModel.DataAnnotations;
using System.ComponentModel;
using MySql.Data.MySqlClient;
using ConfigurationManager = System.Configuration.ConfigurationManager;

namespace Zoologico.Models
{
    public class Prontuario
    {
        [ReadOnly(true)]
        [DisplayName("Código")]
        public int IdProntuario { get; set; }

        [DisplayName("Nome")]
        public string NomeAnimal { get; set; }

        [DisplayName("Espécie")]
        public string NomeEspecie { get; set; }

        [DisplayName("Nascimento")]
        public DateTime DataNasc { get; set; }

        [DisplayName("Peso")]
        [Required(ErrorMessage = "Informe o peso")]
        public double Peso { get; set; }

        [DisplayName("Sexo")]
        [Required(ErrorMessage = "Informe o sexo")]
        public string Sexo { get; set; }

        [DisplayName("Prontuário")]
        [DataType(DataType.MultilineText)]
        public string ObsProntuario { get; set; }

        private readonly MySqlConnection conexao = new MySqlConnection(ConfigurationManager.ConnectionStrings["conexao"].ConnectionString);
        private readonly MySqlCommand cmd = new MySqlCommand();

        public MySqlDataReader ExecuteReadSql(string strQuery)
        {
            cmd.CommandText = strQuery;
            cmd.Connection = conexao;
            MySqlDataReader Leitor = cmd.ExecuteReader();
            return Leitor;
        }

        public List<Prontuario> SelectList()
        {
            conexao.Open();
            string strQuery = "call spSelectProntuarios;";
            MySqlDataReader leitor = ExecuteReadSql(strQuery);
            return ReaderList(leitor);
        }

        private List<Prontuario> ReaderList(MySqlDataReader DR)
        {
            List<Prontuario> list = new List<Prontuario>();
            while (DR.Read())
            {
                var TempProntuario = new Prontuario()
                {
                    IdProntuario = int.Parse(DR["Id do Prontuário"].ToString()),
                    NomeAnimal = DR["Nome"].ToString(),
                    NomeEspecie = DR["Espécie"].ToString(),
                    DataNasc = DateTime.Parse(DR["Nascimento"].ToString()),
                    Peso = Double.Parse(DR["Peso"].ToString()),
                    Sexo = DR["Sexo"].ToString(),
                    ObsProntuario = DR["Observação"].ToString()
            };
                list.Add(TempProntuario);
            }
            DR.Close();
            conexao.Close();
            return list;
        }

        public Prontuario SelectProntuario(int Id)
        {
            Prontuario prontuario = new Prontuario();
            string strQuery = "call spSelectProntuarioEspecifico(" + Id + ");";

            conexao.Open();
            MySqlDataReader DR = ExecuteReadSql(strQuery);

            DR.Read();
            prontuario.IdProntuario = int.Parse(DR["Id do Prontuário"].ToString());
            prontuario.NomeAnimal = DR["Nome"].ToString();
            prontuario.DataNasc = DateTime.Parse(DR["Nascimento"].ToString());
            prontuario.Peso = Double.Parse(DR["Peso"].ToString());
            prontuario.NomeEspecie = DR["Espécie"].ToString();
            prontuario.Sexo = DR["Sexo"].ToString();
            prontuario.ObsProntuario = DR["Observação"].ToString();

            DR.Close();
            conexao.Close();
            return prontuario;
        }

    }
}
