/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadVendedorDAO;
import br.com.velocity.sistema.entidades.CadVendedor;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadVendedorService implements Serializable {

    private CadVendedorDAO dao;

    public CadVendedorService() {
        dao = new CadVendedorDAO();
    }

    public void save(CadVendedor vendedor) {
            dao.save(vendedor);
    }

    public void update(CadVendedor vendedor) {
            dao.getById(vendedor.getIdVendedor());
            dao.update(vendedor);
        }

    public void delete(CadVendedor vendedor) {
            CadVendedor u = dao.getById(vendedor.getIdVendedor());
            dao.delete(u);
           }

    public CadVendedor carregar(Integer id){
        return dao.getById(id);
    }
    
    public List<CadVendedor> findAll() {
        return dao.findAll();
    }
    
    public List<CadVendedor> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
