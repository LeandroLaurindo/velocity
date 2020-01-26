/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadVendedorDAO;
import br.com.velocity.sistema.entidades.CadVendedor;
import br.com.velocity.sistema.managers.SimpleEntityManager;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadVendedorService implements Serializable {

    private CadVendedorDAO dao;

    private SimpleEntityManager simpleEntityManager;

    public CadVendedorService(SimpleEntityManager simpleEntityManager) {
        this.simpleEntityManager = simpleEntityManager;
        dao = new CadVendedorDAO(simpleEntityManager.getEntityManager());
    }

    public void save(CadVendedor vendedor) {
        try {
            simpleEntityManager.beginTransaction();
            dao.save(vendedor);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void update(CadVendedor vendedor) {
        try {
            simpleEntityManager.beginTransaction();
            dao.getById(vendedor.getIdVendedor());
            dao.update(vendedor);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void delete(CadVendedor vendedor) {
        try {
            simpleEntityManager.beginTransaction();
            CadVendedor u = dao.getById(vendedor.getIdVendedor());
            dao.delete(u);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public CadVendedor carregar(Integer id){
        return dao.getById(id);
    }
    
    public List<CadVendedor> findAll() {
        return dao.findAll();
    }
    
    public List<CadVendedor> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
