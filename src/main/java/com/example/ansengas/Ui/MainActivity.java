package com.example.ansengas.Ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ansengas.Constant;
import com.example.ansengas.MySingleton;
import com.example.ansengas.R;
import com.example.ansengas.Shaireprefmanager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText txtPassword,txtUsername;
    Button btnLogin;
    private ProgressDialog loadingbar;
    AlertDialog.Builder builder;
    String TAG ="a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtPassword=(EditText)findViewById(R.id.txtPassword);
        txtUsername=(EditText)findViewById(R.id.txtUsername);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        loadingbar=new ProgressDialog(this);
        builder=new AlertDialog.Builder(MainActivity.this);

        if(Shaireprefmanager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, HomeActivity.class));
            return;

        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtUsername.getText().toString().equals("")){
                    if(!txtPassword.getText().toString().equals("")){
                        login();
                    }
                    else {
                        txtPassword.setError("Enter Valid Password");
                    }
                }else {
                    txtUsername.setError("Enter Valid username");
                }

            }
        });
    }

    private void login() {

        loadingbar.setTitle("Loading");
        loadingbar.setMessage("Checking Username and Password");
        loadingbar.show();
        loadingbar.setCanceledOnTouchOutside(true);

        String newusername = txtUsername.getText().toString();
        String newpassword = txtPassword.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("response", "onResponse: "+response );

                            JSONObject jsonObject=new JSONObject(response);
                            String code=jsonObject.getString("code");


                            if(code.equals("200"))
                            {
                                String refid=jsonObject.getString("refid");
                                String name=jsonObject.getString("name");
                                String mobile=jsonObject.getString("mobile");
                                String rootId=jsonObject.getString("rootId");

                                Shaireprefmanager.getInstance(getApplicationContext())
                                        .userLogin(newusername,refid,name,mobile,rootId);
                                Log.e(TAG, "root id "+rootId );
                                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                                startActivity(intent);

                            }
                            else
                            {
                                builder.setTitle("Login_Error");
                                displayalert("Try Again");
                                loadingbar.dismiss();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Hours.setText("Something Went Wrong....");
                error.printStackTrace();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("username",newusername);
                params.put("password",newpassword);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestque(stringRequest);
    }

    public void displayalert(String message)
    {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}