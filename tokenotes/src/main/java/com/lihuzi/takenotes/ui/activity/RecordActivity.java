package com.lihuzi.takenotes.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tokenotes.R;
import com.lihuzi.takenotes.db.CoreDB;
import com.lihuzi.takenotes.model.NotesModel;
import com.lihuzi.takenotes.ui.adapter.RecordAdapter;
import com.lihuzi.takenotes.utils.DateUtils;

import java.util.ArrayList;

public class RecordActivity extends AppCompatActivity {
    private ArrayList<NotesModel> _list;
    private int _headerPosition;
    private String _headerDateString = "";

    private RecyclerView _recyclerView;
    private RecordAdapter _adapter;
    private TextView _headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        initView();
        initListener();
        initLoad();
    }

    private void initView() {
        _recyclerView = findViewById(R.id.act_record_recyclerview);
        _headerView = findViewById(R.id.act_record_headerview_tv);
    }

    private void initListener() {
        _recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (position != _headerPosition) {
                    setHeaderView(position);
                }
            }
        });
    }

    private final static String MONTH_FORMAT = "yyyy-MM";

    private void setHeaderView(int position) {
        _headerPosition = position;
        long date = _adapter.getItem(position)._date;
        String month = DateUtils.getTimeFormatString(MONTH_FORMAT, date);
        if (!month.equals(_headerDateString)) {
            _headerDateString = month;
            ArrayList<NotesModel> list = CoreDB.queryThisMonth(date);
            float pay = 0;
            float obtain = 0;
            for (int i = 0; i < list.size(); i++) {
                NotesModel model = list.get(i);
                if (model._type > 0) {
                    pay += model._sum;
                } else {
                    obtain += model._sum;
                }
            }
            _headerView.setText("本月支出: " + String.valueOf(pay) + ",本月收入: " + String.valueOf(obtain));
        }
    }

    private void initLoad() {
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<NotesModel> list = CoreDB.queryPay();
        _adapter = new RecordAdapter(list);
        _recyclerView.setAdapter(_adapter);
        if (_adapter.getItemCount() > 0) {
            setHeaderView(0);
        }
    }
}
