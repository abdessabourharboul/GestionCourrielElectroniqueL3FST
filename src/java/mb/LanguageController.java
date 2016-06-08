package mb;

import bean.User;
import controller.util.SessionUtil;
import java.io.Serializable;
import java.util.Locale;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import service.UserFacade;

@ManagedBean(name = "languageCTR")
@SessionScoped
public class LanguageController implements Serializable {

    @EJB
    private UserFacade userFacade;
    private User user = SessionUtil.getConnectedUser();
    private String language = user.getLanguage();

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String actionLanguage(String lang) {
        if (lang.equals("ar")) {
            FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("ar"));
        } else {
            FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("fr"));
        }
        setLanguage(lang);
        user.setLanguage(lang);
        userFacade.edit(user);
        return null;
    }
}
