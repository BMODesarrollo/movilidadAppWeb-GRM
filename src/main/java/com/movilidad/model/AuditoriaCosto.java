/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import com.movilidad.dto.AuditoriaCostoDTO;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "auditoria_costo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuditoriaCosto.findAll", query = "SELECT a FROM AuditoriaCosto a")
    , @NamedQuery(name = "AuditoriaCosto.findByIdAuditoriaCosto", query = "SELECT a FROM AuditoriaCosto a WHERE a.idAuditoriaCosto = :idAuditoriaCosto")
    , @NamedQuery(name = "AuditoriaCosto.findByValor", query = "SELECT a FROM AuditoriaCosto a WHERE a.valor = :valor")
    , @NamedQuery(name = "AuditoriaCosto.findByDesde", query = "SELECT a FROM AuditoriaCosto a WHERE a.desde = :desde")
    , @NamedQuery(name = "AuditoriaCosto.findByHasta", query = "SELECT a FROM AuditoriaCosto a WHERE a.hasta = :hasta")
    , @NamedQuery(name = "AuditoriaCosto.findByUsername", query = "SELECT a FROM AuditoriaCosto a WHERE a.username = :username")
    , @NamedQuery(name = "AuditoriaCosto.findByCreado", query = "SELECT a FROM AuditoriaCosto a WHERE a.creado = :creado")
    , @NamedQuery(name = "AuditoriaCosto.findByModificado", query = "SELECT a FROM AuditoriaCosto a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AuditoriaCosto.findByEstadoReg", query = "SELECT a FROM AuditoriaCosto a WHERE a.estadoReg = :estadoReg")})
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "AuditoriaCostoMapping",
            classes = {
                @ConstructorResult(targetClass = AuditoriaCostoDTO.class,
                        columns = {
                            @ColumnResult(name = "tipo_costo")
                            ,
                            @ColumnResult(name = "tipo_vehiculo")
                            ,
                            @ColumnResult(name = "valor", type = int.class)
                            ,
                            @ColumnResult(name = "desde", type = Date.class)
                            ,
                            @ColumnResult(name = "hasta", type = Date.class)
                        }
                )
            }),})
public class AuditoriaCosto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_auditoria_costo")
    private Integer idAuditoriaCosto;
    @Column(name = "valor")
    private Integer valor;
    @Column(name = "desde")
    @Temporal(TemporalType.DATE)
    private Date desde;
    @Column(name = "hasta")
    @Temporal(TemporalType.DATE)
    private Date hasta;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @JoinColumn(name = "id_auditoria", referencedColumnName = "id_auditoria")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Auditoria idAuditoria;
    @JoinColumn(name = "id_auditoria_costo_tipo", referencedColumnName = "id_auditoria_costo_tipo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AuditoriaCostoTipo idAuditoriaCostoTipo;
    @JoinColumn(name = "id_vehiculo_tipo", referencedColumnName = "id_vehiculo_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private VehiculoTipo idVehiculoTipo;

    public AuditoriaCosto() {
    }

    public AuditoriaCosto(Integer idAuditoriaCosto) {
        this.idAuditoriaCosto = idAuditoriaCosto;
    }

    public Integer getIdAuditoriaCosto() {
        return idAuditoriaCosto;
    }

    public void setIdAuditoriaCosto(Integer idAuditoriaCosto) {
        this.idAuditoriaCosto = idAuditoriaCosto;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    public Date getModificado() {
        return modificado;
    }

    public void setModificado(Date modificado) {
        this.modificado = modificado;
    }

    public Integer getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(Integer estadoReg) {
        this.estadoReg = estadoReg;
    }

    public Auditoria getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(Auditoria idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public AuditoriaCostoTipo getIdAuditoriaCostoTipo() {
        return idAuditoriaCostoTipo;
    }

    public void setIdAuditoriaCostoTipo(AuditoriaCostoTipo idAuditoriaCostoTipo) {
        this.idAuditoriaCostoTipo = idAuditoriaCostoTipo;
    }

    public VehiculoTipo getIdVehiculoTipo() {
        return idVehiculoTipo;
    }

    public void setIdVehiculoTipo(VehiculoTipo idVehiculoTipo) {
        this.idVehiculoTipo = idVehiculoTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuditoriaCosto != null ? idAuditoriaCosto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuditoriaCosto)) {
            return false;
        }
        AuditoriaCosto other = (AuditoriaCosto) object;
        if ((this.idAuditoriaCosto == null && other.idAuditoriaCosto != null) || (this.idAuditoriaCosto != null && !this.idAuditoriaCosto.equals(other.idAuditoriaCosto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AuditoriaCosto[ idAuditoriaCosto=" + idAuditoriaCosto + " ]";
    }

}
