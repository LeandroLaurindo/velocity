/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadEnderecoDAO;
import br.com.velocity.sistema.entidades.CadEndereco;
import br.com.velocity.sistema.managers.SimpleEntityManager;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadEnderecoService implements Serializable {

    private CadEnderecoDAO dao;

    private SimpleEntityManager simpleEntityManager;

    public CadEnderecoService(SimpleEntityManager simpleEntityManager) {
        this.simpleEntityManager = simpleEntityManager;
        dao = new CadEnderecoDAO(simpleEntityManager.getEntityManager());
    }

    public void save(CadEndereco endereco) {
        try {
            simpleEntityManager.beginTransaction();
            dao.save(endereco);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void update(CadEndereco endereco) {
        try {
            simpleEntityManager.beginTransaction();
            dao.getById(endereco.getIdEndereco());
            dao.update(endereco);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void delete(CadEndereco endereco) {
        try {
            simpleEntityManager.beginTransaction();
            CadEndereco u = dao.getById(endereco.getIdEndereco());
            dao.delete(u);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public CadEndereco carregar(Integer id){
        return dao.getById(id);
    }
    
     public CadEndereco carregar(String parametro){
        return dao.getByParametro(parametro);
    }
    
    public List<CadEndereco> findAll() {
        return dao.findAll();
    }
    
    public List<CadEndereco> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
