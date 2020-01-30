/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.bean;

import br.com.velocity.sistema.entidades.CadCliente;
import br.com.velocity.sistema.entidades.CadDocumentos;
import br.com.velocity.sistema.entidades.CadHabilitacao;
import br.com.velocity.sistema.managers.SimpleEntityManager;
import br.com.velocity.sistema.service.CadClienteService;
import br.com.velocity.sistema.service.CadDocumentosService;
import br.com.velocity.sistema.service.CadHabilitacaoService;
import br.com.velocity.sistema.util.MessagesView;
import br.com.velocity.sistema.util.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Leandro Laurindo
 */
@ManagedBean(name = "cadHabilitacaoBean")
@ViewScoped
public class CadHabilitacaoBean implements Serializable {

    private SimpleEntityManager manager = new SimpleEntityManager("locadoraPU");

    private CadHabilitacaoService habilitacaoService = new CadHabilitacaoService();
    
    private CadClienteService  clienteService = new CadClienteService();
    
    private CadDocumentosService documentosService =  new CadDocumentosService();

    private MessagesView msg = new MessagesView();

    private CadHabilitacao cadHabilitacao;

    private List<CadHabilitacao> listaHabilitacoes;

    private List<CadCliente> listaClientes;

    private CadDocumentos documentos;
    
    private List<CadDocumentos> listaDocumentos;
    
    private Integer idDocumento;

    @PostConstruct
    public void init() {
        this.cadHabilitacao = new CadHabilitacao();
        this.listaHabilitacoes = new ArrayList<>();
        this.listaClientes = new ArrayList<>();
        this.documentos = new CadDocumentos();
        this.listaDocumentos = new ArrayList<>();
        try{
       String pessoa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
            if(null != pessoa){
           this.documentos = this.documentosService.carregar("WHERE c.idDocumentos="+ pessoa+"");
            setarDocumentos();
            
            }else{
              this.listaDocumentos = this.documentosService.findAll();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        listarDados();
    }

    public void setarDocumentos() {   
        Util.executarAcao("PF('dlghab').show()");
        Util.updateComponente("frmdadosH");
    }
    
     public void setaDocumentos() {   
        Util.executarAcao("PF('dlghab2').show()");
        Util.updateComponente("frmdadosH2");
    }

    public void setarDadosEditar(CadHabilitacao ch) {
        this.cadHabilitacao = ch;
        this.documentos = ch.getDocumentoFk();
        Util.executarAcao("PF('dlghab').show()");
        Util.updateComponente("frmdadosH");
    }

    public void setarDadosRemover(CadHabilitacao ch) {
        this.cadHabilitacao = ch;
        // this.documentos = ch.getDocumentoFk();
        Util.executarAcao("PF('removerHab').show()");
        //Util.updateComponente("frmdadosH");
    }

    public void listarDados() {
        this.listaHabilitacoes = this.habilitacaoService.findAll();
    }

    public void salvar() {
        Date atual = new Date();
        try {
            if (cadHabilitacao.getValidade().getTime() > atual.getTime()) {
                this.cadHabilitacao.setDocumentoFk(documentos);
                this.habilitacaoService.save(cadHabilitacao);
                listarDados();
                this.msg.info("Habilitação inserida com sucesso.");
            } else {
                this.msg.info("Habilitação vencida.");
            }
            Util.executarAcao("PF('dlghab').hide()");
            Util.updateComponente("formHabilitacao");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void salvarN() {
        Date atual = new Date();
        try {
            if (cadHabilitacao.getValidade().getTime() > atual.getTime()) {
                
                this.documentos = this.documentosService.carregar(idDocumento);
                this.cadHabilitacao.setDocumentoFk(documentos);
                this.habilitacaoService.save(cadHabilitacao);
                listarDados();
                this.msg.info("Habilitação inserida com sucesso.");
            } else {
                this.msg.info("Habilitação vencida.");
            }
            Util.executarAcao("PF('dlghab2').hide()");
            Util.updateComponente("formHabilitacao");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editar() {
        try {
            Date atual = new Date();
            if (cadHabilitacao.getValidade().getTime() > atual.getTime()) {
                this.cadHabilitacao.setDocumentoFk(documentos);
                this.habilitacaoService.update(cadHabilitacao);
                this.msg.info("Habilitação editada com sucesso.");
                Util.executarAcao("PF('dlghab').hide()");
             Util.updateComponente("formHabilitacao");
            } else {
                this.msg.info("Habilitação vencida.");
            }
            
        } catch (Exception e) {
            this.msg.info("Não foi  possivel editar habilitação.");
        }
    }
    
    public void editarN() {
        try {
            Date atual = new Date();
            if (cadHabilitacao.getValidade().getTime() > atual.getTime()) {
                this.documentos = this.documentosService.carregar(idDocumento);
                this.cadHabilitacao.setDocumentoFk(documentos);
                this.habilitacaoService.update(cadHabilitacao);
                this.msg.info("Habilitação editada com sucesso.");
                Util.executarAcao("PF('dlghab').hide()");
             Util.updateComponente("formHabilitacao");
            } else {
                this.msg.info("Habilitação vencida.");
            }
            
        } catch (Exception e) {
            this.msg.info("Não foi  possivel editar habilitação.");
        }
    }

    public void remover() {
        try {
            this.habilitacaoService.delete(cadHabilitacao);
            this.msg.info("Habilitação removida com sucesso.");
            Util.executarAcao("PF('removerHab').hide()");
            Util.updateComponente("formHabilitacao");
        } catch (Exception e) {
            this.msg.info("Não foi  possivel remover habilitação.");
        }
    }
    
    
    public void novo(){
        this.cadHabilitacao = new CadHabilitacao();
        this.documentos = new CadDocumentos();
        this.listaClientes = this.clienteService.findAll();
        
    }

    public SimpleEntityManager getManager() {
        return manager;
    }

    public void setManager(SimpleEntityManager manager) {
        this.manager = manager;
    }

    public CadHabilitacaoService getHabilitacaoService() {
        return habilitacaoService;
    }

    public void setHabilitacaoService(CadHabilitacaoService habilitacaoService) {
        this.habilitacaoService = habilitacaoService;
    }

    public MessagesView getMsg() {
        return msg;
    }

    public void setMsg(MessagesView msg) {
        this.msg = msg;
    }

    public CadHabilitacao getCadHabilitacao() {
        return cadHabilitacao;
    }

    public void setCadHabilitacao(CadHabilitacao cadHabilitacao) {
        this.cadHabilitacao = cadHabilitacao;
    }

    public List<CadHabilitacao> getListaHabilitacoes() {
        return listaHabilitacoes;
    }

    public void setListaHabilitacoes(List<CadHabilitacao> listaHabilitacoes) {
        this.listaHabilitacoes = listaHabilitacoes;
    }

    public List<CadCliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<CadCliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public CadDocumentos getDocumentos() {
        return documentos;
    }

    public void setDocumentos(CadDocumentos documentos) {
        this.documentos = documentos;
    }

    public CadClienteService getClienteService() {
        return clienteService;
    }

    public void setClienteService(CadClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public CadDocumentosService getDocumentosService() {
        return documentosService;
    }

    public void setDocumentosService(CadDocumentosService documentosService) {
        this.documentosService = documentosService;
    }

    public List<CadDocumentos> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(List<CadDocumentos> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }
  
}
