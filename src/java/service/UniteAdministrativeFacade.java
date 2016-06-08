/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.UniteAdministrative;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author asus
 */
@Stateless
public class UniteAdministrativeFacade extends AbstractFacade<UniteAdministrative> {

    @PersistenceContext(unitName = "ProjetFinEtudesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UniteAdministrativeFacade() {
        super(UniteAdministrative.class);
    }
    
}
