package com.softices.trainingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softices.trainingapp.R;
import com.softices.trainingapp.model.AppModel;

import java.util.List;

public class AllContactsAdapter extends RecyclerView.Adapter<AllContactsAdapter.ContactViewHolder> {

    private List<AppModel> appModelList;
    //    private final View.OnClickListener imagePhoneCall;
    private Context mContext;

    public AllContactsAdapter(List<AppModel> appModelList, Context mContext) {
        this.appModelList = appModelList;
//        this.imagePhoneCall = imagePhoneCall;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public AllContactsAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_contact, null);
        ContactViewHolder contactViewHolder = new ContactViewHolder(view);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        AppModel appModel = appModelList.get(position);

        if (appModel.getContactName() != null)
            holder.tvContactName.setText(appModel.getContactName());
        else
            holder.tvContactName.setText("N.A.");
//        holder.tvContactNumber.setText(appModel.getContactNumber());
    }

    @Override
    public int getItemCount() {
        return appModelList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        ImageView ivContactImage, ivPhoneCall;
        TextView tvContactName, tvContactNumber;

        public ContactViewHolder(View itemView) {
            super(itemView);
//            ivPhoneCall=(ImageView) itemView.findViewById(R.id.ic_image_calling);
            ivContactImage = itemView.findViewById(R.id.iv_contact_image);
            tvContactName = itemView.findViewById(R.id.tv_Contact_Name);
//            tvContactNumber = (TextView) itemView.findViewById(R.id.tv_Contact_number);
        }
    }
}