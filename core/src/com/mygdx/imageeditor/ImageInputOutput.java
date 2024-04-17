package com.mygdx.imageeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;

public class ImageInputOutput {
    public static ImageInputOutput Instance;

    public ImageInputOutput(){
        Instance = this;
    }

    public Pixmap loadImage(String filePath){
        byte[] fileBytes = Gdx.files.internal(filePath).readBytes();
        int[] fileIntData = Util.unsignBytes(fileBytes);
        if(fileBytes[0] != 'B' || fileBytes[1] != 'M') return null;
        byte[] fileSize = {fileBytes[2], fileBytes[3], fileBytes[4], fileBytes[5]};
        byte[] start = {fileBytes[10], fileBytes[11], fileBytes[12], fileBytes[13]};
        byte[] widthBytes = {fileBytes[18], fileBytes[19], fileBytes[20], fileBytes[21]};
        byte[] heightBytes = {fileBytes[22], fileBytes[23], fileBytes[24], fileBytes[25]};
        byte[] bitsPerPixel = {fileBytes[28], fileBytes[29]};
        int startPoint = Util.bytesToInt(start);
        int width = Util.bytesToInt(widthBytes);
        int height = Util.bytesToInt(heightBytes);
        if(Util.bytesToInt(bitsPerPixel) != 24) return null;

        Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
        int r, g, b;
        int x = 0;
        int y = height - 1;
        for(int i = startPoint; i < fileIntData.length - 3; i += 3){
            b = fileIntData[i];
            g = fileIntData[i+1];
            r = fileIntData[i+2];
            float bFloat = b/255.0f;
            float gFloat = g/255.0f;
            float rFloat = r/255.0f;
            pixmap.setColor(new Color(rFloat, gFloat, bFloat, 1.0f));
            pixmap.drawPixel(x, y);
            x++;
            if(x >= width){
                x = 0;
                y--;
            }
        }
        return pixmap;
    }
}
