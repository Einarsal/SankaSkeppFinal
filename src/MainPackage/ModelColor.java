package MainPackage;

import java.awt.*;

public class ModelColor {

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getPosition() {
        return position;
    }

    public void setPosition(float position) {
        this.position = position;
    }

    public ModelColor(Color color, float position) {
        this.color = color;
        this.position = position;
    }
//
//    public ModelColor() {
//    }

    Color color;
    float position;


}
