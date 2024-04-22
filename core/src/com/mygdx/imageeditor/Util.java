package com.mygdx.imageeditor;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;

public class Util {
    public static int bytesToInt(byte[] bytes){
        int result = 0;
        for(int i = 0; i < bytes.length; i++){
            result += (int)bytes[i] << (8*i);
        }
        return result;
    }

    public static int[] unsignBytes(byte[] bytes){
        int[] ints = new int[bytes.length];
        for(int i = 0; i < bytes.length; i++){
            if(bytes[i] >= 0) ints[i] = bytes[i];
            else ints[i] = bytes[i] + 256;
        }
        return ints;
    }

    public static byte[] intToSignedBytes(int value){
        byte[] result = new byte[4];
        for(int i = 0; i < 4; i++){
            result[i] = (byte)((value >> (24 - 8*i)) & 255);
        }
        return result;
    }

    public static Pixmap scalePixmap(Pixmap source, Vector2 desiredSize){
        Pixmap target = new Pixmap((int)desiredSize.x, (int)desiredSize.y, Pixmap.Format.RGBA8888);
        for(int targetX = 0; targetX < target.getWidth(); targetX++){
            for(int targetY = 0; targetY < target.getHeight(); targetY++){
                int sourceX = Math.round((float)targetX/target.getWidth()*source.getWidth());
                int sourceY = Math.round((float)targetY/target.getHeight()*source.getHeight());
                target.drawPixel(targetX, targetY, source.getPixel(sourceX, sourceY));
            }
        }
        return target;
    }
}
