package com.petertacon.bikecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    Button exitActivity;

    String[] ListViewDescription = new String[] {
            "Mensajería Daniela","Mensajería José","Cambio de Aceite","Mensajería Martha",
            "Mensajería Pibox"
    };

    String[] ListViewTitle = new String[] {
            "3419 - 3518","10000 - 10100","3419 - 3518","3419 - 3518", "3419 - 3518"
    };

    String[] ListViewDate = new String[] {
            "23/04/1970","19/04/1993","13/08/2001","11/01/1970","27/11/1999"
    };

    int[] ListViewImages = new int[]{
            R.drawable.motorbike, R.drawable.motorbike, R.drawable.motorbike, R.drawable.motorbike,
            R.drawable.motorbike
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        exitActivity = findViewById(R.id.btnExit);

        List<HashMap<String, String>> aList = new ArrayList<>();
        for (int x = 0; x < 5; x++){
            HashMap<String, String> hm = new HashMap<>();
            hm.put("ListTitle",ListViewTitle[x]);
            hm.put("ListDescription",ListViewDescription[x]);
            hm.put("ListDate", ListViewDate[x]);
            hm.put("ListImages",Integer.toString(ListViewImages[x]));
            aList.add(hm);
        }

        String[] from = {
                "ListImages","ListTitle","ListDescription","ListDate"
        };
        int[] to = {
                R.id.listview_images,R.id.Title,R.id.Description,R.id.Date
        };

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(),aList, R.layout.list_view_items,from,to);
        ListView simpleListview = (ListView)findViewById(R.id.lyRegister);
        simpleListview.setAdapter(simpleAdapter);
    }
    @Override
    public void onBackPressed(){
        // usado para evitar que cierre la app
    }

    //Método usado para salir de app
    public void onExitClick (View v) {
        final Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        //Click Listener para comunicar botón
        exitActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}