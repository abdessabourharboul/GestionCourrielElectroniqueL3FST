/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

/**
 *
 * @author asus
 */
import bean.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilterCabinet implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Get the loginBean from session attribute
        User user = (User) ((HttpServletRequest) request).getSession().getAttribute("user");

//         For the first application request there is no loginBean in the session so user needs to log in
//         For other requests loginBean is present but we need to check if user has logged in successfully
        if (user == null || user.isLogged() == false) {
            String contextPath = ((HttpServletRequest) request).getContextPath();
            ((HttpServletResponse) response).sendRedirect(contextPath + "/index.xhtml");
        } 
//        else if (user.getRole().equals("cabinet")) {
            chain.doFilter(request, response);
//        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        // Nothing to do here!
    }

    @Override
    public void destroy() {
        // Nothing to do here!
    }
}
