package com.test.game.menu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


public class MenuStyle {
    //colors
    public static final Color active = new Color(0xd8bb23ff);
    public static final Color inActive = new Color(0xefefefff);
    public static final Color pressed = new Color(0xC4C4C4ff);

    //font
    private BitmapFont font;
    public BitmapFont getFont() {
        return font;
    }
    public void setFont(BitmapFont destroyFont) {
        this.font = destroyFont;
    }

    //label style
    private Label.LabelStyle labelStyle;
    public Label.LabelStyle getLabelStyle() {
        return labelStyle;
    }
    public void setLabelStyle(Label.LabelStyle labelStyle) {
        this.labelStyle = labelStyle;
    }

    //button style
    private TextButton.TextButtonStyle buttonStyle;
    public TextButton.TextButtonStyle getButtonStyle() {
        return buttonStyle;
    }
    public void setButtonStyle(TextButton.TextButtonStyle buttonStyle) {
        this.buttonStyle = buttonStyle;
    }



    public MenuStyle() {
        //initialize font
        font = new BitmapFont(Gdx.files.internal("fonts/destroy.fnt"),false);

        //initialize label style
        labelStyle = new Label.LabelStyle(font, MenuStyle.inActive);

        //initialize button style
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = MenuStyle.inActive;
        buttonStyle.downFontColor = MenuStyle.pressed;
        buttonStyle.checkedFontColor = MenuStyle.active;
        //menuButtonStyle.overFontColor = active;
    }

    public void dispose() {
        font.dispose();
    }
}
