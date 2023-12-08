using Microsoft.AspNetCore.Mvc;
using Zoologico.Areas.Gerenciamento.DAO;
using Zoologico.Areas.Gerenciamento.Models;
using Zoologico.Utils;
using Zoologico.Areas.Gerenciamento.ViewModels;

namespace Zoologico.Areas.Gerenciamento.Controllers
{
    public class FuncionarioController : Controller
    {
        FuncionarioDAO ObjFuncionario = new FuncionarioDAO();
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
        public ActionResult Insert(Funcionario vielmodel)
        {
            if (!ModelState.IsValid)
                return View(vielmodel);

            Funcionario novoanimal = new Funcionario()
            {
                Nome = vielmodel.Nome,
                Email = vielmodel.Email,
                CPF = vielmodel.CPF,
                Cargo = vielmodel.Cargo,
                Senha = Hash.GerarHash(vielmodel.Senha),
                Usuario = vielmodel.Usuario,
                RG = vielmodel.RG,
                DataNasc = vielmodel.DataNasc,
                DataAdm = vielmodel.DataAdm
            };
            ObjFuncionario.InsertFuncionario(novoanimal);

            TempData["MensagemCadastro"] = "Cadastro de " + vielmodel.Nome + " realizado com sucesso";

            return RedirectToAction("Index");
        }
    }
}
