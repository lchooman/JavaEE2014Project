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


import com.lewischooman.models.MovieDB;
import com.lewischooman.services.IMovieShowSrv;
import com.lewischooman.utils.Utility;
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

@Component(value="homeBean")
@Scope(value="request")
public class HomeBean implements java.io.Serializable {

    @Autowired
    @Qualifier(value="movieShowSrv")
    private IMovieShowSrv movieShowSrv;

    private List<MovieDB> movies;

    public List<MovieDB> getMovies() {
        if (this.movies == null) {
            this.movies = this.movieShowSrv.getMoviesByDate(Utility.getCurDate(), null);
        }
        return this.movies;
    }
}
