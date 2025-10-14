package arq.obj.Classes.ContaCorrente;

import arq.obj.Classes.Movimentacao.Movimentacao;
import arq.obj.Classes.Movimentacao.MovimentacaoRepository;
import arq.obj.Classes.Cartao.Cartao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;

@Service
public class ContaCorrenteService {

    @Autowired
    private ContaCorrenteRepository repository;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    public List<ContaCorrente> listarTodas() {
        return repository.findAll();
    }

    public ContaCorrente buscarPorNumero(String numero) {
        return repository.findByNumero(numero);
    }

    public ContaCorrente salvar(ContaCorrente conta) {
        return repository.save(conta);
    }

    public Float saque(String numero, Float valor) {
        ContaCorrente conta = buscarPorNumero(numero);
        if (conta == null) {
            throw new RuntimeException("Conta não encontrada");
        }

        Float saldo = conta.saque(valor);

        if (!conta.getMovimentacoes().isEmpty()) {
            Movimentacao ultima = conta.getMovimentacoes().get(conta.getMovimentacoes().size() - 1);
            movimentacaoRepository.save(ultima);
        }

        repository.save(conta);
        return saldo;
    }

    public Float deposito(String numero, Float valor) {
        ContaCorrente conta = buscarPorNumero(numero);
        if (conta == null) {
            throw new RuntimeException("Conta não encontrada");
        }

        Float saldo = conta.deposito(valor);

        if (!conta.getMovimentacoes().isEmpty()) {
            Movimentacao ultima = conta.getMovimentacoes().get(conta.getMovimentacoes().size() - 1);
            movimentacaoRepository.save(ultima);
        }

        repository.save(conta);
        return saldo;
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