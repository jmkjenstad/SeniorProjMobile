/**
 * Author: Jayson Kjenstad
 *
 * This file manages all the API calls.  Retrofit is being used as the HTTP
 * service.  This acts as a manager for all the calls and sets the headers
 * and bodies of the calls correctly.
 */

package com.example.a7142885.doorpanes;

import java.util.List;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CalendarEventAPI {

    //GET request to get all events in system
    @GET("calendar/{getcalendarevents}/")
    Call<List<CalendarEvent>> getAllEvents(@Header("Authorization")String access_token,
                                           @Path("getcalendarevents") String user);

    //POST request to get a login token for correct user
    @FormUrlEncoded
    @POST("token")
    Call<ResponseBody> getToken(@Field ("grant_type") String field1,
                                @Field ("userName") String field2,
                                @Field ("password") String field3);

    //GET request to return a list of all rooms in system
    @GET("room/getallrooms")
    Call<List<RoomModel>>getRooms(@Header("Authorization")String access_token);

    //GET request to return a list of all faculty members
    @GET("faculty/getfacultymembers")
    Call<List<FacultyModel>>getFacultyMembers(@Header("Authorization")String access_token);

    //GET request with room number parameter.  Returns all events for given room
    @GET("calendar/getcalendareventsbyroom")
    Call<List<CalendarEvent>> getByRoom(@Header("Authorization")String access_token,
                                        @Query("Room")String room);

    //GET request with room faculty name parameter.  Returns all events for given faculty member
    @GET("calendar/getcalendareventsbyowner")
    Call<List<CalendarEvent>> getByOwner(@Header("Authorization")String access_token,
                                         @Query("Owner")String owner);


    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();


    //retrofit builder
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://doorpaneswebapp.azurewebsites.net/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


}

