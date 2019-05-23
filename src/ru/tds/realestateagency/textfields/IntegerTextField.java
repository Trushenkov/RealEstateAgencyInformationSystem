package ru.tds.realestateagency.textfields;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextField;

import java.util.Objects;

/**
 * Класс для создания поля для ввода номера телефона в формате 8XXXXXXXXXX (11-значный)
 *
 * @author Трушенков Дмитрий
 */
public class IntegerTextField extends TextField {

    private final IntegerProperty maxLength;

    public IntegerTextField() {
        super();
        this.maxLength = new SimpleIntegerProperty(-1);
    }

    public IntegerProperty maxLengthProperty() {
        return this.maxLength;
    }

    private final Integer getMaxLength() {
        return this.maxLength.getValue();
    }

    public final void setMaxLength(Integer maxLength) {
        Objects.requireNonNull(maxLength, "Максимальная длина не может быть null, без ограничений -1");
        this.maxLength.setValue(maxLength);
    }

    @Override
    public void replaceText(int start, int end, String text) {


        if (this.getMaxLength() <= 0) {
            // Нормальное поведение, в случае когда не достигнута максимальная длина
            if (text.matches("[0-9]") || text.isEmpty()) {
                super.replaceText(start, end, text);
            }
        }
        else {
            // Получиаем текст в текстовом поле, прежде чем пользователь введет что-то
            String currentText = this.getText() == null ? "" : this.getText();

            // Вычисляем текст, который обычно должен быть в текстовом поле
            String finalText = currentText.substring(0, start) + text + currentText.substring(end);

            // Если максимальная длина не превышена
            int numberOfexceedingCharacters = finalText.length() - this.getMaxLength();
            if (numberOfexceedingCharacters <= 0) {
                // Нормальное поведение
                if (text.matches("[0-9]") || text.isEmpty()) {
                    super.replaceText(start, end, text);
                }
            }
            else {
                // В противном случае вырезаем текст, который собирался вставить пользователь
                String cutInsertedText = text.substring(
                        0,
                        text.length() - numberOfexceedingCharacters
                );

                // И заменяем этот текст
                super.replaceText(start, end, cutInsertedText);
            }
        }
    }

    @Override
    public void replaceSelection(String replacement) {
        super.replaceSelection(replacement);
    }
}
