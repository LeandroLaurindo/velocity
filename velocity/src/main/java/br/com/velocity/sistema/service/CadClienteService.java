/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadClienteDAO;
import br.com.velocity.sistema.entidades.CadCliente;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadClienteService implements Serializable {
   
  
    private CadClienteDAO dao;

    public CadClienteService() {
        dao = new CadClienteDAO();
   }
    public void save(CadCliente cliente) {
        dao.save(cliente);

    }

    public void update(CadCliente cliente) {
        dao.getById(cliente.getIdCliente());
        dao.update(cliente);
    }

    public void delete(CadCliente cliente) {
        CadCliente u = dao.getById(cliente.getIdCliente());
        dao.delete(u);
    }

    public CadCliente carregar(Integer id) {
        return dao.getById(id);
    }

    public CadCliente carregar(String paramentro) {
        return dao.getByParametro(paramentro);
    }

    public List<CadCliente> findAll() {
        return dao.findAll();
    }

    public List<CadCliente> findAll(String parametros) {
        return dao.findAll(parametros);
    }

    public CadClienteDAO getDao() {
        return dao;
    }

    public void setDao(CadClienteDAO dao) {
        this.dao = dao;
    }
  
}
