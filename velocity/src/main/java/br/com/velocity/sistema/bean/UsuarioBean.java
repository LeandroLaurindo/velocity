/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.bean;

import br.com.velocity.sistema.entidades.Perfis;
import br.com.velocity.sistema.entidades.Usuario;
import br.com.velocity.sistema.service.CadGruposService;
import br.com.velocity.sistema.service.PerfisService;
import br.com.velocity.sistema.service.UsuarioService;
import br.com.velocity.sistema.util.MessagesView;
import br.com.velocity.sistema.util.Util;
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
        this.lista = serviceUsuario.findAll(" ORDER BY c.login");
        
    }

    public void setarDados(Usuario usu) {
        usu.setSenha("");
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
        if(usuario.getIdUsuario() == null){
        try {
            if (getAtivo().equalsIgnoreCase("SIM")) {
                this.usuario.setAtivo(true);
            } else {
                this.usuario.setAtivo(false);
            }
            Perfis perfis = this.perfisService.carregar("WHERE c.nomePerfil ='" + idPerfil + "'");
            this.usuario.setPerfilFk(perfis);
            this.usuario.setSenha(Util.hashPassword(this.usuario.getSenha()));
            this.usuario.setLogin(transformar(this.usuario.getLogin()));
            this.serviceUsuario.save(this.usuario);
            listarUsuarios();
            this.msg.info("Inserido com sucesso!");
            Util.executarAcao("PF('dlgUsuario').hide()");
            Util.updateComponente("databelaUsuario");
        } catch (Exception e) {
            e.printStackTrace();
            this.msg.error("Não foi possivel inserir!");
        }
        }else{
            editar();
        }
    }
    
    public String transformar(String texto){
        if(texto != null){
            if(!texto.isEmpty()){
            return texto.toUpperCase();
            }
        }
        return "";
    }

    public void editar() {
        try {
            if (getAtivo().equalsIgnoreCase("SIM")) {
                this.usuario.setAtivo(true);
            } else {
                this.usuario.setAtivo(false);
            }
            Perfis perfis = this.perfisService.carregar("WHERE c.nomePerfil ='" + idPerfil + "'");
            this.usuario.setPerfilFk(perfis);
            if(!usuario.getSenha().isEmpty()){
            this.usuario.setSenha(Util.hashPassword(this.usuario.getSenha()));
            }else{
             Usuario u = serviceUsuario.carregar(" WHERE c.idUsuario=" +usuario.getIdUsuario()+"");
             this.usuario.setSenha(u.getSenha());
            }
            this.usuario.setLogin(transformar(this.usuario.getLogin()));
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

    public void excluir() {
        try {
            this.serviceUsuario.delete(this.usuario.getIdUsuario());
            listarUsuarios();
            this.msg.info("Removido com sucesso!");
            Util.updateComponente("databelaUsuario");
        } catch (Exception e) {
            e.printStackTrace();
            this.msg.error("Não foi possivel remover!");
        }
    }
   public void urlImagem() {
        Util.rediricionar("imagens/contoleImagensUsuarios.xhtml");
    }
   
    public void urlEditaImagem(Integer id) {
        String nome = String.valueOf(id);
        nome += "usuario";
        Util.rediricionar("imagens/contoleImagensUsuarios.xhtml?id=" + nome);
    }
    
    public void voltarParaHome(){
        System.out.println("Chamou");
        Util.rediricionar("home.xhtml");
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
