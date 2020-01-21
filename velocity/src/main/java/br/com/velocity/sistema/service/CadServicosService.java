/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadServicosDAO;
import br.com.velocity.sistema.entidades.CadServicos;
import br.com.velocity.sistema.managers.SimpleEntityManager;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadServicosService {

    private CadServicosDAO dao;

    private SimpleEntityManager simpleEntityManager;

    public CadServicosService(SimpleEntityManager simpleEntityManager) {
        this.simpleEntityManager = simpleEntityManager;
        dao = new CadServicosDAO(simpleEntityManager.getEntityManager());
    }

    public void save(CadServicos cadServico) {
        try {
            simpleEntityManager.beginTransaction();
            dao.save(cadServico);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void update(CadServicos cadServico) {
        try {
            simpleEntityManager.beginTransaction();
            dao.getById(cadServico.getIdServico());
            dao.update(cadServico);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void delete(CadServicos cadServico) {
        try {
            simpleEntityManager.beginTransaction();
            CadServicos u = dao.getById(cadServico.getIdServico());
            dao.delete(u);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public CadServicos carregar(Integer id){
        return dao.getById(id);
    }
    
    public List<CadServicos> findAll() {
        return dao.findAll();
    }
    
    public List<CadServicos> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
