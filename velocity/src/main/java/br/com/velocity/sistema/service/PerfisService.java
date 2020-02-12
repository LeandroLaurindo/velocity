/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.PerfisDAO;
import br.com.velocity.sistema.entidades.Perfis;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class PerfisService implements Serializable {

    private PerfisDAO dao;

    public PerfisService() {
        dao = new PerfisDAO();
    }

    public void save(Perfis perfil) {
        dao.save(perfil);
    }

    public void update(Perfis perfil) {
        dao.getById(perfil.getIdPerfil());
        dao.update(perfil);
    }

    public void delete(Perfis perfil) {
        Perfis u = dao.getById(perfil.getIdPerfil());
        dao.delete(u);
    }

    public Perfis carregar(Integer id) {
        return dao.getById(id);
    }

    public Perfis carregar(String parametro) {
        return dao.getByParametro(parametro);
    }

    public List<Perfis> findAll() {
        return dao.findAll();
    }

    public List<Perfis> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
