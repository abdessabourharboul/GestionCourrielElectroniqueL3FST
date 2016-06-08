/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.CourrierDepart;
import bean.UniteAdministrative;
import bean.User;
import controller.util.SessionUtil;
import java.util.ArrayList;
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
public class CourrierDepartFacade extends AbstractFacade<CourrierDepart> {

    @PersistenceContext(unitName = "ProjetFinEtudesPU")
    private EntityManager em;

    @EJB
    UniteAdministrativeFacade uniteAdministrativeFacade;

    public UniteAdministrative suivi(String courrierID) {
        CourrierDepart loaded = find(courrierID);
        if (loaded != null) {
            if (loaded.getUniteDepart().getUniteAdministrativePere().getUniteAdministrativePere().getId() == 1) {
                if (loaded.isValidationDai() == false && loaded.isValidationSg() == false) {
                    return loaded.getUniteDepart().getUniteAdministrativePere();
                } else if (loaded.isValidationCabinet() == false) {
                    return loaded.getUniteDepart().getUniteAdministrativePere().getUniteAdministrativePere();
                } else {
                    return uniteAdministrativeFacade.find(4);
                }
            } else if (loaded.getUniteDepart().getUniteAdministrativePere().getId() == 1) {
                if (loaded.isValidationDai() == false) {
                    return loaded.getUniteDepart().getUniteAdministrativePere();
                } else {
                    return uniteAdministrativeFacade.find(4);
                }
            } else {
                return uniteAdministrativeFacade.find(4);
            }
        }
        return null;
    }

    public int existe(List<CourrierDepart> items, CourrierDepart cd) {
        for (int i = 0; i < items.size(); i++) {
            CourrierDepart item = items.get(i);
            if (item.getId() == cd.getId()) {
                return 1;
            }
        }
        return -1;
    }

    public List<CourrierDepart> courrierValideByCabinet() {
        String req = "SELECT co FROM CourrierDepart co WHERE co.validationCabinet = 1";
        return em.createQuery(req).getResultList();
    }

    public List<CourrierDepart> courrierDepartSg() {
        String req = "SELECT co FROM CourrierDepart co WHERE co.validationSg = 0";
        List<CourrierDepart> items = em.createQuery(req).getResultList();
        List<CourrierDepart> res = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            CourrierDepart item = items.get(i);
            if (item.getUniteDepart().getUniteAdministrativePere() == null) {
            } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 3) {
                int index = existe(res, item);
                if (index == -1) {
                    res.add(item);
                }
            }
        }
        return res;
    }

    public List<CourrierDepart> courrierDepartDai() {
        String req = "SELECT co FROM CourrierDepart co WHERE co.validationDai = 0";
        List<CourrierDepart> items = em.createQuery(req).getResultList();
        List<CourrierDepart> res = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            CourrierDepart item = items.get(i);
            if (item.getUniteDepart().getUniteAdministrativePere() == null) {
            } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 2) {
                int index = existe(res, item);
                if (index == -1) {
                    res.add(item);
                }
            }
        }
        return res;
    }

    public List<CourrierDepart> courrierDepartCabinet() {
        String req = "SELECT co FROM CourrierDepart co WHERE co.validationCabinet = 0";
        List<CourrierDepart> items = em.createQuery(req).getResultList();
        List<CourrierDepart> res = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            CourrierDepart item = items.get(i);
            if (item.getUniteDepart().getUniteAdministrativePere() == null) {
            } else if ((item.getUniteDepart().getUniteAdministrativePere().getId() == 2 && item.isValidationDai() == true)
                    || (item.getUniteDepart().getId() == 2)
                    || (item.getUniteDepart().getId() == 3)
                    || (item.getUniteDepart().getUniteAdministrativePere().getId() == 3 && item.isValidationSg() == true)) {
                int index = existe(res, item);
                if (index == -1) {
                    res.add(item);
                }
            }
        }
        System.out.println(res);
        return res;

    }

    public List<CourrierDepart> getItems(List<CourrierDepart> items) {
        User user = SessionUtil.getConnectedUser();
        if (items == null) {
            if (user.getUniteAdministrative() == null) {
                items = findAll();
            } else if (user.getUniteAdministrative().getId() == 1) {
                items = courrierDepartCabinet();
            } else if (user.getUniteAdministrative().getId() == 2) {
                items = courrierDepartDai();
            } else if (user.getUniteAdministrative().getId() == 3) {
                items = courrierDepartSg();
            } else if (user.getUniteAdministrative().getId() == 4) {
                items = findAll();
            } else {
                findAll();
            }
        }
        return items;
    }

    public void validate(CourrierDepart selected, List<CourrierDepart> items) {
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

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CourrierDepartFacade() {
        super(CourrierDepart.class);
    }

}
