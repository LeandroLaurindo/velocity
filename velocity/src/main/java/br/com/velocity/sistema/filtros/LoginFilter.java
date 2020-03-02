/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.filtros;

import br.com.velocity.sistema.entidades.Usuario;
import br.com.velocity.sistema.util.SessionUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 *
 * @author Leandro Laurindo
 */
@WebFilter(urlPatterns = "/", servletNames = "{Faces Servlet}")
public class LoginFilter extends AbstractFilter implements Filter {

    @Override
    public void init(FilterConfig fc) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) sr;
        HttpServletResponse response = (HttpServletResponse) sr1;

        HttpSession session = request.getSession();
        Usuario user = (Usuario) session.getAttribute("user");
        if (session.isNew() || user == null) {
            doLogin(sr, sr1, request);
        } else {
            fc.doFilter(sr, sr1);
        }
    }

    @Override
    public void destroy() {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
