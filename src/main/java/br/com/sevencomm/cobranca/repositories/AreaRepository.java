package br.com.sevencomm.cobranca.repositories;

import br.com.sevencomm.cobranca.models.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Long> {
    List<Area> findByNomeLike(String nome);
}
