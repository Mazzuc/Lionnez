﻿using Microsoft.AspNetCore.Mvc;
using System.Security.Claims;
using Zoologico.DAO;
using Zoologico.Models;

namespace Zoologico.Controllers
{
    public class AnimalController : Controller
    {
        AnimalDAO ObjAnimal = new AnimalDAO();
        HabitatDAO ObjHabitat = new HabitatDAO();

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

        public ActionResult Search(string NomeAnimal)
        {
            var list = ObjAnimal.Search(NomeAnimal);
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
            ObjAnimal.InsertAnimal(novoanimal);

            TempData["MensagemCadastro"] = "Cadastro de " + vielmodel.NomeAnimal + " realizado com sucesso";

            return RedirectToAction("Select");
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
            TempData["MensagemCadastro"] = "Cadastro excluído";

            return RedirectToAction("Select");
        }

        public ActionResult Edit(int Id)
        {
            var objAnimal = ObjAnimal.SelectAnimal(Id);

            return View(objAnimal);
        }

        [HttpPost]
        public ActionResult Edit(int IdAnimal, string NomeHabitat, string DescricaoAnimal, string ObsProntuario)
        {
            if (!ModelState.IsValid)
                return View("select");

            ObjAnimal.UpdateAnimal(IdAnimal, NomeHabitat, DescricaoAnimal, ObsProntuario);

            return RedirectToAction("Select");
        }

        public ActionResult ValidaAnimal(string NomeAnimal)
        {
            bool AnimalExists;
            string animal = ObjAnimal.ValidaAnimal(NomeAnimal);

            if (animal.Length == 0)
                AnimalExists = false;
            else
                AnimalExists = true;

            return Json(!AnimalExists, new System.Text.Json.JsonSerializerOptions());
        }

        public ActionResult ValidaHabitat(string NomeHabitat)
        {
            bool HabitatExists;
            string habitat = ObjHabitat.ValidaHabitat(NomeHabitat);
            string qts = ObjAnimal.ValidaHabitat(NomeHabitat);

            if (habitat.Length == 0)
                HabitatExists = true;
            else if (qts.Length == 0)
                HabitatExists = true;
            else
                HabitatExists = false;

            return Json(!HabitatExists, new System.Text.Json.JsonSerializerOptions());
        }

    }
}
