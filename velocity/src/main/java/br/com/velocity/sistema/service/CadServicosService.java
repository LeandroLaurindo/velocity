/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadServicosDAO;
import br.com.velocity.sistema.entidades.CadServicos;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadServicosService implements Serializable {

    private CadServicosDAO dao;

    public CadServicosService() {
        dao = new CadServicosDAO();
    }

    
    public boolean save(CadServicos cadServico) {
         return dao.save(cadServico);
    }

    public boolean update(CadServicos cadServico) {
        try {
            dao.getById(cadServico.getIdServico());
          return dao.update(cadServico);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(CadServicos cadServico) {
        try {
           
            CadServicos u = dao.getById(cadServico.getIdServico());
           return dao.delete(u);
        } catch (Exception e) {
            e.printStackTrace();
            return  false;
        }
    }

    public CadServicos carregar(Integer id){
        return dao.getById(id);
    }
    
    public List<CadServicos> findAll() {
        return dao.findAll();
    }
    
    public List<CadServicos> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
