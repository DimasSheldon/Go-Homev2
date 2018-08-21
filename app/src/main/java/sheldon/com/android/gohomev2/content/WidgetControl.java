package sheldon.com.android.gohomev2.content;

import android.graphics.Color;

public class WidgetControl {
    private String label, value;
    private int cvColor, iconColor;
//    private int toggleState;


    public WidgetControl(String label, String color, String value) {
        setColor(color);
        this.label = label;
        this.value = value;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public int cvColor() {
        return cvColor;
    }

    public int iconColor() {
        return iconColor;
    }

//    public int getToggleState() {
//        return toggleState;
//    }
//
//    public void setToggleState(int toggleState) {
//        this.toggleState = toggleState;
//    }
}
