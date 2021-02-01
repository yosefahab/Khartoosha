package com.test.game.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;

public class ChoiceImage extends BaseDrawable {
    Image topImage;
    Image bottomImage;
    public void setBottomImage(Image bottomImage) {
        this.bottomImage = bottomImage;
    }

    boolean checked;
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public ChoiceImage(String topImagePath, String bottomImagePath, boolean checked) {
        this.topImage = new Image(new Texture(topImagePath));
        this.bottomImage = new Image(new Texture(bottomImagePath));
        this.checked = checked;
    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height){
        if(bottomImage != null && !checked){
            float padding = 10;
            bottomImage.getDrawable().draw(batch, x - (padding / 2), y - (padding / 2), topImage.getWidth() + padding, topImage.getHeight() + padding);
        }
        topImage.getDrawable().draw(batch, x, y, topImage.getWidth(), topImage.getHeight());
        if(bottomImage != null && checked){
            float padding = 10;
            bottomImage.getDrawable().draw(batch, x - (padding / 2), y - (padding / 2), topImage.getWidth() + padding, topImage.getHeight() + padding);
        }
    }
}

