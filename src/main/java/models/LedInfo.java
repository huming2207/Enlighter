package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class LedInfo
{
    private IntegerProperty brightness;
    private IntegerProperty color;

    public int getBrightness()
    {
        return brightness.get();
    }

    public IntegerProperty brightnessProperty()
    {
        return brightness;
    }

    public void setBrightness(int brightness)
    {
        this.brightness.set(brightness);
    }

    public int getColor()
    {
        return color.get();
    }

    public IntegerProperty colorProperty()
    {
        return color;
    }

    public void setColor(int color)
    {
        this.color.set(color);
    }
}
