package br.com.sevencomm.cobranca.domain.models;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    public Role(){

    }

    public Role(String nome){
        this.nome = nome;
    }

    public Role(Integer id, String nome){
        this.id =  id;
        this.nome = nome;
    }

    @Override
    public String getAuthority() {
        return nome;
    }
}
