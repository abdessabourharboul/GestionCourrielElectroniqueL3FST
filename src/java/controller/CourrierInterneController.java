package controller;

import bean.Courrier;
import bean.CourrierInterne;
import bean.Traitement;
import bean.UniteAdministrative;
import bean.User;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import controller.util.SessionUtil;
import service.CourrierInterneFacade;

import java.io.Serializable;
import java.util.ArrayList;
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

@Named("courrierInterneController")
@SessionScoped
public class CourrierInterneController implements Serializable {

    @EJB
    private service.CourrierInterneFacade ejbFacade;
    @EJB
    private service.TraitementFacade traitementFacade;
    private List<CourrierInterne> items = null;
    private List<CourrierInterne> itemsav = null;
    private CourrierInterne selected;
    private List<UniteAdministrative> uniteAdministratives;
    private UniteAdministrative uniteAdministrative;
    private Traitement traitement;
    private List<Traitement> traitements;

    public void saveTraitement() {
        traitementFacade.saveTraitementInterne(getTraitement(), getSelected());
        getTraitements().add(getTraitement());
        traitement = null;
    }

    public Traitement getTraitement() {
        if (traitement == null) {
            traitement = new Traitement();
        }
        return traitement;
    }

    public void setTraitement(Traitement traitement) {
        this.traitement = traitement;
    }

    public List<Traitement> getTraitements() {
        if (getSelected().getId() != null) {
            traitements = traitementFacade.findTraitementsByCourrierInterne(selected);
        } else {
            traitements = null;
        }
        return traitements;
    }

    public void setTraitements(List<Traitement> traitements) {
        this.traitements = traitements;
    }

    public String courrierLusRow(CourrierInterne courrierInterne) {
        if (courrierLus(courrierInterne) == true) {
            return "lu";
        } else if (courrierLus(courrierInterne) == false) {
            return "white";
        }
        return "";
    }

    public int nombreCourrierNonLus() {
        return ejbFacade.nombreCourrierInterneNonLus();
    }

    public List<CourrierInterne> Lus() {
        return ejbFacade.Lus();
    }

    public void LireCourrier() {
        ejbFacade.LireCourrier(getSelected());
    }

    public boolean courrierLus(CourrierInterne courrierInterne) {
        return ejbFacade.courrierLus(courrierInterne);
    }

    public List<CourrierInterne> favoris() {
        User user = SessionUtil.getConnectedUser();
        if (user.getUniteAdministrative() == null) {
            return new ArrayList<>();
        }
        return ejbFacade.favoris();
    }

    public void FavoriserCourrier(CourrierInterne courrierInterne) {
        ejbFacade.FavoriserCourrier(courrierInterne);
    }

    public boolean courrierFavoris(CourrierInterne courrierInterne) {
        return ejbFacade.courrierFavoris(courrierInterne);
    }

    public UniteAdministrative getUniteAdministrative() {
        if (uniteAdministrative == null) {
            uniteAdministrative = new UniteAdministrative();
        }
        return uniteAdministrative;
    }

    public void setUniteAdministrative(UniteAdministrative uniteAdministrative) {
        this.uniteAdministrative = uniteAdministrative;
    }

    public List<UniteAdministrative> getUniteAdministratives() {
//        System.out.println(getSelected().getId());
        if (getSelected().getId() != null) {
            uniteAdministratives = ejbFacade.suivi(getSelected());
        } else {
            uniteAdministratives = null;
        }
        return uniteAdministratives;
    }

    public void setUniteAdministratives(List<UniteAdministrative> uniteAdministratives) {
        this.uniteAdministratives = uniteAdministratives;
    }

    public void validate() {
        System.out.println("nono");
        ejbFacade.validate(selected, items);
        setSelected(null);
    }

    public CourrierInterneController() {
    }

    public CourrierInterne getSelected() {
        if (selected == null) {
            selected = new CourrierInterne();
        }
        return selected;
    }

    public void setSelected(CourrierInterne selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CourrierInterneFacade getFacade() {
        return ejbFacade;
    }

    public CourrierInterne prepareCreate() {
        selected = new CourrierInterne();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CourrierInterneCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CourrierInterneUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CourrierInterneDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<CourrierInterne> getItems() {
        return ejbFacade.getItems(items);
    }

    public List<CourrierInterne> getItemsav() {
        return ejbFacade.getItemsav(itemsav);
    }

    public void setItemsav(List<CourrierInterne> itemsav) {
        this.itemsav = itemsav;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == PersistAction.CREATE) {
                    getFacade().createCourrierInterne(selected);
                } else if (persistAction == PersistAction.UPDATE) {
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

    public CourrierInterne getCourrierInterne(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<CourrierInterne> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<CourrierInterne> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = CourrierInterne.class)
    public static class CourrierInterneControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CourrierInterneController controller = (CourrierInterneController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "courrierInterneController");
            return controller.getCourrierInterne(getKey(value));
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
            if (object instanceof CourrierInterne) {
                CourrierInterne o = (CourrierInterne) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), CourrierInterne.class.getName()});
                return null;
            }
        }

    }

}
