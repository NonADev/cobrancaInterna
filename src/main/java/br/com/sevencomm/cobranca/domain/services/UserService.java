package br.com.sevencomm.cobranca.domain.services;

import br.com.sevencomm.cobranca.application.dtos.UserDTO;
import br.com.sevencomm.cobranca.application.configs.exception.ObjectNotFoundException;
import br.com.sevencomm.cobranca.domain.models.User;
import br.com.sevencomm.cobranca.data.repositories.AreaRepository;
import br.com.sevencomm.cobranca.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AreaRepository areaRepository;

    public UserDTO getUserById(Integer id){
        return userRepository.findById(id).map(UserDTO::create).orElseThrow(()->new ObjectNotFoundException("User not found"));
    }

    public List<User> getUsersByArea(Integer id){
        return userRepository.findAllByAreaId(id);
    }

    public void delete(Integer id){
        userRepository.deleteById(id);
    }

    public UserDTO insert(User user){
        if(areaRepository.findById(user.getAreaId()).isPresent()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            return UserDTO.create(userRepository.save(user));
        }
        else{
            throw new ObjectNotFoundException("Area not found");
        }
    }

    public List<UserDTO> getUsers(){
        return userRepository.findAll().stream().map(UserDTO::create).collect(Collectors.toList());
    }

}
