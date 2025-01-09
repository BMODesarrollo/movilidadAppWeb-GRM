package com.movilidad.jsf;

import com.movilidad.ejb.PlaRecuProcesoProDetFacadeLocal;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.model.planificacion_recursos.PlaRecuProcesoPro;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.springframework.security.core.context.SecurityContextHolder;
import com.movilidad.ejb.PlaRecuProcesoProFacadeLocal;
import com.movilidad.model.planificacion_recursos.PlaRecuProcesoProDet;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Named(value = "planRecuProcesoProBean")
@ViewScoped
public class PlaRecuProcesoProBean implements Serializable {

    @EJB
    private PlaRecuProcesoProFacadeLocal procesoProEJB;
    @EJB
    private PlaRecuProcesoProDetFacadeLocal procesoProDetEJB;

    //colecciones
    private List<PlaRecuProcesoPro> listProcesoPro;
    private List<PlaRecuProcesoProDet> listProcesoProDet;

    //atributos
    private ParamAreaUsr pau;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private Double porcentaje;
    private PlaRecuProcesoPro nuevaEtapa;
    private String nuevoDetalle;
    private Integer nuevaDuracion;

    private Map<String, Double> progresoTotalPorEtapa;
    private Set<String> etapasMostradas;

   

    @PostConstruct
    public void init() {
        listProcesoPro = procesoProEJB.findAll();
        listProcesoProDet = procesoProDetEJB.findAll();
        progresoTotalPorEtapa = calcularProgresoTotalPorEtapa();
        calcularPromedioDuracion();
        etapasMostradas = new HashSet<>();
        nuevaEtapa = new PlaRecuProcesoPro();
    }

    public boolean todosUnchecked(String descripcionEtapa) {
        return listProcesoProDet.stream()
                .filter(item -> item.getIdPlaRecuProcesoPro().getDescripcion().equals(descripcionEtapa))
                .allMatch(item -> !item.getIsChecked());
    }

    public String getProgresoPorEtapa(String descripcionEtapa) {
        double progreso = progresoTotalPorEtapa.getOrDefault(descripcionEtapa, 0.0);

        // Verifica si todos los registros de la etapa están en false
        boolean todosUnchecked = listProcesoProDet.stream()
                .filter(item -> item.getIdPlaRecuProcesoPro().getDescripcion().equals(descripcionEtapa))
                .allMatch(item -> !item.getIsChecked());

        if (todosUnchecked) {
            return "Sin progreso"; // Texto personalizado o valor predeterminado
        }

        return formatProgreso(progreso);
    }

    public Map<String, Double> calcularProgresoTotalPorEtapa() {
        Map<String, Double> progreso = new HashMap<>();

        for (PlaRecuProcesoProDet item : listProcesoProDet) {
            String descripcionEtapa = item.getIdPlaRecuProcesoPro().getDescripcion();
            double totalDuracionEtapa = getTotalDuracionPorEtapa(descripcionEtapa);

            // Solo calcular progreso si hay duración total
            if (totalDuracionEtapa > 0) {
                double progresoEtapa = (item.getDuracion() * 100.0) / totalDuracionEtapa;

                // Sumar al progreso total si está marcado
                if (item.getIsChecked()) {
                    progreso.merge(descripcionEtapa, progresoEtapa, Double::sum);
                }
            }
        }

        return progreso;
    }

    // Método para obtener la suma total de duraciones por etapa
    public double getTotalDuracionPorEtapa(String descripcionEtapa) {
        return listProcesoProDet.stream()
                .filter(item -> item.getIdPlaRecuProcesoPro().getDescripcion().equals(descripcionEtapa))
                .mapToDouble(PlaRecuProcesoProDet::getDuracion)
                .sum();
    }

    public Map<String, Double> getProgresoTotalPorEtapa() {
        return progresoTotalPorEtapa;
    }

    public void cambiarCheck(PlaRecuProcesoProDet item) {
        item.setIsChecked(!item.getIsChecked());
        item.setModificado(new Date());
        item.setUsernameEdit(user.getUsername());
        procesoProDetEJB.edit(item);

        // Recalcular progreso total y promedio después del cambio
        progresoTotalPorEtapa = calcularProgresoTotalPorEtapa();
        calcularPromedioDuracion();
    }

    public double calcularProgresoGeneral() {
        double progresoTotal = 0.0;
        int totalEtapas = 0;

        for (Map.Entry<String, Double> entry : progresoTotalPorEtapa.entrySet()) {
            progresoTotal += entry.getValue();
            totalEtapas++;
        }

        double promedio = (totalEtapas > 0) ? progresoTotal / totalEtapas : 0.0;
        return promedio * 100;
    }

    public String formatProgreso(Double valor) {
        if (valor == null) {
            return "0.00 %";
        }
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(valor) + " %";
    }

    public double calcularPromedioDuracion() {
        double sumaTotalDuracion = 0;
        double sumaDuracionChecked = 0;

        // Recorremos la lista de procesos
        for (PlaRecuProcesoProDet item : listProcesoProDet) {
            double duracion = item.getDuracion();
            sumaTotalDuracion += duracion;

            if (item.getIsChecked() == true) {
                sumaDuracionChecked += duracion;
            }
        }

        if (sumaTotalDuracion == 0) {
            return 0;
        }
        return (sumaDuracionChecked / sumaTotalDuracion) * 100;
    }

    public void guardarNuevoDetalle() {
        if (nuevaEtapa != null && nuevoDetalle != null && nuevaDuracion != null) {
            PlaRecuProcesoProDet nuevoRegistro = new PlaRecuProcesoProDet();
            nuevaEtapa = procesoProEJB.find(nuevaEtapa.getIdPlaRecuProcesoPro());
            nuevoRegistro.setIdPlaRecuProcesoPro(nuevaEtapa);
            nuevoRegistro.setDescripcion(nuevoDetalle);
            nuevoRegistro.setDuracion(nuevaDuracion);
            nuevoRegistro.setIsChecked(false); // Inicializamos como no marcado
            nuevoRegistro.setCreado(new Date());
            nuevoRegistro.setEstadoReg(0);
            nuevoRegistro.setUsernameCreate(user.getUsername());
            procesoProDetEJB.create(nuevoRegistro);

            MovilidadUtil.addSuccessMessage("Registro Creado correctamente");

            //nuevaEtapa = null;
            //nuevoDetalle = null;
            //(nuevaDuracion = null;
        } else {
            MovilidadUtil.addErrorMessage("Agregar todos los campos");
        }
    }

    public String getProgressBarClass(double value) {
        if (value < 30) {
            return "low-progress";
        } else if (value < 70) {
            return "medium-progress";
        } else {
            return "high-progress";
        }
    }

    public boolean mostrarProgreso(String etapa) {
        if (etapasMostradas.contains(etapa)) {
            return false; // Ya se mostró esta etapa
        }
        etapasMostradas.add(etapa); // Marca la etapa como mostrada
        return true; // Mostrar el progreso
    }

    public void resetEtapasMostradas() {
        etapasMostradas.clear(); // Resetea el conjunto para nuevas renderizaciones
    }

    public boolean esPrimeraFila(String etapa, int index) {
        for (int i = 0; i < index; i++) {
            if (listProcesoProDet.get(i).getIdPlaRecuProcesoPro().getDescripcion().equals(etapa)) {
                return false; // Ya se mostró esta etapa
            }
        }
        return true; // Es la primera vez que aparece esta etapa
    }

    public PlaRecuProcesoProFacadeLocal getProcesoProEJB() {
        return procesoProEJB;
    } 
    
    public Set<String> getEtapasMostradas() {
        return etapasMostradas;
    }

    public void setEtapasMostradas(Set<String> etapasMostradas) {
        this.etapasMostradas = etapasMostradas;
    }

    public void setProcesoProEJB(PlaRecuProcesoProFacadeLocal procesoProEJB) {
        this.procesoProEJB = procesoProEJB;
    }

    public PlaRecuProcesoProDetFacadeLocal getProcesoProDetEJB() {
        return procesoProDetEJB;
    }

    public void setProcesoProDetEJB(PlaRecuProcesoProDetFacadeLocal procesoProDetEJB) {
        this.procesoProDetEJB = procesoProDetEJB;
    }

    public List<PlaRecuProcesoPro> getListProcesoPro() {
        return listProcesoPro;
    }

    public void setListProcesoPro(List<PlaRecuProcesoPro> listProcesoPro) {
        this.listProcesoPro = listProcesoPro;
    }

    public List<PlaRecuProcesoProDet> getListProcesoProDet() {
        return listProcesoProDet;
    }

    public void setListProcesoProDet(List<PlaRecuProcesoProDet> listProcesoProDet) {
        this.listProcesoProDet = listProcesoProDet;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public PlaRecuProcesoPro getNuevaEtapa() {
        return nuevaEtapa;
    }

    public void setNuevaEtapa(PlaRecuProcesoPro nuevaEtapa) {
        this.nuevaEtapa = nuevaEtapa;
    }

    public String getNuevoDetalle() {
        return nuevoDetalle;
    }

    public void setNuevoDetalle(String nuevoDetalle) {
        this.nuevoDetalle = nuevoDetalle;
    }

    public Integer getNuevaDuracion() {
        return nuevaDuracion;
    }

    public void setNuevaDuracion(Integer nuevaDuracion) {
        this.nuevaDuracion = nuevaDuracion;
    }

}
