package br.com.sevencomm.cobranca.repositories;

import br.com.sevencomm.cobranca.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {

}
