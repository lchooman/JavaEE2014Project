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

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="ShowBookingDB")
@Table(name="show_booking")
public class ShowBookingDB implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="date_booked", nullable=false)
    @Temporal(value=TemporalType.DATE)
    private Date dateBooked;

    @Column(name="seats_booked", nullable=false)
    private Integer seatsBooked;

    @Column(name="card_number", nullable=false, length=19)
    private String cardNumber;

    @Column(name="ref_num", nullable=false)
    private Integer refNum;

    @ManyToOne(fetch=FetchType.EAGER, targetEntity=MovieShowDB.class)
    @JoinColumn(name="show_id", referencedColumnName="id")
    private MovieShowDB movieShow;

    @ManyToOne(fetch=FetchType.EAGER, targetEntity=CustomerDB.class)
    @JoinColumn(name="customer_id", referencedColumnName="id")
    private CustomerDB customer;

    public ShowBookingDB () {
    }
    
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the dateBooked
     */
    public Date getDateBooked() {
        return dateBooked;
    }

    /**
     * @param dateBooked the dateBooked to set
     */
    public void setDateBooked(Date dateBooked) {
        this.dateBooked = dateBooked;
    }

    /**
     * @return the seatsBooked
     */
    public Integer getSeatsBooked() {
        return seatsBooked;
    }

    /**
     * @param seatsBooked the seatsBooked to set
     */
    public void setSeatsBooked(Integer seatsBooked) {
        this.seatsBooked = seatsBooked;
    }

    /**
     * @return the cardNumber
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * @param cardNumber the cardNumber to set
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * @return the refNum
     */
    public Integer getRefNum() {
        return refNum;
    }

    /**
     * @param refNum the refNum to set
     */
    public void setRefNum(Integer refNum) {
        this.refNum = refNum;
    }

    /**
     * @return the movieShow
     */
    public MovieShowDB getMovieShow() {
        return movieShow;
    }

    /**
     * @param movieShow the movieShow to set
     */
    public void setMovieShow(MovieShowDB movieShow) {
        this.movieShow = movieShow;
    }

    /**
     * @return the customer
     */
    public CustomerDB getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(CustomerDB customer) {
        this.customer = customer;
    }
}
