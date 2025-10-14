package arq.obj.Classes.ContaCorrente;

import arq.obj.Classes.Cartao.Cartao;
import arq.obj.Classes.Cliente.Cliente;
import arq.obj.Classes.Movimentacao.Movimentacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ContaCorrente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String agencia;
	private String numero;
	private Float saldo;
	private Float limite;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "contaCorrente")
	private List<Movimentacao> movimentacoes = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "contaCorrente")
	private List<Cartao> cartoes = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "cliente_cpf")
	private Cliente cliente;

    public String getAgencia() {
        return this.agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Float getSaldo() {
        return this.saldo;
    }

    public Float getLimite() {
        return this.limite;
    }

	public Float saque(Float valor){
		try {
			if (valor != null && valor > 0 && valor <= this.saldo + this.limite) {
				this.saldo -= valor;
				this.movimentacoes.add(new Movimentacao(valor, "SAQUE", java.time.LocalDate.now(), this));
			}
		} catch (Exception e) {
			System.out.println("Erro ao realizar saque: " + e.getMessage());
		}
		return this.saldo;
	}

	public Float deposito(Float valor){
		try {
			if (valor == null || valor <= 0) throw new Exception("Valor inválido");
			this.saldo += valor;
			this.movimentacoes.add(new Movimentacao(valor, "DEPOSITO", java.time.LocalDate.now(), this));
		} catch (Exception e) {
			System.out.println("Erro ao realizar depósito: " + e.getMessage());
		}
		return this.saldo;
	}

	public List<Movimentacao> listaMovimentacoes() {
		return this.movimentacoes;
	}

	public List<Cartao> listaCartoes() {
		return this.cartoes;
	}

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Movimentacao> getMovimentacoes() {
        return this.movimentacoes;
    }

    public void setMovimentacoes(List<Movimentacao> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }

    public List<Cartao> getCartoes() {
        return this.cartoes;
    }

    public void setCartoes(List<Cartao> cartoes) {
        this.cartoes = cartoes;
    }

}