/**
 * Author: Jayson Kjenstad
 *
 * This file contains the code for the main Dashboard activity.  This is where the calendar is
 * displayed.  API calls are used to get the correct data to display using the open source calendar
 * framework written by alamkanak, found on Git Hub.  It is titled Android Week View
 */
package com.example.a7142885.doorpanes;

//all needed imports for this project
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import org.json.JSONException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, WV.EventClickListener, WV.MonthChangeListener, WV.EventLongPressListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private WV mWeekView;
    private ArrayList<WVEvent> mNewEvents;
    WVEvent event;
    List<WVEvent> Allevents;

    PopupWindow popUp;
    LinearLayout layout;
    TextView tv;
    DrawerLayout.LayoutParams params;
    LinearLayout mainLayout;
    boolean click = true;

    Point p;

    public Dashboard() throws JSONException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);
        // Get a reference for the week view in the layout.
        mWeekView = (WV) findViewById(R.id.weekView);

// Set an action when any event is clicked.
        mWeekView.setOnEventClickListener(this);

// The week view has infinite scrolling horizontally. We have to provide the events of a
// month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

// Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

        mNewEvents = new ArrayList<WVEvent>();
        Allevents = new ArrayList<WVEvent>();

        popUp = new PopupWindow(this);
        layout = new LinearLayout(this);
        mainLayout = new LinearLayout(this);
        tv = new TextView(this);



        //create the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (GlobalVars.roomVfac == 1)
        {
            getSupportActionBar().setTitle(GlobalVars.selectedRoom);
        }
        else
        {
            getSupportActionBar().setTitle(GlobalVars.selectedFaculty);
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();

        //create the navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    //handles when the drawer is returned
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        // Handle navigation view item clicks here.

        final Handler h = new Handler();
        final int delay = 10000; //milliseconds

        h.postDelayed(new Runnable(){
            public void run(){

                int id = item.getItemId();

                if (id == R.id.nav_sync) {

                    if (Allevents.size() > 0) {
                        Allevents.clear();
                    }

                    //create retrofit calls
                    Log.d("YourTag", "YourOutput");
                    final CalendarEventAPI events = CalendarEventAPI.retrofit.create(CalendarEventAPI.class);
                    final CalendarEventAPI events2 = CalendarEventAPI.retrofit.create(CalendarEventAPI.class);
                    final Call<List<CalendarEvent>> call =
                            events.getByRoom(GlobalVars.type + " " + GlobalVars.acctoken, GlobalVars.selectedRoom);
                    final Call<List<CalendarEvent>> call2 =
                            events2.getByOwner(GlobalVars.type + " " + GlobalVars.acctoken, GlobalVars.selectedFaculty);

                    //Log.d("Yes", events.toString());

                //retrofit callbacks
                    //room selection
                if (GlobalVars.roomVfac == 1) {
                    call.enqueue(new Callback<List<CalendarEvent>>() {
                        @Override
                        public void onResponse(Call<List<CalendarEvent>> call, Response<List<CalendarEvent>> response) {

                            List<CalendarEvent> events1 = response.body();
                            Log.d("Code", Integer.toString(response.code()));
                            if (response.code() == 200) {
                                int size = response.body().size();
                                int id = 0;

                                List<WVEvent> Allevents2 = new ArrayList<WVEvent>();
                                Calendar startTime = Calendar.getInstance();
                                Calendar endTime = (Calendar) startTime.clone();
                                int blah = Color.parseColor("#2D3142");
                                int red = Color.RED;

                                //loop through all models to get data needed
                                for (int i = 0; i < events1.size(); i++) {

                                    //parse the time
                                    String year = events1.get(i).getStartTime().substring(0, 4);
                                    String month = events1.get(i).getStartTime().substring(5, 7);
                                    String day = events1.get(i).getStartTime().substring(8, 10);
                                    String hour = events1.get(i).getStartTime().substring(11, 13);
                                    String minute = events1.get(i).getStartTime().substring(14, 16);
                                    String second = events1.get(i).getStartTime().substring(17, 19);

                                    String eyear = events1.get(i).getEndTime().substring(0, 4);
                                    String emonth = events1.get(i).getEndTime().substring(5, 7);
                                    String eday = events1.get(i).getEndTime().substring(8, 10);
                                    String ehour = events1.get(i).getEndTime().substring(11, 13);
                                    String eminute = events1.get(i).getEndTime().substring(14, 16);
                                    String esecond = events1.get(i).getEndTime().substring(17, 19);

                                    startTime = Calendar.getInstance();
                                    startTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
                                    startTime.set(Calendar.MINUTE, Integer.parseInt(minute));
                                    startTime.set(Calendar.MONTH, Integer.parseInt(month) - 1);
                                    startTime.set(Calendar.YEAR, Integer.parseInt(year));
                                    startTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
                                    startTime.set(Calendar.SECOND, Integer.parseInt(second));
                                    endTime = (Calendar) startTime.clone();
                                    endTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(ehour));
                                    endTime.set(Calendar.MINUTE, Integer.parseInt(eminute));
                                    endTime.set(Calendar.MONTH, Integer.parseInt(emonth) - 1);
                                    endTime.set(Calendar.YEAR, Integer.parseInt(eyear));
                                    endTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(eday));
                                    endTime.set(Calendar.SECOND, Integer.parseInt(esecond));

                                    endTime.add(Calendar.SECOND, -1);

                                    String description = events1.get(i).getDescription();
                                    String owner = events1.get(i).getEventOwner();

                                    event = new WVEvent(id++, events1.get(i).getTitle(), startTime, endTime, description, owner);
                                    if (events1.get(i).getCancelled() == false) {
                                        event.setColor(blah);
                                    } else {
                                        event.setColor(red);
                                    }

                                    Log.d("Month", event.toString());
                                    Allevents.add(event);
                                }


                                //set the times
                                Calendar startOfMonth = Calendar.getInstance();
                                startOfMonth.set(Calendar.YEAR, 2017);
                                startOfMonth.set(Calendar.MONTH, 1);
                                startOfMonth.set(Calendar.DAY_OF_MONTH, 1);
                                startOfMonth.set(Calendar.HOUR_OF_DAY, 0);
                                startOfMonth.set(Calendar.MINUTE, 0);
                                startOfMonth.set(Calendar.SECOND, 0);
                                startOfMonth.set(Calendar.MILLISECOND, 0);
                                Calendar endOfMonth = (Calendar) startOfMonth.clone();
                                endOfMonth.set(Calendar.DAY_OF_MONTH, endOfMonth.getMaximum(Calendar.DAY_OF_MONTH));
                                endOfMonth.set(Calendar.HOUR_OF_DAY, 23);
                                endOfMonth.set(Calendar.MINUTE, 59);
                                endOfMonth.set(Calendar.SECOND, 59);

                                //fill events to list of Allevents, this is the list of event displayed
                                for (WVEvent event : Allevents2) {
                                    if (event.getEndTime().getTimeInMillis() > startOfMonth.getTimeInMillis() &&
                                            event.getStartTime().getTimeInMillis() < endOfMonth.getTimeInMillis()) {
                                        Allevents.add(event);

                                    }
                                }

                                mWeekView.notifyDatasetChanged();
                            }


                        }

                        @Override
                        public void onFailure(Call<List<CalendarEvent>> call, Throwable t) {
                            final TextView textView = (TextView) findViewById(R.id.textView);
                            textView.setText("Something went wrong: " + t.getMessage());

                            Log.d("Error", "Error");


                        }
                    });
                }


                if (GlobalVars.roomVfac == 2) {
                    call2.enqueue(new Callback<List<CalendarEvent>>() {
                        @Override
                        public void onResponse(Call<List<CalendarEvent>> call, Response<List<CalendarEvent>> response) {
                            List<CalendarEvent> events1 = response.body();
                            Log.d("Code", Integer.toString(response.code()));
                            if (response.code() == 200) {
                                int size = response.body().size();
                                int id = 0;


                                List<WVEvent> Allevents2 = new ArrayList<WVEvent>();
                                Calendar startTime = Calendar.getInstance();
                                Calendar endTime = (Calendar) startTime.clone();
                                int blah = Color.parseColor("#2D3142");
                                int red = Color.RED;

                                //fill models with data
                                for (int i = 0; i < events1.size(); i++) {

                                    //parse time
                                    String year = events1.get(i).getStartTime().substring(0, 4);
                                    String month = events1.get(i).getStartTime().substring(5, 7);
                                    String day = events1.get(i).getStartTime().substring(8, 10);
                                    String hour = events1.get(i).getStartTime().substring(11, 13);
                                    String minute = events1.get(i).getStartTime().substring(14, 16);
                                    String second = events1.get(i).getStartTime().substring(17, 19);

                                    String eyear = events1.get(i).getEndTime().substring(0, 4);
                                    String emonth = events1.get(i).getEndTime().substring(5, 7);
                                    String eday = events1.get(i).getEndTime().substring(8, 10);
                                    String ehour = events1.get(i).getEndTime().substring(11, 13);
                                    String eminute = events1.get(i).getEndTime().substring(14, 16);
                                    String esecond = events1.get(i).getEndTime().substring(17, 19);

                                    startTime = Calendar.getInstance();
                                    startTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
                                    startTime.set(Calendar.MINUTE, Integer.parseInt(minute));
                                    startTime.set(Calendar.MONTH, Integer.parseInt(month) - 1);
                                    startTime.set(Calendar.YEAR, Integer.parseInt(year));
                                    startTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
                                    startTime.set(Calendar.SECOND, Integer.parseInt(second));
                                    endTime = (Calendar) startTime.clone();
                                    endTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(ehour));
                                    endTime.set(Calendar.MINUTE, Integer.parseInt(eminute));
                                    endTime.set(Calendar.MONTH, Integer.parseInt(emonth) - 1);
                                    endTime.set(Calendar.YEAR, Integer.parseInt(eyear));
                                    endTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(eday));
                                    endTime.set(Calendar.SECOND, Integer.parseInt(esecond));

                                    String description = events1.get(i).getDescription();
                                    String owner = events1.get(i).getEventOwner();

                                    event = new WVEvent(id++, events1.get(i).getTitle(), startTime, endTime, description, owner);

                                    if (events1.get(i).getCancelled() == false) {
                                        event.setColor(blah);
                                    } else {
                                        event.setColor(red);
                                    }


                                    Log.d("Month", event.toString());
                                    Allevents.add(event);
                                }


                                Calendar startOfMonth = Calendar.getInstance();
                                startOfMonth.set(Calendar.YEAR, 2017);
                                startOfMonth.set(Calendar.MONTH, 1);
                                startOfMonth.set(Calendar.DAY_OF_MONTH, 1);
                                startOfMonth.set(Calendar.HOUR_OF_DAY, 0);
                                startOfMonth.set(Calendar.MINUTE, 0);
                                startOfMonth.set(Calendar.SECOND, 0);
                                startOfMonth.set(Calendar.MILLISECOND, 0);
                                Calendar endOfMonth = (Calendar) startOfMonth.clone();
                                endOfMonth.set(Calendar.DAY_OF_MONTH, endOfMonth.getMaximum(Calendar.DAY_OF_MONTH));
                                endOfMonth.set(Calendar.HOUR_OF_DAY, 23);
                                endOfMonth.set(Calendar.MINUTE, 59);
                                endOfMonth.set(Calendar.SECOND, 59);


                                for (WVEvent event : Allevents2) {
                                    if (event.getEndTime().getTimeInMillis() > startOfMonth.getTimeInMillis() &&
                                            event.getStartTime().getTimeInMillis() < endOfMonth.getTimeInMillis()) {
                                        Allevents.add(event);

                                    }
                                }

                                mWeekView.notifyDatasetChanged();
                            }


                        }

                        @Override
                        public void onFailure(Call<List<CalendarEvent>> call, Throwable t) {
                            final TextView textView = (TextView) findViewById(R.id.textView);
                            textView.setText("Something went wrong: " + t.getMessage());
                            Log.d("no", "no");
                        }
                    });
                }






                }


                h.postDelayed(this, delay);
            }
        }, delay);






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //when a week event is tapped, show a little notation on the bottom of the screen
    @Override
    public void onEventClick(WVEvent event, RectF eventRect) {

            String description = event.getDescription();
            String owner = event.getOwner();
            String name = event.getName();

            Toast.makeText(Dashboard.this,
                    "Your Message", Toast.LENGTH_LONG).show();
            showPopup(Dashboard.this, p, description, owner, name);

    }

    private void showPopup(final Activity context, Point p, String description, String owner, String name) {

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;


        int popupWidth = 600;
        int popupHeight = 500;

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.rl_custom_layout);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.custom_layout, viewGroup);

        // Creating the PopupWindow

        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 30;
        int OFFSET_Y = 30;

        int[] location = new int[2];
        p = new Point();
        p.x = location[0];
        p.y = location[1];

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        ((TextView)popup.getContentView().findViewById(R.id.tv)).setText("Event Name: " + name +"\nEvent owner: " + owner + "\nEvent Description: " +description);

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);

        // Getting a reference to Close button, and close the popup when clicked.
        Button close = (Button) layout.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
    }

    @Override
    public void onEventLongPress(WVEvent event, RectF eventRect) {
        Toast.makeText(this, "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    //loads in and parses a JSON endpoint
    public String loadJSONFromAsset() throws IOException {


        Writer writer = new StringWriter();
        char[] buffer = new char[1024];


        return "json";

    }


    int callcount = 3;


    //when the month is changed, load in all the new events based on the parameters and show
    //on the calendar
    //gets called on .notifyDatasetChanged
    @Override
    public List<WVEvent> onMonthChange(final int newYear, final int newMonth) {

        Calendar startOfMonth = Calendar.getInstance();
        startOfMonth.set(Calendar.YEAR, newYear);
        startOfMonth.set(Calendar.MONTH,  newMonth - 1);
        startOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        startOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        startOfMonth.set(Calendar.MINUTE, 0);
        startOfMonth.set(Calendar.SECOND, 0);
        startOfMonth.set(Calendar.MILLISECOND, 0);
        Calendar endOfMonth = (Calendar) startOfMonth.clone();
        endOfMonth.set(Calendar.DAY_OF_MONTH, endOfMonth.getMaximum(Calendar.DAY_OF_MONTH));
        endOfMonth.set(Calendar.HOUR_OF_DAY, 23);
        endOfMonth.set(Calendar.MINUTE, 59);
        endOfMonth.set(Calendar.SECOND, 59);

        List<WVEvent> Allevents3 = new ArrayList<WVEvent>();

        for (WVEvent event : Allevents) {
            if (event.getEndTime().getTimeInMillis() > startOfMonth.getTimeInMillis() &&
                    event.getStartTime().getTimeInMillis() < endOfMonth.getTimeInMillis()) {
                Allevents3.add(event);

            }
        }

        //return events to be displayed
        return Allevents3;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Dashboard Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }



}
