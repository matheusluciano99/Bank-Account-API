package arq.obj.Classes.Cartao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository repository;

    public List<Cartao> listarTodos() {
        return repository.findAll();
    }

    public Cartao buscarPorNumero(String numeroCartao) {
        return repository.findByNumeroCartao(numeroCartao);
    }

    public Cartao salvar(Cartao cartao) {
        return repository.save(cartao);
    }

    public void deletar(String numeroCartao) {
        Cartao cartao = buscarPorNumero(numeroCartao);
        if (cartao != null) {
            repository.deleteById(cartao.getId());
        }
    }
}