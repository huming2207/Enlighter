package models;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

public class LedInfo
{
    private IntegerProperty brightness;
    private IntegerProperty color;
    private transient ObjectBinding<Color> colorBinding;

    public LedInfo()
    {
        this.brightness = new SimpleIntegerProperty();
        this.color = new SimpleIntegerProperty();
        this.colorBinding = Bindings.createObjectBinding(() -> intToColor(getColor()), colorProperty());
    }

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


    public Color getColorBinding()
    {
        return colorBinding.get();
    }

    public ObjectBinding<Color> colorBindingProperty()
    {
        return colorBinding;
    }

    /**
     * Ref: https://stackoverflow.com/questions/49255738/javafx-how-to-bind-rgb-value-in-integerproperty-to-colorpicker
     * @author Thom
     * @param value 24bit RGB integer
     * @return The color object
     */
    private static Color intToColor(int value)
    {
        int r = (value >>> 16) & 0xFF;
        int g = (value >>> 8) & 0xFF;
        int b = value & 0xFF;
        return Color.rgb(r,g,b);
    }

    /**
     * Always do a full clone instead of passing a new instance to the bound instance, or it will not work.
     * @param ledInfo new LedInfo instance to be cloned.
     */
    public void clone(LedInfo ledInfo)
    {
        this.color.set(ledInfo.getColor());
        this.brightness.set(ledInfo.getBrightness());
    }
}
