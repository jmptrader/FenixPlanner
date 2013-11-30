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
import java.awt.Color;

/**
 * TODO Document me!
 *
 * @author Petter Holmström
 */
public class EventType extends JavaBean {

    public static final String PROP_DESCRIPTION = "description";
    public static final String PROP_BACKGROUNDCOLOR = "backgroundColor";
    public static final String PROP_FOREGROUNDCOLOR = "foregroundColor";
    public static final String PROP_ORGANIZED = "organized";

    private String description = "";
    private Color backgroundColor = Color.WHITE;
    private Color foregroundColor = Color.BLACK;
    private boolean organized = false;
    private Program program;

    public Program getProgram() {
        return program;
    }

    void setProgram(Program program) {
        this.program = program;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        String old = this.description;
        this.description = StringUtils.nullToEmptyString(description);
        getPropertyChangeSupport().firePropertyChange(PROP_DESCRIPTION, old, this.description);
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        Color old = this.backgroundColor;
        if (backgroundColor == null) {
            backgroundColor = Color.WHITE;
        }
        this.backgroundColor = backgroundColor;
        getPropertyChangeSupport().firePropertyChange(PROP_BACKGROUNDCOLOR, old, this.backgroundColor);
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {
        Color old = this.foregroundColor;
        if (foregroundColor == null) {
            foregroundColor = Color.BLACK;
        }
        this.foregroundColor = foregroundColor;
        getPropertyChangeSupport().firePropertyChange(PROP_FOREGROUNDCOLOR, old, this.foregroundColor);
    }

    public boolean isOrganized() {
        return organized;
    }

    public void setOrganized(boolean organized) {
        boolean old = this.organized;
        this.organized = organized;
        getPropertyChangeSupport().firePropertyChange(PROP_ORGANIZED, old, this.organized);
    }
}
