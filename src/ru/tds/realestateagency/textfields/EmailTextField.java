package ru.tds.realestateagency.textfields;

import javafx.scene.control.TextField;

/**
 * Класс для создания поля для ввода электронной почты
 *
 * @author Трушенков Дмитрий
 */
public class EmailTextField extends TextField {

    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("[A-Za-z0-9+\\-_.@]") || text.isEmpty()) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String replacement) {
        super.replaceSelection(replacement);
    }
}
