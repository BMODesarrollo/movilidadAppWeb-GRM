package com.movilidad.jsf;

import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.SstArlFacadeLocal;
import com.movilidad.ejb.SstDocumentoEmpresaFacadeLocal;
import com.movilidad.ejb.SstEmpresaDocsFacadeLocal;
import com.movilidad.ejb.SstEmpresaFacadeLocal;
import com.movilidad.ejb.SstEmpresaTipoFacadeLocal;
import com.movilidad.ejb.SstTipoIdentificacionFacadeLocal;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.SstArl;
import com.movilidad.model.SstDocumentoEmpresa;
import com.movilidad.model.SstEmpresa;
import com.movilidad.model.SstEmpresaDocs;
import com.movilidad.model.SstEmpresaTipo;
import com.movilidad.model.SstTipoIdentificacion;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.SstDocumentoEmpresaArchivo;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ToggleEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "sstEmpresaBean")
@ViewScoped
public class SstEmpresaBean implements Serializable {

    @EJB
    private SstEmpresaFacadeLocal empresaEjb;
    @EJB
    private SstEmpresaDocsFacadeLocal empresaDocsEjb;
    @EJB
    private SstArlFacadeLocal arlEjb;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @EJB
    private SstDocumentoEmpresaFacadeLocal documentoEmpresaEjb;
    @EJB
    private SstTipoIdentificacionFacadeLocal tipoIdentificacionEjb;
    @EJB
    private SstEmpresaTipoFacadeLocal empresaTipoEjb;

    private SstEmpresa sstEmpresa;
    private SstEmpresaDocs sstEmpresaDocs;
    private SstEmpresa selected;
    private Integer i_ArchivoIndex;
    private Integer i_Arl;
    private Integer i_TipoIdRepresentante;
    private Integer i_TipoIdResponsable;
    private Integer i_TipoEmpresa;
    private String razonSocial;
    private String nitCedula;
    private String tamanoArchivo;

    private boolean isEditing;
    private boolean isEditingArchivo;
    private boolean isNuevoArchivoRe; // Nuevo archivo desde rowexpansion
    private boolean flagDocumentos;

    private List<SstEmpresa> lstSstEmpresas;
    private List<SstArl> lstSstArls;
    private List<SstEmpresaTipo> lstSstEmpresaTipos;
    private List<SstTipoIdentificacion> lstTiposIdentificacion;
    private List<SstDocumentoEmpresa> lstDocumentoEmpresas;
    private List<SstDocumentoEmpresaArchivo> lstDocumentosArchivos;

    private List<SstEmpresaDocs> lstDocumentosHistorico;

    private Map<Integer, SstDocumentoEmpresa> hMDocumentos;

    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstSstEmpresas = empresaEjb.findAllEstadoReg();
        tamanoArchivo = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_SST_EMPRESA_TAMANO);
    }

    /**
     * Realiza la carga de los datos para el registro de empresas
     */
    public void nuevo() {
        flagDocumentos = false;
        razonSocial = null;
        nitCedula = null;
        i_TipoEmpresa = null;
        i_Arl = null;
        i_TipoIdRepresentante = null;
        i_TipoIdResponsable = null;
        isEditing = false;
        isEditingArchivo = false;
        isNuevoArchivoRe = false;
        sstEmpresa = new SstEmpresa();
        sstEmpresaDocs = null;
        i_ArchivoIndex = null;
        selected = null;
        lstSstArls = arlEjb.findAll();
        lstSstEmpresaTipos = empresaTipoEjb.findAllByEstadoReg();
        lstTiposIdentificacion = tipoIdentificacionEjb.findAllEstadoReg();
        lstDocumentosArchivos = new ArrayList<>();
        lstDocumentoEmpresas = documentoEmpresaEjb.findAllTiposVigentes();

        if (lstDocumentoEmpresas != null) {
            for (SstDocumentoEmpresa lstSstEmpresa : lstDocumentoEmpresas) {
                SstEmpresaDocs empresaDoc = new SstEmpresaDocs();
                empresaDoc.setIdSstEmpresaTipoDoc(lstSstEmpresa);
                lstDocumentosArchivos.add(new SstDocumentoEmpresaArchivo(empresaDoc));
            }
        }
    }

    private void cargarTipoDocumentosEmpresa(List<SstEmpresaDocs> lista) {
        hMDocumentos = new HashMap<>();

        if (lista != null) {
            for (SstEmpresaDocs docs : lista) {
                hMDocumentos.put(docs.getIdSstEmpresaTipoDoc().getIdSstDocumentoEmpresa(), docs.getIdSstEmpresaTipoDoc());
            }
        }
    }

    /**
     * Método que carga el listado de documentos de una empresa
     *
     * @return
     */
    public List<SstEmpresaDocs> cargarDocumentos() {
        SstEmpresa sstEmpresaAux = selected;
        List<SstEmpresaDocs> lista = empresaDocsEjb.findAllActivos(sstEmpresaAux.getIdSstEmpresa());
        sstEmpresaAux.setSstEmpresaDocsList(lista);
        lstDocumentoEmpresas = documentoEmpresaEjb.findAllTiposVigentes();

        if (lista != null) {
            cargarTipoDocumentosEmpresa(lista);
        }

        if (lstDocumentoEmpresas != null) {
            for (SstDocumentoEmpresa documentoEmpresa : lstDocumentoEmpresas) {
                if (!hMDocumentos.containsKey(documentoEmpresa.getIdSstDocumentoEmpresa())) {
                    SstEmpresaDocs empresaDoc = new SstEmpresaDocs();
                    empresaDoc.setIdSstEmpresaTipoDoc(documentoEmpresa);
                    empresaDoc.setIdSstEmpresa(selected);
                    lista.add(empresaDoc);
                }
            }

            lista = lista
                    .stream()
                    .sorted((o1, o2) -> o1.getIdSstEmpresaTipoDoc().getIdSstDocumentoEmpresa().compareTo(o2.getIdSstEmpresaTipoDoc().getIdSstDocumentoEmpresa()))
                    .collect(Collectors.toList());
        }

        return lista;

    }

    /**
     * Evento que se dispara al cargar el histórico de registros de documentos
     * registrados por tipo de documento
     *
     * @param event
     */
    public void onRowToggleHistorico(ToggleEvent event) {
        SstEmpresaDocs sstEmpresaDocsAux = ((SstEmpresaDocs) event.getData());
        lstDocumentosHistorico = empresaDocsEjb.obtenerHistorico(sstEmpresaDocsAux.getIdSstEmpresaTipoDoc().getIdSstDocumentoEmpresa(), sstEmpresaDocsAux.getIdSstEmpresa().getIdSstEmpresa());
    }

    /**
     * Realiza la carga del registro a modificar en la vista de edición
     */
    public void editar() {
        isEditing = true;
        flagDocumentos = false;
        i_TipoEmpresa = selected.getIdSstEmpresaTipo().getIdSstEmpresaTipo();
        razonSocial = selected.getRazonSocial();
        nitCedula = selected.getNitCedula();
        i_TipoIdRepresentante = selected.getIdSstTipoIdentificacionRepresentante().getIdSstTipoIdentificacion();
        i_TipoIdResponsable = selected.getIdSstTipoIdentificacionResponsable().getIdSstTipoIdentificacion();
        sstEmpresa = selected;
        lstTiposIdentificacion = tipoIdentificacionEjb.findAllEstadoReg();
        i_Arl = sstEmpresa.getIdSstArl().getIdSstArl();
        lstSstArls = arlEjb.findAll();
        lstSstEmpresaTipos = empresaTipoEjb.findAllByEstadoReg();
    }

    /**
     * Persiste ó modifica un registro en base de datos y lo agrega/modifica en
     * la lista de empresas registradas
     */
    public void guardar() {
        String validacion = validarDatos(isEditing);
        if (isEditing) {
            if (validacion == null) {
                sstEmpresa.setNitCedula(nitCedula);
                sstEmpresa.setRazonSocial(razonSocial);
                sstEmpresa.setIdSstEmpresaTipo(empresaTipoEjb.find(i_TipoEmpresa));
                sstEmpresa.setIdSstArl(arlEjb.find(i_Arl));
                sstEmpresa.setIdSstTipoIdentificacionRepresentante(tipoIdentificacionEjb.find(i_TipoIdRepresentante));
                sstEmpresa.setIdSstTipoIdentificacionResponsable(tipoIdentificacionEjb.find(i_TipoIdResponsable));
                sstEmpresa.setModificado(MovilidadUtil.fechaCompletaHoy());
                sstEmpresa.setUsername(user.getUsername());
                empresaEjb.edit(sstEmpresa);
                PrimeFaces.current().executeScript("PF('wlvEmpresas').hide()");
                init();
                MovilidadUtil.addSuccessMessage("Registro modificado con éxito");
            } else {
                MovilidadUtil.addErrorMessage(validacion);
            }
        } else {
            if (validacion == null) {
                for (SstDocumentoEmpresaArchivo archivo : lstDocumentosArchivos) {
                    if (archivo.getEmpresaDoc().getIdSstEmpresaTipoDoc().getRequerido().equals(1) && archivo.getFile() == null) {
                        MovilidadUtil.addErrorMessage("DEBE realizar la carga del siguiente documento: " + archivo.getEmpresaDoc().getIdSstEmpresaTipoDoc().getTipoDocumento());

                        if (flagDocumentos) {
                            MovilidadUtil.updateComponent("frmEmpresa:docsEmpresa");
                        }

                        return;
                    }
                }

                sstEmpresa.setNitCedula(nitCedula);
                sstEmpresa.setRazonSocial(razonSocial);
                sstEmpresa.setIdSstEmpresaTipo(empresaTipoEjb.find(i_TipoEmpresa));
                sstEmpresa.setIdSstArl(arlEjb.find(i_Arl));
                sstEmpresa.setIdSstTipoIdentificacionRepresentante(tipoIdentificacionEjb.find(i_TipoIdRepresentante));
                sstEmpresa.setIdSstTipoIdentificacionResponsable(tipoIdentificacionEjb.find(i_TipoIdResponsable));
                sstEmpresa.setUsername(user.getUsername());
                sstEmpresa.setEstadoReg(0);
                sstEmpresa.setCreado(MovilidadUtil.fechaCompletaHoy());
                empresaEjb.create(sstEmpresa);

                for (SstDocumentoEmpresaArchivo archivo : lstDocumentosArchivos) {
                    SstEmpresaDocs empresaDoc = archivo.getEmpresaDoc();
                    empresaDoc.setCreado(MovilidadUtil.fechaCompletaHoy());
                    empresaDoc.setEstadoReg(0);
                    empresaDoc.setUsername(user.getUsername());
                    empresaDoc.setIdSstEmpresa(sstEmpresa);
                    empresaDoc.setModificado(MovilidadUtil.fechaCompletaHoy());
                    empresaDocsEjb.create(empresaDoc);

                    if (archivo.getFile() != null) {
                        String path = Util.saveFile(archivo.getFile(), empresaDoc.getIdSstEmpresaDocs(), "sst_documento_empresa");
                        empresaDoc.setPath(path);
                        empresaDocsEjb.edit(empresaDoc);
                    }
                }

                lstSstEmpresas.add(sstEmpresa);
                notificar();
                nuevo();
                init();
                MovilidadUtil.addSuccessMessage("Registro guardado con éxito");
            } else {
                MovilidadUtil.addErrorMessage(validacion);
            }
        }
    }

    private String validarDatos(boolean flagEdit) {
        if (flagEdit) {
            if (!sstEmpresa.getRazonSocial().trim().equals(razonSocial)) {
                if (empresaEjb.findByRazonSocial(sstEmpresa.getRazonSocial().trim()) != null) {
                    return "La empresa a modificar YA EXISTE";
                }
            }
            if (!sstEmpresa.getNitCedula().trim().equals(nitCedula)) {
                if (empresaEjb.findByNitCedula(sstEmpresa.getNitCedula().trim()) != null) {
                    return "El NIT a modificar YA EXISTE";
                }
            }

            if (i_TipoIdRepresentante == null) {
                return "Debe seleccionar un tipo de documento para el representante legal";
            }

            if (i_TipoIdResponsable == null) {
                return "Debe seleccionar un tipo de documento para el responsable de SST";
            }

        } else {

            this.validarCamposOnBlur();

            if (empresaEjb.findByRazonSocial(razonSocial.trim()) != null) {
                return "La empresa a guardar ya existe";
            }
            if (empresaEjb.findByNitCedula(nitCedula.trim()) != null) {
                return "El NIT a guardar YA EXISTE";
            }

            if (i_TipoIdRepresentante == null) {
                return "Debe seleccionar un tipo de documento para el representante legal";
            }

            if (i_TipoIdResponsable == null) {
                return "Debe seleccionar un tipo de documento para el responsable de SST";
            }
        }

        return null;
    }

    /**
     * Realiza la carga del archivo para mostrar el documento en la vista
     *
     * @param path
     * @throws Exception
     */
    public void prepDownloadLocal(String path) throws Exception {
        viewDMB.setDownload(MovilidadUtil.prepDownload(path));
    }

    /**
     * Actualiza los datos de un documento asociado a un visitante sin tener en
     * cuenta la subida de archivos
     */
    public void actualizarSinCambioDeArchivo() {
        if (isEditingArchivo) {

            if (sstEmpresaDocs.getIdSstEmpresaDocs() != null) {
                if (sstEmpresaDocs.getIdSstEmpresaTipoDoc().getVigencia() == 1) {

                    if (Util.validarFechaCambioEstado(sstEmpresaDocs.getDesde(), sstEmpresaDocs.getHasta())) {
                        MovilidadUtil.addErrorMessage("La fecha desde DEBE ser menor a la fecha fin");
                        return;
                    }

                    if (empresaDocsEjb.findByFechas(sstEmpresaDocs.getIdSstEmpresaDocs(), sstEmpresaDocs.getIdSstEmpresa().getIdSstEmpresa(), sstEmpresaDocs.getIdSstEmpresaTipoDoc().getIdSstDocumentoEmpresa(), sstEmpresaDocs.getDesde(), sstEmpresaDocs.getHasta()) != null) {
                        MovilidadUtil.addErrorMessage("Ya existe un registro para el rango de fechas indicado");
                        return;
                    }
                }
            } else {

                if (sstEmpresaDocs.getIdSstEmpresaTipoDoc().getVigencia() == 1) {

                    if (Util.validarFechaCambioEstado(sstEmpresaDocs.getDesde(), sstEmpresaDocs.getHasta())) {
                        MovilidadUtil.addErrorMessage("La fecha desde DEBE ser menor a la fecha fin");
                        return;
                    }

                    if (empresaDocsEjb.findByFechas(0, sstEmpresaDocs.getIdSstEmpresa().getIdSstEmpresa(), sstEmpresaDocs.getIdSstEmpresaTipoDoc().getIdSstDocumentoEmpresa(), sstEmpresaDocs.getDesde(), sstEmpresaDocs.getHasta()) != null) {
                        MovilidadUtil.addErrorMessage("Ya existe un registro para el rango de fechas indicado");
                        return;
                    }
                }

                sstEmpresaDocs.setActivo(1);
                sstEmpresaDocs.setEstadoReg(0);
                sstEmpresaDocs.setCreado(MovilidadUtil.fechaCompletaHoy());
                sstEmpresaDocs.setUsername(user.getUsername());
            }
        }
        sstEmpresaDocs.setModificado(MovilidadUtil.fechaCompletaHoy());
        empresaDocsEjb.edit(sstEmpresaDocs);
        cargarDocumentos();
        sstEmpresaDocs = null;
        isEditingArchivo = false;
        PrimeFaces.current().executeScript("PF('AddFilesListDialog').hide()");
        MovilidadUtil.addSuccessMessage("Registro editado éxitosamente");
    }

    /**
     * Evento que dispara la subida de archivos, para documentos de empresas
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {

        String path;

        if (isEditingArchivo) {

            if (sstEmpresaDocs.getIdSstEmpresaTipoDoc().getVigencia() == 1) {
                if (Util.validarFechaCambioEstado(sstEmpresaDocs.getDesde(), sstEmpresaDocs.getHasta())) {
                    MovilidadUtil.addErrorMessage("La fecha desde DEBE ser menor a la fecha fin");
                    return;
                }
                if (empresaDocsEjb.findByFechas(sstEmpresaDocs.getIdSstEmpresaDocs(), sstEmpresaDocs.getIdSstEmpresa().getIdSstEmpresa(), sstEmpresaDocs.getIdSstEmpresaTipoDoc().getIdSstDocumentoEmpresa(), sstEmpresaDocs.getDesde(), sstEmpresaDocs.getHasta()) != null) {
                    MovilidadUtil.addErrorMessage("Ya existe un registro para el rango de fechas indicado");
                    return;
                }
            }

            if (sstEmpresaDocs.getPath() != null) {
                Util.deleteFile(sstEmpresaDocs.getPath());
            }

            if (sstEmpresaDocs.getIdSstEmpresaDocs() != null) {
                path = Util.saveFile(event.getFile(), sstEmpresaDocs.getIdSstEmpresaDocs(), "sst_documento_empresa");
            } else {
                sstEmpresaDocs.setActivo(1);
                sstEmpresaDocs.setEstadoReg(0);
                sstEmpresaDocs.setCreado(MovilidadUtil.fechaCompletaHoy());
                empresaDocsEjb.create(sstEmpresaDocs);
                path = Util.saveFile(event.getFile(), sstEmpresaDocs.getIdSstEmpresaDocs(), "sst_documento_empresa");
            }
            sstEmpresaDocs.setUsername(user.getUsername());
            sstEmpresaDocs.setPath(path);
            actualizarSinCambioDeArchivo();
            return;
        }

        if (isNuevoArchivoRe) {

            if (sstEmpresaDocs.getIdSstEmpresaTipoDoc().getVigencia() == 1) {
                if (Util.validarFechaCambioEstado(sstEmpresaDocs.getDesde(), sstEmpresaDocs.getHasta())) {
                    MovilidadUtil.addErrorMessage("La fecha desde DEBE ser menor a la fecha fin");
                    return;
                }
                if (empresaDocsEjb.findByFechas(0, sstEmpresaDocs.getIdSstEmpresa().getIdSstEmpresa(), sstEmpresaDocs.getIdSstEmpresaTipoDoc().getIdSstDocumentoEmpresa(), sstEmpresaDocs.getDesde(), sstEmpresaDocs.getHasta()) != null) {
                    MovilidadUtil.addErrorMessage("Ya existe un registro para el rango de fechas indicado");
                    return;
                }
            }

            SstEmpresaDocs empresaDocsAux = empresaDocsEjb.findUtimoDocumentoActivo(sstEmpresaDocs.getIdSstEmpresaTipoDoc().getIdSstDocumentoEmpresa(), sstEmpresaDocs.getIdSstEmpresa().getIdSstEmpresa());

            if (empresaDocsAux != null) {
                empresaDocsAux.setActivo(0);
                empresaDocsEjb.edit(empresaDocsAux);
            }

            sstEmpresaDocs.setActivo(1);
            sstEmpresaDocs.setEstadoReg(0);
            sstEmpresaDocs.setUsername(user.getUsername());
            sstEmpresaDocs.setCreado(MovilidadUtil.fechaCompletaHoy());
            empresaDocsEjb.create(sstEmpresaDocs);
            path = Util.saveFile(event.getFile(), sstEmpresaDocs.getIdSstEmpresaDocs(), "sst_documento_empresa");
            sstEmpresaDocs.setPath(path);
            empresaDocsEjb.edit(sstEmpresaDocs);

            cargarDocumentos();

            MovilidadUtil.hideModal("AddFilesListDialog");
            MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");

            return;
        }

        lstDocumentosArchivos.get(i_ArchivoIndex).setFile(event.getFile());
        PrimeFaces.current().executeScript("PF('AddFilesListDialog').hide()");
        MovilidadUtil.addSuccessMessage("Archivo cargado éxitosamente");
    }

    /**
     * Cancela la subida de un documento
     *
     * @param id
     */
    public void cancelarArchivo(Integer id) {
        SstDocumentoEmpresaArchivo obj = lstDocumentosArchivos.get(id);
        obj.setFile(null);
        obj.getEmpresaDoc().setNumero(null);
        obj.getEmpresaDoc().setDesde(null);
        obj.getEmpresaDoc().setHasta(null);
        MovilidadUtil.addSuccessMessage("Archivo cancelado éxitosamente");
    }

    /**
     * Método que se ejecuta al cerrar modal que realiza la carga de archivos
     */
    public void onCloseAddFileModal() {
        isEditingArchivo = false;
        isNuevoArchivoRe = false;
    }

    /**
     * Valida si se muestra el listado de documentos al crear una empresa
     *
     * @return
     */
    public boolean mostrarListaDocumentos() {
        return (isEditing == false && flagDocumentos == true);
    }

    /**
     * Método que valida los si los campos se han diligenciado para luego
     * mostrar la tabla en donde se cargan los documentos
     */
    public void validarCamposOnBlur() {
        this.flagDocumentos = false;

        if (nitCedula == null || nitCedula.isEmpty()) {
            return;
        }
        if (razonSocial == null || razonSocial.isEmpty()) {
            return;
        }
        if (sstEmpresa.getDireccionEmpresa() == null || sstEmpresa.getDireccionEmpresa().isEmpty()) {
            return;
        }
        if (sstEmpresa.getTelefonoEmpresa() == null || sstEmpresa.getTelefonoEmpresa().isEmpty()) {
            return;
        }
        if (sstEmpresa.getEmailEmpresa() == null || sstEmpresa.getEmailEmpresa().isEmpty()) {
            return;
        }
        if (i_Arl == null) {
            return;
        }
        if (i_TipoEmpresa == null) {
            return;
        }
        if (i_TipoIdRepresentante == null) {
            return;
        }
        if (sstEmpresa.getNumeroDocRepresentante() == null || sstEmpresa.getNumeroDocRepresentante().isEmpty()) {
            return;
        }
        if (sstEmpresa.getNombreRepresentante() == null || sstEmpresa.getNombreRepresentante().isEmpty()) {
            return;
        }
        if (i_TipoIdResponsable == null) {
            return;
        }
        if (sstEmpresa.getNumeroDocResponsable() == null || sstEmpresa.getNumeroDocResponsable().isEmpty()) {
            return;
        }
        if (sstEmpresa.getNombreResponsable() == null || sstEmpresa.getNombreResponsable().isEmpty()) {
            return;
        }
        if (sstEmpresa.getTelefonoFijoResponsable() == null || sstEmpresa.getTelefonoFijoResponsable().isEmpty()) {
            return;
        }
        if (sstEmpresa.getTelefonoMovilResponsable() == null || sstEmpresa.getTelefonoMovilResponsable().isEmpty()) {
            return;
        }
        if (sstEmpresa.getUsrNombre() == null || sstEmpresa.getUsrNombre().isEmpty()) {
            return;
        }
        if (sstEmpresa.getEmailResponsable() == null || sstEmpresa.getEmailResponsable().isEmpty()) {
            return;
        }
        this.flagDocumentos = true;
    }

    public String validarVencimiento(Date fechaHasta) {

        if (fechaHasta != null) {
            // VAlidación para documentos YA vencidos
            if (fechaHasta.compareTo(MovilidadUtil.fechaHoy()) < 0) {
                return "cssRed texto-blanco";
            }
            // Validación para documentos próximos a vencer
            if (MovilidadUtil.getDiferenciaDia(MovilidadUtil.fechaHoy(), fechaHasta) <= 20) {
                return "cssYellow texto-blanco";
            }

        }

        return "";
    }

    /*
     * Parámetros para el envío de correo
     */
    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(Util.ID_TEMPLATE_SST_EMPRESA);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    /**
     * Realiza el envío de correo de las novedad registrada a las partes
     * interesadas
     */
    private void notificar() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("modulo", Util.getUrlContext(request) + "/public/sst/login.jsf");
        mailProperties.put("nombre_usuario", sstEmpresa.getUsrNombre());
        mailProperties.put("empresa", sstEmpresa.getRazonSocial().toUpperCase());
        String subject = "Se ha registrado la empresa " + sstEmpresa.getRazonSocial().toUpperCase();
        String destinatarios = "";

        destinatarios = sstEmpresa.getEmailEmpresa() + "," + sstEmpresa.getEmailResponsable();
        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", null);
    }

    public SstEmpresa getSstEmpresa() {
        return sstEmpresa;
    }

    public void setSstEmpresa(SstEmpresa sstEmpresa) {
        this.sstEmpresa = sstEmpresa;
    }

    public SstEmpresa getSelected() {
        return selected;
    }

    public void setSelected(SstEmpresa selected) {
        this.selected = selected;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public Integer getI_Arl() {
        return i_Arl;
    }

    public void setI_Arl(Integer i_Arl) {
        this.i_Arl = i_Arl;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNitCedula() {
        return nitCedula;
    }

    public void setNitCedula(String nitCedula) {
        this.nitCedula = nitCedula;
    }

    public List<SstEmpresa> getLstSstEmpresas() {
        return lstSstEmpresas;
    }

    public void setLstSstEmpresas(List<SstEmpresa> lstSstEmpresas) {
        this.lstSstEmpresas = lstSstEmpresas;
    }

    public List<SstArl> getLstSstArls() {
        return lstSstArls;
    }

    public void setLstSstArls(List<SstArl> lstSstArls) {
        this.lstSstArls = lstSstArls;
    }

    public List<SstDocumentoEmpresaArchivo> getLstDocumentosArchivos() {
        return lstDocumentosArchivos;
    }

    public void setLstDocumentosArchivos(List<SstDocumentoEmpresaArchivo> lstDocumentosArchivos) {
        this.lstDocumentosArchivos = lstDocumentosArchivos;
    }

    public List<SstEmpresaDocs> getLstDocumentosHistorico() {
        return lstDocumentosHistorico;
    }

    public void setLstDocumentosHistorico(List<SstEmpresaDocs> lstDocumentosHistorico) {
        this.lstDocumentosHistorico = lstDocumentosHistorico;
    }

    public Integer getI_ArchivoIndex() {
        return i_ArchivoIndex;
    }

    public void setI_ArchivoIndex(Integer i_ArchivoIndex) {
        this.i_ArchivoIndex = i_ArchivoIndex;
    }

    public SstEmpresaDocs getSstEmpresaDocs() {
        return sstEmpresaDocs;
    }

    public void setSstEmpresaDocs(SstEmpresaDocs sstEmpresaDocs) {
        this.sstEmpresaDocs = sstEmpresaDocs;
    }

    public boolean isIsEditingArchivo() {
        return isEditingArchivo;
    }

    public void setIsEditingArchivo(boolean isEditingArchivo) {
        this.isEditingArchivo = isEditingArchivo;
    }

    public boolean isFlagDocumentos() {
        return flagDocumentos;
    }

    public void setFlagDocumentos(boolean flagDocumentos) {
        this.flagDocumentos = flagDocumentos;
    }

    public Integer getI_TipoIdRepresentante() {
        return i_TipoIdRepresentante;
    }

    public void setI_TipoIdRepresentante(Integer i_TipoIdRepresentante) {
        this.i_TipoIdRepresentante = i_TipoIdRepresentante;
    }

    public Integer getI_TipoIdResponsable() {
        return i_TipoIdResponsable;
    }

    public void setI_TipoIdResponsable(Integer i_TipoIdResponsable) {
        this.i_TipoIdResponsable = i_TipoIdResponsable;
    }

    public List<SstTipoIdentificacion> getLstTiposIdentificacion() {
        return lstTiposIdentificacion;
    }

    public void setLstTiposIdentificacion(List<SstTipoIdentificacion> lstTiposIdentificacion) {
        this.lstTiposIdentificacion = lstTiposIdentificacion;
    }

    public Integer getI_TipoEmpresa() {
        return i_TipoEmpresa;
    }

    public void setI_TipoEmpresa(Integer i_TipoEmpresa) {
        this.i_TipoEmpresa = i_TipoEmpresa;
    }

    public List<SstEmpresaTipo> getLstSstEmpresaTipos() {
        return lstSstEmpresaTipos;
    }

    public void setLstSstEmpresaTipos(List<SstEmpresaTipo> lstSstEmpresaTipos) {
        this.lstSstEmpresaTipos = lstSstEmpresaTipos;
    }

    public boolean isIsNuevoArchivoRe() {
        return isNuevoArchivoRe;
    }

    public void setIsNuevoArchivoRe(boolean isNuevoArchivoRe) {
        this.isNuevoArchivoRe = isNuevoArchivoRe;
    }

    public String getTamanoArchivo() {
        return tamanoArchivo;
    }

    public void setTamanoArchivo(String tamanoArchivo) {
        this.tamanoArchivo = tamanoArchivo;
    }

}
