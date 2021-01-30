/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.velocity.sistema.bean;

import br.com.velocity.sistema.entidades.CadMulta;
import br.com.velocity.sistema.service.CadModeloVeiculoService;
import br.com.velocity.sistema.service.CadMultaService;
import br.com.velocity.sistema.service.ControleVeiculosService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Leandro Laurindo
 */
public class CadMultaBeam implements Serializable{
    
    private CadMulta cadMulta;
    private CadMultaService multaService;
    private CadModeloVeiculoService veiculoService;
    private ControleVeiculosService controleVeiculosService;
    private List<String> listarPlacasVeiculos;
    private List<CadMulta> listaCadMultas;

    @PostConstruct
    public void init(){
        this.multaService =  new CadMultaService();
        this.cadMulta = new CadMulta();
        this.listarPlacasVeiculos = new ArrayList<>();
        this.listaCadMultas = new ArrayList<>();
        this.veiculoService = new CadModeloVeiculoService();
        this.controleVeiculosService = new ControleVeiculosService();
        this.listarPlacasVeiculos = this.veiculoService.findAll("WHERE c.placa"," ORDER BY c.placa ASC");
        this.listaCadMultas = this.multaService.findAll("WHERE c.siatuacao='ABERTA'");
    }
    
    
    public void listarPlaca(){
        
    }

    public CadMulta getCadMulta() {
        return cadMulta;
    }

    public void setCadMulta(CadMulta cadMulta) {
        this.cadMulta = cadMulta;
    }

    public List<String> getListarPlacasVeiculos() {
        return listarPlacasVeiculos;
    }

    public void setListarPlacasVeiculos(List<String> listarPlacasVeiculos) {
        this.listarPlacasVeiculos = listarPlacasVeiculos;
    }

    public List<CadMulta> getListaCadMultas() {
        return listaCadMultas;
    }

    public void setListaCadMultas(List<CadMulta> listaCadMultas) {
        this.listaCadMultas = listaCadMultas;
    }
    
    
}
