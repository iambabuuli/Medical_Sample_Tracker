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
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class ReceivedSamplesAdapter extends RecyclerView.Adapter<ReceivedSamplesAdapter.ViewHolder> implements Filterable {

    ArrayList<ReceivedSamplesData> list;
    ArrayList<ReceivedSamplesData> listCopy;


    public ReceivedSamplesAdapter(ArrayList<ReceivedSamplesData> list) {

        this.list = list;
        this.listCopy = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.received_samples_detail, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.sampleId.setText(list.get(position).getSampleId());
        holder.patientsIDNUmber.setText(list.get(position).getPatientsIDNumber());
        holder.disease.setText(list.get(position).getDisease());
        holder.sampleType.setText(list.get(position).getSampleType());
        holder.registeredBy.setText(list.get(position).getRegisteredBy());
        holder.receivedBy.setText(list.get(position).getReceivedBy());
        holder.sampleStatus.setText(list.get(position).getSampleStatus());
        holder.results.setText(list.get(position).getResults());
        holder.date.setText(list.get(position).getDateReceived());

        holder.call.setVisibility(View.GONE);
        holder.edit.setVisibility(View.GONE);
        holder.delete.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(v -> {

            if(holder.call.getVisibility() == View.GONE &&
               holder.edit.getVisibility() == View.GONE &&
               holder.delete.getVisibility() == View.GONE){

                holder.call.setVisibility(View.VISIBLE);
                holder.edit.setVisibility(View.VISIBLE);
                holder.delete.setVisibility(View.VISIBLE);

            } else {

                holder.call.setVisibility(View.GONE);
                holder.edit.setVisibility(View.GONE);
                holder.delete.setVisibility(View.GONE);

            }
        });

        holder.call.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
            builder.setMessage("What would you want to call the person who registered this sample ?");
            builder.setCancelable(false);

            builder.setNegativeButton("Cancel", (dialog, which) -> builder.setCancelable(true));

            builder.setPositiveButton("Call", (dialog, which) -> {

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel: " + list.get(position).getRegisters_phone_number()));
                holder.itemView.getContext().startActivity(callIntent);

                builder.setCancelable(true);
            }
            );

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        });

        holder.edit.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
            builder.setMessage("What would you want to edit this sample record ?");
            builder.setCancelable(false);

            builder.setNegativeButton("Cancel", (dialog, which) -> builder.setCancelable(true));

            builder.setPositiveButton("Edit", (dialog, which) -> {

                Intent intent = new Intent(holder.itemView.getContext(), ReceiveSamples.class);
                intent.putExtra("sample_id",list.get(position).getSampleId());
                intent.putExtra("sample_status", list.get(position).getSampleStatus());
                intent.putExtra("results", list.get(position).getResults());
                intent.putExtra("lab_notes", list.get(position).getLabNotes());

                Toast.makeText(holder.itemView.getContext(), "Switching to update section. ", Toast.LENGTH_LONG).show();

                holder.itemView.getContext().startActivity(intent);
                builder.setCancelable(true);
            }
            );

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        holder.delete.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
            builder.setMessage("What would you want to delete this record ?");
            builder.setCancelable(false);

            builder.setNegativeButton("Cancel", (dialog, which) -> builder.setCancelable(true));

            builder.setPositiveButton("Delete", (dialog, which) -> {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, References.delete_received_sample,
                        response -> {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                String message = jsonObject.getString("message");

                                if(success.equals("1")){

                                    Snackbar.make(v, message, Snackbar.LENGTH_LONG).show();

                                }else if(success.equals("0")){

                                    Snackbar.make(v, message, Snackbar.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {

                                Snackbar.make(v, "Internal system error.", Snackbar.LENGTH_LONG).show();
                                Log.e("error", e.getLocalizedMessage());
                            }
                        }, error -> {

                            Snackbar.make(v, "System error is preventing from deleting this record. ", Snackbar.LENGTH_LONG).show();
                            Log.e("TAG", error.getMessage());
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<>();
                        params.put("id", list.get(position).getSampleId());
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(holder.itemView.getContext());
                requestQueue.add(stringRequest);
                builder.setCancelable(true);

            }
            );

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
                }else{

                    ArrayList<ReceivedSamplesData> searchResults = new ArrayList<>();

                    for (ReceivedSamplesData data: listCopy) {

                        if(data.sampleId.toLowerCase().contains(searchText) ||
                        data.disease.toLowerCase().contains(searchText) ||
                        data.patientsIDNumber.toLowerCase().contains(searchText)
                        || data.receivedBy.toLowerCase().contains(searchText) ||
                        data.registeredBy.toLowerCase().contains(searchText) ||
                        data.results.toLowerCase().contains(searchText)){

                            searchResults.add(data);
                        }
                    }

                    list = searchResults;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                list = (ArrayList<ReceivedSamplesData>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sampleId, patientsIDNUmber, disease, sampleType, registeredBy, receivedBy, sampleStatus, results, date,
        call, edit, view, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sampleId = itemView.findViewById(R.id.sampleID_receive_detail);
            patientsIDNUmber = itemView.findViewById(R.id.patientsIDNumber_receive_detail);
            disease = itemView.findViewById(R.id.disease_receive_detail);
            sampleType = itemView.findViewById(R.id.sample_type_receive_detail);
            registeredBy = itemView.findViewById(R.id.deliveredBy_receive_detail);
            receivedBy = itemView.findViewById(R.id.receivedBy_receive_detail);
            sampleStatus = itemView.findViewById(R.id.sampleStatus_receive_detail);
            results = itemView.findViewById(R.id.results_receive_detail);
            date = itemView.findViewById(R.id.date_receive_detail);

            call = itemView.findViewById(R.id.received_samples_call_textView_button);
            edit = itemView.findViewById(R.id.received_samples_edit_textView_button);
            delete =itemView.findViewById(R.id.received_samples_delete_textView_button);

        }
    }
}
