/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.dao;

import br.com.velocity.sistema.entidades.CadImagens;
import java.io.Serializable;

/**
 *
 * @author Leandro Laurindo
 */
public class CadImagensDAO extends GenericDAO<Integer, CadImagens> implements Serializable{
    
    public CadImagensDAO() {
       // super(Util.JpaEntityManager());
    }
    
}
