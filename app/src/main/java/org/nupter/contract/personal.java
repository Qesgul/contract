package org.nupter.contract;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by Axes on 2015/12/5.
 */
public class personal extends Activity{
    Button back1;

    LinearLayout lin1;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal);

        back1=(Button)findViewById(R.id.back1);
        lin1=(LinearLayout)findViewById(R.id.lin1);

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(personal.this, MainActivity.class);
                startActivity(intent);
            }
        });

        lin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(personal.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
