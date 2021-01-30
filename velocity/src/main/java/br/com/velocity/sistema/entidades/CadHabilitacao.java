/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.velocity.sistema.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Leandro Laurindo
 */
@Entity
@Table(name = "cad_habilitacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CadHabilitacao.findAll", query = "SELECT c FROM CadHabilitacao c")})
public class CadHabilitacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_habilitacao")
    private Integer idHabilitacao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "num_registro")
    private String numRegistro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "validade")
    @Temporal(TemporalType.DATE)
    private Date validade;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "categoria")
    private String categoria;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "orgao")
    private String orgao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "emissao")
    @Temporal(TemporalType.DATE)
    private Date emissao;
    @JoinColumn(name = "documento_fk", referencedColumnName = "id_documentos")
    @ManyToOne
    private CadDocumentos documentoFk;

    public CadHabilitacao() {
    }

    public CadHabilitacao(Integer idHabilitacao) {
        this.idHabilitacao = idHabilitacao;
    }

    public CadHabilitacao(Integer idHabilitacao, String numRegistro, Date validade, String categoria, String orgao, Date emissao) {
        this.idHabilitacao = idHabilitacao;
        this.numRegistro = numRegistro;
        this.validade = validade;
        this.categoria = categoria;
        this.orgao = orgao;
        this.emissao = emissao;
    }

    public Integer getIdHabilitacao() {
        return idHabilitacao;
    }

    public void setIdHabilitacao(Integer idHabilitacao) {
        this.idHabilitacao = idHabilitacao;
    }

    public String getNumRegistro() {
        return numRegistro;
    }

    public void setNumRegistro(String numRegistro) {
        this.numRegistro = numRegistro;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getOrgao() {
        return orgao;
    }

    public void setOrgao(String orgao) {
        this.orgao = orgao;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public CadDocumentos getDocumentoFk() {
        return documentoFk;
    }

    public void setDocumentoFk(CadDocumentos documentoFk) {
        this.documentoFk = documentoFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHabilitacao != null ? idHabilitacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CadHabilitacao)) {
            return false;
        }
        CadHabilitacao other = (CadHabilitacao) object;
        if ((this.idHabilitacao == null && other.idHabilitacao != null) || (this.idHabilitacao != null && !this.idHabilitacao.equals(other.idHabilitacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.velocity.sistema.entidades.CadHabilitacao[ idHabilitacao=" + idHabilitacao + " ]";
    }

}
