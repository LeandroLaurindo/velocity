/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadTipoServicoDAO;
import br.com.velocity.sistema.entidades.CadTipoServico;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadTipoServicoService implements Serializable {

    private CadTipoServicoDAO dao;

    public CadTipoServicoService() {
        dao = new CadTipoServicoDAO();
    }

    public boolean save(CadTipoServico cadTipoServico) {
        return dao.save(cadTipoServico);
    }

    public boolean update(CadTipoServico cadTipoServico) {
        try {
            dao.getById(cadTipoServico.getIdTipoServico());
            return dao.update(cadTipoServico);
        } catch (Throwable e) {
            return false;
        }
    }

    public boolean delete(CadTipoServico cadTipoServico) {
        try {
            CadTipoServico u = dao.getById(cadTipoServico.getIdTipoServico());
            return dao.delete(u);
        } catch (Throwable e) {
            return false;
        }
    }

    public CadTipoServico carregar(Integer id) {
        return dao.getById(id);
    }

    public CadTipoServico carregar(String paramentro) {
        return dao.getByParametro(paramentro);
    }

    public List<CadTipoServico> findAll() {
        return dao.findAll();
    }

    public List<CadTipoServico> findAll(String parametros) {
        return dao.findAll(parametros);
    }

    public CadTipoServicoDAO getDao() {
        return dao;
    }

    public void setDao(CadTipoServicoDAO dao) {
        this.dao = dao;
    }

}
