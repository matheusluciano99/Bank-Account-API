package arq.obj.Classes.Cartao;

import arq.obj.Classes.Usuario.Usuario;
import arq.obj.Classes.Usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService service;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public Collection<Cartao> listarCartoes() {
        return service.listarTodos();
    }

    @GetMapping("/{numeroCartao}")
    public Cartao buscarCartao(@PathVariable String numeroCartao) {
        return service.buscarPorNumero(numeroCartao);
    }

    @PostMapping
    public Cartao criarCartao(@RequestHeader(name = "token") String token, @RequestBody Cartao cartao) {
        Usuario usuario = usuarioService.validarToken(token);
        return service.salvar(cartao);
    }

    @GetMapping("/{numeroCartao}/ativo")
    public boolean isCartaoAtivo(@PathVariable String numeroCartao) {
        Cartao cartao = service.buscarPorNumero(numeroCartao);
        return cartao != null && "ATIVO".equals(cartao.getStatus()) && !cartao.isExpired();
    }

    @DeleteMapping("/{numeroCartao}")
    public void deletarCartao(@PathVariable String numeroCartao, @RequestHeader(name = "token") String token) {
        Usuario usuario = usuarioService.validarToken(token);
        service.deletar(numeroCartao);
    }
}