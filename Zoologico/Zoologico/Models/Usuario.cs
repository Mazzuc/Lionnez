using MySql.Data.MySqlClient;
using System.ComponentModel.DataAnnotations;
using ConfigurationManager = System.Configuration.ConfigurationManager;
using System.Drawing;
using Zoologico.Areas.Gerenciamento.Models;
using System.ComponentModel;
using Microsoft.AspNetCore.Mvc;

namespace Zoologico.Models
{
    public class Usuario
    {

        [DisplayName("Login")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "Informe o usuário")]
        [Remote("ValidaLogin", "Home", ErrorMessage = "Login incorreto")]
        public string Login { get; set; }

        [DisplayName("Senha")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "Informe a senha")]
        [Remote("ValidaSenha", "Home", ErrorMessage = "Senha incorreta")]
        public string Senha { get; set; }


        private readonly MySqlConnection conexao = new MySqlConnection(ConfigurationManager.ConnectionStrings["conexao"].ConnectionString);
        private readonly MySqlCommand cmd = new MySqlCommand();

        public string ValidaLogin(string vLogin)
        {
            conexao.Open();
            cmd.CommandText = "call spLogin(@Login);";
            cmd.Parameters.Add("@Login", MySqlDbType.VarChar).Value = vLogin;
            cmd.Connection = conexao;
            string login = (string)cmd.ExecuteScalar();
            conexao.Close();
            if (login == null)
                login = "";
            return login;
        }

        public string ValidaSenha(string vSenha)
        {
            conexao.Open();
            cmd.CommandText = "call spSenha(@Senha);";
            cmd.Parameters.Add("@Senha", MySqlDbType.VarChar).Value = vSenha;
            cmd.Connection = conexao;
            string senha = (string)cmd.ExecuteScalar();
            conexao.Close();
            if (senha == null)
                senha = "";
            return senha;
        }
    }

}
