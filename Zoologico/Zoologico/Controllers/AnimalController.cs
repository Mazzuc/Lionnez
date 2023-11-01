using Microsoft.AspNetCore.Mvc;
using Zoologico.DAO;
using Zoologico.Models;

namespace Zoologico.Controllers
{
    public class AnimalController : Controller
    {
        AnimalDAO ObjAnimal = new AnimalDAO();

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

            AnimalDAO novoanimal = new AnimalDAO()
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

        public ActionResult Delete(int Id)
        {
            var objAnimal = ObjAnimal.SelectAnimal(Id);
            return View(objAnimal);
        }
        [HttpPost, ActionName("Delete")]
        public ActionResult ConfirmeDelete(int Id)
        {
            ObjAnimal.DeleteAnimal(Id);
            return RedirectToAction("Select");
        }

        public ActionResult Edit(int Id)
        {
            var objAnimal = ObjAnimal.SelectAnimal(Id);

            return View(objAnimal);
        }
        //[HttpPost]
        //public ActionResult Edit(Animal vielmodel)
        //{
        //    if (!ModelState.IsValid)
        //    {

        //        Animal animal = new Animal()
        //        {
        //            IdAnimal = vielmodel.IdAnimal,
        //            NomeHabitat = vielmodel.NomeHabitat,
        //            DescricaoAnimal = vielmodel.DescricaoAnimal,
        //            ObsProntuario = vielmodel.ObsProntuario
        //        };
        //        animal.UpdateAnimal(animal);

        //        return RedirectToAction("Select");
        //    }
        //    return View(vielmodel);
           
        //}
    }
}
