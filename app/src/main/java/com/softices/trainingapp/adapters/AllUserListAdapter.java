package com.softices.trainingapp.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.softices.trainingapp.DatabaseHelper;
import com.softices.trainingapp.R;
import com.softices.trainingapp.activities.ToolsOptionActivity;
import com.softices.trainingapp.model.AppModel;

import java.util.List;

public class AllUserListAdapter extends RecyclerView.Adapter<AllUserListAdapter.UserViewHolder> {

    private List<AppModel> listuser;
    DatabaseHelper databaseHelper;
    AppModel appModel;

    public AllUserListAdapter(List<AppModel> listUsers) {
        this.listuser = listUsers;
    }

    @NonNull
    @Override
    public AllUserListAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_listof_user,
                parent, false);
        return new UserViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        Log.e("mye", "name " + listuser.get(position).getUserEmail());
        holder.tvName.setText(listuser.get(position).getUserName());
        holder.tvMail.setText(listuser.get(position).getUserEmail());
        holder.tvNumber.setText(listuser.get(position).getUserNumber() + " ");
        holder.tvPassword.setText(listuser.get(position).getUserPassword());
    }

    @Override
    public int getItemCount() {
        Log.v(AllUserListAdapter.class.getSimpleName(), "" + listuser.size());
        return listuser.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvMail;
        public TextView tvNumber;
        public TextView tvPassword;
        public Button btnDeleteListUser;
        public Toolbar toolbar;

        public UserViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_usre_name);
            tvMail = (TextView) view.findViewById(R.id.tv_user_email);
            tvNumber = (TextView) view.findViewById(R.id.tv_user_number);
            tvPassword = (TextView) view.findViewById(R.id.tv_user_password);
            btnDeleteListUser = (Button) view.findViewById(R.id.btn_delete_user);
            Toolbar toolbar = view.findViewById(R.id.toolbar);
            appModel=new AppModel();
            btnDeleteListUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            databaseHelper.deleteRecord(appModel);
//                            reloadUserDatabase();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
    }
}