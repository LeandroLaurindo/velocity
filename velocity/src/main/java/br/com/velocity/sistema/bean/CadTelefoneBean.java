/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.bean;

import br.com.velocity.sistema.entidades.CadCliente;
import br.com.velocity.sistema.entidades.CadDocumentos;
import br.com.velocity.sistema.entidades.CadTelefone;
import br.com.velocity.sistema.service.CadDocumentosService;
import br.com.velocity.sistema.service.CadTelefoneService;
import br.com.velocity.sistema.service.UsuarioService;
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
@ManagedBean(name = "cadTelefoneBean")
@ViewScoped
public class CadTelefoneBean implements Serializable {

    private CadDocumentosService documentosService;

    private CadTelefoneService enderecoService;

    private MessagesView msg;

    private CadTelefone cadTelefone;

    private List<CadTelefone> listaTelefones;

    private List<CadDocumentos> listaDocumentos;

    private List<CadCliente> listaClientes;

    private CadDocumentos documentos;

    private Integer idDocumento;

    @PostConstruct
    public void init() {
        this.cadTelefone = new CadTelefone();
        this.listaTelefones = new ArrayList<>();
        this.listaClientes = new ArrayList<>();
        this.documentos = new CadDocumentos();
        this.listaTelefones = new ArrayList<>();
        this.documentosService = new CadDocumentosService();
        this.enderecoService = new CadTelefoneService();
        this.msg = new MessagesView();
        try {
            String pessoa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
            if (null != pessoa) {
                this.documentos = this.documentosService.carregar("WHERE c.idDocumentos=" + pessoa + " c.documentoFk.pessoaFk.situacao ='SIM'");
                setarTelefones();

            } else {
                this.listaDocumentos = this.documentosService.findAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        listarDados();
    }

    public void setarTelefones() {
        Util.executarAcao("PF('dlgtele').show()");
        Util.updateComponente("frmdadosH");
    }

    public void setaTelefones() {
        Util.executarAcao("PF('dlgtele2').show()");
        Util.updateComponente("frmdadosH2");
    }

    public String retornoWhatsapp(Boolean tem) {
        if (tem != null) {
            if (tem) {
                return "Sim";
            } else {
                return "NAO";
            }
        } else {
            return "SEM STATUS";
        }
    }

    public void setarDadosEditar(CadTelefone ch) {
        this.cadTelefone = ch;
        this.documentos = ch.getDocumentoFk();
        Util.executarAcao("PF('dlgtele').show()");
        Util.updateComponente("frmdadosH");
    }

    public void setarDadosRemover(CadTelefone ch) {
        this.cadTelefone = ch;
        // this.documentos = ch.getDocumentoFk();
        Util.executarAcao("PF('removerTele').show()");
        //Util.updateComponente("frmdadosH");
    }

    public void listarDados() {
        this.listaTelefones = this.enderecoService.findAll();
    }

    public void salvar() {
        try {
            this.cadTelefone.setDocumentoFk(documentos);
            this.cadTelefone.setDataInsercao(new Date());
            this.cadTelefone.setDataAlteracao(new Date());
            this.cadTelefone.setUsuarioFk(new UsuarioService().carregar(3));
            this.enderecoService.save(cadTelefone);
            listarDados();
            this.msg.info("Telefone inserido com sucesso.");
            Util.executarAcao("PF('dlgtele').hide()");
            Util.updateComponente("formTelefone");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void salvarN() {
        try {
            this.documentos = this.documentosService.carregar(idDocumento);
            this.cadTelefone.setDocumentoFk(documentos);
            this.cadTelefone.setDataInsercao(new Date());
            this.cadTelefone.setDataAlteracao(new Date());
            this.cadTelefone.setUsuarioFk(new UsuarioService().carregar(3));
            this.enderecoService.save(cadTelefone);
            listarDados();
            this.msg.info("Telefone inserido com sucesso.");
            Util.executarAcao("PF('dlgtele2').hide()");
            Util.updateComponente("formTelefone");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editar() {
        try {
            this.cadTelefone.setDocumentoFk(documentos);
            this.cadTelefone.setDataAlteracao(new Date());
            this.cadTelefone.setUsuarioFk(new UsuarioService().carregar(3));
            this.enderecoService.update(cadTelefone);
            this.msg.info("Endereco editado com sucesso.");
            listarDados();
            Util.executarAcao("PF('dlgtele').hide()");
            Util.updateComponente("formEndereco");
        } catch (Exception e) {
            this.msg.info("Não foi  possivel editar endereco.");
        }
    }

    public void editarN() {
        try {
            this.documentos = this.documentosService.carregar(idDocumento);
            this.cadTelefone.setDocumentoFk(documentos);
            this.cadTelefone.setDataAlteracao(new Date());
            this.cadTelefone.setUsuarioFk(new UsuarioService().carregar(3));
            this.enderecoService.update(cadTelefone);
            this.msg.info("Endereco editado com sucesso.");
            listarDados();
            Util.executarAcao("PF('dlgtele').hide()");
            Util.updateComponente("formEndereco");
        } catch (Exception e) {
            this.msg.info("Não foi  possivel editar endereco.");
        }
    }

    public void remover() {
        try {
            this.enderecoService.delete(cadTelefone);
            this.msg.info("Endereco removido com sucesso.");
            listarDados();
            Util.executarAcao("PF('removerTele').hide()");
            Util.updateComponente("formEndereco");
        } catch (Exception e) {
            this.msg.info("Não foi  possivel remover endereco.");
        }
    }

    public void novo() {
        this.cadTelefone = new CadTelefone();
        this.documentos = new CadDocumentos();
        this.listaDocumentos = this.documentosService.findAll();
        Util.executarAcao("PF('dlgtele2').show()");
        Util.updateComponente("frmdadosH2");
    }

    public CadDocumentosService getDocumentosService() {
        return documentosService;
    }

    public void setDocumentosService(CadDocumentosService documentosService) {
        this.documentosService = documentosService;
    }

    public CadTelefoneService getEnderecoService() {
        return enderecoService;
    }

    public void setEnderecoService(CadTelefoneService enderecoService) {
        this.enderecoService = enderecoService;
    }

    public MessagesView getMsg() {
        return msg;
    }

    public void setMsg(MessagesView msg) {
        this.msg = msg;
    }

    public CadTelefone getCadTelefone() {
        return cadTelefone;
    }

    public void setCadTelefone(CadTelefone cadTelefone) {
        this.cadTelefone = cadTelefone;
    }

    public List<CadTelefone> getListaTelefones() {
        return listaTelefones;
    }

    public void setListaTelefones(List<CadTelefone> listaTelefones) {
        this.listaTelefones = listaTelefones;
    }

    public List<CadDocumentos> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(List<CadDocumentos> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
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

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

}
