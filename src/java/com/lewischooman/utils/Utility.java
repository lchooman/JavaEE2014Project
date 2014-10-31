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

import com.lewischooman.config.SpringMVCViewHandler;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class Utility {
    public static final String LOGGED_IN_USER_ATTRIBUTE = "__LOGGED_IN_USER__";
    public static final int BOOKING_MINUTES_BEFORE = -15;
    
    public static <T> boolean hasOneCommonElem(Collection<T> collA, Collection<T> collB) {
        if (collA == null || collA.isEmpty() || collB == null || collB.isEmpty()) {
            return false;
        }

        if (collA.size() <= collB.size()) {
            for (T elemA : collA) {
                if (collB.contains(elemA)) {
                    return true;
                }
            }
        } else {
            return hasOneCommonElem(collB, collA);
        }
        return false;
    }

    public static String getImageUrl(String fileName) {
        return "../images/" + fileName;
    }

    public static Date addToDate(Date date, int daysOffset) {
        return addToDate(date, Calendar.DAY_OF_MONTH, daysOffset);
    }
    
    public static Date addToDate(Date date, int field, int offset) {
        Calendar newCal = Calendar.getInstance();
        newCal.setTime(date);
        newCal.add(field, offset);
        return newCal.getTime();
    }

    public static Date getCurDate() {
        Calendar newCal = Calendar.getInstance();
        int year = newCal.get(Calendar.YEAR), month = newCal.get(Calendar.MONTH), date = newCal.get(Calendar.DAY_OF_MONTH);
        newCal.clear();
        newCal.set(year, month, date);
        return newCal.getTime();
    }

    public static Date getCurTime() {
        Calendar newCal = Calendar.getInstance();
        int hour = newCal.get(Calendar.HOUR_OF_DAY), minute = newCal.get(Calendar.MINUTE);
        newCal.clear();
        newCal.set(Calendar.HOUR_OF_DAY, hour);
        newCal.set(Calendar.MINUTE, minute);
        return newCal.getTime();
    }

    public static Date getCurTimeAddMinutes(int addMinutes) {
        Calendar newCal = Calendar.getInstance();
        int hour = newCal.get(Calendar.HOUR_OF_DAY), minute = newCal.get(Calendar.MINUTE);
        newCal.clear();
        newCal.set(Calendar.HOUR_OF_DAY, hour);
        newCal.set(Calendar.MINUTE, minute);
        newCal.add(Calendar.MINUTE, addMinutes);
        return newCal.getTime();
    }

    public static HttpSession getHttpSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);
        return httpSession;
    }

    public static void setSessionAttribute(String attributeName, Object attributeValue) {
        HttpSession httpSession = getHttpSession();
        if (httpSession != null) {
            if (attributeValue != null) {
                httpSession.setAttribute(attributeName, attributeValue);
            } else {
                httpSession.removeAttribute(attributeName);
            }
        }
    }

    public static Object getSessionAttribute(String attributeName) {
        HttpSession httpSession = getHttpSession();
        if (httpSession != null) {
            return httpSession.getAttribute(attributeName);
        } else {
            return null;
        }
    }

    public static String getCurrentViewName() {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewName = context.getViewRoot().getViewId();
        if (viewName.endsWith(SpringMVCViewHandler.JSF_URL_EXT)) {
            viewName = viewName.substring(viewName.lastIndexOf('/') + 1, viewName.length() - SpringMVCViewHandler.JSF_URL_EXT_LEN);
        } else {
            viewName = viewName.substring(viewName.lastIndexOf('/') + 1);
        }
        return viewName;
    }

    public static FacesMessage getFacesMessage(String msg, FacesMessage.Severity severity) {
        return getFacesMessage(msg, msg, severity);
    }

    public static FacesMessage getFacesMessage(String detailMsg, String summaryMsg, FacesMessage.Severity severity) {
        FacesMessage fMsg;
        fMsg = new FacesMessage();
        fMsg.setDetail(detailMsg);
        fMsg.setSummary(summaryMsg);
        fMsg.setSeverity(severity);

        return fMsg;
    }

    public static String getDateTimeInFormat(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern(pattern);
        return dateFormat.format(date);
    }
}