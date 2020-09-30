package br.com.sevencomm.cobranca.services;

import br.com.sevencomm.cobranca.models.Area;
import br.com.sevencomm.cobranca.repositories.AreaRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AreaService {
    @Autowired
    private AreaRepository areaRepository;

    public List<Area> getAll() {
        return areaRepository.findAll();    //.stream().map(AreaDTO::create).collect(Collectors.toList()); //quando adicionar DTOs
    }

    public List<Area> getByName(String nome){
        return areaRepository.findByNomeLike("%"+nome+"%");
    }

    public Optional<Area> getById(Long id) {
        return areaRepository.findById(id); //.orElseThrow(() -> new ObjectNotFoundException("Area not found")); // ! esse null e tratamento do elseThrow
    }

    public Area insert(Area area) {
        Assert.isNull(area.getId(), "Não foi possivel inserir registro");
        return areaRepository.save(area);
    }

    public Area update(Area area, Long id) {
        Optional<Area> optionalArea = getById(id);

        if (optionalArea.isPresent()) {
            Area aux = optionalArea.get();

            aux.setNome(area.getNome());

            return areaRepository.save(aux);
        }
        else {
            return null;
        }
    }

    public void delete(Long id){
        areaRepository.deleteById(id);
    }
}
