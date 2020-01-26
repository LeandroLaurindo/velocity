/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadHabilitacaoDAO;
import br.com.velocity.sistema.entidades.CadHabilitacao;
import br.com.velocity.sistema.managers.SimpleEntityManager;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadHabilitacaoService implements Serializable {

    private CadHabilitacaoDAO dao;

    private SimpleEntityManager simpleEntityManager;

    public CadHabilitacaoService(SimpleEntityManager simpleEntityManager) {
        this.simpleEntityManager = simpleEntityManager;
        dao = new CadHabilitacaoDAO(simpleEntityManager.getEntityManager());
    }

    public void save(CadHabilitacao habilitacao) {
        try {
            simpleEntityManager.beginTransaction();
            dao.save(habilitacao);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void update(CadHabilitacao habilitacao) {
        try {
            simpleEntityManager.beginTransaction();
            dao.getById(habilitacao.getIdHabilitacao());
            dao.update(habilitacao);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void delete(CadHabilitacao habilitacao) {
        try {
            simpleEntityManager.beginTransaction();
            CadHabilitacao u = dao.getById(habilitacao.getIdHabilitacao());
            dao.delete(u);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public CadHabilitacao carregar(Integer id){
        return dao.getById(id);
    }
    
    public List<CadHabilitacao> findAll() {
        return dao.findAll();
    }
    
    public List<CadHabilitacao> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
