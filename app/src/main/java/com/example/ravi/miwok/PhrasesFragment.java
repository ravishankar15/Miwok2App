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
public class PhrasesFragment extends Fragment {

    private MediaPlayer mediaplayer;
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    private AudioManager am;


    AudioManager.OnAudioFocusChangeListener afchangelistener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if(i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ){
                mediaplayer.pause();
                mediaplayer.seekTo(0);
            }else if(i == AudioManager.AUDIOFOCUS_GAIN){
                mediaplayer.start();
            }
            else if(i == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };

    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.activity_words,container,false);
        am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Words> phrases = new ArrayList<>();
        phrases.add(new Words("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        phrases.add(new Words("What is your name?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        phrases.add(new Words("My name is...","oyaaset...",R.raw.phrase_my_name_is));
        phrases.add(new Words("How are you feeling?" , "michәksәs?",R.raw.phrase_how_are_you_feeling));
        phrases.add(new Words("I’m feeling good.","kuchi achit",R.raw.phrase_im_feeling_good));
        phrases.add(new Words("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming));
        phrases.add(new Words("Yes, I’m coming.","hәә’ әәnәm",R.raw.phrase_im_coming));
        phrases.add(new Words("I’m coming.","әәnәm",R.raw.phrase_im_coming));
        phrases.add(new Words("Let’s go.","yoowutis",R.raw.phrase_lets_go));
        phrases.add(new Words("Come here.","әnni'nem",R.raw.phrase_come_here));


        WordAdapter phrasesadapter = new WordAdapter(getActivity(),R.color.category_phrases,phrases);

        ListView phraseslist = (ListView) rootview.findViewById(R.id.list);
        phraseslist.setAdapter(phrasesadapter);

        phraseslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();

                int result = am.requestAudioFocus(afchangelistener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mediaplayer = MediaPlayer.create(getActivity(), phrases.get(i).getAudioresourseid());
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
            am.abandonAudioFocus(afchangelistener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
