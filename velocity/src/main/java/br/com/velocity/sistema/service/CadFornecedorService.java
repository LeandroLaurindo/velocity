/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadFornecedorDAO;
import br.com.velocity.sistema.entidades.CadFornecedor;
import br.com.velocity.sistema.managers.SimpleEntityManager;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadFornecedorService implements Serializable {

    private CadFornecedorDAO dao;

    private SimpleEntityManager simpleEntityManager;

    public CadFornecedorService(SimpleEntityManager simpleEntityManager) {
        this.simpleEntityManager = simpleEntityManager;
        dao = new CadFornecedorDAO(simpleEntityManager.getEntityManager());
    }

    public void save(CadFornecedor fornecedor) {
        try {
            simpleEntityManager.beginTransaction();
            dao.save(fornecedor);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void update(CadFornecedor fornecedor) {
        try {
            simpleEntityManager.beginTransaction();
            dao.getById(fornecedor.getIdFornecedor());
            dao.update(fornecedor);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void delete(CadFornecedor fornecedor) {
        try {
            simpleEntityManager.beginTransaction();
            CadFornecedor u = dao.getById(fornecedor.getIdFornecedor());
            dao.delete(u);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public CadFornecedor carregar(Integer id){
        return dao.getById(id);
    }
    
    public List<CadFornecedor> findAll() {
        return dao.findAll();
    }
    
    public List<CadFornecedor> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
