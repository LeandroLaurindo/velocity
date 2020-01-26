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
    public static void updateComponente(String id){
        org.primefaces.context.RequestContext.getCurrentInstance().update(id);
    }
    /**
     * 
     * @param id 
     */
    public static void executarAcao(String id){
        org.primefaces.context.RequestContext.getCurrentInstance().execute(id);
    }
    
    public static void rediricionar(String destino){
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            if(destino.contains("index") || destino.contains("home")){
              context.redirect(context.getRequestContextPath() +"/"+ destino+"");  
            }else{
            context.redirect(context.getRequestContextPath() +"/pages/"+ destino+"");
            }
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
