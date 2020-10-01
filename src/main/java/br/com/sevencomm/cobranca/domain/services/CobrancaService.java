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

    public Cobranca getCobrancaById(Integer cobrancaId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<Cobranca> fndCobranca = cobrancaInternaRepository.findById(cobrancaId);

        if (!fndCobranca.isPresent()) throw new IllegalArgumentException("Cobranca not found");

        if (!(fndCobranca.get().getBeneficiarioAreaId().equals(user.getAreaId())) && !(fndCobranca.get().getPagadorAreaId().equals(user.getAreaId())))
            throw new IllegalArgumentException("User not allowed");

        return fndCobranca.get();
    }

    public void aprovarCobranca(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<Cobranca> fndCobranca = cobrancaInternaRepository.findById(id);

        if(!fndCobranca.isPresent()) throw new IllegalArgumentException("Cobranca not found");

        if(!(fndCobranca.get().getBeneficiarioAreaId().equals(user.getAreaId()))) throw new IllegalArgumentException("User not allowed");

        Cobranca cobranca = fndCobranca.get();
        cobranca.setStatusId(2);
        cobrancaInternaRepository.save(cobranca);
    }

    public void recusarCobranca(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<Cobranca> fndCobranca = cobrancaInternaRepository.findById(id);

        if(!fndCobranca.isPresent()) throw new IllegalArgumentException("Cobranca not found");

        if(!(fndCobranca.get().getBeneficiarioAreaId().equals(user.getAreaId()))) throw new IllegalArgumentException("User not allowed");

        Cobranca cobranca = fndCobranca.get();
        cobranca.setStatusId(3);
        cobrancaInternaRepository.save(cobranca);
    }

    public List<Cobranca> listCobrancas() {
        return cobrancaInternaRepository.findAll();
    }

    public List<Cobranca> listCobrancas(Integer areaId) {
        return cobrancaInternaRepository.findAllByPagadorAreaIdOrBeneficiarioAreaId(areaId);
    }

    public List<Cobranca> listCobrancasEnviadas(Integer areaId) {
        return cobrancaInternaRepository.findAllByPagadorAreaId(areaId);
    }

    public List<Cobranca> listCobrancasRecebidas(Integer areaId) {
        return cobrancaInternaRepository.findAllByBeneficiarioAreaId(areaId);
    }

    public Cobranca updateCobranca(Cobranca cobrancaInterna, Integer id) {
        Optional<Cobranca> optAux = cobrancaInternaRepository.findById(id);

        if (optAux.isPresent()) {
            Cobranca ciAux = optAux.get();

            ciAux.setStatusId(1); //caso o requisitor altere, o status volta pra 1
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

    public void deleteCobranca(Integer id) {
        cobrancaInternaRepository.deleteById(id);
    }

    public Cobranca insertCobranca(Cobranca cobrancaInterna) {
        if (cobrancaInterna.getPagadorAreaId() != cobrancaInterna.getBeneficiarioAreaId()
                && statusRepository.findById(cobrancaInterna.getStatusId()).isPresent()
                && userRepository.findById(cobrancaInterna.getPagadorAreaId()).isPresent()
                && userRepository.findById(cobrancaInterna.getBeneficiarioAreaId()).isPresent()){
            Assert.isNull(cobrancaInterna.getId(), "Não foi possivel inserir registro");
            cobrancaInterna.setStatusId(1);
            return cobrancaInternaRepository.save(cobrancaInterna);
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    public List<Cobranca> listAllByStatus(Integer status) {
        return cobrancaInternaRepository.findAllByStatusId(status);
    }
}
