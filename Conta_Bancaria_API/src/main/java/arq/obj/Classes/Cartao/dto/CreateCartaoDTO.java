package arq.obj.Classes.Cartao.dto;

import java.time.LocalDate;

public record CreateCartaoDTO(
    String numeroCartao,
    String tipo,
    LocalDate validade,
    Integer contaId
) {
}
