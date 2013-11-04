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
package com.github.peholmst.fenix.planner.ui.components;

import java.text.ParseException;
import javax.swing.JFormattedTextField;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Formatter that converts between a string and a {@link LocalDate}.
 *
 * @author Petter Holmström
 */
public class LocalDateFormatter extends JFormattedTextField.AbstractFormatter {

    private final DateTimeFormatter formatter;

    public LocalDateFormatter() {
        formatter = DateTimeFormat.forPattern("dd.MM.yyyy");
    }

    @Override
    public Object stringToValue(String text) throws ParseException {
        if (text == null || text.isEmpty()) {
            return null;
        } else {
            try {
                return formatter.parseLocalDate(text);
            } catch (IllegalArgumentException ex) {
                throw new ParseException(ex.getMessage(), 0);
            }
        }
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value == null) {
            return null;
        } else {
            return ((LocalDate) value).toString(formatter);
        }
    }

}
