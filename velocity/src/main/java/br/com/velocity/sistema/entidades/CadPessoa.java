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
@Table(name = "cad_pessoa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CadPessoa.findAll", query = "SELECT c FROM CadPessoa c")})
public class CadPessoa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pessoa")
    private Integer idPessoa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "tipo_pessoa")
    private String tipoPessoa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150, message = "O campo Nome deve ter entre 1 e 150 caracteres")
    @Column(name = "nome")
    private String nome;
    @Size(max = 150, message = "O campo Cidade deve ter entre 1 e 150 caracteres")
    @Column(name = "razao_social")
    private String razaoSocial;
    @Column(name = "data_nascimento")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    @Column(name = "sexo")
    private Character sexo;
    @Size(max = 50, message = "O campo Estado Civil deve ter entre 1 e 30 caracteres")
    @Column(name = "estado_civil")
    private String estadoCivil;
    @Size(max = 100, message = "O campo Profisão deve ter entre 1 e 100 caracteres")
    @Column(name = "profissao")
    private String profissao;
    @Size(max = 150, message = "O campo Cidade deve ter entre 1 e 30 caracteres")
    @Column(name = "conjuge")
    private String conjuge;
    @Column(name = "estrangeiro")
    private Boolean estrangeiro;
    @Size(max = 100, message = "O campo Pais deve ter entre 1 e 100 caracteres")
    @Column(name = "pais")
    private String pais;
    @Size(max = 100, message = "O campo Nacionalidade deve ter entre 1 e 100 caracteres")
    @Column(name = "nacionalidade")
    private String nacionalidade;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30, message = "O campo Siatução deve ter entre 1 e 30 caracteres")
    @Column(name = "situacao")
    private String situacao;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoaFk")
    private List<CadDocumentos> cadDocumentosList;
    @JoinColumn(name = "usuario_fk", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario usuarioFk;

    public CadPessoa() {
    }

    public CadPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    public CadPessoa(Integer idPessoa, String tipoPessoa, String nome, String situacao, Date dataInsercao, Date dataAlteracao) {
        this.idPessoa = idPessoa;
        this.tipoPessoa = tipoPessoa;
        this.nome = nome;
        this.situacao = situacao;
        this.dataInsercao = dataInsercao;
        this.dataAlteracao = dataAlteracao;
    }

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getConjuge() {
        return conjuge;
    }

    public void setConjuge(String conjuge) {
        this.conjuge = conjuge;
    }

    public Boolean getEstrangeiro() {
        return estrangeiro;
    }

    public void setEstrangeiro(Boolean estrangeiro) {
        this.estrangeiro = estrangeiro;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
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

    @XmlTransient
    public List<CadDocumentos> getCadDocumentosList() {
        return cadDocumentosList;
    }

    public void setCadDocumentosList(List<CadDocumentos> cadDocumentosList) {
        this.cadDocumentosList = cadDocumentosList;
    }

    public Usuario getUsuarioFk() {
        return usuarioFk;
    }

    public void setUsuarioFk(Usuario usuarioFk) {
        this.usuarioFk = usuarioFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPessoa != null ? idPessoa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CadPessoa)) {
            return false;
        }
        CadPessoa other = (CadPessoa) object;
        if ((this.idPessoa == null && other.idPessoa != null) || (this.idPessoa != null && !this.idPessoa.equals(other.idPessoa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.locadora.entidades.CadPessoa[ idPessoa=" + idPessoa + " ]";
    }
}
