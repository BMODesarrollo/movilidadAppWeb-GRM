package com.movilidad.jsf;

import com.movilidad.ejb.AccAbogadoFacadeLocal;
import com.movilidad.model.AccAbogado;
import com.movilidad.model.Accidente;
import com.movilidad.model.Empleado;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.ReporteUtil;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "poderBean")
@ViewScoped
public class AccPoderManagedBean implements Serializable {

    @EJB
    private AccAbogadoFacadeLocal accAbogadoEjb;
    @Inject
    private AccidenteJSF accidenteJSF;

    private Accidente accidente;
    private Empleado empleado;
    private AccAbogado abogado;

    private int idAbogado;
    private StreamedContent file;

    private List<AccAbogado> lstAbogados;

    public void onRowSelect(SelectEvent event) {
        try {
            accidente = (Accidente) event.getObject();
            empleado = accidente.getIdEmpleado();
            abogado = null;
            file = null;
            idAbogado = 0;

        } catch (Exception e) {
            System.out.println("Error al seleccionar Accidente");
        }
    }

    public void generarPoder() throws FileNotFoundException {
        try {
            if (!validar()) {
                Map parametros = new HashMap();

                parametros.put("path", "/tmp/");
                parametros.put("nomOperador", empleado != null ? (empleado.getNombres().concat(" ").concat(empleado.getApellidos())).toUpperCase() : "N/A");
                parametros.put("nomAbogado", abogado.getNombreCompleto().toUpperCase());
                parametros.put("cedulaLugar", abogado.getCedulaExpedicion());
                parametros.put("tpLugar", abogado.getTarjetaProfesionalExpedicion());
                parametros.put("placa", accidente.getIdVehiculo() != null ? accidente.getIdVehiculo().getPlaca().toUpperCase() : "N/A");
                parametros.put("ccAbogado", abogado.getCedula());
                parametros.put("tpAbogado", abogado.getTarjetaProfesional());
                parametros.put("membrete", abogado.getMembrete());
                parametros.put("membreteMayus", abogado.getMembrete().toUpperCase().charAt(0) + abogado.getMembrete().substring(1, abogado.getMembrete().length()).toLowerCase());

                String path = ReporteUtil.datosPoder(parametros);
                File pdf = new File(path);
                InputStream stream = new FileInputStream(pdf);
                file = new DefaultStreamedContent(stream, "application/pdf", "Poder_CASO_" + parametros.get("nomOperador") + "_" + Util.dateFormat(accidente.getFechaAcc()) + ".pdf");

                accidente = null;
                empleado = null;
                abogado = null;
                idAbogado = 0;
                accidenteJSF.setAccidente(null);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("Error al generar documento");
        }
    }

    public void asignarAbogado() {
        if (idAbogado == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un abogado");
            PrimeFaces.current().ajax().update(":msgs");
            return;
        }

        abogado = accAbogadoEjb.find(idAbogado);
    }

    public boolean validar() {
        if (idAbogado == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un abogado");
            PrimeFaces.current().ajax().update(":msgs");
            return true;
        }
        return false;
    }

    public void reset() {
        accidente = null;
        empleado = null;
        abogado = null;
        idAbogado = 0;
        accidenteJSF.reset();
    }

    public Accidente getAccidente() {
        return accidente;
    }

    public void setAccidente(Accidente accidente) {
        this.accidente = accidente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public AccAbogado getAbogado() {
        return abogado;
    }

    public void setAbogado(AccAbogado abogado) {
        this.abogado = abogado;
    }

    public int getIdAbogado() {
        return idAbogado;
    }

    public void setIdAbogado(int idAbogado) {
        this.idAbogado = idAbogado;
    }

    public List<AccAbogado> getLstAbogados() {
        lstAbogados = accAbogadoEjb.findAll();
        return lstAbogados;
    }

    public void setLstAbogados(List<AccAbogado> lstAbogados) {
        this.lstAbogados = lstAbogados;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }
}
