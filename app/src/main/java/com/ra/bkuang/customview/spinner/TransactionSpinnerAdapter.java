package com.ra.bkuang.customview.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ra.bkuang.R;
import com.ra.bkuang.domain.model.AkunModel;
import com.ra.bkuang.domain.model.KategoriModel;

import java.util.List;
import java.util.Objects;

public class TransactionSpinnerAdapter<T> extends ArrayAdapter<T> {

    private final LayoutInflater layoutInflater;
    private final List<T> listData;

    public TransactionSpinnerAdapter(
            @NonNull Context context,
            int resource,
            @NonNull List<T> objects) {
        super(context, resource, objects);
        layoutInflater = LayoutInflater.from(context);
        listData = objects;
    }

    @Nullable
    @Override
    public T getItem(int position) {
        return listData.get(position);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = layoutInflater.inflate(R.layout.item_transaction_spinner, parent, false);
        setView(v, Objects.requireNonNull(getItem(position)));
        return v;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_transaction_spinner, parent, false);
            setView(convertView, getItem(position));
        }
        return convertView;
    }

    private void setView(View v, T model) {
        TextView name = v.findViewById(R.id.tv_name);

        if(model instanceof AkunModel) {
            AkunModel akunModel = (AkunModel) model;
            name.setText(akunModel.getNama());
        } else if(model instanceof KategoriModel) {
            KategoriModel kategoriModel = (KategoriModel) model;
            name.setText(kategoriModel.getNama());
         }
    }
}
