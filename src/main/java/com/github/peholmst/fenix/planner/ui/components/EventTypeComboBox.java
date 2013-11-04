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

import com.github.peholmst.fenix.planner.model.EventType;
import com.github.peholmst.fenix.planner.model.Program;
import com.github.peholmst.fenix.planner.ui.model.EventTypesComboBoxModel;
import javax.swing.JComboBox;

/**
 * Combo box that contains the {@link EventType}s of a {@link Program}.
 *
 * @author Petter Holmström
 */
public class EventTypeComboBox extends JComboBox {

    private final EventTypesComboBoxModel comboBoxModel = new EventTypesComboBoxModel();

    public EventTypeComboBox() {
        setModel(comboBoxModel);
    }

    /**
     * Sets the {@link Program} whose event types should be shown in the combo box.
     * This method will register a listener with the program, so clients must
     * remember to set it to {@code null} when it is no longer needed in order
     * to prevent memory leaks.
     *
     * @param program the program to use, or {@code null}.
     */
    public void setProgram(Program program) {
        comboBoxModel.setProgram(program);
    }

}
