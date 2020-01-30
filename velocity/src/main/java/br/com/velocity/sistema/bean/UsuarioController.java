/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.bean;

import br.com.velocity.sistema.entidades.Usuario;
import br.com.velocity.sistema.util.SessionUtil;
import static br.com.velocity.sistema.util.SessionUtil.getRequest;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Leandro Laurindo
 */
@ManagedBean(name = "usuarioController")
@SessionScoped
public class UsuarioController {
    private Usuario user;

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
    
    public String logOut(){
        getRequest().getSession().invalidate();
        System.out.println("br.com.velocity.sistema.bean.UsuarioController.logOut()");
        return "/login.xhtml";
    } 
    
    public String getUsuario(){
        return SessionUtil.getUser().getLogin();
    }
    public String admin(){
        return "/pages/home.xhtml?faces-redirect=true";
    } 
}
