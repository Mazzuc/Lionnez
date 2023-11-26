using MySql.Data.MySqlClient;
using System.ComponentModel.DataAnnotations;
using ConfigurationManager = System.Configuration.ConfigurationManager;
using System.Drawing;
using Zoologico.Areas.Gerenciamento.Models;

namespace Zoologico.Models
{
    public class Cadastro
    {
        public int IdUsuario { get; set; }

        [Required]

        public string Nome { get; set; }

        [Required]
        public string Email { get; set; }

        [Required]
        public string CPF { get; set; }

        [Required]
        public string Usuario { get; set; }

        [Required]
        public string Senha { get; set; }
        public int Acesso { get; set; }

        private readonly MySqlConnection conexao = new MySqlConnection(ConfigurationManager.ConnectionStrings["conexao"].ConnectionString);
        private readonly MySqlCommand cmd = new MySqlCommand();

        public void InsertCadastro(Cadastro cadastro)
        {
            conexao.Open();
            cmd.CommandText = "call spInsertUsuario(@Nome, @Email, @CPF, @Usuario, @Senha);";
            cmd.Parameters.Add("@Nome", MySqlDbType.VarChar).Value = cadastro.Nome;
            cmd.Parameters.Add("@Email", MySqlDbType.VarChar).Value = cadastro.Email;
            cmd.Parameters.Add("@CPF", MySqlDbType.VarChar).Value = cadastro.CPF;
            cmd.Parameters.Add("@Usuario", MySqlDbType.VarChar).Value = cadastro.Usuario;
            cmd.Parameters.Add("@Senha", MySqlDbType.VarChar).Value = cadastro.Senha;
            cmd.Connection = conexao;
            cmd.ExecuteNonQuery();
            conexao.Close();
        }

        public Cadastro SelectUsuario(string vUsuario)
        {
            conexao.Open();
            cmd.CommandText = "call spSelectUsuario(@Usuario);";
            cmd.Parameters.Add("@Usuario", MySqlDbType.VarChar).Value = vUsuario;
            cmd.Connection = conexao;
            var readUsuario = cmd.ExecuteReader();
            var TempUsuario = new Cadastro();

            if (readUsuario.Read())
            {
                TempUsuario.IdUsuario = int.Parse(readUsuario["IdCadastro"].ToString());
                TempUsuario.Nome = readUsuario["Nome"].ToString();
                TempUsuario.Usuario = readUsuario["Usuario"].ToString();
                TempUsuario.Senha = readUsuario["Senha"].ToString();
            }
            conexao.Close();
            return TempUsuario;
        }
    }
}
