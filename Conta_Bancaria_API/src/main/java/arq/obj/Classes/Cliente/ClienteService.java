package arq.obj.Classes.Cliente;

import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class ClienteService {
    private final Map<String, Cliente> clientes = new HashMap<>();

    public Collection<Cliente> listarTodos() {
        return clientes.values();
    }

    public Cliente buscarPorCpf(String cpf) {
        return clientes.get(cpf);
    }

    public Cliente salvar(Cliente cliente) {
        clientes.put(cliente.getCpf(), cliente);
        return cliente;
    }

    public void deletar(String cpf) {
        clientes.remove(cpf);
    }
}