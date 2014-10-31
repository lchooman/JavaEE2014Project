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
import com.lewischooman.models.MovieShowDB;
import com.lewischooman.services.IMovieShowSrv;
import com.lewischooman.utils.Utility;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component(value="showCollectionReportBean")
@Scope(value="request")
public class ShowCollectionReportBean {
    
    private static final String SHOW_COLLECTION_REPORT_START_DATE_ATTRIBUTE = "SHOW_COLLECTION_REPORT_START_DATE_ATTRIBUTE";
    private static final String SHOW_COLLECTION_REPORT_END_DATE_ATTRIBUTE = "SHOW_COLLECTION_REPORT_END_DATE_ATTRIBUTE";
    private static final String SHOW_COLLECTION_REPORT_MOVIES_ATTRIBUTE = "SHOW_COLLECTION_REPORT_MOVIES_ATTRIBUTE";
    private static final String SHOW_COLLECTION_REPORT_MOVIE_ATTRIBUTE = "SHOW_COLLECTION_REPORT_MOVIE_ATTRIBUTE";
    private static final String SHOW_COLLECTION_REPORT_MOVIE_SHOW_ATTRIBUTE = "SHOW_COLLECTION_REPORT_MOVIE_SHOW_ATTRIBUTE";
    

    @Autowired
    @Qualifier(value="movieShowSrv")
    private IMovieShowSrv movieShowSrv;

    private Date startDate;

    private Date endDate;

    private Integer movieId;
    
    private Integer movieShowId;

    private MovieDB movie;
    
    private MovieShowDB movieShow;

    private List<MovieDB> movies;
    
    private UIInput startDateUI;

    private Integer seatsBooked;

    private Double avgPrice;

    private Double amount;

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
        Utility.setSessionAttribute(SHOW_COLLECTION_REPORT_START_DATE_ATTRIBUTE, this.startDate);
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
        Utility.setSessionAttribute(SHOW_COLLECTION_REPORT_END_DATE_ATTRIBUTE, this.endDate);
    }

    /**
     * @return the startDateUI
     */
    public UIInput getStartDateUI() {
        return startDateUI;
    }

    /**
     * @param startDateUI the startDateUI to set
     */
    public void setStartDateUI(UIInput startDateUI) {
        this.startDateUI = startDateUI;
    }

    public String getMovieTitle() {
        if (this.startDate != null) {
            if (this.endDate != null) {
                return "Show Collection Report between " + Utility.getDateTimeInFormat(this.startDate, "EEE, dd/MM/yyyy") +
                       " and " + Utility.getDateTimeInFormat(this.endDate, "EEE, dd/MM/yyyy");
            } else {
                return "Show Collection Report from " + Utility.getDateTimeInFormat(this.startDate, "EEE, dd/MM/yyyy");
            }
        } else {
            return "Show Collection Report";
        }
    }

    /**
     * @return the movie
     */
    public MovieDB getMovie() {
        if (this.movie == null) {
            if (this.movieId == null) {
                return (MovieDB) Utility.getSessionAttribute(SHOW_COLLECTION_REPORT_MOVIE_ATTRIBUTE);
            } else {
                Utility.setSessionAttribute(SHOW_COLLECTION_REPORT_MOVIE_ATTRIBUTE, this.movie = this.movieShowSrv.getMovieByDateMovieId(this.startDate, this.endDate, this.movieId));
            }
        }
        return this.movie;
    }

    /**
     * @return the movieId
     */
    public Integer getMovieId() {
        return this.movieId;
    }

    /**
     * @param movieId the movieId to set
     */
    public void setMovieId(Integer movieId) {
        // System.out.println("setMovieId: " + movieId);
        this.movieId = movieId;
    }

    /**
     * @return the movieShow
     */
    public MovieShowDB getMovieShow() {
        if (this.movieShow == null) {
            if (this.movieShowId == null) {
                return (MovieShowDB) Utility.getSessionAttribute(SHOW_COLLECTION_REPORT_MOVIE_SHOW_ATTRIBUTE);
            } else {
                Utility.setSessionAttribute(SHOW_COLLECTION_REPORT_MOVIE_SHOW_ATTRIBUTE, this.movieShow = this.movieShowSrv.getMovieShowById(this.movieShowId));
            }
        }
        return this.movieShow;
    }

    /**
     * @return the movieShowId
     */
    public Integer getMovieShowId() {
        return this.movieShowId;
    }

    /**
     * @param movieShowId the movieShowId to set
     */
    public void setMovieShowId(Integer movieShowId) {
        // System.out.println("setMovieShowId: " + movieShowId);
        this.movieShowId = movieShowId;
        Utility.setSessionAttribute(SHOW_COLLECTION_REPORT_MOVIE_SHOW_ATTRIBUTE, this.movieShow = this.movieShowSrv.getMovieShowById(this.movieShowId));
    }

    private void setMovies() {
        if (this.startDate != null) {
            this.movies = this.movieShowSrv.getMoviesByDate(this.startDate, this.endDate);
            Utility.setSessionAttribute(SHOW_COLLECTION_REPORT_MOVIES_ATTRIBUTE, this.movies);
        }
    }

    public String go() {
        setMovies();
        return "";
    }

    public String go2MovieShows() {
        this.movie = getMovie();
        return "showCollectionShowsReport";
    }

    public String go2MovieShow() {
        this.movieShow = getMovieShow();
        return "showCollectionShowReport";
    }

    public void preRender(ComponentSystemEvent evt) {
        if (this.startDate == null) {
            Date curDate = Utility.getCurDate();
            this.startDate = (Date) Utility.getSessionAttribute(SHOW_COLLECTION_REPORT_START_DATE_ATTRIBUTE);
            if (this.startDate == null) {
                setStartDate(curDate);
            }
        }
        if (this.endDate == null) {
            this.endDate = (Date) Utility.getSessionAttribute(SHOW_COLLECTION_REPORT_END_DATE_ATTRIBUTE);
        }
    }

    @SuppressWarnings(value="unchecked")
    public List<MovieDB> getMoviesByDate() {
        // System.out.println("getMoviesByTheater -> Start Date: " + this.startDate + ", daysOffset: " + this.daysOffset +
        //                    ", theaterID: " + this.theaterId + ", this.movies: " + (this.movies == null ? "IS NULL" : "NOT NULL"));
        if (this.movies == null) {
            if (this.startDate == null) {
                return (List<MovieDB>) Utility.getSessionAttribute(SHOW_COLLECTION_REPORT_MOVIES_ATTRIBUTE);
            } else {
                setMovies();
            }
        }
        return this.movies;
    }

    public void validateEndDate(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
        UIInput stDateUI = (UIInput) component.getAttributes().get("startDateUI");
        if (stDateUI != null) {
            if (stDateUI.isValid() && ((Date) stDateUI.getValue()).compareTo((Date) value) > 0) {
                throw new ValidatorException(Utility.getFacesMessage("End date cannot be earlier than start date", FacesMessage.SEVERITY_ERROR));
            }
        } else {
            throw new ValidatorException(Utility.getFacesMessage("Start date not available for validation", FacesMessage.SEVERITY_ERROR));
        }
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
        if (this.amount == null) {
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

    public String getSummary() {
        if (this.movies == null || this.movies.isEmpty()) {
            return "No show collection report for the selected query";
        } else {
            return "";
        }
    }

    public boolean isMoreThanOneMovie() {
        return (this.movies != null && this.movies.size() > 1);
    }

    public String getFooterClass() {
        return (this.movies == null || this.movies.isEmpty() ? "showCollectionFooter" : (this.movies.size() > 1 ? "showCollectionFooterTotal" : "hide"));
    }
}