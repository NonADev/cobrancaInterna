package br.com.sevencomm.cobranca.domain.interfaces;

import br.com.sevencomm.cobranca.domain.models.Cobranca;

import java.util.List;

public interface ICobrancaService {
    Cobranca getCobranca(Integer cobrancaId);
    List<Cobranca> listCobrancas();
    List<Cobranca> listCobrancas(Integer statusId );
    void deleteCobranca(Integer id);
    Cobranca insertCobranca(Cobranca cobrancaInterna);
    List<Cobranca> listCobrancasEnviadas(Integer statusId);
    List<Cobranca> listCobrancasRecebidas(Integer statusId);
}
