package arq.obj.Classes.ContaCorrente;

import arq.obj.Classes.Cartao.Cartao;
import arq.obj.Classes.Movimentacao.Movimentacao;
import arq.obj.Classes.Usuario.Usuario;
import arq.obj.Classes.Usuario.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("/contas")
@Tag(name = "Contas Correntes", description = "Endpoints para gerenciamento de contas correntes")
public class ContaCorrenteController {

    @Autowired
    private ContaCorrenteService service;

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Listar todas as contas", description = "Retorna uma lista com todas as contas correntes")
    @GetMapping
    public Collection<ContaCorrente> listarContas() {
        return service.listarTodas();
    }

    @Operation(summary = "Buscar conta por número", description = "Retorna os dados de uma conta específica pelo número")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Conta encontrada"),
        @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    @GetMapping("/{numero}")
    public ContaCorrente buscarConta(
        @Parameter(description = "Número da conta") @PathVariable String numero
    ) {
        return service.buscarPorNumero(numero);
    }

    @Operation(summary = "Criar nova conta", description = "Cria uma nova conta corrente (requer autenticação)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Conta criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContaCorrente criarConta(
        @Parameter(description = "Token de autenticação") @RequestHeader(name = "token") String token,
        @RequestBody ContaCorrente contaCorrente
    ) {
        Usuario usuario = usuarioService.validarToken(token);
        return service.salvar(contaCorrente);
    }

    @Operation(summary = "Realizar saque", description = "Realiza um saque em uma conta corrente (requer autenticação)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Saque realizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Saldo insuficiente ou valor inválido"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    @PostMapping("/{numero}/saque")
    public Float saque(
        @Parameter(description = "Número da conta") @PathVariable String numero,
        @Parameter(description = "Token de autenticação") @RequestHeader(name = "token") String token,
        @Parameter(description = "Valor do saque") @RequestParam Float valor
    ) {
        Usuario usuario = usuarioService.validarToken(token);
        return service.saque(numero, valor);
    }

    @Operation(summary = "Realizar depósito", description = "Realiza um depósito em uma conta corrente (requer autenticação)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Depósito realizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Valor inválido"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    @PostMapping("/{numero}/deposito")
    public Float deposito(
        @Parameter(description = "Número da conta") @PathVariable String numero,
        @Parameter(description = "Token de autenticação") @RequestHeader(name = "token") String token,
        @Parameter(description = "Valor do depósito") @RequestParam Float valor
    ) {
        Usuario usuario = usuarioService.validarToken(token);
        return service.deposito(numero, valor);
    }

    @Operation(summary = "Listar movimentações", description = "Retorna todas as movimentações de uma conta")
    @GetMapping("/{numero}/movimentacoes")
    public Collection<Movimentacao> listarMovimentacoes(
        @Parameter(description = "Número da conta") @PathVariable String numero
    ) {
        return service.listarMovimentacoes(numero);
    }

    @Operation(summary = "Listar cartões", description = "Retorna todos os cartões vinculados a uma conta")
    @GetMapping("/{numero}/cartoes")
    public Collection<Cartao> listarCartoes(
        @Parameter(description = "Número da conta") @PathVariable String numero
    ) {
        return service.listarCartoes(numero);
    }
}