using Microsoft.AspNetCore.Mvc;
using Zoologico.Models;

namespace Zoologico.Controllers
{
    public class HabitatController : Controller
    {
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
