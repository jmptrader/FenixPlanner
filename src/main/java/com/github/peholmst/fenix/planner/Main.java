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
package com.github.peholmst.fenix.planner;

import com.github.peholmst.fenix.planner.model.Program;
import com.github.peholmst.fenix.planner.ui.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 * Application launcher.
 *
 * @author Petter Holmström
 */
public class Main {

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        System.out.println(ApplicationInfo.getTitle());
        System.out.println("Version " + ApplicationInfo.getVersion());
        System.out.println("This program is free software: you can redistribute it and/or modify\n"
                + "it under the terms of the GNU Affero General Public License as\n"
                + "published by the Free Software Foundation, either version 3 of the\n"
                + "License, or (at your option) any later version.");
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new MainFrame(new Program()).setVisible(true);
            }
        });
    }
}
