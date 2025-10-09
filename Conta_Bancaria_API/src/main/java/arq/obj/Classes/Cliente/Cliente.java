package arq.obj.Classes.Cliente;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Cliente {
	@Id
	private String cpf;
	private String nome;
	private LocalDate dataNascimento;
	private Float salario;

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Float getSalario() {
        return this.salario;
    }

    public void setSalario(Float salario) {
        this.salario = salario;
    }
    
}

