﻿using Microsoft.AspNetCore.Mvc;
using Zoologico.Models;

namespace Zoologico.Controllers
{
    public class ProntuarioController : Controller
    {
        Prontuario ObjProntuario = new Prontuario();
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
            novaconsulta.InsertConsulta(novaconsulta);

            return RedirectToAction("Details", "Prontuario");
        }
    }
}
