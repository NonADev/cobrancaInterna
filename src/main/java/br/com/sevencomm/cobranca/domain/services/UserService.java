package br.com.sevencomm.cobranca.domain.services;

import br.com.sevencomm.cobranca.application.dtos.UserDTO;
import br.com.sevencomm.cobranca.application.configs.exception.ObjectNotFoundException;
import br.com.sevencomm.cobranca.domain.interfaces.IUserService;
import br.com.sevencomm.cobranca.domain.models.Role;
import br.com.sevencomm.cobranca.domain.models.User;
import br.com.sevencomm.cobranca.data.repositories.AreaRepository;
import br.com.sevencomm.cobranca.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private UserRepository userRepository;

    private AreaRepository areaRepository;

    public UserService(UserRepository userRepository,AreaRepository areaRepository){
        this.userRepository = userRepository;
        this.areaRepository = areaRepository;
    }

    public UserDTO getUserById(Integer id) {
        return userRepository.findById(id).map(UserDTO::create).orElseThrow(() -> new ObjectNotFoundException("User not found"));
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<User> optUser = userRepository.findById(user.getId());

        if (!optUser.isPresent()) throw new ObjectNotFoundException("User not found");

        return optUser.get();
    }

    public List<User> getUsersByArea(Integer id) {
        return userRepository.findAllByAreaId(id);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public UserDTO insertUser(User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty())
            throw new IllegalArgumentException("Roles cannot be null");

        if (!areaRepository.findById(user.getAreaId()).isPresent())
            throw new IllegalArgumentException("Area not found");

        if (userRepository.findByLogin(user.getLogin()) != null)
            throw new IllegalArgumentException("Login already in use");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));


        user.setRoles(new ArrayList<>()); //quando implementar roles retirar essa parte
        return UserDTO.create(userRepository.save(user));
    }

    public List<UserDTO> listUsers() {
        return userRepository.findAll().stream().map(UserDTO::create).collect(Collectors.toList());
    }
}
