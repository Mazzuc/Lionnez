using Microsoft.AspNetCore.Mvc;
using Zoologico.Models;

namespace Zoologico.Controllers
{
    public class AnimalController : Controller
    {
        Animal ObjAnimal = new Animal();

        public ActionResult Details(int Id)
        {
            var habitat = ObjAnimal.SelectAnimal(Id);
            return View(habitat);
        }
        public ActionResult Select()
        {
            var list = ObjAnimal.SelectList();
            return View(list);
        }
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
