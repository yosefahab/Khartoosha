package com.test.game.screens.menu_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class testScreen implements Screen {
    //TODO: delete once done
    boolean once = false;
    Stage stage;
    Table table;
    ImageButton.ImageButtonStyle imageButtonStyle;
    ImageButton imageButton;

    public testScreen() {
        imageButtonStyle = new ImageButton.ImageButtonStyle();
        /*imageButtonStyle.over = choiceImage;
        choiceImage.setChecked(true);
        imageButtonStyle.down = choiceImage;
        imageButtonStyle.checked = choiceImage;
        choiceImage.setBottomImage(null); // null is handled in ChoiceImage, in that case draw() only draws the choice
        imageButtonStyle.up = choiceImage;*/





        /*Image image1 = new Image(new Texture("menu/menu_char1_inactive.png"));
        Image image2 = new Image(new Texture("menu/menu_char_choice_active.png"));

        imageButtonStyle.up = image1.getDrawable();
        imageButtonStyle.down = image2.getDrawable();
        imageButtonStyle.imageDown = image1.getDrawable();*/

        imageButton = new ImageButton(imageButtonStyle);
//        imageButton.setTransform(true);
//        imageButton.setScale(0.3f);
        //imageButton.setSize(100,100);
        imageButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("clicked");
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

            }
        });
    }

    @Override
    public void show() {
        stage = new Stage();
        table = new Table();
        table.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        table.setFillParent(false);
        Gdx.input.setInputProcessor(stage);
        table.add(imageButton);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
