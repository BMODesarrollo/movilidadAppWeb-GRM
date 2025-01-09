package com.movilidad.model.planificacion_recursos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
 * Clase que permite la iteracción del sistema con la tabla pla_recu_proceso_pro que corresponde 
 a 'PlaRecuLugar' del módulo 'Planificación de recursos'
 * @author Luis Lancheros
 */
@Entity
@Table(name = "pla_recu_proceso_pro_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlaRecuProcesoProDet.findAll", query = "SELECT i FROM PlaRecuProcesoProDet i"),
    @NamedQuery(name = "PlaRecuProcesoProDet.findByIdProcesoPro", query = "SELECT i FROM PlaRecuProcesoProDet i WHERE i.idPlaRecuProcesoProDet = :idPlaRecuProcesoProDet"),
    @NamedQuery(name = "PlaRecuProcesoProDet.findByDescripcion", query = "SELECT i FROM PlaRecuProcesoProDet i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "PlaRecuProcesoProDet.findByCreado", query = "SELECT i FROM PlaRecuProcesoProDet i WHERE i.creado = :creado"),
    @NamedQuery(name = "PlaRecuProcesoProDet.findByModificado", query = "SELECT i FROM PlaRecuProcesoProDet i WHERE i.modificado = :modificado"),
    @NamedQuery(name = "PlaRecuProcesoProDet.findByEstadoReg", query = "SELECT i FROM PlaRecuProcesoProDet i WHERE i.estadoReg = :estadoReg")})

public class PlaRecuProcesoProDet implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pla_recu_proceso_pro_det")
    private Integer idPlaRecuProcesoProDet;
    @JoinColumn(name = "id_pla_recu_proceso_pro", referencedColumnName = "id_pla_recu_proceso_pro")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlaRecuProcesoPro idPlaRecuProcesoPro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "is_checked")
    private Boolean isChecked;
    @Column(name = "duracion")
    private Integer duracion;
    @Size(max = 15)
    @Column(name = "username_create")
    private String usernameCreate;
    @Size(max = 15)
    @Column(name = "username_edit")
    private String usernameEdit;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    
    public PlaRecuProcesoProDet() {
    }

    public Integer getIdPlaRecuProcesoProDet() {
        return idPlaRecuProcesoProDet;
    }

    public void setIdPlaRecuProcesoProDet(Integer idPlaRecuProcesoProDet) {
        this.idPlaRecuProcesoProDet = idPlaRecuProcesoProDet;
    }

    public PlaRecuProcesoPro getIdPlaRecuProcesoPro() {
        return idPlaRecuProcesoPro;
    }

    public void setIdPlaRecuProcesoPro(PlaRecuProcesoPro idPlaRecuProcesoPro) {
        this.idPlaRecuProcesoPro = idPlaRecuProcesoPro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }
    
    public String getUsernameCreate() {
        return usernameCreate;
    }

    public void setUsernameCreate(String usernameCreate) {
        this.usernameCreate = usernameCreate;
    }

    public String getUsernameEdit() {
        return usernameEdit;
    }

    public void setUsernameEdit(String usernameEdit) {
        this.usernameEdit = usernameEdit;
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

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlaRecuProcesoProDet != null ? idPlaRecuProcesoProDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaRecuProcesoProDet)) {
            return false;
        }
        PlaRecuProcesoProDet other = (PlaRecuProcesoProDet) object;
        return !((this.idPlaRecuProcesoProDet == null && other.idPlaRecuProcesoProDet != null) || (this.idPlaRecuProcesoProDet != null && 
                !this.idPlaRecuProcesoProDet.equals(other.idPlaRecuProcesoProDet)));
    }

    @Override
    public String toString() {
        return "com.movilidad.model.planificacion_recursos.procesoProDet[ idPlaRecuProcesoProDet=" + idPlaRecuProcesoProDet + " ]";
    }

    public Object stream() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}