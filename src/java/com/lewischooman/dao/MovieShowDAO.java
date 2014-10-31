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

package com.lewischooman.dao;

import com.lewischooman.models.MovieShowDB;
import com.lewischooman.utils.Status;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository(value="movieShowDAO")
public class MovieShowDAO implements IMovieShowDAO {

    @Autowired
    @Qualifier(value="sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings(value="unchecked")
    public List<MovieShowDB> getMovieShowsByDateTheater(Date dateFrom, Date dateTo, Integer theaterId, boolean breakByTheater, Integer movieId) {
        Session session;
        Criteria criteria;

        session = this.sessionFactory.getCurrentSession();
        criteria = session.createCriteria(MovieShowDB.class, "shw");
        if (dateFrom != null) {
            criteria.add(Restrictions.ge("shw.showDate", dateFrom));
        }
        if (dateTo != null) {
            criteria.add(Restrictions.le("shw.showDate", dateTo));
        }
        criteria.createAlias("shw.movie", "mov", JoinType.INNER_JOIN).
                     createAlias("shw.hall", "hal", JoinType.INNER_JOIN);
        if (movieId != null) {
            criteria.add(Restrictions.eq("mov.id", movieId));
        }
        if (breakByTheater) {
            criteria.createAlias("hal.theater", "the", JoinType.INNER_JOIN);
            if (theaterId != null) {
                criteria.add(Restrictions.eq("the.id", theaterId));
            }
            criteria.addOrder(Order.asc("the.name"));
        }
        criteria.addOrder(Order.asc("mov.title")).
                 addOrder(Order.asc("shw.showDate")).
                 addOrder(Order.asc("shw.showTime"));
        
        return (List<MovieShowDB>) criteria.list();
    }

    @Override
    public MovieShowDB getMovieShowById(Integer movieShowId) {
        return this.getMovieShowById(movieShowId, LockOptions.NONE);
    }

    @Override
    public MovieShowDB getMovieShowById(Integer movieShowId, LockOptions lockOption) {
        Session session;
        session = this.sessionFactory.getCurrentSession();
        return (MovieShowDB) session.get(MovieShowDB.class, movieShowId, lockOption);
    }

    @Override
    public Status updateMovieShowWithBooking(MovieShowDB [] movieShowArr, Integer seatsBooked) {
        Session session;
        Integer showSeatsAvailable, showSeatsBooked;

        session = this.sessionFactory.getCurrentSession();
        if (session.contains(movieShowArr[0])) {
            session.evict(movieShowArr[0]);
        }
        movieShowArr[0] = this.getMovieShowById(movieShowArr[0].getId(), LockOptions.UPGRADE);
        showSeatsAvailable = movieShowArr[0].getSeatsAvailable();
        showSeatsBooked = movieShowArr[0].getSeatsBooked();
        if (showSeatsAvailable - showSeatsBooked >= seatsBooked) {
            showSeatsBooked += seatsBooked;
            movieShowArr[0].setSeatsBooked(showSeatsBooked);
            movieShowArr[0].setSeatsBalance(showSeatsAvailable - showSeatsBooked);
            movieShowArr[0].setAmount(movieShowArr[0].getPrice() * showSeatsBooked);

            return Status.OK;
        } else {
            return Status.INSUFFICIENT_SEATS;
        }
    }
}