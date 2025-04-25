package GUI.Promotion;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class CustomTextField extends JTextField {

    public CustomTextField(int columns) {
        super(columns);
        setFont(new Font("Arial", Font.PLAIN, 14));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(149, 165, 166)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }
}
