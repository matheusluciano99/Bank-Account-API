package arq.obj.Classes.Usuario;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

@Service
public class UsuarioService {

    private HashMap<String, Usuario> usuarios = new HashMap<>();

    private HashMap<String, Usuario> tokens = new HashMap<>();

    public Usuario cadastrarUsuario(Usuario usuario) {

        String password = usuario.getPassword();

        usuario.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        usuarios.put(usuario.getEmail(), usuario);
        return usuario;
    }

    public Collection<Usuario> listarUsuarios() {
        return usuarios.values();
    }

    public String login(Usuario usuario) {

        Usuario user = usuarios.get(usuario.getEmail());
        if (BCrypt.checkpw(usuario.getPassword(), user.getPassword())) {

            String token = UUID.randomUUID().toString();
            tokens.put(token, usuario);
            return token;
        }

        throw new RuntimeException("Usuário ou senha inválidos");



    }

    public Usuario validarToken(String token) {

        Usuario usuario = tokens.get(token);

        if (usuario == null) {
            throw new RuntimeException("Token invalido");
        }

        return usuario;

    }
}