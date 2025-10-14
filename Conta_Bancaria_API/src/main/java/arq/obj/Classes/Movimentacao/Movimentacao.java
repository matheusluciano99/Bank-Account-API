package arq.obj.Classes.Movimentacao;

import arq.obj.Classes.ContaCorrente.ContaCorrente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private Float valor;
    
    @Column(nullable = false)
    private String tipo;
    
    @Column(nullable = false)
    private LocalDate data;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "conta_id")
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
