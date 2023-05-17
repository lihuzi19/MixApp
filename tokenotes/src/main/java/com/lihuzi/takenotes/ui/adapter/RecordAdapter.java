package com.lihuzi.takenotes.ui.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tokenotes.R;
import com.lihuzi.takenotes.db.CoreDB;
import com.lihuzi.takenotes.model.NotesModel;
import com.lihuzi.takenotes.utils.DateUtils;
import com.lihuzi.takenotes.utils.GoodsTypeUtils;

import java.util.ArrayList;

/**
 * Created by cocav on 2017/11/14.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<NotesModel> _list;

    public RecordAdapter(ArrayList<NotesModel> list) {
        this._list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record_layout, parent, false);
        return new RecordViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RecordViewHolder) holder).setModel(_list.get(position));
    }

    @Override
    public int getItemCount() {
        return _list.size();
    }

    public NotesModel getItem(int position) {
        return _list.get(position);
    }

    class RecordViewHolder extends RecyclerView.ViewHolder {
        private NotesModel _model;

        private TextView _name;
        private TextView _sum;
        private TextView _type;
        private TextView _desc;
        private TextView _date;

        public RecordViewHolder(View itemView) {
            super(itemView);
            _name = itemView.findViewById(R.id.item_record_name);
            _sum = itemView.findViewById(R.id.item_record_sum);
            _type = itemView.findViewById(R.id.item_record_type);
            _desc = itemView.findViewById(R.id.item_record_desc);
            _date = itemView.findViewById(R.id.item_record_date);

            itemView.setOnLongClickListener(longClickListener);
        }

        public void setModel(NotesModel model) {
            this._model = model;
            _name.setText("商品名称:" + _model._name);
            _sum.setText("商品金额:" + _model._sum);
            _type.setText("商品类型:" + GoodsTypeUtils.getType(_model._type));
            _desc.setText("商品描述:" + _model._desc);
            _date.setText("日期:" + DateUtils.getTimeFormatString(_model._date));
        }

        private View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setItems(new String[]{"删除"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CoreDB.deleteById(_model._db_id);
                        _list.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                    }
                });
                builder.show();
                return true;
            }
        };
    }
}
