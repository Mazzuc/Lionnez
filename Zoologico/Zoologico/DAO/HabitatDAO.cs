using MySql.Data.MySqlClient;
using Zoologico.Models;
using ConfigurationManager = System.Configuration.ConfigurationManager;

namespace Zoologico.DAO
{
    public class HabitatDAO
    {
        MySQLConfig db = new MySQLConfig();

        public void InsertHabitat(string NomeHabitat, string TipoHabitat, int Capacidade, string Vegetacao, string Clima, string Solo)
        {
            db.Open();
            string strQuery = "call spInsertHabitat('"+ NomeHabitat + "', '"+ TipoHabitat + "', "+ Capacidade + ", '"+ Vegetacao + "', '"+ Clima + "', '"+ Solo + "');";

            db.ExecuteNowdSql(strQuery);
            db.Close();
        }


        public void UpdateHabitat(int IdHabitat, string NomeHabitat, int Capacidade, string Vegetacao, string Clima, string Solo)
        {
            db.Open();
            string strQuery = "call spUpdateHabitat('" + IdHabitat + "', '" + NomeHabitat + "', " + Capacidade + ", '" + Vegetacao + "', '" + Clima + "', '" + Solo + "');";

            db.ExecuteNowdSql(strQuery);
            db.Close();
        }

        public void DeleteHabitat(int Id)
        {
            db.Open();
            string strQuery = "call spDeleteHabitat(" + Id + ");";
            db.ExecuteNowdSql(strQuery);
            db.Close();
        }

        public Habitat SelectHabitat(int Id)
        {
            Habitat habitat = new Habitat();
            string strQuery = "call spSelectHabitatUnic(" + Id + ");";

            db.Open();
            MySqlDataReader DR = db.ExecuteReadSql(strQuery);

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
            db.Close();
            return habitat;
        }
        public List<Habitat> SelectList()
        {
            db.Open();
            string strQuery = "call spSelectHabitat;";
            MySqlDataReader leitor = db.ExecuteReadSql(strQuery);
            return ReaderList(leitor);
        }

        public List<Habitat> Search(string NomeHabitat)
        {
            db.Open();
            string strQuery = "call spSearchHabitat('"+ NomeHabitat +"');";
            MySqlDataReader leitor = db.ExecuteReadSql(strQuery);
            return ReaderList(leitor);
        }
        private List<Habitat> ReaderList(MySqlDataReader DR)
        {
            List<Habitat> list = new List<Habitat>();
            while (DR.Read())
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
            db.Close();
            return list;
        }
    }
}
