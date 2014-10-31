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


import com.lewischooman.models.TheaterDB;
import com.lewischooman.services.ITheaterSrv;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

@Component(value="aboutUsBean")
@Scope(value="request")
public class AboutUsBean {

    @Autowired
    @Qualifier(value="theaterSrv")
    private ITheaterSrv theaterSrv;

    private List<TheaterDB> theaters;

    public List<TheaterDB> getTheaters() {
        if (this.theaters == null) {
            this.theaters = this.theaterSrv.getTheatersWithHalls();
        }
        return this.theaters;
    }

    public int getNumTheaters() {
        return getTheaters().size();
    }
}
