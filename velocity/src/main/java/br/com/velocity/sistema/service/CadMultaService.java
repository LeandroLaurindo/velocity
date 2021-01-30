/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadMultaDAO;
import br.com.velocity.sistema.entidades.CadMulta;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadMultaService implements Serializable {
   
  
    private CadMultaDAO dao;

    public CadMultaService() {
        dao = new CadMultaDAO();
   }
    public boolean save(CadMulta multa) {
       return dao.save(multa);
    }

    public boolean update(CadMulta multa) {
        try{
        dao.getById(multa.getIdMulta());
       return dao.update(multa);
        }catch(Throwable e){
            return false;
        }
    }

    public boolean delete(CadMulta multa) {
        try{
        CadMulta u = dao.getById(multa.getIdMulta());
        return dao.delete(u);
        }catch(Throwable e){
            return false;
        }
    }

    public CadMulta carregar(Integer id) {
        return dao.getById(id);
    }

    public CadMulta carregar(String paramentro) {
        return dao.getByParametro(paramentro);
    }

    public List<CadMulta> findAll() {
        return dao.findAll();
    }

    public List<CadMulta> findAll(String parametros) {
        return dao.findAll(parametros);
    }
    
    public CadMultaDAO getDao() {
        return dao;
    }

    public void setDao(CadMultaDAO dao) {
        this.dao = dao;
    }
  
}
