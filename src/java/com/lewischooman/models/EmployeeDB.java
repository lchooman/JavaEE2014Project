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

package com.lewischooman.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity(name="EmployeeDB")
@Table(name="employee")
@PrimaryKeyJoinColumn(name="employee_id", referencedColumnName="id")
public class EmployeeDB extends UserDB {

    @Column(name="login_name", nullable=false, length=30)
    private String loginName;

    public EmployeeDB() {
    }

    public String getLoginName() {
        return this.loginName;
    }
    
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
