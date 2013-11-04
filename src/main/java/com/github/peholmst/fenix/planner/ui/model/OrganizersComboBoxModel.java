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
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 * Combo box model for displaying the {@link Organizer}s of a {@link Program} in
 * a Swing combo box.
 *
 * @author Petter Holmström
 */
public class OrganizersComboBoxModel extends AbstractListModel<Organizer> implements ComboBoxModel<Organizer>, PropertyChangeListener {

    private Program program;
    private Organizer selectedOrganizer;

    /**
     * Sets the {@link Program} whose organizers should be shown in the combo
     * box. This method will register a listener with the program, so clients
     * must remember to set it to {@code null} when it is no longer needed in
     * order to prevent memory leaks.
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
        fireContentsChanged(this, -1, -1);
    }

    @Override
    public int getSize() {
        return program == null ? 0 : program.getOrganizers().size();
    }

    @Override
    public Organizer getElementAt(int index) {
        return program.getOrganizers().get(index);
    }

    @Override
    public void setSelectedItem(Object anItem) {
        this.selectedOrganizer = (Organizer) anItem;
        fireContentsChanged(this, -1, -1);
    }

    @Override
    public Object getSelectedItem() {
        return selectedOrganizer;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Program.PROP_ORGANIZERS.equals(evt.getPropertyName())) {
            if (evt instanceof IndexedPropertyChangeEvent) {
                IndexedPropertyChangeEvent ievt = (IndexedPropertyChangeEvent) evt;
                if (ievt.getNewValue() == null) {
                    fireIntervalRemoved(this, ievt.getIndex(), ievt.getIndex());
                } else if (ievt.getOldValue() == null) {
                    fireIntervalAdded(this, ievt.getIndex(), ievt.getIndex());
                }
            } else {
                fireContentsChanged(this, 0, getSize() - 1);
            }
        }
    }
}
