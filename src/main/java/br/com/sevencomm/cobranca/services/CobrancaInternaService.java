package br.com.sevencomm.cobranca.services;

import br.com.sevencomm.cobranca.models.CobrancaInterna;
import br.com.sevencomm.cobranca.repositories.CobrancaInternaRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CobrancaInternaService {
    @Autowired
    private CobrancaInternaRepository cobrancaInternaRepository;

    public List<CobrancaInterna> getAll() {
        return cobrancaInternaRepository.findAll();
    }

    public Optional<CobrancaInterna> getById(Long id) {
        return cobrancaInternaRepository.findById(id);
    }

    public CobrancaInterna update(CobrancaInterna cobrancaInterna, Long id) {
        Optional<CobrancaInterna> optAux = cobrancaInternaRepository.findById(id);

        if (optAux.isPresent()) {
            CobrancaInterna ciAux = optAux.get();

            ciAux.setBeneficiarioAreaId(cobrancaInterna.getBeneficiarioAreaId());
            ciAux.setPagadorAreaId(cobrancaInterna.getPagadorAreaId());
            ciAux.setDatahora(cobrancaInterna.getDatahora());
            ciAux.setDescricao(cobrancaInterna.getDescricao());
            ciAux.setPagadorAreaId(cobrancaInterna.getPagadorAreaId());
            ciAux.setValor(cobrancaInterna.getValor());

            return cobrancaInternaRepository.save(ciAux);
        }
        else {
            return null;
        }
    }

    public void delete(Long id) {
        cobrancaInternaRepository.deleteById(id);
    }

    public CobrancaInterna insert(CobrancaInterna cobrancaInterna) {
        Assert.isNull(cobrancaInterna.getId(), "Não foi possivel inserir registro");
        return cobrancaInternaRepository.save(cobrancaInterna);
    }
}
