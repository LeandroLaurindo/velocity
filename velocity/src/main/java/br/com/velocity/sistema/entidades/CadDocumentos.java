/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

/**
 *
 * @author Leandro Laurindo
 */
@Entity
@Table(name = "cad_documentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CadDocumentos.findAll", query = "SELECT c FROM CadDocumentos c")})
public class CadDocumentos implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentoFk")
    private Collection<CadCliente> cadClienteCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_documentos")
    private Integer idDocumentos;
    @Size(max = 11)
    @Column(name = "cpf")
    private String cpf;
    @Size(max = 15)
    @Column(name = "cnpj")
    private String cnpj;
    @Size(max = 20)
    @Column(name = "rg")
    private String rg;
    @Size(max = 30)
    @Column(name = "inscricao_estudal")
    private String inscricaoEstudal;
    @Size(max = 30)
    @Column(name = "inscricao_municipal")
    private String inscricaoMunicipal;
    @Size(max = 30)
    @Column(name = "suframa")
    private String suframa;
    @Size(max = 30)
    @Column(name = "passaporte")
    private String passaporte;
    @Column(name = "validade_passaporte")
    @Temporal(TemporalType.DATE)
    private Date validadePassaporte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_insercao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInsercao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_alteracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlteracao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "usuario_fk")
    private int usuarioFk;
    @JoinColumn(name = "pessoa_fk", referencedColumnName = "id_pessoa")
    @ManyToOne(optional = false)
    private CadPessoa pessoaFk;

    public CadDocumentos() {
    }

    public CadDocumentos(Integer idDocumentos) {
        this.idDocumentos = idDocumentos;
    }

    public CadDocumentos(Integer idDocumentos, Date dataInsercao, Date dataAlteracao, int usuarioFk) {
        this.idDocumentos = idDocumentos;
        this.dataInsercao = dataInsercao;
        this.dataAlteracao = dataAlteracao;
        this.usuarioFk = usuarioFk;
    }

    public Integer getIdDocumentos() {
        return idDocumentos;
    }

    public void setIdDocumentos(Integer idDocumentos) {
        this.idDocumentos = idDocumentos;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getInscricaoEstudal() {
        return inscricaoEstudal;
    }

    public void setInscricaoEstudal(String inscricaoEstudal) {
        this.inscricaoEstudal = inscricaoEstudal;
    }

    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    public void setInscricaoMunicipal(String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    public String getSuframa() {
        return suframa;
    }

    public void setSuframa(String suframa) {
        this.suframa = suframa;
    }

    public String getPassaporte() {
        return passaporte;
    }

    public void setPassaporte(String passaporte) {
        this.passaporte = passaporte;
    }

    public Date getValidadePassaporte() {
        return validadePassaporte;
    }

    public void setValidadePassaporte(Date validadePassaporte) {
        this.validadePassaporte = validadePassaporte;
    }

    public Date getDataInsercao() {
        return dataInsercao;
    }

    public void setDataInsercao(Date dataInsercao) {
        this.dataInsercao = dataInsercao;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public int getUsuarioFk() {
        return usuarioFk;
    }

    public void setUsuarioFk(int usuarioFk) {
        this.usuarioFk = usuarioFk;
    }

    public CadPessoa getPessoaFk() {
        return pessoaFk;
    }

    public void setPessoaFk(CadPessoa pessoaFk) {
        this.pessoaFk = pessoaFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDocumentos != null ? idDocumentos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CadDocumentos)) {
            return false;
        }
        CadDocumentos other = (CadDocumentos) object;
        if ((this.idDocumentos == null && other.idDocumentos != null) || (this.idDocumentos != null && !this.idDocumentos.equals(other.idDocumentos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.velocity.sistema.entidades.CadDocumentos[ idDocumentos=" + idDocumentos + " ]";
    }

    @XmlTransient
    public Collection<CadCliente> getCadClienteCollection() {
        return cadClienteCollection;
    }

    public void setCadClienteCollection(Collection<CadCliente> cadClienteCollection) {
        this.cadClienteCollection = cadClienteCollection;
    }
    
}
