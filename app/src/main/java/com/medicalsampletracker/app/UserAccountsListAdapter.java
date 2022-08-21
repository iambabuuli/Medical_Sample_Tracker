package com.medicalsampletracker.app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAccountsListAdapter extends RecyclerView.Adapter<UserAccountsListAdapter.ViewHolder> implements Filterable {

    ArrayList<UserAccountInformation> list;
    ArrayList<UserAccountInformation> listCopy;

    public UserAccountsListAdapter(ArrayList<UserAccountInformation> list) {
        this.list = list;
        this.listCopy = list;
    }

    @NonNull
    @Override
    public UserAccountsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_user_account_details, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAccountsListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.first_name.setText(list.get(position).getFirst_name());
        holder.last_name.setText(list.get(position).getLast_name());
        holder.phone_number.setText(list.get(position).getPhone_number());
        holder.accountType.setText(list.get(position).accountType);

        holder.edit.setVisibility(View.GONE);
        holder.call.setVisibility(View.GONE);
        holder.delete.setVisibility(View.GONE);

        if(!list.get(position).getProfile_picture_address().isEmpty()){

            Picasso.get().load(list.get(position).getProfile_picture_address()).into(holder.profile_photo);

        }else{

            holder.profile_photo.setImageResource(R.drawable.ic_person_black_100dp);
        }

        holder.itemView.setOnClickListener(v -> {

            if(holder.edit.getVisibility() == View.GONE &&
               holder.call.getVisibility() == View.GONE &&
               holder.delete.getVisibility() == View.GONE){

               holder.edit.setVisibility(View.VISIBLE);
               holder.call.setVisibility(View.VISIBLE);
               holder.delete.setVisibility(View.VISIBLE);

            } else {

                holder.edit.setVisibility(View.GONE);
                holder.call.setVisibility(View.GONE);
                holder.delete.setVisibility(View.GONE);

            }
        });

        holder.call.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
            builder.setMessage("Do you want to call this person ?");
            builder.setCancelable(false);

            builder.setPositiveButton("Yes", (dialog, which) -> {

            Intent call_number = new Intent(Intent.ACTION_DIAL);
            call_number.setData(Uri.parse("tel: " + list.get(position).getPhone_number()));
            holder.itemView.getContext().startActivity(call_number);

            });

            builder.setNegativeButton("No", (dialog, which) -> builder.setCancelable(true));

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        holder.edit.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
            builder.setMessage("Do you want to update this account ?");
            builder.setCancelable(false);

            builder.setPositiveButton("Yes", (dialog, which) -> {

                Intent intent = new Intent(holder.itemView.getContext(), UpdateUserAccount.class);

                intent.putExtra("first_name", list.get(position).getFirst_name());
                intent.putExtra("last_name", list.get(position).getLast_name());
                intent.putExtra("email", list.get(position).getEmail());
                intent.putExtra("account_type", list.get(position).getAccountType());
                intent.putExtra("phone_number", list.get(position).getPhone_number());
                intent.putExtra("profile_picture_path", list.get(position).getProfile_picture_path());
                intent.putExtra("profile_picture_address", list.get(position).getProfile_picture_address());

                holder.itemView.getContext().startActivity(intent);

            });

            builder.setNegativeButton("No", (dialog, which) -> builder.setCancelable(true));

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        holder.delete.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
            builder.setMessage("Delete this account ?");
            builder.setCancelable(false);

            builder.setPositiveButton("Yes", (dialog, which) -> {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, References.delete_user_account, response -> {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        String message = jsonObject.getString("message");

                        if(success.equals("1")){

                            Toast.makeText(holder.itemView.getContext(), message, Toast.LENGTH_LONG).show();

                        }else if(success.equals("0")){

                            Toast.makeText(holder.itemView.getContext(), message, Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {

                        Toast.makeText(holder.itemView.getContext(), "Sever error.", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                        Log.e("TAG", response);
                    }

                }, error -> {

                    Toast.makeText(holder.itemView.getContext(), "System error.", Toast.LENGTH_LONG).show();
                    Log.e("TAG", error.getMessage());
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<>();
                        params.put("email", list.get(position).getEmail());
                        params.put("profile_picture_path", list.get(position).getProfile_picture_path());
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(holder.itemView.getContext());
                requestQueue.add(stringRequest);

            });

            builder.setNegativeButton("No", (dialog, which) -> builder.setCancelable(false));

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String searchText = constraint.toString();

                if(searchText.isEmpty()){

                    list = listCopy;
                }else {

                    ArrayList<UserAccountInformation> results = new ArrayList<>();

                    for (UserAccountInformation data : listCopy) {

                        if(data.first_name.toLowerCase().contains(searchText) ||
                        data.last_name.toLowerCase().contains(searchText) ||
                        data.email.toLowerCase().contains(searchText) ||
                        data.accountType.toLowerCase().contains(searchText) ||
                        data.phone_number.toLowerCase().contains(searchText)){

                            results.add(data);
                        }
                    }

                    list = results;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list =(ArrayList<UserAccountInformation>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView first_name, last_name, phone_number, accountType, call, edit, delete;
        CircleImageView profile_photo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            first_name = itemView.findViewById(R.id.account_user_first_name_edit);
            last_name = itemView.findViewById(R.id.account_user_last_name_edit);
            accountType = itemView.findViewById(R.id.account_account_type_edit);
            phone_number = itemView.findViewById(R.id.account_phone_number_edit);
            call = itemView.findViewById(R.id.account_user_call_user_textView_button);
            edit = itemView.findViewById(R.id.account_user_edit_account_textView_button);
            delete = itemView.findViewById(R.id.account_delete_account_textView_button);
            profile_photo = itemView.findViewById(R.id.user_account_imageView);
        }
    }
}
