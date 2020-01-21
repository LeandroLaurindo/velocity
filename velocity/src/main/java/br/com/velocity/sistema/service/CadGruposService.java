/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadGruposDAO;
import br.com.velocity.sistema.entidades.CadGrupos;
import br.com.velocity.sistema.managers.SimpleEntityManager;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadGruposService {

    private CadGruposDAO dao;

    private SimpleEntityManager simpleEntityManager;

    public CadGruposService(SimpleEntityManager simpleEntityManager) {
        this.simpleEntityManager = simpleEntityManager;
        dao = new CadGruposDAO(simpleEntityManager.getEntityManager());
    }

    public void save(CadGrupos cadGrupos) {
        try {
            simpleEntityManager.beginTransaction();
            dao.save(cadGrupos);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void update(CadGrupos cadGrupos) {
        try {
            simpleEntityManager.beginTransaction();
            dao.getById(cadGrupos.getIdGrupo());
            dao.update(cadGrupos);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void delete(CadGrupos cadGrupos) {
        try {
            simpleEntityManager.beginTransaction();
            CadGrupos u = dao.getById(cadGrupos.getIdGrupo());
            dao.delete(u);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public CadGrupos carregar(Integer id){
        return dao.getById(id);
    }
    
    public List<CadGrupos> findAll() {
        return dao.findAll();
    }
    
    public List<CadGrupos> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
