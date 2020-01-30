/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadFuncionariosDAO;
import br.com.velocity.sistema.entidades.CadFuncionarios;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadFuncionariosService implements Serializable {

    private CadFuncionariosDAO dao;

    public CadFuncionariosService() {
        dao = new CadFuncionariosDAO();
    }

    public void save(CadFuncionarios funcionarios) {
            dao.save(funcionarios);
         }

    public void update(CadFuncionarios funcionarios) {
            dao.getById(funcionarios.getIdFuncionario());
            dao.update(funcionarios);
    }

    public void delete(CadFuncionarios funcionarios) {
            CadFuncionarios u = dao.getById(funcionarios.getIdFuncionario());
            dao.delete(u);
    }

    public CadFuncionarios carregar(Integer id){
        return dao.getById(id);
    }
    
    public List<CadFuncionarios> findAll() {
        return dao.findAll();
    }
    
    public List<CadFuncionarios> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
