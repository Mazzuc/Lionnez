using Microsoft.AspNetCore.Mvc;
using MySql.Data.MySqlClient;
using MySqlX.XDevAPI;
using Zoologico.Models;

namespace Zoologico.Controllers
{
    public class HabitatController : Controller
    {
        Habitat ObjHabitat = new Habitat();
        Animal ObjAnimal = new Animal();

        public ActionResult Details(int Id)
        {
            var list = ObjAnimal.SelectListHabitat(Id);
            return View(list);
        }
        [HttpGet]
        public ActionResult Select()
        {
            var list = ObjHabitat.SelectList();
            return View(list);
        }
        [HttpGet]
        public ActionResult Insert()
        {
            return View();
        }

        [HttpPost]
        public ActionResult Insert(Habitat vielmodel)
        {
            if (!ModelState.IsValid)
                return View(vielmodel);

            Habitat novohabitat = new Habitat()
            {
                NomeHabitat = vielmodel.NomeHabitat,
                TipoHabitat = vielmodel.TipoHabitat,
                Capacidade = vielmodel.Capacidade,
                Vegetacao = vielmodel.Vegetacao,
                Clima = vielmodel.Clima,
                Solo = vielmodel.Solo
            };
            novohabitat.InsertHabitat(novohabitat);

            return RedirectToAction("Index", "Home");
        }

        public ActionResult Delete(int Id)
        {
            var objHabitat = ObjHabitat.SelectHabitat(Id);
            return View(objHabitat);
        }
        [HttpPost, ActionName("Delete")]
        public ActionResult ConfirmeDelete(int Id)
        {
            ObjHabitat.DeleteHabitat(Id);
            return RedirectToAction("Select");
        }

        public ActionResult Edit(int Id)
        {
            var objHabitat = ObjHabitat.SelectHabitat(Id);

            return View(objHabitat);
        }
        [HttpPost]
        public ActionResult Edit(Habitat vielmodel)
        {
            if (!ModelState.IsValid)
            {

                Habitat mudancahabitat = new Habitat()
                {
                    IdHabitat = vielmodel.IdHabitat,
                    NomeHabitat = vielmodel.NomeHabitat,
                    Capacidade = vielmodel.Capacidade,
                    Vegetacao = vielmodel.Vegetacao,
                    Clima = vielmodel.Clima,
                    Solo = vielmodel.Solo
                };
                mudancahabitat.UpdateHabitat(mudancahabitat);

                return RedirectToAction("Select");
            }
            return View(vielmodel);
        }
    }
}
