package br.com.sevencomm.cobranca.domain.services;

import br.com.sevencomm.cobranca.domain.interfaces.ICobrancaService;
import br.com.sevencomm.cobranca.domain.models.Cobranca;
import br.com.sevencomm.cobranca.data.repositories.CobrancaRepository;
import br.com.sevencomm.cobranca.data.repositories.StatusRepository;
import br.com.sevencomm.cobranca.data.repositories.UserRepository;
import br.com.sevencomm.cobranca.domain.models.User;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CobrancaService implements ICobrancaService {

    @Autowired
    private CobrancaRepository cobrancaInternaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    public Cobranca getCobranca(Integer cobrancaId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<Cobranca> fndCobranca = cobrancaInternaRepository.findById(cobrancaId);

        if (!fndCobranca.isPresent()) throw new IllegalArgumentException("Cobranca not found");

        if (!(fndCobranca.get().getBeneficiarioAreaId().equals(user.getAreaId())) && !(fndCobranca.get().getPagadorAreaId().equals(user.getAreaId())))
            throw new IllegalArgumentException("User not allowed");

        return fndCobranca.get();
    }

    public List<Cobranca> listCobrancas() {
        return cobrancaInternaRepository.findAll();
    }

    public List<Cobranca> listCobrancas(Integer statusId) {
        return cobrancaInternaRepository.findAllByPagadorAreaId(statusId);
    }

    public List<Cobranca> listCobrancasEnviadas(Integer statusId) {
        return null;
    }

    public List<Cobranca> listCobrancasRecebidas(Integer statusId) {
        return null;
    }

    public Cobranca update(Cobranca cobrancaInterna, Integer id) {
        Optional<Cobranca> optAux = cobrancaInternaRepository.findById(id);

        if (optAux.isPresent()) {
            Cobranca ciAux = optAux.get();

            ciAux.setBeneficiarioAreaId(cobrancaInterna.getBeneficiarioAreaId());
            ciAux.setPagadorAreaId(cobrancaInterna.getPagadorAreaId());
            ciAux.setDatahora(cobrancaInterna.getDatahora());
            ciAux.setDescricao(cobrancaInterna.getDescricao());
            ciAux.setPagadorAreaId(cobrancaInterna.getPagadorAreaId());
            ciAux.setValor(cobrancaInterna.getValor());

            return cobrancaInternaRepository.save(ciAux);
        } else {
            throw new IllegalArgumentException("Cobrança not found");
        }
    }

    public List<Cobranca> getAllByBeneficiarioAreaId(Integer id){
        return cobrancaInternaRepository.findAllByBeneficiarioAreaId(id);
    }

    public List<Cobranca> getAllByStatusId(Integer id){
        return cobrancaInternaRepository.findAllByStatusId(id);
    }

    public void deleteCobranca(Integer id) {
        cobrancaInternaRepository.deleteById(id);
    }

    public Cobranca insertCobranca(Cobranca cobrancaInterna) {
        if (cobrancaInterna.getPagadorAreaId() != cobrancaInterna.getBeneficiarioAreaId()
                && statusRepository.findById(cobrancaInterna.getStatusId()).isPresent()
                && userRepository.findById(cobrancaInterna.getPagadorAreaId()).isPresent()
                && userRepository.findById(cobrancaInterna.getBeneficiarioAreaId()).isPresent()){
            Assert.isNull(cobrancaInterna.getId(), "Não foi possivel inserir registro");
            return cobrancaInternaRepository.save(cobrancaInterna);
        }
        else{
            throw new IllegalArgumentException();
        }
    }
}
