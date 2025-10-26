package arq.obj.Classes.Movimentacao.dto;

import arq.obj.Classes.Movimentacao.Movimentacao;
import java.time.LocalDate;

public record MovimentacaoResponseDTO(
    Integer id,
    Float valor,
    String tipo,
    LocalDate data,
    Integer contaId
) {
    public MovimentacaoResponseDTO(Movimentacao movimentacao) {
        this(
            movimentacao.getId(),
            movimentacao.getValor(),
            movimentacao.getTipo(),
            movimentacao.getData(),
            movimentacao.getContaCorrente() != null ? movimentacao.getContaCorrente().getId() : null
        );
    }
}
