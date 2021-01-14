/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.velocity.sistema.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Leandro Laurindo
 */
@ManagedBean
@SessionScoped
public class RodapeBean implements Serializable{

    String rodape = "";

    @PostConstruct
    public void init() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        rodape =" 2020 - " + simpleDateFormat.format(new Date()) +" VELOCITY RENT CAR - TODOS DIREITOS RESERVADOS. ";
        rodape += "Desenvolvido por:  ELM Soluções.";
    }

    public String getRodape() {
        return rodape;
    }

    public void setRodape(String rodape) {
        this.rodape = rodape;
    }
    
}
