package arq.obj.Classes.ContaCorrente.dto;

import arq.obj.Classes.ContaCorrente.ContaCorrente;

public record ContaCorrenteResponseDTO(
    Integer id,
    String agencia,
    String numero,
    Float saldo,
    Float limite,
    Integer clienteId
) {
    public ContaCorrenteResponseDTO(ContaCorrente conta) {
        this(
            conta.getId(),
            conta.getAgencia(),
            conta.getNumero(),
            conta.getSaldo(),
            conta.getLimite(),
            conta.getCliente() != null ? conta.getCliente().getId() : null
        );
    }
}
