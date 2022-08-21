package com.medicalsampletracker.app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

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

public class RegSamplesListAdapter extends RecyclerView.Adapter<RegSamplesListAdapter.ViewHolder> implements Filterable{

    ArrayList<RegisteredSamplesData> list;
    ArrayList<RegisteredSamplesData> listCopy;
    Context context;

    public RegSamplesListAdapter(ArrayList<RegisteredSamplesData> list, Context context) {
        this.list = list;
        this.listCopy = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.register_samples_details, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.sampleId.setText(list.get(position).getSampleId());
        holder.patientsIDNumber.setText(list.get(position).getPatientsIDNo());
        holder.disease.setText(list.get(position).getSuspectedDisease());
        holder.sampleType.setText(list.get(position).getSampleType());
        holder.registeredBy.setText(list.get(position).getRegisteredBy());
        holder.isSampleReceived.setText(list.get(position).getIsSampleReceived());
        holder.result.setText(list.get(position).getResults());
        holder.paid.setText(list.get(position).getPaid());
        holder.date.setText(list.get(position).getDateRegistered());

        holder.call.setVisibility(View.GONE);
        holder.view_patient_information.setVisibility(View.GONE);
        holder.edit.setVisibility(View.GONE);
        holder.delete.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(v -> {

            if(holder.call.getVisibility() == View.GONE &&
            holder.view_patient_information.getVisibility() == View.GONE &&
            holder.edit.getVisibility() == View.GONE &&
            holder.delete.getVisibility() == View.GONE){

            holder.view_patient_information.setVisibility(View.VISIBLE);
            holder.edit.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.VISIBLE);

            if(!list.get(position).getReceivers_phone_number().equals(""))
                    holder.call.setVisibility(View.VISIBLE);

            } else {

                holder.call.setVisibility(View.GONE);
                holder.view_patient_information.setVisibility(View.GONE);
                holder.edit.setVisibility(View.GONE);
                holder.delete.setVisibility(View.GONE);

            }

        });

        holder.call.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
            builder.setMessage("What would you want to call the person who received this sample ?");
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

        holder.view_patient_information.setOnClickListener(v -> {

            Intent intentDetails = new Intent(holder.itemView.getContext(), RegisteredSamplesDetailedInformation.class);

            intentDetails.putExtra("sample_id",list.get(position).getSampleId());
            intentDetails.putExtra("patients_id_number", list.get(position).getPatientsIDNo());
            intentDetails.putExtra("patients_surname", list.get(position).getPatientsSurname());
            intentDetails.putExtra("patients_firstName", list.get(position).getPatientsFirstName());
            intentDetails.putExtra("patients_date_of_birth", list.get(position).getPatientsDateOfBirth());
            intentDetails.putExtra("patients_gender", list.get(position).getPatientsGender());
            intentDetails.putExtra("patients_address", list.get(position).getPatientsAddress());
            intentDetails.putExtra("patients_age", list.get(position).getPatientsAge());
            intentDetails.putExtra("hospital_or_folio_number", list.get(position).getHospitalOrFolioNumber());
            intentDetails.putExtra("patients_phone_number", list.get(position).getPatientsPhoneNumber());
            intentDetails.putExtra("patients_email", list.get(position).getPatientsEmail());
            intentDetails.putExtra("hospital_patient", list.get(position).getHospitalPatient());
            intentDetails.putExtra("expected_date_of_return", list.get(position).getExpectedDateOfReturn());
            intentDetails.putExtra("clinical_or_drug_information", list.get(position).getClinicalOrDrugInformation());
            intentDetails.putExtra("suspected_disease", list.get(position).getSuspectedDisease());
            intentDetails.putExtra("next_of_kin", list.get(position).getNextOfKin());
            intentDetails.putExtra("sample_type", list.get(position).getSampleType());
            intentDetails.putExtra("registered_by", list.get(position).getRegisteredBy());
            intentDetails.putExtra("received_by", list.get(position).getReceivedBy());
            intentDetails.putExtra("date_registered", list.get(position).getDateRegistered());
            intentDetails.putExtra("lab_notes", list.get(position).getLabNotes());
            intentDetails.putExtra("results", list.get(position).getResults());
            intentDetails.putExtra("paid", list.get(position).getPaid());
            intentDetails.putExtra("is_sample_received", list.get(position).getIsSampleReceived());

            holder.itemView.getContext().startActivity(intentDetails);

        });

        holder.edit.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
            builder.setMessage("Do you want to edit this record ?");
            builder.setCancelable(false);

            builder.setPositiveButton("Yes", (dialog, which) -> {

                Intent intent = new Intent(holder.itemView.getContext(), RegisterSamples.class);

                intent.putExtra("sample_id",list.get(position).getSampleId());
                intent.putExtra("patients_id_number", list.get(position).getPatientsIDNo());
                intent.putExtra("patients_surname", list.get(position).getPatientsSurname());
                intent.putExtra("patients_firstName", list.get(position).getPatientsFirstName());
                intent.putExtra("patients_date_of_birth", list.get(position).getPatientsDateOfBirth());
                intent.putExtra("patients_gender", list.get(position).getPatientsAge());
                intent.putExtra("patients_address", list.get(position).getPatientsAddress());
                intent.putExtra("patients_age", list.get(position).getPatientsAge());
                intent.putExtra("hospital_or_folio_number", list.get(position).getHospitalOrFolioNumber());
                intent.putExtra("patients_phone_number", list.get(position).getPatientsPhoneNumber());
                intent.putExtra("patients_email", list.get(position).getPatientsEmail());
                intent.putExtra("hospital_patient", list.get(position).getHospitalPatient());
                intent.putExtra("expected_date_of_return", list.get(position).getExpectedDateOfReturn());
                intent.putExtra("clinical_or_drug_information", list.get(position).getClinicalOrDrugInformation());
                intent.putExtra("suspected_disease", list.get(position).getSuspectedDisease());
                intent.putExtra("next_of_kin", list.get(position).getNextOfKin());
                intent.putExtra("sample_type", list.get(position).getSampleType());
                intent.putExtra("registered_by", list.get(position).getRegisteredBy());

                holder.itemView.getContext().startActivity(intent);
                builder.setCancelable(true);
            });

            builder.setNegativeButton("No", (dialog, which) -> builder.setCancelable(true)
            );

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        });

        holder.delete.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
            builder.setMessage("Do you want to delete this record ?");

            builder.setNegativeButton("No", (dialog, which) -> builder.setCancelable(true));

            builder.setPositiveButton("Yes", (dialog, which) -> {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, References.delete_registered_sample,
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

                                e.printStackTrace();
                                Snackbar.make(v, "Internal server error.", Snackbar.LENGTH_LONG).show();
                                Log.e("TAG", e.getMessage());
                            }
                        }, error -> Snackbar.make(v, "System error.", Snackbar.LENGTH_LONG).show()){
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

                    ArrayList<RegisteredSamplesData> searchResults = new ArrayList<>();

                    for (RegisteredSamplesData data: listCopy) {

                        if(data.sampleId.toLowerCase().contains(searchText) ||
                        data.patientsIDNo.toLowerCase().contains(searchText) ||
                        data.suspectedDisease.toLowerCase().contains(searchText) ||
                        data.registeredBy.toLowerCase().contains(searchText) ||
                        data.sampleType.toLowerCase().contains(searchText) ||
                        data.isSampleReceived.toLowerCase().contains(searchText) ||
                        data.paid.toLowerCase().contains(searchText) ||
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
                list = (ArrayList<RegisteredSamplesData>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sampleId, patientsIDNumber, disease, sampleType, registeredBy, isSampleReceived, result, date, paid,
        call, view_patient_information, edit, delete;

        public ViewHolder(View view) {
            super(view);

            sampleId = view.findViewById(R.id.sampleID_reg_detail);
            patientsIDNumber = view.findViewById(R.id.patientsIDNumber_reg_detail);
            disease = view.findViewById(R.id.suspected_disease_reg_detail);
            sampleType = view.findViewById(R.id.sample_type_reg_detail);
            registeredBy = view.findViewById(R.id.registered_by_reg_detail);
            isSampleReceived = view.findViewById(R.id.is_sample_received_reg_detail);
            result = view.findViewById(R.id.results_reg_detail);
            paid = view.findViewById(R.id.paid_reg_detail);
            date = view.findViewById(R.id.date_registered_reg_detail);
            call = view.findViewById(R.id.registered_samples_call_textView_button);
            view_patient_information = view.findViewById(R.id.registered_samples_view_textView_button);
            edit = view.findViewById(R.id.registered_samples_edit_textView_button);
            delete = view.findViewById(R.id.registered_samples_delete_textView_button);

        }
    }
}

