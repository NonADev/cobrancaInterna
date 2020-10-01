package br.com.sevencomm.cobranca.data.repositories;


import br.com.sevencomm.cobranca.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLogin(String login);
    List<User> findAllByAreaId(Integer id);
}
