/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadEmailDAO;
import br.com.velocity.sistema.entidades.CadEmail;
import br.com.velocity.sistema.managers.SimpleEntityManager;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadEmailService {

    private CadEmailDAO dao;

    private SimpleEntityManager simpleEntityManager;

    public CadEmailService(SimpleEntityManager simpleEntityManager) {
        this.simpleEntityManager = simpleEntityManager;
        dao = new CadEmailDAO(simpleEntityManager.getEntityManager());
    }

    public void save(CadEmail email) {
        try {
            simpleEntityManager.beginTransaction();
            dao.save(email);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void update(CadEmail email) {
        try {
            simpleEntityManager.beginTransaction();
            dao.getById(email.getIdEmail());
            dao.update(email);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void delete(CadEmail email) {
        try {
            simpleEntityManager.beginTransaction();
            CadEmail u = dao.getById(email.getIdEmail());
            dao.delete(u);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public CadEmail carregar(Integer id){
        return dao.getById(id);
    }
    
    public CadEmail carregar(String parametro){
        return dao.getByParametro(parametro);
    }
    
    public List<CadEmail> findAll() {
        return dao.findAll();
    }
    
    public List<CadEmail> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
