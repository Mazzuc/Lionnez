using Microsoft.AspNetCore.Mvc;

namespace Zoologico.Areas.Compra.Controllers
{
    public class IngressoController : Controller
    {
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult Ingresso()
        {
            return View();
        }

        [HttpPost]
        public ActionResult ResumoComprar(FormCollection resumo)
        {
            ViewBag.Nome = resumo["Nome"];
            ViewBag.Email = resumo["Email"];
            ViewBag.Cpf = resumo["Cpf"];
            string Data = resumo["DataIngresso"];
            DateTime dataConvertida;
            if (DateTime.TryParse(Data, out dataConvertida))
            {
                string dataFormatada = dataConvertida.ToString("dd/MM/yyyy");
                ViewBag.Data = dataFormatada;
                ViewBag.QtdInteira = resumo["QtdInteira"];
                ViewBag.QtdInteiraFormat = ViewBag.QtdInteira + "X";
                ViewBag.TotalInteira = Int32.Parse(ViewBag.QtdInteira) * 30;
                ViewBag.FormatInteira = "R$ " + ViewBag.TotalInteira + ",00";
                ViewBag.QtdMeia = resumo["QtdMeia"];
                ViewBag.QtdMeiaFormat = ViewBag.QtdMeia + "X";
                ViewBag.TotalMeia = Int32.Parse(ViewBag.QtdMeia) * 15;
                ViewBag.FormatMeia = "R$ " + ViewBag.TotalMeia + ",00";
                ViewBag.TotaResumo = ViewBag.TotalMeia + ViewBag.TotalInteira;
                ViewBag.FormatTotal = "R$ " + ViewBag.TotaResumo + ",00";
                return View();
            }
            return View();
        }

        //[HttpPost]
        public ActionResult Pagamento(/*FormCollection paga*/)
        {
            //ViewBag.Teste = paga["Teste"];
            return View();
        }

        [HttpPost]
        public ActionResult NotaFiscal(FormCollection paga)
        {
            ViewBag.Metodo = paga["metodo"];
            return View();
        }
    }
}
