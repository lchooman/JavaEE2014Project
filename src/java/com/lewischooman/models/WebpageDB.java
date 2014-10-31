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

import com.lewischooman.utils.WebpageStatus;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name="WebpageDB")
@Table(name="webpage")
public class WebpageDB implements Serializable {

    private static final long serialVersionUID = 22L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="name", nullable=false, length=30)
    private String name;

    @Column(name="description", nullable=false, length=50)
    private String description;

    @Column(name="login_flag", nullable=true, length=1)
    private String loginFlag;
    
    @Column(name="menu_flag", nullable=false, length=1)
    private String menuFlag;
    
    @Column(name="menu_order", nullable=false)
    private Integer menuOrder;
    
    @ElementCollection(targetClass=RoleDB.class)
    @ManyToMany(targetEntity=RoleDB.class, cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(name="webpage_role", joinColumns={@JoinColumn(name="webpage_id", referencedColumnName="id", unique=false)},
               inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id", unique=false)})
    private Set<RoleDB> roles;

    @Transient
    private WebpageStatus webpageStatus;
    
    public WebpageDB() {
        this.webpageStatus = WebpageStatus.WEBPAGE;
    }
    
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getLoginFlag() {
        return this.loginFlag;
    }

    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
    }

    public String getMenuFlag() {
        return this.menuFlag;
    }

    public void setMenuFlag(String menuFlag) {
        this.menuFlag = menuFlag;
    }

    public Integer getMenuOrder() {
        return this.menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public Set<RoleDB> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<RoleDB> roles) {
        this.roles = roles;
    }

    public void setWebpageStatus(WebpageStatus webpageStatus) {
        this.webpageStatus = webpageStatus;
    }

    public boolean isWebpage() {
        return this.webpageStatus == WebpageStatus.WEBPAGE;
    }

    public boolean isCurrentView() {
        return this.webpageStatus == WebpageStatus.CURRENTVIEW;
    }

    public boolean isLogout() {
        return this.webpageStatus == WebpageStatus.LOGOUT;
    }
}
