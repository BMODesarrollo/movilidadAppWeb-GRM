/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author solucionesit
 */
@Embeddable
public class TblHistoricoIquidacionEmpleadoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_ejecucion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date idEjecucion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_empleado")
    private int idEmpleado;

    public TblHistoricoIquidacionEmpleadoPK() {
    }

    public TblHistoricoIquidacionEmpleadoPK(Date idEjecucion, int idEmpleado) {
        this.idEjecucion = idEjecucion;
        this.idEmpleado = idEmpleado;
    }

    public Date getIdEjecucion() {
        return idEjecucion;
    }

    public void setIdEjecucion(Date idEjecucion) {
        this.idEjecucion = idEjecucion;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEjecucion != null ? idEjecucion.hashCode() : 0);
        hash += (int) idEmpleado;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblHistoricoIquidacionEmpleadoPK)) {
            return false;
        }
        TblHistoricoIquidacionEmpleadoPK other = (TblHistoricoIquidacionEmpleadoPK) object;
        if ((this.idEjecucion == null && other.idEjecucion != null) || (this.idEjecucion != null && !this.idEjecucion.equals(other.idEjecucion))) {
            return false;
        }
        if (this.idEmpleado != other.idEmpleado) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.TblHistoricoIquidacionEmpleadoPK[ idEjecucion=" + idEjecucion + ", idEmpleado=" + idEmpleado + " ]";
    }
    
}
