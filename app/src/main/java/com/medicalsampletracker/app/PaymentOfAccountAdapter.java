package com.medicalsampletracker.app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
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

class PaymentOfAccountAdapter extends RecyclerView.Adapter<PaymentOfAccountAdapter.ViewHolder> implements Filterable {

    ArrayList<PaymentOfAccountData> list;
    ArrayList<PaymentOfAccountData> listCopy;

    public PaymentOfAccountAdapter(ArrayList<PaymentOfAccountData> list) {
        this.list = list;
        this.listCopy = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_of_account_list_details, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.patientIdNumber.setText(list.get(position).getPatientsIdNumber());
        holder.IDNumber.setText(list.get(position).getIdNumber());
        holder.surname.setText(list.get(position).getSurname());
        holder.firstName.setText(list.get(position).getFirstName());
        holder.title.setText(list.get(position).getTitle());
        holder.postalAddress.setText(list.get(position).getPostalAddress());
        holder.telephoneNumber.setText(list.get(position).getTelephoneNumber());
        holder.email.setText(list.get(position).getEmail());
        holder.employer.setText(list.get(position).getEmployer());
        holder.medicalAidName.setText(list.get(position).getMedicalAidName());
        holder.receiptName.setText(list.get(position).getCashReceiptNumber());
        holder.date.setText(list.get(position).getDate());

        holder.edit.setVisibility(View.GONE);
        holder.delete.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(v -> {

            if(holder.edit.getVisibility() == View.GONE &&
               holder.delete.getVisibility() == View.GONE){

                holder.edit.setVisibility(View.VISIBLE);
                holder.delete.setVisibility(View.VISIBLE);

            } else {

                holder.edit.setVisibility(View.GONE);
                holder.delete.setVisibility(View.GONE);
            }
        });

        holder.edit.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
            builder.setMessage("Do you want to edit this record ?");
            builder.setCancelable(false);

            builder.setPositiveButton("Yes", (dialog, which) -> {

                Intent intent = new Intent(holder.itemView.getContext(), PaymentOfAccount.class);

                intent.putExtra("patients_id_number", list.get(position).getPatientsIdNumber());
                intent.putExtra("id_no", list.get(position).getIdNumber());
                intent.putExtra("surname", list.get(position).getSurname());
                intent.putExtra("first_name", list.get(position).getFirstName());
                intent.putExtra("title", list.get(position).getTitle());
                intent.putExtra("postal_address", list.get(position).getPostalAddress());
                intent.putExtra("telephone_number", list.get(position).getTelephoneNumber());
                intent.putExtra("email", list.get(position).getEmail());
                intent.putExtra("employer", list.get(position).getEmployer());
                intent.putExtra("medical_aid_name", list.get(position).getMedicalAidName());
                intent.putExtra("cash_receipt_number", list.get(position).getCashReceiptNumber());

                holder.itemView.getContext().startActivity(intent);

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

                StringRequest stringRequest = new StringRequest(Request.Method.POST, References.delete_payment_of_account_url,
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
                        params.put("id", list.get(position).getCashReceiptNumber());
                        params.put("patient_id", list.get(position).getPatientsIdNumber());
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
                }else {

                    ArrayList<PaymentOfAccountData> searchResults = new ArrayList<>();

                    for (PaymentOfAccountData data : listCopy) {

                        if (data.patientsIdNumber.toLowerCase().contains(searchText) ||
                        data.idNumber.toLowerCase().contains(searchText) ||
                        data.surname.toLowerCase().contains(searchText) ||
                        data.firstName.toLowerCase().contains(searchText) ||
                        data.title.toLowerCase().contains(searchText) ||
                        data.postalAddress.toLowerCase().contains(searchText) ||
                        data.telephoneNumber.toLowerCase().contains(searchText) ||
                        data.email.toLowerCase().contains(searchText) ||
                        data.employer.toLowerCase().contains(searchText) ||
                        data.medicalAidName.toLowerCase().contains(searchText) ||
                        data.cashReceiptNumber.toLowerCase().contains(searchText)){

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
                list = (ArrayList<PaymentOfAccountData>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView patientIdNumber, IDNumber, surname, firstName, title, postalAddress, telephoneNumber, email, employer,
        medicalAidName, receiptName, date, edit, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            patientIdNumber = itemView.findViewById(R.id.payment_of_account_patient_id_list_detail);
            IDNumber = itemView.findViewById(R.id.payment_of_account_id_number_list_detail);
            surname = itemView.findViewById(R.id.payment_of_account_surname_list_detail);
            firstName = itemView.findViewById(R.id.payment_of_account_firstName_list_detail);
            title = itemView.findViewById(R.id.payment_of_account_title_list_detail);
            postalAddress = itemView.findViewById(R.id.payment_of_account_postalAddress_list_detail);
            telephoneNumber = itemView.findViewById(R.id.payment_of_account_telephoneNumber_list_detail);
            email = itemView.findViewById(R.id.payment_of_account_email_list_detail);
            employer = itemView.findViewById(R.id.payment_of_account_employer_list_detail);
            medicalAidName = itemView.findViewById(R.id.payment_of_account_medicalAidName_list_detail);
            receiptName = itemView.findViewById(R.id.payment_of_account_cashReceipt_list_detail);
            date = itemView.findViewById(R.id.payment_of_account_date_list_detail);

            edit = itemView.findViewById(R.id.payment_of_account_edit_textView_button);
            delete = itemView.findViewById(R.id.payment_of_account_delete_textView_button);

        }
    }
}
