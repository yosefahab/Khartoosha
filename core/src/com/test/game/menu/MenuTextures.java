package com.test.game.menu;

public interface MenuTextures {
    void chosenTexture(int dynamicTextureNum);
    void drawStatic();
    void checkBoundsAndDrawDynamic(MenuTextureDimDynamic[]dim, int dynamicTextureNum);
}
