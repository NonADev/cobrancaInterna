package br.com.sevencomm.cobranca.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class CobrancaInterna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    int pagadorAreaId;
    int beneficiarioAreaId;
    Long statusId;
    String datahora; // ! colocar como datetime
    String descricao;
    Double valor;
}
