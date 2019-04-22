/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.CourrierInterne;
import bean.UniteAdministrative;
import bean.User;
import controller.util.SessionUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author asus
 */
@Stateless
public class CourrierInterneFacade extends AbstractFacade<CourrierInterne> {

    @PersistenceContext(unitName = "ProjetFinEtudesPU")
    private EntityManager em;

    @EJB
    UniteAdministrativeFacade uniteAdministrativeFacade;
    @EJB
    CourrierFacade courrierFacade;

    public void createCourrierInterne(CourrierInterne selected) {
        selected.setDateDepart(new Date());
        selected.setUniteDepart(SessionUtil.getConnectedUser().getUniteAdministrative());
        super.create(selected);
    }

    public void validate(CourrierInterne selected, List<CourrierInterne> items) {
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

    public List<CourrierInterne> courrierInterneDestineA() {
        User user = SessionUtil.getConnectedUser();
        String req = "Select distinct d FROM CourrierInterne d JOIN d.uniteDestinataires t WHERE t.id = '" + user.getUniteAdministrative().getId() + "' AND d.numOrdre is not null";
        List<CourrierInterne> items = em.createQuery(req).getResultList();
        return items;
    }

    public List<CourrierInterne> courrierInterneDestineA(UniteAdministrative uniteAdministrative) {
        String req = "Select distinct d FROM CourrierInterne d JOIN d.uniteDestinataires t WHERE t.id = '" + uniteAdministrative.getId() + "' AND d.numOrdre is not null";
        List<CourrierInterne> items = em.createQuery(req).getResultList();
        return items;
    }

    public List<CourrierInterne> courrierInterneCabinetav() {
        String req = "Select d FROM CourrierInterne d WHERE d.validationCabinet = 0 AND d.numOrdre is not null";
        List<CourrierInterne> items = em.createQuery(req).getResultList();
        List<CourrierInterne> res = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            CourrierInterne item = items.get(i);
            for (int j = 0; j < item.getUniteDestinataires().size(); j++) {
                UniteAdministrative get = item.getUniteDestinataires().get(j);
                if (get.getUniteAdministrativePere() == null) {
                } else if (get.getId() == 2 || get.getUniteAdministrativePere().getId() == 2) {
                    if (item.getUniteDepart().getUniteAdministrativePere() == null) {
                    } else if ((item.getUniteDepart().getUniteAdministrativePere().getId() == 3 && item.isValidationSg() == true) || item.getUniteDepart().getId() == 3) {
                        res.add(item);
                        j = item.getUniteDestinataires().size();
                    }
                } else if (get.getId() == 3 || get.getUniteAdministrativePere().getId() == 3) {
                    if (item.getUniteDepart().getUniteAdministrativePere() == null) {
                    } else if ((item.getUniteDepart().getUniteAdministrativePere().getId() == 2 && item.isValidationDai() == true) || item.getUniteDepart().getId() == 2) {
                        res.add(item);
                        j = item.getUniteDestinataires().size();
                    }
                }
            }
        }
        return res;
    }

    public List<CourrierInterne> courrierInterneDaiav() {
        String req = "Select d FROM CourrierInterne d WHERE d.validationDai = 0 AND d.numOrdre is not null";
        List<CourrierInterne> items = em.createQuery(req).getResultList();
        List<CourrierInterne> res = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            CourrierInterne item = items.get(i);
            for (int j = 0; j < item.getUniteDestinataires().size(); j++) {
                UniteAdministrative get = item.getUniteDestinataires().get(j);
                if (get.getUniteAdministrativePere() == null) {
                } else if (get.getId() == 1 || get.getId() == 3 || get.getUniteAdministrativePere().getId() == 3) {
                    if (item.getUniteDepart().getUniteAdministrativePere() == null) {
                    } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 2) {
                        res.add(item);
                        j = item.getUniteDestinataires().size();
                    }
                } else if (get.getUniteAdministrativePere().getId() == 2) {
                    if (item.getUniteDepart().getUniteAdministrativePere() == null) {
                    } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 3 && item.isValidationSg() == true && item.isValidationCabinet() == true) {
                        res.add(item);
                        j = item.getUniteDestinataires().size();
                    } else if (item.getUniteDepart().getId() == 3 && item.isValidationCabinet() == true) {
                        res.add(item);
                        j = item.getUniteDestinataires().size();
                    } else if (item.getUniteDepart().getId() == 1) {
                        res.add(item);
                        j = item.getUniteDestinataires().size();
                    }
                }
            }
        }
        return res;
    }

    public List<CourrierInterne> courrierInterneSgav() {
        String req = "Select d FROM CourrierInterne d WHERE d.validationSg = 0 AND d.numOrdre is not null";
        List<CourrierInterne> items = em.createQuery(req).getResultList();
        List<CourrierInterne> res = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            CourrierInterne item = items.get(i);
            for (int j = 0; j < item.getUniteDestinataires().size(); j++) {
                UniteAdministrative get = item.getUniteDestinataires().get(j);
                if (get.getUniteAdministrativePere() == null) {
                } else if (get.getId() == 1 || get.getId() == 2 || get.getUniteAdministrativePere().getId() == 2) {
                    if (item.getUniteDepart().getUniteAdministrativePere() == null) {
                    } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 3) {
                        res.add(item);
                        j = item.getUniteDestinataires().size();
                    }
                } else if (get.getUniteAdministrativePere().getId() == 3) {
                    if (item.getUniteDepart().getUniteAdministrativePere() == null) {
                    } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 2 && item.isValidationDai() == true && item.isValidationCabinet() == true) {
                        res.add(item);
                        j = item.getUniteDestinataires().size();
                    } else if (item.getUniteDepart().getId() == 2 && item.isValidationCabinet() == true) {
                        res.add(item);
                        j = item.getUniteDestinataires().size();
                    } else if (item.getUniteDepart().getId() == 1) {
                        res.add(item);
                        j = item.getUniteDestinataires().size();
                    }
                }
            }
        }
        return res;
    }

    public List<CourrierInterne> courrierInterneCabinet() {
        UniteAdministrative uniteAdministrative = uniteAdministrativeFacade.find(new Long(1));
        List<CourrierInterne> items = courrierInterneDestineA(uniteAdministrative);
        List<CourrierInterne> res = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            CourrierInterne item = items.get(i);
            if (item.getUniteDepart().getUniteAdministrativePere() == null) {
            } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 2 && item.isValidationDai() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 3 && item.isValidationSg() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 2) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 3) {
                res.add(item);
            }
        }
        return res;
    }

    public List<CourrierInterne> courrierInterneDai() {
        UniteAdministrative uniteAdministrative = uniteAdministrativeFacade.find(new Long(2));
        List<CourrierInterne> items = courrierInterneDestineA(uniteAdministrative);
        List<CourrierInterne> res = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            CourrierInterne item = items.get(i);
            if (item.getUniteDepart().getUniteAdministrativePere() == null) {
                res.add(item);
            } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 2) {
                res.add(item);
            } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 3 && item.isValidationSg() == true && item.isValidationCabinet() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 2 && item.isValidationCabinet() == true) {
                res.add(item);
            }
        }
        return res;
    }

    public List<CourrierInterne> courrierInterneSg() {
        UniteAdministrative uniteAdministrative = uniteAdministrativeFacade.find(new Long(3));
        List<CourrierInterne> items = courrierInterneDestineA(uniteAdministrative);
        List<CourrierInterne> res = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            CourrierInterne item = items.get(i);
            if (item.getUniteDepart().getUniteAdministrativePere() == null) {
                res.add(item);
            } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 3) {
                res.add(item);
            } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 2 && item.isValidationDai() == true && item.isValidationCabinet() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 2 && item.isValidationCabinet() == true) {
                res.add(item);
            }
        }
        return res;
    }

    public List<CourrierInterne> courrierInterneServiceDai() {
        List<CourrierInterne> items = courrierInterneDestineA();
        List<CourrierInterne> res = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            CourrierInterne item = items.get(i);
            if (item.getUniteDepart().getUniteAdministrativePere() == null && item.isValidationDai() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 3 && item.isValidationDai() == true && item.isValidationCabinet() == true && item.isValidationSg() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 2) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 3 && item.isValidationCabinet() == true && item.isValidationDai() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 2) {
                res.add(item);
            }
        }
        return res;
    }

    public List<CourrierInterne> courrierInterneServiceDai(UniteAdministrative uniteAdministrative) {
        List<CourrierInterne> items = courrierInterneDestineA(uniteAdministrative);
        List<CourrierInterne> res = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            CourrierInterne item = items.get(i);
            if (item.getUniteDepart().getUniteAdministrativePere() == null && item.isValidationDai() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 3 && item.isValidationDai() == true && item.isValidationCabinet() == true && item.isValidationSg() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 2) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 3 && item.isValidationCabinet() == true && item.isValidationDai() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 2) {
                res.add(item);
            }
        }
        return res;
    }

    public List<CourrierInterne> courrierInterneDivisionSg() {
        List<CourrierInterne> items = courrierInterneDestineA();
        List<CourrierInterne> res = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            CourrierInterne item = items.get(i);
            if (item.getUniteDepart().getUniteAdministrativePere() == null && item.isValidationSg() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 2 && item.isValidationDai() == true && item.isValidationCabinet() == true && item.isValidationSg() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 3) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 2 && item.isValidationCabinet() == true && item.isValidationSg() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 3) {
                res.add(item);
            }
        }
        return res;
    }

    public List<CourrierInterne> courrierInterneDivisionSg(UniteAdministrative uniteAdministrative) {
        List<CourrierInterne> items = courrierInterneDestineA(uniteAdministrative);
        List<CourrierInterne> res = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            CourrierInterne item = items.get(i);
            if (item.getUniteDepart().getUniteAdministrativePere() == null && item.isValidationSg() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 2 && item.isValidationDai() == true && item.isValidationCabinet() == true && item.isValidationSg() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 3) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 2 && item.isValidationCabinet() == true && item.isValidationSg() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 3) {
                res.add(item);
            }
        }
        return res;
    }

    public List<CourrierInterne> getItems(List<CourrierInterne> items) {
        User user = SessionUtil.getConnectedUser();
        if (items == null) {
            if (user.getUniteAdministrative() == null) {
                items = findAll();
            } else if (user.getUniteAdministrative().getId() == 1) {
                items = courrierInterneCabinet();
            } else if (user.getUniteAdministrative().getId() == 2) {
                items = courrierInterneDai();
            } else if (user.getUniteAdministrative().getId() == 3) {
                items = courrierInterneSg();
            } else if (user.getUniteAdministrative().getId() == 4) {
                items = findAll();
            } else if (user.getUniteAdministrative().getUniteAdministrativePere().getId() == 2) {
                items = courrierInterneServiceDai();
            } else if (user.getUniteAdministrative().getUniteAdministrativePere().getId() == 3) {
                items = courrierInterneDivisionSg();
            }
        }
        return items;
    }

    public List<CourrierInterne> getItemsav(List<CourrierInterne> items) {
        User user = SessionUtil.getConnectedUser();
        if (items == null) {
            if (user.getUniteAdministrative() == null) {
                items = findAll();
            } else if (user.getUniteAdministrative().getId() == 1) {
                items = courrierInterneCabinetav();
            } else if (user.getUniteAdministrative().getId() == 2) {
                items = courrierInterneDaiav();
            } else if (user.getUniteAdministrative().getId() == 3) {
                items = courrierInterneSgav();
            } else {
                findAll();
            }
        }
        return items;
    }

    public List<CourrierInterne> courrierValideByCabinet() {
        String req = "SELECT co FROM CourrierInterne co WHERE co.validationCabinet = 1";
        return em.createQuery(req).getResultList();
    }

    public List<UniteAdministrative> suivi(CourrierInterne loaded) {
        List<UniteAdministrative> res = new ArrayList<>();
        for (int i = 0; i < uniteAdministrativeFacade.findAll().size(); i++) {
            UniteAdministrative get = uniteAdministrativeFacade.findAll().get(i);
            if (get.getUniteAdministrativePere() == null) {
                List<CourrierInterne> cr = courrierInterneCabinetav();
                List<CourrierInterne> cr1 = courrierInterneCabinet();
                if (cr.contains(loaded) || cr1.contains(loaded)) {
                    res.add(get);
                }
            } else if (get.getUniteAdministrativePere().getId() == 1) {
                if (get.getId() == 2) {
                    List<CourrierInterne> cr = courrierInterneDaiav();
                    List<CourrierInterne> cr1 = courrierInterneDai();
                    if (cr.contains(loaded) || cr1.contains(loaded)) {
                        res.add(get);
                    }
                } else if (get.getId() == 3) {
                    List<CourrierInterne> cr = courrierInterneSgav();
                    List<CourrierInterne> cr1 = courrierInterneSg();
                    if (cr.contains(loaded) || cr1.contains(loaded)) {
                        res.add(get);
                    }
                }
            } else if (get.getUniteAdministrativePere().getId() == 2) {
                List<CourrierInterne> cr = courrierInterneServiceDai(get);
                if (cr.contains(loaded)) {
                    res.add(get);
                }
            } else if (get.getUniteAdministrativePere().getId() == 3) {
                List<CourrierInterne> cr = courrierInterneDivisionSg(get);
                if (cr.contains(loaded)) {
                    res.add(get);
                }
            }
        }
        for (int i = 0; i < res.size(); i++) {
            UniteAdministrative get = res.get(i);
            System.out.println(get);
        }
        return res;
    }

    public int exist(List<CourrierInterne> items, CourrierInterne c) {
        for (int i = 0; i < items.size(); i++) {
            CourrierInterne item = items.get(i);
            if (item.equals(c)) {
                return 1;
            }
        }
        return -1;
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

    public void FavoriserCourrier(CourrierInterne courrierInterne) {
        User user = SessionUtil.getConnectedUser();
        courrierInterne.getFavoris().add(user.getUniteAdministrative());
        super.edit(courrierInterne);
    }

    public boolean courrierFavoris(CourrierInterne courrierInterne) {
        User user = SessionUtil.getConnectedUser();
        if (exist(courrierInterne.getFavoris(), user.getUniteAdministrative()) == 1) {
            return true;
        }
        return false;
    }

    public List<CourrierInterne> favoris() {
        User user = SessionUtil.getConnectedUser();
        String req = "Select distinct d FROM CourrierInterne d JOIN d.favoris t WHERE t.id = '" + user.getUniteAdministrative().getId() + "'";
        List<CourrierInterne> items = em.createQuery(req).getResultList();
        return items;
    }

    public int nombreCourrierInterneNonLus() {
        User user = SessionUtil.getConnectedUser();
        if (user.getUniteAdministrative() == null) {
            return 0;
        } else if (user.getUniteAdministrative().getId() == 1) {
            return nombreCourrierInterneNonLus(courrierInterneCabinet());
        } else if (user.getUniteAdministrative().getId() == 2) {
            return nombreCourrierInterneNonLus(courrierInterneDai());
        } else if (user.getUniteAdministrative().getId() == 3) {
            return nombreCourrierInterneNonLus(courrierInterneSg());
        } else if (user.getUniteAdministrative().getId() == 4) {
            return nombreCourrierInterneNonLus(findAll());
        } else if (user.getUniteAdministrative().getUniteAdministrativePere().getId() == 2) {
            return nombreCourrierInterneNonLus(courrierInterneServiceDai());
        } else if (user.getUniteAdministrative().getUniteAdministrativePere().getId() == 3) {
            return nombreCourrierInterneNonLus(courrierInterneDivisionSg());
        } else {
            return 0;
        }
    }

    public int nombreCourrierInterneNonLus(List<CourrierInterne> courriersInternes) {
        User user = SessionUtil.getConnectedUser();
        List<CourrierInterne> res = new ArrayList<>();
        for (int i = 0; i < courriersInternes.size(); i++) {
            CourrierInterne get = courriersInternes.get(i);
            for (int j = 0; j < get.getLu().size(); j++) {
                UniteAdministrative get1 = get.getLu().get(j);
                if (user.getUniteAdministrative().equals(get1)) {
                    res.add(get);
                }
            }
        }
        return (courriersInternes.size() - res.size());
    }

    public List<CourrierInterne> Lus() {
        User user = SessionUtil.getConnectedUser();
        String req = "Select distinct d FROM CourrierInterne d JOIN d.lu t WHERE t.id = '" + user.getUniteAdministrative().getId() + "'";
        List<CourrierInterne> items = em.createQuery(req).getResultList();
        return items;
    }

    public void LireCourrier(CourrierInterne courrierInterne) {
        User user = SessionUtil.getConnectedUser();
        if (courrierInterne.getLu().contains(user.getUniteAdministrative())) {
        } else {
            courrierInterne.getLu().add(user.getUniteAdministrative());
            super.edit(courrierInterne);
        }
    }

    public boolean courrierLus(CourrierInterne courrierInterne) {
        User user = SessionUtil.getConnectedUser();
        if (exist(courrierInterne.getLu(), user.getUniteAdministrative()) == 1) {
            return true;
        }
        return false;
    }

    public void CloturerCourrier(CourrierInterne courrierInterne) {
        User user = SessionUtil.getConnectedUser();
        courrierInterne.getClotures().add(user.getUniteAdministrative());
        super.edit(courrierInterne);
    }

    public boolean droitCloture(CourrierInterne courrierInterne) {
        User user = SessionUtil.getConnectedUser();
        if (courrierInterne.getUniteDestinataires().contains(user.getUniteAdministrative())) {
            return true;
        }
        return false;
    }

    public boolean isCloture(CourrierInterne courrierInterne) {
        if (courrierInterne.getClotures().equals(courrierInterne.getUniteDestinataires())) {
            return true;
        }
        return false;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CourrierInterneFacade() {
        super(CourrierInterne.class);
    }

}
