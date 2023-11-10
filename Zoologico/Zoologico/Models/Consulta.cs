using System.ComponentModel.DataAnnotations;
using System.ComponentModel;
using ConfigurationManager = System.Configuration.ConfigurationManager;
using MySql.Data.MySqlClient;

namespace Zoologico.Models
{
    public class Consulta
    {
        [ReadOnly(true)]
        [DisplayName("Código")]
        public int IdProntuario { get; set; }

        [DisplayName("Alergia")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "Informe sobre a alergia")]
        public string Alergia { get; set; }

        [DisplayName("Peso")]
        [Required(ErrorMessage = "Informe o peso")]
        public double Peso { get; set; }

        [DisplayName("Descrição")]
        [DataType(DataType.MultilineText)]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "A descrição é obrigatória")]
        public string DescricaoHistorico { get; set; }

        [DisplayName("Data da Consulta")]
        public DateTime DataCadas { get; set; }


    }
}
