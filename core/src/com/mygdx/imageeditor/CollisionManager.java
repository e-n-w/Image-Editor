package com.mygdx.imageeditor;

import com.badlogic.gdx.math.Vector2;

public class CollisionManager {
    public static CollisionManager Instance;

    public CollisionManager(){
        Instance = this;
    }

    public IClickable getClicked(Vector2 coordinates){
        for(IClickable clickable : InputManager.Instance.Clickables){
            Rec2D clickableRec;
            if(clickable instanceof Rec2D){
                clickableRec = (Rec2D)clickable;
            }else{
                continue;
            }
            if(coordinates.x >= clickableRec.Position.x && coordinates.x <= clickableRec.Position.x + clickableRec.Scale.x){
                if(coordinates.y >= clickableRec.Position.y && coordinates.y <= clickableRec.Position.y + clickableRec.Scale.y){
                    return clickable;
                }
            }
        }
        return null;
    }

    public IHoverable getHovered(Vector2 coordinates) {
        for(IHoverable hoverable : InputManager.Instance.Hoverables){
            Rec2D hoverableRec;
            if(hoverable instanceof Rec2D){
                hoverableRec = (Rec2D)hoverable;
            }else{
                continue;
            }
            if(coordinates.x >= hoverableRec.Position.x && coordinates.x <= hoverableRec.Position.x + hoverableRec.Scale.x){
                if(coordinates.y >= hoverableRec.Position.y && coordinates.y <= hoverableRec.Position.y + hoverableRec.Scale.y){
                    return hoverable;
                }
            }
        }
        return null;
    }
}
