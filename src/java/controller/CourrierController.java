package controller;

import bean.Consigne;
import bean.Contact;
import bean.Courrier;
import bean.PieceJointe;
import bean.Traitement;
import bean.User;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import controller.util.ServerConfigUtil;
import controller.util.SessionUtil;
import java.io.InputStream;
import service.CourrierFacade;
import java.io.Serializable;
import java.util.ArrayList;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named("courrierController")
@SessionScoped
public class CourrierController implements Serializable {

    @EJB
    private service.CourrierFacade ejbFacade;
    @EJB
    private service.ConsigneFacade consigneFacade;
    @EJB
    private service.TraitementFacade traitementFacade;
    @EJB
    private service.PieceJointeFacade pieceJointeFacade;
    private List<Courrier> items = null;
    private Courrier selected;
    private Consigne consigne;
    private List<Consigne> consignes;
    private PieceJointe pieceJointe;
    private PieceJointe pieceJointeHelper;
    private List<PieceJointe> pieceJointes;
    private List<PieceJointe> pieceJointesHelper;
    private List<Courrier> filteredItems;
    private boolean skip;
    private Contact contact;
    private Traitement traitement;
    private List<Traitement> traitements;

    public boolean isCloture(Courrier c) {
        return ejbFacade.isCloture(c);
    }

    public String clotures(Courrier c) {
        if (isCloture(c) == true) {
            return "Cloturé";
        }
        return "En cours";
    }

    public void CloturerCourrier() {
        ejbFacade.CloturerCourrier(getSelected());
    }

    public boolean droitCloture() {
        return ejbFacade.droitCloture(getSelected());
    }

    public int nombreCourrierNonLus() {
        return ejbFacade.nombreCourrierNonLus();
    }

    public List<Courrier> Lus() {
        return ejbFacade.Lus();
    }

    public void LireCourrier() {
        ejbFacade.LireCourrier(getSelected());
    }

    public boolean courrierLus(Courrier courrier) {
        return ejbFacade.courrierLus(courrier);
    }

    public List<Courrier> favoris() {
        User user = SessionUtil.getConnectedUser();
        if (user.getUniteAdministrative() == null) {
            return new ArrayList<>();
        }
        return ejbFacade.favoris();
    }

    public void FavoriserCourrier(Courrier courrier) {
        ejbFacade.FavoriserCourrier(courrier);
    }

    public boolean courrierFavoris(Courrier courrier) {
        return ejbFacade.courrierFavoris(courrier);
    }

    public String courrierLusRow(Courrier courrier) {
        if (courrierLus(courrier) == true) {
            return "lu";
        } else if (courrierLus(courrier) == false) {
            return "white";
        }
        return "";
    }

    public String alarStyleClass(Courrier courrier) {
        if (courrier.getContact().getNom().equals("amine")) {
            return "red";
        } else {
            return "black";
        }
    }

    public Contact getContact() {
        contact = new Contact();
        contact.setId(1L);
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void needCreateContact() {
        System.out.println("ha selected" + selected.getContact());
        System.out.println("ha contact" + getContact());
        if (selected.getContact().getId() == null) {
            RequestContext.getCurrentInstance().execute("PF('ContactCreateDialog').show()");
        }
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public List<Courrier> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<Courrier> filteredItems) {
        this.filteredItems = filteredItems;
    }

    public void upload(FileUploadEvent event) {
        getPieceJointeHelper().setLogo("doc-" + new Date().getTime() + ".png");
        ServerConfigUtil.upload(event.getFile(), ServerConfigUtil.getPieceJointePath(true), getPieceJointeHelper().getLogo());
        getPieceJointesHelper().add(pieceJointeFacade.clone(getPieceJointeHelper()));
    }

    public void uploadEdit(FileUploadEvent event) {
        getPieceJointe().setLogo("doc-" + new Date().getTime() + ".png");
        getPieceJointe().setCourrier(selected);
        ServerConfigUtil.upload(event.getFile(), ServerConfigUtil.getPieceJointePath(true), getPieceJointe().getLogo());
    }

    public void createPj() {
        pieceJointeFacade.create(getPieceJointe());
        getSelected().getPieceJointes().clear();
    }

    public void destroyPj(PieceJointe pieceJointe) {
        ServerConfigUtil.delete(ServerConfigUtil.getPieceJointePath(true), pieceJointe.getLogo());
        pieceJointeFacade.remove(pieceJointe);
        getSelected().getPieceJointes().clear();
    }

    public StreamedContent fileDownloadView(PieceJointe pieceJointe) {
        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(ServerConfigUtil.getPieceJointePath(false) + "/" + pieceJointe.getLogo());
        return new DefaultStreamedContent(stream, "image/jpg", "downloaded_optimus.jpg");
    }

    public String findPath(PieceJointe pieceJointe) {
        if (pieceJointe.getLogo() != null) {
            System.out.println("path ==> " + ServerConfigUtil.getPieceJointePath(false) + "/" + pieceJointe.getLogo());
            return ServerConfigUtil.getPieceJointePath(false) + "/" + pieceJointe.getLogo();
        }
        return ServerConfigUtil.getPieceJointePath(false) + "/noOne.png";
    }

    public void findConsignesByCourrier(Courrier courrier) {
        selected = courrier;
        getSelected().setConsignes(consigneFacade.findConsignesByCourrier(courrier));
    }

    public void findPieceJointesByCourrier(Courrier courrier) {
        selected = courrier;
        getSelected().setPieceJointes(pieceJointeFacade.findPieceJointesByCourrier(courrier));
    }

    public void validate() {
        ejbFacade.validate(selected, items);
        setSelected(null);
        setConsignes(null);
        setPieceJointes(null);
    }

    public void saveTraitement() {
        traitementFacade.saveTraitement(getTraitement(), getSelected());
        getTraitements().add(getTraitement());
        traitement = null;
    }

    public void saveConsigne() {
        consigneFacade.saveConsigne(getConsigne(), getSelected());
        getConsignes().add(getConsigne());
        consigne = null;
    }

    public Courrier getSelected() {
        if (selected == null) {
            selected = new Courrier();
        }
        return selected;
    }

    public void setSelected(Courrier selected) {
        this.selected = selected;
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
            traitements = traitementFacade.findTraitementsByCourrier(selected);
        } else {
            traitements = null;
        }
        return traitements;
    }

    public void setTraitements(List<Traitement> traitements) {
        this.traitements = traitements;
    }

    public Consigne getConsigne() {
        if (consigne == null) {
            consigne = new Consigne();
        }
        return consigne;
    }

    public void setConsigne(Consigne consigne) {
        this.consigne = consigne;
    }

    public List<Consigne> getConsignes() {
        if (getSelected().getId() != null) {
            consignes = consigneFacade.findConsignesByCourrier(selected);
        } else {
            consignes = null;
        }
        return consignes;
    }

    public void setConsignes(List<Consigne> consignes) {
        this.consignes = consignes;
    }

    public PieceJointe getPieceJointe() {
        if (pieceJointe == null) {
            pieceJointe = new PieceJointe();
        }
        return pieceJointe;
    }

    public void setPieceJointe(PieceJointe pieceJointe) {
        this.pieceJointe = pieceJointe;
    }

    public List<PieceJointe> getPieceJointes() {
        if (getSelected().getId() != null) {
            pieceJointes = pieceJointeFacade.findPieceJointesByCourrier(selected);
        } else {
            pieceJointes = null;
        }
        return pieceJointes;
    }

    public void setPieceJointes(List<PieceJointe> pieceJointes) {
        this.pieceJointes = pieceJointes;
    }

    public PieceJointe getPieceJointeHelper() {
        if (pieceJointeHelper == null) {
            pieceJointeHelper = new PieceJointe();
        }
        return pieceJointeHelper;
    }

    public void setPieceJointeHelper(PieceJointe pieceJointeHelper) {
        this.pieceJointeHelper = pieceJointeHelper;
    }

    public List<PieceJointe> getPieceJointesHelper() {
        if (pieceJointesHelper == null) {
            pieceJointesHelper = new ArrayList<>();
        }
        return pieceJointesHelper;
    }

    public void setPieceJointesHelper(List<PieceJointe> pieceJointesHelper) {
        this.pieceJointesHelper = pieceJointesHelper;
    }

    public List<Courrier> getItems() {
        return ejbFacade.getItems(items);
    }

    public CourrierController() {
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CourrierFacade getFacade() {
        return ejbFacade;
    }

    public Courrier prepareCreate() {
        selected = new Courrier();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CourrierCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CourrierUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CourrierDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == PersistAction.CREATE) {
                    getFacade().createCourrier(getSelected(), getPieceJointesHelper());
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

    public Courrier getCourrier(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Courrier> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Courrier> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Courrier.class)
    public static class CourrierControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CourrierController controller = (CourrierController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "courrierController");
            return controller.getCourrier(getKey(value));
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
            if (object instanceof Courrier) {
                Courrier o = (Courrier) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Courrier.class.getName()});
                return null;
            }
        }

    }

}
