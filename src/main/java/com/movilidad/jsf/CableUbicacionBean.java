package com.movilidad.jsf;

import com.movilidad.ejb.CableEstacionFacadeLocal;
import com.movilidad.ejb.CableUbicacionFacadeLocal;
import com.movilidad.ejb.SegMedioComunicacionFacadeLocal;
import com.movilidad.ejb.SegRegistroArmamentoFacadeLocal;
import com.movilidad.model.CableEstacion;
import com.movilidad.model.CableUbicacion;
import com.movilidad.model.SegMedioComunicacion;
import com.movilidad.model.SegRegistroArmamento;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "cableUbicacionBean")
@ViewScoped
public class CableUbicacionBean implements Serializable {

    @EJB
    private CableUbicacionFacadeLocal cableUbicacionEjb;
    @EJB
    private CableEstacionFacadeLocal cableEstacionEjb;
    @EJB
    private SegRegistroArmamentoFacadeLocal registroArmamentoEjb;
    @EJB
    private SegMedioComunicacionFacadeLocal medioComunicacionEjb;

    private CableUbicacion cableUbicacion;
    private CableUbicacion selected;
    private String codigo;
    private String nombre;
    private Integer i_CableEstacion;
    private Integer i_RegArmamento;
    private Integer i_MedioComunicacion;

    private boolean isEditing;

    private List<CableUbicacion> lstCableUbicacion;
    private List<CableEstacion> lstCableEstacion;
    private List<SegRegistroArmamento> lstRegistroArmamentos;
    private List<SegMedioComunicacion> lstMediosComunicacion;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstCableUbicacion = cableUbicacionEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        cableUbicacion = new CableUbicacion();
        selected = null;
        nombre = "";
        codigo = "";
        i_CableEstacion = null;
        i_RegArmamento = null;
        i_MedioComunicacion = null;
        lstCableEstacion = cableEstacionEjb.findByEstadoReg();
        lstRegistroArmamentos = registroArmamentoEjb.findByEstadoReg();
        lstMediosComunicacion = medioComunicacionEjb.findByEstadoReg();
        isEditing = false;
    }

    public void editar() {
        isEditing = true;
        codigo = selected.getCodigo();
        nombre = selected.getNombre();

        lstCableEstacion = cableEstacionEjb.findByEstadoReg();
        lstRegistroArmamentos = registroArmamentoEjb.findByEstadoReg();
        lstMediosComunicacion = medioComunicacionEjb.findByEstadoReg();

        i_CableEstacion = selected.getIdCableEstacion().getIdCableEstacion();
        i_RegArmamento = selected.getIdSegRegistroArmamento() != null ? selected.getIdSegRegistroArmamento().getIdSegRegistroArmamento() : null;
        i_MedioComunicacion = selected.getIdSegMedioComunicacion() != null ? selected.getIdSegMedioComunicacion().getIdSegMedioComunicacion() : null;
        cableUbicacion = selected;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    private void guardarTransactional() {
        String validacion = validarDatos();

        if (validacion == null) {
            if (isEditing) {
                cableUbicacion.setIdCableEstacion(cableEstacionEjb.find(i_CableEstacion));
                cableUbicacion.setIdSegRegistroArmamento(i_RegArmamento != null ? registroArmamentoEjb.find(i_RegArmamento) : null);
                cableUbicacion.setIdSegMedioComunicacion(i_MedioComunicacion != null ? medioComunicacionEjb.find(i_MedioComunicacion) : null);

                cableUbicacion.setCodigo(codigo);
                cableUbicacion.setNombre(nombre);
                cableUbicacion.setUsername(user.getUsername());
                cableUbicacion.setModificado(new Date());
                cableUbicacionEjb.edit(cableUbicacion);
                PrimeFaces.current().executeScript("PF('wvUbicaciones').hide();");
                MovilidadUtil.addSuccessMessage("Ubicación actualizada éxitosamente");
            } else {

                cableUbicacion.setIdCableEstacion(cableEstacionEjb.find(i_CableEstacion));
                cableUbicacion.setIdSegRegistroArmamento(i_RegArmamento != null ? registroArmamentoEjb.find(i_RegArmamento) : null);
                cableUbicacion.setIdSegMedioComunicacion(i_MedioComunicacion != null ? medioComunicacionEjb.find(i_MedioComunicacion) : null);

                cableUbicacion.setCodigo(codigo);
                cableUbicacion.setNombre(nombre);
                cableUbicacion.setUsername(user.getUsername());
                cableUbicacion.setCreado(new Date());
                cableUbicacionEjb.create(cableUbicacion);
                lstCableUbicacion.add(cableUbicacion);
                MovilidadUtil.addSuccessMessage("Ubicación agregada éxitosamente");
                nuevo();
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    private String validarDatos() {

        if (isEditing) {
            if (!cableUbicacion.getCodigo().equals(codigo)) {
                if (cableUbicacionEjb.findByCodigo(codigo.trim()) != null) {
                    return "YA existe un registro con el código a ingresar";
                }
            }
            if (!cableUbicacion.getNombre().equals(nombre)) {
                if (cableUbicacionEjb.findByNombre(nombre.trim()) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }

            if (i_RegArmamento != null) {
                if (cableUbicacion.getIdSegRegistroArmamento() != null) {
                    if (!cableUbicacion.getIdSegRegistroArmamento().getIdSegRegistroArmamento().equals(i_RegArmamento)) {
                        if (cableUbicacionEjb.findByIdArmamento(i_RegArmamento) != null) {
                            return "YA existe un registro con el armamento seleccionado";
                        }
                    }
                } else if (cableUbicacionEjb.findByIdArmamento(i_RegArmamento) != null) {
                    return "YA existe un registro con el armamento seleccionado";
                }
            }
            if (i_MedioComunicacion != null) {
                if (cableUbicacion.getIdSegMedioComunicacion() != null) {
                    if (!cableUbicacion.getIdSegMedioComunicacion().getIdSegMedioComunicacion().equals(i_MedioComunicacion)) {
                        if (cableUbicacionEjb.findByIdMedioComunicacion(i_MedioComunicacion) != null) {
                            return "YA existe un registro con el medio de comunicación seleccionado";
                        }
                    }
                } else if (cableUbicacionEjb.findByIdMedioComunicacion(i_MedioComunicacion) != null) {
                    return "YA existe un registro con el medio de comunicación seleccionado";
                }
            }

        } else {
            if (lstCableUbicacion != null) {
                if (cableUbicacionEjb.findByCodigo(codigo.trim()) != null) {
                    return "YA existe un registro con el código a ingresar";
                }

                if (cableUbicacionEjb.findByNombre(nombre.trim()) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }

                if (i_RegArmamento != null) {
                    if (cableUbicacionEjb.findByIdArmamento(i_RegArmamento) != null) {
                        return "YA existe un registro con el armamento seleccionado";
                    }
                }
                if (i_MedioComunicacion != null) {
                    if (cableUbicacionEjb.findByIdMedioComunicacion(i_MedioComunicacion) != null) {
                        return "YA existe un registro con el medio de comunicación seleccionado";
                    }
                }

            }
        }
        return null;
    }

    public CableUbicacion getCableUbicacion() {
        return cableUbicacion;
    }

    public void setCableUbicacion(CableUbicacion cableEstacion) {
        this.cableUbicacion = cableEstacion;
    }

    public CableUbicacion getSelected() {
        return selected;
    }

    public void setSelected(CableUbicacion selected) {
        this.selected = selected;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<CableUbicacion> getLstCableUbicacion() {
        return lstCableUbicacion;
    }

    public void setLstCableUbicacion(List<CableUbicacion> lstCableUbicacion) {
        this.lstCableUbicacion = lstCableUbicacion;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public Integer getI_CableEstacion() {
        return i_CableEstacion;
    }

    public void setI_CableEstacion(Integer i_CableEstacion) {
        this.i_CableEstacion = i_CableEstacion;
    }

    public Integer getI_RegArmamento() {
        return i_RegArmamento;
    }

    public void setI_RegArmamento(Integer i_RegArmamento) {
        this.i_RegArmamento = i_RegArmamento;
    }

    public Integer getI_MedioComunicacion() {
        return i_MedioComunicacion;
    }

    public void setI_MedioComunicacion(Integer i_MedioComunicacion) {
        this.i_MedioComunicacion = i_MedioComunicacion;
    }

    public List<CableEstacion> getLstCableEstacion() {
        return lstCableEstacion;
    }

    public void setLstCableEstacion(List<CableEstacion> lstCableEstacion) {
        this.lstCableEstacion = lstCableEstacion;
    }

    public List<SegRegistroArmamento> getLstRegistroArmamentos() {
        return lstRegistroArmamentos;
    }

    public void setLstRegistroArmamentos(List<SegRegistroArmamento> lstRegistroArmamentos) {
        this.lstRegistroArmamentos = lstRegistroArmamentos;
    }

    public List<SegMedioComunicacion> getLstMediosComunicacion() {
        return lstMediosComunicacion;
    }

    public void setLstMediosComunicacion(List<SegMedioComunicacion> lstMediosComunicacion) {
        this.lstMediosComunicacion = lstMediosComunicacion;
    }

}
