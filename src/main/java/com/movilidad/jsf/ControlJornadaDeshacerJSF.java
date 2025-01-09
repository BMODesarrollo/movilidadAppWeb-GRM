package com.movilidad.jsf;

import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.model.PrgSercon;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 *
 * @author Omar.beltran
 */
@Named(value = "controlJornadaDeshacerJSF")
@ViewScoped
public class ControlJornadaDeshacerJSF implements Serializable {

    @EJB
    private PrgSerconFacadeLocal prgSerconEJB;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    
    private List<PrgSercon> listPrgSercon;
    private Date dDesde;
    private Date dHasta;

    public ControlJornadaDeshacerJSF() {
    }

    @PostConstruct
    public void init() {
        listPrgSercon = new ArrayList<>();
        dDesde = new Date();
        dHasta = new Date();
    }

    public void buscarJornadas() {
        if (validarDatos(dDesde, dHasta)) {
            return;
        }
        listPrgSercon = prgSerconEJB.findByDateAndLiquidado(dDesde,
                dHasta,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(),
                Util.ID_LIQUIDADO, 0);
        if (listPrgSercon != null && listPrgSercon.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron registros entre las fechas indicadas.");
            return;
        }
        MovilidadUtil.addSuccessMessage("Registros encontrados");
        PrimeFaces.current().executeScript("PF('jornadaWV').hide()");
    }

    public void onDeshacerJornadaSeleccionada() {
        try {
            if (validarDatos(dDesde, dHasta)) {
                return;
            }
            listPrgSercon = prgSerconEJB.findByDateAndLiquidado(dDesde,
                    dHasta,
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(),
                    Util.ID_LIQUIDADO, 0);
            if (listPrgSercon != null && listPrgSercon.isEmpty()) {
                MovilidadUtil.addErrorMessage("No se encontraron registros entre las fechas indicadas.");
                reset();
                return;
            }
            for (PrgSercon p : listPrgSercon) {
                p.setLiquidado(0);
                p.setUserLiquida(null);
                prgSerconEJB.edit(p);
            }
            //Deshacer generica jornada
            MovilidadUtil.addSuccessMessage("Proceso realizado con éxito");
            reset();
        } catch (Exception e) {
            reset();
            MovilidadUtil.addErrorMessage("Error al realizar procedimiento");
        }
    }

    boolean validarDatos(Date desde, Date hasta) {
        try {
            if (MovilidadUtil.fechasIgualMenorMayor(hasta, desde, false) == 0) {
                MovilidadUtil.addErrorMessage("Fecha Hasta no puede ser inferior a Fecha Desde");
                return true;
            }
            return false;
        } catch (ParseException e) {
            return false;
        }
    }

    public void reset() {
        listPrgSercon = new ArrayList<>();
        dDesde = new Date();
        dHasta = new Date();
    }

    public String getFormatFecha(Date d) {
        return Util.dateFormat(d);
    }

    public List<PrgSercon> getListPrgSercon() {
        return listPrgSercon;
    }

    public void setListPrgSercon(List<PrgSercon> listPrgSercon) {
        this.listPrgSercon = listPrgSercon;
    }

    public Date getdDesde() {
        return dDesde;
    }

    public void setdDesde(Date dDesde) {
        this.dDesde = dDesde;
    }

    public Date getdHasta() {
        return dHasta;
    }

    public void setdHasta(Date dHasta) {
        this.dHasta = dHasta;
    }

}
