package arq.obj.Classes.Cartao;

import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class CartaoService {
    private final Map<String, Cartao> cartoes = new HashMap<>();

    public Collection<Cartao> listarTodos() {
        return cartoes.values();
    }

    public Cartao buscarPorNumero(String numeroCartao) {
        return cartoes.get(numeroCartao);
    }

    public Cartao salvar(Cartao cartao) {
        cartoes.put(cartao.getNumeroCartao(), cartao);
        return cartao;
    }

    public void deletar(String numeroCartao) {
        cartoes.remove(numeroCartao);
    }
}