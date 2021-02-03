package com.test.game.menu;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class OnOffLabel {
    private boolean isOn;
    private final Label label;
    Label.LabelStyle labelStyle;

    public OnOffLabel(boolean isOn, Label label, Label.LabelStyle labelStyle) {
        this.isOn = isOn;
        this.label = label;
        this.labelStyle = labelStyle;
    }

    public boolean getIsOn() {
        return isOn;
    }

    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
        String isOnString = (isOn) ? "on" : "off";
        label.setText(isOnString);
        labelStyle.fontColor = (isOn) ? MenuStyle.active : MenuStyle.inActive;
    }

    public Label getLabel() {
        return label;
    }
}
