/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadModeloVeiculoDAO;
import br.com.velocity.sistema.entidades.CadModeloVeiculo;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadModeloVeiculoService implements Serializable {

    private CadModeloVeiculoDAO dao;

    public CadModeloVeiculoService() {
        dao = new CadModeloVeiculoDAO();
    }

    public void save(CadModeloVeiculo modeloVeiculo) {
            dao.save(modeloVeiculo);
    }

    public void update(CadModeloVeiculo modeloVeiculo) {
            dao.getById(modeloVeiculo.getIdModelo());
            dao.update(modeloVeiculo);
    }

    public void delete(CadModeloVeiculo modeloVeiculo) {
            CadModeloVeiculo u = dao.getById(modeloVeiculo.getIdModelo());
            dao.delete(u);
    }

    public CadModeloVeiculo carregar(Integer id){
        return dao.getById(id);
    }
    
    public List<CadModeloVeiculo> findAll() {
        return dao.findAll();
    }
    
    public List<CadModeloVeiculo> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
