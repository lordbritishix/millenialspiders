package com.unitutoring.unitutoring;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.unitutoring.unitutoring.events.LoginEvent;
import com.unitutoring.unitutoring.events.MatchEvent;
import com.unitutoring.unitutoring.events.RegisterEvent;
import com.unitutoring.unitutoring.models.Tutor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rickychang on 2016-09-24.
 */
public class BackendSingleton {
    public static final String ACCOUNT_TYPE_STUDENT = "STUDENT";
    private static final String BASE_URL = "https://garbagehound-142922.appspot.com/";

    private static BackendSingleton ourInstance = new BackendSingleton();
    private static RequestQueue sRequestQueue;

    public static void init(Context context) {
        if (sRequestQueue == null) {
            sRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
    }

    public static BackendSingleton getInstance() {
        return ourInstance;
    }

    private BackendSingleton() {
    }

    /**
     * Register a new user. Upon completion a {@link RegisterEvent} is emitted.
     *
     * @param username
     * @param password
     * @param accountType
     */
    public void register(String username, String password, String accountType) {
        String url = BASE_URL + "registration";

        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("accountType", accountType);

        FormDataRequest request = new FormDataRequest(Request.Method.POST, url, params,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        RegisterEvent event = new RegisterEvent();
                        event.isSuccessful = false;

                        try {
                            JSONObject responseJsonObject = new JSONObject(response);
                            if (responseJsonObject.has("success")) {
                                event.isSuccessful = responseJsonObject.getBoolean("success");
                            }

                            BusSingleton.getInstance().post(event);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        RegisterEvent event = new RegisterEvent();
                        event.isSuccessful = false;

                        if (error != null && error.networkResponse != null) {
                            event.httpErrorCode = error.networkResponse.statusCode;
                        }

                        BusSingleton.getInstance().post(event);
                    }
                });

        sRequestQueue.add(request);
    }

    /**
     * Login as an existing user. Upon completion, {@link LoginEvent} is emitted
     *
     * @param username
     * @param password
     */
    public void login(String username, String password) {
        String url = BASE_URL + "login";

        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        FormDataRequest request = new FormDataRequest(Request.Method.POST, url, params,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LoginEvent event = new LoginEvent();
                        event.isSuccessful = false;

                        try {
                            JSONObject responseJsonObject = new JSONObject(response);
                            if (responseJsonObject.has("success")) {
                                event.isSuccessful = responseJsonObject.getBoolean("success");
                            }

                            BusSingleton.getInstance().post(event);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LoginEvent event = new LoginEvent();
                        event.isSuccessful = false;

                        if (error != null && error.networkResponse != null) {
                            event.httpErrorCode = error.networkResponse.statusCode;
                        }

                        BusSingleton.getInstance().post(event);
                    }
                });

        sRequestQueue.add(request);
    }

    public void getTutorMatches(String username) {
        String url = BASE_URL + "match?username=" + username;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Tutor> list = new ArrayList<>();

                        MatchEvent event = new MatchEvent();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                list.add(Tutor.create(object));
                            }

                            event.isSuccessful = true;
                            event.tutorList = list;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        BusSingleton.getInstance().post(event);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        MatchEvent event = new MatchEvent();
                        event.isSuccessful = false;
                        if (error != null && error.networkResponse != null) {
                            event.httpErrorCode = error.networkResponse.statusCode;
                        }

                        BusSingleton.getInstance().post(event);
                    }
                });

        sRequestQueue.add(request);
    }

    /**
     * POST request that takes x-www-form-urlencoded form data.
     */
    private static class FormDataRequest extends StringRequest {
        Map<String, String> mParams;

        public FormDataRequest(int method, String url, Map<String, String> params,
                               Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(Method.POST, url, listener, errorListener);
            mParams = params;
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return mParams;
        }
    }
}
