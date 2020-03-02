/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Leandro Laurindo
 */
public class Util {

    public Util() {
    }

    /**
     *
     * @param id
     */
    public static void updateComponente(String id) {
        org.primefaces.PrimeFaces.current().ajax().update(id);
    }

    /**
     *
     * @param id
     */
    public static void executarAcao(String id) {
        org.primefaces.PrimeFaces.current().executeScript(id);
    }

    public static void rediricionar(String destino) {
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            if (destino.contains("login") || destino.contains("accessNegado")) {
                context.redirect(context.getRequestContextPath() + "/" + destino + "");
            } else {
                context.redirect(context.getRequestContextPath() + "/pages/" + destino + "");
            }
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static EntityManager JpaEntityManager() {

        FacesContext facesContext = FacesContext.getCurrentInstance();

        ExternalContext externalContext = facesContext.getExternalContext();

        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        return (EntityManager) request.getAttribute("entityManager");
    }

    public static String hashPassword(String senha) {

        return BCrypt.hashpw(senha, BCrypt.gensalt());

    }

    public static boolean checkPass(String senha, String hashedPassword) {
        return BCrypt.checkpw(senha, hashedPassword);
    }
}
