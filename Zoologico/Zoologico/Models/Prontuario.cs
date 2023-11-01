using System.ComponentModel.DataAnnotations;
using System.ComponentModel;

namespace Zoologico.Models
{
    public class Prontuario
    {
        [ReadOnly(true)]
        [DisplayName("Código")]
        public int IdProntuario { get; set; }

        [DisplayName("Nome")]
        public string NomeAnimal { get; set; }

        [DisplayName("Espécie")]
        public string NomeEspecie { get; set; }

        [DisplayName("Nascimento")]
        public DateTime DataNasc { get; set; }

        [DisplayName("Peso")]
        [Required(ErrorMessage = "Informe o peso")]
        public double Peso { get; set; }

        [DisplayName("Sexo")]
        [Required(ErrorMessage = "Informe o sexo")]
        public string Sexo { get; set; }

        [DisplayName("Prontuário")]
        [DataType(DataType.MultilineText)]
        public string ObsProntuario { get; set; }

    }
}
