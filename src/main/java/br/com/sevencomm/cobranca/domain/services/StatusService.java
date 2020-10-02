package br.com.sevencomm.cobranca.domain.services;

import br.com.sevencomm.cobranca.application.configs.exception.ObjectNotFoundException;
import br.com.sevencomm.cobranca.domain.interfaces.IStatusService;
import br.com.sevencomm.cobranca.domain.models.Area;
import br.com.sevencomm.cobranca.domain.models.Status;
import br.com.sevencomm.cobranca.data.repositories.StatusRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService implements IStatusService {

    private StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository){
        this.statusRepository = statusRepository;
    }

    public List<Status> listAll() {
        return statusRepository.findAll();    //.stream().map(StatusDTO::create).collect(Collectors.toList()); //quando adicionar DTOs
    }

    public Status getStatusById(Integer id) {
        Optional<Status> optStatus = statusRepository.findById(id);

        if (!optStatus.isPresent()) throw new ObjectNotFoundException("Area not found");

        return optStatus.get();
    }

    public Status insertStatus(Status status) {
        Assert.isNull(status.getId(), "Não foi possivel inserir registro, não deve possuir id");

        if (status.getDescricao().length() <= 3) throw new IllegalArgumentException("Minimum description size 4");

        return statusRepository.save(status);
    }

    public Status updateStatus(Status status, Integer id) {
        Optional<Status> fndStatus = statusRepository.findById(id);

        if (!fndStatus.isPresent()) throw new ObjectNotFoundException("Status not found");

        if (status.getDescricao() == null || status.getDescricao().equals(""))
            throw new IllegalArgumentException("Descrição invalida");

        if (status.getDescricao().length() <= 3) throw new IllegalArgumentException("Minimum description size 4");

        Status auxStatus = fndStatus.get();

        auxStatus.setDescricao(status.getDescricao());

        return statusRepository.save(auxStatus);
    }

    public void deleteStatus(Integer id) {
        statusRepository.deleteById(id);
    }
}
