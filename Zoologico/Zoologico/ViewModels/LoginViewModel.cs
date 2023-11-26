using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace Zoologico.ViewModels
{
    public class LoginViewModel
    {
        public string UrlRetorno { get; set; }

        [DisplayName("Usuário")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "O login é obrigatório")]
        public string Usuario { get; set; }

        [StringLength(8, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "A senha é obrigatória")]
        [DataType(DataType.Password)]
        public string Senha { get; set; }
    }
}
