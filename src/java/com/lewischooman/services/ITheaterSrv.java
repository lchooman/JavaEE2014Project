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

import com.lewischooman.models.TheaterDB;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface ITheaterSrv {

    @Transactional(value="txManager", propagation=Propagation.REQUIRED)
    List<TheaterDB> getTheaters();

    @Transactional(value="txManager", propagation=Propagation.REQUIRED)
    List<TheaterDB> getTheatersWithHalls();

    @Transactional(value="txManager", propagation=Propagation.REQUIRED)
    List<TheaterDB> getTheaterSelectItems();
}
