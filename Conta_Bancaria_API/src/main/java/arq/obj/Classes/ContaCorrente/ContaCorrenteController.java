package arq.obj.Classes.ContaCorrente;

import arq.obj.Classes.Cartao.Cartao;
import arq.obj.Classes.Movimentacao.Movimentacao;
import arq.obj.Classes.Usuario.Usuario;
import arq.obj.Classes.Usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("/contas")
public class ContaCorrenteController {

    @Autowired
    private ContaCorrenteService service;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public Collection<ContaCorrente> listarContas() {
        return service.listarTodas();
    }

    @GetMapping("/{numero}")
    public ContaCorrente buscarConta(@PathVariable String numero) {
        return service.buscarPorNumero(numero);
    }

    @PostMapping
    public ContaCorrente criarConta(@RequestHeader(name = "token") String token, @RequestBody ContaCorrente contaCorrente) {
        Usuario usuario = usuarioService.validarToken(token);
        return service.salvar(contaCorrente);
    }

    @PostMapping("/{numero}/saque")
    public Float saque(@PathVariable String numero, @RequestHeader(name = "token") String token, @RequestParam Float valor) {
        Usuario usuario = usuarioService.validarToken(token);
        return service.saque(numero, valor);
    }

    @PostMapping("/{numero}/deposito")
    public Float deposito(@PathVariable String numero, @RequestHeader(name = "token") String token, @RequestParam Float valor) {
        Usuario usuario = usuarioService.validarToken(token);
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