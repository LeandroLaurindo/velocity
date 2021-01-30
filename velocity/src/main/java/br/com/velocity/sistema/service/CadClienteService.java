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
    public boolean save(CadCliente cliente) {
       return dao.save(cliente);
    }

    public boolean update(CadCliente cliente) {
        try{
        dao.getById(cliente.getIdCliente());
       return dao.update(cliente);
        }catch(Throwable e){
            return false;
        }
    }

    public boolean delete(CadCliente cliente) {
        try{
        CadCliente u = dao.getById(cliente.getIdCliente());
        return dao.delete(u);
        }catch(Throwable e){
            return false;
        }
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
