using Microsoft.AspNetCore.Mvc;
using Zoologico.Models;

namespace Zoologico.Controllers
{
    public class HabitatController : Controller
    {
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult Resultado(Habitat ObjHabitat)
        {
            return View(ObjHabitat);
        }

        public ActionResult Criar()
        {
            return View();
        }

        [HttpPost]
        public ActionResult Criar(Habitat ObjHabitat)
        {

            if (ModelState.IsValid)
            {
                return View("Resultado", ObjHabitat);
            }

            return View();
        }
    }
}
