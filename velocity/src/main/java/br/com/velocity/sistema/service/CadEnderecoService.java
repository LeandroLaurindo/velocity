/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadEnderecoDAO;
import br.com.velocity.sistema.entidades.CadEndereco;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadEnderecoService implements Serializable {

    private CadEnderecoDAO dao;

    public CadEnderecoService() {
        dao = new CadEnderecoDAO();
    }

    public boolean save(CadEndereco endereco) {
        return dao.save(endereco);
    }

    public boolean update(CadEndereco endereco) {
        try{
        dao.getById(endereco.getIdEndereco());
        return dao.update(endereco);
        }catch(Throwable e){
            return false;
        }
    }

    public boolean delete(CadEndereco endereco) {
        try{
        CadEndereco u = dao.getById(endereco.getIdEndereco());
        return dao.delete(u);
        }catch(Throwable e){
            return false;
        }
    }

    public CadEndereco carregar(Integer id) {
        return dao.getById(id);
    }

    public CadEndereco carregar(String parametro) {
        return dao.getByParametro(parametro);
    }

    public List<CadEndereco> findAll() {
        return dao.findAll();
    }

    public List<CadEndereco> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
