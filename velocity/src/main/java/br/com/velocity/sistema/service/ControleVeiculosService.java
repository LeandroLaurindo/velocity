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
    public void save(ControleVeiculos cliente) {
        dao.save(cliente);

    }

    public void update(ControleVeiculos cliente) {
        dao.getById(cliente.getIdControleVeiculo());
        dao.update(cliente);
    }

    public void delete(ControleVeiculos cliente) {
        ControleVeiculos u = dao.getById(cliente.getIdControleVeiculo());
        dao.delete(u);
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
