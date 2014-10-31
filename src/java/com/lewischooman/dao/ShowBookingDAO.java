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

import com.lewischooman.models.CustomerDB;
import com.lewischooman.models.MovieShowDB;
import com.lewischooman.models.ShowBookingDB;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository(value="showBookingDAO")
public class ShowBookingDAO implements IShowBookingDAO {

    @Autowired
    @Qualifier(value="sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public ShowBookingDB saveShowBooking(MovieShowDB movieShow, Date dateBooked, Integer seatsBooked, String cardNumber, CustomerDB customer, int refNum) {
        Session session;
        ShowBookingDB showBooking;

        session = this.sessionFactory.getCurrentSession();
        showBooking = new ShowBookingDB();
        showBooking.setDateBooked(dateBooked);
        showBooking.setCardNumber(cardNumber);
        showBooking.setSeatsBooked(seatsBooked);
        showBooking.setMovieShow(movieShow);
        showBooking.setCustomer(customer);
        showBooking.setRefNum(refNum);

        session.persist(showBooking);

        return showBooking;
    }
}
