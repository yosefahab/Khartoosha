package com.test.game.menu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;


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

    //heading label style
    private Label.LabelStyle headingLabelStyle;
    public Label.LabelStyle getHeadingLabelStyle() {
        return headingLabelStyle;
    }
    public void setHeadingLabelStyle(Label.LabelStyle headingLabelStyle) {
        this.headingLabelStyle = headingLabelStyle;
    }

    /*//on off label style
    private Label.LabelStyle onOffLabelStyle;
    public Label.LabelStyle getOnOffLabelStyle() {
        return onOffLabelStyle;
    }
    public void setOnOffLabelStyle(Label.LabelStyle onOffLabelStyle) {
        this.onOffLabelStyle = onOffLabelStyle;
    }*/

    //text button style
    private TextButton.TextButtonStyle textButtonStyle;
    public TextButton.TextButtonStyle getTextButtonStyle() {
        return textButtonStyle;
    }
    public void setTextButtonStyle(TextButton.TextButtonStyle textButtonStyle) {
        this.textButtonStyle = textButtonStyle;
    }

    //image button style
    private ImageButton.ImageButtonStyle imageButtonStyle;
    public ImageButton.ImageButtonStyle getImageButtonStyle() {
        return imageButtonStyle;
    }

    public MenuStyle() {
        //initialize font
        font = new BitmapFont(Gdx.files.internal("fonts/destroy.fnt"),false);

        //initialize heading label style
        headingLabelStyle = new Label.LabelStyle(font, MenuStyle.inActive);
        headingLabelStyle.fontColor = MenuStyle.pressed;

        /*//initialize on off label style
        onOffLabelStyle = new Label.LabelStyle(font, MenuStyle.inActive);
        onOffLabelStyle.fontColor = MenuStyle.pressed;*/

        //initialize text button style
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.fontColor = MenuStyle.inActive;
        textButtonStyle.downFontColor = MenuStyle.pressed;
        textButtonStyle.checkedFontColor = MenuStyle.active;
        //menuButtonStyle.overFontColor = active;
    }
    
    void setImageButtonStyle(String topImagePath, String bottomImagePath) {
        //IMPORTANT: image button style has to be updated with every button via setImageButtonStyle, unlike text buttons

        //initialize image button menu style
        imageButtonStyle = new ImageButton.ImageButtonStyle();

        Sprite choice = new Sprite(new Texture(topImagePath)); //menu/menu_char1_inactive.png
        Sprite choiceBackground = new Sprite(new Texture(bottomImagePath)); //menu/menu_char_choice_active.png
        /*Sprite choiceWithTransparency = new Sprite(choice);
        choiceWithTransparency.setAlpha(0.5f);*/

        //choiceBackground.setScale(choice.getScaleX(), choice.getScaleY());
        choice.setSize(choice.getWidth()-35,choice.getHeight()-35);
        choiceBackground.setSize(choice.getWidth()+35, choice.getHeight()+35);
        SpriteDrawable choiceDrawable = new SpriteDrawable(choice);
        SpriteDrawable choiceBackgroundDrawable = new SpriteDrawable(choiceBackground);
        //SpriteDrawable choiceWithTransparencyDrawable = new SpriteDrawable(choiceWithTransparency);
        imageButtonStyle.up = choiceDrawable;
        imageButtonStyle.checked = choiceBackgroundDrawable;
        imageButtonStyle.imageChecked = choiceDrawable;
        //TODO: delete after handleKeyboard() is implemented in ChoiceMenuController
        /*imageButtonStyle.over = choiceBackgroundDrawable;
        imageButtonStyle.imageOver = choiceDrawable;*/
    }

    public void dispose() {
        font.dispose();
    }
}
