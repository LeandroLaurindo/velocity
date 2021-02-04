/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.ControleVeiculosDAO;
import br.com.velocity.sistema.entidades.ControleVeiculos;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class ControleVeiculosService implements Serializable {

    private ControleVeiculosDAO dao;

    public ControleVeiculosService() {
        dao = new ControleVeiculosDAO();
    }

    public boolean save(ControleVeiculos cliente) {
        return dao.save(cliente);

    }

    public boolean update(ControleVeiculos cliente) {
        try {
            dao.getById(cliente.getIdControleVeiculo());
            return dao.update(cliente);
        } catch (Throwable e) {
            return false;
        }
    }

    public boolean delete(ControleVeiculos cliente) {
        try {
            ControleVeiculos u = dao.getById(cliente.getIdControleVeiculo());
            return dao.delete(u);
        } catch (Throwable e) {
            return false;
        }
    }

    public ControleVeiculos carregar(Integer id) {
        return dao.getById(id);
    }

    public ControleVeiculos carregar(String paramentro) {
        return dao.getByParametro(paramentro);
    }

    public List<ControleVeiculos> findAll() {
        return dao.findAll();
    }

    public List<ControleVeiculos> findAll(String parametros) {
        return dao.findAll(parametros);
    }

    public ControleVeiculosDAO getDao() {
        return dao;
    }

    public void setDao(ControleVeiculosDAO dao) {
        this.dao = dao;
    }

}
