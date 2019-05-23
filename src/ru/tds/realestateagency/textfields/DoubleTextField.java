package ru.tds.realestateagency.textfields;

import javafx.scene.control.TextField;

/**
 * Класс для создания поля для ввода вещественных отрицательных и положительных значений
 *
 * @author Трушенков Дмитрий
 */
public class DoubleTextField extends TextField {

    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("[0-9+\\-]") || text.isEmpty()) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String replacement) {
        super.replaceSelection(replacement);
    }

}
