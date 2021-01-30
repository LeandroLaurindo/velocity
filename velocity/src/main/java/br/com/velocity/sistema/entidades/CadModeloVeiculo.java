/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Leandro Laurindo
 */
@Entity
@Table(name = "cad_modelo_veiculo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CadModeloVeiculo.findAll", query = "SELECT c FROM CadModeloVeiculo c")})
public class CadModeloVeiculo implements Serializable {

    @Size(max = 50, message = "O campo Motivo deve ter entre 1 e 50 caracteres")
    @Column(name = "motivo")
    private String motivo;

   

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_modelo")
    private Integer idModelo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100, message = "O campo Modelo deve ter entre 1 e 100 caracteres")
    @Column(name = "modelo")
    private String modelo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100, message = "O campo Fabricante deve ter entre 1 e 100 caracteres")
    @Column(name = "fabricante")
    private String fabricante;
    @Column(name = "passageiros")
    private Integer passageiros;
    @Column(name = "portas")
    private Integer portas;
    @Column(name = "croqui_fk")
    private Integer croquiFk;
    @Column(name = "protecao_fk")
    private Integer protecaoFk;
    @Size(max = 2147483647)
    @Column(name = "checklist")
    private String checklist;
    @Column(name = "bagagem")
    private Integer bagagem;
    @Column(name = "tanque")
    private Integer tanque;
    @Size(max = 8, message = "O campo Placa deve ter entre 1 e 50 caracteres")
    @Column(name = "placa")
    private String placa;
     @Column(name = "disponivel")
    private Boolean disponivel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "veiculoFk")
    private Collection<ControleVeiculos> controleVeiculosCollection;

    public CadModeloVeiculo() {
    }

    public CadModeloVeiculo(Integer idModelo) {
        this.idModelo = idModelo;
    }

    public CadModeloVeiculo(Integer idModelo, String modelo, String fabricante) {
        this.idModelo = idModelo;
        this.modelo = modelo;
        this.fabricante = fabricante;
    }

    public Integer getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Integer idModelo) {
        this.idModelo = idModelo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Integer getPassageiros() {
        return passageiros;
    }

    public void setPassageiros(Integer passageiros) {
        this.passageiros = passageiros;
    }

    public Integer getPortas() {
        return portas;
    }

    public void setPortas(Integer portas) {
        this.portas = portas;
    }

    public Integer getCroquiFk() {
        return croquiFk;
    }

    public void setCroquiFk(Integer croquiFk) {
        this.croquiFk = croquiFk;
    }

    public Integer getProtecaoFk() {
        return protecaoFk;
    }

    public void setProtecaoFk(Integer protecaoFk) {
        this.protecaoFk = protecaoFk;
    }

    public String getChecklist() {
        return checklist;
    }

    public void setChecklist(String checklist) {
        this.checklist = checklist;
    }

    public Integer getBagagem() {
        return bagagem;
    }

    public void setBagagem(Integer bagagem) {
        this.bagagem = bagagem;
    }

    public Integer getTanque() {
        return tanque;
    }

    public void setTanque(Integer tanque) {
        this.tanque = tanque;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
     @XmlTransient
    public Collection<ControleVeiculos> getControleVeiculosCollection() {
        return controleVeiculosCollection;
    }

    public void setControleVeiculosCollection(Collection<ControleVeiculos> controleVeiculosCollection) {
        this.controleVeiculosCollection = controleVeiculosCollection;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idModelo != null ? idModelo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CadModeloVeiculo)) {
            return false;
        }
        CadModeloVeiculo other = (CadModeloVeiculo) object;
        if ((this.idModelo == null && other.idModelo != null) || (this.idModelo != null && !this.idModelo.equals(other.idModelo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.locadora.entidades.CadModeloVeiculo[ idModelo=" + idModelo + " ]";
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }


}
