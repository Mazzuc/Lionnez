using MySql.Data.MySqlClient;
using Zoologico.Areas.Gerenciamento.Models;

namespace Zoologico.DAO
{
    public class ProntuarioDAO
    {
        MySQLConfig db = new MySQLConfig();

        public List<Prontuario> SelectList()
        {
            db.Open();
            string strQuery = "call spSelectProntuarios;";
            MySqlDataReader leitor = db.ExecuteReadSql(strQuery);
            return ReaderList(leitor);
        }

        public List<Prontuario> Search(string NomeAnimal)
        {
            db.Open();
            string strQuery = "call spSearchProntuario('"+NomeAnimal+"');";
            MySqlDataReader leitor = db.ExecuteReadSql(strQuery);
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
            db.Close();
            return list;
        }

        public Prontuario SelectProntuario(int Id)
        {
            Prontuario prontuario = new Prontuario();
            string strQuery = "call spSelectProntuarioEspecifico(" + Id + ");";

            db.Open();
            MySqlDataReader DR = db.ExecuteReadSql(strQuery);

            DR.Read();
            prontuario.IdProntuario = int.Parse(DR["Id do Prontuário"].ToString());
            prontuario.NomeAnimal = DR["Nome"].ToString();
            prontuario.DataNasc = DateTime.Parse(DR["Nascimento"].ToString());
            prontuario.Peso = Double.Parse(DR["Peso"].ToString());
            prontuario.NomeEspecie = DR["Espécie"].ToString();
            prontuario.Sexo = DR["Sexo"].ToString();
            prontuario.ObsProntuario = DR["Observação"].ToString();

            DR.Close();
            db.Close();
            return prontuario;
        }
    }
}
