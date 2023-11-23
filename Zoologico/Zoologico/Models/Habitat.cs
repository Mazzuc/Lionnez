using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using Microsoft.AspNetCore.Mvc;

namespace Zoologico.Models
{
    public class Habitat
    {
        [ReadOnly(true)]
        [DisplayName("Código")]
        public int IdHabitat { get; set; }

        [DisplayName("Nome")]
        [Required(ErrorMessage = "O nome é obrigatório")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Remote("ValidaHabitat", "Habitat", ErrorMessage = "Habitat já cadastrado")]
        public string NomeHabitat { get; set; }

        [DisplayName("Tipo")]
        [Required(ErrorMessage = "O tipo é obrigatório")]
        public string TipoHabitat { get; set; }

        [DisplayName("Capacidade")]
        [Range(1, 100, ErrorMessage = "A capacidade deve ser maior que zero")]
        [Required(ErrorMessage = "Informe a capacidade")]
        public int Capacidade { get; set; }

        [DisplayName("Vegetação")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "Informe a vegetação")]
        public string Vegetacao { get; set; }

        [DisplayName("Clima")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "Informe o clima")]
        public string Clima { get; set; }

        [DisplayName("Solo")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "Informe o solo")]
        public string Solo { get; set; }

        [ReadOnly(true)]
        [DisplayName("Número de Animais")]
        public int QtdAnimais { get; set; }

    }
}
