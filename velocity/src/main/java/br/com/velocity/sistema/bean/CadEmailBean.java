/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.bean;

import br.com.velocity.sistema.entidades.CadCliente;
import br.com.velocity.sistema.entidades.CadDocumentos;
import br.com.velocity.sistema.entidades.CadEmail;
import br.com.velocity.sistema.entidades.Usuario;
import br.com.velocity.sistema.service.CadClienteService;
import br.com.velocity.sistema.service.CadDocumentosService;
import br.com.velocity.sistema.service.CadEmailService;
import br.com.velocity.sistema.util.MessagesView;
import br.com.velocity.sistema.util.SessionUtil;
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
@ManagedBean(name = "cadEmailBean")
@ViewScoped
public class CadEmailBean implements Serializable {

    private CadEmailService cadEmailService = new CadEmailService();

    private CadClienteService clienteService = new CadClienteService();

    private CadDocumentosService documentosService = new CadDocumentosService();

    private MessagesView msg = new MessagesView();

    private CadEmail cadEmail;

    private List<CadEmail> listaEmails;

    private List<CadCliente> listaClientes;

    private CadDocumentos documentos;

    private List<CadDocumentos> listaDocumentos;

    private Integer idDocumento;

    @PostConstruct
    public void init() {
        this.cadEmail = new CadEmail();
        this.listaEmails = new ArrayList<>();
        this.listaClientes = new ArrayList<>();
        this.documentos = new CadDocumentos();
        this.listaDocumentos = new ArrayList<>();
        try {
            String pessoa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
            if (null != pessoa) {
                this.documentos = this.documentosService.carregar("WHERE c.idDocumentos=" + pessoa + "");
                setarDocumentos();

            } else {
                this.listaDocumentos = this.documentosService.findAll();
            }
        } catch (Exception e) {
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

    public void setarDadosEditar(CadEmail ch) {
        this.cadEmail = ch;
        this.documentos = ch.getDocumentoFk();
        Util.executarAcao("PF('dlghab').show()");
        Util.updateComponente("frmdadosH");
    }

    public void setarDadosRemover(CadEmail ch) {
        this.cadEmail = ch;
        // this.documentos = ch.getDocumentoFk();
        Util.executarAcao("PF('removerHab').show()");
        //Util.updateComponente("frmdadosH");
    }

    public void listarDados() {
        this.listaEmails = this.cadEmailService.findAll();
    }

    public void salvar() {
                  
        try {
            if (cadEmail.getEmail().contains("@") || cadEmail.getEmail().contains(".")) {
                if (cadEmail.getIdEmail() == null) {
                    if(documentos.getIdDocumentos() == null){
                    this.cadEmail.setDocumentoFk(this.documentosService.carregar(idDocumento));
                    }else{
                    this.cadEmail.setDocumentoFk(documentos);
                    }
                    this.cadEmail.setDataAlteracao(new Date());
                    this.cadEmail.setDataInsercao(new Date());
                    this.cadEmail.setUsuarioFk(SessionUtil.getUser());
                    this.cadEmailService.save(cadEmail);
                    listarDados();
                    this.msg.info("Email inserido com sucesso.");
                    Util.executarAcao("PF('dlghab').hide()");
                    Util.updateComponente("formHabilitacao");
                } else {
                    editar();
                }
            } else {
                this.msg.info("Email informado  e invalido.");
            }
        } catch (Exception e) {
            this.msg.info("Não foi  possivel salvar email.");
        }
    }

    public void editar() {
        try {
            this.cadEmail.setDocumentoFk(documentos);
            this.cadEmailService.update(cadEmail);
            this.msg.info("Email ediatado com sucesso.");
            Util.executarAcao("PF('dlghab').hide()");
            Util.updateComponente("formHabilitacao");

        } catch (Exception e) {
            this.msg.info("Não foi  possivel editar email.");
        }
    }

    public void remover() {
        try {
            this.cadEmailService.delete(cadEmail);
            listarDados();
            this.msg.info("Email removido com sucesso.");
            Util.executarAcao("PF('removerHab').hide()");
            Util.updateComponente("formHabilitacao");
        } catch (Exception e) {
            this.msg.info("Não foi  possivel remover email.");
        }
    }

    public void novo() {
        this.cadEmail = new CadEmail();
        this.documentos = new CadDocumentos();
        this.listaClientes = this.clienteService.findAll();

    }

    public CadEmailService getCadEmailService() {
        return cadEmailService;
    }

    public void setCadEmailService(CadEmailService cadEmailService) {
        this.cadEmailService = cadEmailService;
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

    public MessagesView getMsg() {
        return msg;
    }

    public void setMsg(MessagesView msg) {
        this.msg = msg;
    }

    public CadEmail getCadEmail() {
        return cadEmail;
    }

    public void setCadEmail(CadEmail cadEmail) {
        this.cadEmail = cadEmail;
    }

    public List<CadEmail> getListaEmails() {
        return listaEmails;
    }

    public void setListaEmails(List<CadEmail> listaEmails) {
        this.listaEmails = listaEmails;
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
