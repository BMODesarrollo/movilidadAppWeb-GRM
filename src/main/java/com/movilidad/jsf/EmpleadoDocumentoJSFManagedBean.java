/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoDocumentosFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.EmpleadoTipoDocumentosFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.EmpleadoDocumentos;
import com.movilidad.model.EmpleadoTipoDocumentos;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.EmpleadoDocumentosArchivo;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.inject.Inject;
import javax.faces.view.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Soluciones IT
 */
@Named(value = "emplDocJSFMB")
@ViewScoped
public class EmpleadoDocumentoJSFManagedBean implements Serializable {

    private EmpleadoDocumentosArchivo selected;
    private Empleado empleado;
    private EmpleadoTipoDocumentos emplTDoc;
    private int i_idEmplTipoDoc;
    private boolean flag_edit;

    private DefaultStreamedContent download;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @Inject
    private EmpleadoListJSFManagedBean emplListMB;

    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;

    private UploadedFile file;

    private List<EmpleadoDocumentos> listEmplDoc;
    private List<Empleado> listEmpls;
    private List<EmpleadoTipoDocumentos> listEmplTipoDoc;
    private List<EmpleadoDocumentosArchivo> listaDocs;
    private List<EmpleadoDocumentosArchivo> listaDocsAux;

    @EJB
    private EmpleadoTipoDocumentosFacadeLocal emplTipoDocEJB;
    @EJB
    private EmpleadoDocumentosFacadeLocal emplDocEJB;

    @EJB
    private EmpleadoFacadeLocal emplEJB;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    /**
     * Creates a new instance of EmpleadoDocumentosJSFManagedBean
     */
    public EmpleadoDocumentoJSFManagedBean() {
        listEmplDoc = new ArrayList();
        listEmpls = new ArrayList();
        listEmplTipoDoc = new ArrayList();
        this.selected = null;
    }

    @PostConstruct
    public void init() {
        if (MovilidadUtil.validarUrl("empleado/empledoDocumentosTabla")) {
            consultar();
        }
    }

    public String getEmplString() {
        if (empleado == null) {
            return "";
        }
        if (empleado.getIdEmpleado() == null) {
            return "";
        }
        return (String) (empleado.getCodigoTm() == null
                ? empleado.getIdentificacion() : empleado.getCodigoTm().toString())
                + " " + empleado.getNombres() + " " + empleado.getApellidos();
    }

    public void agregar() {
        if (validar(selected)) {
            return;
        }
        listaDocsAux.add(selected);
        MovilidadUtil.hideModal("EmpleadoCreateDlg_wv");

    }

    public void consultar() {
        this.listEmplDoc = this.emplDocEJB.findAllActivosUF(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void prepareListEmpleados() {
        this.empleado = new Empleado();
    }

    public void prepareTipoDoc() {
        this.emplTDoc = new EmpleadoTipoDocumentos();
    }

    public void prepareCreate() {
        flag_edit = false;
        empleado = new Empleado();
        listaDocs = new ArrayList<>();
        listaDocsAux = new ArrayList<>();
    }

    public void openModalEmpleadoList() {
        MovilidadUtil.openModal("wvEmpleadoListDialog");
        emplListMB.setListEmpls(emplEJB.findAllEmpleadosActivos(0));
        emplListMB.setModulo(ConstantsUtil.EMPlDOC);
        emplListMB.setForm("EmpleadoDocuGestionForm:empledoId,EmpleadoDocuGestionForm:id_uf");
    }

    public void onRowDblClckSelectTD(final SelectEvent event) {
        setEmplTDoc((EmpleadoTipoDocumentos) event.getObject());
        MovilidadUtil.updateComponent("EmpleadoDocuCreateForm:PGrid");
        MovilidadUtil.hideModal("TipoDocuListDialog");
    }

    public void prepDownloadLocal(String path) throws Exception {
        viewDMB.setDownload(MovilidadUtil.prepDownload(path));
    }

    public void cargarTiposCargos() {
        listaDocs = new ArrayList<>();
        EmpleadoDocumentosArchivo documentosArchivo;
        this.listEmplTipoDoc = this.emplTipoDocEJB.findByIdCargo(empleado.getIdEmpleadoCargo().getIdEmpleadoTipoCargo());
        for (EmpleadoTipoDocumentos tipoDoc : listEmplTipoDoc) {
            EmpleadoDocumentos obj = emplDocEJB.findByIdEmpleadoAndIdTipoDocu(empleado.getIdEmpleado(),
                    tipoDoc.getIdEmpleadoTipoDocumento());
            documentosArchivo = new EmpleadoDocumentosArchivo();
            if (obj != null) {
                documentosArchivo.setEmpleadoDocumentos(obj);
            } else {
                documentosArchivo.setEmpleadoDocumentos(new EmpleadoDocumentos());
                documentosArchivo.getEmpleadoDocumentos().setIdEmpleado(empleado);
                documentosArchivo.getEmpleadoDocumentos().setEstadoReg(0);
                documentosArchivo.getEmpleadoDocumentos().setUsuario(user.getUsername());
                documentosArchivo.getEmpleadoDocumentos().setIdEmpleadoTipoDocumento(tipoDoc);
            }
            listaDocs.add(documentosArchivo);
        }
        if (listEmplTipoDoc.isEmpty()) {
            MovilidadUtil.addAdvertenciaMessage("No hay tipos de documentos configurados al tipo cargo del empleado");
            MovilidadUtil.updateComponent("msgs");
            MovilidadUtil.updateComponent("EmpleadoDocuGestionForm:msgs_gestion");
        }
        MovilidadUtil.updateComponent("EmpleadoDocuGestionForm:dt_docu_cargo");

    }

    public boolean validar(EmpleadoDocumentosArchivo param) {
        if (param.getEmpleadoDocumentos().getIdEmpleado() == null) {
            MovilidadUtil.addErrorMessage("Se debe Seleecionar un Empleado");
            return true;
        }
        if (param.getEmpleadoDocumentos().getIdEmpleadoTipoDocumento() == null) {
            MovilidadUtil.addErrorMessage("Se debe Seleecionar un Tipo de Documento");
            return true;
        }
        if (param.getArchivo() == null) {
            MovilidadUtil.addErrorMessage("Se debe Cargar el documento");
            return true;
        }
        if (param.getEmpleadoDocumentos().getIdEmpleadoTipoDocumento().getRadicado() == 1) {
            if (param.getEmpleadoDocumentos().getRadicado() != null) {
                if (param.getEmpleadoDocumentos().getRadicado().isEmpty()) {
                    MovilidadUtil.addErrorMessage("Radicado obligatorio");
                    return true;
                }
                if (param.getEmpleadoDocumentos().getFechaRadicado() == null) {
                    MovilidadUtil.addErrorMessage("Radicado obligatorio");
                    return true;
                }
            } else {
                MovilidadUtil.addErrorMessage("Radicado obligatorio");
                return true;
            }
        }
        if (param.getEmpleadoDocumentos().getIdEmpleadoTipoDocumento().getVencimiento() == 1) {
            if (param.getEmpleadoDocumentos().getVigenteDesde() == null) {
                MovilidadUtil.addErrorMessage("Fecha desde obligatoria");
                return true;
            }
            if (param.getEmpleadoDocumentos().getVigenteHasta() == null) {
                MovilidadUtil.addErrorMessage("Fecha hasta obligatoria");
                return true;
            }
            try {
                int valor = MovilidadUtil.fechasIgualMenorMayor(
                        param.getEmpleadoDocumentos().getVigenteDesde(),
                        param.getEmpleadoDocumentos().getVigenteHasta(), false);
                if (valor == 2) {
                    MovilidadUtil.addErrorMessage("La fecha desde no puede ser menor a la fecha hasta");
                    return true;
                }
            } catch (ParseException e) {
                MovilidadUtil.addErrorMessage("La fecha desde no puede ser menor a la fecha hasta");
                return true;
            }
            if (emplDocEJB.findByIdEmpleadoAndIdTipoDocuAndFechas(
                    param.getEmpleadoDocumentos().getIdEmpleado().getIdEmpleado(),
                    param.getEmpleadoDocumentos().getIdEmpleadoTipoDocumento()
                            .getIdEmpleadoTipoDocumento(), param.getEmpleadoDocumentos().getVigenteDesde(),
                    param.getEmpleadoDocumentos().getVigenteHasta()) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un registro con fechas similares");
                return true;
            }
        }
        return false;
    }

    public void guardar() throws IOException, ParseException {
//        validarFechaRadicado();
//        validarFechaVencimiento();

        for (EmpleadoDocumentosArchivo obj : listaDocsAux) {
            obj.getEmpleadoDocumentos().setCreado(MovilidadUtil.fechaCompletaHoy());
            obj.getEmpleadoDocumentos().setEstadoReg(0);
            obj.getEmpleadoDocumentos().setActivo(true);
            obj.getEmpleadoDocumentos().setIdGopUnidadFuncional(
                    obj.getEmpleadoDocumentos().getIdEmpleado().getIdGopUnidadFuncional());
            if (obj.getEmpleadoDocumentos().getIdEmpleadoDocumento() != null) {
                obj.getEmpleadoDocumentos().setIdEmpleadoDocumento(null);
            }
            emplDocEJB.create(obj.getEmpleadoDocumentos());

            String path = Util.getProperty("empleadoDocumentos.dir")
                    + obj.getEmpleadoDocumentos().getIdEmpleado().getIdEmpleado() + "/";

            if (Util.crearDirectorio(path)) {
                path = path + Util.generarNombre(obj.getArchivo().getFileName());
                Util.saveFile(obj.getArchivo(), path, false);
            }
            obj.getEmpleadoDocumentos().setPathDocumento(path);
            emplDocEJB.edit(obj.getEmpleadoDocumentos());
            boolean vigencia = obj.getEmpleadoDocumentos().getIdEmpleadoTipoDocumento()
                    .getVencimiento() == 1;
            emplDocEJB.updateUltimoDocActivo(
                    obj.getEmpleadoDocumentos()
                            .getIdEmpleadoTipoDocumento()
                            .getIdEmpleadoTipoDocumento(),
                    obj.getEmpleadoDocumentos().getIdEmpleado().getIdEmpleado(),
                    obj.getEmpleadoDocumentos().getIdEmpleadoDocumento(), vigencia);

        }

        MovilidadUtil.addSuccessMessage("Accion completada con exito.");
        MovilidadUtil.hideModal("EmpleadoGestionDlg_wv");
        consultar();
    }

    public void guardarEdit() throws IOException, ParseException {
        String newPath = "";
        boolean resultDelete = false;

        if (selected.getArchivo() != null) {

            newPath = Util.getProperty("empleadoDocumentos.dir")
                    + selected.getEmpleadoDocumentos().getIdEmpleado().getIdEmpleado() + "/";

            if (Util.crearDirectorio(newPath)) {
                newPath = newPath + Util.generarNombre(selected.getArchivo().getFileName());
                Util.saveFile(selected.getArchivo(), newPath, false);
            }
        }
        if (!"".equals(newPath)) {
            resultDelete = MovilidadUtil.eliminarFichero(selected.getEmpleadoDocumentos().getPathDocumento());
            selected.getEmpleadoDocumentos().setPathDocumento(newPath);
        }

        selected.getEmpleadoDocumentos().setUsuario(user.getUsername());
        selected.getEmpleadoDocumentos().setModificado(MovilidadUtil.fechaCompletaHoy());
        try {
            this.emplDocEJB.edit(selected.getEmpleadoDocumentos());
            MovilidadUtil.addSuccessMessage("Se modificó el registro exitosamente");
            MovilidadUtil.hideModal("EmpleadoCreateDlg_wv");
        } catch (Exception e) {
            MovilidadUtil.addFatalMessage("Error Interno " + e.getMessage());
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
        String name = Util.generarNombre(file.getFileName());
        if (name.length() > 80) {
            MovilidadUtil.addErrorMessage("El nombre de archivo es muy extenso. Maximo 80 caracteres.");
            MovilidadUtil.updateComponent("EmpleadoDocuCreateForm:upload_file");
            return;
        }
        selected.setArchivo(file);
        MovilidadUtil.addSuccessMessage("Archivo cargado: " + file.getFileName());
    }

    public void cerrarModal() {
        MovilidadUtil.hideModal("EmpleadoCreateDialog");
        init();
    }

    public void agregar(EmpleadoDocumentosArchivo param) {
        selected = param;
        selected.getEmpleadoDocumentos().setVigenteDesde(MovilidadUtil.fechaHoy());
        MovilidadUtil.openModal("EmpleadoCreateDlg_wv");

    }

    public void editar(EmpleadoDocumentos param) {
        if (!param.isActivo()) {
            MovilidadUtil.addErrorMessage("No es posible modificar este registro, ya fue desactivado");
            return;
        }
        empleado = param.getIdEmpleado();
        flag_edit = true;
        selected = new EmpleadoDocumentosArchivo();
        selected.setEmpleadoDocumentos(param);
        MovilidadUtil.openModal("EmpleadoCreateDlg_wv");

    }

    public void validarFechaRadicado() {
        if (emplTDoc.getRadicado() == 1) {
            if (selected.getEmpleadoDocumentos().getFechaRadicado()
                    .compareTo(MovilidadUtil.fechaHoy()) < 0) {
                MovilidadUtil.addAdvertenciaMessage("La Fecha de Radicado debe ser igual o posterior al dia de hoy");
            }
        }
    }

    public void validarFechaVencimiento() {
        if (emplTDoc.getVencimiento() == 1) {
            if (selected.getEmpleadoDocumentos().getVigenteHasta()
                    .compareTo(selected.getEmpleadoDocumentos().getVigenteDesde()) <= 0) {
                MovilidadUtil.addAdvertenciaMessage("La Fecha Desde no deber ser posterior a la Fecha Hasta");
            }
        }
    }

    public List<EmpleadoDocumentos> getListEmplDeparts() {
        return listEmplDoc;
    }

    public void setListEmplDeparts(List<EmpleadoDocumentos> listEmplDoc) {
        this.listEmplDoc = listEmplDoc;
    }

    public int getI_idEmplTipoDoc() {
        return i_idEmplTipoDoc;
    }

    public void setI_idEmplTipoDoc(int i_idEmplTipoDoc) {
        this.i_idEmplTipoDoc = i_idEmplTipoDoc;
    }

    public List<EmpleadoDocumentos> getListEmplDoc() {
        return listEmplDoc;
    }

    public void setListEmplDoc(List<EmpleadoDocumentos> listEmplDoc) {
        this.listEmplDoc = listEmplDoc;
    }

    public List<Empleado> getListEmpls() {
        return listEmpls;
    }

    public void setListEmpls(List<Empleado> listEmpls) {
        this.listEmpls = listEmpls;
    }

    public List<EmpleadoTipoDocumentos> getListEmplTipoDoc() {
        return listEmplTipoDoc;
    }

    public void setListEmplTipoDoc(List<EmpleadoTipoDocumentos> listEmplTipoDoc) {
        this.listEmplTipoDoc = listEmplTipoDoc;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public EmpleadoTipoDocumentos getEmplTDoc() {
        return emplTDoc;
    }

    public void setEmplTDoc(EmpleadoTipoDocumentos emplTDoc) {
        this.emplTDoc = emplTDoc;
    }

    public DefaultStreamedContent getDownload() {
        return download;
    }

    public void setDownload(DefaultStreamedContent download) {
        this.download = download;
    }

    public EmpleadoListJSFManagedBean getEmplListMB() {
        return emplListMB;
    }

    public void setEmplListMB(EmpleadoListJSFManagedBean emplListMB) {
        this.emplListMB = emplListMB;
    }

    public List<EmpleadoDocumentosArchivo> getListaDocs() {
        return listaDocs;
    }

    public void setListaDocs(List<EmpleadoDocumentosArchivo> listaDocs) {
        this.listaDocs = listaDocs;
    }

    public EmpleadoDocumentosArchivo getSelected() {
        return selected;
    }

    public void setSelected(EmpleadoDocumentosArchivo selected) {
        this.selected = selected;
    }

    public boolean isFlag_edit() {
        return flag_edit;
    }

    public void setFlag_edit(boolean flag_edit) {
        this.flag_edit = flag_edit;
    }

}
