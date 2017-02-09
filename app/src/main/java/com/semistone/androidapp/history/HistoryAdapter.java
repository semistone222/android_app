package com.semistone.androidapp.history;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.semistone.androidapp.R;
import com.semistone.androidapp.data.History;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by semistone on 2017-02-09.
 */

public class HistoryAdapter extends RealmRecyclerViewAdapter<History, HistoryAdapter.Holder> {

    public HistoryAdapter(Context context, OrderedRealmCollection<History> data) {
        super(context, data, true);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_history, parent, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        History history = getData().get(position);
        holder.data = history;

        holder.tvPoint.setText(history.getPoint());
        holder.tvDonateDate.setText(String.valueOf(history.getDonateDate()));
        holder.itemView.setTag(history.getHistoryId());
    }

    class Holder extends RecyclerView.ViewHolder {
        public TextView tvDonateDate;
        public TextView tvPoint;
        public History data;

        public Holder(View view) {
            super(view);
            tvDonateDate = (TextView) view.findViewById(R.id.tv_donate_date);
            tvPoint = (TextView) view.findViewById(R.id.tv_point);
        }
    }
}
