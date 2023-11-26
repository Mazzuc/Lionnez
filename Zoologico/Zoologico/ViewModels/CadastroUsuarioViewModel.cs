using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Drawing;

namespace Zoologico.ViewModels
{
    public class CadastroUsuarioViewModel
    {
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "O nome é obrigatório")]
        public string Nome { get; set; }

        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "O email é obrigatório")]
        [DataType(DataType.EmailAddress)]
        public string Email { get; set; }

        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "O CPF é obrigatório")]
        //[Remote("ValidaCPF", "Animal", ErrorMessage = "CPF já cadastrado")]
        public string CPF { get; set; }

        [DisplayName("Usuário")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "O login é obrigatório")]
        //[Remote("ValidaUsuario", "Animal", ErrorMessage = "Usuário já cadastrado")]
        public string Usuario { get; set; }

        [StringLength(8, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "A senha é obrigatória")]
        [DataType(DataType.Password)]
        public string Senha { get; set; }

        [Required(ErrorMessage = "Confirme a senha")]
        [DisplayName("Confirme a senha")]
        [DataType(DataType.Password)]
        [Compare(nameof(Senha), ErrorMessage ="As senhas são diferentes")]
        public string ConfirmaSenha { get; set; }
    }
}
