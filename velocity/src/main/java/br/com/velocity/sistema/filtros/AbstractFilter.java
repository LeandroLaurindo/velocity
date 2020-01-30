/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.filtros;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Leandro Laurindo
 */
public class AbstractFilter {
    public AbstractFilter() {
        super();
    }

    protected void doLogin(ServletRequest request, ServletResponse response, HttpServletRequest req) throws ServletException, IOException {
         req.getContextPath();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/login.xhtml");
        dispatcher.forward(request, response);
    }

    protected void accessDenied(ServletRequest request, ServletResponse response, HttpServletRequest req) throws ServletException, IOException {
         req.getContextPath();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/pages/accessNegado.xhtml");
        dispatcher.forward(request, response);
    }
}
