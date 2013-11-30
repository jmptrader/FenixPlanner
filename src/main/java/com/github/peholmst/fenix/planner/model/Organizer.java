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
package com.github.peholmst.fenix.planner.model;

import com.github.peholmst.fenix.planner.utils.StringUtils;

/**
 * TODO Document me!
 * 
 * @author Petter Holmström
 */
public class Organizer extends JavaBean implements Comparable<Organizer> {

    public static final String PROP_INITIALS = "initials";
    public static final String PROP_FULLNAME = "fullName";
    public static final String PROP_PHONENUMBER = "phoneNumber";
    public static final String PROP_EMAIL = "email";
    
    private String initials = "";
    private String fullName = "";
    private String phoneNumber = "";
    private String email = "";
    private Program program;

    public Program getProgram() {
        return program;
    }

    void setProgram(Program program) {
        this.program = program;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        String old = this.initials;
        this.initials = StringUtils.nullToEmptyString(initials);
        getPropertyChangeSupport().firePropertyChange(PROP_INITIALS, old, this.initials);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        String old = this.fullName;
        this.fullName = StringUtils.nullToEmptyString(fullName);
        getPropertyChangeSupport().firePropertyChange(PROP_FULLNAME, old, this.fullName);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        String old = this.phoneNumber;
        this.phoneNumber = StringUtils.nullToEmptyString(phoneNumber);
        getPropertyChangeSupport().firePropertyChange(PROP_PHONENUMBER, old, this.phoneNumber);        
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String old = this.email;
        this.email = StringUtils.nullToEmptyString(email);
        getPropertyChangeSupport().firePropertyChange(PROP_EMAIL, old, this.email);
    }

    @Override
    public int compareTo(Organizer o) {
        return fullName.compareTo(o.fullName);
    }

}
