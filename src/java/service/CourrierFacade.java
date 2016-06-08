/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author asus
 */
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

    public List<Courrier> courrierValideByCabinet() {
        String req = "SELECT co FROM Courrier co WHERE co.validationCabinet = 1";
        return em.createQuery(req).getResultList();
    }

    public List<Courrier> courrierCabinet() {
        String req = "SELECT co FROM Courrier co WHERE co.validationCabinet = 0";
        return em.createQuery(req).getResultList();
    }

    public List<Courrier> courrierSg() {
        List<Courrier> loaded = courrierValideByCabinet();
        List<Courrier> res = new ArrayList<>();
        for (int i = 0; i < loaded.size(); i++) {
            Courrier get = loaded.get(i);
            for (int j = 0; j < get.getUniteAdministratives().size(); j++) {
                UniteAdministrative get1 = get.getUniteAdministratives().get(j);
                if ((get1.getUniteAdministrativePere().getId() == 3 || get1.getId() == 3) && get.isValidationSg() == false) {
                    res.add(get);
                    j = get.getUniteAdministratives().size();
                }
            }
        }
        return res;
    }

    public List<Courrier> courrierDai() {
        List<Courrier> loaded = courrierValideByCabinet();
        List<Courrier> res = new ArrayList<>();
        for (int i = 0; i < loaded.size(); i++) {
            Courrier get = loaded.get(i);
            for (int j = 0; j < get.getUniteAdministratives().size(); j++) {
                UniteAdministrative get1 = get.getUniteAdministratives().get(j);
                if ((get1.getUniteAdministrativePere().getId() == 2 || get1.getId() == 2) && get.isValidationDai() == false) {
                    res.add(get);
                    j = get.getUniteAdministratives().size();
                }
            }
        }
        return res;
    }

    public List<Courrier> getItems(List<Courrier> items) {
        User user = SessionUtil.getConnectedUser();
        if (items == null) {
            if (user.getUniteAdministrative().getId() == 1) {
                items = courrierCabinet();
            } else if (user.getUniteAdministrative().getId() == 2) {
                items = courrierDai();
            } else if (user.getUniteAdministrative().getId() == 3) {
                items = courrierSg();
            } else {
                items = findAll();
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

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CourrierFacade() {
        super(Courrier.class);
    }

}
