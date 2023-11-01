using Microsoft.AspNetCore.Mvc;
using MySql.Data.MySqlClient;
using MySqlX.XDevAPI;
using Zoologico.DAO;
using Zoologico.Models;

namespace Zoologico.Controllers
{
    public class HabitatController : Controller
    {
        HabitatDAO ObjHabitat = new HabitatDAO();
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
        public ActionResult Insert(string NomeHabitat, string TipoHabitat, int Capacidade, string Vegetacao, string Clima, string Solo)
        {
            if (!ModelState.IsValid)
                return View("Select");

            ObjHabitat.InsertHabitat(NomeHabitat, TipoHabitat, Capacidade, Vegetacao, Clima, Solo);

            return RedirectToAction("Select");
        }

        [HttpPost]
        public ActionResult Edit(int IdHabitat, string NomeHabitat, int Capacidade, string Vegetacao, string Clima, string Solo)
        {
            if (!ModelState.IsValid)
                return View("select");
   
                ObjHabitat.UpdateHabitat(IdHabitat, NomeHabitat, Capacidade, Vegetacao, Clima, Solo);

                return RedirectToAction("Select");
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
       
    }
}
