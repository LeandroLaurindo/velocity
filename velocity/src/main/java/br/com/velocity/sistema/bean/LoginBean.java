/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.bean;

import br.com.velocity.sistema.entidades.Usuario;
import br.com.velocity.sistema.service.UsuarioService;
import br.com.velocity.sistema.util.MessagesView;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Leandro Laurindo
 */
@RequestScoped
@ManagedBean
public class LoginBean {
    private String login;
    private String password;
    private UsuarioController usuarioController = new UsuarioController();
    private final MessagesView mv = new MessagesView();

    public void setUsuarioController(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private Usuario isValidLogin(String login, String password) {
         Usuario user;
        
            user = new UsuarioService().carregar(" WHERE c.login= '"+login+"'");
        
        if (user == null || !password.equals(user.getSenha())) {
            return null;
        }
        return user;
    }
    
    
    public String entrar() {
        Usuario user = isValidLogin(login, password);

        if (user != null) {
            usuarioController.setUser(user);
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            request.getSession().setAttribute("user", user);
            return "/pages/home.xhtml";
        }
        
        mv.warn("Check your login/password");
        return null;
    }

}
