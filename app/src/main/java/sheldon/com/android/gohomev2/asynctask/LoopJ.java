package sheldon.com.android.gohomev2.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import org.json.*;

import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;
import sheldon.com.android.gohomev2.R;

public class LoopJ {
    private static final String TAG = "LOOPJ";
    private static final String AUTH_URL = "user/submitLogin/";
    private static final String SYNC_URL = "device/getListSensor/";

    private Context context;
    private LoopJListener loopJListener;

    public static String uname, fullName, email, role, token, auth;
    public static JSONObject syncResponse;
    public static boolean isBusy;

    public LoopJ(LoopJListener loopJListener) {
        this.loopJListener = loopJListener;
    }

    public LoopJ(Context context, LoopJListener loopJListener) {
        this.context = context;
        this.loopJListener = loopJListener;
    }

    public void sendLoginRequest(String username, String hashPassword) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("username", username);
        requestParams.put("hashpassword", hashPassword);

        LoopJRestClient.post(AUTH_URL, requestParams, new JsonHttpResponseHandler() {
            ProgressDialog progressDialog;

            @Override
            public void onStart() {
                progressDialog = new ProgressDialog(context,
                        R.style.AppTheme_Dark_Dialog);

                progressDialog.setMessage("Authenticating...");
                progressDialog.setIndeterminate(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG, "onFailure#1: " + errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, "onFailure#2: " + responseString);
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    uname = response.getString("username");
                    fullName = response.getString("full_name");
                    email = response.getString("email");
                    role = response.getString("role");
                    token = response.getString("token");
                    auth = response.getString("logStat");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.d("AUTHENTICATOR_AUTHSTAT", "onSuccess: " + auth);
//                Log.d("AUTHENTICATOR_USERNAME", "onSuccess: " + uname);
//                Log.d("AUTHENTICATOR_TOKEN", "onSuccess: " + token);
                loopJListener.authenticate(auth);
            }

            @Override
            public void onFinish() {
                //Do something with the response
                progressDialog.dismiss();
            }
        });
    }

    public void synchronize(String token, String username) {
//        Log.d("USERNAME", "synchronize: " + username);
//        Log.d("TOKEN", "synchronize: " + token);

//        LoopJRestClient.addHeader("username", username);
        LoopJRestClient.addHeader("username", "dimas");
        LoopJRestClient.addHeader("tokenid", token);

        LoopJRestClient.get(SYNC_URL, null, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                isBusy = true;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject errorResponse) {
                Log.d(TAG, "onFailure#1: " + errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                                  Throwable throwable) {
                Log.d(TAG, "onFailure#2: " + responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                syncResponse = response;
            }

            @Override
            public void onFinish() {
                isBusy = false;
            }
        });
    }
}