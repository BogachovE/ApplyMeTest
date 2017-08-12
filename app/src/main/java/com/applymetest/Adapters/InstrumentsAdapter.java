package com.applymetest.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.applymetest.Models.Instrument;
import com.applymetest.R;

import java.util.ArrayList;

/**
 * Created by bogachov on 08.08.17.
 */

public class InstrumentsAdapter extends RecyclerView.Adapter<InstrumentsAdapter.InstrumentsHolder> {

    ArrayList<Instrument> instrumentsArray;

    public static class InstrumentsHolder extends RecyclerView.ViewHolder {

        TextView instrument_title;
        RelativeLayout instrument_back;
        ImageView instrument_image;

        public InstrumentsHolder(View v) {
            super(v);
            instrument_title = (TextView)v.findViewById(R.id.instrument_title);
            instrument_back = (RelativeLayout)v.findViewById(R.id.instrument_back);
            instrument_image = (ImageView)v.findViewById(R.id.instrument_image);
        }
    }


    public InstrumentsAdapter(ArrayList<Instrument> instrumentsArray) {
        this.instrumentsArray = instrumentsArray;
    }

    @Override
    public InstrumentsHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_item, viewGroup, false);
        InstrumentsHolder avh = new InstrumentsHolder(v);
        return avh;
    }

    @Override
    public void onBindViewHolder(final InstrumentsHolder holder, final int position) {
        final Context context = holder.instrument_title.getContext();
        holder.instrument_image.setImageResource(instrumentsArray.get(position).getImage());
        holder.instrument_back.setBackgroundResource(instrumentsArray.get(position).getColor());
        holder.instrument_title.setText(instrumentsArray.get(position).getTitle());
        holder.instrument_title.setTextColor(ContextCompat.getColor(context, instrumentsArray.get(position).getColor()));
    }



    @Override
    public int getItemCount() {
        return instrumentsArray.size();
    }
}
