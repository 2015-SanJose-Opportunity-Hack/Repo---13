package com.example.kvohra.careforcare;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity{
    public static final String PREFS_NAME = "MyApp_Settings";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
     /*   SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();*/

        // Reading from SharedPreferences
        String parentToken = settings.getString("parent", "");
        String childToken = settings.getString("child", "");

        if(parentToken.equalsIgnoreCase("")==false || childToken.equalsIgnoreCase("")==false)
        {
            if(parentToken.equalsIgnoreCase("")==false)
            {
                //launch as parent
                Log.d("ourtoken",parentToken);
            }
            else if (childToken.equalsIgnoreCase("")==false)
            {
                //launch as child
                Log.d("ourtoken",childToken);
            }
        }
        setContentView(R.layout.activity_main);

        //if null
        init();
    }


    protected void init()
    {
        Button parent = (Button)findViewById(R.id.buttonParent);
        parent.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final Dialog dialog = getDialog();
                dialog.show();
                
                Button saveButton = (Button)dialog.findViewById(R.id.saveButton);
                saveButton.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Log.d("save", "save");
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                        //Writing data to SharedPreferences
                        SharedPreferences.Editor editor = settings.edit();

                        EditText etxtName = (EditText)dialog.findViewById(R.id.etxtName);
                        String token = etxtName.getText().toString();
                        editor.putString("parent", token);
                        editor.commit();
                        dialog.dismiss();
                    }
                }));

                Button cancelButton = (Button)dialog.findViewById(R.id.cancelButton);
                cancelButton.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Log.d("cancel", "cancel");
                        dialog.dismiss();
                    }
                }));
            }
        }));

        Button child = (Button)findViewById(R.id.buttonChild);
        child.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final Dialog dialog = getDialog();
                dialog.show();

                Button saveButton = (Button)dialog.findViewById(R.id.saveButton);
                saveButton.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Log.d("save", "save");
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                        //Writing data to SharedPreferences
                        SharedPreferences.Editor editor = settings.edit();
                        EditText etxtName = (EditText)dialog.findViewById(R.id.etxtName);
                        String token = etxtName.getText().toString();
                        editor.putString("child", token);
                        editor.commit();
                        dialog.dismiss();
                    }
                }));

                Button cancelButton = (Button)dialog.findViewById(R.id.cancelButton);
                cancelButton.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Log.d("cancel", "cancel");
                        dialog.dismiss();
                    }
                }));
            }
        }));



    }

    protected Dialog getDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.parentdialogue);
        dialog.setTitle("Save New Number");
        dialog.setCancelable(true);
        return dialog;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


}
