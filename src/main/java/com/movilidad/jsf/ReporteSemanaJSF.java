/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.dto.ReporteSemanaActualDTO;
import com.movilidad.ejb.NovedadPrgTcFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.ejb.ReporteSemanaMotivoFacadeLocal;
import com.movilidad.ejb.ReporteSemanaMotivoPrgFacadeLocal;
import com.movilidad.model.NovedadPrgTc;
import com.movilidad.model.ReporteSemanaActualMotivo;
import com.movilidad.model.ReporteSemanaMotivoPrg;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Julian Arévalo
 */
@Named(value = "semanaActualBean")
@ViewScoped
public class ReporteSemanaJSF implements Serializable {

    public ReporteSemanaJSF() {
    }
    @EJB
    private PrgTcFacadeLocal prgTcFacadeLocal;
    @EJB
    private ReporteSemanaMotivoFacadeLocal ReporteSemanaMotivoFacadeLocal;
    @EJB
    private NovedadPrgTcFacadeLocal novedadPrgTcFacadeLocal;
    @EJB
    private ReporteSemanaMotivoPrgFacadeLocal reporteSemanaMotivoPrgFacadeLocal;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    private Date desde = MovilidadUtil.fechaCompletaHoy();
    private Date hasta = MovilidadUtil.fechaCompletaHoy();
    private List<ReporteSemanaActualDTO> listDetalle;
    private List<NovedadPrgTc> listAusentismo;
    private List<ReporteSemanaActualMotivo> listMotivos;
    private List<ReporteSemanaMotivoPrg> listMotivosPrgTc;
    private ReporteSemanaActualMotivo motivo;
    private Integer idMotivo;
    private ReporteSemanaActualDTO selectedDto;
    private ReporteSemanaMotivoPrg motivoPrg;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        listMotivos = ReporteSemanaMotivoFacadeLocal.getAllActivo();
        motivo = new ReporteSemanaActualMotivo();
        motivoPrg = new ReporteSemanaMotivoPrg();
    }

    public void consultar() {
        listDetalle = prgTcFacadeLocal.findReporteSemanaActual(desde, hasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        listAusentismo = novedadPrgTcFacadeLocal.findNovedadAusentismoPrgTcByFechas(desde, hasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        listMotivosPrgTc = reporteSemanaMotivoPrgFacadeLocal.getActivo();
        for (ReporteSemanaActualDTO rsa : listDetalle) {
            for (NovedadPrgTc nov : listAusentismo) {
                if (Objects.equals(nov.getIdPrgTc().getIdPrgTc(), rsa.getIdPrgTc())) {
                    if (rsa.getNovedad() == null) {
                        rsa.setNovedad(nov);
                    } else {
                        rsa.setNovedadFinal(nov);
                    }
                }
            }
            for (ReporteSemanaMotivoPrg motPrg : listMotivosPrgTc) {
                if (Objects.equals(rsa.getIdPrgTc(), motPrg.getIdPrgTc().getIdPrgTc())) {
                    rsa.setIdMotivo(motPrg.getIdMotivo());
                    rsa.setMotivoPrg(motPrg);
                }
            }
        }
    }

    public void consultarMotivos() {
        listMotivos = ReporteSemanaMotivoFacadeLocal.getAllActivo();
    }

    public String gestion(ReporteSemanaActualDTO obj) {
        if (obj != null) {
            if ("Eliminado".equals(obj.getEstadoOperacion())) {
                return "Eliminado";
            } else if (obj.getNovedad() == null) {
                if (obj.getCodigoTm() != null) {
                    return "Cumpliendo";
                }
                if (obj.getCodigoTm() == null) {
                    return "Pendiente por cubrir";
                }
            } else {
                if (obj.getNovedad().getIdNovedad() != null) {
                    if (obj.getCodigoTm() == null) {
                        return "Pendiente por cubrir";
                    }
                    if (obj.getNovedad().getIdNovedad().getIdEmpleado().getCodigoTm() == null) {
                        return "Pendiente por cubrir";
                    }
                    if (obj.getNovedad().getIdNovedad().getIdEmpleado().getCodigoTm() != null) {
                        return "Cubierto";
                    }
                    if (obj.getNovedad().getIdNovedad().getIdEmpleado().getCodigoTm() == null && obj.getCodigoTm() != null) {
                        return "Cumpliendo";
                    }
                }

                return "Otro";
            }
        }
        return "Otro";
    }

    public void activarDesactivar(ReporteSemanaActualMotivo obj, int opc) {
        if (opc == 0) {
            obj.setActivo(0);
        } else {
            obj.setActivo(1);
        }
        obj.setModificado(MovilidadUtil.fechaCompletaHoy());
        ReporteSemanaMotivoFacadeLocal.edit(obj);
        MovilidadUtil.addSuccessMessage("Acción completada exitosamente.");
        consultarMotivos();
        PrimeFaces.current().ajax().update(":frm_principal:dt_motivo");
    }

    public void guardar() {
        guardarTransactional(motivo);
        consultarMotivos();
        PrimeFaces.current().ajax().update(":frm_principal:dt_motivo");
    }

    @Transactional
    public void guardarTransactional(ReporteSemanaActualMotivo obj) {
        motivo = new ReporteSemanaActualMotivo();
        motivo.setActivo(1);
        motivo.setMotivo(obj.getMotivo());
        motivo.setUsername(user.getUsername());
        motivo.setEstadoReg(0);
        motivo.setCreado(MovilidadUtil.fechaCompletaHoy());
        ReporteSemanaMotivoFacadeLocal.create(motivo);
        MovilidadUtil.addSuccessMessage("Se guardó el registro exitosamente.");
        MovilidadUtil.hideModal("wv_create_dlg");
        consultar();
        limpiarForm();
    }

    public void limpiarForm() {
        motivo = new ReporteSemanaActualMotivo();
        motivoPrg = new ReporteSemanaMotivoPrg();
        idMotivo = 0;
        selectedDto = null;
    }

    @Transactional
    public void guardarMotivo() {
        if (selectedDto != null && idMotivo > 0) {
            motivoPrg = new ReporteSemanaMotivoPrg();
            motivoPrg.setIdMotivo(ReporteSemanaMotivoFacadeLocal.find(idMotivo));
            motivoPrg.setIdPrgTc(prgTcFacadeLocal.find(selectedDto.getIdPrgTc()));
            motivoPrg.setUsername(user.getUsername());
            motivoPrg.setEstadoReg(0);
            motivoPrg.setCreado(MovilidadUtil.fechaCompletaHoy());
            ReporteSemanaMotivoPrg motivoPrgExist;
            motivoPrgExist = reporteSemanaMotivoPrgFacadeLocal.findByIdPrgTc(motivoPrg.getIdPrgTc().getIdPrgTc());
            if (motivoPrgExist == null) {
                reporteSemanaMotivoPrgFacadeLocal.create(motivoPrg);
                MovilidadUtil.addSuccessMessage("Se guardó el registro exitosamente.");
                motivoPrg.setIdMotivoPrg(ReporteSemanaMotivoFacadeLocal.find(idMotivo).getIdMotivo());
            } else {
                motivoPrg.setIdMotivoPrg(motivoPrgExist.getIdMotivoPrg());
                reporteSemanaMotivoPrgFacadeLocal.edit(motivoPrg);
                MovilidadUtil.addSuccessMessage("Se actualizó el registro exitosamente.");
            }
            listDetalle = new ArrayList<>();
            consultar();
            PrimeFaces.current().executeScript("PF('wlVdb_semana_actual').update();");
            PrimeFaces.current().ajax().update(":form_rpt_semana_actual:db_semana_actual");
            //PrimeFaces.current().ajax().update(":form_rpt_semana_actual:db_semana_actual:column_gestion");
        } else {
            MovilidadUtil.addErrorMessage("Ocurrió un error al agregar el motivo.");
        }
        MovilidadUtil.hideModal("wv_create_dlg");
        limpiarForm();
    }

    public List<Object> GestionFilter() {
        List<Object> aux_list = new ArrayList<>();
        if (listDetalle != null) {
            for (ReporteSemanaActualDTO d : listDetalle) {
                aux_list.add(gestion(d));
            }
            aux_list = aux_list.stream().distinct().collect(Collectors.toList());
        }
        return aux_list;
    }

    public List<Object> AusentismoFilter() {
        List<Object> aux_list = new ArrayList<>();
        if (listDetalle != null) {
            for (ReporteSemanaActualDTO d : listDetalle) {
                if (d.getNovedad() != null) {
                    if (d.getNovedad().getIdNovedad() != null) {
                        aux_list.add(d.getNovedad().getIdNovedad().getIdNovedadTipoDetalle().getDescripcionTipoNovedad());
                    }
                }
            }
            aux_list = aux_list.stream().distinct().collect(Collectors.toList());
        }
        return aux_list;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public List<ReporteSemanaActualDTO> getListDetalle() {
        return listDetalle;
    }

    public void setListDetalle(List<ReporteSemanaActualDTO> listDetalle) {
        this.listDetalle = listDetalle;
    }

    public List<ReporteSemanaActualMotivo> getListMotivos() {
        return listMotivos;
    }

    public void setListMotivos(List<ReporteSemanaActualMotivo> listMotivos) {
        this.listMotivos = listMotivos;
    }

    public ReporteSemanaActualMotivo getMotivo() {
        return motivo;
    }

    public void setMotivo(ReporteSemanaActualMotivo motivo) {
        this.motivo = motivo;
    }

    public Integer getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(Integer idMotivo) {
        this.idMotivo = idMotivo;
    }

    public ReporteSemanaActualDTO getSelectedDto() {
        return selectedDto;
    }

    public void setSelectedDto(ReporteSemanaActualDTO selectedDto) {
        this.selectedDto = selectedDto;
    }

    public ReporteSemanaMotivoPrg getMotivoPrg() {
        return motivoPrg;
    }

    public void setMotivoPrg(ReporteSemanaMotivoPrg motivoPrg) {
        this.motivoPrg = motivoPrg;
    }

}
