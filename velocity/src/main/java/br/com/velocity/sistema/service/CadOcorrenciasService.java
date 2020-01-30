/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadOcorrenciasDAO;
import br.com.velocity.sistema.entidades.CadOcorrencias;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadOcorrenciasService implements Serializable {

    private CadOcorrenciasDAO dao;

    public CadOcorrenciasService() {
        dao = new CadOcorrenciasDAO();
    }

    public void save(CadOcorrencias cadOcorrencias) {
            dao.save(cadOcorrencias);   
    }

    public void update(CadOcorrencias cadOcorrencias) {
            dao.getById(cadOcorrencias.getIdOcorrencia());
            dao.update(cadOcorrencias);
          }

    public void delete(CadOcorrencias cadOcorrencias) {
            CadOcorrencias u = dao.getById(cadOcorrencias.getIdOcorrencia());
            dao.delete(u);    
    }
   
    public CadOcorrencias carregar(Integer id){
        return dao.getById(id);
    }
    
    public List<CadOcorrencias> findAll() {
        return dao.findAll();
    }
    
    public List<CadOcorrencias> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
