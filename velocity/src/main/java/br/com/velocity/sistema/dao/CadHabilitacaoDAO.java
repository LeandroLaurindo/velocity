/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.dao;

import br.com.velocity.sistema.entidades.CadHabilitacao;
import br.com.velocity.sistema.util.Util;
import java.io.Serializable;

/**
 *
 * @author Leandro Laurindo
 */
public class CadHabilitacaoDAO extends GenericDAO<Integer, CadHabilitacao> implements Serializable{
    
    public CadHabilitacaoDAO() {
        //super(Util.JpaEntityManager());
    }
    
}
