package arq.obj.Classes.Usuario;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    private HashMap<String, Usuario> tokens = new HashMap<>();

    public Usuario cadastrarUsuario(Usuario usuario) {
        String password = usuario.getPassword();
        usuario.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        return repository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return repository.findAll();
    }

    public String login(Usuario usuario) {
        Usuario user = repository.findByEmail(usuario.getEmail());
        if (user != null && BCrypt.checkpw(usuario.getPassword(), user.getPassword())) {
            String token = UUID.randomUUID().toString();
            tokens.put(token, user);
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