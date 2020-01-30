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

    public void save(CadHabilitacao habilitacao) {
       
        dao.save(habilitacao);
        
    }

    public void update(CadHabilitacao habilitacao) {
            dao.getById(habilitacao.getIdHabilitacao());
            dao.update(habilitacao);
    }

    public void delete(CadHabilitacao habilitacao) {
            CadHabilitacao u = dao.getById(habilitacao.getIdHabilitacao());
            dao.delete(u);
    }

    public CadHabilitacao carregar(Integer id){
        return dao.getById(id);
    }
    
    public List<CadHabilitacao> findAll() {
        return dao.findAll();
    }
    
    public List<CadHabilitacao> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
