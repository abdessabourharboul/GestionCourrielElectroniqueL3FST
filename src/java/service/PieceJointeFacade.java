/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Courrier;
import bean.PieceJointe;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author asus
 */
@Stateless
public class PieceJointeFacade extends AbstractFacade<PieceJointe> {

    @PersistenceContext(unitName = "ProjetFinEtudesPU")
    private EntityManager em;

    public PieceJointe clone(PieceJointe pieceJointe) {
        PieceJointe Item = new PieceJointe();
        Item.setLogo(pieceJointe.getLogo());
        return Item;
    }

    public List<PieceJointe> findPieceJointesByCourrier(Courrier courrier) {
        String req = "SELECT pi FROM PieceJointe pi WHERE 1=1 AND pi.courrier.id ='" + courrier.getId() + "'";
        return em.createQuery(req).getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PieceJointeFacade() {
        super(PieceJointe.class);
    }

}
