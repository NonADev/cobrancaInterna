package br.com.sevencomm.cobranca.domain.interfaces;

import br.com.sevencomm.cobranca.domain.models.Status;

import java.util.List;

public interface IStatusService {
    void deleteStatus(Integer id);

    Status insertStatus(Status status);
    Status updateStatus(Status status, Integer id);
    Status getStatusById(Integer id);

    List<Status> listAll();

}
