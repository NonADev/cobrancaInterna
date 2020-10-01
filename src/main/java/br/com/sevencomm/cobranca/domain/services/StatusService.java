package br.com.sevencomm.cobranca.domain.services;

import br.com.sevencomm.cobranca.domain.models.Status;
import br.com.sevencomm.cobranca.data.repositories.StatusRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {
    @Autowired
    private StatusRepository statusRepository;

    public List<Status> getAll() {
        return statusRepository.findAll();    //.stream().map(StatusDTO::create).collect(Collectors.toList()); //quando adicionar DTOs
    }

    public Optional<Status> getById(Integer id) {
        return statusRepository.findById(id); //.orElseThrow(() -> new ObjectNotFoundException("Status not found")); // ! esse null e tratamento do elseThrow
    }

    public Status insert(Status status) {
        Assert.isNull(status.getId(), "Não foi possivel inserir registro");
        return statusRepository.save(status);
    }

    public Status update(Status status, Integer id) {
        Optional<Status> optionalStatus = getById(id);

        if (optionalStatus.isPresent()) {
            Status aux = optionalStatus.get();

            aux.setDescricao(status.getDescricao());

            return statusRepository.save(aux);
        }
        else {
            return null;
        }
    }

    public void delete(Integer id){
        statusRepository.deleteById(id);
    }
}