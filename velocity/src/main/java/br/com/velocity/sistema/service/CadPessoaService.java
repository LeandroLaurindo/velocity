/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.CadPessoaDAO;
import br.com.velocity.sistema.entidades.CadPessoa;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class CadPessoaService implements Serializable {

    private CadPessoaDAO dao;

    public CadPessoaService() {
        dao = new CadPessoaDAO();
    }

    public boolean save(CadPessoa pessoa) {
      return dao.save(pessoa);
    }

    public boolean update(CadPessoa pessoa) {
        try{
           dao.getById(pessoa.getIdPessoa());
           return dao.update(pessoa);
        }catch(Throwable e){
           return false;
        }
    }

    public boolean updateExcliur(Integer id, String p) {
        try{
        CadPessoa pessoa = dao.getById(id);
        pessoa.setDataAlteracao(new Date());
        pessoa.setSituacao(p);
        return dao.update(pessoa);
        }catch(Throwable e){
            return false;
        }
    }

    public boolean delete(CadPessoa pessoa) {
        try{
        CadPessoa u = dao.getById(pessoa.getIdPessoa());
       return dao.delete(u);
        }catch(Throwable e){
            return false;
        }
    }

    public CadPessoa carregar(Integer id) {
        return dao.getById(id);
    }

    public CadPessoa carregar(String paramtendo) {
        return dao.getByParametro(paramtendo);
    }

    public List<CadPessoa> findAll() {
        return dao.findAll();
    }

    public List<CadPessoa> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
