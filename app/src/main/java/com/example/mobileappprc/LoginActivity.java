package com.example.mobileappprc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText username;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.login_button);
        username = findViewById(R.id.username_form);
        password = findViewById(R.id.password_form);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                SafetyNet.getClient(LoginActivity.this).verifyWithRecaptcha("6LcyMdMfAAAAAOzX2Pk7VcAdx2DMiw3vxB8i28iS")
                        .addOnSuccessListener( LoginActivity.this,
                                new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                                    @Override
                                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                                        // Indicates communication with reCAPTCHA service was
                                        // successful.
                                        String userResponseToken = response.getTokenResult();
                                        if (!userResponseToken.isEmpty()) {
                                            // Validate the user response token using the
                                            // reCAPTCHA siteverify API.
                                            processLoginStep(userResponseToken,username.getText().toString(),password.getText().toString());
                                        }
                                    }
                                })
                        .addOnFailureListener( LoginActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (e instanceof ApiException) {
                                    // An error occurred when communicating with the
                                    // reCAPTCHA service. Refer to the status code to
                                    // handle the error appropriately.
                                    ApiException apiException = (ApiException) e;
                                    int statusCode = apiException.getStatusCode();
                                    Log.d("Captcha Token", "Error: " + CommonStatusCodes
                                            .getStatusCodeString(statusCode));
                                } else {
                                    // A different, unknown type of error occurred.
                                    Log.d("Captcha Token", "Error: " + e.getMessage());
                                }
                            }
                        });
                //openHomeActivity();
            }
        });

    }

    private void ProcessLogin() {

    }

    private void processLoginStep(String token, String username, String password) {
        RequestQueue queue = Volley.newRequestQueue(this);
        //Lagay niyo IP Address niyo sa string nato
        String ipAddress = "";
        String url ="http://"+ipAddress+"/captcha_check.php?captcha="+token;

        /*
        Gawa kayo php file sa htdocs, then paste niyo to
        tapos run xampp then goods nayan dapat pag tama ung recpatcha verification nyo, magprorpoceeed na sa home.
        * <?php

        $curl = curl_init();

        curl_setopt_array($curl, array(
          CURLOPT_URL => 'https://www.google.com/recaptcha/api/siteverify',
          CURLOPT_RETURNTRANSFER => true,
          CURLOPT_ENCODING => '',
          CURLOPT_MAXREDIRS => 10,
          CURLOPT_TIMEOUT => 0,
          CURLOPT_FOLLOWLOCATION => true,
          CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
          CURLOPT_CUSTOMREQUEST => 'POST',
          CURLOPT_POSTFIELDS => 'response='.$_REQUEST['captcha'].'&secret=6LcyMdMfAAAAAMGlNjOt_SeQyMjDEr4M8sOCGmns',
          CURLOPT_HTTPHEADER => array(
            'Content-Type: application/x-www-form-urlencoded'
          ),
        ));

        $response = curl_exec($curl);

        curl_close($curl);
        $array_res = json_decode($response, true);
        if($array_res["success"]=="true"){
        echo "Login Successful";
        } else {
        echo "Login Failed";
        }

        * */

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        openHomeActivity();
                        Toast.makeText(LoginActivity.this, ""+response, Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);
    }

    private void openHomeActivity() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

}