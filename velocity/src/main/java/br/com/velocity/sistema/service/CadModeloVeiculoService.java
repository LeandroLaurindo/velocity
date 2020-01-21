/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadModeloVeiculoDAO;
import br.com.velocity.sistema.entidades.CadModeloVeiculo;
import br.com.velocity.sistema.managers.SimpleEntityManager;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadModeloVeiculoService {

    private CadModeloVeiculoDAO dao;

    private SimpleEntityManager simpleEntityManager;

    public CadModeloVeiculoService(SimpleEntityManager simpleEntityManager) {
        this.simpleEntityManager = simpleEntityManager;
        dao = new CadModeloVeiculoDAO(simpleEntityManager.getEntityManager());
    }

    public void save(CadModeloVeiculo modeloVeiculo) {
        try {
            simpleEntityManager.beginTransaction();
            dao.save(modeloVeiculo);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void update(CadModeloVeiculo modeloVeiculo) {
        try {
            simpleEntityManager.beginTransaction();
            dao.getById(modeloVeiculo.getIdModelo());
            dao.update(modeloVeiculo);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void delete(CadModeloVeiculo modeloVeiculo) {
        try {
            simpleEntityManager.beginTransaction();
            CadModeloVeiculo u = dao.getById(modeloVeiculo.getIdModelo());
            dao.delete(u);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
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
