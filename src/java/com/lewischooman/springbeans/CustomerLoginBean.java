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

import com.lewischooman.services.IUserSrv;
import com.lewischooman.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value="customerLoginBean")
@Scope(value="request")
public class CustomerLoginBean {

    @Autowired
    @Qualifier(value="customerSrv")
    private IUserSrv customerSrv;

    private String emailId;

    private String password;

    private String loginMsg;
    
    public String getEmailId() {
        return this.emailId ;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return "";
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginMsg() {
        return this.loginMsg;
    }

    public String login() {
        boolean loginOK;
        loginOK = this.customerSrv.login(Utility.getHttpSession(), this.emailId, this.password);
        if (loginOK) {
            this.loginMsg = "";
            return "listMovies";
        } else {
            this.loginMsg = "Invalid Credentials";
            return "";
        }
    }
}