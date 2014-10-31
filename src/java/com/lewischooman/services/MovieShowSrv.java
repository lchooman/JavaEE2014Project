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

package com.lewischooman.services;

import com.lewischooman.dao.IMovieDAO;
import com.lewischooman.dao.IMovieShowDAO;
import com.lewischooman.dao.IShowBookingDAO;
import com.lewischooman.models.CustomerDB;
import com.lewischooman.models.MovieDB;
import com.lewischooman.models.MovieShowDB;
import com.lewischooman.models.ShowBookingDB;
import com.lewischooman.models.TheaterDB;
import com.lewischooman.utils.Status;
import com.lewischooman.utils.Utility;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value="movieShowSrv")
public class MovieShowSrv implements IMovieShowSrv {

    @Autowired
    @Qualifier(value="movieShowDAO")
    private IMovieShowDAO movieShowDAO;

    @Autowired
    @Qualifier(value="movieDAO")
    private IMovieDAO movieDAO;

    @Autowired
    @Qualifier(value="showBookingDAO")
    private IShowBookingDAO showBookingDAO;
    
    @Override
    public List<TheaterDB> getMovieShowsByDateTheater(Date dateFrom, int dayOffset, Integer theaterId) {
        List<TheaterDB> theaters = new ArrayList<>();
        List<MovieShowDB> movieShows;
        TheaterDB curTheater = null;
        MovieDB curMovie = null;
        int startMovieShowIndex = 0;

        if (dateFrom == null) {
            return theaters;
        }
        movieShows = this.movieShowDAO.getMovieShowsByDateTheater(dateFrom, Utility.addToDate(dateFrom, dayOffset), theaterId, true, null);
        for (int i = 0; i < movieShows.size(); i++) {
            if (curTheater == null || !movieShows.get(i).getHall().getTheater().getId().equals(curTheater.getId())) {
                if (curMovie != null) {
                    curMovie.setMovieShows(new ArrayList<>(movieShows.subList(startMovieShowIndex, i)));
                }
                curTheater = movieShows.get(i).getHall().getTheater();
                curTheater.clearMovies();
                theaters.add(curTheater);

                if (movieShows.get(i).getMovie().getMovieShows() != null) {
                    curMovie = new MovieDB(); // Needs to make a copy of the MovieDB instance because it might be shared across theaters
                    curMovie.setId(movieShows.get(i).getMovie().getId());
                    curMovie.setImageFile(movieShows.get(i).getMovie().getImageFile());
                    curMovie.setTitle(movieShows.get(i).getMovie().getTitle());
                } else {
                    curMovie = movieShows.get(i).getMovie();
                }
                
                curTheater.addMovie(curMovie);
                startMovieShowIndex = i;
            } else if (!movieShows.get(i).getMovie().getId().equals(curMovie.getId())) {
                curMovie.setMovieShows(new ArrayList<>(movieShows.subList(startMovieShowIndex, i)));

                if (movieShows.get(i).getMovie().getMovieShows() != null) {
                    curMovie = new MovieDB(); // Needs to make a copy of the MovieDB instance because it might be shared across theaters
                    curMovie.setId(movieShows.get(i).getMovie().getId());
                    curMovie.setImageFile(movieShows.get(i).getMovie().getImageFile());
                    curMovie.setTitle(movieShows.get(i).getMovie().getTitle());
                } else {
                    curMovie = movieShows.get(i).getMovie();
                }
                
                curTheater.addMovie(curMovie);
                startMovieShowIndex = i;
            }
        }
        if (curMovie != null) {
            curMovie.setMovieShows(new ArrayList<>(movieShows.subList(startMovieShowIndex, movieShows.size())));
        }
        
        return theaters;
    }

    @Override
    public List<MovieDB> getMoviesByDate(Date dateFrom, Date dateTo) {
        List<MovieDB> movies = new ArrayList<>();
        List<MovieShowDB> movieShows;
        MovieDB curMovie = null;
        int startMovieShowIndex = 0;

        if (dateFrom == null) {
            return movies;
        }
        movieShows = this.movieShowDAO.getMovieShowsByDateTheater(dateFrom, dateTo, null, false, null);
        for (int i = 0; i < movieShows.size(); i++) {
            if (curMovie == null || !movieShows.get(i).getMovie().getId().equals(curMovie.getId())) {
                if (curMovie != null) {
                    curMovie.setMovieShows(new ArrayList<>(movieShows.subList(startMovieShowIndex, i)));
                }
                if (movieShows.get(i).getMovie().getMovieShows() != null) {
                    curMovie = new MovieDB();
                    curMovie.setId(movieShows.get(i).getMovie().getId());
                    curMovie.setImageFile(movieShows.get(i).getMovie().getImageFile());
                    curMovie.setTitle(movieShows.get(i).getMovie().getTitle());
                } else {
                    curMovie = movieShows.get(i).getMovie();
                }

                movies.add(curMovie);
                startMovieShowIndex = i;
            }
        }
        if (curMovie != null) {
            curMovie.setMovieShows(new ArrayList<>(movieShows.subList(startMovieShowIndex, movieShows.size())));
        }
        
        return movies;
    }

    @Override
    public MovieDB getMovieByDateMovieId(Date dateFrom, Date dateTo, Integer movieId) {
        MovieDB movie;
        List<MovieShowDB> movieShows = this.movieShowDAO.getMovieShowsByDateTheater(dateFrom, dateTo, null, false, movieId);
        if (movieShows != null && !movieShows.isEmpty()) {
            movieShows.get(0).getMovie().setMovieShows(movieShows);
            return movieShows.get(0).getMovie();
        } else {
            movie = this.movieDAO.getMovieById(movieId);
            return movie;
        }
    }
    
    @Override
    public MovieShowDB getMovieShowById(Integer movieShowId) {
        return (MovieShowDB) this.movieShowDAO.getMovieShowById(movieShowId);
    }

    @Override
    public boolean checkSeatsAvailibility(MovieShowDB[] movieShowArr, Integer seatsBooked) {
        movieShowArr[0] = this.movieShowDAO.getMovieShowById(movieShowArr[0].getId());
        return (movieShowArr[0].getSeatsAvailable() - movieShowArr[0].getSeatsBooked() >= seatsBooked);
    }

    @Override
    public Status saveMovieShowBooking(MovieShowDB[] movieShowArr, ShowBookingDB[] showBookingArr, Date dateBooked, Integer seatsToBook, String cardNumber, CustomerDB customer) {
        Status status;
        int refNum = (new Random()).nextInt(900000000) + 100000000;

        if (movieShowArr == null || movieShowArr.length == 0 || movieShowArr[0].getId() == null) {
           return Status.MISSING_SHOW;
        }

        status = movieShowDAO.updateMovieShowWithBooking(movieShowArr, seatsToBook);
        if (status == Status.OK) {
            showBookingArr[0] = this.showBookingDAO.saveShowBooking(movieShowArr[0], dateBooked, seatsToBook, cardNumber, customer, refNum);
        }
        return status;
    }
}
