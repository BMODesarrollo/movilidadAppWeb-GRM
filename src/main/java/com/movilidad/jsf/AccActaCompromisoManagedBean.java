package com.movilidad.jsf;

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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * Permite apartir de un objeto Accidente, generar un pdf
 *
 * @author Carlos Ballestas
 */
@Named(value = "actaCompromisoBean")
@ViewScoped
public class AccActaCompromisoManagedBean implements Serializable {

    @Inject
    private AccidenteJSF accidenteJSF;

    private Accidente accidente;
    private Empleado empleado;

    private String causa;
    private String lugar;
    private Date fechaRetro;
    private Date hora;
    private StreamedContent file;

    /**
     *
     * @param event Objeto Accidente Seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        try {
            accidente = (Accidente) event.getObject();
            empleado = accidente.getIdEmpleado();
            file = null;
            causa = "";
            lugar = "";
            fechaRetro = null;
            hora = null;

        } catch (Exception e) {
            System.out.println("Error al seleccionar Accidente");
        }
    }

    /**
     * Permite generar el pdf luego de confirmar el formulario del usuario
     *
     * @throws FileNotFoundException
     */
    public void generarActa() throws FileNotFoundException {
        try {
            if (!validar()) {
                Map parametros = new HashMap();
                Calendar fecha = Calendar.getInstance();
                fecha.setTime(fechaRetro);

                int anio = fecha.get(Calendar.YEAR);
                int mes = fecha.get(Calendar.MONTH) + 1;
                int dia = fecha.get(Calendar.DAY_OF_MONTH);

                parametros.put("path", "/tmp/");
                parametros.put("dia", String.format("%02d", dia));
                parametros.put("mes", String.format("%02d", mes));
                parametros.put("anio", String.valueOf(anio));
                parametros.put("hora", Util.dateToTime(hora));
                parametros.put("lugar", lugar);
                parametros.put("causa", causa);

                String path = ReporteUtil.datosInformeCompromiso(empleado, parametros);
                File pdf = new File(path);
                InputStream stream = new FileInputStream(pdf);
                file = new DefaultStreamedContent(stream, "application/pdf", "Acta-Compromiso_CASO_" + (empleado.getNombres().concat(" ").concat(empleado.getApellidos())).toUpperCase() + "_" + Util.dateFormat(accidente.getFechaAcc()) + ".pdf");

                accidente = null;
                empleado = null;
                causa = "";
                lugar = "";
                fechaRetro = null;
                hora = null;
                accidenteJSF.setAccidente(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("Error al generar documento");
        }
    }

    /**
     * Permite validar los valores minimos que requiere el formulario.
     *
     * @return true en caso dado las validaciones sean correctas, false si una
     * validacion no se cumple
     */
    public boolean validar() {
        if (causa == null || causa.equals("")) {
            MovilidadUtil.addErrorMessage("Debe ingresar la causa de retroalimentación");
            PrimeFaces.current().ajax().update(":msgs");
            return true;
        }
        if (fechaRetro == null) {
            MovilidadUtil.addErrorMessage("Debe especificar la fecha de retroalimentación");
            PrimeFaces.current().ajax().update(":msgs");
            return true;
        }
        if (hora == null || Util.dateToTime(hora).equals("00:00")) {
            MovilidadUtil.addErrorMessage("Debe especificar hora válida");
            PrimeFaces.current().ajax().update(":msgs");
            return true;
        }
        if (lugar == null || lugar.equals("")) {
            MovilidadUtil.addErrorMessage("Lugar es requerido");
            PrimeFaces.current().ajax().update(":msgs");
            return true;
        }
        return false;
    }

    /**
     * Permite restaablecer las variables
     */
    public void reset() {
        accidente = null;
        empleado = null;
        causa = "";
        lugar = "";
        fechaRetro = new Date();
        hora = null;
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

    public Date getFechaRetro() {
        return fechaRetro;
    }

    public void setFechaRetro(Date fechaRetro) {
        this.fechaRetro = fechaRetro;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }
}
