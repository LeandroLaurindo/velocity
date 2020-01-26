/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadClienteDAO;
import br.com.velocity.sistema.entidades.CadCliente;
import br.com.velocity.sistema.managers.SimpleEntityManager;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadClienteService implements Serializable {

    private CadClienteDAO dao;

    private SimpleEntityManager simpleEntityManager;

    public CadClienteService(SimpleEntityManager simpleEntityManager) {
        this.simpleEntityManager = simpleEntityManager;
        dao = new CadClienteDAO(simpleEntityManager.getEntityManager());
    }

    public void save(CadCliente cliente) {
        try {
            simpleEntityManager.beginTransaction();
            dao.save(cliente);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void update(CadCliente cliente) {
        try {
            simpleEntityManager.beginTransaction();
            dao.getById(cliente.getIdCliente());
            dao.update(cliente);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void delete(CadCliente cliente) {
        try {
            simpleEntityManager.beginTransaction();
            CadCliente u = dao.getById(cliente.getIdCliente());
            dao.delete(u);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public CadCliente carregar(Integer id){
        return dao.getById(id);
    }
    
    public CadCliente carregar(String paramentro){
        return dao.getByParametro(paramentro);
    }
    
    public List<CadCliente> findAll() {
        return dao.findAll();
    }
    
    public List<CadCliente> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
