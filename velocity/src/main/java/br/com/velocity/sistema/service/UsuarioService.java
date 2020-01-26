/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.UsuarioDAO;
import br.com.velocity.sistema.entidades.Usuario;
import br.com.velocity.sistema.managers.SimpleEntityManager;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class UsuarioService implements Serializable {

    private UsuarioDAO dao;
    private SimpleEntityManager simpleEntityManager;

    public UsuarioService(SimpleEntityManager simpleEntityManager) {
        this.simpleEntityManager = simpleEntityManager;
        dao = new UsuarioDAO(simpleEntityManager.getEntityManager());
    }

    public void save(Usuario cadUsuario) {
        try {
            simpleEntityManager.beginTransaction();
            dao.save(cadUsuario);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void update(Usuario cadUsuario) {
        try {
            simpleEntityManager.beginTransaction();
            dao.getById(cadUsuario.getIdUsuario());
            dao.update(cadUsuario);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public void delete(Integer id) {
        try {
            simpleEntityManager.beginTransaction();
            Usuario u = dao.getById(id);
            dao.delete(u);
            simpleEntityManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }

    public Usuario carregar(Integer id){
        return dao.getById(id);
    }
    //aqui passa do where para frente os filtros desejados
    public Usuario carregar(String parametro){
        return dao.getByParametro(parametro);
    }
    
    public List<Usuario> findAll() {
        return dao.findAll();
    }
    
    public List<Usuario> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}
