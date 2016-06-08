package controller;

import bean.PieceJointe;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import controller.util.ServerConfigUtil;
import service.PieceJointeFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.event.FileUploadEvent;

@Named("pieceJointeController")
@SessionScoped
public class PieceJointeController implements Serializable {

    @EJB
    private service.PieceJointeFacade ejbFacade;
    private List<PieceJointe> items = null;
    private PieceJointe selected;

    public void upload(FileUploadEvent event) {
        selected.setLogo("logo-" + new Date().getTime() + ".png");
        ServerConfigUtil.upload(event.getFile(), ServerConfigUtil.getPieceJointePath(true), selected.getLogo());
    }

    public String findPath() {
        if (selected.getLogo() != null) {
            System.out.println("path ==> " + ServerConfigUtil.getPieceJointePath(false) + "/" + selected.getLogo());
            return ServerConfigUtil.getPieceJointePath(false) + "/" + selected.getLogo();
        }
        return ServerConfigUtil.getPieceJointePath(false) + "/noOne.png";
    }

    public PieceJointeController() {
    }

    public PieceJointe getSelected() {
        return selected;
    }

    public void setSelected(PieceJointe selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PieceJointeFacade getFacade() {
        return ejbFacade;
    }

    public PieceJointe prepareCreate() {
        selected = new PieceJointe();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PieceJointeCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PieceJointeUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PieceJointeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PieceJointe> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public PieceJointe getPieceJointe(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<PieceJointe> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PieceJointe> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PieceJointe.class)
    public static class PieceJointeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PieceJointeController controller = (PieceJointeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pieceJointeController");
            return controller.getPieceJointe(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PieceJointe) {
                PieceJointe o = (PieceJointe) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PieceJointe.class.getName()});
                return null;
            }
        }

    }

}
