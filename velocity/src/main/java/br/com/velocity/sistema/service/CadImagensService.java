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

    public boolean save(CadImagens imagem) {
       return dao.save(imagem);
    }

    public boolean update(CadImagens imagem) {
        try{
        dao.getById(imagem.getIdImagem());
        return dao.update(imagem);
        }catch(Throwable e){
            return false;
        }
    }

    public boolean delete(CadImagens imagem) {
        try{
        CadImagens u = dao.getById(imagem.getIdImagem());
        return dao.delete(u);
        }catch(Throwable e){
            return false;
        }
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
