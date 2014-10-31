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

package com.lewischooman.dao;

import com.lewischooman.models.CustomerDB;
import com.lewischooman.models.RoleDB;
import com.lewischooman.utils.Status;
import java.util.HashSet;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository(value="customerDAO")
public class CustomerDAO implements ICustomerDAO {
    
    @Autowired
    @Qualifier(value="sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public CustomerDB getUserbyEmailId(String emailId) {
        Session session;
        Criteria criteria;

        session = this.sessionFactory.getCurrentSession();
        criteria = session.createCriteria(CustomerDB.class);
        criteria.add(Restrictions.eq("emailId", emailId));
        
        return (CustomerDB) criteria.uniqueResult();
    }

    @Override
    public Status saveUser(Integer[] id, String longName, String password, String emailId, String contactNumber, String address) {
        Session session;
        Criteria criteria;
        CustomerDB customer;
        RoleDB role;

        session = this.sessionFactory.getCurrentSession();
        criteria = session.createCriteria(RoleDB.class);
        criteria.add(Restrictions.eq("name", "customer"));
        role = (RoleDB) criteria.uniqueResult();

        customer = new CustomerDB();
        customer.setLongName(longName);
        customer.setPassword(password);
        customer.setEmailId(emailId);
        customer.setContactNumber(contactNumber);
        customer.setAddress(address);
        customer.setRoles(new HashSet<RoleDB>());
        customer.getRoles().add(role);

        if (id != null && id.length > 0) {
            id[0] = (Integer) session.save(customer);
        } else {
            session.save(customer);
        }
        return Status.OK;
    }
}
