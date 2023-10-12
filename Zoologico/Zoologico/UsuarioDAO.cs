namespace Zoologico
{
    public class UsuarioDAO
    {
        MySQLConfiguration db = new MySQLConfiguration();


        public void Insert(string strNomeUsu, string strHabitat, string strDataNasc, string strEspecie, string strPorte, string strPeso, string strSexo, string strDescricao, string strDieta, string strProntuario)
        {
            string strInsert = string.Format("call spInsertAnimal('{0}', '{1}', '{2}', '{3}', '{4}', '{5}', '{6}', '{7}', '{8}', '{9}');", strNomeUsu, strEspecie, strHabitat, strDataNasc, strPorte, strPeso, strSexo, strDescricao, strDieta, strProntuario);

            db.Open();
            db.ExecuteNowdSql(strInsert);
            db.Close();
        }

        public void Update(string strNomeUsu, string strHabitat, string strPeso, string strDescricao, string strProntuario)
        {

            db.Open();
            string strUpdate = string.Format("call spUpdateAnimal('{0}', '{1}', '{2}', '{3}', '{4}')", strNomeUsu, strHabitat, strPeso, strDescricao, strProntuario);

            db.ExecuteNowdSql(strUpdate);
            db.Close();
        }

        public void Delete(string strNomeAnimal)
        {
            string strDelete = string.Format("call spDeleteAnimal('{0}');", strNomeAnimal);

            db.Open();
            db.ExecuteNowdSql(strDelete);
            db.Close();
        }

        public string SelectDado(string strIdUsu)
        {
            string strDado = ("select NomeUsu from tbUsuario where IdUsu = " + strIdUsu + ";");

            db.Open();
            strDado = db.ExecuteScalarSql(strDado);
            db.Close();

            return strDado;
        }

        internal void Insert()
        {
            throw new NotImplementedException();
        }
    }
}
