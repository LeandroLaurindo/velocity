/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadHabilitacaoDAO;
import br.com.velocity.sistema.entidades.CadHabilitacao;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadHabilitacaoService implements Serializable {

    private CadHabilitacaoDAO dao;

    public CadHabilitacaoService() {
        dao = new CadHabilitacaoDAO();
    }

    public boolean save(CadHabilitacao habilitacao) {

        return dao.save(habilitacao);

    }

    public boolean update(CadHabilitacao habilitacao) {
        try {
            dao.getById(habilitacao.getIdHabilitacao());
            return dao.update(habilitacao);
        } catch (Throwable e) {
            return false;
        }
    }

    public boolean delete(CadHabilitacao habilitacao) {
        try {
            CadHabilitacao u = dao.getById(habilitacao.getIdHabilitacao());
            return dao.delete(u);
        } catch (Throwable e) {
            return false;
        }
    }

    public CadHabilitacao carregar(Integer id) {
        return dao.getById(id);
    }

    public CadHabilitacao carregarParametros(String p) {
        return dao.getByParametro(p);
    }
    public List<CadHabilitacao> findAll() {
        return dao.findAll();
    }

    public List<CadHabilitacao> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
