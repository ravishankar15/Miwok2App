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
public class NumberFragment extends Fragment {


    private MediaPlayer mediaplayer;
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    private AudioManager am;

    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if((i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT)||(i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)) {
                mediaplayer.pause();
                mediaplayer.seekTo(0);
            }else if(i == AudioManager.AUDIOFOCUS_GAIN){
                System.out.println("Success2");
                mediaplayer.start();
            }else if(i == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };

    public NumberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.activity_words,container,false);


        am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Words> numbers = new ArrayList<Words>();

        numbers.add(new Words("One","Lutti",R.drawable.number_one,R.raw.number_one));
        numbers.add(new Words("Two","Otiiko",R.drawable.number_two,R.raw.number_two));
        numbers.add(new Words("Three","Tolookosu",R.drawable.number_three,R.raw.number_three));
        numbers.add(new Words("Four","Oyyisa",R.drawable.number_four,R.raw.number_four));
        numbers.add(new Words("Five","Massokka",R.drawable.number_five,R.raw.number_five));
        numbers.add(new Words("Six","Temmokka",R.drawable.number_six,R.raw.number_six));
        numbers.add(new Words("Seven","Keneku",R.drawable.number_seven,R.raw.number_seven));
        numbers.add(new Words("Eight","Kawinta",R.drawable.number_eight,R.raw.number_eight));
        numbers.add(new Words("Nine","Wo'e",R.drawable.number_nine,R.raw.number_nine));
        numbers.add(new Words("Ten","na'aacha",R.drawable.number_ten,R.raw.number_ten));

        WordAdapter numberadpter = new WordAdapter(getActivity(),R.color.category_numbers,numbers);

        ListView numberlist = (ListView) rootview.findViewById(R.id.list);
        numberlist.setAdapter(numberadpter);

        numberlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();

                // Request audio focus for playback
                int result = am.requestAudioFocus(afChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback.
                    mediaplayer = MediaPlayer.create(getActivity(), numbers.get(i).getAudioresourseid());
                    mediaplayer.start();
                    mediaplayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });
        return rootview;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
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
            am.abandonAudioFocus(afChangeListener);
        }
    }


}
