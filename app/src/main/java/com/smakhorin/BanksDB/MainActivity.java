package com.smakhorin.BanksDB;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
implements BanksAdapter.ListItemClickListener {

    public static final String DATABASE_NAME = "Banks.db";

    private RecyclerView recyclerView;
    private BanksAdapter banksAdapter;
    private DatabaseHelper mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        banksAdapter = new BanksAdapter(this);
        mDatabase = new DatabaseHelper(this);

        listDatabase();

        Objects.requireNonNull(getSupportActionBar()).setTitle("Bank Info");
        recyclerView = findViewById(R.id.rv_banks);
        recyclerView.setLayoutManager(new LinearLayoutManagerWrapper(this));
        recyclerView.setAdapter(banksAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_add) {
            Intent intent = new Intent(this,AddActivity.class);
            startActivityForResult(intent,100);
            return true;
        }
        if(id == R.id.menu_refresh) {
            banksAdapter = new BanksAdapter(this);
            listDatabase();
            recyclerView.setAdapter(banksAdapter);
            Toast.makeText(this,"Refreshed button pressed!",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Log.d("OnActivityResult:"," ENTERED");
        if (data != null) {
            if(requestCode == 100) {
                String[] getData = data.getStringArrayExtra("outputData");
                if(getData != null) {
                    Bank b = new Bank(getData[0], getData[1], getData[2], Integer.valueOf(getData[3]), getData[4], getData[5]);
                    mDatabase.addBank(b);
                    banksAdapter.notifyItemInserted(banksAdapter.getItemCount() - 1);
                }
            }
            if(requestCode == 101) {
                String[] getData = data.getStringArrayExtra("outputData");
                if(getData != null) {
                    Bank b = new Bank(Integer.valueOf(getData[0]), getData[1], getData[2], getData[3], Integer.valueOf(getData[4]), getData[5], getData[6]);
                    mDatabase.updateProduct(b);
                    banksAdapter.notifyDataSetChanged();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void listDatabase() {
        List<Bank> banks = mDatabase.listBanks();
        if(banks.size() > 0) {
            Bank[] banksArray = new Bank[banks.size()];
            banksArray = banks.toArray(banksArray);
            banksAdapter.setData(banksArray);
        }
        else {
            generateDataBaseFromJSON();
        }
    }


    private void generateDataBaseFromJSON() {
        Gson gson = new Gson();
        try {
            Bank[] banks = gson.fromJson(readRawJSON(getResources().openRawResource(R.raw.generated)), Bank[].class);
            for(Bank b : banks) {
                mDatabase.addBank(b);
            }
            banksAdapter.setData(banks);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String readRawJSON(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buf = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toString();
    }


    @Override
    public void onEditClick(int position) {
        Bank b = banksAdapter.getElement(position);
        String[] dataToSend = new String[7];
        dataToSend[0] = String.valueOf(b.getId());
        dataToSend[1] = b.getName();
        dataToSend[2] = b.getAddress();
        dataToSend[3] = b.getPhone();
        dataToSend[4] = String.valueOf(b.getEmployeeCount());
        dataToSend[5] = b.getWorkTime();
        dataToSend[6] = b.getEmail();
        Intent intent = new Intent(this,EditActivity.class);
        intent.putExtra("inputData",dataToSend);
        startActivityForResult(intent,101);
    }

    @Override
    public void onDeleteClick(int position) {
        Long l = banksAdapter.getItemId(position);
        mDatabase.deleteBank(l.intValue());
        banksAdapter.notifyItemRemoved(position);
        banksAdapter.notifyItemRangeChanged(position,banksAdapter.getItemCount());
    }
}
