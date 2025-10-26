package arq.obj.Classes.Cliente.dto;

import java.time.LocalDate;

public record CreateClienteDTO(
    String cpf,
    String nome,
    LocalDate dataNascimento,
    Float salario
) {
}
