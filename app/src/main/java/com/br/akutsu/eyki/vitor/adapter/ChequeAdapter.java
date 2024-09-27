package com.br.akutsu.eyki.vitor.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.br.akutsu.eyki.vitor.R;
import com.br.akutsu.eyki.vitor.entities.Cheque;

import java.util.List;

public class ChequeAdapter extends ArrayAdapter<Cheque> {
    public ChequeAdapter(@NonNull Context context, @NonNull List<Cheque> cheques) {
        super(context, 0, cheques);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_cheque, parent, false);
        }

        TextView tvItemValor = convertView.findViewById(R.id.tvItemValor);
        TextView tvItemDias = convertView.findViewById(R.id.tvItemDias);
        TextView tvItemJuros = convertView.findViewById(R.id.tvItemJuros);

        Cheque cheque = getItem(position);

        tvItemValor.setText(String.format("%.2f", cheque.getValor()));
        tvItemJuros.setText(String.format("%.2f", cheque.getJuros()));
        tvItemDias.setText(String.valueOf(cheque.getDias()));

        return convertView;
    }
}
