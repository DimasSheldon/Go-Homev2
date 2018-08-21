package sheldon.com.android.gohomev2.content;

import android.graphics.Color;

import sheldon.com.android.gohomev2.R;

public class WidgetMonitor {
    private String label, value;
    private int icon, cvColor, iconColor;

    //WidgetMonitor("ion ion-waterdrop", "HUMIDITY", "bg-blue", "NA")
    public WidgetMonitor(String icon, String label, String color, String value) {
        setIcon(icon);
        setColor(color);
        this.label = label;
        this.value = value;
    }

    public void setIcon(String icon) {
        switch (icon) {
            case "ion ion-thermometer":
                this.icon = R.mipmap.ic_temperature_foreground;
                break;
            case "ion ion-android-alert":
//                this.icon = R.mipmap.ic_temperature_foreground;
                break;
            case "ion ion-ios-lightbulb":
                this.icon = R.mipmap.ic_light_bulb_white_foreground;
                break;
            case "ion ion-waterdrop":
                this.icon = R.mipmap.ic_humidity_foreground;
                break;
            default:
                this.icon = R.drawable.logo_white;
                break;
        }
    }

    public void setColor(String color) {
        switch (color) {
            case "bg-red":
                this.cvColor = Color.parseColor("#d32f2f");
                this.iconColor = Color.parseColor("#9a0007");
                break;
            case "bg-blue":
                this.cvColor = Color.parseColor("#1565c0");
                this.iconColor = Color.parseColor("#003c8f");
                break;
            case "bg-orange":
                this.cvColor = Color.parseColor("#ff8f00");
                this.iconColor = Color.parseColor("#c56000");
                break;
            default:
                this.cvColor = Color.GRAY;
                this.iconColor = Color.DKGRAY;
                break;
        }
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public int getIcon() {
        return icon;
    }

    public int cvColor() {
        return cvColor;
    }

    public int iconColor() {
        return iconColor;
    }
}
