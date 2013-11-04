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

import com.github.peholmst.fenix.planner.model.MultilingualContent;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

/**
 * TODO Document me!
 *
 * @author Petter Holmström
 */
public class MultilingualTextComponentBinder implements Observer, DocumentListener {

    private final Locale locale;
    private JTextComponent textComponent;
    private MultilingualContent<String> textContent;

    public MultilingualTextComponentBinder(Locale locale) {
        assert locale != null : "locale must not be null";
        this.locale = locale;
    }

    public void bind(JTextComponent textComponent, MultilingualContent<String> textContent) {
        assert textComponent != null : "textComponent must not be null";
        assert textContent != null : "textContent must not be null";
        this.textComponent = textComponent;
        this.textContent = textContent;

        textComponent.setText(textContent.get(locale));
        textContent.addObserver(this);
        textComponent.getDocument().addDocumentListener(this);
    }

    public void unbind() {
        textContent.deleteObserver(this);
        textContent = null;
        textComponent.getDocument().removeDocumentListener(this);
        textComponent = null;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == textContent && locale.equals(arg)) {
            textComponent.setText(textContent.get(locale));
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        updateContentFromComponent();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updateContentFromComponent();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        updateContentFromComponent();
    }

    private void updateContentFromComponent() {
        textContent.set(locale, textComponent.getText());
    }
}
