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

package com.lewischooman.controllers;

import com.lewischooman.services.IWebpageSrv;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;

@Controller
public class IndexController { // By default model is request scoped unless @SessionAttributes annotation is applied to class

    @Autowired
    @Qualifier(value="webpageSrv")
    private IWebpageSrv webpageSrv;

    @RequestMapping(value="/{viewName}.htm", method={RequestMethod.GET, RequestMethod.POST})
    public String getView(@PathVariable(value="viewName") String viewName, HttpSession httpSession, Model model) {

        switch (this.webpageSrv.getViewPermissions(viewName, httpSession)) {
            case OK:
                model.addAttribute("msg", "");
                return viewName;
            case NOT_LOGGED_IN:
                model.addAttribute("msg", "Login required and you are not yet logged in. Please login.");
                return "messages";
            case INSUFFICIENT_PRIVILEGES:
                model.addAttribute("msg", "Insufficient privileges. Please re-login as another user with sufficient privileges.");
                return "messages";
            case UNKNOWN_REQUEST:
                model.addAttribute("msg", "Unknown request. For security reasons, please do not try to access unknown webpages.");
                return "messages";
            default:
                model.addAttribute("msg", "Unknown error.");
                return "messages";
        }
    }
}
