package com.movilidad.jsf;

import com.movilidad.security.UserExtended;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import com.movilidad.ejb.ActividadColFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.PrgTc;
import com.movilidad.model.planificacion_recursos.ActividadCol;
import java.util.ArrayList;

/**
 *
 * @author luis.lancheros
 */
@Named(value = "prgCalendarioCapacitacionesJSF")
@ViewScoped
public class PrgCalendarioCapacitacionesJSF implements Serializable {

    @EJB
    private ActividadColFacadeLocal actividadEspEJB;
    @EJB
    private PrgTcFacadeLocal prgTcEJB;

    private List<ActividadCol> listActividadesEsp;
    private List<PrgTc> listPrgTc;

    private boolean bFlag;
    private ScheduleModel eventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();
    private String estado;
    private List<Empleado> listEmpleados; // Lista de empleados
    private Empleado selectedEmpleado;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public PrgCalendarioCapacitacionesJSF() {
        eventModel = new DefaultScheduleModel();
    }

    @PostConstruct
    public void init() {

        listActividadesEsp = actividadEspEJB.findAll();
        eventModel = new DefaultScheduleModel();
        generarEventos();
    }

    public void buscarTareas() {

    }

    public void generarEventos() {
        Map<String, DefaultScheduleEvent> eventosAgrupados = new HashMap<>();

        for (ActividadCol e : listActividadesEsp) {
            listPrgTc = prgTcEJB.findByEmpleadoByTarea(e.getFechaIni(),
                    e.getHoraIni(), e.getHoraFin(), e.getDescripcion());

            Date fechaCompleta = combinarFechaYHora(e.getFechaIni(), e.getHoraIni());
            Date fechaDuracion = combinarFechaYHora(e.getFechaFin(), e.getHoraFin());

            String clave = e.getDescripcion() + "-" + e.getPlaRecuLugar().getLugar() + "-" + fechaCompleta.getTime();

            if (eventosAgrupados.containsKey(clave)) {
                DefaultScheduleEvent eventoExistente = eventosAgrupados.get(clave);
                if (e.getEmpleado() != null) {
                    String nuevaData = eventoExistente.getData().toString() + "\n"
                            + e.getEmpleado().getIdentificacion() + " - " + e.getEmpleado().getNombresApellidos() + " - " + e.getEstado();
                    eventoExistente.setData(nuevaData);
                }
            } else {
                String estiloEstado = "estado-" + e.getEstado() + "-style";
                DefaultScheduleEvent nuevoEvento = new DefaultScheduleEvent(
                        e.getDescripcion() + " - " + e.getPlaRecuLugar().getLugar(),
                        fechaCompleta,
                        fechaDuracion,
                        estiloEstado);

                StringBuilder data = new StringBuilder();
                if (e.getEmpleado() != null) {
                    data.append("\n")
                            .append(e.getEmpleado().getIdentificacion())
                            .append(" - ")
                            .append(e.getEmpleado().getNombresApellidos())
                            .append(" - ")
                            .append(e.getEstado());
                    
                }

                if (listPrgTc != null && !listPrgTc.isEmpty()) {
                    for (PrgTc prgTc : listPrgTc) {
                        if (prgTc.getIdEmpleado() != null) {
                            data.append("\n")
                                    .append(prgTc.getIdEmpleado().getIdentificacion())
                                    .append(" - ")
                                    .append(prgTc.getIdEmpleado().getNombresApellidos())
                                    .append(" - ")
                                    .append("PROGRAMADO");
                        }
                    }
                }

                nuevoEvento.setData(data.toString());
                eventosAgrupados.put(clave, nuevoEvento);
            }

        }

        for (DefaultScheduleEvent evento : eventosAgrupados.values()) {
            eventModel.addEvent(evento);
        }
    }

    public void onEventSelect(SelectEvent selectEvent) {
    event = (ScheduleEvent) selectEvent.getObject();
    
    listEmpleados = new ArrayList<>();

    if (event.getData() != null) {
        String[] lines = event.getData().toString().split("\n");
        for (String line : lines) {
            // Verifica si la línea contiene un empleado
            if (line.contains("-")) {
                String[] parts = line.split("-");
                if (parts.length >= 3) {
                    String identificacion = parts[0].trim();
                    String nombresApellidos = parts[1].trim();
                    String estado = parts[2].trim();
                    String estadoTexto = convertirEstado(estado);

                    // Crea un objeto Empleado con los datos y lo agrega a la lista
                    Empleado empleado = new Empleado();
                    empleado.setIdentificacion(identificacion);
                    empleado.setNombres(nombresApellidos);
                    empleado.setApellidos(estadoTexto); // Si deseas que el estado se guarde en apellidos, déjalo así

                    listEmpleados.add(empleado);
                    }
                }
            }
        }
    }
    
    private String convertirEstado(String estado) {
    switch (estado) {
        case "0":
            return "Pendiente";
        case "1":
            return "Aprobado";
        case "2":
            return "Rechazado";
        case "3":
            return "En Gestión";
        default:
            return "PROGRAMADO";
    }
}

    public static Date combinarFechaYHora(Date fecha, String hora) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);

        String[] partesHora = hora.split(":");
        int horas = Integer.parseInt(partesHora[0]);
        int minutos = Integer.parseInt(partesHora[1]);
        int segundos = Integer.parseInt(partesHora[2]);

        calendar.set(Calendar.HOUR_OF_DAY, horas);
        calendar.set(Calendar.MINUTE, minutos);
        calendar.set(Calendar.SECOND, segundos);

        return calendar.getTime();
    }

    public boolean isbFlag() {
        return bFlag;
    }

    public void setbFlag(boolean bFlag) {
        this.bFlag = bFlag;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public List<ActividadCol> getListActividadesEsp() {
        return listActividadesEsp;
    }

    public void setListActividadesEsp(List<ActividadCol> listActividadesEsp) {
        this.listActividadesEsp = listActividadesEsp;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ActividadColFacadeLocal getActividadEspEJB() {
        return actividadEspEJB;
    }

    public void setActividadEspEJB(ActividadColFacadeLocal actividadEspEJB) {
        this.actividadEspEJB = actividadEspEJB;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public PrgTcFacadeLocal getPrgTcEJB() {
        return prgTcEJB;
    }

    public void setPrgTareaEJB(PrgTcFacadeLocal prgTcEJB) {
        this.prgTcEJB = prgTcEJB;
    }

    public List<PrgTc> getListPrgTc() {
        return listPrgTc;
    }

    public void setListPrgTc(List<PrgTc> listPrgTc) {
        this.listPrgTc = listPrgTc;
    }
    
    public List<Empleado> getListEmpleados() {
        return listEmpleados;
    }

    public void setListEmpleados(List<Empleado> empleados) {
        this.listEmpleados = empleados;
    }

    public Empleado getSelectedEmpleado() {
        return selectedEmpleado;
    }

    public void setSelectedEmpleado(Empleado selectedEmpleado) {
        this.selectedEmpleado = selectedEmpleado;
    }

}
