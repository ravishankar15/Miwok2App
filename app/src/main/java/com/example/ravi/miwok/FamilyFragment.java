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
public class FamilyFragment extends Fragment {

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
            }else if (i == AudioManager.AUDIOFOCUS_GAIN){
                mediaplayer.start();
            }else if (i == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };


    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.activity_words,container,false);
        am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Words> family = new ArrayList<Words>();
        family.add(new Words("father","әpә",R.drawable.family_father,R.raw.family_father));
        family.add(new Words("mother","әṭa",R.drawable.family_mother,R.raw.family_mother));
        family.add(new Words("son","angsi",R.drawable.family_son,R.raw.family_son));
        family.add(new Words("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        family.add(new Words("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        family.add(new Words("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        family.add(new Words("older sister","teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        family.add(new Words("younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        family.add(new Words("grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        family.add(new Words("grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));

        WordAdapter familyadapter = new WordAdapter(getActivity(),R.color.category_family,family);

        ListView familylist = (ListView) rootview.findViewById(R.id.list);
        familylist.setAdapter(familyadapter);

        familylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();

                int result = am.requestAudioFocus(audioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mediaplayer = MediaPlayer.create(getActivity(), family.get(i).getAudioresourseid());
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
