/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadDocumentosDAO;
import br.com.velocity.sistema.entidades.CadDocumentos;
import br.com.velocity.sistema.managers.SimpleEntityManager;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadDocumentosService implements Serializable {

    private CadDocumentosDAO dao;

    private SimpleEntityManager simpleEntityManager;

    public CadDocumentosService(SimpleEntityManager simpleEntityManager) {
        this.simpleEntityManager = simpleEntityManager;
        dao = new CadDocumentosDAO(simpleEntityManager.getEntityManager());
    }

    public void save(CadDocumentos documentos) {
        try {
            simpleEntityManager.beginTransaction();
            dao.save(documentos);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void update(CadDocumentos documentos) {
        try {
            simpleEntityManager.beginTransaction();
            dao.getById(documentos.getIdDocumentos());
            dao.update(documentos);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void delete(CadDocumentos documentos) {
        try {
            simpleEntityManager.beginTransaction();
            CadDocumentos u = dao.getById(documentos.getIdDocumentos());
            dao.delete(u);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public CadDocumentos carregar(Integer id){
        return dao.getById(id);
    }
    
     public CadDocumentos carregar(String parametro){
        return dao.getByParametro(parametro);
    }
    
    public List<CadDocumentos> findAll() {
        return dao.findAll();
    }
    
    public List<CadDocumentos> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
