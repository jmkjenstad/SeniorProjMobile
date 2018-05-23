package com.example.a7142885.doorpanes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Selection;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class choose extends AppCompatActivity {

    boolean threadflag = false;
    private RadioGroup radioGroup;
    private RadioButton roombutton;
    private RadioButton facultybutton;
    public Spinner spinSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //set the layout as calendar choose
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose);

        //create all the Radio Buttons
        radioGroup = (RadioGroup) findViewById(R.id.radgroup);
        roombutton = (RadioButton) findViewById(R.id.radio_rooms);
        facultybutton = (RadioButton) findViewById(R.id.radio_fac);

        //set listener to radio group to check if button is changed
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {



            @Override

            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // find which radio button is selected
                if(checkedId == R.id.radio_rooms) {

                } else if(checkedId == R.id.radio_fac) {

                }

            }



        });

        //create the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set title to show on toolbar
        getSupportActionBar().setTitle("Choose a Calendar!");

        //Hide the fab.  This could later be used as a place to message professor
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();

        //create lists for the spinner
        final ArrayList<String> facschedule = new ArrayList<String>();
        final ArrayList<String> roomschedule = new ArrayList<String>();

        //maps username to fullname for faculty list
        final Map<String, String> map = new HashMap<String, String>();

        //set up retrofit call for rooms
        final CalendarEventAPI rooms = CalendarEventAPI.retrofit.create(CalendarEventAPI.class);
        final Call<List<RoomModel>> call = rooms.getRooms(GlobalVars.type + " " + GlobalVars.acctoken);

        //set up retrofit call for faculty members
        final CalendarEventAPI members = CalendarEventAPI.retrofit.create(CalendarEventAPI.class);
        final Call<List<FacultyModel>> call2 = members.getFacultyMembers(GlobalVars.type + " " + GlobalVars.acctoken);

        //callback for room call
        call.enqueue(new Callback<List<RoomModel>>() {
            @Override
            public void onResponse(Call<List<RoomModel>> call, Response<List<RoomModel>> response) {

                //fill rooms with the response.  Auto serialized to fit the room model
                List<RoomModel> rooms = response.body();
                int size = response.body().size();

                //loop through all models to get info we need
                for (int i = 0; i < rooms.size(); i++) {
                    String room = rooms.get(i).getRoomNumber();
                    Log.d("Room", room);
                    roomschedule.add(room);
                    //schedule.add(Integer.toString(i));
                }

                //Log.d("Flag", "TRUE!");
                threadflag = true;

            }
            //if no response...
            @Override
            public void onFailure(Call<List<RoomModel>> call, Throwable t) {

            }
        });


        //callback for faculty list
        call2.enqueue(new Callback<List<FacultyModel>>() {
            @Override
            public void onResponse(Call<List<FacultyModel>> call, Response<List<FacultyModel>> response) {

                //fill facmembers with the response.  Auto serialized to fit the faculty members model
                List<FacultyModel> facmembers = response.body();

                //loop through all models to get info we need
                for (int i = 0; i < facmembers.size(); i++) {
                    String name = facmembers.get(i).getFullName();
                    Log.d("Name", name);
                    facschedule.add(name);
                    map.put(name,facmembers.get(i).getUsername());
                    //schedule.add(Integer.toString(i));

                }

            }
            @Override
            public void onFailure(Call<List<FacultyModel>> call, Throwable t) {

            }
        });



        //dynamically add the list to the spinner
        //click listener for room radio button
        roombutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //if room is seleted, set global flag to 1
                GlobalVars.roomVfac = 1;

                //add room list to the spinner
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(choose.this, R.layout.spinner_item, roomschedule);
                adapter.notifyDataSetChanged();
                spinSchedule = (Spinner)findViewById(R.id.spinner);
                spinSchedule.setPrompt("Select schedule");
                spinSchedule.setAdapter(adapter);

            }


        } );

        //faculty button click listener
        facultybutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //if faculty is selected, set global flag to 2
                GlobalVars.roomVfac = 2;

                //add faculty list to spinner
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(choose.this, R.layout.spinner_item, facschedule);
                adapter.notifyDataSetChanged();
                spinSchedule = (Spinner)findViewById(R.id.spinner);
                spinSchedule.setPrompt("Select schedule");
                spinSchedule.setAdapter(adapter);
            }


        } );


        //create a click listener for the select button on the calendar choose layout
        final Button button = (Button) findViewById(R.id.select);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //if global flag set to room
                if (GlobalVars.roomVfac == 1) {

                    //store the selected room in global var
                    GlobalVars.selectedRoom = spinSchedule.getSelectedItem().toString();
                    Log.d("Calendar Selected", GlobalVars.selectedRoom);
                }

                //if global flag set to faculty
                if (GlobalVars.roomVfac == 2) {

                    //store the selected faculty in global flag
                    GlobalVars.selectedFaculty = map.get(spinSchedule.getSelectedItem().toString());
                    Log.d("Calendar Selected", GlobalVars.selectedFaculty);

                }

                //go to Dashboard activity
                Intent intent = new Intent(choose.this, Dashboard.class);
                startActivity(intent);
            }
        });

    }

    public void changeList()
    {

    }




}
