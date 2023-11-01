using Microsoft.AspNetCore.Mvc;
using Zoologico.DAO;
using Zoologico.Models;

namespace Zoologico.Controllers
{
    public class ProntuarioController : Controller
    {
        ProntuarioDAO ObjProntuario = new ProntuarioDAO();
        Consulta ObjConsulta = new Consulta();
        public ActionResult Select()
        {
            var list = ObjProntuario.SelectList();
            return View(list);
        }

        public ActionResult Details(int Id)
        {
            var list = ObjConsulta.SelectListConsulta(Id);
            return View(list);
        }

        [HttpGet]
        public ActionResult Insert()
        {
            return View();
        }

        [HttpPost]
        public ActionResult Insert(Consulta vielmodel, int Id)
        {
            if (!ModelState.IsValid)
                return View(vielmodel);

            Consulta novaconsulta = new Consulta()
            {
                IdProntuario = Id,
                Alergia = vielmodel.Alergia,
                DescricaoHistorico = vielmodel.DescricaoHistorico,
                Peso = vielmodel.Peso
            };
            novaconsulta.InsertConsulta(novaconsulta, Id);

            return RedirectToAction("Select", "Prontuario");
        }
    }
}
