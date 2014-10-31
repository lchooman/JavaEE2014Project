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

package com.lewischooman.convert;

import com.lewischooman.utils.Utility;
import java.util.Map;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="groupConverter")
public class GroupConverter implements Converter {
    private static final int DFLT_SIZE = 4;
    private static final String DFLT_SEPARATOR = " ";
    private static final String DFLT_DIRECTION = "RightToLeft";
    private static final String DFLT_PATTERN = "\\d{13,19}";
    private static final Pattern PUNCTUATION_MARKS = Pattern.compile("[^0-9a-zA-Z]");
    
    private int size;
    private String separator;
    private String direction;
    private Pattern pattern;

    private void applyAttributes(Map<String, Object> attributes) {
        Object obj;
        String str;
        int num;

        this.size = DFLT_SIZE;
        if ((obj = attributes.get("groupConverter.size")) != null) {
            try {
                num = Integer.parseInt((String) obj);
                if (num > 0) {
                    this.size = num;
                }
            } catch (NumberFormatException ex) {
            }
        }

        if ((obj = attributes.get("groupConverter.separator")) != null &&
             !(str = obj.toString()).isEmpty()) {
            this.separator = str.substring(0, 1);
        } else {
            this.separator = DFLT_SEPARATOR;
        }

        if ((obj = attributes.get("groupConverter.direction")) != null &&
            !(str = obj.toString()).trim().isEmpty()) {
            this.direction = str.trim();
        } else {
            this.direction = DFLT_DIRECTION;
        }

        if ((obj = attributes.get("groupConverter.pattern")) != null &&
            !(str = obj.toString()).trim().isEmpty()) {
            this.pattern = Pattern.compile(str);
        } else {
            this.pattern = Pattern.compile(DFLT_PATTERN);
        }
    }
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) throws ConverterException {
        String str;
        FacesMessage fMsg;

        if (value == null || value.trim().isEmpty()) {
            return null;
        } else {
            try {
                applyAttributes(component.getAttributes());
            } catch (RuntimeException ex) {
                throw new ConverterException(Utility.getFacesMessage("Converter error", FacesMessage.SEVERITY_ERROR));
            }
        }
        
        str = value.replace(this.separator, "").trim();
        str = PUNCTUATION_MARKS.matcher(str).replaceAll("");
        if (this.pattern.matcher(str).matches()) {
            return str;
        } else {
            throw new ConverterException(Utility.getFacesMessage("Invalid input", FacesMessage.SEVERITY_ERROR));
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent component, Object obj) throws ConverterException {
        if (obj == null) {
            return "";
        } else {
            applyAttributes(component.getAttributes());
        }

        int sbInLen;
        StringBuilder sbIn = new StringBuilder(), sbOut = new StringBuilder();
        sbIn.append(((String) obj).trim());
        sbInLen = sbIn.length();

        if ("RightToLeft".equalsIgnoreCase(this.direction)) {
            sbIn.reverse();
        }
        
        for (int i = 0; i < sbInLen; i += this.size) {
            if (i + this.size < sbInLen) {
                sbOut.append(sbIn, i, i + this.size);
                sbOut.append(this.separator);
            } else {
                sbOut.append(sbIn, i, sbInLen);
            }
        }

        if ("RightToLeft".equalsIgnoreCase(this.direction)) {
            sbOut.reverse();
        }

        return sbOut.toString();
    }
}
