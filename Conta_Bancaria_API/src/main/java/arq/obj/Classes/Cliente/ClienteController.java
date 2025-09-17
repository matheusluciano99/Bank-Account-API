package arq.obj.Classes.Cliente;

import arq.obj.Classes.Usuario.Usuario;
import arq.obj.Classes.Usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public Collection<Cliente> listarClientes() {
        return service.listarTodos();
    }

    @GetMapping("/{cpf}")
    public Cliente buscarCliente(@PathVariable String cpf) {
        return service.buscarPorCpf(cpf);
    }

    @PostMapping
    public Cliente criarCliente(@RequestHeader(name = "token") String token, @RequestBody Cliente cliente) {
        Usuario usuario = usuarioService.validarToken(token);
        return service.salvar(cliente);
    }

    @PutMapping("/{cpf}")
    public Cliente editarCliente(@PathVariable String cpf, @RequestHeader(name = "token") String token, @RequestBody Cliente cliente) {
        Usuario usuario = usuarioService.validarToken(token);
        cliente.setCpf(cpf);
        return service.salvar(cliente);
    }

    @DeleteMapping("/{cpf}")
    public void deletarCliente(@PathVariable String cpf, @RequestHeader(name = "token") String token) {
        Usuario usuario = usuarioService.validarToken(token);
        service.deletar(cpf);
    }
}