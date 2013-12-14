/*
 * Fenix Planner
 * Copyright (C) 2013 Petter Holmström
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fenix.planner.model;

import fenix.planner.utils.StringUtils;
import java.awt.image.BufferedImage;

/**
 * TODO Document me!
 * 
 * @author Petter Holmström
 */
public class Header extends JavaBean {

    public static final String PROP_DEPARTMENTNAME = "departmentName";
    public static final String PROP_SECTIONNAME = "sectionName";
    public static final String PROP_HEADING = "heading";
    public static final String PROP_AUTHORINITIALS = "authorInitials";

    private String departmentName = "";
    private String sectionName = "";
    private String heading = "";
    private String authorInitials = "";
    private BufferedImage logo;
    private Program program;

    public Program getProgram() {
        return program;
    }

    void setProgram(Program program) {
        this.program = program;
    }

    public BufferedImage getLogo() {
        return logo;
    }

    public void setLogo(BufferedImage logo) {
        this.logo = logo;
    }

    public boolean hasLogo() {
        return logo != null;
    }

    public String getAuthorInitials() {
        return authorInitials;
    }

    public void setAuthorInitials(String authorInitials) {
        String old = this.authorInitials;
        this.authorInitials = StringUtils.nullToEmptyString(authorInitials);
        getPropertyChangeSupport().firePropertyChange(PROP_AUTHORINITIALS, old, this.authorInitials);
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        String old = this.departmentName;
        this.departmentName = departmentName;
        getPropertyChangeSupport().firePropertyChange(PROP_DEPARTMENTNAME, old, this.departmentName);
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        String old = this.sectionName;
        this.sectionName = sectionName;
        getPropertyChangeSupport().firePropertyChange(PROP_SECTIONNAME, old, this.sectionName);
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        String old = this.heading;
        this.heading = heading;
        getPropertyChangeSupport().firePropertyChange(PROP_HEADING, old, this.heading);
    }
}
