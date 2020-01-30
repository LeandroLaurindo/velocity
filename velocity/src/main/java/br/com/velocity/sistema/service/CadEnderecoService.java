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

    public void save(CadEndereco endereco) {
        dao.save(endereco);
    }

    public void update(CadEndereco endereco) {
        dao.getById(endereco.getIdEndereco());
        dao.update(endereco);
    }

    public void delete(CadEndereco endereco) {
        CadEndereco u = dao.getById(endereco.getIdEndereco());
        dao.delete(u);
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
