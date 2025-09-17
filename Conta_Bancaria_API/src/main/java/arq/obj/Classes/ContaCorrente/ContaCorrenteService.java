package arq.obj.Classes.ContaCorrente;

import arq.obj.Classes.Cliente.Cliente;
import arq.obj.Classes.Movimentacao.Movimentacao;
import arq.obj.Classes.Cartao.Cartao;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class ContaCorrenteService {
    private final Map<String, ContaCorrente> contas = new HashMap<>();

    public Collection<ContaCorrente> listarTodas() {
        return contas.values();
    }

    public ContaCorrente buscarPorNumero(String numero) {
        return contas.get(numero);
    }

    public ContaCorrente salvar(ContaCorrente conta) {
        contas.put(conta.getNumero(), conta);
        return conta;
    }

    public Float saque(String numero, Float valor) {
        ContaCorrente conta = buscarPorNumero(numero);
        if (conta != null) {
            return conta.saque(valor);
        }
        throw new RuntimeException("Conta não encontrada");
    }

    public Float deposito(String numero, Float valor) {
        ContaCorrente conta = buscarPorNumero(numero);
        if (conta != null) {
            return conta.deposito(valor);
        }
        throw new RuntimeException("Conta não encontrada");
    }

    public Collection<Movimentacao> listarMovimentacoes(String numero) {
        ContaCorrente conta = buscarPorNumero(numero);
        if (conta != null) {
            return conta.listaMovimentacoes();
        }
        throw new RuntimeException("Conta não encontrada");
    }

    public Collection<Cartao> listarCartoes(String numero) {
        ContaCorrente conta = buscarPorNumero(numero);
        if (conta != null) {
            return conta.listaCartoes();
        }
        throw new RuntimeException("Conta não encontrada");
    }
}