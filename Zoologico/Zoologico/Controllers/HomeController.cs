using Microsoft.AspNetCore.Mvc;
using System.Security.Claims;
using Zoologico.Models;
using Zoologico.ViewModels;
using Microsoft.Owin.Host.SystemWeb;
using Zoologico.Areas.Gerenciamento.Models;
using Zoologico.Areas.Gerenciamento.ViewModels;
using Zoologico.DAO;

namespace Zoologico.Controllers
{
    public class HomeController : Controller
    {
        Usuario ObjLogin = new Usuario();
        public ActionResult Index()
        {
            return View();
        }
        public ActionResult ValidaLogin(string vLogin)
        {
            bool UsuarioExists;
            string usuario = ObjLogin.ValidaLogin(vLogin);

            if (usuario.Length == 0)
                UsuarioExists = true;
            else
                UsuarioExists = false;

            return Json(!UsuarioExists, new System.Text.Json.JsonSerializerOptions());
        }
    }
}