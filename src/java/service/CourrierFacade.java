package service;

import bean.Courrier;
import bean.PieceJointe;
import bean.UniteAdministrative;
import bean.User;
import controller.util.JsfUtil;
import controller.util.SessionUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CourrierFacade extends AbstractFacade<Courrier> {

    @PersistenceContext(unitName = "ProjetFinEtudesPU")
    private EntityManager em;

    @EJB
    private service.PieceJointeFacade pieceJointeFacade;

    public Long maxId() {
        Long maxId = (Long) em.createQuery("SELECT MAX(c.id) FROM Courrier c").getSingleResult();
        return maxId;
    }

    public void createCourrier(Courrier selected, List<PieceJointe> pieceJointes) {
        super.create(selected);
        Courrier loaded = find(maxId());
        for (int i = 0; i < pieceJointes.size(); i++) {
            PieceJointe get = pieceJointes.get(i);
            System.out.println(get.toString());
            get.setCourrier(loaded);
            pieceJointeFacade.create(get);
        }
        List<PieceJointe> loadedPieceJointes = pieceJointeFacade.findPieceJointesByCourrier(loaded);
        loaded.setPieceJointes(loadedPieceJointes);
        super.edit(loaded);
        pieceJointes.clear();
        selected = null;
        JsfUtil.addSuccessMessage("Courrier a été creé avec succés");
    }

    public void FavoriserCourrier(Courrier courrier) {
        User user = SessionUtil.getConnectedUser();
        courrier.getFavoris().add(user.getUniteAdministrative());
        super.edit(courrier);
    }

    public boolean courrierFavoris(Courrier courrier) {
        User user = SessionUtil.getConnectedUser();
        if (exist(courrier.getFavoris(), user.getUniteAdministrative()) == 1) {
            return true;
        }
        return false;
    }

    public List<Courrier> favoris() {
        User user = SessionUtil.getConnectedUser();
        String req = "Select distinct d FROM Courrier d JOIN d.favoris t WHERE t.id = '" + user.getUniteAdministrative().getId() + "'";
        List<Courrier> items = em.createQuery(req).getResultList();
        return items;
    }

    public int nombreCourrierNonLus() {
        User user = SessionUtil.getConnectedUser();
        if (user.getUniteAdministrative() == null) {
            return 0;
        } else if (user.getUniteAdministrative().getId() == 1) {
            return nombreCourrierNonLus(courrierCabinet());
        } else if (user.getUniteAdministrative().getId() == 2) {
            return nombreCourrierNonLus(courrierDai());
        } else if (user.getUniteAdministrative().getId() == 3) {
            return nombreCourrierNonLus(courrierSg());
        } else if (user.getUniteAdministrative().getId() == 4) {
            return nombreCourrierNonLus(findAll());
        } else if (user.getUniteAdministrative().getUniteAdministrativePere().getId() == 2) {
            return nombreCourrierNonLus(courrierServiceDai());
        } else if (user.getUniteAdministrative().getUniteAdministrativePere().getId() == 3) {
            return nombreCourrierNonLus(courrierDivisionSg());
        } else {
            return 0;
        }
    }

    public int nombreCourrierNonLus(List<Courrier> courriers) {
        User user = SessionUtil.getConnectedUser();
        List<Courrier> res = new ArrayList<>();
        for (int i = 0; i < courriers.size(); i++) {
            Courrier get = courriers.get(i);
            for (int j = 0; j < get.getLu().size(); j++) {
                UniteAdministrative get1 = get.getLu().get(j);
                if (user.getUniteAdministrative().equals(get1)) {
                    res.add(get);
                }
            }
        }
        return (courriers.size() - res.size());
    }

    public List<Courrier> Lus() {
        User user = SessionUtil.getConnectedUser();
        String req = "Select distinct d FROM Courrier d JOIN d.lu t WHERE t.id = '" + user.getUniteAdministrative().getId() + "'";
        List<Courrier> items = em.createQuery(req).getResultList();
        return items;
    }

    public void LireCourrier(Courrier courrier) {
        User user = SessionUtil.getConnectedUser();
        if (courrier.getLu().contains(user.getUniteAdministrative())) {
        } else {
            courrier.getLu().add(user.getUniteAdministrative());
            super.edit(courrier);
        }
    }

    public boolean courrierLus(Courrier courrier) {
        User user = SessionUtil.getConnectedUser();
        if (exist(courrier.getLu(), user.getUniteAdministrative()) == 1) {
            return true;
        }
        return false;
    }

    public int exist(List<UniteAdministrative> items, UniteAdministrative ua) {
        for (int i = 0; i < items.size(); i++) {
            UniteAdministrative item = items.get(i);
            if (item.equals(ua)) {
                return 1;
            }
        }
        return -1;
    }

    public List<Courrier> courrierDestines() {
        User user = SessionUtil.getConnectedUser();
        String req = "Select distinct d FROM Courrier d JOIN d.uniteAdministratives t WHERE t.id = '" + user.getUniteAdministrative().getId() + "'";
        List<Courrier> items = em.createQuery(req).getResultList();
        return items;
    }

    public List<Courrier> courrierValideByCabinet() {
        String req = "SELECT co FROM Courrier co WHERE co.validationCabinet = 1";
        return em.createQuery(req).getResultList();
    }

    public List<Courrier> courrierCabinet() {
        String req = "SELECT co FROM Courrier co WHERE co.validationCabinet = 0";
        return em.createQuery(req).getResultList();
    }

    public List<Courrier> courrierDai() {
        List<Courrier> loaded = courrierValideByCabinet();
        List<Courrier> res = new ArrayList<>();
        for (int i = 0; i < loaded.size(); i++) {
            Courrier get = loaded.get(i);
            for (int j = 0; j < get.getUniteAdministratives().size(); j++) {
                UniteAdministrative get1 = get.getUniteAdministratives().get(j);
                if (get1.getUniteAdministrativePere() == null) {
                } else if ((get1.getUniteAdministrativePere().getId() == 2 || get1.getId() == 2) && get.isValidationDai() == false) {
                    res.add(get);
                    j = get.getUniteAdministratives().size();
                }
            }
        }
        return res;
    }

    public List<Courrier> courrierSg() {
        List<Courrier> loaded = courrierValideByCabinet();
        List<Courrier> res = new ArrayList<>();
        for (int i = 0; i < loaded.size(); i++) {
            Courrier get = loaded.get(i);
            for (int j = 0; j < get.getUniteAdministratives().size(); j++) {
                UniteAdministrative get1 = get.getUniteAdministratives().get(j);
                if (get1.getUniteAdministrativePere() == null) {
                } else if ((get1.getUniteAdministrativePere().getId() == 3 || get1.getId() == 3) && get.isValidationSg() == false) {
                    res.add(get);
                    j = get.getUniteAdministratives().size();
                }
            }
        }
        return res;
    }

    private List<Courrier> courrierServiceDai() {
        List<Courrier> items = courrierDestines();
        List<Courrier> res = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            Courrier item = items.get(i);
            if (item.isValidationDai() == true && item.isValidationCabinet() == true) {
                res.add(item);
            }
        }
        return res;
    }

    private List<Courrier> courrierDivisionSg() {
        List<Courrier> items = courrierDestines();
        List<Courrier> res = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            Courrier item = items.get(i);
            if (item.isValidationSg() == true && item.isValidationCabinet() == true) {
                res.add(item);
            }
        }
        return res;
    }

    public List<Courrier> getItems(List<Courrier> items) {
        User user = SessionUtil.getConnectedUser();
        if (items == null) {
            if (user.getUniteAdministrative() == null) {
                items = findAll();
            } else if (user.getUniteAdministrative().getId() == 1) {
                items = courrierCabinet();
            } else if (user.getUniteAdministrative().getId() == 2) {
                items = courrierDai();
            } else if (user.getUniteAdministrative().getId() == 3) {
                items = courrierSg();
            } else if (user.getUniteAdministrative().getId() == 4) {
                items = findAll();
            } else if (user.getUniteAdministrative().getUniteAdministrativePere().getId() == 2) {
                items = courrierServiceDai();
            } else if (user.getUniteAdministrative().getUniteAdministrativePere().getId() == 3) {
                items = courrierDivisionSg();
            }
        }
        return items;
    }

    public void validate(Courrier selected, List<Courrier> items) {
        User user = SessionUtil.getConnectedUser();
        if (user.getUniteAdministrative().getId() == 1) {
            selected.setValidationCabinet(true);
        } else if (user.getUniteAdministrative().getId() == 2) {
            selected.setValidationDai(true);
        } else if (user.getUniteAdministrative().getId() == 3) {
            selected.setValidationSg(true);
        }
        super.edit(selected);
        items = null;
    }

    public void CloturerCourrier(Courrier courrier) {
        User user = SessionUtil.getConnectedUser();
        courrier.getClotures().add(user.getUniteAdministrative());
        super.edit(courrier);
    }

    public boolean droitCloture(Courrier courrier) {
        User user = SessionUtil.getConnectedUser();
        if (exist(courrier.getUniteAdministratives(), user.getUniteAdministrative()) == 1) {
            return true;
        }
        return false;
    }

    public boolean isCloture(Courrier courrier) {
        if (courrier.getClotures().equals(courrier.getUniteAdministratives())) {
            return true;
        }
        return false;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CourrierFacade() {
        super(Courrier.class);
    }

}
