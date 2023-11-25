using Microsoft.EntityFrameworkCore.Metadata.Internal;
using MySql.Data.MySqlClient;
using Zoologico.Models;
using ConfigurationManager = System.Configuration.ConfigurationManager;

namespace Zoologico.DAO
{
    public class AnimalDAO
    {
        MySQLConfig db = new MySQLConfig();

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

        public void UpdateAnimal(int IdAnimal, string NomeHabitat, string DescricaoAnimal, string ObsProntuario)
        {
            db.Open();
            string strQuery = "call spUpdateAnimal('" + IdAnimal + "', '" + NomeHabitat + "', '" + DescricaoAnimal + "', '" + ObsProntuario + "');";

            db.ExecuteNowdSql(strQuery);
            db.Close();
        }

        public void DeleteAnimal(int Id)
        {
            db.Open();
            string strQuery = "call spDeleteAnimal(" + Id + ");";
            db.ExecuteNowdSql(strQuery);
            db.Close();
        }


        public List<Animal> SelectList()
        {
            db.Open();
            string strQuery = "call spSelectAnimal;";
            MySqlDataReader leitor = db.ExecuteReadSql(strQuery);
            return ReaderList(leitor);
        }

        public List<Animal> Search(string NomeAnimal)
        {
            db.Open();
            string strQuery = "call spSearchAnimal('" +NomeAnimal+ "');";
            MySqlDataReader leitor = db.ExecuteReadSql(strQuery);
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
                    Peso = Double.Parse(DR["Peso"].ToString()),
                    DataNasc = DateTime.Parse(DR["Nascimento"].ToString()),
                    NomeHabitat = DR["Habitat"].ToString()
                };
                list.Add(TempAnimal);
            }
            DR.Close();
            db.Close();
            return list;
        }

        public Animal SelectAnimal(int Id)
        {
            Animal habitat = new Animal();
            string strQuery = "call spSelectAnimalEspecifico(" + Id + ");";

            db.Open();
            MySqlDataReader DR = db.ExecuteReadSql(strQuery);

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
            db.Close();
            return habitat;
        }

        public List<Animal> SelectListHabitat(int Id)
        {
            db.Open();
            string strQuery = "call spSelectHabitatAnimais(" + Id + ");";
            MySqlDataReader leitor = db.ExecuteReadSql(strQuery);
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
            db.Close();
            return list;
        }

        public string ValidaAnimal(string vNomeAnimal)
        {
            conexao.Open();
            cmd.CommandText = "call spValidaAnimal(@NomeAnimal);";
            cmd.Parameters.Add("@NomeAnimal", MySqlDbType.VarChar).Value = vNomeAnimal;
            cmd.Connection = conexao;
            string NomeAnimal = (string)cmd.ExecuteScalar();
            conexao.Close();
            if (NomeAnimal == null)
                NomeAnimal = "";
            return NomeAnimal;
        }

        public string ValidaHabitat(string vNomeHabitat)
        {
            conexao.Open();
            cmd.CommandText = "call spValidaHabitatQts(@NomeHabitat);";
            cmd.Parameters.Add("@NomeHabitat", MySqlDbType.VarChar).Value = vNomeHabitat;
            cmd.Connection = conexao;
            string qts = (string)cmd.ExecuteScalar();
            conexao.Close();
            if (qts == null)
                qts = "";
            return qts;
        }
    }
}
