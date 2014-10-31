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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name="TheaterDB")
@Table(name="theater")
public class TheaterDB implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Integer id;
    
    @Column(name="name", nullable=false, length=50)
    private String name;
    
    @Column(name="address", nullable=false, length=100)
    private String address;
    
    @Column(name="tel", nullable=false, length=15)
    private String tel;
    
    @Column(name="fax", nullable=false, length=15)
    private String fax;
    
    @Column(name="email_id", nullable=false, length=50)
    private String emailId;

    @Transient
    private List<HallDB> halls;

    @Transient
    private List<MovieDB> movies;

    @Transient
    private List<MovieDB> unmodifiableMovies;

    @Transient
    private Integer seatsBooked;

    @Transient
    private Double avgPrice;

    @Transient
    private Double amount;

    public TheaterDB() {
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @return the emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * @param emailId the emailId to set
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /**
     * @return the halls
     */
    public List<HallDB> getHalls() {
        return halls;
    }

    /**
     * @param halls the halls to set
     */
    public void setHalls(List<HallDB> halls) {
        this.halls = halls;
    }

    private void initMovies() {
        if (this.movies == null) {
            this.movies = new ArrayList<>();
            this.unmodifiableMovies = Collections.unmodifiableList(this.movies);
        }
    }

    public List<MovieDB> getMovies() {
        initMovies();
        return this.unmodifiableMovies;
    }
    
    public void clearMovies() {
        initMovies();
        this.seatsBooked = null;
        this.avgPrice = null;
        this.amount = null;
        this.movies.clear();
    }

    public void addMovie(MovieDB movie) {
        initMovies();
        this.seatsBooked = null;
        this.avgPrice = null;
        this.amount = null;
        this.movies.add(movie);
    }

    public Integer getSeatsBooked() {
        if (this.movies != null && this.seatsBooked == null) {
            int sumSeatsBooked = 0;
            for (MovieDB movie : this.movies) {
                sumSeatsBooked += movie.getSeatsBooked();
            }
            this.seatsBooked = sumSeatsBooked;
        }
        return this.seatsBooked;
    }

    public Double getAmount() {
        if (this.movies != null && this.amount == null) {
            double sumAmount = 0;
            for (MovieDB movie : this.movies) {
                sumAmount += movie.getAmount();
            }
            this.amount = sumAmount;
        }
        return this.amount;
    }

    public Double getAvgPrice() {
        if (this.avgPrice == null) {
            int sumSeatsBooked;
            if ((sumSeatsBooked = getSeatsBooked()) == 0) {
                this.avgPrice = getAmount() / sumSeatsBooked;
            } else {
                this.avgPrice = 0.0;
            }
        }
        return this.avgPrice;
    }

    public boolean isMoreThanOneMovie() {
        return (this.movies != null && this.movies.size() > 1);
    }

    public String getFooterClass() {
        return (this.movies != null && this.movies.size() > 1 ? "movieShowFooter" : "hide");
    }

    public int getNumberOfHalls() {
        return (this.halls != null ? this.halls.size() : 0);
    }

    public int getTotalHallSeats() {
        int seatsAvailable = 0;
        if (this.halls != null) {
            for (HallDB hall : this.halls) {
                seatsAvailable += hall.getSeatsAvailable();
            }
        }
        return seatsAvailable;
    }
}