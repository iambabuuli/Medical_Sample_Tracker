package com.medicalsampletracker.app;

public class References {

    //Local host address of the machine
//    static final String address = "http://192.168.43.132/sample_tracker_app/";

    //Address of the online server
    static final String address = "http://wenzotek.com/projects/api/sample_tracker_app/";

    static final String login_url = address + "login.php";
    static final String register_url = address + "create_account.php";
    static final String delete_user_account = address + "delete_user_account.php";
    static final String change_user_privileges = address + "change_user_privileges.php";
    static final String update_account_without_password = address + "update_account_without_password.php";
    static final String update_account_with_password = address + "update_account_with_password.php";
    static final String delete_registered_sample = address + "delete_registered_sample.php";
    static final String delete_received_sample = address + "delete_received_sample.php";
    static final String update_registered_sample = address + "update_registered_sample.php";
    static final String update_received_sample = address + "update_received_samples.php";
    static final String register_sample_url = address +"register_samples.php";
    static final String receive_sample_url = address +"receive_samples.php";
    static final String view_reg_sample_url = address +"view_registered_sample.php";
    static final String view_received_sample_url = address +"view_received_sample.php";
    static final String modify_delete_url = address + "modify_delete.php";
    static final String manage_account_url = address + "manage_account.php";
    static final String display_user_accounts_list = address + "display_user_accounts_list.php";
    static final String payment_of_account_url = address + "payment_of_account.php";
    static final String update_payment_of_account_url = address + "update_payment_of_account.php";
    static final String delete_payment_of_account_url = address + "delete_payment_of_account.php";
    static final String view_payment_of_account = address + "view_payment_of_account.php";

    //Statistics
    static final String total_samples_number = address + "statistics/total_samples.php";
    static final String negative_samples_number = address + "statistics/negative_samples.php";
    static final String positive_samples_number = address + "statistics/positive_samples.php";
    static final String pending_samples_number = address + "statistics/pending_samples.php";

    static final String get_count_for_results_and_diseases_in_location = address + "statistics/get_count_for_results_and_diseases_in_location.php";

    static final String get_all_existing_addresses = address + "statistics/get_all_existing_addresses.php";
    static final String get_all_existing_suspected_diseases = address + "statistics/get_all_existing_suspected_diseases.php";
    static final String get_Data_Entries_For_PieChart = address + "statistics/get_pie_chart_data_for_suspected_diseases.php";

}
