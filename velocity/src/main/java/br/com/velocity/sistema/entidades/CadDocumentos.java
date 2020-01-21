/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentoFk")
    private List<CadFuncionarios> cadFuncionariosList;
    @JoinColumn(name = "pessoa_fk", referencedColumnName = "id_pessoa")
    @ManyToOne(optional = false)
    private CadPessoa pessoaFk;
    @OneToMany(mappedBy = "documentoFk")
    private List<CadHabilitacao> cadHabilitacaoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentoFk")
    private List<CadEmail> cadEmailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentoFk")
    private List<CadFornecedor> cadFornecedorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentoFk")
    private List<CadVendedor> cadVendedorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentoFk")
    private List<CadCliente> cadClienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentoFk")
    private List<CadTelefone> cadTelefoneList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentoFk")
    private List<CadOcorrencias> cadOcorrenciasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentoFk")
    private List<CadEndereco> cadEnderecoList;

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

    @XmlTransient
    public List<CadFuncionarios> getCadFuncionariosList() {
        return cadFuncionariosList;
    }

    public void setCadFuncionariosList(List<CadFuncionarios> cadFuncionariosList) {
        this.cadFuncionariosList = cadFuncionariosList;
    }

    public CadPessoa getPessoaFk() {
        return pessoaFk;
    }

    public void setPessoaFk(CadPessoa pessoaFk) {
        this.pessoaFk = pessoaFk;
    }

    @XmlTransient
    public List<CadHabilitacao> getCadHabilitacaoList() {
        return cadHabilitacaoList;
    }

    public void setCadHabilitacaoList(List<CadHabilitacao> cadHabilitacaoList) {
        this.cadHabilitacaoList = cadHabilitacaoList;
    }

    @XmlTransient
    public List<CadEmail> getCadEmailList() {
        return cadEmailList;
    }

    public void setCadEmailList(List<CadEmail> cadEmailList) {
        this.cadEmailList = cadEmailList;
    }

    @XmlTransient
    public List<CadFornecedor> getCadFornecedorList() {
        return cadFornecedorList;
    }

    public void setCadFornecedorList(List<CadFornecedor> cadFornecedorList) {
        this.cadFornecedorList = cadFornecedorList;
    }

    @XmlTransient
    public List<CadVendedor> getCadVendedorList() {
        return cadVendedorList;
    }

    public void setCadVendedorList(List<CadVendedor> cadVendedorList) {
        this.cadVendedorList = cadVendedorList;
    }

    @XmlTransient
    public List<CadCliente> getCadClienteList() {
        return cadClienteList;
    }

    public void setCadClienteList(List<CadCliente> cadClienteList) {
        this.cadClienteList = cadClienteList;
    }

    @XmlTransient
    public List<CadTelefone> getCadTelefoneList() {
        return cadTelefoneList;
    }

    public void setCadTelefoneList(List<CadTelefone> cadTelefoneList) {
        this.cadTelefoneList = cadTelefoneList;
    }

    @XmlTransient
    public List<CadOcorrencias> getCadOcorrenciasList() {
        return cadOcorrenciasList;
    }

    public void setCadOcorrenciasList(List<CadOcorrencias> cadOcorrenciasList) {
        this.cadOcorrenciasList = cadOcorrenciasList;
    }

    @XmlTransient
    public List<CadEndereco> getCadEnderecoList() {
        return cadEnderecoList;
    }

    public void setCadEnderecoList(List<CadEndereco> cadEnderecoList) {
        this.cadEnderecoList = cadEnderecoList;
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
        return "br.com.locadora.entidades.CadDocumentos[ idDocumentos=" + idDocumentos + " ]";
    }
    
}
