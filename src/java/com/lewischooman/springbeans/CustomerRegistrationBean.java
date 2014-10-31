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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.lewischooman.services.IUserSrv;
import com.lewischooman.utils.Status;
import com.lewischooman.utils.Utility;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value="customerRegistrationBean")
@Scope(value="request")
public class CustomerRegistrationBean {

    private static final Pattern EMAIL_REGEX = Pattern.compile("[^\\s@]+@[^\\s@]+[.][a-z]{2,}");
    
    @Autowired
    @Qualifier(value="customerSrv")
    private IUserSrv customerSrv;

    private String emailId;

    private String password;

    private String longName;

    private String contactNumber;

    private String address;

    private String registerMsg;
    
    private UIInput passwordUI;

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
     * @return the password
     */
    public String getPassword() {
        return "";
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the confirmPassword
     */
    public String getConfirmPassword() {
        return "";
    }

    /**
     * @param confirmPassword the confirmPassword to set
     */
    public void setConfirmPassword(String confirmPassword) {
    }

     /**
     * @return the longName
     */
    public String getLongName() {
        return longName;
    }

    /**
     * @param longName the longName to set
     */
    public void setLongName(String longName) {
        this.longName = longName;
    }

    /**
     * @return the contactNumber
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * @param contactNumber the contactNumber to set
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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
     * @return the registerMsg
     */
    public String getRegisterMsg() {
        return registerMsg;
    }

    /**
     * @param registerMsg the registerMsg to set
     */
    public void setRegisterMsg(String registerMsg) {
        this.registerMsg = registerMsg;
    }

    /**
     * @return the passwordUI
     */
    public UIInput getPasswordUI() {
        return passwordUI;
    }

    /**
     * @param passwordUI the passwordUI to set
     */
    public void setPasswordUI(UIInput passwordUI) {
        this.passwordUI = passwordUI;
    }

    public void validateEmailId(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
        Matcher matcher;
        matcher = EMAIL_REGEX.matcher((String) value);
        if (!matcher.matches()) {
            throw new ValidatorException(Utility.getFacesMessage("Invalid email address format, must be in format Name@Domain.TopLevelDomain", FacesMessage.SEVERITY_ERROR));
        } else if (((String) value).length() > 50) {
            throw new ValidatorException(Utility.getFacesMessage("Email address too long, max length is 50", FacesMessage.SEVERITY_ERROR));
        } else if (this.customerSrv.alreadyExists((String) value)) {
            throw new ValidatorException(Utility.getFacesMessage("Email address already exists", FacesMessage.SEVERITY_ERROR));
        }
    }

    public void validateConfirmPassword(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
        UIInput passwdUI = (UIInput) component.getAttributes().get("passwordUI");
        if (passwdUI != null) {
            String passwd = (String) passwdUI.getValue();
            if (passwdUI.isValid() && !passwd.equals(value)) {
                throw new ValidatorException(Utility.getFacesMessage("Passwords mismatch", FacesMessage.SEVERITY_ERROR));
            }
        } else {
            throw new ValidatorException(Utility.getFacesMessage("Password not available for validation", FacesMessage.SEVERITY_ERROR));
        }
    }

    public String registerCustomer() {
        Integer[] customerIdArr = new Integer[1];
        Status saveStatus;
        saveStatus = this.customerSrv.register(customerIdArr, this.longName, this.password, this.emailId, this.contactNumber, this.address);
        if (saveStatus == Status.OK) {
            this.setRegisterMsg("");
            return "successRegistration";
        } else {
            this.setRegisterMsg("Unable to save customer");
            return "";
        }
    }
}