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
                    if ((item.getUniteDepart().getUniteAdministrativePere().getId() == 3 && item.isValidationSg() == true) || item.getUniteDepart().getId() == 3) {
                        res.add(item);
                        j = item.getUniteDestinataires().size();
                    }
                } else if (get.getId() == 3 || get.getUniteAdministrativePere().getId() == 3) {
                    if ((item.getUniteDepart().getUniteAdministrativePere().getId() == 2 && item.isValidationDai() == true) || item.getUniteDepart().getId() == 2) {
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
                if (get.getId() == 1 || get.getId() == 3 || get.getUniteAdministrativePere().getId() == 3) {
                    if (item.getUniteDepart().getUniteAdministrativePere().getId() == 2) {
                        res.add(item);
                        j = item.getUniteDestinataires().size();
                    }
                } else if (get.getUniteAdministrativePere().getId() == 2) {
                    if (item.getUniteDepart().getUniteAdministrativePere().getId() == 3 && item.isValidationSg() == true && item.isValidationCabinet() == true) {
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
                if (get.getId() == 1 || get.getId() == 2 || get.getUniteAdministrativePere().getId() == 2) {
                    if (item.getUniteDepart().getUniteAdministrativePere().getId() == 3) {
                        res.add(item);
                        j = item.getUniteDestinataires().size();
                    }
                } else if (get.getUniteAdministrativePere().getId() == 3) {
                    if (item.getUniteDepart().getUniteAdministrativePere().getId() == 2 && item.isValidationDai() == true && item.isValidationCabinet() == true) {
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
        List<CourrierInterne> items = courrierInterneDestineA();
        List<CourrierInterne> res = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            CourrierInterne item = items.get(i);
            if (item.getUniteDepart().getUniteAdministrativePere().getId() == 2 && item.isValidationDai() == true) {
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
        List<CourrierInterne> items = courrierInterneDestineA();
        List<CourrierInterne> res = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            CourrierInterne item = items.get(i);
            if (item.getUniteDepart().getUniteAdministrativePere().getId() == 2) {
                res.add(item);
            } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 3 && item.isValidationSg() == true && item.isValidationCabinet() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 2 && item.isValidationCabinet() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 1) {
                res.add(item);
            }
        }
        return res;
    }

    public List<CourrierInterne> courrierInterneSg() {
        List<CourrierInterne> items = courrierInterneDestineA();
        List<CourrierInterne> res = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            CourrierInterne item = items.get(i);
            if (item.getUniteDepart().getUniteAdministrativePere().getId() == 3) {
                res.add(item);
            } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 2 && item.isValidationDai() == true && item.isValidationCabinet() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 2 && item.isValidationCabinet() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 1) {
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
            if (item.getUniteDepart().getUniteAdministrativePere().getId() == 3 && item.isValidationDai() == true && item.isValidationCabinet() == true && item.isValidationSg() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 2) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 3 && item.isValidationCabinet() == true && item.isValidationDai() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 2) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 1 && item.isValidationDai() == true) {
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
            if (item.getUniteDepart().getUniteAdministrativePere().getId() == 3 && item.isValidationDai() == true && item.isValidationCabinet() == true && item.isValidationSg() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 2) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 3 && item.isValidationCabinet() == true && item.isValidationDai() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 2) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 1 && item.isValidationDai() == true) {
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
            if (item.getUniteDepart().getUniteAdministrativePere().getId() == 2 && item.isValidationDai() == true && item.isValidationCabinet() == true && item.isValidationSg() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 3) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 2 && item.isValidationCabinet() == true && item.isValidationSg() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 3) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 1 && item.isValidationSg() == true) {
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
            if (item.getUniteDepart().getUniteAdministrativePere().getId() == 2 && item.isValidationDai() == true && item.isValidationCabinet() == true && item.isValidationSg() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getUniteAdministrativePere().getId() == 3) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 2 && item.isValidationCabinet() == true && item.isValidationSg() == true) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 3) {
                res.add(item);
            } else if (item.getUniteDepart().getId() == 1 && item.isValidationSg() == true) {
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
            if (get.getId() == 1) {
                List<CourrierInterne> cr = courrierInterneCabinetav();
                List<CourrierInterne> cr1 = courrierInterneCabinet();
                if (exist(cr, loaded) == 1) {
                    res.add(get);
                }
                if (exist(cr1, loaded) == 1) {
                    res.add(get);
                }
            } else if (get.getId() == 2) {
                List<CourrierInterne> cr = courrierInterneDaiav();
                List<CourrierInterne> cr1 = courrierInterneDai();
                if (exist(cr, loaded) == 1) {
                    res.add(get);
                }
                if (exist(cr1, loaded) == 1) {
                    res.add(get);
                }
            } else if (get.getId() == 3) {
                List<CourrierInterne> cr = courrierInterneSgav();
                List<CourrierInterne> cr1 = courrierInterneSg();
                if (exist(cr, loaded) == 1) {
                    res.add(get);
                }
                if (exist(cr1, loaded) == 1) {
                    res.add(get);
                }
            } else if (get.getUniteAdministrativePere().getId() == 2) {
                List<CourrierInterne> cr = courrierInterneServiceDai(get);
                if (exist(cr, loaded) == 1) {
                    res.add(get);
                }
            } else if (get.getUniteAdministrativePere().getId() == 3) {
                List<CourrierInterne> cr = courrierInterneDivisionSg(get);
                if (exist(cr, loaded) == 1) {
                    res.add(get);
                }
            }
        }
        List<UniteAdministrative> nv = new ArrayList();
        for (int i = 0; i < res.size(); i++) {
            UniteAdministrative get = res.get(i);
            if (exist(res, get) == 1) {
                nv.add(get);
            }
        }
        return nv;
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

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CourrierInterneFacade() {
        super(CourrierInterne.class);
    }

}
