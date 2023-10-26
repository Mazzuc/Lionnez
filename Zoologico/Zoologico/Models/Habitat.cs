using MySql.Data.MySqlClient;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System;
using System.Configuration;
using System.Data;
using ConfigurationManager = System.Configuration.ConfigurationManager;
using Org.BouncyCastle.Asn1.Mozilla;

namespace Zoologico.Models
{
    public class Habitat
    {
        [ReadOnly(true)]
        [DisplayName("Código")]
        public int IdHabitat { get; set; }

        [DisplayName("Nome")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no máximo 50 caracteres")]
        [Required(ErrorMessage = "O nome é obrigatório")]
        public string NomeHabitat { get; set; }

        [DisplayName("Tipo")]
        [Required(ErrorMessage = "O tipo é obrigatório")]
        public string TipoHabitat { get; set; }

        [DisplayName("Capacidade")]
        [Range(1, 100, ErrorMessage = "A capacidade deve ser maior que zero")]
        [Required(ErrorMessage = "Informe a capacidade")]
        public int Capacidade { get; set; }

        [DisplayName("Vegetação")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no máximo 50 caracteres")]
        [Required(ErrorMessage = "Informe a vegetação")]
        public string Vegetacao { get; set; }

        [DisplayName("Clima")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no máximo 50 caracteres")]
        [Required(ErrorMessage = "Informe o clima")]
        public string Clima { get; set; }

        [DisplayName("Solo")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no máximo 50 caracteres")]
        [Required(ErrorMessage = "Informe o solo")]
        public string Solo { get; set; }

        [ReadOnly(true)]
        [DisplayName("Número de Animais")]
        public int QtdAnimais { get; set; }

        private readonly MySqlConnection conexao = new MySqlConnection(ConfigurationManager.ConnectionStrings["conexao"].ConnectionString);
        private readonly MySqlCommand cmd = new MySqlCommand();
        public void InsertHabitat(Habitat habitat)
        {
            conexao.Open();
            cmd.CommandText = ("call spInsertHabitat(@NomeHabitat, @TipoHabitat, @Capacidade, @Vegetacao, @Clima, @Solo);");
            cmd.Parameters.Add("@NomeHabitat", MySqlDbType.VarChar).Value = habitat.NomeHabitat;
            cmd.Parameters.Add("@TipoHabitat", MySqlDbType.VarChar).Value = habitat.TipoHabitat;
            cmd.Parameters.Add("@Capacidade", MySqlDbType.Int64).Value = habitat.Capacidade;
            cmd.Parameters.Add("@Vegetacao", MySqlDbType.VarChar).Value = habitat.Vegetacao;
            cmd.Parameters.Add("@Clima", MySqlDbType.VarChar).Value = habitat.Clima;
            cmd.Parameters.Add("@Solo", MySqlDbType.VarChar).Value = habitat.Solo;

            cmd.Connection = conexao;
            cmd.ExecuteNonQuery();
            conexao.Close();
        }

        public void UpdateHabitat(Habitat habitat)
        {
            conexao.Open();
            cmd.CommandText = ("call spUpdateHabitat(@IdHabitat, @NomeHabitat, @Capacidade, @Vegetacao, @Clima, @Solo);");
            cmd.Parameters.Add("@IdHabitat", MySqlDbType.Int64).Value = habitat.IdHabitat;
            cmd.Parameters.Add("@NomeHabitat", MySqlDbType.VarChar).Value = habitat.NomeHabitat;
            cmd.Parameters.Add("@Capacidade", MySqlDbType.Int64).Value = habitat.Capacidade;
            cmd.Parameters.Add("@Vegetacao", MySqlDbType.VarChar).Value = habitat.Vegetacao;
            cmd.Parameters.Add("@Clima", MySqlDbType.VarChar).Value = habitat.Clima;
            cmd.Parameters.Add("@Solo", MySqlDbType.VarChar).Value = habitat.Solo;

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

        public void DeleteHabitat(int Id)
        {
            conexao.Open();

            cmd.CommandText = ("call spDeleteHabitat(" + Id + ");");

            cmd.Connection = conexao;
            cmd.ExecuteNonQuery();
            conexao.Close();
        }

        public Habitat SelectHabitat(int Id)
        {
            Habitat habitat = new Habitat();
            string strQuery = "call spSelectHabitatUnic(" + Id + ");";

            conexao.Open();
            MySqlDataReader DR = ExecuteReadSql(strQuery);

            DR.Read();
            habitat.IdHabitat = int.Parse(DR["Id do Habitat"].ToString());
            habitat.NomeHabitat = DR["Nome"].ToString();
            habitat.TipoHabitat = DR["Tipo"].ToString();
            habitat.Vegetacao = DR["Vegetação"].ToString();
            habitat.Clima = DR["Clima"].ToString();
            habitat.Solo = DR["Solo"].ToString();
            habitat.Capacidade = int.Parse(DR["Capacidade"].ToString());
            habitat.QtdAnimais = int.Parse(DR["Animais"].ToString());

            DR.Close();
            conexao.Close();
            return habitat;
        }
        public List<Habitat> SelectList()
        {
            conexao.Open();
            string strQuery = "call spSelectHabitat;";
            MySqlDataReader leitor = ExecuteReadSql(strQuery);
            return ReaderList(leitor);
        }

        private List<Habitat> ReaderList(MySqlDataReader DR)
        {
            List<Habitat> list = new List <Habitat>();
            while(DR.Read())
            {
                var TempHabitat = new Habitat()
                {
                    IdHabitat = int.Parse(DR["Id do Habitat"].ToString()),
                    NomeHabitat = DR["Nome"].ToString(),
                    TipoHabitat = DR["Tipo"].ToString(),
                    Capacidade = int.Parse(DR["Capacidade"].ToString()),
                    Vegetacao = DR["Vegetação"].ToString(),
                    Clima = DR["Clima"].ToString(),
                    Solo = DR["Solo"].ToString(),
                    QtdAnimais = int.Parse(DR["Animais"].ToString()),
                };
                list.Add(TempHabitat);
            }
            DR.Close();
            conexao.Close();
            return list;
        }


    }
}
