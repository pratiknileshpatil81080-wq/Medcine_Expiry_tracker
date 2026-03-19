package com.example.medexpiry;

import android.database.Cursor;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MedicineDataActivity extends AppCompatActivity {

    DatabaseHelper db;
    TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_data);

        table = findViewById(R.id.tableLayout);

        db = new DatabaseHelper(this);

        loadData();
    }

    private void loadData(){

        Cursor cursor = db.getAllData();

        int sr=1;

        while(cursor.moveToNext()){

            int id = cursor.getInt(0);

            TableRow row = new TableRow(this);

            TextView srNo = new TextView(this);
            srNo.setText(String.valueOf(sr++));

            TextView name = new TextView(this);
            name.setText(cursor.getString(1));

            TextView count = new TextView(this);
            count.setText(cursor.getString(2));

            TextView purchase = new TextView(this);
            purchase.setText(cursor.getString(3));

            TextView expiry = new TextView(this);
            expiry.setText(cursor.getString(4));

            TextView imageLink = new TextView(this);

            String medicineName = cursor.getString(1);
            String imageUrl = cursor.getString(5);

            imageLink.setText(medicineName);
            imageLink.setTextColor(android.graphics.Color.BLUE);
            imageLink.setPaintFlags(imageLink.getPaintFlags() | android.graphics.Paint.UNDERLINE_TEXT_FLAG);

            imageLink.setOnClickListener(v -> {

                android.content.Intent intent =
                        new android.content.Intent(android.content.Intent.ACTION_VIEW,
                                android.net.Uri.parse(imageUrl));

                startActivity(intent);

            });

            EditText dosage = new EditText(this);
            dosage.setText(cursor.getString(6));

            EditText info = new EditText(this);
            info.setText(cursor.getString(7));

            Button deleteBtn = new Button(this);
            deleteBtn.setText("Delete");

            deleteBtn.setOnClickListener(v -> {

                db.deleteData(id);

                table.removeView(row);

            });

            srNo.setPadding(20,20,20,20);
            name.setPadding(20,20,20,20);
            count.setPadding(20,20,20,20);
            purchase.setPadding(20,20,20,20);
            expiry.setPadding(20,20,20,20);
            imageLink.setPadding(20,20,20,20);
            dosage.setPadding(20,20,20,20);
            info.setPadding(20,20,20,20);
            deleteBtn.setPadding(20,20,20,20);

            row.addView(srNo);
            row.addView(name);
            row.addView(count);
            row.addView(purchase);
            row.addView(expiry);
            row.addView(imageLink);
            row.addView(dosage);
            row.addView(info);
            row.addView(deleteBtn);

            table.addView(row);
        }

    }

}
