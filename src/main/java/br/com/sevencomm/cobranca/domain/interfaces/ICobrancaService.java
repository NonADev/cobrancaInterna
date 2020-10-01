package br.com.sevencomm.cobranca.domain.interfaces;

import br.com.sevencomm.cobranca.domain.models.Cobranca;

import java.util.List;

public interface ICobrancaService {
    void deleteCobranca(Integer id);

    void aprovarCobranca(Integer id);
    void recusarCobranca(Integer id);

    Cobranca getCobrancaById(Integer cobrancaId);
    Cobranca insertCobranca(Cobranca cobrancaInterna);
    Cobranca updateCobranca(Cobranca cobrancaInterna, Integer id);

    List<Cobranca> listCobrancas();
    List<Cobranca> listCobrancas(Integer areaId);
    List<Cobranca> listAllByStatus(Integer status);
    List<Cobranca> listCobrancasEnviadas(Integer statusId); //pagador
    List<Cobranca> listCobrancasRecebidas(Integer statusId); //recebeneficiario
}
