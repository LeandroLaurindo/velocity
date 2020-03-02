/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.bean;

import br.com.velocity.sistema.entidades.CadModeloVeiculo;
import br.com.velocity.sistema.service.CadModeloVeiculoService;
import br.com.velocity.sistema.util.Util;
import br.com.velocity.sistema.util.MessagesView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Leandro Laurindo
 */
@ManagedBean(name = "cadModeloVeiculoBean")
@ViewScoped
public class CadModeloVeiculoBean implements Serializable {

    private CadModeloVeiculoService veiculoService;
    private CadModeloVeiculo modeloVeiculo;
    private List<CadModeloVeiculo> listaDeMVeiculos;
    private MessagesView msg;

    @PostConstruct
    public void init() {
        this.veiculoService = new CadModeloVeiculoService();
        this.modeloVeiculo = new CadModeloVeiculo();
        this.listaDeMVeiculos = new ArrayList<>();
        this.msg = new MessagesView();
        listar();
    }

    public void salvar() {
        if (this.modeloVeiculo.getIdModelo() == null) {
            this.veiculoService.save(this.modeloVeiculo);
            this.msg.info("Salvo com sucesso.");
            listar();
            this.modeloVeiculo = new CadModeloVeiculo();
            Util.executarAcao("PF('dglVeiculo').hide()");
        } else {
            this.editar();
        }
    }

    public void editar() {
        this.veiculoService.update(this.modeloVeiculo);
        this.msg.info("Editado com sucesso.");
        listar();
        this.modeloVeiculo = new CadModeloVeiculo();
        Util.executarAcao("PF('dglVeiculo').hide()");
    }

    public void deletar() {
        this.veiculoService.delete(this.modeloVeiculo);
        this.msg.info("Removido com sucesso.");
        listar();
        this.modeloVeiculo = new CadModeloVeiculo();
    }

    public void limpar() {
        this.modeloVeiculo = new CadModeloVeiculo();
        Util.executarAcao("PF('dglVeiculo').show()");
        Util.updateComponente("formCdCMV");
    }

    public void cancelar() {
        this.modeloVeiculo = new CadModeloVeiculo();
        Util.executarAcao("PF('dglVeiculo').hide()");
    }

    public void setarDadosEditar(CadModeloVeiculo cmv) {
        this.modeloVeiculo = cmv;
        Util.executarAcao("PF('dglVeiculo').show()");
        Util.updateComponente("formCdCMV");
    }

    public void setarDadosDeletar(CadModeloVeiculo cmv) {
        this.modeloVeiculo = cmv;
        Util.updateComponente("forCmvConf");
        Util.executarAcao("PF('dlgConf').show()");
        
    }

    public void listar() {
        this.listaDeMVeiculos = this.veiculoService.findAll(" ORDER BY c.modelo");
        Util.updateComponente("formCMV");
    }

    public void urlImagem(Integer id) {
        String nome = String.valueOf(id);
        nome += "novaveiculo";
        Util.rediricionar("imagens/contoleImagensVeiculos.xhtml?id=" + nome);
    }
   
    public void urlEditaImagem(Integer id) {
        String nome = String.valueOf(id);
        nome += "veiculo";
        Util.rediricionar("imagens/contoleImagensVeiculos.xhtml?id=" + nome);
    }
    
    public CadModeloVeiculoService getVeiculoService() {
        return veiculoService;
    }

    public void setVeiculoService(CadModeloVeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    public CadModeloVeiculo getModeloVeiculo() {
        return modeloVeiculo;
    }

    public void setModeloVeiculo(CadModeloVeiculo modeloVeiculo) {
        this.modeloVeiculo = modeloVeiculo;
    }

    public List<CadModeloVeiculo> getListaDeMVeiculos() {
        return listaDeMVeiculos;
    }

    public void setListaDeMVeiculos(List<CadModeloVeiculo> listaDeMVeiculos) {
        this.listaDeMVeiculos = listaDeMVeiculos;
    }

}
