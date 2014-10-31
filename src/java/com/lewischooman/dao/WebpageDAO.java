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

import com.lewischooman.models.WebpageDB;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository(value="webpageDAO")
public class WebpageDAO implements IWebpageDAO {
    
    @Autowired
    @Qualifier(value="sessionFactory")
    private SessionFactory sessionFactory;
    
    @Override
    @SuppressWarnings(value="unchecked")
    public List<WebpageDB> getWebpages() {
        Session session;
        Criteria criteria;
        
        session = this.sessionFactory.getCurrentSession();
        criteria = session.createCriteria(WebpageDB.class);
        List<WebpageDB> webpages = (List<WebpageDB>) criteria.list();
        return webpages;
    }

    @Override
    @SuppressWarnings(value="unchecked")
    public List<WebpageDB> getWebpages4Menu(String loginFlag) {
        Session session;
        Criteria criteria;

        session = this.sessionFactory.getCurrentSession();
        criteria = session.createCriteria(WebpageDB.class);
        criteria.add(Restrictions.eq("menuFlag", "Y")).
                 add(Restrictions.or(Restrictions.eq("loginFlag", loginFlag), Restrictions.isNull("loginFlag"))).
                 addOrder(Order.asc("menuOrder"));
        List<WebpageDB> webpages = (List<WebpageDB>) criteria.list();
        return webpages;
    }
}
