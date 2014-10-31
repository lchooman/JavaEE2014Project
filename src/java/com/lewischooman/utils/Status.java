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

package com.lewischooman.utils;

public enum Status {

    OK(0, "OK"),
    NOT_LOGGED_IN(1, "user not logged in"),
    INSUFFICIENT_PRIVILEGES(2, "insufficient privileges"),
    UNKNOWN_REQUEST(3, "unknown request"),
    ALREADY_EXISTS(4, "already exists"),
    INSUFFICIENT_SEATS(5, "insufficient seats"),
    MISSING_SHOW(6, "missing show");

    private final int statusValue;
    private final String statusStr;

    private Status(int statusValue, String statusStr) {
        this.statusValue = statusValue;
        this.statusStr = statusStr;
    }

    public int getValue() {
        return this.statusValue;
    }

    @Override
    public String toString() {
        return this.statusStr;
    }
}
