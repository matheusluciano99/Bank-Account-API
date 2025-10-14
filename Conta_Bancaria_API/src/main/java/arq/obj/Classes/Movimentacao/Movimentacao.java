package arq.obj.Classes.Movimentacao;

import arq.obj.Classes.ContaCorrente.ContaCorrente;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Float valor;
    private String tipo;
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "conta_numero")
    private ContaCorrente contaCorrente;

    public Movimentacao() {
    }

    public Movimentacao(Float valor, String tipo, LocalDate data, ContaCorrente contaCorrente) {
        this.valor = valor;
        this.tipo = tipo;
        this.data = data;
        this.contaCorrente = contaCorrente;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getValor() {
        return this.valor;
    }

    public String getTipo() {
        return this.tipo;
    }

    public LocalDate getData() {
        return this.data;
    }

    public ContaCorrente getContaCorrente() {
        return this.contaCorrente;
    }
}
