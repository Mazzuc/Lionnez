using Microsoft.EntityFrameworkCore.Metadata.Internal;
using MySql.Data.MySqlClient;
using Zoologico.Models;

namespace Zoologico.DAO
{
    public class AnimalDAO
    {
        MySQLConfig db = new MySQLConfig();

        public void InsertAnimal(Animal animal)
        {
            Double buffer = Convert.ToDouble(animal.Peso);

            db.Open();
            string strQuery = "call spInsertAnimal('" + animal.NomeAnimal + "', '" + animal.NomeEspecie + "', '" + animal.NomeHabitat + "', '" + animal.DataNasc +"', '"+ animal.NomePorte +"', "+ buffer +", '"+ animal.Sexo +"', '"+ animal.DescricaoAnimal+"', '"+ animal.NomeDieta +"', '"+ animal.ObsProntuario +"');";

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
    }
}
