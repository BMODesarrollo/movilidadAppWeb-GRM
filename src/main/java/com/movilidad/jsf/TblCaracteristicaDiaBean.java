package com.movilidad.jsf;

import com.movilidad.ejb.TblCalendarioCaracteristicasDiaFacadeLocal;
import com.movilidad.model.TblCalendarioCaracteristicasDia;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "tblCaracteristicaDiaBean")
@ViewScoped
public class TblCaracteristicaDiaBean implements Serializable {
    
    @EJB
    private TblCalendarioCaracteristicasDiaFacadeLocal caracteristicaDiaEjb;
    
    private TblCalendarioCaracteristicasDia caracteristicaDia;
    private TblCalendarioCaracteristicasDia selected;
    
    private boolean b_afecta_tec;
    private boolean b_afecta_prg;
    private boolean flagEdit;
    
    private String nombre;
    
    private List<TblCalendarioCaracteristicasDia> lista;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    @PostConstruct
    public void init() {
        consultar();
    }
    
    public void nuevo() {
        caracteristicaDia = new TblCalendarioCaracteristicasDia();
        nombre = "";
        selected = null;
        b_afecta_tec = false;
        b_afecta_prg = false;
        flagEdit = false;
    }
    
    public void editar() {
        
//        if (!isAlterable()) {
//            MovilidadUtil.addErrorMessage("Usted NO puede editar éste registro");
//            return;
//        }
        
        flagEdit = true;
        nombre = selected.getNombre();
        caracteristicaDia = selected;
        b_afecta_prg = (caracteristicaDia.getAfectaPrg() == 1);
        b_afecta_tec = (caracteristicaDia.getAfectaTec() == 1);
    }
    
    public void guardar() {
        String validacion = validar();
        if (validacion == null) {
            caracteristicaDia.setAfectaPrg(b_afecta_prg ? 1 : 0);
            caracteristicaDia.setAfectaTec(b_afecta_tec ? 1 : 0);
            caracteristicaDia.setUsername(user.getUsername());
            caracteristicaDia.setNombre(nombre);
            
            if (flagEdit) {
                caracteristicaDia.setUsername(user.getUsername());
                caracteristicaDia.setModificado(MovilidadUtil.fechaCompletaHoy());
                caracteristicaDiaEjb.edit(caracteristicaDia);
                MovilidadUtil.hideModal("wVCaracteristicaDia");
                MovilidadUtil.addSuccessMessage("Registro modificado éxitosamente");
            } else {
                caracteristicaDia.setCreado(MovilidadUtil.fechaCompletaHoy());
                caracteristicaDia.setEstadoReg(0);
                caracteristicaDiaEjb.create(caracteristicaDia);
                lista.add(caracteristicaDia);
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
                nuevo();
            }
            
            consultar();
            
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }
    
    private boolean isAlterable() {
        if (selected == null) {
            return false;
        }
        return selected.getUsername().equals(user.getUsername());
    }
    
    private void consultar() {
        lista = caracteristicaDiaEjb.findAllByEstadoReg();
    }
    
    private String validar() {
        
        if (flagEdit) {
            if (caracteristicaDiaEjb.findByNombre(selected.getIdTblCalendarioCaracteristicaDia(), nombre) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
            
        } else {
            if (caracteristicaDiaEjb.findByNombre(0, nombre) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        }
        return null;
    }
    
    public TblCalendarioCaracteristicasDia getSelected() {
        return selected;
    }
    
    public void setSelected(TblCalendarioCaracteristicasDia selected) {
        this.selected = selected;
    }
    
    public boolean isB_afecta_tec() {
        return b_afecta_tec;
    }
    
    public void setB_afecta_tec(boolean b_afecta_tec) {
        this.b_afecta_tec = b_afecta_tec;
    }
    
    public boolean isB_afecta_prg() {
        return b_afecta_prg;
    }
    
    public void setB_afecta_prg(boolean b_afecta_prg) {
        this.b_afecta_prg = b_afecta_prg;
    }
    
    public boolean isFlagEdit() {
        return flagEdit;
    }
    
    public void setFlagEdit(boolean flagEdit) {
        this.flagEdit = flagEdit;
    }
    
    public TblCalendarioCaracteristicasDia getCaracteristicaDia() {
        return caracteristicaDia;
    }
    
    public void setCaracteristicaDia(TblCalendarioCaracteristicasDia caracteristicaDia) {
        this.caracteristicaDia = caracteristicaDia;
    }
    
    public List<TblCalendarioCaracteristicasDia> getLista() {
        return lista;
    }
    
    public void setLista(List<TblCalendarioCaracteristicasDia> lista) {
        this.lista = lista;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
