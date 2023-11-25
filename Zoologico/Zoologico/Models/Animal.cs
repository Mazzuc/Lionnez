using Microsoft.AspNetCore.Mvc;
using Microsoft.CodeAnalysis.FlowAnalysis.DataFlow;
using MySql.Data.MySqlClient;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using ConfigurationManager = System.Configuration.ConfigurationManager;

namespace Zoologico.Models
{
    public class Animal
    {
        [ReadOnly(true)]
        [DisplayName("Código")]
        public int IdAnimal { get; set; }

        [DisplayName("Nome")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "O nome é obrigatório")]
        [Remote("ValidaAnimal", "Animal", ErrorMessage = "Animal já cadastrado")]

        public string NomeAnimal { get; set; }

        [DisplayName("Espécie")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "A espécie é obrigatório")]
        public string NomeEspecie { get; set; }

        [DisplayName("Habitat")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "Informe o habitat")]
        [Remote("ValidaHabitat", "Animal", ErrorMessage = "Habitat não existe ou está sobrecarregado")]
        public string NomeHabitat { get; set; }

        [Required(ErrorMessage = "A data de nascimento é obrigatória")]
        [DataType(DataType.Date, ErrorMessage = "Data em formato inválido")]
        [DisplayName("Nascimento")]
        public DateTime DataNasc { get; set; }

        [DisplayName("Porte")]
        [Required(ErrorMessage = "Informe o porte")]
        public string NomePorte { get; set; }

        [DisplayName("Peso")]
        [Required(ErrorMessage = "Informe o peso")]
        public double Peso { get; set; }

        [DisplayName("Sexo")]
        [StringLength(1, MinimumLength = 1, ErrorMessage = "Informe F ou M")]
        [Required(ErrorMessage = "Informe o sexo")]
        public string Sexo { get; set; }

        [DisplayName("Descrição")]
        [DataType(DataType.MultilineText)]
        [StringLength(150, MinimumLength = 5, ErrorMessage = "O campo deve conter no máximo 150 caracteres")]
        [Required(ErrorMessage = "A descrição é obrigatória")]
        public string DescricaoAnimal { get; set; }


        [DisplayName("Dieta")]
        [Required(ErrorMessage = "Informe a dieta do animal")]
        public string NomeDieta { get; set; }

        [DisplayName("Prontuário")]
        [DataType(DataType.MultilineText)]
        [StringLength(150, MinimumLength = 5, ErrorMessage = "O campo deve conter no máximo 150 caracteres")]
        [Required(ErrorMessage = "A observação sobre o prontuário é obrigatória")]
        public string ObsProntuario { get; set; }

    }
}
