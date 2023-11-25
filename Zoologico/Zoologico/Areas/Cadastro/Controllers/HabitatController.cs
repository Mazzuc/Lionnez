using Microsoft.AspNetCore.Mvc;

namespace Zoologico.Areas.Cadastro.Controllers
{
    public class HabitatController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }
    }
}
