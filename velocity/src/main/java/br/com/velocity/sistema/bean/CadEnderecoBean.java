/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.bean;

import br.com.velocity.sistema.entidades.CadCliente;
import br.com.velocity.sistema.entidades.CadDocumentos;
import br.com.velocity.sistema.entidades.CadEndereco;
import br.com.velocity.sistema.entidades.Usuario;
import br.com.velocity.sistema.managers.SimpleEntityManager;
import br.com.velocity.sistema.service.CadDocumentosService;
import br.com.velocity.sistema.service.CadEnderecoService;
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
@ManagedBean(name = "cadEnderecoBean")
@ViewScoped
public class CadEnderecoBean implements Serializable {

    private CadDocumentosService documentosService;

    private CadEnderecoService enderecoService;

    private MessagesView msg;

    private CadEndereco cadEndereco;

    private List<CadEndereco> listaEnderecos;

    private List<CadDocumentos> listaDocumentos;

    private List<CadCliente> listaClientes;

    private CadDocumentos documentos;

    private Integer idDocumento;

   
    @PostConstruct
    public void init() {
        this.cadEndereco = new CadEndereco();
        this.listaEnderecos = new ArrayList<>();
        this.listaClientes = new ArrayList<>();
        this.documentos = new CadDocumentos();
        this.listaEnderecos = new ArrayList<>();
        this.documentosService = new CadDocumentosService();
        this.enderecoService = new CadEnderecoService();
        this.msg = new MessagesView();
        try {
            String pessoa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
            if (null != pessoa) {
                this.documentos = this.documentosService.carregar("WHERE c.idDocumentos=" + pessoa + "");
                setarEnderecos();

            } else {
                this.listaDocumentos = this.documentosService.findAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        listarDados();
    }

    public void setarEnderecos() {
        Util.executarAcao("PF('dlghab').show()");
        Util.updateComponente("frmdadosH");
    }

    public void setaEnderecos() {
        Util.executarAcao("PF('dlghab2').show()");
        Util.updateComponente("frmdadosH2");
    }

    public void setarDadosEditar(CadEndereco ch) {
        this.cadEndereco = ch;
        this.documentos = ch.getDocumentoFk();
        Util.executarAcao("PF('dlghab').show()");
        Util.updateComponente("frmdadosH");
    }

    public void setarDadosRemover(CadEndereco ch) {
        this.cadEndereco = ch;
        // this.documentos = ch.getDocumentoFk();
        Util.executarAcao("PF('removerHab').show()");
        //Util.updateComponente("frmdadosH");
    }

    public void listarDados() {
        this.listaEnderecos = this.enderecoService.findAll();
    }

    public void salvar() {
        try {
            this.cadEndereco.setDocumentoFk(documentos);
            this.cadEndereco.setDataInsercao(new Date());
            this.cadEndereco.setDataAlteracao(new Date());
              this.cadEndereco.setUsuarioFk(new UsuarioService().carregar(3));
            this.enderecoService.save(cadEndereco);
            listarDados();
            this.msg.info("Endereco inserido com sucesso.");
            Util.executarAcao("PF('dlghab').hide()");
            Util.updateComponente("formEndereco");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void salvarN() {
        try {
            this.documentos = this.documentosService.carregar(idDocumento);
            this.cadEndereco.setDocumentoFk(documentos);
            this.cadEndereco.setDataInsercao(new Date());
            this.cadEndereco.setDataAlteracao(new Date());
            this.cadEndereco.setUsuarioFk(new UsuarioService().carregar(3));
            this.enderecoService.save(cadEndereco);
            listarDados();
            this.msg.info("Endereco inserido com sucesso.");
            Util.executarAcao("PF('dlghab2').hide()");
              Util.updateComponente("formEndereco");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editar() {
        try {
            this.cadEndereco.setDocumentoFk(documentos);
            this.cadEndereco.setDataAlteracao(new Date());
            this.cadEndereco.setUsuarioFk(new UsuarioService().carregar(3));
            this.enderecoService.update(cadEndereco);
            this.msg.info("Endereco editado com sucesso.");
            listarDados();
            Util.executarAcao("PF('dlghab').hide()");
            Util.updateComponente("formEndereco");
        } catch (Exception e) {
            this.msg.info("Não foi  possivel editar endereco.");
        }
    }

    public void editarN() {
        try {
            this.documentos = this.documentosService.carregar(idDocumento);
            this.cadEndereco.setDocumentoFk(documentos);
            this.cadEndereco.setDataAlteracao(new Date());
              this.cadEndereco.setUsuarioFk(new UsuarioService().carregar(3));
            this.enderecoService.update(cadEndereco);
            this.msg.info("Endereco editado com sucesso.");
            listarDados();
            Util.executarAcao("PF('dlghab').hide()");
            Util.updateComponente("formEndereco");
        } catch (Exception e) {
            this.msg.info("Não foi  possivel editar endereco.");
        }
    }

    public void remover() {
        try {
            this.enderecoService.delete(cadEndereco);
            this.msg.info("Endereco removido com sucesso.");
            listarDados();
            Util.executarAcao("PF('removerHab').hide()");
            Util.updateComponente("formEndereco");
        } catch (Exception e) {
            this.msg.info("Não foi  possivel remover endereco.");
        }
    }

    public void novo() {
        this.cadEndereco = new CadEndereco();
        this.documentos = new CadDocumentos();
        this.listaDocumentos = this.documentosService.findAll();
        Util.executarAcao("PF('dlghab2').show()");
        Util.updateComponente("frmdadosH2");
    }

    

    public CadDocumentosService getDocumentosService() {
        return documentosService;
    }

    public void setDocumentosService(CadDocumentosService documentosService) {
        this.documentosService = documentosService;
    }

    public CadEnderecoService getEnderecoService() {
        return enderecoService;
    }

    public void setEnderecoService(CadEnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    public MessagesView getMsg() {
        return msg;
    }

    public void setMsg(MessagesView msg) {
        this.msg = msg;
    }

    public CadEndereco getCadEndereco() {
        return cadEndereco;
    }

    public void setCadEndereco(CadEndereco cadEndereco) {
        this.cadEndereco = cadEndereco;
    }

    public List<CadEndereco> getListaEnderecos() {
        return listaEnderecos;
    }

    public void setListaEnderecos(List<CadEndereco> listaEnderecos) {
        this.listaEnderecos = listaEnderecos;
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
