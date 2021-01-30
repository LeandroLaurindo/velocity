/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadTelefoneDAO;
import br.com.velocity.sistema.entidades.CadTelefone;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadTelefoneService implements Serializable {

    private CadTelefoneDAO dao;

    public CadTelefoneService() {
        dao = new CadTelefoneDAO();
    }

    public boolean save(CadTelefone telefone) {
        return dao.save(telefone);
    }

    public boolean update(CadTelefone telefone) {
        try {
            dao.getById(telefone.getIdTelefone());
            return dao.update(telefone);
        } catch (Throwable e) {
            return false;
        }
    }

    public boolean delete(CadTelefone telefone) {
        try {
            CadTelefone u = dao.getById(telefone.getIdTelefone());
            return dao.delete(u);
        } catch (Throwable e) {
            return false;
        }
    }

    public CadTelefone carregar(Integer id) {
        return dao.getById(id);
    }

    public CadTelefone carregar(String parametro) {
        return dao.getByParametro(parametro);
    }

    public List<CadTelefone> findAll() {
        return dao.findAll();
    }

    public List<CadTelefone> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
