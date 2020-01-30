/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadDocumentosDAO;
import br.com.velocity.sistema.entidades.CadDocumentos;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadDocumentosService implements Serializable {

    private CadDocumentosDAO dao;
    
    
    public CadDocumentosService() {
        dao = new CadDocumentosDAO();
    }

    public void save(CadDocumentos documentos) {
        dao.save(documentos);
    }

    public void update(CadDocumentos documentos) {
        dao.getById(documentos.getIdDocumentos());
        dao.update(documentos);
    }

    public void delete(CadDocumentos documentos) {
        CadDocumentos u = dao.getById(documentos.getIdDocumentos());
        dao.delete(u);
    }

    public CadDocumentos carregar(Integer id) {
        return dao.getById(id);
    }

    public CadDocumentos carregar(String parametro) {
        return dao.getByParametro(parametro);
    }

    public List<CadDocumentos> findAll() {
        return dao.findAll();
    }

    public List<CadDocumentos> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
