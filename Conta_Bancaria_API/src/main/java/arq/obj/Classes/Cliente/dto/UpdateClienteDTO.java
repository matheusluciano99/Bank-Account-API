package arq.obj.Classes.Cliente.dto;

import java.time.LocalDate;

public record UpdateClienteDTO(
    String nome,
    LocalDate dataNascimento,
    Float salario
) {
}
