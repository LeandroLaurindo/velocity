/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadOcorrenciasDAO;
import br.com.velocity.sistema.entidades.CadOcorrencias;
import br.com.velocity.sistema.managers.SimpleEntityManager;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadOcorrenciasService implements Serializable {

    private CadOcorrenciasDAO dao;

    private SimpleEntityManager simpleEntityManager;

    public CadOcorrenciasService(SimpleEntityManager simpleEntityManager) {
        this.simpleEntityManager = simpleEntityManager;
        dao = new CadOcorrenciasDAO(simpleEntityManager.getEntityManager());
    }

    public void save(CadOcorrencias cadOcorrencias) {
        try {
            simpleEntityManager.beginTransaction();
            dao.save(cadOcorrencias);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void update(CadOcorrencias cadOcorrencias) {
        try {
            simpleEntityManager.beginTransaction();
            dao.getById(cadOcorrencias.getIdOcorrencia());
            dao.update(cadOcorrencias);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void delete(CadOcorrencias cadOcorrencias) {
        try {
            simpleEntityManager.beginTransaction();
            CadOcorrencias u = dao.getById(cadOcorrencias.getIdOcorrencia());
            dao.delete(u);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public CadOcorrencias carregar(Integer id){
        return dao.getById(id);
    }
    
    public List<CadOcorrencias> findAll() {
        return dao.findAll();
    }
    
    public List<CadOcorrencias> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
