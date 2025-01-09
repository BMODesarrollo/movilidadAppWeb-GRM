/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccDesplazaAFacadeLocal;
import com.movilidad.ejb.AccReincidenciaFacadeLocal;
import com.movilidad.ejb.AccTipoDocsFacadeLocal;
import com.movilidad.ejb.AccidenteDocumentoFacadeLocal;
import com.movilidad.ejb.AccidenteFacadeLocal;
import com.movilidad.ejb.AccidenteLugarFacadeLocal;
import com.movilidad.ejb.ConfigEmpresaFacadeLocal;
import com.movilidad.ejb.EmpleadoDocumentosFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.EmpleadoTipoDocumentosFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.ejb.VehiculoDocumentoFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.ejb.VehiculoTipoDocumentoFacadeLocal;
import com.movilidad.model.AccAbogado;
import com.movilidad.model.AccAtencionVia;
import com.movilidad.model.AccClase;
import com.movilidad.model.AccDesplazaA;
import com.movilidad.model.AccReincidencia;
import com.movilidad.model.AccTipoDocs;
import com.movilidad.model.Accidente;
import com.movilidad.model.AccidenteDocumento;
import com.movilidad.model.AccidenteLugar;
import com.movilidad.model.AccidenteVehiculo;
import com.movilidad.model.ConfigEmpresa;
import com.movilidad.model.Empleado;
import com.movilidad.model.EmpleadoDocumentos;
import com.movilidad.model.EmpleadoTipoDocumentos;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadTipoDetalles;
import com.movilidad.model.PrgSercon;
import com.movilidad.model.Vehiculo;
import com.movilidad.model.VehiculoDocumentos;
import com.movilidad.model.VehiculoTipoDocumentos;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.GeocodingDTO;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author HP
 */
@Named(value = "accidenteJSF")
@ViewScoped
public class AccidenteJSF implements Serializable {

    @EJB
    private AccidenteFacadeLocal accidenteFacadeLocal;
    @EJB
    private NovedadTipoDetallesFacadeLocal novedadTipoDetalleFacadeLocal;
    @EJB
    private NovedadFacadeLocal novedadFacadeLocal;
    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;
    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;
    @EJB
    private AccidenteDocumentoFacadeLocal accidenteDocumentoFacadeLocal;
    @EJB
    private AccTipoDocsFacadeLocal accTipoDocsFacadeLocal;
    @EJB
    private EmpleadoTipoDocumentosFacadeLocal empleadoTipoDocumentosFacadeLocal;
    @EJB
    private VehiculoTipoDocumentoFacadeLocal vehiculoTipoDocumentoFacadeLocal;
    @EJB
    private EmpleadoDocumentosFacadeLocal empleadoDocumentosFacadeLocal;
    @EJB
    private VehiculoDocumentoFacadeLocal vehiculoDocumentoFacadeLocal;
    @EJB
    private PrgTcFacadeLocal prgTcFacadeLocal;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @EJB
    private AccReincidenciaFacadeLocal accReincidenciaFacadeLocal;
    @EJB
    private PrgSerconFacadeLocal prgSerconFacadeLocal;
    @EJB
    private AccidenteLugarFacadeLocal accidenteLugarFacadeLocal;
    @EJB
    private ConfigEmpresaFacadeLocal configEmpresaFacadeLocal;
    @EJB
    private AccDesplazaAFacadeLocal accDesplazaAEJB;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    @Inject
    private AccidenteConductorJSF accidenteConductorJSF;
    @Inject
    private AccidenteVehiculoJSF accidenteVehiculoJSF;

    private Accidente accidente;

    private List<NovedadTipoDetalles> listNovedadTipoDetalles;
    private List<Accidente> listAccidente;
    private List<Empleado> listEmpleados;
    private List<GopUnidadFuncional> lstUnidadesFuncionales;
    private List<AccDesplazaA> lstAccDesplazamientos;

    private int i_opcion;
    private int i_idAccNovTipDet;
    private int i_idAccTipServ;
    private int i_idAccClas;
    private int i_idAccDetCla;
    private int i_idAccTipTur;
    private int i_idAccActRealizada;
    private Integer idAccAbogado;
    private Integer idAccAtencionVia;
    private Integer idAccDesplaza;

    private int i_auxNovDet;
    private Date fechaIni;
    private Date fechaFin;
    private String c_codVeh;
    private Integer i_codOpe;
    private Integer i_codOpeMaster;
    private int i_numDia;
    private int idGopUnidadFuncional;

    private boolean flagListaUF;
    private boolean b_control;
    private boolean b_ipat;
    private boolean b_casoTransmilenio;
    private boolean b_juridica;
    private boolean b_flag;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @Inject
    private AccidenteLugarJSF accL;
    @Inject
    private AccidenteVehiculoJSF accV;
    @Inject
    private AccidenteConductorJSF accC;
    @Inject
    private AccidenteVictimaJSF accVic;
    @Inject
    private AccidenteTestigoJSF accT;
    @Inject
    private AccidenteDiligenciasjJSF accD;
    @Inject
    private AccidenteAnalisisJSF accA;
    @Inject
    private AccidenteCostosJSF accCo;
    @Inject
    private AccidenteDocumentoJSF accDoc;
    @Inject
    private SegInoperativosBean inoperativosBean;

    //
    private int idGopUF;
    private boolean isRolAbogado;

    public AccidenteJSF() {
    }

    @PostConstruct
    public void init() {
        accidente = new Accidente();
        accidente.setFechaAcc(new Date());
        b_flag = true;
        i_opcion = 0;
        i_idAccNovTipDet = 0;
        i_idAccTipServ = 0;
        i_idAccClas = 0;
        i_idAccDetCla = 0;
        i_idAccTipTur = 0;
        i_idAccActRealizada = 0;
        b_casoTransmilenio = false;
        b_juridica = false;
        b_ipat = false;
        b_control = false;
        fechaFin = new Date();
        fechaIni = new Date();
        i_codOpe = null;
        c_codVeh = "";
        i_auxNovDet = 0;
        i_codOpeMaster = null;
        i_numDia = 0;
        listEmpleados = new ArrayList<>();
        lstAccDesplazamientos = accDesplazaAEJB.findByEstadoReg();

//        idGopUnidadFuncional = unidadFuncionalSessionBean.getIdGopUnidadFuncional();
//        if (idGopUnidadFuncional == 0) {
//            lstUnidadesFuncionales = unidadFuncionalEjb.findAllByEstadoReg();
//            flagListaUF = true;
//        }
        idGopUF = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
        // ahora se consultan los accidentes del dia, desde el inicio.
        buscarAccidente(0);
        rolAccAbogado();

    }

    void rolAccAbogado() {
        isRolAbogado = user.getAuthorities().stream().anyMatch(us -> {
            return us.getAuthority().equals(ConstantsUtil.ROLE_ACC_ABOGADO);
        });
    }

    public void inoperatividadAccidentalidad() {
        if (accidente.getIdAccidente() != null) {
            inoperativosBean.gestionarInoperatividadByAccidente(accidente);
        } else {
            inoperativosBean.nuevo();
        }
    }

    public void guardar() {
        try {
            cargarUF();
            if (idGopUF == 0) {
                MovilidadUtil.addErrorMessage("Debe seleccionar una unidad funcional");
                return;
            }
            if (accidente != null) {
                accidente.setIdGopUnidadFuncional(new GopUnidadFuncional(idGopUF));
                cargarObjetos();
                if (b_control) {
                    b_control = false;
                    return;
                }
                if (accidente.getFechaCierre() != null) {
                    accidente.setUserCierre(user.getUsername());
                }
                accidente.setCreado(new Date());
                accidente.setModificado(new Date());
                accidente.setUsername(user.getUsername());
                accidente.setEstadoReg(0);
                accidenteFacadeLocal.create(accidente);
                //        alertaReincidenciaAcc(accidente);
                documentosAcc(accidente);
                i_auxNovDet = i_idAccNovTipDet;
                fechaIni = MovilidadUtil.dateSinHora(accidente.getFechaAcc());
                fechaFin = MovilidadUtil.dateSinHora(accidente.getFechaAcc());
                buscarAccidente(0);
                reset();
                MovilidadUtil.addSuccessMessage("Accidente Guardado correctamente");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Accidente");
        }
    }

    public void editar() {
        try {
            cargarUF();
            if (idGopUF == 0) {
                MovilidadUtil.addErrorMessage("Debe seleccionar una unidad funcional");
                return;
            }
            if (accidente != null) {
                cargarObjetos();
                if (b_control) {
                    b_control = false;
                    return;
                }
                if (accidente.getIdNovedad() != null) {
                    if (accidente.getIdNovedad().getIdNovedadTipoDetalle().getIdNovedadTipoDetalle() != i_idAccNovTipDet) {
                        Novedad n = accidente.getIdNovedad();
                        n.setIdNovedadTipoDetalle(new NovedadTipoDetalles(i_idAccNovTipDet));
                        novedadFacadeLocal.edit(n);
                    }
                }
                generarKmRecorridoOperador(accidente);
                accidenteFacadeLocal.edit(accidente);
                MovilidadUtil.addSuccessMessage("Accidente Actualizado correctamente");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Accidente");
        }
    }

    public void prepareGuardar() {
        accidente = new Accidente();
        accidente.setFechaAcc(new Date());
    }

    public void onRowSelect(SelectEvent event) {
        try {
            accidente = (Accidente) event.getObject();
            accidente = accidenteFacadeLocal.find(accidente.getIdAccidente());
            b_flag = false;
            accL.init();
            accV.reset();
            accC.reset();
            accVic.reset();
            accT.reset();
            accD.reset();
            accA.reset();
            accCo.reset();
            accDoc.reset();
            idAccAtencionVia = accidente.getIdAccAtencionVia() != null ? accidente.getIdAccAtencionVia().getIdAccAtencionVia() : null;
            idAccAbogado = accidente.getIdAccAbogado() != null ? accidente.getIdAccAbogado().getIdAccAbogado() : null;
            idAccDesplaza = accidente.getIdAccDesplazaA() != null ? accidente.getIdAccDesplazaA().getIdAccDesplazaA() : null;
            i_idAccActRealizada = accidente.getIdAccActRealizada() != null ? accidente.getIdAccActRealizada().getIdAccActRealizada() : 0;
            i_idAccClas = accidente.getIdClase() != null ? accidente.getIdClase().getIdAccClase() : 0;
            i_idAccDetCla = accidente.getIdAccDetClase() != null ? accidente.getIdAccDetClase().getIdAccDetClase() : 0;
            i_idAccNovTipDet = accidente.getIdNovedadTipoDetalle() != null ? accidente.getIdNovedadTipoDetalle().getIdNovedadTipoDetalle() : 0;
            i_idAccTipServ = accidente.getIdAccTipoServ() != null ? accidente.getIdAccTipoServ().getIdAccTipoServ() : 0;
            i_idAccTipTur = accidente.getIdAccTipoTurno() != null ? accidente.getIdAccTipoTurno().getIdAccTipoTurno() : 0;
//            i_numDia = accidente.getFechaAcc() != null ? getDiaSemana(accidente.getFechaAcc()) : 0;
            b_casoTransmilenio = accidente.getCasoTm() != null ? accidente.getCasoTm() == 1 : false;
            b_juridica = accidente.getJuridica() != null ? accidente.getJuridica() == 1 : false;
            if (accidente.getNroIpat() != null) {
                b_ipat = !accidente.getNroIpat().isEmpty();
            } else {
                b_ipat = false;
            }
            if (accidente.getIdNovedad().getPuntosPmConciliados() != null)
                accA.setPuntosPmConciliados(accidente.getIdNovedad().getPuntosPmConciliados());
            if (accidente.getOperadorIsResponsable() != null)
                accA.setIsResponsable(accidente.getOperadorIsResponsable() == 1);
            
            accA.setIsAfectaBonificacion(accidente.getIdNovedad().getProcede() == 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        accidente = new Accidente();
        accidente.setFechaAcc(new Date());
        i_opcion = 0;
        i_idAccNovTipDet = 0;
        i_idAccTipServ = 0;
        i_idAccClas = 0;
        i_idAccDetCla = 0;
        i_idAccTipTur = 0;
        i_idAccActRealizada = 0;
        b_casoTransmilenio = false;
        b_juridica = false;
        b_ipat = false;
        b_flag = true;
        i_codOpeMaster = null;
        i_numDia = 0;
        accL.init();
    }

    void cargarObjetos() {
//        if (i_idAccTipTur != 0) {
//            accidente.setIdAccTipoTurno(new AccTipoTurno(i_idAccTipTur));
//        } else {
//            MovilidadUtil.addErrorMessage("Acc Tipo Turno es requerido");
//            b_control = true;
//        }
//        if (i_idAccTipServ != 0) {
//            accidente.setIdAccTipoServ(new AccTipoServ(i_idAccTipServ));
//        } else {
//            MovilidadUtil.addErrorMessage("Acc Tipo Servicio es requerido");
//            b_control = true;
//        }
//        if (i_idAccActRealizada != 0) {
//            accidente.setIdAccActRealizada(new AccActRealizada(i_idAccActRealizada));
//        }
        if (i_idAccClas != 0) {
            accidente.setIdClase(new AccClase(i_idAccClas));
        } else {
            MovilidadUtil.addErrorMessage("Acc Clasificación es requerido");
            b_control = true;
        }
        if (i_idAccNovTipDet != 0) {
            accidente.setIdNovedadTipoDetalle(new NovedadTipoDetalles(i_idAccNovTipDet));
        } else {
            MovilidadUtil.addErrorMessage("Tipo de evento es requerido");
            b_control = true;
        }
        if (idAccAbogado != null) {
            accidente.setIdAccAbogado(new AccAbogado(idAccAbogado));
        }
        if (idAccDesplaza != null) {
            accidente.setIdAccDesplazaA(new AccDesplazaA(idAccDesplaza));
        }
        if (idAccAtencionVia != null) {
            accidente.setIdAccAtencionVia(new AccAtencionVia(idAccAtencionVia));
        }
//        if (i_idAccDetCla != 0) {
//            accidente.setIdAccDetClase(new AccDetClase(i_idAccDetCla));
//        } else {
//            MovilidadUtil.addErrorMessage("Detalle de clasificación es requerido");
//            b_control = true;
//        }
//        if (b_casoTransmilenio) {
//            accidente.setCasoTm(1);
//        } else {
//            accidente.setCasoTm(0);
//        }
        accidente.setCasoTm(b_casoTransmilenio ? 1 : 0);
        accidente.setJuridica(b_juridica ? 1 : 0);
//        if (accidente.getConciliado() != null) {
//            if (accidente.getConciliado() == 0) {
//                accidente.setValorConciliado(0);
//            }
//        }
        b_ipat = false;
        if (accidente.getNroIpat() != null) {
            b_ipat = !accidente.getNroIpat().isEmpty();
        }
//        } else {
//            b_ipat = false;
//        }
//        if (accidente.getIdEmpleado() != null) {
//            accidente.setMesesOperando(cargarMesesOperando(accidente.getFechaAcc(), accidente.getIdEmpleado().getFechaIngreso()));
//            accidente.setFechaIngresoEmpleado(accidente.getIdEmpleado().getFechaIngreso());
//        }
        if (accidente.getFechaCierre() != null) {
            if (accidente.getUserCierre() == null) {
                accidente.setUserCierre(user.getUsername());
            }
        }
    }

    public int compartirIdAccidente() {
        if (accidente == null) {
            return 0;
        }
        if (accidente.getIdAccidente() == null) {
            return 0;
        }
        return accidente.getIdAccidente();
    }

    @Transactional
    public GeocodingDTO guardarAccidente(Novedad novedad) {
        try {
            accidente = new Accidente();
            if (!Util.isStringNullOrEmpty(novedad.getHora())) {
                accidente.setFechaAcc(Util.dateTimeFormat(Util.dateFormat(novedad.getFecha()) + " " + novedad.getHora()));
            } else {
                accidente.setFechaAcc(Util.unirFechaHoraByDates(novedad.getFecha(), new Date()));
            }

            // validad duplicidad de accidente
            if (novedad.getIdEmpleado() != null && novedad.getIdVehiculo() != null && novedad.getIdGopUnidadFuncional() != null) {
                Long duplicidad = accidenteFacadeLocal.existeAccidenteAbiertoByIdEmpleadoByIdVehiculoAndIdNovDetAndIdGopUF(novedad.getIdEmpleado().getIdEmpleado(),
                        novedad.getIdVehiculo().getIdVehiculo(),
                        novedad.getIdNovedadTipoDetalle().getIdNovedadTipoDetalle(),
                        novedad.getIdGopUnidadFuncional().getIdGopUnidadFuncional()
                );
                if (duplicidad > 0) {
                    MovilidadUtil.addAdvertenciaMessage("El proceso de accidentalidad no ha iniciado, debido a que existe uno similar. La novedad si ha sido creada");
                    return new GeocodingDTO("X");
                }
            }
            //
            accidente.setIdGopUnidadFuncional(novedad.getIdGopUnidadFuncional());
            if (novedad.getPrgTc() != null) {
                accidente.setIdPrgTc(novedad.getPrgTc());
            }
            Vehiculo veh = null;
            if (novedad.getIdVehiculo() != null) {
                veh = vehiculoFacadeLocal.find(novedad.getIdVehiculo().getIdVehiculo());
                accidente.setIdVehiculo(veh);
//                accidente.setIdGopUnidadFuncional(novedad.getIdVehiculo().getIdGopUnidadFuncional());
            }
            Empleado emp = null;
            if (novedad.getIdEmpleado() != null) {
                emp = empleadoFacadeLocal.find(novedad.getIdEmpleado().getIdEmpleado());
                accidente.setIdEmpleado(emp);
//                if (emp.getFechaIngreso() != null) {
////                    int i_mesesOperando = cargarMesesOperando(novedad.getIdEmpleado().getFechaIngreso(), novedad.getFecha());
////                    accidente.setMesesOperando(i_mesesOperando);
////                    accidente.setFechaIngresoEmpleado(novedad.getIdEmpleado().getFechaIngreso());
//                }
            }
            datosPrgSerconAccidente(accidente);
            accidente.setIdNovedad(novedad);
            accidente.setIdNovedadTipoDetalle(novedad.getIdNovedadTipoDetalle());
            accidente.setUsername(novedad.getUsername());
            accidente.setCreado(novedad.getCreado());
            accidente.setEstadoReg(0);
            accidenteFacadeLocal.create(accidente);
            alertaReincidenciaAcc(accidente, 1);
            documentosAcc(accidente);
            AccidenteVehiculo guardarPorAccidente = null;
            if (veh != null) {
                guardarPorAccidente = accidenteVehiculoJSF.guardarPorAccidente(accidente, veh);
            }
            if (emp != null) {
                accidenteConductorJSF.guardarPorAccidente(accidente, guardarPorAccidente, emp);
            }
//             crear direccion de lugar
            if (veh != null && !Util.isStringNullOrEmpty(veh.getCodigo())) {
                ConfigEmpresa keyServiceGeo = configEmpresaFacadeLocal
                        .findByLlave(ConstantsUtil.URL_SERVICE_LOCATION_VEHICLE);
                if (keyServiceGeo == null) {
                    MovilidadUtil.addErrorMessage("El sistema no cuenta con la KEY de GEO necesaria");
                    return null;
                }
                ConfigEmpresa keyGoogle = configEmpresaFacadeLocal
                        .findByLlave(Util.CODE_API_KEY_GOOGLE);
                if (keyGoogle == null) {
                    MovilidadUtil.addErrorMessage("El sistema no cuenta con la KEY de GOOGLE necesaria");
                    return null;
                }
                GeocodingDTO ubicacionVeh = Util.conocerUbicacionVehiculo(keyGoogle.getValor(),
                        keyServiceGeo.getValor(),
                        veh.getCodigo().toUpperCase());
                if (ubicacionVeh == null) {
                    MovilidadUtil.addFatalMessage("Error obtener cordenadas del servicio de GEO");
                    return null;
                }
                AccidenteLugar accidenteLugar = new AccidenteLugar();
                Date d = new Date();
                accidenteLugar.setDireccion(ubicacionVeh.getDireccion());
                accidenteLugar.setLatitude(BigDecimal.valueOf(ubicacionVeh.getLatitud()));
                accidenteLugar.setLongitude(BigDecimal.valueOf(ubicacionVeh.getLongitud()));
                accidenteLugar.setIdAccidente(accidente);
                accidenteLugar.setCreado(d);
                accidenteLugar.setModificado(d);
                accidenteLugar.setUsername(user.getUsername());
                accidenteLugar.setEstadoReg(0);
                accidenteLugarFacadeLocal.create(accidenteLugar);
                return ubicacionVeh;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void datosPrgSerconAccidente(Accidente a) {
        if (a.getIdEmpleado() == null) {
            return;
        }

        PrgSercon prgSerconActual = prgSerconFacadeLocal
                .getByIdEmpleadoAndFecha(a.getIdEmpleado().getIdEmpleado(),
                        a.getFechaAcc());
        PrgSercon prgSerconPass = prgSerconFacadeLocal
                .getByIdEmpleadoAndFecha(a.getIdEmpleado().getIdEmpleado(),
                        MovilidadUtil.sumarDias(a.getFechaAcc(), -1));

        a.setIdPrgSerconActual(prgSerconActual);
        a.setIdPrgSerconPass(prgSerconPass);
    }

    @Transactional
    public void actualizarAccidente(Novedad novedad, boolean cambioAccidente) {
        try {
            accidente = accidenteFacadeLocal.findByNovedad(novedad.getIdNovedad());
            if (accidente != null) {
                accidente.setIdGopUnidadFuncional(novedad.getIdGopUnidadFuncional());
                String codVehiculo = accidente.getIdVehiculo().getCodigo();
                String cedula = accidente.getIdEmpleado().getIdentificacion();
                accidente.setEstadoReg(0);
                if (novedad.getIdVehiculo() != null) {
                    accidente.setIdVehiculo(novedad.getIdVehiculo());
//                    accidente.setIdGopUnidadFuncional(novedad.getIdVehiculo().getIdGopUnidadFuncional());
                }
                if (novedad.getIdEmpleado() != null) {
                    accidente.setIdEmpleado(novedad.getIdEmpleado());
//                    int i_mesesOperando = cargarMesesOperando(novedad.getIdEmpleado().getFechaIngreso(), novedad.getFecha());
//                    accidente.setMesesOperando(i_mesesOperando);
//                    accidente.setFechaIngresoEmpleado(novedad.getIdEmpleado().getFechaIngreso());
                }
                datosPrgSerconAccidente(accidente);
                accidente.setIdNovedad(novedad);
                accidente.setIdNovedadTipoDetalle(novedad.getIdNovedadTipoDetalle());
//                accidente.setFechaAcc(novedad.getFecha());

                // Se agregó ajuste para concatenar hora a la fecha del accidente
                if (!Util.isStringNullOrEmpty(novedad.getHora())) {
                    accidente.setFechaAcc(Util.dateTimeFormat(Util.dateFormat(novedad.getFecha()) + " " + novedad.getHora()));
                } else {
                    accidente.setFechaAcc(Util.unirFechaHoraByDates(novedad.getFecha(), new Date()));
                }

                if (cambioAccidente) {
                    accidente.setEstadoReg(1);
                }
                accidenteFacadeLocal.edit(accidente);

                AccidenteVehiculo actualizarPorAccidente = null;
                if (novedad.getIdVehiculo() != null) {
                    actualizarPorAccidente = accidenteVehiculoJSF.actualizarPorAccidente(accidente, codVehiculo);
                }
                if (novedad.getIdEmpleado() != null) {
                    accidenteConductorJSF.actualizarPorAccidente(accidente, actualizarPorAccidente, cedula);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buscarAccidente(int i) {
        try {
            cargarUF();
            int i_auxEmp = 0;
            int i_auxVeh = 0;
            if (i_codOpe != null) {
                Empleado empleado = empleadoFacadeLocal.getEmpleadoCodigoTM(i_codOpe);
                if (empleado != null) {
                    i_auxEmp = empleado.getIdEmpleado();
                }
            }
            if (!c_codVeh.isEmpty()) {
                Vehiculo vehiculo = vehiculoFacadeLocal.getVehiculo(c_codVeh, idGopUF);
                if (vehiculo != null) {
                    i_auxVeh = vehiculo.getIdVehiculo();
                }
            }
//            int idGopUF = unidadFuncionalSessionBean.getIdGopUnidadFuncional();
            if (fechaFin.compareTo(fechaIni) < 0) {
                MovilidadUtil.addErrorMessage("Fecha fin no puede ser inferior a fecha inicio");
                return;
            }
            if (i == 0) {
//                listAccidente = accidenteFacadeLocal.findByArguments(i_auxVeh, i_auxEmp, i_auxNovDet, fechaIni, fechaFin, idGopUF);
                listAccidente = accidenteFacadeLocal.findByArguments(i_auxVeh, i_auxEmp, i_auxNovDet, fechaIni, fechaFin, idGopUF);
            }
            if (i == 1) {
                listAccidente = accidenteFacadeLocal.findAccidenteForInformeMaster(i_auxVeh, i_auxEmp, i_auxNovDet, fechaIni, fechaFin, idGopUF);
            }
            if (i == 2) {
                listAccidente = accidenteFacadeLocal.findAccidenteForInformeOperador(i_auxVeh, i_auxEmp, i_auxNovDet, fechaIni, fechaFin, idGopUF);
            }
            if (i == 3) {
                listAccidente = accidenteFacadeLocal.findAccidenteForInformeMasterEdit(i_auxVeh, i_auxEmp, i_auxNovDet, fechaIni, fechaFin, idGopUF);
            }
        } catch (Exception e) {
        }
    }

    public void limpiar() {
        fechaFin = new Date();
        fechaIni = new Date();
        i_codOpe = null;
        c_codVeh = "";
        i_auxNovDet = 0;
    }

    public void cargarListaEmpleado() {
        listEmpleados = empleadoFacadeLocal.findEmpleadosOperadores(0);
    }

    public void onSelectEmpleado(Empleado event) {
        if (event.getCodigoTm() == null) {
            MovilidadUtil.addErrorMessage("Opción no valida.");
            MovilidadUtil.hideModal("listEmpDlg");
            return;
        }
        accidente.setIdMaster(event);
        MovilidadUtil.addSuccessMessage("Primer Responsable cargado correctamente");
        MovilidadUtil.hideModal("listEmpDlg");
    }

    public void cargarOpeMaster() {
        if (i_codOpeMaster == null) {
            return;
        }
        Empleado empleado = empleadoFacadeLocal.getEmpleadoCodigoTM(i_codOpeMaster);
        if (empleado == null) {
            MovilidadUtil.addErrorMessage("No se encontró operador con el código registrado");
            return;
        }
        if (empleado.getIdEmpleadoCargo() == null) {
            MovilidadUtil.addErrorMessage("El operador no cuenta con un cargo asociado");
        }
//                        if (empleado.getIdEmpleadoCargo().getIdEmpleadoTipoCargo() == Util.ID_OPE_MASTER
//                                || empleado.getIdEmpleadoCargo().getNombreCargo().equalsIgnoreCase(Util.OPE_MASTER)) {
        accidente.setIdMaster(empleado);
        MovilidadUtil.addSuccessMessage("Primer Responsable cargado correctamente");
        i_codOpeMaster = null;
//                            return;
//                        }
//                        MovilidadUtil.addErrorMessage("El código ingresado no corresponde a Operador Master");
    }

//    int cargarMesesOperando(Date fechaEmp, Date fechaAcc) {
//        Calendar ingresoEmp = Calendar.getInstance();
//        Calendar fechaAccidente = Calendar.getInstance();
//        ingresoEmp.setTime(fechaEmp); //fecha de ingreso del empleado
//        fechaAccidente.setTime(fechaAcc); // fecha actual del accidente
//        int difA = fechaAccidente.get(Calendar.YEAR) - ingresoEmp.get(Calendar.YEAR);
//        int difM = difA * 12 + fechaAccidente.get(Calendar.MONTH) - ingresoEmp.get(Calendar.MONTH);
//        return Math.abs(difM);
//    }
//    int getDiaSemana(Date d) {
//        Calendar c = Calendar.getInstance();
//        c.setTime(d); // vairables int
//        return c.get(Calendar.DAY_OF_WEEK);
//    }
    public void agregarNumIpat() {
        if (b_ipat) {
            MovilidadUtil.openModal("ipatDlg");
        } else {
            b_ipat = false;
            accidente.setNroIpat(null);
        }
    }

    public void limpiarNumIpat() {
        if (Util.isStringNullOrEmpty(accidente.getNroIpat())) {
            accidente.setNroIpat(null);
            b_ipat = false;
        }
    }

    void documentosAcc(Accidente a) {
        HashMap<String, EmpleadoTipoDocumentos> mapEmpTpDocumento = new HashMap<>();
        HashMap<String, VehiculoTipoDocumentos> mapVehTpDocumento = new HashMap<>();
        HashMap<String, AccTipoDocs> mapAccTpDocs = new HashMap<>();
        List<EmpleadoTipoDocumentos> listEmpTpDocs = empleadoTipoDocumentosFacadeLocal.findAllEstadoReg();
        if (listEmpTpDocs != null) {
            listEmpTpDocs.stream().filter((etd) -> (etd.getCodigo() != null)).forEachOrdered((etd) -> {
                mapEmpTpDocumento.put(etd.getCodigo(), etd);
            });
        }
        List<VehiculoTipoDocumentos> listVehTpDocs = vehiculoTipoDocumentoFacadeLocal.findAllEstadoReg();
        if (listVehTpDocs != null) {
            listVehTpDocs.stream().filter((vtd) -> (vtd.getCodigo() != null)).forEachOrdered((vtd) -> {
                mapVehTpDocumento.put(vtd.getCodigo(), vtd);
            });
        }
        List<AccTipoDocs> listAccTpDocs = accTipoDocsFacadeLocal.estadoReg();
        if (listAccTpDocs != null) {
            listAccTpDocs.stream().filter((atd) -> (atd.getCodigo() != null)).forEachOrdered((atd) -> {
                mapAccTpDocs.put(atd.getCodigo(), atd);
            });
        }
        //Empleados
        AccidenteDocumento ad;
        if (a.getIdEmpleado() != null) {
            for (AccTipoDocs atd : mapAccTpDocs.values()) {
                EmpleadoTipoDocumentos etd = mapEmpTpDocumento.get(atd.getCodigo());
                if (etd != null) {
                    if (etd.getVencimiento() == 1) {
                        EmpleadoDocumentos empDocs = empleadoDocumentosFacadeLocal.findByActivoAndVigente(a.getIdEmpleado().getIdEmpleado(),
                                etd.getIdEmpleadoTipoDocumento(),
                                Util.dateFormat(a.getFechaAcc()));
                        if (empDocs != null && empDocs.getPathDocumento() != null) {
                            if (!empDocs.getPathDocumento().isEmpty()) {
                                ad = new AccidenteDocumento();
                                ad.setIdAccidente(a);
                                ad.setFecha(a.getFechaAcc());
                                ad.setDescripcion("Obtenido del sistema");
                                ad.setCreado(new Date());
                                ad.setModificado(new Date());
                                ad.setUsername(user.getUsername());
                                ad.setEstadoReg(0);
                                ad.setPath(empDocs.getPathDocumento());
                                ad.setIdAccTipoDocs(atd);
                                accidenteDocumentoFacadeLocal.create(ad);
                            }
                        }
                    } else {
                        EmpleadoDocumentos empDocs = empleadoDocumentosFacadeLocal.findByActivo(a.getIdEmpleado().getIdEmpleado(),
                                etd.getIdEmpleadoTipoDocumento());
                        if (empDocs != null && empDocs.getPathDocumento() != null) {
                            if (!empDocs.getPathDocumento().isEmpty()) {
                                ad = new AccidenteDocumento();
                                ad.setIdAccidente(a);
                                ad.setFecha(a.getFechaAcc());
                                ad.setDescripcion("Obtenido del sistema");
                                ad.setCreado(new Date());
                                ad.setModificado(new Date());
                                ad.setUsername(user.getUsername());
                                ad.setEstadoReg(0);
                                ad.setPath(empDocs.getPathDocumento());
                                ad.setIdAccTipoDocs(atd);
                                accidenteDocumentoFacadeLocal.create(ad);
                            }
                        }
                    }
                }
            }
        }
        //Vehiculos
        if (a.getIdVehiculo() != null) {
            for (AccTipoDocs atd : mapAccTpDocs.values()) {
                VehiculoTipoDocumentos vtd = mapVehTpDocumento.get(atd.getCodigo());
                if (vtd != null) {
                    if (vtd.getVencimiento() == 1) {
                        VehiculoDocumentos vehDocs = vehiculoDocumentoFacadeLocal.findByActivoAndVigente(a.getIdVehiculo().getIdVehiculo(),
                                vtd.getIdVehiculoTipoDocumento(),
                                Util.dateFormat(a.getFechaAcc()));
                        if (vehDocs != null && vehDocs.getPathDocumento() != null) {
                            if (!vehDocs.getPathDocumento().isEmpty()) {
                                ad = new AccidenteDocumento();
                                ad.setIdAccidente(a);
                                ad.setFecha(a.getFechaAcc());
                                ad.setDescripcion("Obtenido del sistema");
                                ad.setCreado(new Date());
                                ad.setModificado(new Date());
                                ad.setUsername(user.getUsername());
                                ad.setEstadoReg(0);
                                ad.setPath(vehDocs.getPathDocumento());
                                ad.setIdAccTipoDocs(atd);
                                accidenteDocumentoFacadeLocal.create(ad);
                            }
                        }
                    } else {
                        VehiculoDocumentos vehDocs = vehiculoDocumentoFacadeLocal.findByActivo(
                                a.getIdVehiculo().getIdVehiculo(),
                                vtd.getIdVehiculoTipoDocumento());
                        if (vehDocs != null && vehDocs.getPathDocumento() != null) {
                            if (!vehDocs.getPathDocumento().isEmpty()) {
                                ad = new AccidenteDocumento();
                                ad.setIdAccidente(a);
                                ad.setFecha(a.getFechaAcc());
                                ad.setDescripcion("Obtenido del sistema");
                                ad.setCreado(new Date());
                                ad.setModificado(new Date());
                                ad.setUsername(user.getUsername());
                                ad.setEstadoReg(0);
                                ad.setPath(vehDocs.getPathDocumento());
                                ad.setIdAccTipoDocs(atd);
                                accidenteDocumentoFacadeLocal.create(ad);
                            }
                        }
                    }
                }
            }
        }
    }

    void generarKmRecorridoOperador(Accidente a) {
        if (a.getIdEmpleado() == null) {
            return;
        }
        if (Util.dateToTime(a.getFechaAcc()).equals("00:00")) {
            return;
        }

        Object kmRecorrido = prgTcFacadeLocal
                .obtenerKmRecorridosByOperador(
                        a.getIdEmpleado().getIdEmpleado(),
                        Util.dateFormat(a.getFechaAcc()),
                        Util.dateToTime(a.getFechaAcc()));

        if (kmRecorrido != null) {
            BigDecimal d = (BigDecimal) kmRecorrido;
            d = d.divide(BigDecimal.valueOf(1000));
            accidente.setKmRecorridoOperador(d);
        }
    }
    // Proceso para alertar la reincidencia del operador con respecto al accidente
    // int reincidencia para sumar reincidencias de acuerdo a criterio de implementacion

    void alertaReincidenciaAcc(Accidente a, int reincidencia) {
        try {
            if (a.getIdEmpleado() == null) {
                return;
            }
            if (a.getIdNovedadTipoDetalle().getIdNovedadTipoDetalle() == 103) {
                return;
            }
            AccReincidencia accRein;
            List<AccReincidencia> findAllEstadoReg = accReincidenciaFacadeLocal.findAllEstadoReg();
            if (findAllEstadoReg.isEmpty()) {
                return;
            }
            accRein = findAllEstadoReg.get(0);
            String desde = Util.dateFormat(
                    MovilidadUtil.sumarDias(
                            a.getFechaAcc(),
                            accRein.getDias() * -1));
            String hasta = Util.dateFormat(
                    MovilidadUtil.sumarDias(
                            a.getFechaAcc(),
                            accRein.getDias()));
            List<Accidente> listAccReincidente = accidenteFacadeLocal
                    .findAllByIdEmpleadoAndDates(
                            0,
                            a.getIdEmpleado().getIdEmpleado(),
                            desde,
                            hasta, idGopUnidadFuncional);

            if (listAccReincidente != null && listAccReincidente.isEmpty()) {
                return;
            }
            NotificacionCorreoConf conf = NCCEJB.find(1);
            if (conf == null) {
                return;
            }
            NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(ConstantsUtil.TEMPLATE_REINCIDENCIA_ACC);
            if (template == null) {
                return;
            }
            Map mapa = SendMails.getMailParams(conf, template);
            Map mailProperties = new HashMap();
            mailProperties.put("fecha", Util.dateFormat(a.getFechaAcc()));
            mailProperties.put("tipo", a.getIdNovedadTipoDetalle().getIdNovedadTipo().getNombreTipoNovedad());
            mailProperties.put("detalle", a.getIdNovedadTipoDetalle().getTituloTipoNovedad());
            mailProperties.put("operador", a.getIdEmpleado() != null ? a.getIdEmpleado().getNombresApellidos() : "");
            mailProperties.put("reincidencia", listAccReincidente.size() + reincidencia);
            mailProperties.put("rango", accRein.getDias());
            mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
            SendMails.sendEmail(mapa,
                    mailProperties,
                    accRein.getIdNotificacionProceso().getNombreProceso(),
                    "",
                    accRein.getIdNotificacionProceso().getEmails(),
                    "Notificaciones", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarUF() {
        idGopUF = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
    }

    public int getI_numDia() {
        return i_numDia;
    }

    public void setI_numDia(int i_numDia) {
        this.i_numDia = i_numDia;
    }

    public Integer getI_codOpeMaster() {
        return i_codOpeMaster;
    }

    public void setI_codOpeMaster(Integer i_codOpeMaster) {
        this.i_codOpeMaster = i_codOpeMaster;
    }

    public int getI_opcion() {
        return i_opcion;
    }

    public void setI_opcion(int i_opcion) {
        this.i_opcion = i_opcion;
    }

    public int getI_idAccNovTipDet() {
        return i_idAccNovTipDet;
    }

    public void setI_idAccNovTipDet(int i_idAccNovTipDet) {
        this.i_idAccNovTipDet = i_idAccNovTipDet;
    }

    public int getI_idAccTipServ() {
        return i_idAccTipServ;
    }

    public void setI_idAccTipServ(int i_idAccTipServ) {
        this.i_idAccTipServ = i_idAccTipServ;
    }

    public int getI_idAccClas() {
        return i_idAccClas;
    }

    public void setI_idAccClas(int i_idAccClas) {
        this.i_idAccClas = i_idAccClas;
    }

    public int getI_idAccDetCla() {
        return i_idAccDetCla;
    }

    public void setI_idAccDetCla(int i_idAccDetCla) {
        this.i_idAccDetCla = i_idAccDetCla;
    }

    public int getI_idAccTipTur() {
        return i_idAccTipTur;
    }

    public void setI_idAccTipTur(int i_idAccTipTur) {
        this.i_idAccTipTur = i_idAccTipTur;
    }

    public int getI_idAccActRealizada() {
        return i_idAccActRealizada;
    }

    public void setI_idAccActRealizada(int i_idAccActRealizada) {
        this.i_idAccActRealizada = i_idAccActRealizada;
    }

    public boolean isB_ipat() {
        return b_ipat;
    }

    public void setB_ipat(boolean b_ipat) {
        this.b_ipat = b_ipat;
    }

    public boolean isB_casoTransmilenio() {
        return b_casoTransmilenio;
    }

    public void setB_casoTransmilenio(boolean b_casoTransmilenio) {
        this.b_casoTransmilenio = b_casoTransmilenio;
    }

    public boolean isB_juridica() {
        return b_juridica;
    }

    public void setB_juridica(boolean b_juridica) {
        this.b_juridica = b_juridica;
    }

    public Accidente getAccidente() {
        return accidente;
    }

    public void setAccidente(Accidente accidente) {
        this.accidente = accidente;
    }

    public List<Accidente> getListAccidente() {
        //   listAccidente = accidenteFacadeLocal.findAll();
        return listAccidente;
    }

    public List<NovedadTipoDetalles> getListNovedadTipoDetalles() {
        listNovedadTipoDetalles = novedadTipoDetalleFacadeLocal.findTipoAcc();
        return listNovedadTipoDetalles;
    }

    public boolean isB_flag() {
        return b_flag;
    }

    public int getI_auxNovDet() {
        return i_auxNovDet;
    }

    public void setI_auxNovDet(int i_auxNovDet) {
        this.i_auxNovDet = i_auxNovDet;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getC_codVeh() {
        return c_codVeh;
    }

    public void setC_codVeh(String c_codVeh) {
        this.c_codVeh = c_codVeh;
    }

    public Integer getI_codOpe() {
        return i_codOpe;
    }

    public void setI_codOpe(Integer i_codOpe) {
        this.i_codOpe = i_codOpe;
    }

    public List<Empleado> getListEmpleados() {
        return listEmpleados;
    }

    public void setListEmpleados(List<Empleado> listEmpleados) {
        this.listEmpleados = listEmpleados;
    }

    public List<GopUnidadFuncional> getLstUnidadesFuncionales() {
        return lstUnidadesFuncionales;
    }

    public void setLstUnidadesFuncionales(List<GopUnidadFuncional> lstUnidadesFuncionales) {
        this.lstUnidadesFuncionales = lstUnidadesFuncionales;
    }

    public int getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(int idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    public boolean isFlagListaUF() {
        return flagListaUF;
    }

    public void setFlagListaUF(boolean flagListaUF) {
        this.flagListaUF = flagListaUF;
    }

    public boolean isIsRolAbogado() {
        return isRolAbogado;
    }

    public void setIsRolAbogado(boolean isRolAbogado) {
        this.isRolAbogado = isRolAbogado;
    }

    public Integer getIdAccAbogado() {
        return idAccAbogado;
    }

    public void setIdAccAbogado(Integer idAccAbogado) {
        this.idAccAbogado = idAccAbogado;
    }

    public Integer getIdAccAtencionVia() {
        return idAccAtencionVia;
    }

    public void setIdAccAtencionVia(Integer idAccAtencionVia) {
        this.idAccAtencionVia = idAccAtencionVia;
    }

    public Integer getIdAccDesplaza() {
        return idAccDesplaza;
    }

    public void setIdAccDesplaza(Integer idAccDesplaza) {
        this.idAccDesplaza = idAccDesplaza;
    }

    public List<AccDesplazaA> getLstAccDesplazamientos() {
        return lstAccDesplazamientos;
    }

    public void setLstAccDesplazamientos(List<AccDesplazaA> lstAccDesplazamientos) {
        this.lstAccDesplazamientos = lstAccDesplazamientos;
    }

}
