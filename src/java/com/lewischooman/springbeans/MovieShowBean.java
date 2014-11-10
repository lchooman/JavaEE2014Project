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

import com.lewischooman.models.CustomerDB;
import com.lewischooman.models.MovieShowDB;
import com.lewischooman.models.ShowBookingDB;
import com.lewischooman.models.TheaterDB;
import com.lewischooman.services.IMovieShowSrv;
import com.lewischooman.services.ITheaterSrv;
import com.lewischooman.services.IWebpageSrv;
import com.lewischooman.utils.Status;
import com.lewischooman.utils.Utility;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component(value="movieShowBean")
@Scope(value="request")
public class MovieShowBean {
    
    private static final List<Integer> DAYS_OFFSET;
    private static final String LIST_MOVIES_START_DATE_ATTRIBUTE = "LIST_MOVIES_START_DATE_ATTRIBUTE";
    private static final String LIST_MOVIES_DAYS_OFFSET_ATTRIBUTE = "LIST_MOVIES_DAYS_OFFSET_ATTRIBUTE";
    private static final String LIST_MOVIES_THEATER_ID_ATTRIBUTE = "LIST_MOVIES_THEATER_ID_ATTRIBUTE";
    private static final String LIST_MOVIES_THEATERS_ATTRIBUTE = "LIST_MOVIES_THEATERS_ATTRIBUTE";
    private static final String LIST_MOVIES_MOVIE_SHOW_ATTRIBUTE = "LIST_MOVIES_MOVIE_SHOW_ATTRIBUTE";
    
    static {
        DAYS_OFFSET = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            DAYS_OFFSET.add(i);
        }
    }
    
    @Autowired
    @Qualifier(value="movieShowSrv")
    private IMovieShowSrv movieShowSrv;

    @Autowired
    @Qualifier(value="theaterSrv")
    private ITheaterSrv theaterSrv;

    @Autowired
    @Qualifier(value="webpageSrv")
    private IWebpageSrv webpageSrv;

    private Date startDate;

    private Integer daysOffset;

    private Integer theaterId;
    
    private Integer movieShowId;

    private String bookShowMsg;

    private Integer seatsToBook;
    
    private String cardNumber;

    private Integer referenceNumber;
    
    private MovieShowDB movieShow;

    private List<TheaterDB> theaters;

    private List<TheaterDB> theaterSelectItems;

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
        Utility.setSessionAttribute(LIST_MOVIES_START_DATE_ATTRIBUTE, this.startDate);
    }

    public String getMovieShowTitle() {
        if (this.startDate != null) {
            return "Movie Shows between " + Utility.getDateTimeInFormat(this.startDate, "EEE, dd/MM/yyyy") +
                   " and " + Utility.getDateTimeInFormat(Utility.addToDate(this.startDate, (this.daysOffset == null ? 0 : this.daysOffset)), "EEE, dd/MM/yyyy");
        } else {
            return "Movie Shows";
        }
    }
    
    /**
     * @return the daysOffset
     */
    public Integer getDaysOffset() {
        return daysOffset;
    }

    /**
     * @param daysOffset the daysOffset to set
     */
    public void setDaysOffset(Integer daysOffset) {
        this.daysOffset = daysOffset;
        Utility.setSessionAttribute(LIST_MOVIES_DAYS_OFFSET_ATTRIBUTE, this.daysOffset);
    }

    /**
     * @return the theaterId
     */
    public Integer getTheaterId() {
        return theaterId;
    }

    /**
     * @param theaterId the theaterId to set
     */
    public void setTheaterId(Integer theaterId) {
        this.theaterId = theaterId;
        Utility.setSessionAttribute(LIST_MOVIES_THEATER_ID_ATTRIBUTE, this.theaterId);
    }

    /**
     * @return the bookShowMsg
     */
    public String getBookShowMsg() {
        return bookShowMsg;
    }

    /**
     * @param bookShowMsg the bookShowMsg to set
     */
    public void setBookShowMsg(String bookShowMsg) {
        this.bookShowMsg = bookShowMsg;
    }

    /**
     * @return the seatsToBook
     */
    public Integer getSeatsToBook() {
        return seatsToBook;
    }

    /**
     * @param seatsToBook the seatsToBook to set
     */
    public void setSeatsToBook(Integer seatsToBook) {
        this.seatsToBook = seatsToBook;
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
     * @return the referenceNumber
     */
    public Integer getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * @param referenceNumber the referenceNumber to set
     */
    public void setReferenceNumber(Integer referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
    
    public Double getAmountBooked() {
        Double price;
        MovieShowDB mvShow;
        // System.out.println("this.movieShowId: " + this.movieShowId + ", getMovieShow() : " + (getMovieShow() == null ? "IS NULL" : "NOT NULL"));
        if (this.movieShowId != null && (mvShow = getMovieShow()) != null && (price = mvShow.getPrice()) != null && this.seatsToBook != null) {
            return price * this.seatsToBook;
        } else {
            return null;
        }
    }

    /**
     * @return the movieShow
     */
    public MovieShowDB getMovieShow() {
        if (this.movieShow == null) {
            if (this.movieShowId == null) {
                return (MovieShowDB) Utility.getSessionAttribute(LIST_MOVIES_MOVIE_SHOW_ATTRIBUTE);
            } else {
                Utility.setSessionAttribute(LIST_MOVIES_MOVIE_SHOW_ATTRIBUTE, this.movieShow = this.movieShowSrv.getMovieShowById(this.movieShowId));
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
        Utility.setSessionAttribute(LIST_MOVIES_MOVIE_SHOW_ATTRIBUTE, this.movieShow = this.movieShowSrv.getMovieShowById(this.movieShowId));
    }
    
    public List<TheaterDB> getTheaterSelectItems() {
        if (this.theaterSelectItems == null) {
            this.theaterSelectItems = this.theaterSrv.getTheaterSelectItems();
        }
        return this.theaterSelectItems;
    }
    
    private void setTheaters() {
        if (this.startDate != null) {
            this.theaters = this.movieShowSrv.getMovieShowsByDateTheater(this.startDate, (this.daysOffset == null ? 0 : this.daysOffset), (this.theaterId == null || this.theaterId == -1 ? null : this.theaterId));
            Utility.setSessionAttribute(LIST_MOVIES_THEATERS_ATTRIBUTE, this.theaters);
        }
    }

    public List<Integer> getDaysOffsets() {
        return DAYS_OFFSET;
    }

    public String go() {
        setTheaters();
        return "";
    }

    public String book() {
        this.movieShow = getMovieShow();
        // System.out.println("book(): " + this.startDate + ", daysOffset: " + this.daysOffset + ", theaterID: " + this.theaterId);
        setTheaters();
        return "bookShow";
    }

    public String bookSeats4Show() {
        
        if (Utility.getCurDate().compareTo(this.getMovieShow().getShowDate()) > 0 ||
            ( Utility.getCurDate().compareTo(this.getMovieShow().getShowDate()) == 0 &&
              Utility.getCurTimeAddMinutes(Utility.BOOKING_MINUTES_BEFORE).compareTo(this.getMovieShow().getShowTime()) >= 0 ) ) {
            this.bookShowMsg = "Too late for booking, must be " + Math.abs(Utility.BOOKING_MINUTES_BEFORE) + " minutes before show time";
            return "";
        }
        
        Status saveStatus;
        MovieShowDB[] movieShowArr = new MovieShowDB[1];
        ShowBookingDB[] showBookingArr = new ShowBookingDB[1];

        movieShowArr[0] = this.getMovieShow();
        saveStatus = this.movieShowSrv.saveMovieShowBooking(movieShowArr, showBookingArr, Utility.getCurDate(),
                                                            this.getSeatsToBook(), this.getCardNumber(), (CustomerDB) this.webpageSrv.getLoggedInUser(Utility.getHttpSession()));
        this.movieShow = movieShowArr[0];
        if (saveStatus == Status.OK) {
            this.setReferenceNumber(showBookingArr[0].getRefNum());
            this.bookShowMsg = "";
            return "successBookShow";
        } else if (saveStatus == Status.INSUFFICIENT_SEATS) {
            this.bookShowMsg = "Insufficient available seats left";
            return "";
        } else {
            this.bookShowMsg = "System error";
            return "";
        }
    }

    public void preRender(ComponentSystemEvent evt) {
        if (this.startDate == null) {
            Date curDate = Utility.getCurDate();
            this.startDate = (Date) Utility.getSessionAttribute(LIST_MOVIES_START_DATE_ATTRIBUTE);
            if (this.startDate == null || this.startDate.compareTo(curDate) < 0) {
                setStartDate(curDate);
            }
        }
        if (this.daysOffset == null) {
            this.daysOffset = (Integer) Utility.getSessionAttribute(LIST_MOVIES_DAYS_OFFSET_ATTRIBUTE);
        }
        if (this.theaterId == null) {
            this.theaterId = (Integer) Utility.getSessionAttribute(LIST_MOVIES_THEATER_ID_ATTRIBUTE);
        }
    }

    @SuppressWarnings(value="unchecked")
    public List<TheaterDB> getMovieShowsByTheater() {
        // System.out.println("getMovieShowsByTheater -> Start Date: " + this.startDate + ", daysOffset: " + this.daysOffset +
        //                    ", theaterID: " + this.theaterId + ", this.theaters: " + (this.theaters == null ? "IS NULL" : "NOT NULL"));
        if (this.theaters == null) {
            if (this.startDate == null) {
                return (List<TheaterDB>) Utility.getSessionAttribute(LIST_MOVIES_THEATERS_ATTRIBUTE);
            } else {
                setTheaters();
            }
        }
        return this.theaters;
    }

    public void validateSeatsToBook(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
        Integer seatsBooked;
        boolean seatsAvailabilityCheck;
        MovieShowDB [] movieShowArr;
        if (value != null) {
            movieShowArr = new MovieShowDB[1];
            movieShowArr[0] = getMovieShow();
            seatsBooked = ((Long) value).intValue();
            seatsAvailabilityCheck = this.movieShowSrv.checkSeatsAvailibility(movieShowArr, seatsBooked);
            this.movieShow = movieShowArr[0];
            if (seatsBooked < 1 || !seatsAvailabilityCheck) {
                throw new ValidatorException(Utility.getFacesMessage("Seats to be booked should be between 1 and " + movieShowArr[0].getSeatsBalance(), FacesMessage.SEVERITY_ERROR));
            }
        }
    }

    public String getSummary() {
        if (this.theaters == null || this.theaters.isEmpty()) {
            return "No movie shows for the selected query";
        } else {
            return "";
        }
    }
    
    public String getFooterClass() {
        return (this.theaters == null || this.theaters.isEmpty() ? "theaterFooter" : "hide");
    }
}
