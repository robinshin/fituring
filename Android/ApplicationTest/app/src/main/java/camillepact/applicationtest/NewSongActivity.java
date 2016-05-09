package camillepact.applicationtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView.OnItemSelectedListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Button.*;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class NewSongActivity extends AppCompatActivity {
    Client client = new Client();
    public static final int ELECTRO = 0;
    public static final int DISCO = 1;
    public static final int ORIENTAL = 2;
    public static final int HIPHOP = 3;

    private static int volume = 50;
    private static int style = 2;
    private static int musicOnOff = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_song);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(musicOnOff == 0) {
                    musicOnOff = 1;
                    button.setText("STOP");
                }
                else{
                    musicOnOff = 0;
                    button.setText("LET'S DANCE");
                }
            }
        });

        new Thread(client).start();

        SeekBar seekbar = (SeekBar) findViewById(R.id.progressBar);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                volume = seekBar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        List list = new ArrayList();
        list.add("Electro");
        list.add("Disco");
        list.add("Oriental");
        list.add("Hip-Hop");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String text = spinner.getSelectedItem().toString();
                //Log.d("Spinner", text);
                if(text.equals("Electro")) style = ELECTRO;
                else if(text.equals("Disco")) style = DISCO;
                else if(text.equals("Oriental")) style = ORIENTAL;
                else if(text.equals("Hip-Hop")) style = HIPHOP;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }

    public static String getInfo(){
        return Integer.toString(volume*100 + style*10 + musicOnOff);
    }
}
