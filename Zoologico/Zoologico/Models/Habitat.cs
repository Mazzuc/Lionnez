using MySql.Data.MySqlClient;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System;
using System.Configuration;
using System.Data;
using ConfigurationManager = System.Configuration.ConfigurationManager;
using Org.BouncyCastle.Asn1.Mozilla;

namespace Zoologico.Models
{
    public class Habitat
    {
        [ReadOnly(true)]
        [DisplayName("Código")]
        public int IdHabitat { get; set; }

        [DisplayName("Nome")]
        [StringLength(50, MinimumLength = 5, ErrorMessage = "O campo deve conter no mínimo 5 caracteres")]
        [Required(ErrorMessage = "O nome é obrigatório")]
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
