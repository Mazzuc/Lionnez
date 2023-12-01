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
        public string Login { get; set; }

        [DisplayName("Senha")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "Informe a senha")]
        [Remote("ValidaLogin", "Home", ErrorMessage = "Senha ou Login incorreto")]
        public string Senha { get; set; }


        private readonly MySqlConnection conexao = new MySqlConnection(ConfigurationManager.ConnectionStrings["conexao"].ConnectionString);
        private readonly MySqlCommand cmd = new MySqlCommand();

        public string ValidaLogin(string vLogin, string vSenha)
        {
            conexao.Open();
            cmd.CommandText = "call spLogin(@Login, @Senha);";
            cmd.Parameters.Add("@Login", MySqlDbType.VarChar).Value = vLogin;
            cmd.Parameters.Add("@Senha", MySqlDbType.VarChar).Value = vSenha;
            cmd.Connection = conexao;
            string Login = (string)cmd.ExecuteScalar();
            conexao.Close();
            if (Login == null)
                Login = " ";
            return Login;
        }
    }
}
