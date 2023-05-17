package com.example.lihuzi.infiniteviewpager.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infiniteviewpager.R;
import com.example.lihuzi.infiniteviewpager.ui.view.AutoLineTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TextViewMeasureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view_measure);


        findViewById(R.id.act_textview_finish_tv).setOnClickListener(v -> finish());
        RecyclerView rv = findViewById(R.id.act_textview_measure_rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        contentList = new ArrayList<>();

        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 100; i++) {
            int line = random.nextInt(6) + 1;
            sb.replace(0, sb.length(), "");
            for (int j = 0; j < line; j++) {
                sb.append(j).append("\n");
            }
            contentList.add(sb.toString());
        }

        rv.setAdapter(new rvAdapter());
    }

    private List<String> contentList;

    private class rvAdapter extends RecyclerView.Adapter<rvHolder> {

        @NonNull
        @Override
        public rvHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_auto_textview, viewGroup, false);
            return new rvHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull rvHolder rvHolder, int i) {
            rvHolder.setText(contentList.get(i));
        }

        @Override
        public int getItemCount() {
            return contentList.size();
        }
    }

    private class rvHolder extends RecyclerView.ViewHolder {

        private AutoLineTextView _textView;

        public rvHolder(@NonNull View itemView) {
            super(itemView);
            this._textView = itemView.findViewById(R.id.item_auto_line_tv);
        }

        public void setText(String text) {
            System.out.println(_textView + "setTextView");
            _textView.setText(text);
        }
    }

}
