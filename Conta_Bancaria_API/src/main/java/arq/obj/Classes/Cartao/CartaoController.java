package arq.obj.Classes.Cartao;

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
@RequestMapping("/cartoes")
@Tag(name = "Cartões", description = "Endpoints para gerenciamento de cartões")
public class CartaoController {

    @Autowired
    private CartaoService service;

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Listar todos os cartões", description = "Retorna uma lista com todos os cartões cadastrados")
    @GetMapping
    public Collection<Cartao> listarCartoes() {
        return service.listarTodos();
    }

    @Operation(summary = "Buscar cartão por número", description = "Retorna os dados de um cartão específico pelo número")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cartão encontrado"),
        @ApiResponse(responseCode = "404", description = "Cartão não encontrado")
    })
    @GetMapping("/{numeroCartao}")
    public Cartao buscarCartao(
        @Parameter(description = "Número do cartão") @PathVariable String numeroCartao
    ) {
        return service.buscarPorNumero(numeroCartao);
    }

    @Operation(summary = "Criar novo cartão", description = "Cria um novo cartão vinculado a uma conta (requer autenticação)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cartão criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cartao criarCartao(
        @Parameter(description = "Token de autenticação") @RequestHeader(name = "token") String token,
        @RequestBody Cartao cartao
    ) {
        Usuario usuario = usuarioService.validarToken(token);
        return service.salvar(cartao);
    }

    @Operation(summary = "Verificar se cartão está ativo", description = "Verifica se um cartão está ativo e não expirado")
    @GetMapping("/{numeroCartao}/ativo")
    public boolean isCartaoAtivo(
        @Parameter(description = "Número do cartão") @PathVariable String numeroCartao
    ) {
        Cartao cartao = service.buscarPorNumero(numeroCartao);
        return cartao != null && "ATIVO".equals(cartao.getStatus()) && !cartao.isExpired();
    }

    @Operation(summary = "Deletar cartão", description = "Remove um cartão do sistema (requer autenticação)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cartão deletado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "404", description = "Cartão não encontrado")
    })
    @DeleteMapping("/{numeroCartao}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarCartao(
        @Parameter(description = "Número do cartão") @PathVariable String numeroCartao,
        @Parameter(description = "Token de autenticação") @RequestHeader(name = "token") String token
    ) {
        Usuario usuario = usuarioService.validarToken(token);
        service.deletar(numeroCartao);
    }
}