package com.movilidad.jsf;

import com.movilidad.ejb.SstDocumentoTerceroFacadeLocal;
import com.movilidad.ejb.SstEmpresaFacadeLocal;
import com.movilidad.ejb.SstEmpresaVisitanteDocsFacadeLocal;
import com.movilidad.ejb.SstEmpresaVisitanteFacadeLocal;
import com.movilidad.ejb.SstEpsFacadeLocal;
import com.movilidad.ejb.SstLaborTipoDocsFacadeLocal;
import com.movilidad.ejb.SstTipoIdentificacionFacadeLocal;
import com.movilidad.ejb.SstTipoLaborFacadeLocal;
import com.movilidad.ejb.SstVehiculoMarcaFacadeLocal;
import com.movilidad.ejb.SstVehiculoTipoFacadeLocal;
import com.movilidad.model.SstDocumentoTercero;
import com.movilidad.model.SstEmpresa;
import com.movilidad.model.SstEmpresaVisitante;
import com.movilidad.model.SstEmpresaVisitanteDocs;
import com.movilidad.model.SstEps;
import com.movilidad.model.SstLaborTipoDocs;
import com.movilidad.model.SstTipoIdentificacion;
import com.movilidad.model.SstTipoLabor;
import com.movilidad.model.SstVehiculoMarca;
import com.movilidad.model.SstVehiculoTipo;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.SstDocumentoVisitanteArchivo;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ToggleEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "sstEmpresaVisitanteBean")
@ViewScoped
public class SstEmpresaVisitanteBean implements Serializable {

    @EJB
    private SstEmpresaVisitanteFacadeLocal empresaVisitanteEjb;
    @EJB
    private SstEmpresaVisitanteDocsFacadeLocal empresaVisitanteDocsEjb;
    @EJB
    private SstEmpresaFacadeLocal empresaEjb;
    @EJB
    private SstEpsFacadeLocal epsEjb;
    @EJB
    private SstLaborTipoDocsFacadeLocal tipoDocsLaborEjb;
    @EJB
    private SstTipoLaborFacadeLocal tipoLaborEjb;
    @EJB
    private SstVehiculoTipoFacadeLocal vehiculoTipoEjb;
    @EJB
    private SstVehiculoMarcaFacadeLocal vehiculoMarcaEjb;
    @EJB
    private SstDocumentoTerceroFacadeLocal documentoTerceroEjb;
    @EJB
    private SstTipoIdentificacionFacadeLocal tipoIdentificacionEjb;

    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;

    private SstEmpresaVisitante sstEmpresaVisitante;
    private SstEmpresaVisitante selected;
    private SstEmpresaVisitanteDocs sstVisitanteDocs;
    private Integer i_sstEmpresa;
    private Integer i_sstEps;
    private Integer i_sstTipoLabor;
    private Integer i_sstVehiculoTipo;
    private Integer i_sstVehiculoMarca;
    private Integer i_tipoDocumento;
    private Integer i_ArchivoIndex;
    private String numDocumento;
    private String tamanoArchivo;

    private boolean isEditing;
    private boolean isEditingArchivo;
    private boolean isNuevoArchivoRe; // Nuevo archivo desde rowexpansion
    private boolean flagDocumentos;
    private boolean b_supervisor;

    private List<SstEmpresaVisitante> lstSstEmpresaVisitante;
    private List<SstEmpresa> lstSstEmpresas;
    private List<SstEps> lstSstEps;
    private List<SstTipoLabor> lstSstTipoLabores;
    private List<SstVehiculoTipo> lstVehiculoTipos;
    private List<SstVehiculoMarca> lstVehiculoMarcas;
    private List<SstLaborTipoDocs> lstDocumentoVisitantes;
    private List<SstDocumentoVisitanteArchivo> lstDocumentosArchivos;
    private List<SstTipoIdentificacion> lstTiposIdentificacion;

    private List<SstEmpresaVisitanteDocs> lstDocumentosHistorico;

    private Map<Integer, SstDocumentoTercero> hMDocumentos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstSstEmpresaVisitante = empresaVisitanteEjb.findAllEstadoReg();
        tamanoArchivo = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_SST_EMPRESA_VISITANTE_TAMANO);
    }

    /**
     * Realiza la carga de los datos para el registro de visitantes
     */
    public void nuevo() {
        flagDocumentos = false;
        b_supervisor = false;
        i_tipoDocumento = null;
        i_sstEmpresa = null;
        i_sstEps = null;
        i_sstTipoLabor = null;
        i_sstVehiculoTipo = null;
        i_sstVehiculoMarca = null;
        i_ArchivoIndex = null;
        isEditing = false;
        isEditingArchivo = false;
        isNuevoArchivoRe = false;
        sstEmpresaVisitante = new SstEmpresaVisitante();
        sstVisitanteDocs = null;
        selected = null;
        lstSstEmpresas = empresaEjb.findAllEstadoReg();
        lstSstEps = epsEjb.findAll();
        lstSstTipoLabores = tipoLaborEjb.findAllEstadoReg();
        lstDocumentosArchivos = new ArrayList<>();
        lstVehiculoTipos = vehiculoTipoEjb.findAll();
        lstVehiculoMarcas = vehiculoMarcaEjb.findAll();
        lstTiposIdentificacion = tipoIdentificacionEjb.findAllEstadoReg();
    }

    public void cargarDocumentosPorTipoLabor() {

        if (validarTipoLabor()) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un tipo de labor");
            flagDocumentos = false;
            return;
        }

        if (!isEditing) {
            lstDocumentoVisitantes = tipoDocsLaborEjb.findByTipoLabor(i_sstTipoLabor);
            flagDocumentos = true;

            if (lstDocumentoVisitantes != null) {
                lstDocumentosArchivos.clear();
                for (SstLaborTipoDocs laborTipoDoc : lstDocumentoVisitantes) {
                    SstEmpresaVisitanteDocs visitanteDoc = new SstEmpresaVisitanteDocs();
                    visitanteDoc.setIdSstDocumentoTercero(laborTipoDoc.getIdTipoDocTercero());
                    lstDocumentosArchivos.add(new SstDocumentoVisitanteArchivo(visitanteDoc));
                }
            } else {
                MovilidadUtil.addErrorMessage("NO se encontraron documentos asociados a la labor seleccionada");
                lstDocumentosArchivos = null;
                flagDocumentos = false;
            }
        }

    }

    private boolean validarTipoLabor() {
        return i_sstTipoLabor == null;
    }

    private void cargarTipoDocumentosVisitante(List<SstEmpresaVisitanteDocs> lista) {
        hMDocumentos = new HashMap<>();

        if (lista != null) {
            for (SstEmpresaVisitanteDocs docs : lista) {
                hMDocumentos.put(docs.getIdSstDocumentoTercero().getIdSstDocumentoTercero(), docs.getIdSstDocumentoTercero());
            }
        }
    }

    /**
     * Método que carga el listado de documentos de un visitante
     *
     * @return
     */
    public List<SstEmpresaVisitanteDocs> cargarDocumentos() {
        SstEmpresaVisitante visitante = selected;

        List<SstEmpresaVisitanteDocs> lista = empresaVisitanteDocsEjb.findAllActivos(visitante.getIdSstEmpresaVisitante());
        visitante.setSstEmpresaVisitanteDocsList(lista);
        lstDocumentoVisitantes = tipoDocsLaborEjb.findByTipoLabor(visitante.getIdSstTipoLabor().getIdSstTipoLabor());

        if (lista != null) {
            cargarTipoDocumentosVisitante(lista);
        }

        if (lstDocumentoVisitantes != null) {
            for (SstLaborTipoDocs laborTipoDoc : lstDocumentoVisitantes) {
                if (!hMDocumentos.containsKey(laborTipoDoc.getIdTipoDocTercero().getIdSstDocumentoTercero())) {
                    SstEmpresaVisitanteDocs terceroDoc = new SstEmpresaVisitanteDocs();
                    terceroDoc.setIdSstDocumentoTercero(laborTipoDoc.getIdTipoDocTercero());
                    terceroDoc.setIdSstEmpresaVisitante(selected);
                    lista.add(terceroDoc);
                }
            }
        }

        return lista;

    }

    /**
     * Evento que carga el histórico de registros de documentos registrados por
     * tipo de documento
     *
     * @param event
     */
    public void onRowToggleHistorico(ToggleEvent event) {
        SstEmpresaVisitanteDocs sstEmpresaVisitanteDocAux = ((SstEmpresaVisitanteDocs) event.getData());
        lstDocumentosHistorico = empresaVisitanteDocsEjb.obtenerHistorico(sstEmpresaVisitanteDocAux.getIdSstDocumentoTercero().getIdSstDocumentoTercero(), sstEmpresaVisitanteDocAux.getIdSstEmpresaVisitante().getIdSstEmpresaVisitante());
    }

    /**
     * Realiza la carga del registro a modificar en la vista de edición
     */
    public void editar() {
        b_supervisor = selected.getSupervisor() == 1;
        isEditing = true;
        flagDocumentos = false;
        numDocumento = selected.getNumeroDocumento();
        i_tipoDocumento = selected.getIdSstTipoIdentificacion().getIdSstTipoIdentificacion();
        sstEmpresaVisitante = selected;
        i_sstEmpresa = sstEmpresaVisitante.getIdSstEmpresa().getIdSstEmpresa();

        if (sstEmpresaVisitante.getIdSstTipoLabor() != null) {
            i_sstTipoLabor = sstEmpresaVisitante.getIdSstTipoLabor().getIdSstTipoLabor();
        }

        i_sstEps = sstEmpresaVisitante.getIdSstEps().getIdSstEps();
        i_sstVehiculoTipo = sstEmpresaVisitante.getIdSstVehiculoTipo() != null ? sstEmpresaVisitante.getIdSstVehiculoTipo().getIdSstVehiculoTipo() : null;
        i_sstVehiculoMarca = sstEmpresaVisitante.getIdSstVehiculoMarca() != null ? sstEmpresaVisitante.getIdSstVehiculoMarca().getIdSstVehiculoMarca() : null;

        lstSstEmpresas = empresaEjb.findAllEstadoReg();
        lstTiposIdentificacion = tipoIdentificacionEjb.findAllEstadoReg();
        lstSstEps = epsEjb.findAll();
        lstSstTipoLabores = tipoLaborEjb.findAllEstadoReg();
        lstVehiculoTipos = vehiculoTipoEjb.findAll();
        lstVehiculoMarcas = vehiculoMarcaEjb.findAll();
    }

    /**
     * Persiste ó modifica un registro en base de datos y lo agrega/modifica en
     * la lista de visitantes registrados
     */
    public void guardar() {
        String qr;
        String validacion = validarDatos(isEditing);
        sstEmpresaVisitante.setSupervisor(b_supervisor ? 1 : 0);
        if (isEditing) {
            if (validacion == null) {
                sstEmpresaVisitante.setIdSstEmpresa(empresaEjb.find(i_sstEmpresa));
                sstEmpresaVisitante.setIdSstEps(epsEjb.find(i_sstEps));
                sstEmpresaVisitante.setIdSstTipoLabor(tipoLaborEjb.find(i_sstTipoLabor));
                sstEmpresaVisitante.setIdSstVehiculoMarca(i_sstVehiculoMarca != null ? vehiculoMarcaEjb.find(i_sstVehiculoMarca) : null);
                sstEmpresaVisitante.setIdSstVehiculoTipo(i_sstVehiculoTipo != null ? vehiculoTipoEjb.find(i_sstVehiculoTipo) : null);
                sstEmpresaVisitante.setIdSstTipoIdentificacion(tipoIdentificacionEjb.find(i_tipoDocumento));

                qr = " EMPRESA: " + sstEmpresaVisitante.getIdSstEmpresa().getRazonSocial() + "\n";
                qr += " NOMBRE: " + sstEmpresaVisitante.getNombre() + "\n";
                qr += " APELLIDOS: " + sstEmpresaVisitante.getApellidos() + "\n";
                qr += " " + sstEmpresaVisitante.getIdSstTipoIdentificacion().getNombre().toUpperCase() + " : " + sstEmpresaVisitante.getNumeroDocumento() + "\n";

//                sstEmpresaVisitante.setHashString(generarHashString());
                sstEmpresaVisitante.setQrString(qr);
                sstEmpresaVisitante.setModificado(MovilidadUtil.fechaCompletaHoy());
                sstEmpresaVisitante.setUsername(user.getUsername());
                empresaVisitanteEjb.edit(sstEmpresaVisitante);
                PrimeFaces.current().executeScript("PF('wlvVisitante').hide()");
                init();
                MovilidadUtil.addSuccessMessage("Registro modificado con éxito");
            } else {
                MovilidadUtil.addErrorMessage(validacion);
            }
        } else {
            if (validacion == null) {

                for (SstDocumentoVisitanteArchivo archivo : lstDocumentosArchivos) {
                    if (archivo.getEmpresaVisitanteDoc().getIdSstDocumentoTercero().getRequerido().equals(1) && archivo.getFile() == null) {
                        MovilidadUtil.addErrorMessage("DEBE realizar la carga del siguiente documento: " + archivo.getEmpresaVisitanteDoc().getIdSstDocumentoTercero().getTipoDocTercero());
                        return;
                    }
                }

                sstEmpresaVisitante.setIdSstEmpresa(empresaEjb.find(i_sstEmpresa));
                sstEmpresaVisitante.setIdSstEps(epsEjb.find(i_sstEps));
                sstEmpresaVisitante.setIdSstTipoLabor(tipoLaborEjb.find(i_sstTipoLabor));
                sstEmpresaVisitante.setIdSstVehiculoMarca(i_sstVehiculoMarca != null ? vehiculoMarcaEjb.find(i_sstVehiculoMarca) : null);
                sstEmpresaVisitante.setIdSstVehiculoTipo(i_sstVehiculoTipo != null ? vehiculoTipoEjb.find(i_sstVehiculoTipo) : null);
                sstEmpresaVisitante.setIdSstTipoIdentificacion(tipoIdentificacionEjb.find(i_tipoDocumento));

                qr = " EMPRESA: " + sstEmpresaVisitante.getIdSstEmpresa().getRazonSocial() + "\n";
                qr += " NOMBRE: " + sstEmpresaVisitante.getNombre() + "\n";
                qr += " APELLIDOS: " + sstEmpresaVisitante.getApellidos() + "\n";
                qr += " " + sstEmpresaVisitante.getIdSstTipoIdentificacion().getNombre().toUpperCase() + " : " + sstEmpresaVisitante.getNumeroDocumento() + "\n";

                sstEmpresaVisitante.setQrString(qr);
                sstEmpresaVisitante.setHashString(generarHashString());

                sstEmpresaVisitante.setVisitanteAprobado(0);
                sstEmpresaVisitante.setUsername(user.getUsername());
                sstEmpresaVisitante.setEstadoReg(0);
                sstEmpresaVisitante.setCreado(MovilidadUtil.fechaCompletaHoy());
                empresaVisitanteEjb.create(sstEmpresaVisitante);

                for (SstDocumentoVisitanteArchivo archivo : lstDocumentosArchivos) {
                    SstEmpresaVisitanteDocs visitanteDoc = archivo.getEmpresaVisitanteDoc();
                    visitanteDoc.setCreado(MovilidadUtil.fechaCompletaHoy());
                    visitanteDoc.setEstadoReg(0);
                    visitanteDoc.setUsername(user.getUsername());
                    visitanteDoc.setIdSstEmpresaVisitante(sstEmpresaVisitante);
                    visitanteDoc.setModificado(MovilidadUtil.fechaCompletaHoy());
                    empresaVisitanteDocsEjb.create(visitanteDoc);

                    if (archivo.getFile() != null) {
                        String path = Util.saveFile(archivo.getFile(), visitanteDoc.getIdSstEmpresaVisitanteDocs(), "sst_documento_visitante");
                        visitanteDoc.setPath(path);
                        empresaVisitanteDocsEjb.edit(visitanteDoc);
                    }
                }

                init();
//                lstSstEmpresaVisitante.add(sstEmpresaVisitante);
                nuevo();
                MovilidadUtil.addSuccessMessage("Registro guardado con éxito");
            } else {
                MovilidadUtil.addErrorMessage(validacion);
            }
        }
    }

    public void aprobarVisitante() {
        selected.setVisitanteAprobado(1);
        selected.setFechaAprobacion(MovilidadUtil.fechaCompletaHoy());
        empresaVisitanteEjb.edit(selected);
        MovilidadUtil.addSuccessMessage("Visitante aprobado con éxito");
    }

    private String generarHashString() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Change this to UTF-16 if needed
            md.update(sstEmpresaVisitante.getNumeroDocumento().getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();

            return String.format("%064x", new BigInteger(1, digest));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CableCabinaBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private String validarDatos(boolean flagEdit) {
        if (flagEdit) {
            if (!sstEmpresaVisitante.getNumeroDocumento().trim().equals(numDocumento)) {
                if (empresaVisitanteEjb.findByNumDocumento(sstEmpresaVisitante.getNumeroDocumento()) != null) {
                    return "El visitante a modificar YA EXISTE";
                }
            }
            if (i_tipoDocumento == null) {
                return "Debe seleccionar un tipo de documento";
            }
        } else {
            if (empresaVisitanteEjb.findByNumDocumento(sstEmpresaVisitante.getNumeroDocumento().trim()) != null) {
                return "El visitante a registrar YA EXISTE";
            }
            if (i_tipoDocumento == null) {
                return "Debe seleccionar un tipo de documento";
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
     * Modifica en base de datos los campos que se requiren para los documentos
     * de los visitantes (SIN INCLUIR EL ARCHIVO A CARGAR)
     */
    public void actualizarSinCambioDeArchivo() {

        if (isEditingArchivo) {

            if (sstVisitanteDocs.getIdSstEmpresaVisitanteDocs() != null) {
                if (sstVisitanteDocs.getIdSstDocumentoTercero().getVigencia() == 1) {

                    if (Util.validarFechaCambioEstado(sstVisitanteDocs.getDesde(), sstVisitanteDocs.getHasta())) {
                        MovilidadUtil.addErrorMessage("La fecha desde DEBE ser menor a la fecha fin");
                        return;
                    }

                    if (empresaVisitanteDocsEjb.findByFechas(sstVisitanteDocs.getIdSstEmpresaVisitanteDocs(), sstVisitanteDocs.getIdSstEmpresaVisitante().getIdSstEmpresaVisitante(), sstVisitanteDocs.getIdSstDocumentoTercero().getIdSstDocumentoTercero(), sstVisitanteDocs.getDesde(), sstVisitanteDocs.getHasta()) != null) {
                        MovilidadUtil.addErrorMessage("Ya existe un registro para el rango de fechas indicado");
                        return;
                    }
                }
            } else {

                if (sstVisitanteDocs.getIdSstDocumentoTercero().getVigencia() == 1) {

                    if (Util.validarFechaCambioEstado(sstVisitanteDocs.getDesde(), sstVisitanteDocs.getHasta())) {
                        MovilidadUtil.addErrorMessage("La fecha desde DEBE ser menor a la fecha fin");
                        return;
                    }

                    if (empresaVisitanteDocsEjb.findByFechas(0, sstVisitanteDocs.getIdSstEmpresaVisitante().getIdSstEmpresaVisitante(), sstVisitanteDocs.getIdSstDocumentoTercero().getIdSstDocumentoTercero(), sstVisitanteDocs.getDesde(), sstVisitanteDocs.getHasta()) != null) {
                        MovilidadUtil.addErrorMessage("Ya existe un registro para el rango de fechas indicado");
                        return;
                    }
                }

                sstVisitanteDocs.setActivo(1);
                sstVisitanteDocs.setEstadoReg(0);
                sstVisitanteDocs.setCreado(MovilidadUtil.fechaCompletaHoy());
                sstVisitanteDocs.setUsername(user.getUsername());
            }
        }

        sstVisitanteDocs.setModificado(MovilidadUtil.fechaCompletaHoy());
        empresaVisitanteDocsEjb.edit(sstVisitanteDocs);
        cargarDocumentos();
        sstVisitanteDocs = null;
        isEditingArchivo = false;
        PrimeFaces.current().executeScript("PF('AddFilesListDialog').hide()");
        MovilidadUtil.addSuccessMessage("Registro editado éxitosamente");
    }

    /**
     * Evento que controla la subida de archivos, para documentos de visitantes
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {

        String path;

        if (isEditingArchivo) {

            if (sstVisitanteDocs.getIdSstDocumentoTercero().getVigencia() == 1) {
                if (Util.validarFechaCambioEstado(sstVisitanteDocs.getDesde(), sstVisitanteDocs.getHasta())) {
                    MovilidadUtil.addErrorMessage("La fecha desde DEBE ser menor a la fecha fin");
                    return;
                }
                if (empresaVisitanteDocsEjb.findByFechas(sstVisitanteDocs.getIdSstEmpresaVisitanteDocs(), sstVisitanteDocs.getIdSstEmpresaVisitante().getIdSstEmpresaVisitante(), sstVisitanteDocs.getIdSstDocumentoTercero().getIdSstDocumentoTercero(), sstVisitanteDocs.getDesde(), sstVisitanteDocs.getHasta()) != null) {
                    MovilidadUtil.addErrorMessage("Ya existe un registro para el rango de fechas indicado");
                    return;
                }
            }

            if (sstVisitanteDocs.getPath() != null) {
                Util.deleteFile(sstVisitanteDocs.getPath());
            }

            if (sstVisitanteDocs.getIdSstEmpresaVisitanteDocs() != null) {
                path = Util.saveFile(event.getFile(), sstVisitanteDocs.getIdSstEmpresaVisitanteDocs(), "sst_documento_visitante");
            } else {
                sstVisitanteDocs.setActivo(1);
                sstVisitanteDocs.setEstadoReg(0);
                sstVisitanteDocs.setCreado(MovilidadUtil.fechaCompletaHoy());
                empresaVisitanteDocsEjb.create(sstVisitanteDocs);
                path = Util.saveFile(event.getFile(), sstVisitanteDocs.getIdSstEmpresaVisitanteDocs(), "sst_documento_visitante");
            }
            sstVisitanteDocs.setUsername(user.getUsername());
            sstVisitanteDocs.setPath(path);
            actualizarSinCambioDeArchivo();
            return;
        }

        if (isNuevoArchivoRe) {

            if (sstVisitanteDocs.getIdSstDocumentoTercero().getVigencia() == 1) {
                if (Util.validarFechaCambioEstado(sstVisitanteDocs.getDesde(), sstVisitanteDocs.getHasta())) {
                    MovilidadUtil.addErrorMessage("La fecha desde DEBE ser menor a la fecha fin");
                    return;
                }
                if (empresaVisitanteDocsEjb.findByFechas(0, sstVisitanteDocs.getIdSstEmpresaVisitante().getIdSstEmpresaVisitante(), sstVisitanteDocs.getIdSstDocumentoTercero().getIdSstDocumentoTercero(), sstVisitanteDocs.getDesde(), sstVisitanteDocs.getHasta()) != null) {
                    MovilidadUtil.addErrorMessage("Ya existe un registro para el rango de fechas indicado");
                    return;
                }
            }

            SstEmpresaVisitanteDocs empresaVisitanteDocsAux = empresaVisitanteDocsEjb.findUtimoDocumentoActivo(sstVisitanteDocs.getIdSstDocumentoTercero().getIdSstDocumentoTercero(), sstVisitanteDocs.getIdSstEmpresaVisitante().getIdSstEmpresaVisitante());

            if (empresaVisitanteDocsAux != null) {
                empresaVisitanteDocsAux.setActivo(0);
                empresaVisitanteDocsEjb.edit(empresaVisitanteDocsAux);
            }

            sstVisitanteDocs.setActivo(1);
            sstVisitanteDocs.setEstadoReg(0);
            sstVisitanteDocs.setUsername(user.getUsername());
            sstVisitanteDocs.setCreado(MovilidadUtil.fechaCompletaHoy());
            empresaVisitanteDocsEjb.create(sstVisitanteDocs);
            path = Util.saveFile(event.getFile(), sstVisitanteDocs.getIdSstEmpresaVisitanteDocs(), "sst_documento_visitante");
            sstVisitanteDocs.setPath(path);
            empresaVisitanteDocsEjb.edit(sstVisitanteDocs);

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
     * Valida si se muestra el listado de documentos al agregar un tercero
     *
     * @return
     */
    public boolean mostrarListaDocumentos() {
        return (isEditing == false && flagDocumentos == true);
    }

    /**
     * Cancela la subida de un archivo
     *
     * @param id
     */
    public void cancelarArchivo(Integer id) {
        SstDocumentoVisitanteArchivo obj = lstDocumentosArchivos.get(id);
        obj.setFile(null);
        obj.getEmpresaVisitanteDoc().setNumero(null);
        obj.getEmpresaVisitanteDoc().setDesde(null);
        obj.getEmpresaVisitanteDoc().setHasta(null);
        MovilidadUtil.addSuccessMessage("Archivo cancelado éxitosamente");
    }

    /**
     * Método que se ejecuta al cerrar modal que realiza la carga de archivos
     */
    public void onCloseAddFileModal() {
        isEditingArchivo = false;
        isNuevoArchivoRe = false;
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

    public SstEmpresaVisitante getSstEmpresaVisitante() {
        return sstEmpresaVisitante;
    }

    public void setSstEmpresaVisitante(SstEmpresaVisitante sstEmpresaVisitante) {
        this.sstEmpresaVisitante = sstEmpresaVisitante;
    }

    public SstEmpresaVisitante getSelected() {
        return selected;
    }

    public void setSelected(SstEmpresaVisitante selected) {
        this.selected = selected;
    }

    public Integer getI_sstEmpresa() {
        return i_sstEmpresa;
    }

    public void setI_sstEmpresa(Integer i_sstEmpresa) {
        this.i_sstEmpresa = i_sstEmpresa;
    }

    public Integer getI_sstEps() {
        return i_sstEps;
    }

    public void setI_sstEps(Integer i_sstEps) {
        this.i_sstEps = i_sstEps;
    }

    public Integer getI_sstVehiculoTipo() {
        return i_sstVehiculoTipo;
    }

    public void setI_sstVehiculoTipo(Integer i_sstVehiculoTipo) {
        this.i_sstVehiculoTipo = i_sstVehiculoTipo;
    }

    public Integer getI_sstVehiculoMarca() {
        return i_sstVehiculoMarca;
    }

    public void setI_sstVehiculoMarca(Integer i_sstVehiculoMarca) {
        this.i_sstVehiculoMarca = i_sstVehiculoMarca;
    }

    public List<SstEmpresaVisitante> getLstSstEmpresaVisitante() {
        return lstSstEmpresaVisitante;
    }

    public void setLstSstEmpresaVisitante(List<SstEmpresaVisitante> lstSstEmpresaVisitante) {
        this.lstSstEmpresaVisitante = lstSstEmpresaVisitante;
    }

    public List<SstEps> getLstSstEps() {
        return lstSstEps;
    }

    public void setLstSstEps(List<SstEps> lstSstEps) {
        this.lstSstEps = lstSstEps;
    }

    public List<SstVehiculoTipo> getLstVehiculoTipos() {
        return lstVehiculoTipos;
    }

    public void setLstVehiculoTipos(List<SstVehiculoTipo> lstVehiculoTipos) {
        this.lstVehiculoTipos = lstVehiculoTipos;
    }

    public List<SstVehiculoMarca> getLstVehiculoMarcas() {
        return lstVehiculoMarcas;
    }

    public void setLstVehiculoMarcas(List<SstVehiculoMarca> lstVehiculoMarcas) {
        this.lstVehiculoMarcas = lstVehiculoMarcas;
    }

    public List<SstEmpresaVisitanteDocs> getLstDocumentosHistorico() {
        return lstDocumentosHistorico;
    }

    public void setLstDocumentosHistorico(List<SstEmpresaVisitanteDocs> lstDocumentosHistorico) {
        this.lstDocumentosHistorico = lstDocumentosHistorico;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<SstEmpresa> getLstSstEmpresas() {
        return lstSstEmpresas;
    }

    public void setLstSstEmpresas(List<SstEmpresa> lstSstEmpresas) {
        this.lstSstEmpresas = lstSstEmpresas;
    }

    public Integer getI_ArchivoIndex() {
        return i_ArchivoIndex;
    }

    public void setI_ArchivoIndex(Integer i_ArchivoIndex) {
        this.i_ArchivoIndex = i_ArchivoIndex;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public boolean isIsEditingArchivo() {
        return isEditingArchivo;
    }

    public void setIsEditingArchivo(boolean isEditingArchivo) {
        this.isEditingArchivo = isEditingArchivo;
    }

    public List<SstDocumentoVisitanteArchivo> getLstDocumentosArchivos() {
        return lstDocumentosArchivos;
    }

    public void setLstDocumentosArchivos(List<SstDocumentoVisitanteArchivo> lstDocumentosArchivos) {
        this.lstDocumentosArchivos = lstDocumentosArchivos;
    }

    public List<SstTipoLabor> getLstSstTipoLabores() {
        return lstSstTipoLabores;
    }

    public void setLstSstTipoLabores(List<SstTipoLabor> lstSstTipoLabores) {
        this.lstSstTipoLabores = lstSstTipoLabores;
    }

    public SstEmpresaVisitanteDocs getSstVisitanteDocs() {
        return sstVisitanteDocs;
    }

    public void setSstVisitanteDocs(SstEmpresaVisitanteDocs sstVisitanteDocs) {
        this.sstVisitanteDocs = sstVisitanteDocs;
    }

    public boolean isFlagDocumentos() {
        return flagDocumentos;
    }

    public void setFlagDocumentos(boolean flagDocumentos) {
        this.flagDocumentos = flagDocumentos;
    }

    public Integer getI_tipoDocumento() {
        return i_tipoDocumento;
    }

    public void setI_tipoDocumento(Integer i_tipoDocumento) {
        this.i_tipoDocumento = i_tipoDocumento;
    }

    public List<SstTipoIdentificacion> getLstTiposIdentificacion() {
        return lstTiposIdentificacion;
    }

    public void setLstTiposIdentificacion(List<SstTipoIdentificacion> lstTiposIdentificacion) {
        this.lstTiposIdentificacion = lstTiposIdentificacion;
    }

    public boolean isB_supervisor() {
        return b_supervisor;
    }

    public void setB_supervisor(boolean b_supervisor) {
        this.b_supervisor = b_supervisor;
    }

    public Integer getI_sstTipoLabor() {
        return i_sstTipoLabor;
    }

    public void setI_sstTipoLabor(Integer i_sstTipoLabor) {
        this.i_sstTipoLabor = i_sstTipoLabor;
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