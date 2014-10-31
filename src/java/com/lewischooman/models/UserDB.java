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
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity(name="UserDB")
@Table(name="user")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class UserDB implements Serializable {
    
    private static final long serialVersionUID = 21L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="long_name", nullable=false, length=100)
    private String longName;
    
    @Column(name="password", nullable=false, length=150)
    private String password;

    @ElementCollection(targetClass=RoleDB.class)
    @ManyToMany(targetEntity=RoleDB.class, cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(name="user_role",
               joinColumns={@JoinColumn(name="user_id", referencedColumnName="id", unique=false)},
               inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id", unique=false)})
    private Set<RoleDB> roles;

    public UserDB() {
    }
    
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLongName() {
        return this.longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleDB> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<RoleDB> roles) {
        this.roles = roles;
    }
}