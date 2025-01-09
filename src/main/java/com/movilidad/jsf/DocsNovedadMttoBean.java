/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.NovedadDocsFacadeLocal;
import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadDocs;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "docsNovMttoBean")
@ViewScoped
public class DocsNovedadMttoBean implements Serializable {

    /**
     * Creates a new instance of DocsNovedadMttoBean
     */
    public DocsNovedadMttoBean() {
    }
    @EJB
    private NovedadDocsFacadeLocal docsEJB;
    @Inject
    private FileUploadBean fileUploadBean;
    private List<NovedadDocs> lstNovedadDocs;
    private NovedadDocs novDoc;
    private Novedad novedad;
    private String headerModal = "";
    private boolean flagGuardarImg;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public void editar() {
    }

    public void guardar() {
        if (fileUploadBean.getArchivos() == null || (fileUploadBean.getArchivos()
                != null && fileUploadBean.getArchivos().isEmpty())) {
            MovilidadUtil.addErrorMessage("Se deben agregar archivos.");
            return;
        }
        novDoc = new NovedadDocs();
        novDoc.setPathArchivo("");
        novDoc.setCreado(MovilidadUtil.fechaCompletaHoy());
        novDoc.setUsername(user.getUsername());
        novDoc.setEstadoReg(0);
        novDoc.setIdNovedad(novedad);
        docsEJB.create(novDoc);

        String path = fileUploadBean.guardarFotos(novDoc.getIdNovedadDocsc(), ConstantsUtil.KEY_DIR_NOVEDAD_DOCS);
        if (!path.isEmpty()) {
            novDoc.setPathArchivo(path);
            docsEJB.edit(novDoc);
        }
        findDocByIdNovedad(novedad.getIdNovedad());
        MovilidadUtil.addSuccessMessage(ConstantsUtil.SAVE_DONE);
    }

    /**
     * *
     *
     * @param idNovedad
     */
    void findDocByIdNovedad(int idNovedad) {
        lstNovedadDocs = docsEJB.findAllByIdNovedad(idNovedad);
    }

    public void prepareNovedadDocs(Novedad param, boolean flag) {
        novedad = param;
        flagGuardarImg = flag;
        headerModal = novedad.getIdVehiculo().getPlaca() + " - "
                + novedad.getIdVehiculo().getCodigo();
        fileUploadBean.cargarListArchivo();
        findDocByIdNovedad(novedad.getIdNovedad());
        MovilidadUtil.openModal("novedad_docs_wv");
    }

    public void verFotos(NovedadDocs param) throws IOException {
        if (param == null) {
            return;
        }
        fileUploadBean.obtenerFotos(param.getPathArchivo(), param.getIdNovedadDocsc(), ConstantsUtil.KEY_DIR_NOVEDAD_DOCS);
        MovilidadUtil.openModal("galeria_foto_dialog_wv");
    }

    public List<NovedadDocs> getLstNovedadDocs() {
        return lstNovedadDocs;
    }

    public void setLstNovedadDocs(List<NovedadDocs> lstNovedadDocs) {
        this.lstNovedadDocs = lstNovedadDocs;
    }

    public NovedadDocs getNovDoc() {
        return novDoc;
    }

    public void setNovDoc(NovedadDocs novDoc) {
        this.novDoc = novDoc;
    }

    public String getHeaderModal() {
        return headerModal;
    }

    public void setHeaderModal(String headerModal) {
        this.headerModal = headerModal;
    }

    public boolean isFlagGuardarImg() {
        return flagGuardarImg;
    }

    public void setFlagGuardarImg(boolean flagGuardarImg) {
        this.flagGuardarImg = flagGuardarImg;
    }

}
