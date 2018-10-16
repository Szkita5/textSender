package tutorialspoint.example.com.textsender;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final static int SEND_SMS_PERMISSION_REQUEST_CODE = 111;
    private final static int READ_PHONE_STATE_REQUEST_CODE = 222;
    private final static String UCL_ATTENDANCE_PHONE_NUMBER = "02071838329";

    Button button;
    EditText textbox;

    String personal = "uclpa 16014073,";
    String courseCode = "0042,";
    String number = UCL_ATTENDANCE_PHONE_NUMBER;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    number = "07435239150"; //Lucian test
        setContentView(R.layout.activity_start);
        Button emButton = findViewById(R.id.emtButton);
        Button qmButton = findViewById(R.id.qmButton);
        Button mfgrButton = findViewById(R.id.mfgrButton);
        Button todsButton = findViewById(R.id.todsButton);



        emButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                courseCode = "0038,";
                buttonyAction();
            }
        });

        qmButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                courseCode = "0042,";
                buttonyAction();
            }
        });

        mfgrButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                courseCode = "0025,";
                buttonyAction();
            }
        });

        todsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                courseCode = "0049,";
                buttonyAction();
            }
        });
    }

    private void buttonyAction() {
        setContentView(R.layout.activity_main);

        textbox = (EditText)findViewById(R.id.editText);
        button = (Button)findViewById(R.id.button);
        button.setEnabled(false);

        if (checkPermission(Manifest.permission.SEND_SMS)){
            button.setEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_REQUEST_CODE);
        }

        if (!checkPermission(Manifest.permission.READ_PHONE_STATE)){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE_REQUEST_CODE);
        }

        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                String fourKey = textbox.getText().toString();
                String msg = personal + courseCode + fourKey;

                if (!TextUtils.isEmpty(fourKey)) {
                    if(checkPermission(Manifest.permission.SEND_SMS) && checkPermission(Manifest.permission.READ_PHONE_STATE)){
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(number, null, msg, null, null);
                        Toast.makeText(getApplicationContext(),"message sent", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Permission denied", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Enter 4KEY", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkPermission(String permission) {
        int checkPermission = ContextCompat.checkSelfPermission(this, permission);
        return checkPermission == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SEND_SMS_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(getApplicationContext(),"SMS Enabled", Toast.LENGTH_SHORT).show();
                    button.setEnabled(true);
                }
            case READ_PHONE_STATE_REQUEST_CODE: {
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(getApplicationContext(), "READ_PHONE_STATE Enabled", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }
}
