using System.ComponentModel.DataAnnotations;
using System.ComponentModel;
using Microsoft.AspNetCore.Mvc;

namespace Zoologico.Areas.Gerenciamento.Models
{
    public class Funcionario
    {
        [ReadOnly(true)]
        [DisplayName("Código")]
        public int IdFuncionario { get; set; }

        [DisplayName("Nome")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "O nome é obrigatório")]
        public string Nome { get; set; }

        [DisplayName("Email")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "O email é obrigatório")]
        [DataType(DataType.EmailAddress)]
        public string Email { get; set; }

        [DisplayName("CPF")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "O CPF é obrigatório")]
        public string CPF { get; set; }

        [DisplayName("RG")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "O RG é obrigatório")]
        public string RG { get; set; }

        [DisplayName("Cargo")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "O cargo é obrigatório")]
        public string Cargo { get; set; }

        [Required(ErrorMessage = "A data de nascimento é obrigatória")]
        [DataType(DataType.Date, ErrorMessage = "Data em formato inválido")]
        [Remote("selectDate", "Animal", ErrorMessage = "A data deve ser igual ou inferior a data de hoje.")]
        [DisplayName("Nascimento")]
        public DateTime DataNasc { get; set; }

        [Required(ErrorMessage = "A data de admissão é obrigatória")]
        [DataType(DataType.Date, ErrorMessage = "Data em formato inválido")]
        [Remote("selectDate", "Animal", ErrorMessage = "A data deve ser igual ou inferior a data de hoje.")]
        [DisplayName("Admissão")]
        public DateTime DataAdm { get; set; }

        [DisplayName("Usuário")]
        [StringLength(20, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "o usuário é obrigatório")]
        public string Usuario { get; set; }

        [DisplayName("Senha")]
        [StringLength(8, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "A senha é obrigatório")]
        [DataType(DataType.Password)]
        public string Senha { get; set; }

    }
}
