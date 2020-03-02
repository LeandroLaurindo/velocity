/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.filtros;

import br.com.velocity.sistema.entidades.Usuario;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Leandro Laurindo
 */
@WebFilter(urlPatterns = {"/pages/*"}, servletNames = "{Faces Servlet}")
public class FiltroProtecao extends AbstractFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) sr;

        Usuario user = (Usuario) request.getSession(true).getAttribute("user");
       try{
        if(user.getIdUsuario() == null){
            doLogin(sr, sr1, request);
        }
       
         
        if (user.getPerfilFk().getIdPerfil() <= 3) {
             
           // String viewId = FacesContext.getCurrentInstance().getViewRoot().getId();
           if(!request.getRequestURI().contains("contato") && user.getPerfilFk().getIdPerfil() == 1){
                
            accessDenied(sr, sr1, request);
             return;
            }
           
           
            if(request.getRequestURI().contains("usuarios")){
                
            accessDenied(sr, sr1, request);
             return;
            } 
        }
        }catch(Exception ex){
            doLogin(sr, sr1, request);
       }
        fc.doFilter(sr, sr1);

    }

    @Override
    public void destroy() {

    }

}
