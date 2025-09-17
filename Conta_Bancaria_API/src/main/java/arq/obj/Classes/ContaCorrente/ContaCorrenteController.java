package arq.obj.Classes.ContaCorrente;

import arq.obj.Classes.Cartao.Cartao;
import arq.obj.Classes.Movimentacao.Movimentacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("/contas")
public class ContaCorrenteController {

    @Autowired
    private ContaCorrenteService service;

    @GetMapping
    public Collection<ContaCorrente> listarContas() {
        return service.listarTodas();
    }

    @GetMapping("/{numero}")
    public ContaCorrente buscarConta(@PathVariable String numero) {
        return service.buscarPorNumero(numero);
    }

    @PostMapping
    public ContaCorrente criarConta(@RequestBody ContaCorrente contaCorrente) {
        return service.salvar(contaCorrente);
    }

    @PostMapping("/{numero}/saque")
    public Float saque(@PathVariable String numero, @RequestParam Float valor) {
        return service.saque(numero, valor);
    }

    @PostMapping("/{numero}/deposito")
    public Float deposito(@PathVariable String numero, @RequestParam Float valor) {
        return service.deposito(numero, valor);
    }

    @GetMapping("/{numero}/movimentacoes")
    public Collection<Movimentacao> listarMovimentacoes(@PathVariable String numero) {
        return service.listarMovimentacoes(numero);
    }

    @GetMapping("/{numero}/cartoes")
    public Collection<Cartao> listarCartoes(@PathVariable String numero) {
        return service.listarCartoes(numero);
    }
}