/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.dao;

import br.com.velocity.sistema.entidades.CadPessoa;
import javax.persistence.EntityManager;

/**
 *
 * @author Leandro Laurindo
 */
public class CadPessoaDAO extends GenericDAO<Integer, CadPessoa>{
    
    public CadPessoaDAO(EntityManager entityManager) {
        super(entityManager);
    }
    
}
