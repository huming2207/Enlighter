package models;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;

public class LedInfo
{
    private IntegerProperty brightness = new SimpleIntegerProperty();
    private IntegerProperty color = new SimpleIntegerProperty();
    private transient ObjectProperty<Color> colorProperty = new SimpleObjectProperty<>();
    private transient ObjectBinding<Color> colorBinding;

    public LedInfo()
    {
        // Here we need to allow bi-direct bind, simply convert it to color property instead...
        // TODO: refactor this shit...
        this.colorBinding = Bindings.createObjectBinding(() -> intToColor(getColor()), this.color);
        this.colorBinding.addListener((observable, oldValue, newValue) -> {
            System.out.println("Color changed to: " + newValue.toString());
            colorProperty.set(newValue);
        });
    }

    public Color getColorProperty()
    {
        return colorProperty.get();
    }

    public ObjectProperty<Color> colorProperty()
    {
        return colorProperty;
    }

    public void setColorProperty(Color colorProperty)
    {
        this.colorProperty.set(colorProperty);
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
