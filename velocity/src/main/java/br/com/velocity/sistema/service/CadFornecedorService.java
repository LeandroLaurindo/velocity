/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadFornecedorDAO;
import br.com.velocity.sistema.entidades.CadFornecedor;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadFornecedorService implements Serializable {

    private CadFornecedorDAO dao;
    public CadFornecedorService() {
        dao = new CadFornecedorDAO();
    }

    public void save(CadFornecedor fornecedor) {
            dao.save(fornecedor);
    }

    public void update(CadFornecedor fornecedor) {
            dao.getById(fornecedor.getIdFornecedor());
            dao.update(fornecedor);
    }

    public void delete(CadFornecedor fornecedor) {
            CadFornecedor u = dao.getById(fornecedor.getIdFornecedor());
            dao.delete(u);
    }

    public CadFornecedor carregar(Integer id){
        return dao.getById(id);
    }
    
     public CadFornecedor carregarPorParamentro(String paramentro) {
        return dao.getByParametro(paramentro);
    }
    
    public List<CadFornecedor> findAll() {
        return dao.findAll();
    }
    
    public List<CadFornecedor> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
