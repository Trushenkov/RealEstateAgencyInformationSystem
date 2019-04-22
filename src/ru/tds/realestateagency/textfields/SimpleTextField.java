package ru.tds.realestateagency.textfields;

import javafx.scene.control.TextField;

/**
 * Класс для создания текствого поля, в котором разрешен ввод только русских букв.
 *
 * @author Трушенков Дмитрий
 */
public class SimpleTextField extends TextField {


    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("[а-яА-я]") || text.isEmpty()) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String replacement) {
        super.replaceSelection(replacement);
    }
}
