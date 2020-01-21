/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.dto;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Leandro Laurindo
 */
@ManagedBean
@ViewScoped
public class EstadosDTO {

    private String sigla;
    private String descricao;
    private List<EstadosDTO> lista = new ArrayList<>();

    public EstadosDTO() {
        listar();
    }

    public void listar() {
        this.lista.add(new EstadosDTO("AC", "Acre"));
        this.lista.add(new EstadosDTO("AL", "Alagoas"));
        this.lista.add(new EstadosDTO("AP", "Amapá"));
        this.lista.add(new EstadosDTO("AM", "Amazonas"));
        this.lista.add(new EstadosDTO("BA", "Bahia"));
        this.lista.add(new EstadosDTO("CE", "Ceará"));
        this.lista.add(new EstadosDTO("DF", "Distrito Federal"));
        this.lista.add(new EstadosDTO("ES", "Espírito Santo"));
        this.lista.add(new EstadosDTO("GO", "Goiás"));
        this.lista.add(new EstadosDTO("MA", "Maranhão"));
        this.lista.add(new EstadosDTO("MT", "Mato Grosso"));
        this.lista.add(new EstadosDTO("MS", "Mato Grosso do Sul"));
        this.lista.add(new EstadosDTO("MG", "Minas Gerais"));
        this.lista.add(new EstadosDTO("PA", "Pará"));
        this.lista.add(new EstadosDTO("PB", "Paraíba"));
        this.lista.add(new EstadosDTO("PR", "Paraná"));
        this.lista.add(new EstadosDTO("PE", "Pernambuco"));
        this.lista.add(new EstadosDTO("PI", "Piauí"));
        this.lista.add(new EstadosDTO("RJ", "Rio de Janeiro"));
        this.lista.add(new EstadosDTO("RN", "Rio Grande do Norte"));
        this.lista.add(new EstadosDTO("RS", "Rio Grande do Sul"));
        this.lista.add(new EstadosDTO("RO", "Rondônia"));
        this.lista.add(new EstadosDTO("RR", "Roraima"));
        this.lista.add(new EstadosDTO("SC", "Santa Catarina"));
        this.lista.add(new EstadosDTO("SP", "São Paulo"));
        this.lista.add(new EstadosDTO("TO", "Tocantins"));
      
    }

    public EstadosDTO(String sigla, String descricao) {
        this.sigla = sigla;
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<EstadosDTO> getLista() {
        return lista;
    }

    public void setLista(List<EstadosDTO> lista) {
        this.lista = lista;
    }
  
}
