using MySql.Data.MySqlClient;
using Zoologico.Areas.Gerenciamento.Models;
using ConfigurationManager = System.Configuration.ConfigurationManager;


namespace Zoologico.Areas.Gerenciamento.DAO
{
    public class FuncionarioDAO
    {
        MySQLConfig db = new MySQLConfig();

        private readonly MySqlConnection conexao = new MySqlConnection(ConfigurationManager.ConnectionStrings["conexao"].ConnectionString);
        private readonly MySqlCommand cmd = new MySqlCommand();

        public void InsertFuncionario(Funcionario funcionario)
        {
      

            conexao.Open();
            cmd.CommandText = ("call spInsertFuncionario(@Nome, @Email, @CPF, @Cargo, @Senha, @Usuario, @RG, @DataNasc, @DataAdm);");
            cmd.Parameters.Add("@Nome", MySqlDbType.VarChar).Value = funcionario.Nome;
            cmd.Parameters.Add("@Email", MySqlDbType.VarChar).Value = funcionario.Email;
            cmd.Parameters.Add("@CPF", MySqlDbType.VarChar).Value = funcionario.CPF;
            cmd.Parameters.Add("@Cargo", MySqlDbType.VarChar).Value = funcionario.Cargo;
            cmd.Parameters.Add("@Senha", MySqlDbType.VarChar).Value = funcionario.Senha;
            cmd.Parameters.Add("@Usuario", MySqlDbType.VarChar).Value = funcionario.Usuario;
            cmd.Parameters.Add("@RG", MySqlDbType.VarChar).Value = funcionario.RG;
            cmd.Parameters.Add("@DataNasc", MySqlDbType.Date).Value = funcionario.DataNasc;
            cmd.Parameters.Add("@DataAdm", MySqlDbType.Date).Value = funcionario.DataAdm;

            cmd.Connection = conexao;
            cmd.ExecuteNonQuery();


            conexao.Close();
        }

    }
}
