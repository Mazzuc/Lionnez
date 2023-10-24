using Microsoft.AspNetCore.Mvc;
using MySql.Data.MySqlClient;
using MySqlX.XDevAPI;
using Zoologico.Models;

namespace Zoologico.Controllers
{
    public class HabitatController : Controller
    {
        Habitat ObjHabitat = new Habitat();
        List<Habitat> ObjHabitatList = new List<Habitat>();

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
    }
}
