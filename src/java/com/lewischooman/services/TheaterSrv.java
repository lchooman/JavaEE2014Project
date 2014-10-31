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

import com.lewischooman.dao.IHallDAO;
import com.lewischooman.dao.ITheaterDAO;
import com.lewischooman.models.TheaterDB;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value="theaterSrv")
public class TheaterSrv implements ITheaterSrv {

    @Autowired
    @Qualifier(value="theaterDAO")
    private ITheaterDAO theaterDAO;

    @Autowired
    @Qualifier(value="hallDAO")
    private IHallDAO hallDAO;

    @Override
    public List<TheaterDB> getTheaters() {
        return this.theaterDAO.getTheaters();
    }

    @Override
    public List<TheaterDB> getTheatersWithHalls() {
        List<TheaterDB> theaters = this.theaterDAO.getTheaters();
        for (TheaterDB theater : theaters) {
            theater.setHalls(this.hallDAO.getHallsByTheater(theater.getId()));
        }
        return theaters;
    }

    @Override
    public List<TheaterDB> getTheaterSelectItems() {
        List<TheaterDB> theaters = this.theaterDAO.getTheaters();
        List<TheaterDB> theaterSelectItems = new ArrayList<>();
        TheaterDB theater = new TheaterDB();
        theater.setId(-1);
        theater.setName("<All Theaters>");
        theater.setAddress(null);
        theater.setEmailId(null);
        theater.setFax(null);
        theater.setTel(null);
        theaterSelectItems.add(theater);
        theaterSelectItems.addAll(theaters);
        return theaterSelectItems;
    }
}
