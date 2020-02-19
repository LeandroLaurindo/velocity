/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadImagensDAO;
import br.com.velocity.sistema.entidades.CadImagens;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadImagensService implements Serializable {

    private CadImagensDAO dao;

    public CadImagensService() {
        dao = new CadImagensDAO();
    }

    public void save(CadImagens imagem) {
        dao.save(imagem);
    }

    public void update(CadImagens imagem) {
        dao.getById(imagem.getIdImagem());
        dao.update(imagem);
    }

    public void delete(CadImagens imagem) {
        CadImagens u = dao.getById(imagem.getIdImagem());
        dao.delete(u);
    }

    public CadImagens carregar(Integer id) {
        return dao.getById(id);
    }

    public CadImagens carregar(String parametro) {
        return dao.getByParametro(parametro);
    }

    public List<CadImagens> findAll() {
        return dao.findAll();
    }

    public List<CadImagens> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
