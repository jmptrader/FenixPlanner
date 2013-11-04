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
package com.github.peholmst.fenix.planner.ui.model;

import com.github.peholmst.fenix.planner.model.Organizer;
import com.github.peholmst.fenix.planner.model.Program;
import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.table.AbstractTableModel;

/**
 * Table model for displaying and editing the {@link Organizer}s of a
 * {@link Program} in a Swing table.
 * 
 * @author Petter Holmström
 */
public class OrganizersTableModel extends AbstractTableModel implements PropertyChangeListener {

    private static final int COL_PHONE = 3;
    private static final int COL_EMAIL = 2;
    private static final int COL_NAME = 1;
    private static final int COL_INITIALS = 0;

    private Program program;

    /**
     * Sets the {@link Program} whose organizers should be shown in the table.
     * This method will register a listener with the program, so clients must
     * remember to set it to {@code null} when it is no longer needed in order
     * to prevent memory leaks.
     *
     * @param program the program to use, or {@code null}.
     */
    public void setProgram(Program program) {
        if (this.program != null) {
            this.program.removePropertyChangeListener(this);
        }
        this.program = program;
        if (this.program != null) {
            this.program.addPropertyChangeListener(this);
        }
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return program == null ? 0 : program.getOrganizers().size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        final Organizer organizer = program.getOrganizers().get(rowIndex);
        switch (columnIndex) {
            case COL_INITIALS:
                organizer.setInitials((String) aValue);
                break;
            case COL_NAME:
                organizer.setFullName((String) aValue);
                break;
            case COL_EMAIL:
                organizer.setEmail((String) aValue);
                break;
            case COL_PHONE:
                organizer.setPhoneNumber((String) aValue);
                break;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public String getColumnName(int column) {
        // TODO Translate the column names
        switch (column) {
            case COL_INITIALS:
                return "Initialer";
            case COL_NAME:
                return "Namn";
            case COL_EMAIL:
                return "E-post";
            case COL_PHONE:
                return "Telefon";
            default:
                return "";
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        final Organizer organizer = program.getOrganizers().get(rowIndex);
        switch (columnIndex) {
            case COL_INITIALS:
                return organizer.getInitials();
            case COL_NAME:
                return organizer.getFullName();
            case COL_EMAIL:
                return organizer.getEmail();
            case COL_PHONE:
                return organizer.getPhoneNumber();
            default:
                return null;
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Program.PROP_ORGANIZERS.equals(evt.getPropertyName())) {
            IndexedPropertyChangeEvent ievt = (IndexedPropertyChangeEvent) evt;
            if (ievt.getNewValue() == null) {
                fireTableRowsDeleted(ievt.getIndex(), ievt.getIndex());
            } else if (ievt.getOldValue() == null) {
                fireTableRowsInserted(ievt.getIndex(), ievt.getIndex());
            }
        }
    }
}
