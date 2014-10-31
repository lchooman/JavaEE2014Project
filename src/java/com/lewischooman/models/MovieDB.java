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
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name = "MovieDB")
@Table(name = "movie")
public class MovieDB implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "image_file", nullable = true, length = 300)
    private String imageFile;

    @Transient
    private List<MovieShowDB> movieShows;

    @Transient
    private Integer seatsBooked;

    @Transient
    private Double avgPrice;

    @Transient
    private Double amount;

    public MovieDB() {
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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the imageFile
     */
    public String getImageFile() {
        return imageFile;
    }

    /**
     * @param imageFile the imageFile to set
     */
    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getImageUrl() {
        return this.imageFile;
    }

    /**
     * @return the movieShows
     */
    public List<MovieShowDB> getMovieShows() {
        return movieShows;
    }

    /**
     * @param movieShows the movieShows to set
     */
    public void setMovieShows(List<MovieShowDB> movieShows) {
        this.seatsBooked = null;
        this.avgPrice = null;
        this.amount = null;
        this.movieShows = Collections.unmodifiableList(movieShows);
    }

    public Integer getSeatsBooked() {
        if (this.movieShows != null && this.seatsBooked == null) {
            int sumSeatsBooked = 0;
            for (MovieShowDB movieShow : this.movieShows) {
                sumSeatsBooked += movieShow.getSeatsBooked();
            }
            this.seatsBooked = sumSeatsBooked;
        }
        return this.seatsBooked;
    }

    public Double getAmount() {
        if (this.movieShows != null && this.amount == null) {
            double sumAmount = 0;
            for (MovieShowDB movieShow : this.movieShows) {
                sumAmount += movieShow.getAmount();
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

    public boolean isMoreThanOneMovieShow() {
        return (this.movieShows != null && this.movieShows.size() > 1);
    }

    public String getFooterClass() {
        return (this.movieShows != null && this.movieShows.size() > 1 ? "movieShowFooter" : "hide");
    }
}