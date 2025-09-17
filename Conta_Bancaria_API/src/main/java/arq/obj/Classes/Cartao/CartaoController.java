package arq.obj.Classes.Cartao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService service;

    @GetMapping
    public Collection<Cartao> listarCartoes() {
        return service.listarTodos();
    }

    @GetMapping("/{numeroCartao}")
    public Cartao buscarCartao(@PathVariable String numeroCartao) {
        return service.buscarPorNumero(numeroCartao);
    }

    @PostMapping
    public Cartao criarCartao(@RequestBody Cartao cartao) {
        return service.salvar(cartao);
    }

    @GetMapping("/{numeroCartao}/ativo")
    public boolean isCartaoAtivo(@PathVariable String numeroCartao) {
        Cartao cartao = service.buscarPorNumero(numeroCartao);
        return cartao != null && "ATIVO".equals(cartao.getStatus()) && !cartao.isExpired();
    }

    @DeleteMapping("/{numeroCartao}")
    public void deletarCartao(@PathVariable String numeroCartao) {
        service.deletar(numeroCartao);
    }
}