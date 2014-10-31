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

import com.lewischooman.utils.Utility;
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

@Entity(name="MovieShowDB")
@Table(name="movie_show")
public class MovieShowDB implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="show_date", nullable=false)
    @Temporal(value=TemporalType.DATE)
    private Date showDate;

    @Column(name="show_time", nullable=false)
    @Temporal(value=TemporalType.TIME)
    private Date showTime;

    @Column(name="seats_available", nullable=false)
    private Integer seatsAvailable;

    @Column(name="seats_booked", nullable=false)
    private Integer seatsBooked;

    @Column(name="seats_balance", nullable=false)
    private Integer seatsBalance;

    @Column(name="price", nullable=false, precision=15, scale=2)
    private Double price;

    @Column(name="amount", nullable=false, precision=15, scale=2)
    private Double amount;

    @ManyToOne(fetch=FetchType.EAGER, targetEntity=MovieDB.class)
    @JoinColumn(name="movie_id", referencedColumnName="id")
    private MovieDB movie;

    @ManyToOne(fetch=FetchType.EAGER, targetEntity=HallDB.class)
    @JoinColumn(name="hall_id", referencedColumnName="id")
    private HallDB hall;

    public MovieShowDB() {
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
     * @return the showDate
     */
    public Date getShowDate() {
        return showDate;
    }

    /**
     * @param showDate the show_date to set
     */
    public void setShowDate(Date showDate) {
        this.showDate = showDate;
    }

    /**
     * @return the showTime
     */
    public Date getShowTime() {
        return showTime;
    }

    /**
     * @param showTime the show_time to set
     */
    public void setShowTime(Date showTime) {
        this.showTime = showTime;
    }

    /**
     * @return the seatsAvailable
     */
    public Integer getSeatsAvailable() {
        return seatsAvailable;
    }

    /**
     * @param seatsAvailable the seatsAvailable to set
     */
    public void setSeatsAvailable(Integer seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
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
     * @return the seatsBalance
     */
    public Integer getSeatsBalance() {
        return seatsBalance;
    }

    /**
     * @param seatsBalance the seatsBalance to set
     */
    public void setSeatsBalance(Integer seatsBalance) {
        this.seatsBalance = seatsBalance;
    }

    /**
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @return the amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * @return the movie
     */
    public MovieDB getMovie() {
        return movie;
    }

    /**
     * @param movie the movie to set
     */
    public void setMovie(MovieDB movie) {
        this.movie = movie;
    }

    /**
     * @return the hall
     */
    public HallDB getHall() {
        return hall;
    }

    /**
     * @param hall the hall to set
     */
    public void setHall(HallDB hall) {
        this.hall = hall;
    }

    public boolean isBookable() {
        int compareDate = this.showDate.compareTo(Utility.getCurDate());
        return (this.seatsBalance > 0) && 
               (compareDate > 0 || (compareDate == 0 && this.showTime.compareTo(Utility.getCurTimeAddMinutes(Utility.BOOKING_MINUTES_BEFORE)) > 0));
    }

    public boolean isNotBookable() {
        return !isBookable();
    }

    public String getShowDescription() {
        return Utility.getDateTimeInFormat(this.showDate, "EEE, dd MMM yyyy") + " at " +
               Utility.getDateTimeInFormat(this.showTime, "hh:mm a") + " in " +
               this.hall.getName();
    }
}