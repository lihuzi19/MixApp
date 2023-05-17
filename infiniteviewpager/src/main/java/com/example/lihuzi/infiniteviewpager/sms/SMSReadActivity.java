package com.example.lihuzi.infiniteviewpager.sms;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infiniteviewpager.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SMSReadActivity extends AppCompatActivity {

    private RecyclerView _recyclerView;
    private final static int permissionRequestCode = 100;
    private ContentResolver cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsread);
        _recyclerView = findViewById(R.id.act_sms_read_rv);
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
        checkSMSPermission();

        findViewById(R.id.act_sms_set_default_sms_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultSMSAPP();
            }
        });
        findViewById(R.id.act_sms_insert_sms_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertSMS(cr);
            }
        });
    }

    private void checkSMSPermission() {
        String[] permissions = new String[]{Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS};
        if (checkPermission(permissions)) {
            startReadSMS();
        } else {
            ActivityCompat.requestPermissions(this, permissions, permissionRequestCode);
        }
    }

    private boolean checkPermission(String[] permissions) {
        for (String permission : permissions) {
            if (!(ActivityCompat.checkSelfPermission(this, permission) == PermissionChecker.PERMISSION_GRANTED)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == permissionRequestCode) {
            for (int grant : grantResults) {
                if (grant != PermissionChecker.PERMISSION_GRANTED) {
                    return;
                }
            }
            startReadSMS();
        }
    }

    private void startReadSMS() {
        List list = new ArrayList();
        Uri SMS_INBOX = Uri.parse("content://sms/");
        cr = getContentResolver();
        String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
        Cursor cur = cr.query(SMS_INBOX, projection, null, null, "date desc");
        if (null == cur) {
            Log.i("ooc", "************cur == null");
            return;
        }
        while (cur.moveToNext()) {
            String number = cur.getString(cur.getColumnIndex("address"));//手机号
            String name = cur.getString(cur.getColumnIndex("person"));//联系人姓名列表
            String body = cur.getString(cur.getColumnIndex("body"));//短信内容
            System.out.println("address: " + number + ",body: " + body);
            //至此就获得了短信的相关的内容, 以下是把短信加入map中，构建listview,非必要。
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("num", number);
            map.put("mess", body);
            list.add(map);
        }
    }

    private void insertSMS(ContentResolver cr) {
        ContentValues values = new ContentValues();
        values.put("address", num);
        values.put("type", 1);
        values.put("date", time);
//        values.put("body", "贵帐户*9093于" + "1月12号11:58" + "转入13145.20元【交通银行】");
        values.put("body", body);
        Uri newuri = cr.insert(Uri.parse("content://sms/"), values);
        System.out.println(newuri.toString());
    }

    private long time = System.currentTimeMillis();
    private String body = "";
    private long num = 10690972060560l;

    private void setDefaultSMSAPP() {
        String defaultSmsApp = null;
        String currentPn = getPackageName();//获取当前程序包名
        System.out.println(currentPn);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            defaultSmsApp = Telephony.Sms.getDefaultSmsPackage(this);//获取手机当前设置的默认短信应用的包名
        }
        if (!defaultSmsApp.equals(currentPn)) {
            Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, currentPn);
            startActivity(intent);
        }
    }
}
