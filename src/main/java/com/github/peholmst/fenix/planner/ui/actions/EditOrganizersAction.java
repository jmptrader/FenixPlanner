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
package com.github.peholmst.fenix.planner.ui.actions;

import com.github.peholmst.fenix.planner.model.Program;
import com.github.peholmst.fenix.planner.ui.dialogs.OrganizersDialog;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.MNEMONIC_KEY;
import static javax.swing.Action.NAME;
import static javax.swing.Action.SHORT_DESCRIPTION;
import javax.swing.JFrame;

/**
 * Action that shows a dialog box that allows the user to edit the program
 * organizers.
 *
 * @author peholmst
 */
public class EditOrganizersAction extends AbstractAction {

    private final JFrame mainFrame;
    private final Program program;

    public EditOrganizersAction(JFrame mainFrame, Program program) {
        this.mainFrame = mainFrame;
        this.program = program;
        putValue(SHORT_DESCRIPTION, "Öppnar en dialogruta där du kan lägga till, ändra och ta bort ansvarspersoner");
        putValue(NAME, "Ansvarspersoner");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        OrganizersDialog dialog = new OrganizersDialog(mainFrame, program);
        dialog.setLocationRelativeTo(mainFrame);
        dialog.setVisible(true);
    }

}
