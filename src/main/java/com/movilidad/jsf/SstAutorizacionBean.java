package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.SstAreaEmpresaFacadeLocal;
import com.movilidad.ejb.SstAutorizacionFacadeLocal;
import com.movilidad.ejb.SstEmpresaVisitanteFacadeLocal;
import com.movilidad.ejb.SstEsMatEquiDetFacadeLocal;
import com.movilidad.ejb.SstEsMatEquiFacadeLocal;
import com.movilidad.ejb.SstMatEquiFacadeLocal;
import com.movilidad.ejb.SstMatEquiMarcaFacadeLocal;
import com.movilidad.ejb.SstMatEquiTipoFacadeLocal;
import com.movilidad.ejb.SstTipoLaborFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.SstAreaEmpresa;
import com.movilidad.model.SstAutorizacion;
import com.movilidad.model.SstEmpresa;
import com.movilidad.model.SstEmpresaVisitante;
import com.movilidad.model.SstEsMatEqui;
import com.movilidad.model.SstEsMatEquiDet;
import com.movilidad.model.SstMatEqui;
import com.movilidad.model.SstMatEquiMarca;
import com.movilidad.model.SstMatEquiTipo;
import com.movilidad.model.SstTipoLabor;
import com.movilidad.security.UserExtended;
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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "sstAutorizacionBean")
@ViewScoped
public class SstAutorizacionBean implements Serializable {

    @EJB
    private SstAutorizacionFacadeLocal autorizacionEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private SstEmpresaVisitanteFacadeLocal empresaVisitanteEjb;
    @EJB
    private SstTipoLaborFacadeLocal tipoLaborEjb;
    @EJB
    private SstAreaEmpresaFacadeLocal areaEmpresaEjb;
    @EJB
    private SstEsMatEquiDetFacadeLocal esMatEquiDetEjb;
    @EJB
    private SstMatEquiFacadeLocal matEquiEjb;
    @EJB
    private SstMatEquiMarcaFacadeLocal matEquiMarcaEjb;
    @EJB
    private SstMatEquiTipoFacadeLocal matEquiTipoEjb;
    @EJB
    private SstEsMatEquiFacadeLocal esMatEquiEjb;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;

    private SstAutorizacion sstAutorizacion;
    private SstAutorizacion selected;
    private SstEsMatEqui sstEsMatEqui;
    private SstMatEqui sstMatEqui;
    private SstEsMatEquiDet sstEsMatEquiDet;
    private Empleado empleado;
    private SstEmpresaVisitante sstEmpresaVisitante;

    private Integer i_sstTipoLabor;
    private Integer i_sstAreaEmpresa;
    private Integer i_sstEsMatEqui;
    private Integer i_tipoAcceso;
    private Integer i_sstMatEqui;
    private Integer i_sstMatEquiTipo;
    private Integer i_sstMatEquiMarca;

    private String material;
    private String mensajeCorreo;

    private boolean isEditing;
    private boolean isEditingDetalle;
    private boolean flagMateriales;
    private boolean flagAprobado;
    private boolean flagAprobacionAutorizacion;

    private List<SstAutorizacion> lstAutorizaciones;
    private List<SstEmpresaVisitante> lstEmpresaVisitantes;
    private List<SstEmpresa> lstEmpresas;
    private List<SstTipoLabor> lstTipoLabor;
    private List<SstAreaEmpresa> lstAreaEmpresa;
    private List<SstMatEqui> lstMatEquipos;
    private List<SstMatEquiTipo> lstMatEquiTipos;
    private List<SstMatEquiMarca> lstMatEquiMarcas;
    private List<SstEsMatEquiDet> lstEsMatEquiDet;
    private List<Empleado> lstEmpleados;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstAutorizaciones = autorizacionEjb.findAllEstadoReg();
    }

    public void nuevo() {
        isEditing = false;
        flagMateriales = false;
        i_tipoAcceso = null;
        i_sstTipoLabor = null;
        i_sstAreaEmpresa = null;
        i_sstEsMatEqui = null;
        sstAutorizacion = new SstAutorizacion();
        selected = null;
        empleado = new Empleado();
        sstEmpresaVisitante = new SstEmpresaVisitante();
        sstEsMatEqui = new SstEsMatEqui();
        lstTipoLabor = tipoLaborEjb.findAllEstadoReg();
        lstAreaEmpresa = areaEmpresaEjb.findAll();
        lstEsMatEquiDet = new ArrayList<>();
    }

    public void editar() {
        isEditing = true;
        flagMateriales = selected.getIdSstEsMatEqui() != null;
        sstAutorizacion = selected;

        empleado = sstAutorizacion.getIdResponsable();
        sstEmpresaVisitante = sstAutorizacion.getIdSstEmpresaVisitante();
        i_sstAreaEmpresa = sstAutorizacion.getIdSstAreaEmpresa() != null ? sstAutorizacion.getIdSstAreaEmpresa().getIdSstAreaEmpresa() : null;
        i_sstTipoLabor = sstAutorizacion.getIdSstTipoLabor().getIdSstTipoLabor();

        if (flagMateriales) {
            sstEsMatEqui = selected.getIdSstEsMatEqui();
            i_sstEsMatEqui = selected.getIdSstEsMatEqui().getIdSstEsMatEqui();
            i_tipoAcceso = selected.getIdSstEsMatEqui().getTipoAcceso();
            lstEsMatEquiDet = sstAutorizacion.getIdSstEsMatEqui().getSstEsMatEquiDetList();
        } else {
            i_tipoAcceso = null;
            sstEsMatEqui = new SstEsMatEqui();
            lstEsMatEquiDet = new ArrayList<>();
        }

        lstTipoLabor = tipoLaborEjb.findAllEstadoReg();
        lstAreaEmpresa = areaEmpresaEjb.findAll();
    }

    /**
     * Persiste/modifica en base de datos el registro de una autorización y lo
     * agrega/modifica al listado de autorizaciones registradas
     */
    public void guardar() {
        String validacion = validarDatos(isEditing);
        if (isEditing) {
            if (validacion == null) {
                sstAutorizacion.setIdSstEmpresaVisitante(sstEmpresaVisitante);
                sstAutorizacion.setIdSstEmpresa(sstEmpresaVisitante.getIdSstEmpresa());
                sstAutorizacion.setIdResponsable(empleado);
                sstAutorizacion.setIdSstAreaEmpresa(areaEmpresaEjb.find(i_sstAreaEmpresa));
                sstAutorizacion.setIdSstTipoLabor(tipoLaborEjb.find(i_sstTipoLabor));
                sstAutorizacion.setModificado(new Date());

                if (flagAprobacionAutorizacion) {
                    aprobarAutorizacion();
                }

                if (flagMateriales) {
                    sstEsMatEqui.setTipoAcceso(i_tipoAcceso);
                    sstEsMatEqui.setUsername(user.getUsername());
                    sstEsMatEqui.setIdSstEmpresaVisitante(sstEmpresaVisitante);

                    if (sstEsMatEqui.getIdSstEsMatEqui() == null) {
                        sstEsMatEqui.setSalidaAprobada(0);
                        sstEsMatEqui.setEntradaAprobada(0);
                        sstEsMatEqui.setCreado(new Date());
                        sstEsMatEqui.setEstadoReg(0);
                        esMatEquiEjb.create(sstEsMatEqui);
                    } else {
                        sstEsMatEqui.setModificado(new Date());
                        esMatEquiEjb.edit(sstEsMatEqui);
                    }

                    for (SstEsMatEquiDet detalle : lstEsMatEquiDet) {

                        if (detalle.getCantidad() == 0) {
                            MovilidadUtil.addErrorMessage("El item " + detalle.getIdSstMatEqui().getNombre() + " DEBE tener una cantidad mayor a CERO");
                            return;
                        }

                        detalle.setIdSstEsMatEqui(sstEsMatEqui);
                        detalle.setUsername(user.getUsername());
                        if (detalle.getIdSstEsMatEquiDet() == null) {
                            if (detalle.getIdSstMatEqui().getIdSstMatEqui() == null) {
                                matEquiEjb.create(detalle.getIdSstMatEqui());
                            }
                            detalle.setCreado(new Date());
                            detalle.setEstadoReg(0);
                        } else {
                            detalle.setModificado(new Date());
                        }
                    }
                    sstEsMatEqui.setSstEsMatEquiDetList(lstEsMatEquiDet);
                    sstAutorizacion.setIdSstEsMatEqui(sstEsMatEqui);

                } else {
                    cambiarEstadoMaterialesAlEditar();
                }

                autorizacionEjb.edit(sstAutorizacion);
                PrimeFaces.current().executeScript("PF('wlVAutorizacion').hide();");
                MovilidadUtil.addSuccessMessage("Registro modificado éxitosamente");
                init();
            } else {
                MovilidadUtil.addErrorMessage(validacion);
            }
        } else {
            if (validacion == null) {

                if (autorizacionEjb.verificarRegistroAutorizacion(sstAutorizacion.getFechaHoraLlegada(), sstAutorizacion.getFechaHoraSalida(), sstEmpresaVisitante.getNumeroDocumento()) != null) {
                    MovilidadUtil.addErrorMessage("Ya existe una autorización registrada que coincide con ése rango de fechas");
                    return;
                }

                sstAutorizacion.setIdSstEmpresaVisitante(sstEmpresaVisitante);
                sstAutorizacion.setIdSstEmpresa(sstEmpresaVisitante.getIdSstEmpresa());
                sstAutorizacion.setIdResponsable(empleado);
                sstAutorizacion.setIdSstAreaEmpresa(areaEmpresaEjb.find(i_sstAreaEmpresa));
                sstAutorizacion.setIdSstTipoLabor(tipoLaborEjb.find(i_sstTipoLabor));
                sstAutorizacion.setCreado(new Date());
                sstAutorizacion.setVisitaActiva(0);
                sstAutorizacion.setVisitaAprobada(3); // 3 (Pendiente)
                sstAutorizacion.setEstadoReg(0);
                sstAutorizacion.setIngreso(0);
                sstAutorizacion.setSalio(0);
                sstAutorizacion.setUserAprueba("N/A");
                sstAutorizacion.setUsername(user.getUsername());

                if (flagMateriales) {
                    sstEsMatEqui.setTipoAcceso(i_tipoAcceso);
                    sstEsMatEqui.setSalidaAprobada(0);
                    sstEsMatEqui.setEntradaAprobada(0);
                    sstEsMatEqui.setCreado(new Date());
                    sstEsMatEqui.setUsername(user.getUsername());
                    sstEsMatEqui.setEstadoReg(0);
                    sstEsMatEqui.setIdSstEmpresaVisitante(sstEmpresaVisitante);

                    for (SstEsMatEquiDet detalle : lstEsMatEquiDet) {

                        if (detalle.getCantidad() == 0) {
                            MovilidadUtil.addErrorMessage("El item " + detalle.getIdSstMatEqui().getNombre() + " DEBE tener una cantidad mayor a CERO");
                            return;
                        }

                        if (detalle.getIdSstMatEqui().getIdSstMatEqui() == null) {
                            matEquiEjb.create(detalle.getIdSstMatEqui());
                        }
                        detalle.setIdSstEsMatEqui(sstEsMatEqui);
                        detalle.setCreado(new Date());
                        detalle.setEstadoReg(0);
                        detalle.setUsername(user.getUsername());
                    }

                    sstEsMatEqui.setSstEsMatEquiDetList(new ArrayList<SstEsMatEquiDet>());
                    sstEsMatEqui.setSstEsMatEquiDetList(lstEsMatEquiDet);

                    sstAutorizacion.setIdSstEsMatEqui(sstEsMatEqui);

                }

                autorizacionEjb.create(sstAutorizacion);
                nuevo();
                init();
                PrimeFaces.current().executeScript("PF('wlVAutorizacion').hide();");
                PrimeFaces.current().executeScript("PF('MaterialesListDialog').hide();");
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            } else {
                MovilidadUtil.addErrorMessage(validacion);
            }
        }
    }

    /**
     * Prepara la lista de empleados antes de registrar/modificar una
     * autorización
     */
    public void prepareListEmpleados() {
        lstEmpleados = null;
        if (lstEmpleados == null) {
            lstEmpleados = empleadoEjb.findAll();
            PrimeFaces.current().ajax().update("frmPmEmpleadoList:dtEmpleados");
        }
    }

    /**
     * Prepara la lista de visitantes antes de registrar/modificar una
     * autorización
     */
    public void prepareListVisitantes() {
        lstEmpresaVisitantes = null;
        if (lstEmpresaVisitantes == null) {
            lstEmpresaVisitantes = empresaVisitanteEjb.findAllAprobados();
            PrimeFaces.current().ajax().update("frmVisitanteList:dtVisitantes");
        }
    }

    /**
     * Prepara la lista de materiales/herramientas antes de registrar/modificar
     * una autorización
     */
    public void prepareListMateriales() {

        if (sstEmpresaVisitante.getIdSstEmpresaVisitante() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un visitante");
            return;
        }

        sstMatEqui = new SstMatEqui();
        lstMatEquipos = null;
        if (lstMatEquipos == null) {
            sstEsMatEquiDet = new SstEsMatEquiDet();
            lstMatEquipos = matEquiEjb.findAllEstadoReg(sstEmpresaVisitante.getIdSstEmpresa().getIdSstEmpresa());
            PrimeFaces.current().ajax().update("frmVisitanteList:dtVisitantes");
            PrimeFaces.current().executeScript("PF('MaterialesListDialog').show();");
        }
    }

    /**
     * Evento que se dispara al seleccionar el responsable del visitante en el
     * modal que muestra listado de empleados
     *
     * @param event
     */
    public void onRowVisitanteClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof SstEmpresaVisitante) {
            setSstEmpresaVisitante((SstEmpresaVisitante) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('VisitantesListDialog').clearFilters();");
        PrimeFaces.current().ajax().update(":frmVisitanteList:dtVisitantes");
    }

    /**
     * Evento que se dispara al seleccionar un material/herramienta que ya se
     * encuentra registrado en el listado de herramientas de la empresa a la que
     * pertenece el visitante seleccionado
     *
     * @param event
     */
    public void onRowMaterialClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof SstMatEqui) {
            setSstMatEqui((SstMatEqui) event.getObject());

            for (SstEsMatEquiDet mat : lstEsMatEquiDet) {
                if (mat.getIdSstMatEqui().getNombre().equals(sstMatEqui.getNombre().trim())) {
                    MovilidadUtil.addErrorMessage("El material a registrar YA se encuentra registrado");
                    return;
                }
            }

            sstEsMatEquiDet.setCantidad(1);
            sstEsMatEquiDet.setIdSstMatEqui(sstMatEqui);
            lstEsMatEquiDet.add(sstEsMatEquiDet);
        }
        PrimeFaces.current().executeScript("PF('MaterialesListDialog').hide();");
        PrimeFaces.current().executeScript("PF('wVMaterialesListDialog').clearFilters();");
        PrimeFaces.current().ajax().update(":frmMaterialesList:dtListaMateriales");
    }

    /**
     * Evento que se dispara al seleccionar el responsable del visitante en el
     * modal que muestra listado de empleados
     *
     * @param event
     */
    public void onRowEmpleadoClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof Empleado) {
            setEmpleado((Empleado) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('EmpleadosListDialog').clearFilters();");
        PrimeFaces.current().ajax().update(":frmPmEmpleadoList:dtEmpleados");
    }

    public void nuevoDetalle() {
        isEditingDetalle = false;
        sstEsMatEquiDet = new SstEsMatEquiDet();
        sstMatEqui = new SstMatEqui();
        lstMatEquiTipos = matEquiTipoEjb.findAllByEstadoReg();
        lstMatEquiMarcas = matEquiMarcaEjb.findAllEstadoReg(sstEmpresaVisitante.getIdSstEmpresa().getIdSstEmpresa());
        lstMatEquipos = matEquiEjb.findAllEstadoReg(sstEmpresaVisitante.getIdSstEmpresa().getIdSstEmpresa());
        i_sstMatEqui = null;
        i_sstMatEquiTipo = null;
        i_sstMatEquiMarca = null;
    }

    public void editarDetalle(SstEsMatEquiDet det) {
        isEditingDetalle = true;
        material = det.getIdSstMatEqui().getNombre();
        sstEsMatEquiDet = det;
        sstMatEqui = sstEsMatEquiDet.getIdSstMatEqui();
        lstMatEquipos = matEquiEjb.findAllEstadoReg(sstEmpresaVisitante.getIdSstEmpresa().getIdSstEmpresa());
        lstMatEquiTipos = matEquiTipoEjb.findAllByEstadoReg();
        lstMatEquiMarcas = matEquiMarcaEjb.findAllEstadoReg(sstEmpresaVisitante.getIdSstEmpresa().getIdSstEmpresa());
        i_sstMatEqui = sstEsMatEquiDet.getIdSstMatEqui().getIdSstMatEqui();
        i_sstMatEquiTipo = sstEsMatEquiDet.getIdSstMatEqui().getIdSstMatEquiTipo().getIdSstMatEquiTipo();
        i_sstMatEquiMarca = sstEsMatEquiDet.getIdSstMatEqui().getIdSstMatEquiMarca().getIdSstMatEquiMarca();
    }

    public void guardarDetalleEnLista() {
        if (isEditingDetalle) {
            if (!sstEsMatEquiDet.getIdSstMatEqui().getNombre().equals(material)) {

                if (matEquiEjb.findByNombre(sstMatEqui.getNombre().trim(), sstEmpresaVisitante.getIdSstEmpresa().getIdSstEmpresa()) != null) {
                    MovilidadUtil.addErrorMessage("El siguiente material/herramienta: (" + sstMatEqui.getNombre() + ")  YA SE ENCUENTRA REGISTRADO EN EL LISTADO DE MATERIALES DE LA EMPRESA");
                    return;
                }

                for (SstEsMatEquiDet mat : lstEsMatEquiDet) {
                    if (mat.getIdSstMatEqui().getNombre().equals(sstMatEqui.getNombre().trim())) {
                        MovilidadUtil.addErrorMessage("El material a modificar YA se encuentra en registrado");
                        return;
                    }
                }
            }

            sstEsMatEquiDet.setModificado(new Date());

            sstMatEqui.setIdSstMatEquiMarca(matEquiMarcaEjb.find(i_sstMatEquiMarca));
            sstMatEqui.setIdSstMatEquiTipo(matEquiTipoEjb.find(i_sstMatEquiTipo));
            sstMatEqui.setIdSstEmpresa(sstEmpresaVisitante.getIdSstEmpresa());
            sstMatEqui.setUsername(user.getUsername());
            sstMatEqui.setModificado(new Date());

            sstEsMatEquiDet.setIdSstMatEqui(sstMatEqui);
            MovilidadUtil.addSuccessMessage("Datos actualizados éxitosamente");
        } else {

            if (matEquiEjb.findByNombre(sstMatEqui.getNombre().trim(), sstEmpresaVisitante.getIdSstEmpresa().getIdSstEmpresa()) != null) {
                MovilidadUtil.addErrorMessage("El siguiente material/herramienta: (" + sstMatEqui.getNombre() + ")  YA SE ENCUENTRA REGISTRADO EN EL LISTADO DE MATERIALES DE LA EMPRESA");
                return;
            }

            for (SstEsMatEquiDet mat : lstEsMatEquiDet) {
                if (mat.getIdSstMatEqui().getNombre().equals(sstMatEqui.getNombre().trim())) {
                    MovilidadUtil.addErrorMessage("El material a registrar YA se encuentra registrado");
                    return;
                }
            }

            sstMatEqui.setIdSstMatEquiMarca(matEquiMarcaEjb.find(i_sstMatEquiMarca));
            sstMatEqui.setIdSstMatEquiTipo(matEquiTipoEjb.find(i_sstMatEquiTipo));
            sstMatEqui.setIdSstEmpresa(sstEmpresaVisitante.getIdSstEmpresa());
            sstMatEqui.setUsername(user.getUsername());
            sstMatEqui.setCreado(new Date());
            sstMatEqui.setEstadoReg(0);

            sstEsMatEquiDet.setIdSstMatEqui(sstMatEqui);
            lstEsMatEquiDet.add(sstEsMatEquiDet);
            nuevoDetalle();
            MovilidadUtil.addSuccessMessage("Material agregado éxitosamente");
        }
        PrimeFaces.current().executeScript("PF('wlvEsMatEquiDet').hide();");
        PrimeFaces.current().executeScript("PF('MaterialesListDialog').hide();");
    }

    public void eliminarDetalle(SstEsMatEquiDet detalle) {
        if (isEditing) {
            esMatEquiDetEjb.remove(detalle);
        }
        lstEsMatEquiDet.remove(detalle);
        MovilidadUtil.addSuccessMessage("Material eliminado éxitosamente");
    }

    /**
     * Aprueba una autorización y actualiza su estado en el listado de
     * autorizaciones
     */
    public void aprobarAutorizacion() {

        if (selected.getIdResponsable() == null) {
            editar();
            PrimeFaces.current().ajax().update(":autorizacion", ":frmAutorizacion:pGridDatos");
            PrimeFaces.current().executeScript("PF('wlVAutorizacion').show();");
            MovilidadUtil.addErrorMessage("Debe asignar un responsable a la autorización");
            return;
        }

        if (selected.getIdSstAreaEmpresa() == null) {
            editar();
            PrimeFaces.current().ajax().update(":autorizacion", ":frmAutorizacion:pGridDatos");
            PrimeFaces.current().executeScript("PF('wlVAutorizacion').show();");
            MovilidadUtil.addErrorMessage("Debe asignar un área de labor");
            return;
        }

        selected.setVisitaAprobada(1);
        selected.setUserAprueba(user.getUsername());
        selected.setFechaAprobacion(new Date());
        autorizacionEjb.edit(selected);

        flagAprobado = selected.getVisitaAprobada() == 1;
        sstEsMatEqui = selected.getIdSstEsMatEqui();

        if (sstEsMatEqui != null) {
            switch (sstEsMatEqui.getTipoAcceso()) {
                case 0:
                    sstEsMatEqui.setEntradaAprobada(1);
                    sstEsMatEqui.setFechaAprobacionEntrada(selected.getFechaHoraLlegada());
                    sstEsMatEqui.setUserApruebaEntrada(user.getUsername());
                    break;
                case 1:
                    sstEsMatEqui.setSalidaAprobada(1);
                    sstEsMatEqui.setFechaAprobacionSalida(selected.getFechaHoraSalida());
                    sstEsMatEqui.setUserApruebaSalida(user.getUsername());
                    break;
                case 2:
                    sstEsMatEqui.setEntradaAprobada(1);
                    sstEsMatEqui.setSalidaAprobada(1);
                    sstEsMatEqui.setFechaAprobacionEntrada(selected.getFechaHoraLlegada());
                    sstEsMatEqui.setUserApruebaEntrada(user.getUsername());
                    sstEsMatEqui.setFechaAprobacionSalida(selected.getFechaHoraSalida());
                    sstEsMatEqui.setUserApruebaSalida(user.getUsername());
                    break;
            }
            esMatEquiEjb.edit(sstEsMatEqui);
            flagAprobacionAutorizacion = false;
        }
        notificar();
        MovilidadUtil.addSuccessMessage("Autorización aprobada éxitosamente");
    }

    /**
     * Rechaza una autorización y actualiza su estado en el listado de
     * autorizaciones
     */
    public void rechazarAutorizacion() {
        selected.setVisitaAprobada(0);
        autorizacionEjb.edit(selected);

        flagAprobado = selected.getVisitaAprobada() == 1;
        sstEsMatEqui = selected.getIdSstEsMatEqui();

        if (sstEsMatEqui != null) {
            switch (sstEsMatEqui.getTipoAcceso()) {
                case 0:
                    sstEsMatEqui.setEntradaAprobada(0);
                    sstEsMatEqui.setFechaAprobacionEntrada(null);
                    sstEsMatEqui.setUserApruebaEntrada(null);
                    break;
                case 1:
                    sstEsMatEqui.setSalidaAprobada(0);
                    sstEsMatEqui.setFechaAprobacionSalida(null);
                    sstEsMatEqui.setUserApruebaSalida(null);
                    break;
                case 2:
                    sstEsMatEqui.setEntradaAprobada(0);
                    sstEsMatEqui.setFechaAprobacionEntrada(null);
                    sstEsMatEqui.setUserApruebaEntrada(null);
                    sstEsMatEqui.setFechaAprobacionSalida(null);
                    sstEsMatEqui.setUserApruebaSalida(null);
                    sstEsMatEqui.setSalidaAprobada(0);
                    break;
            }
            esMatEquiEjb.edit(sstEsMatEqui);
        }
        notificar();
        PrimeFaces.current().executeScript("PF('wlvfeedbackAutorizacion').hide()");
        MovilidadUtil.addSuccessMessage("Autorización rechazada éxitosamente");
    }

    /**
     * Dispara
     */
    public void cambiarEstadoMaterialesAlEditar() {
        if (lstEsMatEquiDet != null) {

            List<SstEsMatEquiDet> detalles = sstAutorizacion.getIdSstEsMatEqui().getSstEsMatEquiDetList();

            if (detalles != null) {
                for (SstEsMatEquiDet det : detalles) {
                    esMatEquiDetEjb.remove(det);
                }
            }

            sstAutorizacion.setIdSstEsMatEqui(null);
            autorizacionEjb.edit(sstAutorizacion);

            esMatEquiEjb.remove(sstEsMatEqui);
            lstEsMatEquiDet = null;
            i_tipoAcceso = null;
        }

    }

    /*
     * Parámetros para el envío de correo
     */
    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(Util.ID_TEMPLATE_SST_RESPUESTA_AUTORIZACION);
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
     * Realiza el envío de correo a la empresa (Correspondiente a la
     * autorización seleccionada) notificandoles si se aprobó o NO la
     * autorización seleccionada
     */
    private void notificar() {
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("estado", (flagAprobado ? "APROBADA" : "RECHAZADA"));
        mailProperties.put("mensaje", (flagAprobado ? "N/A" : mensajeCorreo));
        mailProperties.put("numDocumento", selected.getIdSstEmpresaVisitante().getNumeroDocumento());
        mailProperties.put("visitante", selected.getIdSstEmpresaVisitante().getNombre().concat(" ").concat(selected.getIdSstEmpresaVisitante().getApellidos()));
        mailProperties.put("tipoLabor", selected.getIdSstTipoLabor().getTipoLabor());
        mailProperties.put("areaLabor", selected.getIdSstAreaEmpresa() != null ? selected.getIdSstAreaEmpresa().getArea() : "N/A");
        mailProperties.put("responsable", selected.getIdResponsable() != null ? selected.getIdResponsable().getNombres().concat(" ").concat(selected.getIdResponsable().getApellidos()) : "N/A");
        mailProperties.put("fechaHoraLlegada", Util.dateTimeFormat(selected.getFechaHoraLlegada()));
        mailProperties.put("fechaHoraSalida", Util.dateTimeFormat(selected.getFechaHoraSalida()));
        mailProperties.put("motivo", selected.getMotivoVisita());
        String subject = "Su solicitud de autorización ha sido " + (flagAprobado ? "APROBADA" : "RECHAZADA");
        String destinatarios = selected.getIdSstEmpresa().getEmailEmpresa();

        if (mensajeCorreo != null) {
            mensajeCorreo = "";
        }

        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", null);
    }

    private String validarDatos(boolean flagEdit) {
        if (flagEdit) {
            if (flagMateriales) {
                if (lstEsMatEquiDet == null || (lstEsMatEquiDet != null && lstEsMatEquiDet.isEmpty())) {
                    return "DEBE agregar al menos 1 material/herramienta";
                }
            }
            if (sstEmpresaVisitante.getIdSstEmpresaVisitante() == null) {
                return "DEBE seleccionar un visitante";
            }
            if (empleado == null || empleado.getIdEmpleado() == null) {
                return "DEBE seleccionar responsable del visitante";
            }
            if (Util.validarFechaCambioEstado(sstAutorizacion.getFechaHoraLlegada(), sstAutorizacion.getFechaHoraSalida())) {
                return "La fecha de llegada NO puede ser mayor a la fecha de salida";
            }
        } else {
            if (flagMateriales) {
                if (lstEsMatEquiDet == null || (lstEsMatEquiDet != null && lstEsMatEquiDet.isEmpty())) {
                    return "DEBE agregar al menos 1 material/herramienta";
                }
            }
            if (empleado.getIdEmpleado() == null) {
                return "DEBE seleccionar responsable del visitante";
            }
            if (sstEmpresaVisitante.getIdSstEmpresaVisitante() == null) {
                return "DEBE seleccionar un visitante";
            }
            if (Util.validarFechaCambioEstado(sstAutorizacion.getFechaHoraLlegada(), sstAutorizacion.getFechaHoraSalida())) {
                return "La fecha de llegada NO puede ser mayor a la fecha de salida";
            }
        }

        return null;
    }

    public SstAutorizacion getSstAutorizacion() {
        return sstAutorizacion;
    }

    public void setSstAutorizacion(SstAutorizacion sstAutorizacion) {
        this.sstAutorizacion = sstAutorizacion;
    }

    public SstAutorizacion getSelected() {
        return selected;
    }

    public void setSelected(SstAutorizacion selected) {
        this.selected = selected;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public SstEmpresaVisitante getSstEmpresaVisitante() {
        return sstEmpresaVisitante;
    }

    public void setSstEmpresaVisitante(SstEmpresaVisitante sstEmpresaVisitante) {
        this.sstEmpresaVisitante = sstEmpresaVisitante;
    }

    public Integer getI_sstTipoLabor() {
        return i_sstTipoLabor;
    }

    public void setI_sstTipoLabor(Integer i_sstTipoLabor) {
        this.i_sstTipoLabor = i_sstTipoLabor;
    }

    public Integer getI_sstAreaEmpresa() {
        return i_sstAreaEmpresa;
    }

    public void setI_sstAreaEmpresa(Integer i_sstAreaEmpresa) {
        this.i_sstAreaEmpresa = i_sstAreaEmpresa;
    }

    public Integer getI_sstEsMatEqui() {
        return i_sstEsMatEqui;
    }

    public void setI_sstEsMatEqui(Integer i_sstEsMatEqui) {
        this.i_sstEsMatEqui = i_sstEsMatEqui;
    }

    public List<SstEmpresaVisitante> getLstEmpresaVisitantes() {
        return lstEmpresaVisitantes;
    }

    public void setLstEmpresaVisitantes(List<SstEmpresaVisitante> lstEmpresaVisitantes) {
        this.lstEmpresaVisitantes = lstEmpresaVisitantes;
    }

    public List<SstEmpresa> getLstEmpresas() {
        return lstEmpresas;
    }

    public void setLstEmpresas(List<SstEmpresa> lstEmpresas) {
        this.lstEmpresas = lstEmpresas;
    }

    public List<SstTipoLabor> getLstTipoLabor() {
        return lstTipoLabor;
    }

    public void setLstTipoLabor(List<SstTipoLabor> lstTipoLabor) {
        this.lstTipoLabor = lstTipoLabor;
    }

    public List<SstAreaEmpresa> getLstAreaEmpresa() {
        return lstAreaEmpresa;
    }

    public void setLstAreaEmpresa(List<SstAreaEmpresa> lstAreaEmpresa) {
        this.lstAreaEmpresa = lstAreaEmpresa;
    }

    public List<SstEsMatEquiDet> getLstEsMatEquiDet() {
        return lstEsMatEquiDet;
    }

    public void setLstEsMatEquiDet(List<SstEsMatEquiDet> lstEsMatEquiDet) {
        this.lstEsMatEquiDet = lstEsMatEquiDet;
    }

    public List<Empleado> getLstEmpleados() {
        return lstEmpleados;
    }

    public void setLstEmpleados(List<Empleado> lstEmpleados) {
        this.lstEmpleados = lstEmpleados;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public SstEsMatEqui getSstEsMatEqui() {
        return sstEsMatEqui;
    }

    public void setSstEsMatEqui(SstEsMatEqui sstEsMatEqui) {
        this.sstEsMatEqui = sstEsMatEqui;
    }

    public Integer getI_tipoAcceso() {
        return i_tipoAcceso;
    }

    public void setI_tipoAcceso(Integer i_tipoAcceso) {
        this.i_tipoAcceso = i_tipoAcceso;
    }

    public SstEsMatEquiDet getSstEsMatEquiDet() {
        return sstEsMatEquiDet;
    }

    public void setSstEsMatEquiDet(SstEsMatEquiDet sstEsMatEquiDet) {
        this.sstEsMatEquiDet = sstEsMatEquiDet;
    }

    public boolean isIsEditingDetalle() {
        return isEditingDetalle;
    }

    public void setIsEditingDetalle(boolean isEditingDetalle) {
        this.isEditingDetalle = isEditingDetalle;
    }

    public Integer getI_sstMatEqui() {
        return i_sstMatEqui;
    }

    public void setI_sstMatEqui(Integer i_sstMatEqui) {
        this.i_sstMatEqui = i_sstMatEqui;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public List<SstAutorizacion> getLstAutorizaciones() {
        return lstAutorizaciones;
    }

    public void setLstAutorizaciones(List<SstAutorizacion> lstAutorizaciones) {
        this.lstAutorizaciones = lstAutorizaciones;
    }

    public boolean isFlagMateriales() {
        return flagMateriales;
    }

    public void setFlagMateriales(boolean flagMateriales) {
        this.flagMateriales = flagMateriales;
    }

    public SstMatEqui getSstMatEqui() {
        return sstMatEqui;
    }

    public void setSstMatEqui(SstMatEqui sstMatEqui) {
        this.sstMatEqui = sstMatEqui;
    }

    public Integer getI_sstMatEquiTipo() {
        return i_sstMatEquiTipo;
    }

    public void setI_sstMatEquiTipo(Integer i_sstMatEquiTipo) {
        this.i_sstMatEquiTipo = i_sstMatEquiTipo;
    }

    public Integer getI_sstMatEquiMarca() {
        return i_sstMatEquiMarca;
    }

    public void setI_sstMatEquiMarca(Integer i_sstMatEquiMarca) {
        this.i_sstMatEquiMarca = i_sstMatEquiMarca;
    }

    public List<SstMatEquiTipo> getLstMatEquiTipos() {
        return lstMatEquiTipos;
    }

    public void setLstMatEquiTipos(List<SstMatEquiTipo> lstMatEquiTipos) {
        this.lstMatEquiTipos = lstMatEquiTipos;
    }

    public List<SstMatEquiMarca> getLstMatEquiMarcas() {
        return lstMatEquiMarcas;
    }

    public void setLstMatEquiMarcas(List<SstMatEquiMarca> lstMatEquiMarcas) {
        this.lstMatEquiMarcas = lstMatEquiMarcas;
    }

    public boolean isFlagAprobado() {
        return flagAprobado;
    }

    public void setFlagAprobado(boolean flagAprobado) {
        this.flagAprobado = flagAprobado;
    }

    public String getMensajeCorreo() {
        return mensajeCorreo;
    }

    public void setMensajeCorreo(String mensajeCorreo) {
        this.mensajeCorreo = mensajeCorreo;
    }

    public List<SstMatEqui> getLstMatEquipos() {
        return lstMatEquipos;
    }

    public void setLstMatEquipos(List<SstMatEqui> lstMatEquipos) {
        this.lstMatEquipos = lstMatEquipos;
    }

    public boolean isFlagAprobacionAutorizacion() {
        return flagAprobacionAutorizacion;
    }

    public void setFlagAprobacionAutorizacion(boolean flagAprobacionAutorizacion) {
        this.flagAprobacionAutorizacion = flagAprobacionAutorizacion;
    }

}
