using Microsoft.AspNetCore.Mvc;
using Zoologico.Models;

namespace Zoologico.Controllers
{
    public class AnimalController : Controller
    {
        [HttpGet]
        public ActionResult Insert()
        {
            return View();
        }

        [HttpPost]
        public ActionResult Insert(Animal vielmodel)
        {
            if (!ModelState.IsValid)
                return View(vielmodel);

            Animal novoanimal = new Animal()
            {
                NomeAnimal = vielmodel.NomeAnimal,
                NomeEspecie = vielmodel.NomeEspecie,
                NomeHabitat = vielmodel.NomeHabitat,
                DataNasc = vielmodel.DataNasc,
                NomePorte = vielmodel.NomePorte,
                Peso = vielmodel.Peso,
                Sexo = vielmodel.Sexo,
                DescricaoAnimal = vielmodel.DescricaoAnimal,
                NomeDieta = vielmodel.NomeDieta,
                ObsProntuario = vielmodel.ObsProntuario
            };
            novoanimal.InsertAnimal(novoanimal);

            return RedirectToAction("Index", "Home");
        }
    }
}
