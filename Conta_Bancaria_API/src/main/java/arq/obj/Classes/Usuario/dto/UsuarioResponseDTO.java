package arq.obj.Classes.Usuario.dto;

import arq.obj.Classes.Usuario.Usuario;

public record UsuarioResponseDTO(
    Integer id,
    String email
) {
    public UsuarioResponseDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getEmail());
    }
}
