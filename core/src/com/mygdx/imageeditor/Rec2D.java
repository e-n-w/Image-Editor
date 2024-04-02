package com.mygdx.imageeditor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.Vector2;

public class Rec2D {
    public Vector2 Scale;
    public Vector2 Position;
    public Vector2 Velocity;
    public Texture RecTexture;
    private Pixmap _pixelMap;
    private Color _color;

    public Rec2D(Vector2 scale, Vector2 position, Color color){
        Scale = scale;
        Position = position;
        _color = color;
        generateTexture();
    }

    private void generateTexture(){
        _pixelMap = new Pixmap(((int)Scale.x), ((int)Scale.y), Format.RGBA8888);
        _pixelMap.setColor(_color);
        for(int x = 0; x < (int)Scale.x; x++){
            for(int y = 0; y < (int)Scale.y; y++){
                _pixelMap.drawPixel(x, y);
            }
        }
        RecTexture = new Texture(_pixelMap);
    }

    public void update(){
        
    }

    public void changeColor(Color newColor){
        _color = newColor;
        generateTexture();
    }
}
