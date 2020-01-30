/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadGruposDAO;
import br.com.velocity.sistema.entidades.CadGrupos;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadGruposService implements Serializable {

    private CadGruposDAO dao;

    public CadGruposService() {
        dao = new CadGruposDAO();
    }

    public void save(CadGrupos cadGrupos) {
        dao.save(cadGrupos);
    }

    public void update(CadGrupos cadGrupos) {
        dao.getById(cadGrupos.getIdGrupo());
        dao.update(cadGrupos);
    }

    public void delete(CadGrupos cadGrupos) {
        CadGrupos u = dao.getById(cadGrupos.getIdGrupo());
        dao.delete(u);
    }

    public CadGrupos carregar(Integer id) {
        return dao.getById(id);
    }

    public List<CadGrupos> findAll() {
        return dao.findAll();
    }

    public List<CadGrupos> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
