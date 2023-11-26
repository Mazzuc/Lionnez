using Microsoft.AspNetCore.Mvc;
using System.Security.Claims;
using Zoologico.Models;
using Zoologico.ViewModels;
using Microsoft.Owin.Host.SystemWeb;

namespace Zoologico.Controllers
{
    public class HomeController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }

        [HttpGet]
        public ActionResult Insert()
        {
            return View();
        }
        [HttpPost]
        public ActionResult Insert(CadastroUsuarioViewModel vielmodel)
        {
            if (!ModelState.IsValid)
                return View(vielmodel);
            Cadastro novousuario = new Cadastro
            {
                Nome = vielmodel.Nome,
                Email = vielmodel.Email,
                CPF = vielmodel.CPF,
                Usuario = vielmodel.Usuario,
                Senha = vielmodel.Senha
            };
            novousuario.InsertCadastro(novousuario);
            return View();
        }

        public ActionResult Login(string ReturnUrl)
        {
            var vielmodel = new LoginViewModel
            {
                UrlRetorno = ReturnUrl
            };
            return View(vielmodel);
        }
        //[HttpPost]
        //public ActionResult Login(LoginViewModel viewmodel)
        //{
        //    if(!ModelState.IsValid)
        //    {
        //        return View(viewmodel);
        //    }
        //    Cadastro cadastro = new Cadastro();
        //    cadastro = cadastro.SelectUsuario(viewmodel.Usuario);

        //    if(cadastro == null | cadastro.Usuario != viewmodel.Usuario)
        //    {
        //        ModelState.AddModelError("Usuario", "Login incorreto");
        //        return View(viewmodel);
        //    }
        //    if(cadastro.Senha != viewmodel.Senha)
        //    {
        //        ModelState.AddModelError("Senha", "Senha incorreta");
        //        return View(viewmodel);
        //    }

        //    var identity = new ClaimsIdentity(new[]
        //    {
        //        new Claim(ClaimTypes.Name,cadastro.Usuario),
        //        new Claim("Usuario", cadastro.Usuario)
        //    }, "AppAplicationCookie");

        //    Request.GetOwinContext().Authentication.SingIn(identity);
        //    return View();
        //}
    }
}