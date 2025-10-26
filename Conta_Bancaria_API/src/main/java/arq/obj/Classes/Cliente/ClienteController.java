package arq.obj.Classes.Cliente;

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
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista com todos os clientes cadastrados")
    @GetMapping
    public Collection<Cliente> listarClientes() {
        return service.listarTodos();
    }

    @Operation(summary = "Buscar cliente por CPF", description = "Retorna os dados de um cliente específico pelo CPF")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{cpf}")
    public Cliente buscarCliente(
        @Parameter(description = "CPF do cliente") @PathVariable String cpf
    ) {
        return service.buscarPorCpf(cpf);
    }

    @Operation(summary = "Criar novo cliente", description = "Cria um novo cliente no sistema (requer autenticação)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente criarCliente(
        @Parameter(description = "Token de autenticação") @RequestHeader(name = "token") String token,
        @RequestBody Cliente cliente
    ) {
        Usuario usuario = usuarioService.validarToken(token);
        return service.salvar(cliente);
    }

    @Operation(summary = "Editar cliente", description = "Atualiza os dados de um cliente existente (requer autenticação)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PutMapping("/{cpf}")
    public Cliente editarCliente(
        @Parameter(description = "CPF do cliente") @PathVariable String cpf,
        @Parameter(description = "Token de autenticação") @RequestHeader(name = "token") String token,
        @RequestBody Cliente cliente
    ) {
        Usuario usuario = usuarioService.validarToken(token);
        cliente.setCpf(cpf);
        return service.salvar(cliente);
    }

    @Operation(summary = "Deletar cliente", description = "Remove um cliente do sistema (requer autenticação)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @DeleteMapping("/{cpf}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarCliente(
        @Parameter(description = "CPF do cliente") @PathVariable String cpf,
        @Parameter(description = "Token de autenticação") @RequestHeader(name = "token") String token
    ) {
        Usuario usuario = usuarioService.validarToken(token);
        service.deletar(cpf);
    }
}