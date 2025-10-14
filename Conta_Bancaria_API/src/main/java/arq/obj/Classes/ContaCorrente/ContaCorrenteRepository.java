package arq.obj.Classes.ContaCorrente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, Integer> {
	public ContaCorrente findByNumero(String numero);
}
