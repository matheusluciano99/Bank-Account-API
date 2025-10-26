package arq.obj.Classes.Cliente.dto;

import arq.obj.Classes.Cliente.Cliente;
import java.time.LocalDate;

public record ClienteResponseDTO(
    Integer id,
    String cpf,
    String nome,
    LocalDate dataNascimento,
    Float salario
) {
    public ClienteResponseDTO(Cliente cliente) {
        this(
            cliente.getId(),
            cliente.getCpf(),
            cliente.getNome(),
            cliente.getDataNascimento(),
            cliente.getSalario()
        );
    }
}
