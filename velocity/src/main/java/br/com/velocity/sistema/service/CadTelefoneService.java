/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadTelefoneDAO;
import br.com.velocity.sistema.entidades.CadTelefone;
import br.com.velocity.sistema.managers.SimpleEntityManager;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadTelefoneService implements Serializable {

    private CadTelefoneDAO dao;

    private SimpleEntityManager simpleEntityManager;

    public CadTelefoneService(SimpleEntityManager simpleEntityManager) {
        this.simpleEntityManager = simpleEntityManager;
        dao = new CadTelefoneDAO(simpleEntityManager.getEntityManager());
    }

    public void save(CadTelefone telefone) {
        try {
            simpleEntityManager.beginTransaction();
            dao.save(telefone);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void update(CadTelefone telefone) {
        try {
            simpleEntityManager.beginTransaction();
            dao.getById(telefone.getIdTelefone());
            dao.update(telefone);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void delete(CadTelefone telefone) {
        try {
            simpleEntityManager.beginTransaction();
            CadTelefone u = dao.getById(telefone.getIdTelefone());
            dao.delete(u);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public CadTelefone carregar(Integer id){
        return dao.getById(id);
    }
    public CadTelefone carregar(String parametro){
        return dao.getByParametro(parametro);
    }
    
    public List<CadTelefone> findAll() {
        return dao.findAll();
    }
    
    public List<CadTelefone> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
