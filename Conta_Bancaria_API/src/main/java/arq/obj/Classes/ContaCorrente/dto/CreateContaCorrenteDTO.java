package arq.obj.Classes.ContaCorrente.dto;

public record CreateContaCorrenteDTO(
    String agencia,
    String numero,
    Float saldo,
    Float limite,
    Integer clienteId
) {
}
