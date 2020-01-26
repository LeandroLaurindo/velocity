/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadFuncionariosDAO;
import br.com.velocity.sistema.entidades.CadFuncionarios;
import br.com.velocity.sistema.managers.SimpleEntityManager;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadFuncionariosService implements Serializable {

    private CadFuncionariosDAO dao;

    private SimpleEntityManager simpleEntityManager;

    public CadFuncionariosService(SimpleEntityManager simpleEntityManager) {
        this.simpleEntityManager = simpleEntityManager;
        dao = new CadFuncionariosDAO(simpleEntityManager.getEntityManager());
    }

    public void save(CadFuncionarios funcionarios) {
        try {
            simpleEntityManager.beginTransaction();
            dao.save(funcionarios);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void update(CadFuncionarios funcionarios) {
        try {
            simpleEntityManager.beginTransaction();
            dao.getById(funcionarios.getIdFuncionario());
            dao.update(funcionarios);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void delete(CadFuncionarios funcionarios) {
        try {
            simpleEntityManager.beginTransaction();
            CadFuncionarios u = dao.getById(funcionarios.getIdFuncionario());
            dao.delete(u);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public CadFuncionarios carregar(Integer id){
        return dao.getById(id);
    }
    
    public List<CadFuncionarios> findAll() {
        return dao.findAll();
    }
    
    public List<CadFuncionarios> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
