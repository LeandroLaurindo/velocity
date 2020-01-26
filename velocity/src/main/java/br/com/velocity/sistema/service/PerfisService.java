/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.PerfisDAO;
import br.com.velocity.sistema.entidades.Perfis;
import br.com.velocity.sistema.managers.SimpleEntityManager;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class PerfisService implements Serializable {

    private PerfisDAO dao;

    private SimpleEntityManager simpleEntityManager;

    public PerfisService(SimpleEntityManager simpleEntityManager) {
        this.simpleEntityManager = simpleEntityManager;
        dao = new PerfisDAO(simpleEntityManager.getEntityManager());
    }

    public void save(Perfis perfil) {
        try {
            simpleEntityManager.beginTransaction();
            dao.save(perfil);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void update(Perfis perfil) {
        try {
            simpleEntityManager.beginTransaction();
            dao.getById(perfil.getIdPerfil());
            dao.update(perfil);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void delete(Perfis perfil) {
        try {
            simpleEntityManager.beginTransaction();
            Perfis u = dao.getById(perfil.getIdPerfil());
            dao.delete(u);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public Perfis carregar(Integer id){
        return dao.getById(id);
    }
    
    public Perfis carregar(String parametro){
        return dao.getByParametro(parametro);
    }
    
    public List<Perfis> findAll() {
        return dao.findAll();
    }
    
    public List<Perfis> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
