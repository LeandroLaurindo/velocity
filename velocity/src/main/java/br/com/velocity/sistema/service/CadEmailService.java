/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadEmailDAO;
import br.com.velocity.sistema.entidades.CadEmail;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadEmailService implements Serializable {

    private CadEmailDAO dao;

    public CadEmailService() {
        dao = new CadEmailDAO();
    }

    public boolean save(CadEmail email) {
        return dao.save(email);
    }

    public boolean update(CadEmail email) {
        try {
            dao.getById(email.getIdEmail());
            return dao.update(email);
        } catch (Throwable e) {
            return false;
        }

    }

    public boolean delete(CadEmail email) {
        try{
        CadEmail u = dao.getById(email.getIdEmail());
        return dao.delete(u);
        } catch (Throwable e) {
            return false;
        }
    }

    public CadEmail carregar(Integer id) {
        return dao.getById(id);
    }

    public CadEmail carregar(String parametro) {
        return dao.getByParametro(parametro);
    }

    public List<CadEmail> findAll() {
        return dao.findAll();
    }

    public List<CadEmail> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
