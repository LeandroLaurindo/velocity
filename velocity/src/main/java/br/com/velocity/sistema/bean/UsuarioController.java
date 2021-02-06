/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.bean;

import br.com.velocity.sistema.entidades.Usuario;
import br.com.velocity.sistema.util.SessionUtil;
import static br.com.velocity.sistema.util.SessionUtil.getRequest;
import br.com.velocity.sistema.util.Util;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Leandro Laurindo
 */
@ManagedBean(name = "usuarioController")
@SessionScoped
public class UsuarioController implements Serializable{
    private Usuario user;

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
    
    public void logOut(){
        getRequest().getSession().invalidate();
        Util.rediricionar("login.xhtml");
    } 
    
    public String getUsuario(){
        try{
        return SessionUtil.getUser().getLogin();
        }catch(Throwable ex){
            return "login.xhtml?faces-redirect=true";
        }
    }
    
    public String getUsu(){    
        try{
        return String.valueOf(SessionUtil.getUser().getIdUsuario()) +"usuario";
        }catch(Throwable e){
            return null;
        }
    }
    
    public String admin(){
        return "/pages/home.xhtml?faces-redirect=true";
    } 
}
