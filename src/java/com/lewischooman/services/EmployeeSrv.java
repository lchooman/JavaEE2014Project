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

import com.lewischooman.dao.IEmployeeDAO;
import com.lewischooman.models.EmployeeDB;
import com.lewischooman.utils.IDigester;
import com.lewischooman.utils.Status;
import com.lewischooman.utils.Utility;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value="employeeSrv")
public class EmployeeSrv implements IUserSrv {
    @Autowired
    @Qualifier(value="employeeDAO")
    private IEmployeeDAO employeeDAO;

    @Autowired
    @Qualifier(value="digester")
    private IDigester digester;

    @Override
    public boolean login(HttpSession httpSession, String uid, String password) {
        EmployeeDB employee = this.employeeDAO.getUserByLoginName(uid);
        if (employee != null && this.digester.digestToHex(password).equals(employee.getPassword())) {
            httpSession.setAttribute(Utility.LOGGED_IN_USER_ATTRIBUTE, employee);
            return true;
        } else {
            httpSession.removeAttribute(Utility.LOGGED_IN_USER_ATTRIBUTE);
            return false;
        }
    }

    @Override
    public Status register(Integer[] id, String longName, String password, String ... othFlds) {
        return Status.OK;
    }

    @Override
    public boolean alreadyExists(String uid) {
        EmployeeDB employee = this.employeeDAO.getUserByLoginName(uid);
        return (employee != null);
    }
}