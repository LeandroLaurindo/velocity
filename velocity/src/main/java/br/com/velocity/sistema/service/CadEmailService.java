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
import javax.inject.Inject;

/**
 *
 * @author Leandro Laurindo
 */
public class CadEmailService implements Serializable {
   
    
    private CadEmailDAO dao;

    public CadEmailService() {
        dao = new CadEmailDAO();
    }

    public void save(CadEmail email) {
        dao.save(email);
    }

    public void update(CadEmail email) {
        dao.getById(email.getIdEmail());
        dao.update(email);
    }

    public void delete(CadEmail email) {
        CadEmail u = dao.getById(email.getIdEmail());
        dao.delete(u);

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
