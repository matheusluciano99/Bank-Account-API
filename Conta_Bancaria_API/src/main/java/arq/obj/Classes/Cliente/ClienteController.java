package arq.obj.Classes.Cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public Collection<Cliente> listarClientes() {
        return service.listarTodos();
    }

    @GetMapping("/{cpf}")
    public Cliente buscarCliente(@PathVariable String cpf) {
        return service.buscarPorCpf(cpf);
    }

    @PostMapping
    public Cliente criarCliente(@RequestBody Cliente cliente) {
        return service.salvar(cliente);
    }

    @PutMapping("/{cpf}")
    public Cliente editarCliente(@PathVariable String cpf, @RequestBody Cliente cliente) {
        cliente.setCpf(cpf);
        return service.salvar(cliente);
    }

    @DeleteMapping("/{cpf}")
    public void deletarCliente(@PathVariable String cpf) {
        service.deletar(cpf);
    }
}