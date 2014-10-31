/***********************************************************************************************************************************
    Customer movie show booking and employee reporting Java EE system, using Spring 3+, Hibernate 4+ and JSF 2.1+
    Copyright (C) 2014 Lewis Tat Fong CHOO MAN

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
************************************************************************************************************************************/

package com.lewischooman.services;

import com.lewischooman.dao.IWebpageDAO;
import com.lewischooman.models.RoleDB;
import com.lewischooman.models.UserDB;
import com.lewischooman.models.WebpageDB;
import com.lewischooman.utils.Utility;
import com.lewischooman.utils.Status;
import com.lewischooman.utils.WebpageStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
// import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value="webpageSrv")
public class WebpageSrv implements IWebpageSrv {
    private final Map<String, Set<RoleDB>> webpages2RolesMap;
    private final Map<String, String> webpages2LoginFlagMap;
    private final IWebpageDAO webpageDAO;

    @Autowired
    public WebpageSrv (@Qualifier(value="webpageDAO") IWebpageDAO webpageDAO) {
        this.webpageDAO = webpageDAO;
        this.webpages2RolesMap = new HashMap<>();
        this.webpages2LoginFlagMap = new HashMap<>();
    }

    /* DOES not work! Transaction not yet created!
    @PostConstruct
    void init() {
        retrieveWebpages2Maps();
    }
    */

    // @Override
    public void retrieveWebpages2Maps() { // Must be public for @Transactional to work!
        List<WebpageDB> webpages = this.webpageDAO.getWebpages();
        this.webpages2RolesMap.clear();
        this.webpages2LoginFlagMap.clear();
        for (WebpageDB webpage : webpages) {
            this.webpages2RolesMap.put(webpage.getName(), webpage.getRoles());
            this.webpages2LoginFlagMap.put(webpage.getName(), webpage.getLoginFlag());
        }
    }

    @Override
    public Status getViewPermissions (String viewName, HttpSession httpSession) {
        UserDB user;
        String loginFlag;
        Set<RoleDB> webpageRoles;
        
        if (this.webpages2LoginFlagMap == null || this.webpages2LoginFlagMap.isEmpty() || this.webpages2RolesMap == null || this.webpages2RolesMap.isEmpty()) {
            retrieveWebpages2Maps();
        }

        // System.out.println("webpages2LoginFlagMap.size: " + webpages2LoginFlagMap.size() +
        //                    ", webpages2RolesMap.size: " + webpages2RolesMap.size());
        if (this.webpages2LoginFlagMap.containsKey(viewName) && this.webpages2RolesMap.containsKey(viewName)) {
            loginFlag = this.webpages2LoginFlagMap.get(viewName);
            webpageRoles = this.webpages2RolesMap.get(viewName);
            if ( loginFlag == null ||
                 ((user = getLoggedInUser(httpSession)) == null && "N".equals(loginFlag)) ||
                 (user != null && "Y".equals(loginFlag) &&
                  (webpageRoles == null || webpageRoles.isEmpty() || Utility.hasOneCommonElem(webpageRoles, user.getRoles())))
               ) {
                return Status.OK; // Access to web page granted because webpage requires no security/login or logged in user has the appropriate roles
            } else if (user == null) {
                return Status.NOT_LOGGED_IN; // Webpage requires login and/or roles access, but no valid user logged in
            } else {
                return Status.INSUFFICIENT_PRIVILEGES; // Webpage requires roles access, but logged in user does not have the appropriate role
            }
        } else {
            return Status.UNKNOWN_REQUEST; // Unknown view name
        }
    }

    @Override
    public List<WebpageDB> getWebpages4Menu(HttpSession httpSession, String currentViewName) {

        String loginFlag = (getLoggedInUser(httpSession) == null ? "N" : "Y");
        List<WebpageDB> webpages = this.webpageDAO.getWebpages4Menu(loginFlag);

        if ("Y".equals(loginFlag)) {
            List<WebpageDB> filteredWebpages = new ArrayList<>();
            for (WebpageDB webpage : webpages) {
                if (getViewPermissions(webpage.getName(), httpSession) == Status.OK) {
                    if (webpage.getName().equals(currentViewName)) {
                        webpage.setWebpageStatus(WebpageStatus.CURRENTVIEW);
                    }
                    filteredWebpages.add(webpage);
                }
            }
            /* Adding logout link */
            WebpageDB webpage = new WebpageDB();
            webpage.setName("home");
            webpage.setDescription("Logout");
            webpage.setLoginFlag("Y");
            webpage.setMenuFlag("Y");
            webpage.setMenuOrder(Integer.MAX_VALUE);
            webpage.setWebpageStatus(WebpageStatus.LOGOUT);
            filteredWebpages.add(webpage);
            return filteredWebpages;
        } else {
            for (WebpageDB webpage : webpages) {
                if (webpage.getName().equals(currentViewName)) {
                    webpage.setWebpageStatus(WebpageStatus.CURRENTVIEW);
                }
            }
            return webpages;
        }
    }

    @Override
    public UserDB getLoggedInUser(HttpSession httpSession) {
        return (UserDB) httpSession.getAttribute(Utility.LOGGED_IN_USER_ATTRIBUTE);
    }

    @Override
    public void logout(HttpSession httpSession) {
        httpSession.removeAttribute(Utility.LOGGED_IN_USER_ATTRIBUTE);
    }
}
