/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadPessoaDAO;
import br.com.velocity.sistema.entidades.CadPessoa;
import br.com.velocity.sistema.managers.SimpleEntityManager;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadPessoaService implements Serializable {

    private CadPessoaDAO dao;

    private SimpleEntityManager simpleEntityManager;

    public CadPessoaService(SimpleEntityManager simpleEntityManager) {
        this.simpleEntityManager = simpleEntityManager;
        dao = new CadPessoaDAO(simpleEntityManager.getEntityManager());
    }

    public void save(CadPessoa pessoa) {
        try {
            simpleEntityManager.beginTransaction();
            dao.save(pessoa);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void update(CadPessoa pessoa) {
        try {
            simpleEntityManager.beginTransaction();
            dao.getById(pessoa.getIdPessoa());
            dao.update(pessoa);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }
    public void update(Integer id) {
        try {
            simpleEntityManager.beginTransaction();
           CadPessoa pessoa = dao.getById(id);
           pessoa.setDataAlteracao(new Date());
           pessoa.setSituacao("NAO");
            dao.update(pessoa);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void delete(CadPessoa pessoa) {
        try {
            simpleEntityManager.beginTransaction();
            CadPessoa u = dao.getById(pessoa.getIdPessoa());
            dao.delete(u);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public CadPessoa carregar(Integer id){
        return dao.getById(id);
    }
    
    public CadPessoa carregar(String paramtendo){
        return dao.getByParametro(paramtendo);
    }
    
    public List<CadPessoa> findAll() {
        return dao.findAll();
    }
    
    public List<CadPessoa> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
