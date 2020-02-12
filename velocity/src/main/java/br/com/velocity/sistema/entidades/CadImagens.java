/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Leandro Laurindo
 */
@Entity
@Table(name = "cad_imagens")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CadImagens.findAll", query = "SELECT c FROM CadImagens c")})
public class CadImagens implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_imagem")
    private Integer idImagem;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nome_imagem")
    private String nomeImagem;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "imagem")
    private byte[] imagem;

    public CadImagens() {
    }

    public CadImagens(Integer idImagem) {
        this.idImagem = idImagem;
    }

    public CadImagens(Integer idImagem, String nomeImagem, byte[] imagem) {
        this.idImagem = idImagem;
        this.nomeImagem = nomeImagem;
        this.imagem = imagem;
    }

    public Integer getIdImagem() {
        return idImagem;
    }

    public void setIdImagem(Integer idImagem) {
        this.idImagem = idImagem;
    }

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idImagem != null ? idImagem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CadImagens)) {
            return false;
        }
        CadImagens other = (CadImagens) object;
        if ((this.idImagem == null && other.idImagem != null) || (this.idImagem != null && !this.idImagem.equals(other.idImagem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.velocity.sistema.entidades.CadImagens[ idImagem=" + idImagem + " ]";
    }
    
}
