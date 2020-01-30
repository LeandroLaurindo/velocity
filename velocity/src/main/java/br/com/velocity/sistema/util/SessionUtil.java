/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.util;

import br.com.velocity.sistema.entidades.Usuario;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Leandro Laurindo
 */
public class SessionUtil {
    public static HttpSession getSession(){
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }
    
    public static HttpServletRequest getRequest(){
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }
    
    public static String getUserName(){
        return ((Usuario)getSession().getAttribute("user")).getLogin();
    }
    
    public static Usuario getUser(){
        return ((Usuario)getSession().getAttribute("user"));
    }
}
