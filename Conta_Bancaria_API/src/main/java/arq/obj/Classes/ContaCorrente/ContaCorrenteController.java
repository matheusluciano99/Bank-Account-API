package arq.obj.Classes.ContaCorrente;

import arq.obj.Classes.Cliente.Cliente;
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
    public ContaCorrente criarConta(
            @RequestParam String agencia,
            @RequestParam String numero,
            @RequestParam Float saldoInicial,
            @RequestParam Float limite,
            @RequestParam String cpf,
            @RequestParam String nome,
            @RequestParam LocalDate dataNascimento,
            @RequestParam Float salario
    ) {
        Cliente cliente = new Cliente(cpf, nome, dataNascimento, salario);
        ContaCorrente conta = new ContaCorrente(agencia, numero, saldoInicial, limite, cliente);
        return service.salvar(conta);
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
}