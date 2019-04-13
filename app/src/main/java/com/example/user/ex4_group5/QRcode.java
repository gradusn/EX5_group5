package com.example.user.ex4_group5;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class QRcode extends AppCompatActivity {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        //TODO remember to remove this line

        //Intent i=new Intent(this,MainActivity.class);
        //startActivity(i);
    }
    public void scanQR(View v) {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException e) {
            showDialog(QRcode.this, "No Scanner Found",
                    "Download a scanner code activity?", "Yes", "No").show();
        }
    }
    private static AlertDialog showDialog(final QRcode act,
                                          CharSequence title, CharSequence message,
                                          CharSequence buttonYes,
                                          CharSequence buttonNo) {

        AlertDialog.Builder dowloadDialog = new AlertDialog.Builder(act);
        dowloadDialog.setTitle(title).setMessage(message)
                .setPositiveButton(buttonYes,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Uri uri = Uri.parse("market://search?q=pname:"
                                        + "com.google.zxing.client.android");

                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                try {
                                    act.startActivity(intent);
                                } catch (ActivityNotFoundException e) {
                                }

                            }
                        })
                .setNegativeButton(buttonNo,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        });

        return dowloadDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == 0) {
            if(resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast.makeText(this,
                        "Content:" + contents + " Format:" + format,
                        Toast.LENGTH_LONG).show();
                Intent i=new Intent(this,MainActivity.class);
                i.putExtra("rest_table",contents);
                startActivity(i);

            }
        }
    }


}
