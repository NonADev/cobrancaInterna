package br.com.sevencomm.cobranca.domain.interfaces;

import br.com.sevencomm.cobranca.application.dtos.UserDTO;
import br.com.sevencomm.cobranca.domain.models.User;

import java.util.List;

public interface IUserService {
    void deleteUser(Integer id);

    User getCurrentUser();
    UserDTO insertUser(User user);
    UserDTO getUserById(Integer id);

    List<UserDTO>listUsers();
    List<User> getUsersByArea(Integer id);
}
