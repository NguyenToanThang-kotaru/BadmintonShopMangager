package GUI.Promotion;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;


public class CustomDatePicker extends JSpinner{
    public CustomDatePicker() {
        super(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(this, "yyyy-MM-dd");
        this.setEditor(editor);
        this.setValue(new Date()); // mặc định là ngày hôm nay
    }

    public LocalDate getDate() {
        Date selectedDate = (Date) this.getValue();
        return selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public void setDate(LocalDate date) {
        Date d = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.setValue(d);
    }
    
}
