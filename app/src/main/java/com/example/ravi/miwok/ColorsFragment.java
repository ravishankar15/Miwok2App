package com.example.ravi.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ravi.Adapter.WordAdapter;
import com.example.ravi.CustomView.Words;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {

    private MediaPlayer mediaplayer;
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    private AudioManager am;
    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if(i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mediaplayer.pause();
                mediaplayer.seekTo(0);
            }else if(i == AudioManager.AUDIOFOCUS_GAIN){
                mediaplayer.start();
            }else if (i == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };

    public ColorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_words,container,false);
        am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Words> colors = new ArrayList<Words>();
        colors.add(new Words("red","weṭeṭṭi",R.drawable.color_red,R.raw.color_red));
        colors.add(new Words("green","chokokki",R.drawable.color_green,R.raw.color_green));
        colors.add(new Words("brown","ṭakaakki",R.drawable.color_brown,R.raw.color_brown));
        colors.add(new Words("gray","ṭopoppi",R.drawable.color_gray,R.raw.color_gray));
        colors.add(new Words("black","kululli",R.drawable.color_black,R.raw.color_black));
        colors.add(new Words("white","kelelli",R.drawable.color_white,R.raw.color_black));
        colors.add(new Words("dusty yellow","ṭopiisә",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        colors.add(new Words("mustard yellow","chiwiiṭә",R.drawable.color_mustard_yellow,R.raw.color_dusty_yellow));

        WordAdapter coloradapter = new WordAdapter(getActivity(),R.color.category_colors,colors);

        ListView colorlist = (ListView) rootview.findViewById(R.id.list);
        colorlist.setAdapter(coloradapter);

        colorlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();

                int result = am.requestAudioFocus(audioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mediaplayer = MediaPlayer.create(getActivity(), colors.get(i).getAudioresourseid());
                    mediaplayer.start();
                    mediaplayer.setOnCompletionListener(onCompletionListener);

                }
            }
        });
        return rootview;
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaplayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaplayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaplayer = null;
            am.abandonAudioFocus(audioFocusChangeListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
