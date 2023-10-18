using Microsoft.AspNetCore.Mvc;
using Zoologico.Models;

namespace Zoologico.Controllers
{
    public class ProntuarioController : Controller
    {
        Prontuario ObjProntuario = new Prontuario();
        public ActionResult Select()
        {
            var list = ObjProntuario.SelectList();
            return View(list);
        }

        public ActionResult Details(int Id)
        {
            var habitat = ObjProntuario.SelectProntuario(Id);
            return View(habitat);
        }
    }
}
