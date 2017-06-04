package org.nupter.contract;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Axes on 2016/2/1.
 */
public class Phone extends Activity implements View.OnClickListener {
    TextView phoneview;
    Button num0, num1, num2, num3, num4, num5, num6, num7, num8, num9;
    Button mi, jin;
    Button phoneadd,del,phone233;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone);
        phoneview = (EditText) findViewById(R.id.phonetext);
        num0 = (Button) findViewById(R.id.zero);
        num1 = (Button) findViewById(R.id.one);
        num2 = (Button) findViewById(R.id.two);
        num3 = (Button) findViewById(R.id.three);
        num4 = (Button) findViewById(R.id.four);
        num5 = (Button) findViewById(R.id.five);
        num6 = (Button) findViewById(R.id.six);
        num7 = (Button) findViewById(R.id.seven);
        num8 = (Button) findViewById(R.id.eight);
        num9 = (Button) findViewById(R.id.nine);
        mi = (Button) findViewById(R.id.mi);
        jin = (Button) findViewById(R.id.jin);
        phone233=(Button)findViewById(R.id.phone_sb);
        del=(Button)findViewById(R.id.del);

        num0.setOnClickListener(this);
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        mi.setOnClickListener(this);
        jin.setOnClickListener(this);
        phone233.setOnClickListener(this);
        del.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zero:
                String myString0 = phoneview.getText().toString();
                myString0 += "0";
                phoneview.setText(myString0);
                break;
            case R.id.one:
                String myString1=phoneview.getText().toString();
                myString1+="1";
                phoneview.setText(myString1);
                break;
            case R.id.two:
                String myString2=phoneview.getText().toString();
                myString2+="2";
                phoneview.setText(myString2);
                break;
            case R.id.three:
                String myString3=phoneview.getText().toString();
                myString3+="3";
                phoneview.setText(myString3);
                break;
            case R.id.four:
                String myString4=phoneview.getText().toString();
                myString4+="4";
                phoneview.setText(myString4);
                break;
            case R.id.five:
                String myString5=phoneview.getText().toString();
                myString5+="5";
                phoneview.setText(myString5);
                break;
            case R.id.six:
                String myString6=phoneview.getText().toString();
                myString6+="6";
                phoneview.setText(myString6);
                break;
            case R.id.seven:
                String myString7=phoneview.getText().toString();
                myString7+="7";
                phoneview.setText(myString7);
                break;
            case R.id.eight:
                String myString8=phoneview.getText().toString();
                myString8+="8";
                phoneview.setText(myString8);
                break;
            case R.id.nine:
                String myString9=phoneview.getText().toString();
                myString9+="9";
                phoneview.setText(myString9);
                break;
            case R.id.mi:
                String myStringmi=phoneview.getText().toString();
                myStringmi+="*";
                phoneview.setText(myStringmi);
                break;
            case R.id.jin:
                String myStringjin=phoneview.getText().toString();
                myStringjin+="#";
                phoneview.setText(myStringjin);
                break;
            case R.id.del:
                /*String myStringdel=phoneview.getText().toString();
                if(myStringdel==null)
                {
                    del.setClickable(false);
                    return;
                }
                phoneview.setText(myStringdel.substring(0, myStringdel.length()-1));
                break;*/
                String myStringdel=phoneview.getText().toString();
                if(phoneview.getText().toString().length() > 0){
                    phoneview.setText(myStringdel.substring(0, myStringdel.length()-1));
                } else {
                    return;
                }
                break;
            case R.id.phone_sb:
                String phonenumber=phoneview.getText().toString();
                Toast.makeText(Phone.this, "拨打电话！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phonenumber));
                startActivity(intent);
                break;
        }
    }
}