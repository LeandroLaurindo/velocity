/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.dao;

import br.com.velocity.sistema.entidades.CadFornecedor;
import javax.persistence.EntityManager;

/**
 *
 * @author Leandro Laurindo
 */
public class CadFornecedorDAO extends GenericDAO<Integer, CadFornecedor>{
    
    public CadFornecedorDAO(EntityManager entityManager) {
        super(entityManager);
    }
    
}
