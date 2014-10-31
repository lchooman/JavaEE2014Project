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

import com.lewischooman.models.TheaterDB;
import com.lewischooman.services.IMovieShowSrv;
import com.lewischooman.services.ITheaterSrv;
import com.lewischooman.utils.Utility;
import java.util.Date;
import java.util.List;
import javax.faces.event.ComponentSystemEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component(value="dailySalesReportBean")
@Scope(value="request")
public class DailySalesReportBean {

    private static final String DAILY_SALES_REPORT_DATE_ATTRIBUTE  = "DAILY_SALES_REPORT_DATE_ATTRIBUTE";
    private static final String DAILY_SALES_REPORT_THEATER_ID_ATTRIBUTE = "DAILY_SALES_REPORT_THEATER_ID_ATTRIBUTE";
    private static final String DAILY_SALES_REPORT_THEATERS_ATTRIBUTE = "DAILY_SALES_REPORT_THEATERS_ATTRIBUTE";
    
    @Autowired
    @Qualifier(value="movieShowSrv")
    private IMovieShowSrv movieShowSrv;

    @Autowired
    @Qualifier(value="theaterSrv")
    private ITheaterSrv theaterSrv;

    private Date date;

    private Integer theaterId;
    
    private List<TheaterDB> theaters;

    private List<TheaterDB> theaterSelectItems;

    private Integer seatsBooked;

    private Double avgPrice;

    private Double amount;

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
        Utility.setSessionAttribute(DAILY_SALES_REPORT_DATE_ATTRIBUTE, this.date);
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
        Utility.setSessionAttribute(DAILY_SALES_REPORT_THEATER_ID_ATTRIBUTE, this.theaterId);
    }

    public List<TheaterDB> getTheaterSelectItems() {
        if (this.theaterSelectItems == null) {
            this.theaterSelectItems = this.theaterSrv.getTheaterSelectItems();
        }
        return this.theaterSelectItems;
    }

    public String getTitle() {
        if (this.date != null) {
            return "Daily Sales Report for " + Utility.getDateTimeInFormat(this.date, "EEE, dd/MM/yyyy");
        } else {
            return "Daily Sales Report";
        }
    }
    
    private void setTheaters() {
        if (this.date != null) {
            this.theaters = this.movieShowSrv.getMovieShowsByDateTheater(this.date, 0, (this.theaterId == null || this.theaterId == -1 ? null : this.theaterId));
            Utility.setSessionAttribute(DAILY_SALES_REPORT_THEATERS_ATTRIBUTE, this.theaters);
        }
    }

    public String go() {
        setTheaters();
        return "";
    }

    public void preRender(ComponentSystemEvent evt) {
        if (this.date == null) {
            Date curDate = Utility.getCurDate();
            this.date = (Date) Utility.getSessionAttribute(DAILY_SALES_REPORT_DATE_ATTRIBUTE);
            if (this.date == null || this.date.compareTo(curDate) < 0) {
                setDate(curDate);
            }
        }
        if (this.theaterId == null) {
            this.theaterId = (Integer) Utility.getSessionAttribute(DAILY_SALES_REPORT_THEATER_ID_ATTRIBUTE);
        }
    }

    @SuppressWarnings(value="unchecked")
    public List<TheaterDB> getMovieShowsByTheater() {
        if (this.theaters == null) {
            if (this.date == null) {
                this.amount = null;
                this.avgPrice = null;
                this.seatsBooked = null;
                return (List<TheaterDB>) Utility.getSessionAttribute(DAILY_SALES_REPORT_THEATERS_ATTRIBUTE);
            } else {
                this.amount = null;
                this.avgPrice = null;
                this.seatsBooked = null;
                setTheaters();
            }
        }
        return this.theaters;
    }

    public Integer getSeatsBooked() {
        if (this.theaters != null && this.seatsBooked == null) {
            int sumSeatsBooked = 0;
            for (TheaterDB theater : this.theaters) {
                sumSeatsBooked += theater.getSeatsBooked();
            }
            this.seatsBooked = sumSeatsBooked;
        }
        return this.seatsBooked;
    }

    public Double getAmount() {
        if (this.amount == null) {
            double sumAmount = 0;
            for (TheaterDB theater : this.theaters) {
                sumAmount += theater.getAmount();
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
        if (this.theaters == null || this.theaters.isEmpty()) {
            return "No daily sales report for the selected query";
        } else if (isMoreThanOneTheater()) {
            return "Daily Grand Total → Booked Seats: ";
        } else {
            return "";
        }
    }

    public boolean isMoreThanOneTheater() {
        return (this.theaters != null && this.theaters.size() > 1);
    }

    public String getFooterClass() {
        return (this.theaters == null || this.theaters.isEmpty() ? "theaterFooter" : (this.theaters.size() > 1 ? "movieShowFooter" : "hide"));
    }
}