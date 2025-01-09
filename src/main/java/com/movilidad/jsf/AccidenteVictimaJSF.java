/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccidenteVehiculoFacadeLocal;
import com.movilidad.ejb.AccidenteVictimaFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.model.AccCondicion;
import com.movilidad.model.AccEps;
import com.movilidad.model.AccProfesion;
import com.movilidad.model.AccRangoEdad;
import com.movilidad.model.Accidente;
import com.movilidad.model.AccidenteConductor;
import com.movilidad.model.AccidenteVehiculo;
import com.movilidad.model.AccidenteVictima;
import com.movilidad.model.Empleado;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author HP
 */
@Named(value = "accidenteVictimaJSF")
@ViewScoped
public class AccidenteVictimaJSF implements Serializable {

    @EJB
    private AccidenteVictimaFacadeLocal accidenteVictimaFacadeLocal;
    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;
    @EJB
    private AccidenteVehiculoFacadeLocal accidenteVehiculoFacadeLocal;

    private AccidenteVictima accidenteVictima;

    private List<AccidenteVictima> listAccidenteVictima;
    private List<AccidenteVehiculo> listAccidenteVehiculo;

//    private HashMap<Integer, AccidenteVictima> mapAccidenteVictima = new HashMap<Integer, AccidenteVictima>();
    private int i_idAccidente;
    private int i_idAccCondicion;
    private int i_idAccProfesion;
    private int i_idAccRangoEdad;
    private int i_idAccidenteVehiculo;
    private int i_idAccEps;
    private Integer i_codigoOpe;
    private boolean b_flag;

    private UploadedFile file;

    private StreamedContent fileDown;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @Inject
    private AccidenteJSF accidenteJSF;
    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;

    @PostConstruct
    public void init() {
        i_idAccidente = accidenteJSF.compartirIdAccidente();
        b_flag = true;
//        List<AccidenteVictima> findAll = accidenteVictimaFacadeLocal.findAll();
//        for (AccidenteVictima avic : findAll) {
//            mapAccidenteVictima.put(avic.getIdAccidenteVictima(), avic);
//        }
        i_codigoOpe = null;
        i_idAccCondicion = 0;
        i_idAccProfesion = 0;
        i_idAccRangoEdad = 0;
        i_idAccidenteVehiculo = 0;
        i_idAccEps = 0;
        file = null;
    }

    public AccidenteVictimaJSF() {
    }

    public void guardar() {
        try {
            if (i_idAccidente != 0) {
                if (accidenteVictima != null) {
                    cargarObjetos();
                    accidenteVictima.setIdAccidente(new Accidente(i_idAccidente));
                    accidenteVictima.setCreado(new Date());
                    accidenteVictima.setModificado(new Date());
                    accidenteVictima.setUsername(user.getUsername());
                    accidenteVictima.setEstadoReg(0);
                    accidenteVictimaFacadeLocal.create(accidenteVictima);
                    if (file != null) {
                        if (file.getContents().length > 0) {
                            String path = MovilidadUtil.cargarArchivosAccidentalidad(file, i_idAccidente, "soporteConciliacion", accidenteVictima.getIdAccidenteVictima(), "");
                            accidenteVictima.setPathSoporteConciliacion(path);
                        }
                        accidenteVictimaFacadeLocal.edit(accidenteVictima);
                    }
                    MovilidadUtil.addSuccessMessage("Se guardó el Accidente Victima correctamente");
                    reset();
                }
                return;
            }
            MovilidadUtil.addErrorMessage("No se puede realizar esta acción, no se seleccionó un accidente");
        } catch (Exception e) {
            System.out.println("Error en guardar Accidente Vehiculo");
        }
    }

    public void editar() {
        try {
            if (accidenteVictima != null) {
                cargarObjetos();
                String oldRuta = accidenteVictima.getPathSoporteConciliacion() != null ? accidenteVictima.getPathSoporteConciliacion() : null;
                if (file != null) {
                    if (file.getContents().length > 0) {
                        String path = MovilidadUtil.cargarArchivosAccidentalidad(file, i_idAccidente, "soporteConciliacion", accidenteVictima.getIdAccidenteVictima(), "");
                        accidenteVictima.setPathSoporteConciliacion(path);
                        if (oldRuta != null) {
                            Util.deleteFile(oldRuta);
                        }
                    }
                }
                accidenteVictimaFacadeLocal.edit(accidenteVictima);
                MovilidadUtil.addSuccessMessage("Se actualizó el Accidente Victima correctamente");
                reset();
            }
        } catch (Exception e) {
            System.out.println("Error en editar Accidente Vehiculo");
        }
    }

    public void eliminarLista(AccidenteVictima ac) {
        try {
            ac.setEstadoReg(1);
            accidenteVictimaFacadeLocal.edit(ac);
            MovilidadUtil.addSuccessMessage("Se elimino el Accidente Victima de la lista");
            reset();
        } catch (Exception e) {
            System.out.println("Error en editar Accidente Victima");
        }
    }

    public void prepareGuardar() {
        accidenteVictima = new AccidenteVictima();
    }

    public void prepareEditar(AccidenteVictima ac) {
        accidenteVictima = ac;
        b_flag = false;
        i_idAccCondicion = accidenteVictima.getIdAccCondicion() != null ? accidenteVictima.getIdAccCondicion().getIdAccCondicion() : 0;
        i_idAccidenteVehiculo = accidenteVictima.getIdAccidenteVehiculo() != null ? accidenteVictima.getIdAccidenteVehiculo().getIdAccidenteVehiculo() : 0;
        i_idAccProfesion = accidenteVictima.getIdAccProfesion() != null ? accidenteVictima.getIdAccProfesion().getIdAccProfesion() : 0;
        i_idAccRangoEdad = accidenteVictima.getIdAccRangoEdad() != null ? accidenteVictima.getIdAccRangoEdad().getIdAccRangoEdad() : 0;
        i_idAccEps = accidenteVictima.getIdAccEps() != null ? accidenteVictima.getIdAccEps().getIdAccEps() : 0;
    }

    public void reset() {
        accidenteVictima = null;
        i_idAccidenteVehiculo = 0;
        i_idAccCondicion = 0;
        i_codigoOpe = null;
        i_idAccProfesion = 0;
        i_idAccRangoEdad = 0;
        i_idAccEps = 0;
        b_flag = true;
        file = null;
    }

    public void buscarOperador() {
        if (i_codigoOpe == null) {
            MovilidadUtil.addErrorMessage("No se encontraron registros");
            return;
        }
        Empleado empleado = empleadoFacadeLocal.getEmpleadoCodigoTM(i_codigoOpe);
        if (empleado != null) {
            cargarOperador(empleado);
            MovilidadUtil.addSuccessMessage("Código Tm correcto para Operador");
        } else {
            MovilidadUtil.addErrorMessage("No se encontraron registros");
        }
    }

    void cargarOperador(Empleado e) {
        if (!Util.isStringNullOrEmpty(e.getIdentificacion())) {
            accidenteVictima.setCedula(e.getIdentificacion());
        }
        if (!Util.isStringNullOrEmpty(e.getNombres())) {
            accidenteVictima.setNombres(e.getNombres());
        }
        if (!Util.isStringNullOrEmpty(e.getApellidos())) {
            accidenteVictima.setApellidos(e.getApellidos());
        }
        if (!Util.isStringNullOrEmpty(e.getTelefonoFijo())) {
            accidenteVictima.setTelefono(e.getTelefonoFijo());
        }
        if (!Util.isStringNullOrEmpty(e.getTelefonoMovil())) {
            accidenteVictima.setCelular(e.getTelefonoMovil());
        }
        if (!Util.isStringNullOrEmpty(e.getDireccion())) {
            accidenteVictima.setDireccion(e.getDireccion());
        }
//        if (!Util.isStringNullOrEmpty(e.getFechaNcto() != null) {
//            accidenteVictima.setFechaNcto(e.getFechaNcto());
//        }
//        if (e.getGenero() != null) {
//            accidenteVictima.setGenero(e.getGenero());
//        }
    }

    void cargarObjetos() {
        if (i_idAccCondicion != 0) {
            accidenteVictima.setIdAccCondicion(new AccCondicion(i_idAccCondicion));
        }
        if (i_idAccidenteVehiculo != 0) {
            accidenteVictima.setIdAccidenteVehiculo(new AccidenteVehiculo(i_idAccidenteVehiculo));
        }
        if (i_idAccProfesion != 0) {
            accidenteVictima.setIdAccProfesion(new AccProfesion(i_idAccProfesion));
        }
        if (i_idAccRangoEdad != 0) {
            accidenteVictima.setIdAccRangoEdad(new AccRangoEdad(i_idAccRangoEdad));
        }
        if (i_idAccEps != 0) {
            accidenteVictima.setIdAccEps(new AccEps(i_idAccEps));
        }
    }

    public void createVictimaFromConductor(AccidenteConductor ac) {
        int idAcc = accidenteJSF.compartirIdAccidente();
        if (idAcc == 0) {
            return;
        }
        if (ac == null) {
            return;
        }
        if (Util.isStringNullOrEmpty(ac.getCedula())) {
            return;
        }
        AccidenteVictima av = accidenteVictimaFacadeLocal.findAccidenteVictimaByCedulaAndIdAcc(ac.getCedula(), idAcc);
        if (av != null) {
            return;
        }
        Date d = new Date();
        av = new AccidenteVictima();
        av.setNombres(ac.getNombres());
        av.setApellidos(ac.getApellidos());
        av.setCedula(ac.getCedula());
        av.setCelular(ac.getCelular());
        av.setIdAccidenteVehiculo(ac.getIdAccidenteVehiculo());
        av.setDireccion(ac.getDireccion());
        av.setTelefono(ac.getTelefono());
        av.setVersion(ac.getVersion());
        av.setIdAccidente(ac.getIdAccidente());
        av.setCreado(d);
        av.setModificado(d);
        av.setUsername(user.getUsername());
        av.setEstadoReg(0);
        accidenteVictimaFacadeLocal.create(av);
    }

    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
        if (file != null) {
            PrimeFaces.current().executeScript("PF('soporteConciliacionDlg').hide();");
            MovilidadUtil.addSuccessMessage("Archivo cargado correctamente");
            PrimeFaces.current().ajax().update("accidente-form:msg");
        } else {
            PrimeFaces.current().executeScript("PF('soporteConciliacionDlg').hide();");
            MovilidadUtil.addErrorMessage("Error al leer el archivo");
            PrimeFaces.current().ajax().update("accidente-form:msg");
        }
    }

    public void prepDownloadLocal(String path) throws Exception {
        viewDMB.setDownload(MovilidadUtil.prepDownload(path));
        fileDown = MovilidadUtil.prepDownload(path);
    }

    public StreamedContent getFileDown() {
        return fileDown;
    }

    public void setFileDown(StreamedContent fileDown) {
        this.fileDown = fileDown;
    }

    public AccidenteVictima getAccidenteVictima() {
        return accidenteVictima;
    }

    public void setAccidenteVictima(AccidenteVictima accidenteVictima) {
        this.accidenteVictima = accidenteVictima;
    }

    public List<AccidenteVictima> getListAccidenteVictima() {
        i_idAccidente = accidenteJSF.compartirIdAccidente();
        listAccidenteVictima = accidenteVictimaFacadeLocal.estadoReg(i_idAccidente);
        return listAccidenteVictima;
    }

    public List<AccidenteVehiculo> getListAccidenteVehiculo() {
        i_idAccidente = accidenteJSF.compartirIdAccidente();
        listAccidenteVehiculo = accidenteVehiculoFacadeLocal.estadoReg(i_idAccidente);
        return listAccidenteVehiculo;
    }

    public int getI_idAccidente() {
        return i_idAccidente;
    }

    public void setI_idAccidente(int i_idAccidente) {
        this.i_idAccidente = i_idAccidente;
    }

    public int getI_idAccCondicion() {
        return i_idAccCondicion;
    }

    public void setI_idAccCondicion(int i_idAccCondicion) {
        this.i_idAccCondicion = i_idAccCondicion;
    }

    public int getI_idAccProfesion() {
        return i_idAccProfesion;
    }

    public void setI_idAccProfesion(int i_idAccProfesion) {
        this.i_idAccProfesion = i_idAccProfesion;
    }

    public int getI_idAccRangoEdad() {
        return i_idAccRangoEdad;
    }

    public void setI_idAccRangoEdad(int i_idAccRangoEdad) {
        this.i_idAccRangoEdad = i_idAccRangoEdad;
    }

    public int getI_idAccidenteVehiculo() {
        return i_idAccidenteVehiculo;
    }

    public void setI_idAccidenteVehiculo(int i_idAccidenteVehiculo) {
        this.i_idAccidenteVehiculo = i_idAccidenteVehiculo;
    }

    public int getI_idAccEps() {
        return i_idAccEps;
    }

    public void setI_idAccEps(int i_idAccEps) {
        this.i_idAccEps = i_idAccEps;
    }

    public Integer getI_codigoOpe() {
        return i_codigoOpe;
    }

    public void setI_codigoOpe(Integer i_codigoOpe) {
        this.i_codigoOpe = i_codigoOpe;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public boolean isB_flag() {
        return b_flag;
    }

    @FacesConverter("victimaConverter")
    public static class AccidenteVictimaConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AccidenteVictimaJSF controller = (AccidenteVictimaJSF) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "accidenteVictimaJSF");
            return controller.accidenteVictimaFacadeLocal.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof AccidenteVictima) {
                AccidenteVictima o = (AccidenteVictima) object;
                return getStringKey(o.getIdAccidenteVictima());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AccidenteVictima.class.getName()});
                return null;
            }
        }

    }

}
