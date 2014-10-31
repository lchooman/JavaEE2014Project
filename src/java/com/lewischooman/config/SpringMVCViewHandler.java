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

package com.lewischooman.config;

import javax.faces.application.ViewHandlerWrapper;
import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;
import java.util.Map;
import java.util.List;

public class SpringMVCViewHandler extends ViewHandlerWrapper {
    public static final String JSF_URL_EXT = ".xhtml";
    public static final String VIRTUAL_URL_EXT = ".htm";
    public static final int JSF_URL_EXT_LEN = JSF_URL_EXT.length();
    public static final String VIRTUAL_PATH = "/pages/";
    public static final String JSF_PATH = "/WEB-INF/views/jsf/";
    
    private final ViewHandler viewHandler;

    public SpringMVCViewHandler(ViewHandler viewHandler) {
        this.viewHandler = viewHandler;
    }

    @Override
    public ViewHandler getWrapped() {
        return this.viewHandler;
    }

    @Override
    public String getActionURL(FacesContext context, String viewId) {
        String url = super.getActionURL(context, viewId);
        return fromJSFToSpringMVCUrl(url);
    }

    @Override
    public String getBookmarkableURL(FacesContext context, String viewId, java.util.Map<String, List<String>> parameters, boolean includeViewParams) {
        String url = super.getBookmarkableURL(context, viewId, parameters, includeViewParams);
        return fromJSFToSpringMVCUrl(url);
    }


    @Override
    public String getRedirectURL(FacesContext context, String viewId, Map<String, List<String>> parameters, boolean includeViewParams) {
        String url = super.getRedirectURL(context, viewId, parameters, includeViewParams);
        return fromJSFToSpringMVCUrl(url);
    }

    @Override
    public String getResourceURL(FacesContext context, java.lang.String path) {
        String url = super.getResourceURL(context, path);
         return fromJSFToSpringMVCUrl(url);
    }

    private String fromJSFToSpringMVCUrl(String url) {
        if (url != null && url.endsWith(JSF_URL_EXT) && url.contains(JSF_PATH)) {
            return url.substring(0, url.length() - JSF_URL_EXT_LEN).replace(JSF_PATH, VIRTUAL_PATH) + VIRTUAL_URL_EXT;
        } else {
            return url;
        }
    }
}
