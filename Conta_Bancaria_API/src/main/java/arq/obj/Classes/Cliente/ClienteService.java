package arq.obj.Classes.Cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public List<Cliente> listarTodos() {
        return repository.findAll();
    }

    public Cliente buscarPorCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    public Cliente salvar(Cliente cliente) {
        return repository.save(cliente);
    }

    public void deletar(String cpf) {
        Cliente cliente = buscarPorCpf(cpf);
        if (cliente != null) {
            repository.deleteById(cliente.getId());
        }
    }
}