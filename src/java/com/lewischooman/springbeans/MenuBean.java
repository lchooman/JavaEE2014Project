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

package com.lewischooman.springbeans;

import com.lewischooman.models.CustomerDB;
import com.lewischooman.models.EmployeeDB;
import com.lewischooman.models.UserDB;
import com.lewischooman.models.WebpageDB;
import com.lewischooman.services.IWebpageSrv;
import com.lewischooman.utils.Utility;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value="menuBean")
@Scope(value="request")
public class MenuBean {
    
    @Autowired
    @Qualifier(value="webpageSrv")
    private IWebpageSrv webpageSrv;

    List<WebpageDB> webpages4Menu;
    
    private String actionName;
    
    public List<WebpageDB> getWebpages4Menu() {
        if (this.webpages4Menu == null) {
            this.webpages4Menu = this.webpageSrv.getWebpages4Menu(Utility.getHttpSession(), Utility.getCurrentViewName());
        }
        return this.webpages4Menu;
    }

    public String getLoggedInUser() {
        UserDB user = this.webpageSrv.getLoggedInUser(Utility.getHttpSession());
        
        if (user == null) {
            return "No login";
        } else if (user instanceof CustomerDB) {
            return "Customer: " + ((CustomerDB) user).getEmailId();
        } else if (user instanceof EmployeeDB) {
            return "Employee: " + ((EmployeeDB) user).getLoginName();
        } else {
            return "";
        }
    }

    public String logout() {
        this.webpageSrv.logout(Utility.getHttpSession());
        this.webpages4Menu = null; // To refresh current menu item in next webpage
        return "home";
    }

    /**
     * @return the actionName
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * @param actionName the actionName to set
     */
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String go() {
        this.webpages4Menu = null; // To refresh current menu item in next webpage
        return this.actionName;
    }
}
