/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.dto.TpConteoDTO;
import com.movilidad.ejb.MyAppConfirmDepotEntryFacadeLocal;
import com.movilidad.ejb.MyAppConfirmDepotExitFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.PrgAsignacionFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.ejb.TecnicoPatioFacadeLocal;
import com.movilidad.ejb.TecnicoPatioNovedadFacadeLocal;
import com.movilidad.ejb.TecnicoPatioNovedadTipoFacadeLocal;
import com.movilidad.ejb.TecnicoPatioTipoTablaFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.MyAppConfirmDepotEntry;
import com.movilidad.model.MyAppConfirmDepotExit;
import com.movilidad.model.Novedad;
import com.movilidad.model.PrgAsignacion;
import com.movilidad.model.PrgTc;
import com.movilidad.model.TecnicoPatio;
import com.movilidad.model.TecnicoPatioNovedad;
import com.movilidad.model.TecnicoPatioNovedadTipo;
import com.movilidad.model.TecnicoPatioTipoTabla;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import static java.util.regex.Pattern.matches;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Julián Arévalo
 */
@Named(value = "tecnicoPatioController")
@ViewScoped
public class TecnicoPatioBean implements Serializable {

    int uf = 0;

    private Util util;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    @EJB
    private TecnicoPatioFacadeLocal tecnicoPatioEJB;
    @EJB
    private TecnicoPatioFacadeLocal tecnicoPatioFacadeLocal;
    @EJB
    private VehiculoFacadeLocal vehiculoEjb;
    @EJB
    private PrgTcFacadeLocal prgtcEJB;
    @EJB
    private NovedadFacadeLocal novedadEJB;
    @EJB
    private MyAppConfirmDepotEntryFacadeLocal confirmarMyAppEntryEJB;
    @EJB
    private MyAppConfirmDepotExitFacadeLocal confirmarMyAppExitEJB;
    @EJB
    private TecnicoPatioTipoTablaFacadeLocal tecnicoPatioTipoTablaEJB;
    @EJB
    private TecnicoPatioNovedadTipoFacadeLocal tecnicoPatioNovedadTipoEJB;
    @EJB
    private TecnicoPatioNovedadFacadeLocal tecnicoPatioNovedadEJB;
    @EJB
    private PrgAsignacionFacadeLocal prgAsigEJB;

    private List<TecnicoPatio> listTecnicoPatio;
    private List<TecnicoPatio> servicesON;
    private List<TecnicoPatio> servicesOFF;
    private List<Vehiculo> lstVehiculo;
    private List<TpConteoDTO> lstTpConteoDTO;
    private List<Vehiculo> lstVehiculoDisponible;
    private List<TecnicoPatioNovedadTipo> lstNovedadTipo;
    private Vehiculo vehiculo;
    private PrgTc prgTc;
    //private TecnicoPatioEntradaDTO entrada;
    private Novedad novedad;
    //private ConfirmarEntradaTpDTO confirmarDTO;
    private MyAppConfirmDepotEntry confirmarMyAppEntry;
    private MyAppConfirmDepotExit confirmarMyAppExit;
    private TecnicoPatioTipoTabla tecnicoPatioTipoTabla;
    private TecnicoPatioNovedadTipo tecnicoPatioNovedadTipo;
    private TecnicoPatioNovedad tecnicoPatioNovedad;
    private int idPrgTc;
    private int flagBusqueda;
    private String qr_code;
    private final PrimeFaces current = PrimeFaces.current();
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private String servbusForAsignar;

    public TecnicoPatioBean() {
        this.vehiculo = null;
        this.util = null;
        //this.entrada = null;
        this.novedad = null;
        this.confirmarMyAppEntry = null;
        this.confirmarMyAppExit = null;
        //this.confirmarDTO = null;
        this.tecnicoPatioTipoTabla = null;
        this.lstVehiculo = null;
        this.lstVehiculoDisponible = null;
        this.lstTpConteoDTO = null;
        this.tecnicoPatioNovedadTipo = null;
        this.lstNovedadTipo = null;
        this.idPrgTc = 0;
        this.tecnicoPatioNovedad = null;
    }

    @PostConstruct
    public void init() {
        lstVehiculo = this.vehiculoEjb.findAllVehiculosByidGopUnidadFuncional(
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        lstNovedadTipo = tecnicoPatioNovedadTipoEJB.findAll();
        uf = unidadFuncionalSessionBean.getIdGopUnidadFuncional() == 0 ? 1 : unidadFuncionalSessionBean.getIdGopUnidadFuncional();
        servicesON = new ArrayList<>();  // Inicializa la lista servicesON como ArrayList
        servicesOFF = new ArrayList<>();
        this.vehiculo = new Vehiculo();
        this.tecnicoPatioNovedadTipo = new TecnicoPatioNovedadTipo();
        this.util = new Util();
        //this.entrada = new TecnicoPatioEntradaDTO();
        this.novedad = new Novedad();
        this.confirmarMyAppEntry = new MyAppConfirmDepotEntry();
        this.confirmarMyAppExit = new MyAppConfirmDepotExit();
        //this.confirmarDTO = new ConfirmarEntradaTpDTO();
        this.tecnicoPatioTipoTabla = new TecnicoPatioTipoTabla();
        consultarEntradasSalidas();
    }

    private boolean isLowestHour(String time) {
        return isCurrentTimeLowestGreen(time);
    }

    private Date seterTime(Date time, boolean flag) {
        Date date;
        if (flag) {
            date = addDaysToCurrentDate(1);
        } else {
            date = new Date();
        }

        date.setHours(time.getHours());
        date.setMinutes(time.getMinutes());
        date.setSeconds(time.getSeconds());
        return date;
    }

    public void Guardar(int tipo, int idtarea, int fromqr) {
        Date d = new Date();
        Integer typeTableEvent;
        String formattedDate = util.getDateFormatYYYYMMDD(d);
        String formattedHour = util.getDateFormatHHmmss(d);

        if (vehiculo.getCodigo() == null) {
            MovilidadUtil.updateComponent(":frmEntradaSalida:messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar un vehículo");
            return;
        }
        vehiculo = vehiculoEjb.findByCodigo(vehiculo.getCodigo());
        if (vehiculo.getIdGopUnidadFuncional().getIdGopUnidadFuncional() != uf && fromqr == 1 && uf != 0) {
            MovilidadUtil.updateComponent(":frmEntradaSalida:messages");
            MovilidadUtil.addErrorMessage("El vehículo escaneado no corresponde con la unidad funcional asiganda");
            return;
        }
        if (idtarea != 0) {
            prgTc = prgtcEJB.findPrgTcEntradaId(idtarea, formattedHour);
            if (prgTc.getIdPrgTc() == null) {
                MovilidadUtil.updateComponent(":frmEntradaSalida:messages");
                MovilidadUtil.addErrorMessage("La entrada que intenta realizar no está en el rango permitido. (Máximo 1 hora antes del tiempo de origen " + prgtcEJB.find(idtarea).getTimeOrigin() + ")");
                return;
            }
        } else {

            prgTc = tipo == 1 ? prgtcEJB.findPrgTcEntrada(vehiculo.getIdVehiculo(), formattedDate, formattedHour) : prgtcEJB.findPrgTcSalida(vehiculo.getIdVehiculo(), formattedDate, formattedHour);
            if (prgTc.getIdPrgTc() == null) {
                MovilidadUtil.updateComponent(":frmEntradaSalida:messages");
                MovilidadUtil.addErrorMessage(tipo == 1 ? "No se encuentra servicio de entrada de patio" : "No se encuentra servicio de salida de patio");
                return;
            }
        }

        novedad = novedadEJB.findNovedadAfectaDisponibilidadFechaVehiculo(vehiculo.getIdVehiculo(), d);

        if (prgTc.getIdPrgTc() <= 0) {
            MovilidadUtil.updateComponent(":frmEntradaSalida:messages");
            MovilidadUtil.addErrorMessage("Tarea invalida");
            return;
        }
        if (prgTc == null) {
            MovilidadUtil.updateComponent(":frmEntradaSalida:messages");
            MovilidadUtil.addErrorMessage("No se encuentra tarea asociada al identificador");
            return;
        }
        tecnicoPatioTipoTabla = tecnicoPatioTipoTablaEJB.findTipoTabla(uf, formattedDate, vehiculo.getCodigo());
        if (tecnicoPatioTipoTabla == null) {
            MovilidadUtil.updateComponent(":frmEntradaSalida:messages");
            MovilidadUtil.addErrorMessage("No se encuentra servicio");
            return;
        }
        if (tipo == 1) {//Entrada
            if (!prgTc.getToStop().getIsDepot().equals(1)) {
                MovilidadUtil.updateComponent(":frmEntradaSalida:messages");
                MovilidadUtil.addErrorMessage("Servicio no corresponde a una entrada de patio");
                return;
            }
            typeTableEvent = typeShortTable(tecnicoPatioTipoTabla, tipo, vehiculo.getCodigo(), uf, d);
            MyAppConfirmDepotEntry entryConfirm = confirmarMyAppEntryEJB.findServiceConfirmate(prgTc.getIdPrgTc());
            if (entryConfirm != null) {
                MovilidadUtil.updateComponent(":frmEntradaSalida:messages");
                MovilidadUtil.addErrorMessage("Servicio ha sido confirmado el día: " + util.getDateFormatYYYYMMDD(entryConfirm.getCreado()));
                return;
            }
            if (confirmarMyAppEntryEJB.findByIdPrgTc(idPrgTc)!= null) {
                MovilidadUtil.updateComponent(":frmEntradaSalida:messages");
                MovilidadUtil.addErrorMessage("Ya se confirmó");
                return;
            }

            confirmarMyAppEntry.setCreado(d);
            confirmarMyAppEntry.setEstadoReg(0);
            confirmarMyAppEntry.setModificado(d);
            confirmarMyAppEntry.setIdTask(prgTc.getIdPrgTc());
            confirmarMyAppEntry.setIdPrgTc(prgTc);
            confirmarMyAppExit.setUsername(user.getUsername());
            confirmarMyAppEntry.setVerbo("POST");
            confirmarMyAppEntry.setFechaHora(d);
            confirmarMyAppEntry.setProcesado(0);
            confirmarMyAppEntry.setTipoTabla(typeTableEvent);
            try {
                confirmarMyAppEntryEJB.create(confirmarMyAppEntry);
                MovilidadUtil.updateComponent(":frmEntradaSalida:messages");
                MovilidadUtil.addSuccessMessage("Entrada confirmada para el vehículo " + vehiculo.getCodigo() + " con entrada programada a las " + prgTc.getTimeDestiny() + ", se confirma a las " + formattedDate + " " + formattedHour);
            } catch (Exception e) {
                MovilidadUtil.updateComponent(":frmEntradaSalida:messages");
                MovilidadUtil.addErrorMessage("ocurrió un error");
            }
        } else if (tipo == 0) { //Salida
            if (!prgTc.getFromStop().getIsDepot().equals(1)) {
                MovilidadUtil.updateComponent(":frmEntradaSalida:messages");
                MovilidadUtil.addErrorMessage("Servicio no corresponde a una salida de patio");
            }
            typeTableEvent = typeShortTable(tecnicoPatioTipoTabla, tipo, vehiculo.getCodigo(), uf, d);
            MyAppConfirmDepotExit exitConfirm = confirmarMyAppExitEJB.findServiceConfirmate(prgTc.getIdPrgTc());
            if (exitConfirm != null) {
                MovilidadUtil.updateComponent(":frmEntradaSalida:messages");
                MovilidadUtil.addErrorMessage("Servicio ha sido confirmado el día: " + util.getDateFormatYYYYMMDD(exitConfirm.getCreado()));
                return;
            }
            /*
			 * Validar si el vehiculo ya ha realizado su check list
             */
//			dto.setForzarSalida(true);// Siempre salida forzada
//			if (!dto.isForzarSalida()) {
//				Date inicioServicio = uConverter.unirFechaConHoras(obj.getFecha(), obj.getTimeOrigin());
//				boolean isValid = chkDiarioService.existeCheckListByVehiculoAndFechaAndTiempo(
//						obj.getVehiculo().getIdVehiculo(), obj.getEmpleado().getIdEmpleado(),
//						obj.getIdGopUnidadFuncional().getIdGopUnidadFuncional(), inicioServicio);
//				if (!isValid) {
//					// se retorna 1 en el id_data para saber que puede forzar la opcion de confirmar
//					// la salida
//					confirmGeneralDTO.setMessage("Vehículo no cuenta con check list realizado");
//					confirmGeneralDTO.setValid(false);
//					return confirmGeneralDTO;
//				}
//			}

            if (confirmarMyAppExitEJB.findByIdPrgTc(prgTc.getIdPrgTc())!= null) {
                MovilidadUtil.updateComponent(":frmEntradaSalida:messages");
                MovilidadUtil.addErrorMessage("Ya se confirmó");
                return;
            }
            confirmarMyAppExit.setCreado(d);
            confirmarMyAppExit.setEstadoReg(0);
            confirmarMyAppExit.setModificado(d);
            confirmarMyAppExit.setIdTask(prgTc.getIdPrgTc());
            confirmarMyAppExit.setIdPrgTc(prgTc);
            confirmarMyAppExit.setVerbo("POST");
            confirmarMyAppExit.setUsername(user.getUsername());
            confirmarMyAppExit.setFechaHora(d);
            confirmarMyAppExit.setProcesado(0);
            confirmarMyAppExit.setForzar(1);
            confirmarMyAppExit.setTipoTabla(typeTableEvent);
            try {
                confirmarMyAppExitEJB.create(confirmarMyAppExit);
                MovilidadUtil.updateComponent(":frmEntradaSalida:messages");
                MovilidadUtil.addSuccessMessage("Salida confirmada para el vehículo " + vehiculo.getCodigo() + " con salida programada a las " + prgTc.getTimeOrigin() + ", se confirma a las " + formattedDate + " " + formattedHour);
            } catch (Exception e) {
                MovilidadUtil.updateComponent(":frmEntradaSalida:messages");
                MovilidadUtil.addErrorMessage("ocurrió un error");
            }

        }
        if (fromqr == 1) {
            current.executeScript("PF('fotoDialogTp').hide();");
        }
        //init();

    }

    private int typeShortTable(TecnicoPatioTipoTabla typeTable, int typeEvent, String codigoVehiculo, Integer idGopUf,
            Date d) {
        Integer resultado = 0;
        List confirmed = null;
        if (typeTable.getNumEntryDepot() > 1) {
            if (typeEvent == 1) {
                confirmed = confirmarMyAppEntryEJB.findTypeServiceConfirmateByVehiculoAndFecha(codigoVehiculo, idGopUf, d);
                if (confirmed != null) {
                    if (confirmed.isEmpty()) {
                        resultado = 3;
                    } else {
                        resultado = typeTable.getNumEntryDepot();
                    }
                } else {
                    resultado = typeTable.getNumEntryDepot();
                }
            } else if (typeEvent == 0) {
                confirmed = confirmarMyAppExitEJB.findTypeServiceConfirmateByVehiculoAndFecha(codigoVehiculo, idGopUf, d);
                if (confirmed != null) {
                    if (!confirmed.isEmpty()) {
                        resultado = 3;
                    } else {
                        resultado = typeTable.getNumEntryDepot();
                    }
                } else {
                    resultado = typeTable.getNumEntryDepot();
                }
            }
        } else {
            resultado = typeTable.getNumEntryDepot();
        }
        return resultado;
    }

    public boolean isCurrentTimeLowestGreen(String date1) {
        boolean flag = false;
        if (Objects.nonNull(date1)) {
            Date dateA = seterTime(getDateFromString(date1, "HH:mm:ss"), isFormatHHMMSSGreat24(date1));
            Date dateC = new Date();
            flag = isTimeLowestTo(dateA, dateC);
        }
        return flag;
    }

    public boolean isFormatHHMMSSGreat24(String hour) {
        String regex = "30:00:00$|(2[4-9])(:[0-5][0-9]){2}$";
        return matches(regex, hour);
    }

    public Date addDaysToCurrentDate(int days) {
        return addDaysToDate(new Date(), days);
    }

    public Date addDaysToDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    private Date getDateFormat(String date, String format) {
        Date dateFormat = null;
        try {
            SimpleDateFormat sf = new SimpleDateFormat(format);
            dateFormat = sf.parse(date);
        } catch (Exception ex) {
        }
        return dateFormat;
    }

    private boolean isTimeLowestTo(Date date1, Date date3) {
        boolean flag = false;
        if (Objects.nonNull(date1) && Objects.nonNull(date3)) {
            if (date3.before(date1)) {
                flag = true;
            }
        }
        return flag;
    }

    public Date getDateFromString(String date, String format) {
        return getDateFormat(date, format);
    }

    public void consultarEntradasSalidas() {
        Date d = new Date();
        listTecnicoPatio = tecnicoPatioEJB.findAll(uf, d);
        for (TecnicoPatio obj : listTecnicoPatio) {
            String dateOfficial = "";
            dateOfficial = obj.getType_entry() == 1 ? obj.getTime_destiny() : obj.getTime_origin();
            if (isLowestHour(dateOfficial)) {
                servicesON.add(obj);
            } else {
                servicesOFF.add(obj);
            }
        }
        MovilidadUtil.updateComponent("formTp,tbltpOn,tbltpOff");
    }

    public void prepareLeerQR() {
        MovilidadUtil.openModal("fotoDialogTp");
    }

    public void procesarValorQr() {
        if (qr_code.equals("")) {
            MovilidadUtil.addErrorMessage("No se detectó un código QR, intente nuevamente.");
            return;
        } else {
            vehiculo = vehiculoEjb.findByCodigo(qr_code);
//            if (vehiculo != null) {
//                current.executeScript("PF('fotoDialogTp').hide();");
//                current.executeScript("PF('mentradasalidaqr').show();");
//            }

        }
//    if (flagBusqueda == 1) {
//        responsable = sstEmpresaVisitanteEJB.findByHashString(qr_code);
//        if (responsable == null) {
//            MovilidadUtil.addErrorMessage("No existe registro con el codigo escaneado.");
//        } else {
//            MovilidadUtil.addSuccessMessage("Responsable cargado.");
//        }
//        MovilidadUtil.updateComponent("aseo_bano_dialog_form:label_responsable");
//    } else if (flagBusqueda == 2) {
//        aseoParamArea = aseoParamAreaEJB.findByHashString(qr_code);
//        if (aseoParamArea == null) {
//            MovilidadUtil.addErrorMessage("No existe registro con el codigo escaneado.");
//        } else {
//            MovilidadUtil.addSuccessMessage("Area cargada.");
//        }
//        MovilidadUtil.updateComponent("aseo_bano_dialog_form:label_area");
//    }

        //MovilidadUtil.updateComponent("modalEntradaSalida:messages");
        //MovilidadUtil.hideModal("fotoDialogTp");
    }

    public void estadoActual() {
        MovilidadUtil.openModal("estadpv");
        lstTpConteoDTO = tecnicoPatioEJB.findCounterTp(uf, new Date());
    }

    public void busesDiponibles() {
        MovilidadUtil.openModal("disponiblestpv");
        lstVehiculoDisponible = vehiculoEjb.getDisponibles(new Date(), uf);
    }

    public void guardarNovedadSalida() {
        try {
            tecnicoPatioNovedad = new TecnicoPatioNovedad();
            tecnicoPatioNovedadTipo = tecnicoPatioNovedadTipoEJB.find(tecnicoPatioNovedadTipo.getIdNovedadTipo());
            tecnicoPatioNovedad.setPrgTc(prgtcEJB.find(idPrgTc));
            tecnicoPatioNovedad.setEstadoReg(1);
            tecnicoPatioNovedad.setTpNovedadTipo(tecnicoPatioNovedadTipo);
            tecnicoPatioNovedad.setUsername(user.getUsername());
            tecnicoPatioNovedad.setCreado(new Date());
            tecnicoPatioNovedadEJB.create(tecnicoPatioNovedad);
            MovilidadUtil.updateComponent(":frmNovedad:messagesNovedad,msgs");
            MovilidadUtil.addSuccessMessage("Se agregó la novedad al registro.");
            init();
        } catch (Exception e) {
            MovilidadUtil.updateComponent(":frmNovedad:messagesNovedad,msgs");
            MovilidadUtil.addErrorMessage("Ocurrió un error al momento de guardar la novedad.");
        }
    }

    public String nombreNovedadTipo(int idNovedadTipo) {
        for (TecnicoPatioNovedadTipo novedadTipo : lstNovedadTipo) {
            if (novedadTipo.getIdNovedadTipo() == idNovedadTipo) {
                return novedadTipo.getNombre();
            }
        }
        return "No encontrado";
    }

    public void asignar() {
        MovilidadUtil.openModal("AsignarDialog");
    }

    public void buscarPrgTcSinVehiculo() {
        if (!MovilidadUtil.stringIsEmpty(servbusForAsignar)) {
            /**
             * Consultar objeto PrgTc por medio de servbus sin Vhículo asignado
             */
            prgTc = prgtcEJB.buscarPrgTcSinVehiculo(servbusForAsignar, new Date(), unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            if (prgTc == null) {
                MovilidadUtil.addErrorMessage("Servbus digitado no existe.");
                return;
            }
            if (vehiculo != null) {
                if (!validarUnidadFuncIgual(vehiculo, prgTc)) {
                    MovilidadUtil.addErrorMessage("Vehículo y Servbus no comparten la misma unidad funcional.");
                    prgTc = null;
                    return;
                }
            }
            MovilidadUtil.addSuccessMessage("Servbus listo para recibir asignación");

        }

    }

    boolean validarUnidadFuncIgual(Vehiculo veh, PrgTc tc) {
        if (veh.getIdGopUnidadFuncional() != null
                && tc.getIdGopUnidadFuncional() != null) {
            return veh.getIdGopUnidadFuncional().getIdGopUnidadFuncional()
                    .equals(tc.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
        }
        return true;
    }

    public void guardarAsignacion() {
        if (vehiculo.getCodigo() == null) {
            MovilidadUtil.updateComponent(":frmEntradaSalida:messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar un vehículo");
            return;
        }
        vehiculo = vehiculoEjb.findByCodigo(vehiculo.getCodigo());
        if (vehiculo.getIdGopUnidadFuncional().getIdGopUnidadFuncional() != uf && uf != 0) {
            MovilidadUtil.updateComponent(":frmEntradaSalida:messages");
            MovilidadUtil.addErrorMessage("El vehículo escaneado no corresponde con la unidad funcional asiganda");
            return;
        }
        if (prgTc == null) {
            MovilidadUtil.addErrorMessage("Consultar Servbus para la asignació");
            return;
        }
        if (vehiculo == null) {
            MovilidadUtil.addErrorMessage("Consultar vehículo para la asignación");
            return;
        }
        if ((prgTc.getIdVehiculoTipo().getIdVehiculoTipo() == 1
                && vehiculo.getIdVehiculoTipo().getIdVehiculoTipo() == 2)) {
            MovilidadUtil.addErrorMessage("Tipología de vehículo incompatible.");
            prgTc = null;
            vehiculo = null;
            return;
        }
        int gopUF = vehiculo.getIdGopUnidadFuncional().getIdGopUnidadFuncional();
        int update = prgtcEJB.asignarBusToServbus(vehiculo.getIdVehiculo(),
                prgTc.getServbus(), Util.dateFormat(prgTc.getFecha()),
                user.getUsername(), gopUF);
        /**
         * verificar si se alteraron objetos en el método anterior
         */
        if (update > 0) {

            /**
             * Preparar y guardar nuevo objeto de PrgAsignacion
             */
            PrgAsignacion pa = new PrgAsignacion();
            pa.setCreado(MovilidadUtil.fechaCompletaHoy());
            pa.setFecha(prgTc.getFecha());
            pa.setIdVehiculo(vehiculo);
            pa.setServbus(prgTc.getServbus());
            pa.setUsername(user.getUsername());
            pa.setIdGopUnidadFuncional(vehiculo.getIdGopUnidadFuncional());
            prgAsigEJB.create(pa);
            MovilidadUtil.addSuccessMessage("Asignación aplicada Para el vehículo " + vehiculo.getCodigo());
            vehiculo = null;
            prgTc = null;
            servbusForAsignar = "";
        } else {
            MovilidadUtil.addErrorMessage("No se aplicó la asigación para el vehículo " + vehiculo.getCodigo());

        }
        MovilidadUtil.hideModal("AsignarDialog");
        prgTc = null;
        vehiculo = null;
    }

    public int getUf() {
        return uf;
    }

    public void setUf(int uf) {
        this.uf = uf;
    }

    public Util getUtil() {
        return util;
    }

    public void setUtil(Util util) {
        this.util = util;
    }

    public GopUnidadFuncionalSessionBean getUnidadFuncionalSessionBean() {
        return unidadFuncionalSessionBean;
    }

    public void setUnidadFuncionalSessionBean(GopUnidadFuncionalSessionBean unidadFuncionalSessionBean) {
        this.unidadFuncionalSessionBean = unidadFuncionalSessionBean;
    }

    public TecnicoPatioFacadeLocal getTecnicoPatioEJB() {
        return tecnicoPatioEJB;
    }

    public void setTecnicoPatioEJB(TecnicoPatioFacadeLocal tecnicoPatioEJB) {
        this.tecnicoPatioEJB = tecnicoPatioEJB;
    }

    public TecnicoPatioFacadeLocal getTecnicoPatioFacadeLocal() {
        return tecnicoPatioFacadeLocal;
    }

    public void setTecnicoPatioFacadeLocal(TecnicoPatioFacadeLocal tecnicoPatioFacadeLocal) {
        this.tecnicoPatioFacadeLocal = tecnicoPatioFacadeLocal;
    }

    public VehiculoFacadeLocal getVehiculoEjb() {
        return vehiculoEjb;
    }

    public void setVehiculoEjb(VehiculoFacadeLocal vehiculoEjb) {
        this.vehiculoEjb = vehiculoEjb;
    }

    public PrgTcFacadeLocal getPrgtcEJB() {
        return prgtcEJB;
    }

    public void setPrgtcEJB(PrgTcFacadeLocal prgtcEJB) {
        this.prgtcEJB = prgtcEJB;
    }

    public NovedadFacadeLocal getNovedadEJB() {
        return novedadEJB;
    }

    public void setNovedadEJB(NovedadFacadeLocal novedadEJB) {
        this.novedadEJB = novedadEJB;
    }

    public MyAppConfirmDepotEntryFacadeLocal getConfirmarMyAppEntryEJB() {
        return confirmarMyAppEntryEJB;
    }

    public void setConfirmarMyAppEntryEJB(MyAppConfirmDepotEntryFacadeLocal confirmarMyAppEntryEJB) {
        this.confirmarMyAppEntryEJB = confirmarMyAppEntryEJB;
    }

    public MyAppConfirmDepotExitFacadeLocal getConfirmarMyAppExitEJB() {
        return confirmarMyAppExitEJB;
    }

    public void setConfirmarMyAppExitEJB(MyAppConfirmDepotExitFacadeLocal confirmarMyAppExitEJB) {
        this.confirmarMyAppExitEJB = confirmarMyAppExitEJB;
    }

    public TecnicoPatioTipoTablaFacadeLocal getTecnicoPatioTipoTablaEJB() {
        return tecnicoPatioTipoTablaEJB;
    }

    public void setTecnicoPatioTipoTablaEJB(TecnicoPatioTipoTablaFacadeLocal tecnicoPatioTipoTablaEJB) {
        this.tecnicoPatioTipoTablaEJB = tecnicoPatioTipoTablaEJB;
    }

    public List<TecnicoPatio> getListTecnicoPatio() {
        return listTecnicoPatio;
    }

    public void setListTecnicoPatio(List<TecnicoPatio> listTecnicoPatio) {
        this.listTecnicoPatio = listTecnicoPatio;
    }

    public List<TecnicoPatio> getServicesON() {
        return servicesON;
    }

    public void setServicesON(List<TecnicoPatio> servicesON) {
        this.servicesON = servicesON;
    }

    public List<TecnicoPatio> getServicesOFF() {
        return servicesOFF;
    }

    public void setServicesOFF(List<TecnicoPatio> servicesOFF) {
        this.servicesOFF = servicesOFF;
    }

    public List<Vehiculo> getLstVehiculo() {
        return lstVehiculo;
    }

    public void setLstVehiculo(List<Vehiculo> lstVehiculo) {
        this.lstVehiculo = lstVehiculo;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public PrgTc getPrgTc() {
        return prgTc;
    }

    public void setPrgTc(PrgTc prgTc) {
        this.prgTc = prgTc;
    }

    public Novedad getNovedad() {
        return novedad;
    }

    public void setNovedad(Novedad novedad) {
        this.novedad = novedad;
    }

    public MyAppConfirmDepotEntry getConfirmarMyAppEntry() {
        return confirmarMyAppEntry;
    }

    public void setConfirmarMyAppEntry(MyAppConfirmDepotEntry confirmarMyAppEntry) {
        this.confirmarMyAppEntry = confirmarMyAppEntry;
    }

    public MyAppConfirmDepotExit getConfirmarMyAppExit() {
        return confirmarMyAppExit;
    }

    public void setConfirmarMyAppExit(MyAppConfirmDepotExit confirmarMyAppExit) {
        this.confirmarMyAppExit = confirmarMyAppExit;
    }

    public TecnicoPatioTipoTabla getTecnicoPatioTipoTabla() {
        return tecnicoPatioTipoTabla;
    }

    public void setTecnicoPatioTipoTabla(TecnicoPatioTipoTabla tecnicoPatioTipoTabla) {
        this.tecnicoPatioTipoTabla = tecnicoPatioTipoTabla;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public List<TpConteoDTO> getLstTpConteoDTO() {
        return lstTpConteoDTO;
    }

    public void setLstTpConteoDTO(List<TpConteoDTO> lstTpConteoDTO) {
        this.lstTpConteoDTO = lstTpConteoDTO;
    }

    public List<Vehiculo> getLstVehiculoDisponible() {
        return lstVehiculoDisponible;
    }

    public void setLstVehiculoDisponible(List<Vehiculo> lstVehiculoDisponible) {
        this.lstVehiculoDisponible = lstVehiculoDisponible;
    }

    public TecnicoPatioNovedadTipo getTecnicoPatioNovedadTipo() {
        return tecnicoPatioNovedadTipo;
    }

    public void setTecnicoPatioNovedadTipo(TecnicoPatioNovedadTipo tecnicoPatioNovedadTipo) {
        this.tecnicoPatioNovedadTipo = tecnicoPatioNovedadTipo;
    }

    public List<TecnicoPatioNovedadTipo> getLstNovedadTipo() {
        return lstNovedadTipo;
    }

    public void setLstNovedadTipo(List<TecnicoPatioNovedadTipo> lstNovedadTipo) {
        this.lstNovedadTipo = lstNovedadTipo;
    }

    public int getIdPrgTc() {
        return idPrgTc;
    }

    public void setIdPrgTc(int idPrgTc) {
        this.idPrgTc = idPrgTc;
    }

    public int getFlagBusqueda() {
        return flagBusqueda;
    }

    public void setFlagBusqueda(int flagBusqueda) {
        this.flagBusqueda = flagBusqueda;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getServbusForAsignar() {
        return servbusForAsignar;
    }

    public void setServbusForAsignar(String servbusForAsignar) {
        this.servbusForAsignar = servbusForAsignar;
    }

}
