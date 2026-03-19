package com.example.medexpiry;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int IMAGE_PICK_CODE = 100;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    Button selectBtn;
    Button scanBtn;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // UI components
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        selectBtn = findViewById(R.id.selectImageBtn);
        scanBtn = findViewById(R.id.scanBtn);
        imageView = findViewById(R.id.imageView);

        // Navigation Drawer Menu
        navigationView.setNavigationItemSelectedListener(item -> {

            if(item.getItemId()==R.id.nav_profile){
                Toast.makeText(this,"Profile Clicked",Toast.LENGTH_SHORT).show();
            }

            else if(item.getItemId()==R.id.nav_scan){
                startQRScanner();
            }

            else if(item.getItemId()==R.id.nav_data){

                Intent intent = new Intent(MainActivity.this,MedicineDataActivity.class);
                startActivity(intent);

            }

            else if(item.getItemId()==R.id.nav_help){
                Toast.makeText(this,"Helpline",Toast.LENGTH_SHORT).show();
            }

            drawerLayout.closeDrawers();
            return true;
        });

        // Image Picker Button
        selectBtn.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, IMAGE_PICK_CODE);

        });

        // Scan Button
        scanBtn.setOnClickListener(v -> startQRScanner());

    }

    // QR Scanner Function
    private void startQRScanner(){

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan Medicine QR");
        integrator.setCameraId(0);
        integrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // QR Scanner Result
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(result != null){

            if(result.getContents() != null){

                try{

                    String qrData = result.getContents();

                    String[] lines = qrData.split("\n");

                    String name = lines[0].split(":")[1].trim();
                    String expiry = lines[1].split(":")[1].trim();
                    int count = Integer.parseInt(lines[2].split(":")[1].trim());

                    DatabaseHelper db = new DatabaseHelper(this);

                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                    String purchaseDate = sdf2.format(new Date());

                    String imageLink = "https://www.google.com/search?q=" + name + "&tbm=isch";

                    db.insertData(name, count, purchaseDate, expiry, imageLink, "", "");
                    Toast.makeText(this,"Medicine Saved!",Toast.LENGTH_LONG).show();

                    // Expiry Calculation
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    Date today = sdf.parse(sdf.format(new Date()));
                    Date expiryDate = sdf.parse(expiry);

                    long diff = expiryDate.getTime() - today.getTime();

                    long days = diff / (1000 * 60 * 60 * 24);

                    if(days==3)
                        Toast.makeText(this,name+" expires in 3 days",Toast.LENGTH_LONG).show();

                    else if(days==2)
                        Toast.makeText(this,name+" expires in 2 days",Toast.LENGTH_LONG).show();

                    else if(days==1)
                        Toast.makeText(this,name+" expires tomorrow",Toast.LENGTH_LONG).show();

                    else if(days==0)
                        Toast.makeText(this,name+" expires today",Toast.LENGTH_LONG).show();

                    else if(days<0)
                        Toast.makeText(this,name+" already expired",Toast.LENGTH_LONG).show();

                }
                catch(Exception e){

                    Toast.makeText(this,"QR Format Error",Toast.LENGTH_LONG).show();

                }

            }

            return;

        }

        // Image Picker Result
        if(requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null){

            Uri imageUri = data.getData();

            Glide.with(this)
                    .load(imageUri)
                    .into(imageView);

        }

    }

}