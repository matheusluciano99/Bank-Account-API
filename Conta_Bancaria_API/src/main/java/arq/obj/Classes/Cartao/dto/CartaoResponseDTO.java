package arq.obj.Classes.Cartao.dto;

import arq.obj.Classes.Cartao.Cartao;
import java.time.LocalDate;

public record CartaoResponseDTO(
    Integer id,
    String numeroCartao,
    String tipo,
    LocalDate validade,
    String status,
    Integer contaId
) {
    public CartaoResponseDTO(Cartao cartao) {
        this(
            cartao.getId(),
            cartao.getNumeroCartao(),
            cartao.getTipo(),
            cartao.getValidade(),
            cartao.getStatus(),
            cartao.getContaCorrente() != null ? cartao.getContaCorrente().getId() : null
        );
    }
}
