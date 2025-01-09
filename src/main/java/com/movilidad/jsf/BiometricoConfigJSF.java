package com.movilidad.jsf;

import com.movilidad.ejb.BiometricoConfigFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.BiometricoConfig;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.security.UserExtended;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import com.movilidad.utils.MovilidadUtil;
import java.util.Objects;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Omar.beltran
 */
@Named(value = "biometricoConfig")
@ViewScoped
public class BiometricoConfigJSF implements Serializable {

    @EJB
    private BiometricoConfigFacadeLocal biometricoConfigFacadeLocal;
    
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;
    
    private List<BiometricoConfig> listBiometricoConfig;
    private String cNuevoVal;
    private BiometricoConfig bioConfig;
    private ParamAreaUsr pau;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        bioConfig = getBioConfig();
        listBiometricoConfig = new ArrayList<>();
        cNuevoVal = null;
        pau = paramAreaUserEJB.getByIdUser(user.getUsername());
    }

    public void preCrear() {
        PrimeFaces.current().executeScript("PF('dlgNuevoParametro').show();");
    }

    public void crearParametrizacion() {
        try {
            if (Objects.nonNull(bioConfig)) {
                bioConfig.setUsernameCre(user.getUsername());
                bioConfig.setCreado(MovilidadUtil.fechaCompletaHoy());
                bioConfig.setEstadoReg(0);
                biometricoConfigFacadeLocal.create(bioConfig);
                PrimeFaces.current().executeScript("PF('dlgNuevoParametro').clearFilters();");
                PrimeFaces.current().executeScript("PF('dlgNuevoParametro').hide();");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en Generar Horas Reales");
        }
    }

    public void guardarParametro() {

    }

    public void onCellEdit(CellEditEvent event) {
        try {
            BiometricoConfig bioCon = (BiometricoConfig) ((DataTable) event.getComponent()).getRowData();
            if (bioCon != null) {
                if (cNuevoVal != null) {
                    if (event.getColumn().getAriaHeaderText().equals("1")) { // 1 para la columna de concepto
                        bioCon.setConcepto(cNuevoVal);
                    }
                    if (event.getColumn().getAriaHeaderText().equals("2")) { // 2 para valor
                        bioCon.setValor(cNuevoVal);
                    }
                    biometricoConfigFacadeLocal.edit(bioCon);
                    MovilidadUtil.addSuccessMessage("Operación realizada con éxito");
                    return;
                }
                MovilidadUtil.addErrorMessage("No se puede realizar esta operación");
            } else {
                MovilidadUtil.addErrorMessage("No se puede realizar esta operación");
            }
            cNuevoVal = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BiometricoConfig> getListBiometricoConfig() {
        listBiometricoConfig = biometricoConfigFacadeLocal.findAllActivos(0, pau.getIdParamArea().getIdParamArea());
        return listBiometricoConfig;
    }

    public void setListBiometricoConfig(List<BiometricoConfig> listBiometricoConfig) {
        this.listBiometricoConfig = listBiometricoConfig;
    }

    public String getcNuevoVal() {
        return cNuevoVal;
    }

    public void setcNuevoVal(String cNuevoVal) {
        this.cNuevoVal = cNuevoVal;
    }

    //singleton
    public BiometricoConfig getBioConfig(){
        if (bioConfig == null) {
            bioConfig = new BiometricoConfig();
        }
        return bioConfig;
    }

    public void setBiometricoConfig(BiometricoConfig biometricoConfig) {
        this.bioConfig = biometricoConfig;
    }

}
