package com.lihuzi.takenotes.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tokenotes.R;
import com.lihuzi.takenotes.db.CoreDB;
import com.lihuzi.takenotes.utils.GoodsTypeUtils;

public class MainActivity extends AppCompatActivity {
    private EditText _name;
    private EditText _sum;
    private EditText _type;
    private EditText _desc;

    private TextView _done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_takenotes);

        _name = findViewById(R.id.act_act_name);
        _sum = findViewById(R.id.act_act_sum);
        _type = findViewById(R.id.act_act_type);
        _desc = findViewById(R.id.act_act_desc);

        _done = findViewById(R.id.act_act_done);

        _name.addTextChangedListener(textWatcher);
        _sum.addTextChangedListener(textWatcher);
        _type.addTextChangedListener(textWatcher);
        _desc.addTextChangedListener(textWatcher);


        _type.setOnClickListener(typeListener);
        _done.setOnClickListener(doneClick);
        findViewById(R.id.act_act_query).setOnClickListener(query);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private View.OnClickListener doneClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (_name.getText().length() > 0 && _sum.getText().length() > 0 && _type.getText().length() > 0 && _desc.getText().length() > 0) {
                CoreDB.insertNotes(_name.getText().toString(), Float.valueOf(_sum.getText().toString()), Integer.valueOf(_type.getText().toString()), _desc.getText().toString(), System.currentTimeMillis());
                Toast.makeText(v.getContext(), "正确", Toast.LENGTH_SHORT).show();
                _name.setText("");
                _sum.setText("");
                _type.setText("");
                _desc.setText("");
            } else {
                Toast.makeText(v.getContext(), "请输入正确的内容", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private View.OnClickListener query = v -> v.getContext().startActivity(new Intent(v.getContext(), RecordActivity.class));
    private View.OnClickListener typeListener = v -> showTypeDialog();

    private void showTypeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] array = new String[]{"支出", "收入"};
        builder.setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showTypeDialog(which == 1);
            }
        });
        builder.create().show();
    }

    private void showTypeDialog(final boolean obtain) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] array = GoodsTypeUtils.getItem(obtain);
        builder.setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                _type.setText(String.valueOf(GoodsTypeUtils.getTypeWithPosition(obtain, which)));
            }
        });
        builder.create().show();
    }
}
