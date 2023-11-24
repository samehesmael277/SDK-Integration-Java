package com.sameh.taskjava.ui.home;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sameh.taskjava.R;
import com.sameh.taskjava.data.Company;

import java.util.List;

public class CompaniesAdapter extends RecyclerView.Adapter<CompaniesAdapter.ViewHolder> {

    private List<Company> data;

    private final OnCompanyClickListener mListener;

    public CompaniesAdapter(OnCompanyClickListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Company company = data.get(position);

        holder.imgCompany.setImageResource(company.getLogo());
        holder.tvCompanyDes.setText(company.getName());

        holder.root.setOnClickListener(v -> mListener.onItemClick(company));
    }

    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        else
            return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Company> newData) {
        data = newData;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCompany;
        TextView tvCompanyDes;
        ConstraintLayout root;

        public ViewHolder(View itemView) {
            super(itemView);
            imgCompany = itemView.findViewById(R.id.img_company);
            tvCompanyDes = itemView.findViewById(R.id.tv_company_des);
            root = itemView.findViewById(R.id.item_company_constraint);
        }
    }
}
