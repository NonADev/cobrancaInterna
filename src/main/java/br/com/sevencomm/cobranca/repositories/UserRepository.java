package br.com.sevencomm.cobranca.repositories;


import br.com.sevencomm.cobranca.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
