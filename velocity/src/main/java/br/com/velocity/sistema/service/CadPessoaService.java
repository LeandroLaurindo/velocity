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

    public void save(CadPessoa pessoa) {
        dao.save(pessoa);
    }

    public void update(CadPessoa pessoa) {
        dao.getById(pessoa.getIdPessoa());
        dao.update(pessoa);

    }

    public void update(Integer id) {
        CadPessoa pessoa = dao.getById(id);
        pessoa.setDataAlteracao(new Date());
        pessoa.setSituacao("NAO");
        dao.update(pessoa);
    }

    public void delete(CadPessoa pessoa) {
        CadPessoa u = dao.getById(pessoa.getIdPessoa());
        dao.delete(u);
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
