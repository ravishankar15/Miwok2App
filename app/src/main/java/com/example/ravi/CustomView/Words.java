package com.example.ravi.CustomView;

/**
 * Created by Ravi on 30-07-2016.
 */
public class Words {

    private String defaultword;
    private String translatedword;
    private int audioresourseid;
    private int imageresourseid = NO_IMAGE_ID;
    private static final int NO_IMAGE_ID = -1;

    public Words(String defaultword, String translatedword, int audioresourseid){
        this.defaultword = defaultword;
        this.translatedword = translatedword;
        this.audioresourseid = audioresourseid;
    }

    public Words(String defaultword, String translatedword, int imageresourseid,int audioresourseid){
        this.defaultword = defaultword;
        this.translatedword = translatedword;
        this.imageresourseid = imageresourseid;
        this.audioresourseid = audioresourseid;
    }

    public String getDefaultword(){
        return defaultword;

    }

    public String getTranslatedword(){
        return translatedword;
    }

    public int getImageresourseid(){
        return imageresourseid;
    }

    public int getAudioresourseid(){
        return audioresourseid;
    }

    public boolean hasImage(){
        if(imageresourseid != -1){
            return true;
        }
        else {
            return false;
        }
    }

}
