package arq.obj.Classes.Cartao;

import arq.obj.Classes.ContaCorrente.ContaCorrente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Cartao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true, nullable = false)
	private String numeroCartao;
	
	@Column(nullable = false)
	private String tipo;
	private LocalDate validade;
	private String status = "ATIVO";

	public Integer getId() {
		return this.id;
	}

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "conta_id")
	private ContaCorrente contaCorrente;

    public String getNumeroCartao() {
        return this.numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getValidade() {
        return this.validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public Boolean isExpired() {
		if (validade == null) {
			return false;
		}
		return validade.isBefore(LocalDate.now());
	}

	public Boolean cancelaCartao(){
		if ("CANCELADO".equals(this.status))
			return false;
		this.status = "CANCELADO";
		return true;
	}

	public ContaCorrente getContaCorrente() {
		return this.contaCorrente;
	}

	public void setContaCorrente(ContaCorrente contaCorrente) {
		this.contaCorrente = contaCorrente;
	}
}