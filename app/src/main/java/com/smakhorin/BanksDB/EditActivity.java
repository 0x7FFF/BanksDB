package com.smakhorin.BanksDB;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    EditText mName;
    EditText mAddress;
    EditText mPhone;
    EditText mNumberofemployees;
    EditText mWorktime;
    EditText mEmail;

    String[] data;
    Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mName = findViewById(R.id.tv_edit_name);
        mAddress = findViewById(R.id.tv_edit_address);
        mPhone = findViewById(R.id.tv_edit_phone);
        mNumberofemployees = findViewById(R.id.tv_edit_employeecount);
        mWorktime = findViewById(R.id.tv_edit_time);
        mEmail = findViewById(R.id.tv_edit_email);

        data = getIntent().getStringArrayExtra("inputData");
        mName.setText(data[1]);
        mAddress.setText(data[2]);
        mPhone.setText(data[3]);
        mNumberofemployees.setText(data[4]);
        mWorktime.setText(data[5]);
        mEmail.setText(data[6]);
    }

    public void submitData(View view) {
        if(mName.getText().toString().equals("")) {
            displayErrorMessage("Name is empty!");
            return;
        }
        if(mAddress.getText().toString().equals("")) {
            displayErrorMessage("Address is empty!");
            return;
        }
        if(mPhone.getText().toString().equals("")) {
            displayErrorMessage("Phone is empty!");
            return;
        }
        if(mNumberofemployees.getText().toString().equals("")) {
            displayErrorMessage("Number of Employees is empty!");
            return;
        }
        else if(Integer.valueOf(mNumberofemployees.getText().toString()) <= 0) {
            displayErrorMessage("Number of Employees is 0 or negative!");
            return;
        }
        if(mWorktime.getText().toString().equals("")) {
            displayErrorMessage("Worktime is empty!");
            return;
        }
        if(mEmail.getText().toString().equals("")) {
            displayErrorMessage("E-Mail is empty!");
            return;
        }

        data[1] = mName.getText().toString();
        data[2] = mAddress.getText().toString();
        data[3] = mPhone.getText().toString();
        data[4] = mNumberofemployees.getText().toString();
        data[5] = mWorktime.getText().toString();
        data[6] = mEmail.getText().toString();

        Intent output = new Intent();
        output.putExtra("outputData",data);
        setResult(MainActivity.RESULT_OK,output);
        finish();
    }

    private void displayErrorMessage(String msg) {
        if (mToast != null) {
            mToast.cancel();
        }

        mToast = Toast.makeText(this,msg,Toast.LENGTH_SHORT);
        mToast.show();
    }
}
