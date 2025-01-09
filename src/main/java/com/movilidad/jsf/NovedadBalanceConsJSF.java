/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadDano;
import com.movilidad.model.NovedadDocumentos;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 * Permite gestionar la data del objeto Novedad
 *
 * @author cesar
 */
@Named(value = "novedadBalanceConsJSF")
@ViewScoped
public class NovedadBalanceConsJSF implements Serializable {

    @EJB
    private NovedadFacadeLocal novedadFacadeLocal;
    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private List<Novedad> listNovedad;
    private List<Empleado> listEmpleado;
    private Empleado empleado;
    private Date dInicio;
    private Date dFin;
    private String loadGif = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO);

    /**
     * Creates a new instance of NovedadBalanceConsJSF
     */
    public NovedadBalanceConsJSF() {
    }

    @PostConstruct
    public void init() {
        listNovedad = new ArrayList<>();
        listEmpleado = new ArrayList<>();
        empleado = null;
        dInicio = new Date();
        dFin = new Date();
        cargarEmpleados();
    }

    public void buscarNovedades() {
        if (dFin.compareTo(dInicio) < 0) {
            MovilidadUtil.addErrorMessage("Fecha fin no puede ser inferior a fecha inicio");
            return;
        }
        if (empleado == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un empleado");
            return;
        }
        listNovedad = novedadFacadeLocal.findByDateRangeAndIdEmpleado(dInicio, dFin, empleado.getIdEmpleado());
        if (listNovedad.size() > 0) {
            MovilidadUtil.addSuccessMessage(listNovedad.size() + " Novedades encontradas");
        } else {
            MovilidadUtil.addErrorMessage("No se encontraron novedades");
        }
    }

    public List<String> getArchivoDanos(NovedadDano nd) {
        List<String> listPath = new ArrayList<>();
        if (nd.getPathFotos() != null && !nd.getPathFotos().isEmpty()) {
            char ch = nd.getPathFotos().charAt(nd.getPathFotos().length() - 1);
            if (Character.isLetterOrDigit(ch)) {
                if (comprobarSiEsFoto(nd.getPathFotos())) {
                    listPath.add(nd.getPathFotos());
                }
            } else {
                List<String> fileList = Util.getFileList(nd.getIdNovedadDano(), "danos");
                for (String path : fileList) {
                    if (comprobarSiEsFoto(path)) {
                        listPath.add(nd.getPathFotos() + path);
                    }
                }
            }
        }
        return listPath;
    }

    public List<String> getArchivoDocu(Novedad n) {
        List<String> listPath = new ArrayList<>();
        List<NovedadDocumentos> novedadDocumentosList = n.getNovedadDocumentosList();
        for (NovedadDocumentos ndoc : novedadDocumentosList) {
            if (ndoc.getPathDocumento() != null && !ndoc.getPathDocumento().isEmpty()) {
                char ch = ndoc.getPathDocumento().charAt(ndoc.getPathDocumento().length() - 1);
                if (Character.isLetterOrDigit(ch)) {
                    if (comprobarSiEsFoto(ndoc.getPathDocumento())) {
                        listPath.add(ndoc.getPathDocumento());
                    }
                } else {
                    List<String> fileList = Util.getFileList(ndoc.getIdNovedadDocumento(), "novedad_documentos");
                    for (String path : fileList) {
                        if (comprobarSiEsFoto(path)) {
                            listPath.add(ndoc.getPathDocumento() + path);
                        }
                    }
                }
            }
        }
        return listPath;
    }

    boolean comprobarSiEsFoto(String path) {
        return path.contains(".png")
                || path.contains(".PNG")
                || path.contains(".jpg")
                || path.contains(".JPG")
                || path.contains(".jpeg")
                || path.contains(".JPEG");
    }

    public void cargarEmpleados() {
        listEmpleado = empleadoFacadeLocal.findEmpleadosOperadores(
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public List<Novedad> getListNovedad() {
        return listNovedad;
    }

    public void setListNovedad(List<Novedad> listNovedad) {
        this.listNovedad = listNovedad;
    }

    public List<Empleado> getListEmpleado() {
        return listEmpleado;
    }

    public void setListEmpleado(List<Empleado> listEmpleado) {
        this.listEmpleado = listEmpleado;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Date getdInicio() {
        return dInicio;
    }

    public void setdInicio(Date dInicio) {
        this.dInicio = dInicio;
    }

    public Date getdFin() {
        return dFin;
    }

    public void setdFin(Date dFin) {
        this.dFin = dFin;
    }

    public String getLoadGif() {
        return loadGif;
    }

    public void setLoadGif(String loadGif) {
        this.loadGif = loadGif;
    }

}
