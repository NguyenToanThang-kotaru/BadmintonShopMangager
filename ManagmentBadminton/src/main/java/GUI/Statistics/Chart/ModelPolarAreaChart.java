
package GUI.Statistics.Chart;

import java.awt.Color;


public class ModelPolarAreaChart {
    Color color;
    String name;
    double values;

    public ModelPolarAreaChart() {
    }

    public ModelPolarAreaChart(Color color, String name, double values) {
        this.color = color;
        this.name = name;
        this.values = values;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValues() {
        return values;
    }

    public void setValues(double values) {
        this.values = values;
    }
}
