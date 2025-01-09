/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GenericaJornadaExtraFacadeLocal;
import com.movilidad.ejb.GenericaJornadaFacadeLocal;
import com.movilidad.ejb.GenericaJornadaParamFacadeLocal;
import com.movilidad.ejb.GenericaJornadaTipoFacadeLocal;
import com.movilidad.ejb.GenericaJornadaTokenFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.GenericaJornada;
import com.movilidad.model.GenericaJornadaExtra;
import com.movilidad.model.GenericaJornadaParam;
import com.movilidad.model.GenericaJornadaTipo;
import com.movilidad.model.GenericaJornadaToken;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import com.aja.jornada.util.ConfigJornada;
import com.movilidad.utils.Util;
/**
 *
 * @author solucionesit
 */
@Named(value = "genJorTokenJSFMB")
@ViewScoped
public class GenericaJornadaTokenJSFManagedBean implements Serializable {

    @EJB
    private GenericaJornadaTokenFacadeLocal genJornadaTokenEJB;
    @EJB
    private GenericaJornadaFacadeLocal genJornadaEJB;
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;
    @EJB
    private GenericaJornadaParamFacadeLocal genJorParamEJB;
    @EJB
    private GenericaJornadaTipoFacadeLocal jornadaTEJB;
    @EJB
    private GenericaJornadaExtraFacadeLocal genJornadaExtraEjb;

    @Inject
    private CalcularMasivoJSFManagedBean calcularMasivoBean;
    private GenericaJornadaToken genericaJornadaToken;
    private String rol_user;
    private String msgs;
    private String severity;
    private ParamAreaUsr pau;
    private boolean b_maxHorasExtrasSemanaMes = false;
    private GenericaJornadaParam genJornadaParam;

    private double horasExtrasFin;
    private double horasExtrasInicio;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of GenericaJornadaTokenJSFManagedBean
     */
    public GenericaJornadaTokenJSFManagedBean() {
    }

    @PostConstruct
    public void init() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            rol_user = auth.getAuthority();
        }
        if (rol_user.equals("ROLE_DIRGEN")
                || rol_user.equals("ROLE_PROFGEN")
                || rol_user.equals("ROLE_PROFMTTO")
                || rol_user.equals("ROLE_ADMIN")) {
            msgs = "La solicitud ya fue procesada...";
            severity = "info";
            pau = paramAreaUserEJB.getByIdUser(user.getUsername());
            if (pau != null) {
                genJornadaParam = genJorParamEJB.getByIdArea(pau.getIdParamArea().getIdParamArea());
            } else {
                msgs = "No tiene un área configurada...";
                severity = "fatal";
            }

            b_maxHorasExtrasSemanaMes = genJornadaParam != null
                    && genJornadaParam.getHorasExtrasSemanales() >= 0
                    && genJornadaParam.getHorasExtrasMensuales() >= 0;

            Map<String, String> params = FacesContext.getCurrentInstance().
                    getExternalContext().getRequestParameterMap();
            String get = params.get("pin");
            if (get != null) {
                genericaJornadaToken = genJornadaTokenEJB.findByToken(get);
                if (genericaJornadaToken != null) {
                    if (!(genericaJornadaToken.getIdGenericaJornada().getIdParamArea().getIdParamArea().equals(pau.getIdParamArea().getIdParamArea()))) {
                        genericaJornadaToken = null;
                        msgs = "No puede autorizar esta jornada...";
                        severity = "error";
                    }
                }
            }
        } else {
            msgs = "Actualmente no tiene acceso a este módulo...";
            severity = "fatal";
        }
    }

    public boolean aprobarHorasFeriadas(GenericaJornada gj) {
        int totalHorasExtrasNuevas = MovilidadUtil.toSecs(gj.getFestivoExtraDiurno())
                + MovilidadUtil.toSecs(gj.getFestivoExtraNocturno());
        if (totalHorasExtrasNuevas > 0) {
            return true;
        }
        return false;

    }

    private boolean validarHorasPositivas(GenericaJornada gj) {
        if (MovilidadUtil.toSecs(gj.getDiurnas()) < 0) {
            return true;
        }
        if (MovilidadUtil.toSecs(gj.getNocturnas()) < 0) {
            return true;
        }
        if (MovilidadUtil.toSecs(gj.getExtraDiurna()) < 0) {
            return true;
        }
        if (MovilidadUtil.toSecs(gj.getExtraNocturna()) < 0) {
            return true;
        }
        if (MovilidadUtil.toSecs(gj.getFestivoDiurno()) < 0) {
            return true;
        }
        if (MovilidadUtil.toSecs(gj.getFestivoNocturno()) < 0) {
            return true;
        }
        if (MovilidadUtil.toSecs(gj.getFestivoExtraDiurno()) < 0) {
            return true;
        }
        return MovilidadUtil.toSecs(gj.getFestivoExtraNocturno()) < 0;
    }

    public void gestionToken() {
        genericaJornadaToken.setActivo(1);
        genericaJornadaToken.setModificado(MovilidadUtil.fechaCompletaHoy());
        genJornadaTokenEJB.edit(genericaJornadaToken);
    }

    public int horasExtrasByJornada(GenericaJornada gj) {
        int extrasDiurna = MovilidadUtil.toSecs(gj.getExtraDiurna());
        int extrasNocturna = MovilidadUtil.toSecs(gj.getExtraNocturna());
        int festivoExtrasDiurna = MovilidadUtil.toSecs(gj.getFestivoExtraDiurno());
        int festivoExtrasNocturna = MovilidadUtil.toSecs(gj.getFestivoExtraNocturno());
        int dominicalExtrasNocturna = MovilidadUtil.toSecs(gj.getDominicalCompNocturnaExtra());
        int dominicalExtrasdiurna = MovilidadUtil.toSecs(gj.getDominicalCompDiurnaExtra());
        return extrasDiurna + extrasNocturna + festivoExtrasDiurna
                + festivoExtrasNocturna + dominicalExtrasNocturna + dominicalExtrasdiurna;
    }

    public GenericaJornadaExtra calcularGenericaJornadaExtra(GenericaJornada gj) {
        double maxHorasExtrasSemana = genJornadaParam.getHorasExtrasSemanales();
        double maxHorasExtrasMes = genJornadaParam.getHorasExtrasMensuales();
        GenericaJornadaExtra genJornadaExtra = genJornadaExtraEjb.getByEmpleadoAndFecha(gj.getIdEmpleado().getIdEmpleado(), gj.getFecha());
        if (genJornadaExtra == null) {
            Calendar primerDiaMes = Calendar.getInstance();
            Calendar ultimoDiaMes = Calendar.getInstance();
            primerDiaMes.setTime(gj.getFecha());
            ultimoDiaMes.setTime(gj.getFecha());

            primerDiaMes.set(Calendar.DAY_OF_MONTH, 1);
            ultimoDiaMes.set(Calendar.DAY_OF_MONTH, ultimoDiaMes.getActualMaximum(Calendar.DAY_OF_MONTH));

            genJornadaExtra = new GenericaJornadaExtra();
            genJornadaExtra.setIdEmpleado(gj.getIdEmpleado());
            genJornadaExtra.setEstadoReg(0);
            genJornadaExtra.setUsername(user.getUsername());
            genJornadaExtra.setDesde(primerDiaMes.getTime());
            genJornadaExtra.setHasta(ultimoDiaMes.getTime());
            genJornadaExtra.setCreado(MovilidadUtil.fechaCompletaHoy());
            genJornadaExtra.setModificado(MovilidadUtil.fechaCompletaHoy());
            genJornadaExtra.setIdParamArea(pau.getIdParamArea());
            genJornadaExtra.setSemana1(0);
            genJornadaExtra.setSemana2(0);
            genJornadaExtra.setSemana3(0);
            genJornadaExtra.setSemana4(0);
            genJornadaExtra.setSemana5(0);
            genJornadaExtra.setTotal(0);
        }
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(gj.getFecha());
        int numeroSemana = fecha.get(Calendar.WEEK_OF_MONTH);
        horasExtrasInicio = horasExtrasInicio / 3600;
        horasExtrasFin = horasExtrasFin / 3600;
        if (horasExtrasInicio > horasExtrasFin) {
            double dif = horasExtrasInicio - horasExtrasFin;
            if (numeroSemana == 1) {
                genJornadaExtra.setSemana1(genJornadaExtra.getSemana1() - dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - dif);
                return genJornadaExtra;
            }
            if (numeroSemana == 2) {
                genJornadaExtra.setSemana2(genJornadaExtra.getSemana2() - dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - dif);
                return genJornadaExtra;
            }
            if (numeroSemana == 3) {
                genJornadaExtra.setSemana3(genJornadaExtra.getSemana3() - dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - dif);
                return genJornadaExtra;
            }
            if (numeroSemana == 4) {
                genJornadaExtra.setSemana4(genJornadaExtra.getSemana4() - dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - dif);
                return genJornadaExtra;
            }
            if (numeroSemana == 5) {
                genJornadaExtra.setSemana5(genJornadaExtra.getSemana5() - dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - dif);
                return genJornadaExtra;
            }
        } else if (horasExtrasInicio < horasExtrasFin) {
            double dif = horasExtrasFin - horasExtrasInicio;
            if (numeroSemana == 1) {
                genJornadaExtra.setSemana1(genJornadaExtra.getSemana1() + dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() + dif);
                if (genJornadaExtra.getSemana1() > maxHorasExtrasSemana) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana1() * 3600)));
                    return null;
                }
                if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                    return null;
                }
                return genJornadaExtra;
            }
            if (numeroSemana == 2) {
                genJornadaExtra.setSemana2(genJornadaExtra.getSemana2() + dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() + dif);
                if (genJornadaExtra.getSemana2() > maxHorasExtrasSemana) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana2() * 3600)));
                    return null;
                }
                if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                    return null;
                }
                return genJornadaExtra;
            }
            if (numeroSemana == 3) {
                genJornadaExtra.setSemana3(genJornadaExtra.getSemana3() + dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() + dif);
                if (genJornadaExtra.getSemana3() > maxHorasExtrasSemana) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana3() * 3600)));
                    return null;
                }
                if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                    return null;
                }
                return genJornadaExtra;
            }
            if (numeroSemana == 4) {
                genJornadaExtra.setSemana4(genJornadaExtra.getSemana4() + dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() + dif);
                if (genJornadaExtra.getSemana4() > maxHorasExtrasSemana) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana4() * 3600)));
                    return null;
                }
                if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                    return null;
                }
                return genJornadaExtra;
            }
            if (numeroSemana == 5) {
                genJornadaExtra.setSemana5(genJornadaExtra.getSemana5() + dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() + dif);
                if (genJornadaExtra.getSemana5() > maxHorasExtrasSemana) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana5() * 3600)));
                    return null;
                }
                if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                    return null;
                }
                return genJornadaExtra;
            }
        }
        return genJornadaExtra;
    }

    @Transactional
    public void autorizar(int op) throws ParseException {

        GenericaJornada genJornada = genericaJornadaToken.getIdGenericaJornada();
        horasExtrasInicio = horasExtrasByJornada(genJornada);
        if (!rol_user.equals("ROLE_DIRGEN")) {
            int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(genJornada.getFecha(), 1), MovilidadUtil.fechaHoy(), false);

            if (genJornadaParam != null
                    && genJornadaParam.getCtrlAutorizarExtensionJornada() == 0
                    && validacionDia == 0) {
                MovilidadUtil.addErrorMessage("No es posible modificar jornada, sobrepasa 24hrs despues de su ejecución.");
                return;
            }
        }
        try {
            if (genJornada != null) {
                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                String uri = request.getRequestURI();
                String url = request.getRequestURL().toString();
                String ctxPath = request.getContextPath();
                url = url.replaceFirst(uri, "");
                url = url + ctxPath;
                GenericaJornadaTipo gjt = jornadaTEJB.findByHIniAndHFin(genJornada.getRealTimeOrigin(), genJornada.getRealTimeDestiny(), pau.getIdParamArea().getIdParamArea());
                genJornada.setIdGenericaJornadaTipo(gjt);
                if (genJornada.getPrgModificada() == 1) {
                    GenericaJornadaExtra genJorExtra = null;

                    if (op == 1) { // (1) uno para autorizar, (0) cero para no autorizar
                        boolean b_jornadaExtra = false;
                        if ((MovilidadUtil.toSecs(genJornada.getRealTimeDestiny()) - MovilidadUtil.toSecs(genJornada.getTimeOrigin()))
                                > MovilidadUtil.toSecs(ConfigJornada.getTotal_Hrs_laborales())) {

                            if (genJornada.getIdGenericaJornadaTipo() != null) {
                                if (!((MovilidadUtil.toSecs(genJornada.getRealTimeDestiny())
                                        - MovilidadUtil.toSecs(genJornada.getRealTimeOrigin())
                                        - MovilidadUtil.toSecs(ConfigJornada.getTotal_Hrs_laborales())
                                        - MovilidadUtil.toSecs(genJornada.getIdGenericaJornadaTipo().getDescanso()))
                                        <= MovilidadUtil.toSecs(ConfigJornada.getMax_hrs_extra_dia()))) {
                                    MovilidadUtil.addErrorMessage("Solo se permiten hasta 2 horas extras diarias Empleado: "
                                            + genJornada.getIdEmpleado().getIdentificacion()
                                            + "-"
                                            + genJornada.getIdEmpleado().getNombres()
                                            + " "
                                            + genJornada.getIdEmpleado().getApellidos());
                                    return;

                                } else {
                                    b_jornadaExtra = true;
                                }
                            } else {
                                if (!((MovilidadUtil.toSecs(genJornada.getRealTimeDestiny())
                                        - MovilidadUtil.toSecs(genJornada.getRealTimeOrigin())
                                        - MovilidadUtil.toSecs(ConfigJornada.getTotal_Hrs_laborales())) <= MovilidadUtil.toSecs(ConfigJornada.getMax_hrs_extra_dia()))) {
                                    MovilidadUtil.addErrorMessage("Solo se permiten hasta 2 horas extras diarias Empleado: "
                                            + genJornada.getIdEmpleado().getIdentificacion()
                                            + "-"
                                            + genJornada.getIdEmpleado().getNombres()
                                            + " "
                                            + genJornada.getIdEmpleado().getApellidos());
                                    return;
                                } else {
                                    b_jornadaExtra = true;
                                }
                            }
                        }
                        if (b_maxHorasExtrasSemanaMes) {
                            horasExtrasFin = MovilidadUtil.toSecs(horaExtras(genJornada.getRealTimeOrigin(), genJornada.getRealTimeDestiny(), gjt));
                            genJorExtra = calcularGenericaJornadaExtra(genJornada);
                            if (genJorExtra == null) {
                                return;
                            }
                        }
                        genJornada.setAutorizado(1);
                        genJornada.setUserAutorizado(user.getUsername());
                        genJornada.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
                        boolean b_descanso = false;
                        if (genJornada.getIdGenericaJornadaTipo() != null) {
                            if (MovilidadUtil.toSecs(genJornada.getIdGenericaJornadaTipo().getDescanso()) > MovilidadUtil.toSecs("00:00:00")) {
                                b_descanso = true;
                            }
                        } else {
                            if (gjt != null) {
                                genJornada.setIdGenericaJornadaTipo(gjt);
                                if (MovilidadUtil.toSecs(gjt.getDescanso()) > MovilidadUtil.toSecs("00:00:00")) {
                                    b_descanso = true;
                                }
                            }
                        }
                        boolean flagTipoCalculo = genJornada.getIdGenericaJornadaTipo() != null && genJornada.getIdGenericaJornadaTipo().getTipoCalculo() == 1;
                        calcularMasivoBean.cargarMapParamFeriado();
                        if (flagTipoCalculo) {
                            calcularMasivoBean.cargarCalcularDatoPorPartes(genJornada);
                        } else {
                            calcularMasivoBean.cargarCalcularDato(genJornada, 2);
                        }
                        if (validarHorasPositivas(genJornada)) {
                            MovilidadUtil.addErrorMessage("Error al calcular jornada");
                            return;
                        }
                        if (!flagTipoCalculo && (b_descanso && b_jornadaExtra)) {
                            int festivaExtraNocturna = MovilidadUtil.toSecs(genJornada.getFestivoExtraNocturno());
                            int festivaExtraDiurna = MovilidadUtil.toSecs(genJornada.getFestivoExtraDiurno());
                            int extraNocturna = MovilidadUtil.toSecs(genJornada.getExtraNocturna());
                            int extraDiurna = MovilidadUtil.toSecs(genJornada.getExtraDiurna());
                            int descanso_ = MovilidadUtil.toSecs(genJornada.getIdGenericaJornadaTipo().getDescanso());

                            if (festivaExtraNocturna
                                    > 0) {
                                if (festivaExtraNocturna > descanso_) {
                                    festivaExtraNocturna = festivaExtraNocturna - descanso_;
                                    descanso_ = 0;
                                } else if (descanso_ > festivaExtraNocturna) {
                                    descanso_ = descanso_ - festivaExtraNocturna;
                                    festivaExtraNocturna = 0;
                                } else if (festivaExtraNocturna == descanso_) {
                                    festivaExtraNocturna = festivaExtraNocturna - descanso_;
                                    descanso_ = 0;
                                }
                            }
                            if (descanso_
                                    > 0) {
                                if (festivaExtraDiurna > 0) {
                                    if (festivaExtraDiurna > descanso_) {
                                        festivaExtraDiurna = festivaExtraDiurna - descanso_;
                                        descanso_ = 0;
                                    } else if (descanso_ > festivaExtraDiurna) {
                                        descanso_ = descanso_ - festivaExtraDiurna;
                                        festivaExtraDiurna = 0;
                                    } else if (festivaExtraDiurna == descanso_) {
                                        festivaExtraDiurna = festivaExtraDiurna - descanso_;
                                        descanso_ = 0;
                                    }
                                }
                            }
                            if (descanso_
                                    > 0) {
                                if (extraNocturna > 0) {
                                    if (extraNocturna > descanso_) {
                                        extraNocturna = extraNocturna - descanso_;
                                        descanso_ = 0;
                                    } else if (descanso_ > extraNocturna) {
                                        descanso_ = descanso_ - extraNocturna;
                                        extraNocturna = 0;
                                    } else if (extraNocturna == descanso_) {
                                        extraNocturna = extraNocturna - descanso_;
                                        descanso_ = 0;
                                    }
                                }
                            }
                            if (descanso_
                                    > 0) {
                                if (extraDiurna > 0) {
                                    if (extraDiurna > descanso_) {
                                        extraDiurna = extraDiurna - descanso_;
                                        descanso_ = 0;
                                    } else if (descanso_ > extraDiurna) {
                                        descanso_ = descanso_ - extraDiurna;
                                        extraDiurna = 0;
                                    } else if (extraDiurna == descanso_) {
                                        extraDiurna = extraDiurna - descanso_;
                                        descanso_ = 0;
                                    }
                                }
                            }

                            genJornada.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(festivaExtraNocturna));
                            genJornada.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(festivaExtraDiurna));
                            genJornada.setExtraNocturna(MovilidadUtil.toTimeSec(extraNocturna));
                            genJornada.setExtraDiurna(MovilidadUtil.toTimeSec(extraDiurna));
                        }

                        if (!rol_user.equals("ROLE_DIRGEN")) {
                            if (aprobarHorasFeriadas(genJornada)) {
                                int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(genJornada.getFecha(), 1), MovilidadUtil.fechaHoy(), false);
                                if (genJornadaParam != null
                                        && genJornadaParam.getCtrlAprobarExtrasFeriadas() == 0
                                        && validacionDia == 0) {
                                    MovilidadUtil.addErrorMessage("No es posible modificar jornada, sobrepasa 24hrs despues de su ejecución.");
                                    return;
                                }
                            }
                        }
                        genJornada.setDominicalCompDiurnas(null);
                        genJornada.setDominicalCompNocturnas(null);
                        genJornada.setDominicalCompDiurnaExtra(null);
                        genJornada.setDominicalCompNocturnaExtra(null);
                        genJornadaEJB.edit(genJornada);
                        if (genJorExtra != null) {
                            if (genJorExtra.getIdGenericaJornadaExtra() != null) {
                                genJornadaExtraEjb.edit(genJorExtra);
                            } else {
                                genJornadaExtraEjb.create(genJorExtra);
                            }
                        }
                        calcularMasivoBean.recalcularJornada(genJornada);
                        MovilidadUtil.addSuccessMessage("Registro autorizado correctamente1");
                        gestionToken();
                        genericaJornadaToken = null;
                        return;

                    }
                    if (op == 0) {
                        if (!rol_user.equals("ROLE_DIRGEN")) {
                            if (aprobarHorasFeriadas(genJornada)) {
                                int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(genJornada.getFecha(), 1), MovilidadUtil.fechaHoy(), false);
                                if (genJornadaParam != null
                                        && genJornadaParam.getCtrlAprobarExtrasFeriadas() == 0
                                        && validacionDia == 0) {
                                    MovilidadUtil.addErrorMessage("No es posible modificar jornada, sobrepasa 24hrs despues de su ejecución.");
                                    return;
                                }
                            }
                        }
                        genJornada.setUserAutorizado(user.getUsername());
                        genJornada.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
                        genJornada.setAutorizado(0);
                        genJornada.setDominicalCompDiurnas(null);
                        genJornada.setDominicalCompNocturnas(null);
                        genJornada.setDominicalCompDiurnaExtra(null);
                        genJornada.setDominicalCompNocturnaExtra(null);
                        genJornadaEJB.edit(genJornada);
                        if (b_maxHorasExtrasSemanaMes) {
                            if (genJornada.getDiurnas() != null) {
                                double horasExtras = horasExtrasByJornada(genJornada);
                                if (horasExtras > 0) {
                                    calcularGenJorExtraBM(genJornada, horasExtras);
                                }
                            }
                        }
                        MovilidadUtil.addSuccessMessage("Registro NO autorizado correctamente");
                        calcularMasivoBean.recalcularJornada(genJornada);
                        gestionToken();
                        genericaJornadaToken = null;
                        return;
                    }

                } else {
                    MovilidadUtil.addErrorMessage("No puede realizar esta acción debido a que no está modificada la programación");
                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en autorizar");
        }
    }

    public void calcularGenJorExtraBM(GenericaJornada gj, double horasExtras) {
        GenericaJornadaExtra genJornadaExtra = genJornadaExtraEjb.getByEmpleadoAndFecha(gj.getIdEmpleado().getIdEmpleado(), gj.getFecha());
        horasExtras = horasExtras / 3600;
        if (genJornadaExtra == null) {
            Calendar primerDiaMes = Calendar.getInstance();
            Calendar ultimoDiaMes = Calendar.getInstance();
            primerDiaMes.setTime(gj.getFecha());
            ultimoDiaMes.setTime(gj.getFecha());

            primerDiaMes.set(Calendar.DAY_OF_MONTH, 1);
            ultimoDiaMes.set(Calendar.DAY_OF_MONTH, ultimoDiaMes.getActualMaximum(Calendar.DAY_OF_MONTH));

            genJornadaExtra = new GenericaJornadaExtra();
            genJornadaExtra.setIdEmpleado(gj.getIdEmpleado());
            genJornadaExtra.setEstadoReg(0);
            genJornadaExtra.setUsername(user.getUsername());
            genJornadaExtra.setDesde(primerDiaMes.getTime());
            genJornadaExtra.setHasta(ultimoDiaMes.getTime());
            genJornadaExtra.setCreado(MovilidadUtil.fechaCompletaHoy());
            genJornadaExtra.setModificado(MovilidadUtil.fechaCompletaHoy());
            genJornadaExtra.setIdParamArea(pau.getIdParamArea());
            genJornadaExtra.setSemana1(0);
            genJornadaExtra.setSemana2(0);
            genJornadaExtra.setSemana3(0);
            genJornadaExtra.setSemana4(0);
            genJornadaExtra.setSemana5(0);
            genJornadaExtra.setTotal(0);
            genJornadaExtraEjb.create(genJornadaExtra);
        } else {
            Calendar fecha = Calendar.getInstance();
            fecha.setTime(gj.getFecha());
            int numeroSemana = fecha.get(Calendar.WEEK_OF_MONTH);
            if (numeroSemana == 1) {
                genJornadaExtra.setSemana1(genJornadaExtra.getSemana1() - horasExtras);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - horasExtras);
            }
            if (numeroSemana == 2) {
                genJornadaExtra.setSemana2(genJornadaExtra.getSemana2() - horasExtras);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - horasExtras);
            }
            if (numeroSemana == 3) {
                genJornadaExtra.setSemana3(genJornadaExtra.getSemana3() - horasExtras);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - horasExtras);
            }
            if (numeroSemana == 4) {
                genJornadaExtra.setSemana4(genJornadaExtra.getSemana4() - horasExtras);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - horasExtras);
            }
            if (numeroSemana == 5) {
                genJornadaExtra.setSemana5(genJornadaExtra.getSemana5() - horasExtras);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - horasExtras);
            }
            genJornadaExtraEjb.edit(genJornadaExtra);
        }
    }

    public GenericaJornadaExtra calcularGenJorExtraAlCrearJornada(GenericaJornada gj, double horasExtras) {
        double maxHorasExtrasSemana = genJornadaParam.getHorasExtrasSemanales();
        double maxHorasExtrasMes = genJornadaParam.getHorasExtrasMensuales();
        horasExtras = horasExtras / 3600;
        GenericaJornadaExtra genJornadaExtra = genJornadaExtraEjb.getByEmpleadoAndFecha(gj.getIdEmpleado().getIdEmpleado(), gj.getFecha());
        if (genJornadaExtra == null) {
            Calendar primerDiaMes = Calendar.getInstance();
            Calendar ultimoDiaMes = Calendar.getInstance();
            primerDiaMes.setTime(gj.getFecha());
            ultimoDiaMes.setTime(gj.getFecha());

            primerDiaMes.set(Calendar.DAY_OF_MONTH, 1);
            ultimoDiaMes.set(Calendar.DAY_OF_MONTH, ultimoDiaMes.getActualMaximum(Calendar.DAY_OF_MONTH));

            genJornadaExtra = new GenericaJornadaExtra();
            genJornadaExtra.setIdEmpleado(gj.getIdEmpleado());
            genJornadaExtra.setEstadoReg(0);
            genJornadaExtra.setUsername(user.getUsername());
            genJornadaExtra.setDesde(primerDiaMes.getTime());
            genJornadaExtra.setHasta(ultimoDiaMes.getTime());
            genJornadaExtra.setCreado(MovilidadUtil.fechaCompletaHoy());
            genJornadaExtra.setModificado(MovilidadUtil.fechaCompletaHoy());
            genJornadaExtra.setIdParamArea(pau.getIdParamArea());
            genJornadaExtra.setSemana1(0);
            genJornadaExtra.setSemana2(0);
            genJornadaExtra.setSemana3(0);
            genJornadaExtra.setSemana4(0);
            genJornadaExtra.setSemana5(0);
            genJornadaExtra.setTotal(0);
        }
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(gj.getFecha());
        int numeroSemana = fecha.get(Calendar.WEEK_OF_MONTH);
        if (numeroSemana == 1) {
            genJornadaExtra.setSemana1(genJornadaExtra.getSemana1() + horasExtras);
            genJornadaExtra.setTotal(genJornadaExtra.getTotal() + horasExtras);
            if (genJornadaExtra.getSemana1() > maxHorasExtrasSemana) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana1() * 3600)));
                return null;
            }
            if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                return null;
            }
            return genJornadaExtra;
        }
        if (numeroSemana == 2) {
            genJornadaExtra.setSemana2(genJornadaExtra.getSemana2() + horasExtras);
            genJornadaExtra.setTotal(genJornadaExtra.getTotal() + horasExtras);
            if (genJornadaExtra.getSemana2() > maxHorasExtrasSemana) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana2() * 3600)));
                return null;
            }
            if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                return null;
            }
            return genJornadaExtra;
        }
        if (numeroSemana == 3) {
            genJornadaExtra.setSemana3(genJornadaExtra.getSemana3() + horasExtras);
            genJornadaExtra.setTotal(genJornadaExtra.getTotal() + horasExtras);
            if (genJornadaExtra.getSemana3() > maxHorasExtrasSemana) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana3() * 3600)));
                return null;
            }
            if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                return null;
            }
            return genJornadaExtra;
        }
        if (numeroSemana == 4) {
            genJornadaExtra.setSemana4(genJornadaExtra.getSemana4() + horasExtras);
            genJornadaExtra.setTotal(genJornadaExtra.getTotal() + horasExtras);
            if (genJornadaExtra.getSemana4() > maxHorasExtrasSemana) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana4() * 3600)));
                return null;
            }
            if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                return null;
            }
            return genJornadaExtra;
        }
        if (numeroSemana == 5) {
            genJornadaExtra.setSemana5(genJornadaExtra.getSemana5() + horasExtras);
            genJornadaExtra.setTotal(genJornadaExtra.getTotal() + horasExtras);
            if (genJornadaExtra.getSemana5() > maxHorasExtrasSemana) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana5() * 3600)));
                return null;
            }
            if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                return null;
            }
            return genJornadaExtra;
        }
        return genJornadaExtra;
    }

    public String horaExtras(String hIni, String hFin, GenericaJornadaTipo gjt) {
        int extras = (MovilidadUtil.toSecs(hFin) - MovilidadUtil.toSecs(hIni)) - MovilidadUtil.toSecs(ConfigJornada.getTotal_Hrs_laborales());
        if (extras > 0) {
            if (gjt != null) {
                if (extras > MovilidadUtil.toSecs(gjt.getDescanso())) {
                    return MovilidadUtil.toTimeSec(extras - MovilidadUtil.toSecs(gjt.getDescanso()));
                } else {
                    return "00:00:00";
                }
            } else {
                return MovilidadUtil.toTimeSec(extras);
            }
        } else {
            return "00:00:00";
        }
    }

    public GenericaJornadaToken getGenericaJornadaToken() {
        return genericaJornadaToken;
    }

    public void setGenericaJornadaToken(GenericaJornadaToken genericaJornadaToken) {
        this.genericaJornadaToken = genericaJornadaToken;
    }

    public String getMsgs() {
        return msgs;
    }

    public void setMsgs(String msgs) {
        this.msgs = msgs;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

}
