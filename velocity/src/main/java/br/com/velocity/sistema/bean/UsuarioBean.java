/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.bean;

import br.com.velocity.sistema.util.Util;
import br.com.velocity.sistema.util.MessagesView;
import br.com.velocity.sistema.entidades.Perfis;
import br.com.velocity.sistema.entidades.Usuario;
import br.com.velocity.sistema.service.CadGruposService;
import br.com.velocity.sistema.service.PerfisService;
import br.com.velocity.sistema.service.UsuarioService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Leandro Laurindo
 */
@ManagedBean(name = "usuario")
@ViewScoped
public class UsuarioBean implements Serializable {

    private Usuario usuario;
    private UsuarioService serviceUsuario;
    private PerfisService perfisService;
    private CadGruposService cadGruposService;
    private List<Usuario> lista;
    private List<Usuario> listaFiltrada;
    private MessagesView msg = new MessagesView();
    private String idPerfil;
    private String ativo = "";
    private List<Perfis> listaPefis = new ArrayList<>();

    @PostConstruct
    public void init() {
        this.usuario = new Usuario();
        this.serviceUsuario = new UsuarioService();
        this.lista = new ArrayList<>();
        this.listaFiltrada = new ArrayList<>();
        this.cadGruposService = new CadGruposService();
        this.perfisService = new PerfisService();
        this.perfis();
        this.listarUsuarios();
    }

    public void listarUsuarios() {
        this.lista = serviceUsuario.findAll();
    }

    public void setarDados(Usuario usu) {
        this.usuario = usu;
        if (usu.getAtivo()) {
            setAtivo("SIM");
        } else {
            setAtivo("NAO");
        }
        idPerfil = usu.getPerfilFk().getNomePerfil();
        Util.executarAcao("PF('dlgUsuario').show()");
        Util.updateComponente("forUsuDado");

    }

    public void setarDadosRemover(Usuario usu) {
        System.out.println("br.com.locadora.bean.UsuarioBean.setarDadosRemover()" + usu.getIdUsuario());
        this.usuario = usu;
        Util.executarAcao("PF('dlgConf').show()");
        Util.updateComponente("forUsuConf");

    }

    public void buscarPorLogin(String texto) {
        try {
            this.usuario = serviceUsuario.carregar("WHERE c.login = '" + texto + "'");
        } catch (Exception e) {
            this.msg.warn("Dados não encontrado!");
        }
    }

    public void limpar() {
        Util.executarAcao("PF('dlgUsuario').show()");
        this.lista = new ArrayList<>();
        this.usuario = new Usuario();
        this.idPerfil = null;
        Util.updateComponente("forUsuDado");
    }

    public void salvar() {
        try {
            System.out.println("-------------------------------------" + idPerfil);
            if (getAtivo().equalsIgnoreCase("SIM")) {
                this.usuario.setAtivo(true);
            } else {
                this.usuario.setAtivo(false);
            }
            Perfis perfis = this.perfisService.carregar("WHERE c.nomePerfil ='" + idPerfil + "'");
            this.usuario.setPerfilFk(perfis);
            this.serviceUsuario.save(this.usuario);
            this.lista.add(this.usuario);
            this.msg.info("Inserido com sucesso!");
            Util.updateComponente("databelaUsuario");
        } catch (Exception e) {
            this.msg.error("Não foi possivel inserir!");
        }

    }

    public void editar() {
     //   System.out.println("chamouoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
        try {
            if (getAtivo().equalsIgnoreCase("SIM")) {
                this.usuario.setAtivo(true);
            } else {
                this.usuario.setAtivo(false);
            }
            Perfis perfis = this.perfisService.carregar("WHERE c.nomePerfil ='" + idPerfil + "'");
            this.usuario.setPerfilFk(perfis);
            this.serviceUsuario.update(this.usuario);
            this.listarUsuarios();
            this.msg.info("Atualizado com sucesso!");
            Util.executarAcao("PF('dlgUsuario').hide()");
            Util.updateComponente("databelaUsuario");
            
        } catch (Exception e) {
            e.printStackTrace();
            this.msg.error("Não foi possivel atualizar");
        }
    }

    public void excluir(Integer id) {
        try {
            this.serviceUsuario.delete(id);
            this.lista.remove(this.usuario);
            this.msg.info("Removido com sucesso!");
            Util.updateComponente("databelaUsuario");
        } catch (Exception e) {
            e.printStackTrace();
            this.msg.error("Não foi possivel remover!");
        }
    }

    public void perfis() {
        this.listaPefis = this.perfisService.findAll();
    }

    public MessagesView getMsg() {
        return msg;
    }

    public void setMsg(MessagesView msg) {
        this.msg = msg;
    }

    public String getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(String idPerfil) {
        this.idPerfil = idPerfil;
    }
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public UsuarioService getServiceUsuario() {
        return serviceUsuario;
    }

    public void setServiceUsuario(UsuarioService serviceUsuario) {
        this.serviceUsuario = serviceUsuario;
    }

    public CadGruposService getCadGruposService() {
        return cadGruposService;
    }

    public void setCadGruposService(CadGruposService cadGruposService) {
        this.cadGruposService = cadGruposService;
    }

    public List<Usuario> getLista() {
        return lista;
    }

    public void setLista(List<Usuario> lista) {
        this.lista = lista;
    }

    public PerfisService getPerfisService() {
        return perfisService;
    }

    public void setPerfisService(PerfisService perfisService) {
        this.perfisService = perfisService;
    }

    public List<Perfis> getListaPefis() {
        return listaPefis;
    }

    public void setListaPefis(List<Perfis> listaPefis) {
        this.listaPefis = listaPefis;
    }

    public List<Usuario> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(List<Usuario> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

}
