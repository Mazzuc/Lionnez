using Microsoft.AspNetCore.Mvc;
using Microsoft.CodeAnalysis.FlowAnalysis.DataFlow;
using MySql.Data.MySqlClient;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using ConfigurationManager = System.Configuration.ConfigurationManager;

namespace Zoologico.Models
{
    public class Animal
    {
        [ReadOnly(true)]
        [DisplayName("Código")]
        public int IdAnimal { get; set; }

        [DisplayName("Nome")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "O nome é obrigatório")]
        public string NomeAnimal { get; set; }

        [DisplayName("Espécie")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "A espécie é obrigatório")]
        public string NomeEspecie { get; set; }

        [DisplayName("Habitat")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "Informe o habitat")]
        public string NomeHabitat { get; set; }

        [Required(ErrorMessage = "A data de nascimento é obrigatória")]
        [DataType(DataType.Date, ErrorMessage = "Data em formato inválido")]
        [DisplayName("Nascimento")]
        public DateTime DataNasc { get; set; }

        [DisplayName("Porte")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "Informe o porte")]
        public string NomePorte { get; set; }

        [DisplayName("Peso")]
        [Required(ErrorMessage = "Informe o peso")]
        public double Peso { get; set; }

        [DisplayName("Sexo")]
        [StringLength(1, MinimumLength = 1, ErrorMessage = "Informe F ou M")]
        [Required(ErrorMessage = "Informe o sexo")]
        public string Sexo { get; set; }

        [DisplayName("Descrição")]
        [DataType(DataType.MultilineText)]
        [StringLength(150, MinimumLength = 5, ErrorMessage = "O campo deve conter no máximo 150 caracteres")]
        [Required(ErrorMessage = "A descrição é obrigatória")]
        public string DescricaoAnimal { get; set; }


        [DisplayName("Dieta")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "Informe a dieta do animal")]
        public string NomeDieta { get; set; }

        [DisplayName("Prontuário")]
        [DataType(DataType.MultilineText)]
        [StringLength(150, MinimumLength = 5, ErrorMessage = "O campo deve conter no máximo 150 caracteres")]
        [Required(ErrorMessage = "A observação sobre o prontuário é obrigatória")]
        public string ObsProntuario { get; set; }


        private readonly MySqlConnection conexao = new MySqlConnection(ConfigurationManager.ConnectionStrings["conexao"].ConnectionString);
        private readonly MySqlCommand cmd = new MySqlCommand();
        public void InsertAnimal(Animal animal)
        {
            Double buffer = Convert.ToDouble(animal.Peso);

            conexao.Open();
            cmd.CommandText = ("call spInsertAnimal(@NomeAnimal, @NomeEspecie, @NomeHabitat, @DataNasc, @NomePorte, @Peso, @Sexo, @DescricaoAnimal, @NomeDieta, @ObsProntuario);");
            cmd.Parameters.Add("@NomeAnimal", MySqlDbType.VarChar).Value = animal.NomeAnimal;
            cmd.Parameters.Add("@NomeEspecie", MySqlDbType.VarChar).Value = animal.NomeEspecie;
            cmd.Parameters.Add("@NomeHabitat", MySqlDbType.VarChar).Value = animal.NomeHabitat;
            cmd.Parameters.Add("@DataNasc", MySqlDbType.Date).Value = animal.DataNasc;
            cmd.Parameters.Add("@NomePorte", MySqlDbType.VarChar).Value = animal.NomePorte;
            cmd.Parameters.Add("@Peso", MySqlDbType.Double).Value = buffer;
            cmd.Parameters.Add("@Sexo", MySqlDbType.VarChar).Value = animal.Sexo;
            cmd.Parameters.Add("@DescricaoAnimal", MySqlDbType.VarChar).Value = animal.DescricaoAnimal;
            cmd.Parameters.Add("@NomeDieta", MySqlDbType.VarChar).Value = animal.NomeDieta;
            cmd.Parameters.Add("@ObsProntuario", MySqlDbType.VarChar).Value = animal.ObsProntuario;


            cmd.Connection = conexao;
            cmd.ExecuteNonQuery();
            conexao.Close();
        }

        public void UpdateAnimal(Animal animal)
        {

            conexao.Open();
            cmd.CommandText = ("call spUpdateAnimal(@IdAnimal, @NomeHabitat, @DescricaoAnimal, @ObsProntuario);");
            cmd.Parameters.Add("@IdAnimal", MySqlDbType.Int64).Value = animal.IdAnimal;
            cmd.Parameters.Add("@NomeHabitat", MySqlDbType.VarChar).Value = animal.NomeHabitat;
            cmd.Parameters.Add("@DescricaoAnimal", MySqlDbType.VarChar).Value = animal.DescricaoAnimal;
            cmd.Parameters.Add("@ObsProntuario", MySqlDbType.VarChar).Value = animal.ObsProntuario;


            cmd.Connection = conexao;
            cmd.ExecuteNonQuery();
            conexao.Close();
        }

        public void DeleteAnimal(int Id )
        {
            conexao.Open();

            cmd.CommandText = ("call spDeleteAnimal("+Id+");");

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

        public List<Animal> SelectList()
        {
            conexao.Open();
            string strQuery = "call spSelectAnimal;";
            MySqlDataReader leitor = ExecuteReadSql(strQuery);
            return ReaderList(leitor);
        }

        private List<Animal> ReaderList(MySqlDataReader DR)
        {
            List<Animal> list = new List<Animal>();
            while (DR.Read())
            {
                var TempAnimal = new Animal()
                {
                    IdAnimal = int.Parse(DR["Id do Animal"].ToString()),
                    NomeAnimal = DR["Nome"].ToString(),
                    DataNasc = DateTime.Parse(DR["Nascimento"].ToString()),
                    NomeHabitat = DR["Habitat"].ToString()
                };
                list.Add(TempAnimal);
            }
            DR.Close();
            conexao.Close();
            return list;
        }

        public Animal SelectAnimal(int Id)
        {
            Animal habitat = new Animal();
            string strQuery = "call spSelectAnimalEspecifico(" + Id + ");";

            conexao.Open();
            MySqlDataReader DR = ExecuteReadSql(strQuery);

            DR.Read();
            habitat.IdAnimal = int.Parse(DR["Id do Animal"].ToString());
            habitat.NomeAnimal = DR["Nome"].ToString();
            habitat.DataNasc = DateTime.Parse(DR["Nascimento"].ToString());
            habitat.NomeHabitat = DR["Habitat"].ToString();
            habitat.NomeEspecie = DR["Espécie"].ToString();
            habitat.NomePorte = DR["Porte"].ToString();
            habitat.NomeDieta = DR["Dieta"].ToString();
            habitat.Peso = Double.Parse(DR["Peso"].ToString());
            habitat.Sexo = DR["Sexo"].ToString();
            habitat.DescricaoAnimal = DR["Descrição"].ToString();
            habitat.ObsProntuario = DR["Prontuário"].ToString();

            DR.Close();
            conexao.Close();
            return habitat;
        }

        public List<Animal> SelectListHabitat(int Id)
        {
            conexao.Open();
            string strQuery = "call spSelectHabitatAnimais(" + Id + ");";
            MySqlDataReader leitor = ExecuteReadSql(strQuery);
            return ReaderListHabitat(leitor);
        }

        private List<Animal> ReaderListHabitat(MySqlDataReader DR)
        {
            List<Animal> list = new List<Animal>();
            while (DR.Read())
            {
                var TempAnimal = new Animal()
                {
                    IdAnimal = int.Parse(DR["Id do Animal"].ToString()),
                    NomeAnimal = DR["Nome"].ToString(),
                    DataNasc = DateTime.Parse(DR["Nascimento"].ToString()),
                    NomeEspecie = DR["Espécie"].ToString(),
                    NomePorte = DR["Porte"].ToString(),
                    NomeDieta = DR["Dieta"].ToString(),
                    Peso = Double.Parse(DR["Peso"].ToString()),
                    Sexo = DR["Sexo"].ToString(),
                    DescricaoAnimal = DR["Descrição"].ToString(),
                };
                list.Add(TempAnimal);
            }
            DR.Close();
            conexao.Close();
            return list;
        }


    }
}
