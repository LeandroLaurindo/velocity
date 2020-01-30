/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.dao;

import br.com.velocity.sistema.entidades.CadEmail;
import br.com.velocity.sistema.util.Util;
import java.io.Serializable;

/**
 *
 * @author Leandro Laurindo
 */
public class CadEmailDAO extends GenericDAO<Integer, CadEmail> implements Serializable{
    
    public CadEmailDAO() {
       // super(Util.JpaEntityManager());
    }
    
}
