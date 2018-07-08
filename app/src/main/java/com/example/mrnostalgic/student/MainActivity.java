package com.example.mrnostalgic.student;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    public class Tone{
        int toneType;
        String toneName;

        Tone(int toneType, String toneName){
            this.toneType= toneType;
            this.toneName = toneName;
        }

        @Override
        public String toString() {
            return toneName;
        }

    }

    class ToneArrayAdapter extends ArrayAdapter<Tone> {

        Tone[] toneArray;

        public ToneArrayAdapter(Context context, int resource, Tone[] objects) {
            super(context, resource, objects);
            toneArray = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;

            if(row==null){
                LayoutInflater inflater = getLayoutInflater();
                row=inflater.inflate(R.layout.rows, parent, false);
            }



            TextView listName =(TextView)row.findViewById(R.id.name);
            listName.setText(toneArray[position].toneName);

            TextView listDesc=(TextView)row.findViewById(R.id.desc);

            return row;
        }
    }

    Tone[] tones = new Tone[]{

            new Tone(ToneGenerator.TONE_DTMF_0, "Dialpad 0"),
            new Tone(ToneGenerator.TONE_DTMF_1, "Dialpad 1"),
            new Tone(ToneGenerator.TONE_DTMF_2, "Dialpad 2"),
            new Tone(ToneGenerator.TONE_DTMF_3, "Dialpad 3"),
            new Tone(ToneGenerator.TONE_DTMF_4, "Dialpad 4"),
            new Tone(ToneGenerator.TONE_DTMF_5, "Dialpad 5"),
            new Tone(ToneGenerator.TONE_DTMF_6, "Dialpad 6"),
            new Tone(ToneGenerator.TONE_DTMF_7, "Dialpad 7"),
            new Tone(ToneGenerator.TONE_DTMF_8, "Dialpad 8"),
            new Tone(ToneGenerator.TONE_DTMF_9, "Dialpad 9"),
            new Tone(ToneGenerator.TONE_DTMF_C, "C")
    };

    SeekBar toneDurBar;

    ListView toneList;
    public ToneArrayAdapter toneListAdapter;

    ToneGenerator toneGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);

        toneDurBar = (SeekBar) findViewById(R.id.tonedur);
        toneDurBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }
        });

        toneList = (ListView) findViewById(R.id.tonelist);
        toneListAdapter = new ToneArrayAdapter(this,
                R.layout.rows, tones);

        toneList.setAdapter(toneListAdapter);

        toneList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Tone t = (Tone) parent.getItemAtPosition(position);
                int type = t.toneType;
                int durationMs = toneDurBar.getProgress();
                toneGenerator.startTone(type, durationMs);

            }
        });

        final Context context = this;
        Button button;

        super.onCreate(savedInstanceState);

        button = (Button) findViewById(R.id.buttonAlert);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                alertDialogBuilder
                        .setMessage("Do you want to exit this app?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MainActivity.this.finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
            }
        });
    }
}
