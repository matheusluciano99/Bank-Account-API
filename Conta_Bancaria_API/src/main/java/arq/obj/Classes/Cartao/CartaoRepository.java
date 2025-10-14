package arq.obj.Classes.Cartao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Integer> {
	public Cartao findByNumeroCartao(String numeroCartao);
}
