/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import com.movilidad.util.beans.KmsOperador;
import com.movilidad.util.beans.ResumenAsignadosPorPatio;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "mtto_asig")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MttoAsig.findAll", query = "SELECT m FROM MttoAsig m"),
    @NamedQuery(name = "MttoAsig.findByIdMttoAsig", query = "SELECT m FROM MttoAsig m WHERE m.idMttoAsig = :idMttoAsig"),
    @NamedQuery(name = "MttoAsig.findByServbus", query = "SELECT m FROM MttoAsig m WHERE m.servbus = :servbus"),
    @NamedQuery(name = "MttoAsig.findByUsername", query = "SELECT m FROM MttoAsig m WHERE m.username = :username"),
    @NamedQuery(name = "MttoAsig.findByCreado", query = "SELECT m FROM MttoAsig m WHERE m.creado = :creado"),
    @NamedQuery(name = "MttoAsig.findByModificado", query = "SELECT m FROM MttoAsig m WHERE m.modificado = :modificado"),
    @NamedQuery(name = "MttoAsig.findByEstadoReg", query = "SELECT m FROM MttoAsig m WHERE m.estadoReg = :estadoReg")})
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "ResumenAsignadosPorPatioMapping",
            classes = {
                @ConstructorResult(targetClass = ResumenAsignadosPorPatio.class,
                        columns = {
                            @ColumnResult(name = "name"),
                            @ColumnResult(name = "asignados")
                        }
                )
            }),})

public class MttoAsig implements Serializable {

    @JoinColumn(name = "id_from_depot", referencedColumnName = "id_prg_stoppoint")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgStopPoint idFromDepot;
    @JoinColumn(name = "id_to_depot", referencedColumnName = "id_prg_stoppoint")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgStopPoint idToDepot;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mtto_asig")
    private Integer idMttoAsig;
    @Size(max = 10)
    @Column(name = "servbus")
    private String servbus;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_reg")
    private int estadoReg;
    @JoinColumn(name = "id_mtto_tarea", referencedColumnName = "id_mtto_tarea")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private MttoTarea idMttoTarea;
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Vehiculo idVehiculo;
    @Size(max = 8)
    @Column(name = "time_origin")
    private String timeOrigin;
    @Size(max = 8)
    @Column(name = "time_destiny")
    private String timeDestiny;

    public MttoAsig() {
    }

    public MttoAsig(String servbus, MttoTarea idMttoTarea, Vehiculo idVehiculo) {
        this.servbus = servbus;
        this.idMttoTarea = idMttoTarea;
        this.idVehiculo = idVehiculo;
    }

    public MttoAsig(Integer idMttoAsig) {
        this.idMttoAsig = idMttoAsig;
    }

    public MttoAsig(Integer idMttoAsig, String username, Date creado, int estadoReg) {
        this.idMttoAsig = idMttoAsig;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdMttoAsig() {
        return idMttoAsig;
    }

    public void setIdMttoAsig(Integer idMttoAsig) {
        this.idMttoAsig = idMttoAsig;
    }

    public String getServbus() {
        return servbus;
    }

    public void setServbus(String servbus) {
        this.servbus = servbus;
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

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
    }

    public MttoTarea getIdMttoTarea() {
        return idMttoTarea;
    }

    public void setIdMttoTarea(MttoTarea idMttoTarea) {
        this.idMttoTarea = idMttoTarea;
    }

    public Vehiculo getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Vehiculo idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getTimeOrigin() {
        return timeOrigin;
    }

    public void setTimeOrigin(String timeOrigin) {
        this.timeOrigin = timeOrigin;
    }

    public String getTimeDestiny() {
        return timeDestiny;
    }

    public void setTimeDestiny(String timeDestiny) {
        this.timeDestiny = timeDestiny;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMttoAsig != null ? idMttoAsig.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MttoAsig)) {
            return false;
        }
        MttoAsig other = (MttoAsig) object;
        if ((this.idMttoAsig == null && other.idMttoAsig != null) || (this.idMttoAsig != null && !this.idMttoAsig.equals(other.idMttoAsig))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.MttoAsig[ idMttoAsig=" + idMttoAsig + " ]";
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public PrgStopPoint getIdFromDepot() {
        return idFromDepot;
    }

    public void setIdFromDepot(PrgStopPoint idFromDepot) {
        this.idFromDepot = idFromDepot;
    }

    public PrgStopPoint getIdToDepot() {
        return idToDepot;
    }

    public void setIdToDepot(PrgStopPoint idToDepot) {
        this.idToDepot = idToDepot;
    }

}
