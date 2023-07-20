package com.example.e_garage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.e_garage.Utility.NetworkChangeListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MyGarageDetails extends AppCompatActivity {
    final int code = 999;
    private Button confirmButton;
    private ImageView imageView;
    private DatabaseReference databaseReference;
    private String pictureLink="";
    private EditText garageName,description;
    private String division="",district="";
    private Spinner divisionSpinner,districtSpinner;
    private ArrayAdapter<String> adapterDivision;
    private  ArrayAdapter<String> adapterDistrict;
    private String[] categoryDivision = {"","Barishal","Chattogram", "Dhaka","Khulna","Mymensingh","Rajshahi","Rangpur", "Sylhet"};

    private String[] categoryDistrictOfDhaka = {"","Dhaka", "Faridpur", "Gazipur",
            "Gopalganj", "Kishoreganj", "Madaripur","Manikganj", "Munshiganj", "Narayanganj"
            , "Narsinghdi", "Rajbari", "Shariatpur", "Tangail"};

    private String[] categoryDistrictOfSylhet = {"","Habiganj", "Moulvibazar", "Sunamganj", "Sylhet"};

    private String[] categoryDistrictOfRangpur = {"","Dinajpur", "Gaibandha", "Kurigram", "Lalmonirhat",
            "Nilphamari", "Panchagar", "Rangpur", "Thakurgaon"};

    private String[] categoryDistrictOfRajshahi={"","Bogura", "Joypurhat", "'Naogaon'", "Natore",
            "Nawabganj", "Pabna", "Rajshahi", "Sirajganj"};

    private String[] categoryDistrictOfKhulna={"","Bagerhat", "Chuadanga", "Jashore", "Jhenaidah", "Khulna", "Kushtia", "Magura", "Meherpur", "Narail", "Satkhira"};

    private String[] categoryDistrictOfBarishal={"","Barguna", "Barishal", "Bhola", "Jhalakati", "Patuakhali", "Pirojpur"};

    private String[] categoryDistrictOfMymensingh={"","Jamalpur","Mymensingh","Netrokona","Sherpur"};

    private String[] categoryDistrictOfChattogram={"","Bandarban", "Brahmanbaria", "Chandpur", "Chattogram",
            "Cox’s Bazar", "Cumilla", "Feni", "Khagrachhari", "Lakshmipur", "Noakhali", "Rangamati"};
    private NetworkChangeListener networkChangeListener=new NetworkChangeListener();
    @Override
    protected void onStart() {
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
    }
    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_garage_details);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        description=findViewById(R.id.myGarageDetailsDescription);
        confirmButton=findViewById(R.id.myGarageDetailsPostButton);
        imageView=findViewById(R.id.myGarageDetailsChoosePicture);
        garageName=findViewById(R.id.myGarageDetailsName);
        divisionSpinner=findViewById(R.id.myGarageDetailsDivision);
        districtSpinner=findViewById(R.id.myGarageDetailsDistrict);


        adapterDivision = new ArrayAdapter<String>(MyGarageDetails.this, android.R.layout.simple_spinner_item, categoryDivision);
        adapterDivision.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        divisionSpinner.setAdapter(adapterDivision);

        divisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                division = adapterView.getItemAtPosition(i).toString();
                if(division.equals("")){
                    districtSpinner.setEnabled(false);
                }
                if(division.equals("Dhaka")){
                    districtSpinner.setEnabled(true);

                    adapterDistrict = new ArrayAdapter<String>(MyGarageDetails.this, android.R.layout.simple_spinner_item, categoryDistrictOfDhaka);
                    adapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }
                if(division.equals("Sylhet")){
                    districtSpinner.setEnabled(true);

                    adapterDistrict = new ArrayAdapter<String>(MyGarageDetails.this, android.R.layout.simple_spinner_item, categoryDistrictOfSylhet);
                    adapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }
                if(division.equals("Barishal")){
                    districtSpinner.setEnabled(true);

                    adapterDistrict = new ArrayAdapter<String>(MyGarageDetails.this, android.R.layout.simple_spinner_item, categoryDistrictOfBarishal);
                    adapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }if(division.equals("Chattogram")){
                    districtSpinner.setEnabled(true);

                    adapterDistrict = new ArrayAdapter<String>(MyGarageDetails.this, android.R.layout.simple_spinner_item, categoryDistrictOfChattogram);
                    adapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }if(division.equals("Khulna")){
                    districtSpinner.setEnabled(true);

                    adapterDistrict = new ArrayAdapter<String>(MyGarageDetails.this, android.R.layout.simple_spinner_item, categoryDistrictOfKhulna);
                    adapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }if(division.equals("Mymensingh")){
                    districtSpinner.setEnabled(true);

                    adapterDistrict = new ArrayAdapter<String>(MyGarageDetails.this, android.R.layout.simple_spinner_item, categoryDistrictOfMymensingh);
                    adapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }if(division.equals("Rajshahi")){
                    districtSpinner.setEnabled(true);

                    adapterDistrict = new ArrayAdapter<String>(MyGarageDetails.this, android.R.layout.simple_spinner_item, categoryDistrictOfRajshahi);
                    adapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }if(division.equals("Rangpur")){
                    districtSpinner.setEnabled(true);

                    adapterDistrict = new ArrayAdapter<String>(MyGarageDetails.this, android.R.layout.simple_spinner_item, categoryDistrictOfRangpur);
                    adapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }

                districtSpinner.setAdapter(adapterDistrict);

                districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        district = adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }



    public void myGarageDetailsChoosingButton(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == code && resultCode == Activity.RESULT_OK && data != null) {

            confirmButton.setEnabled(false);
            Uri result = data.getData();
            String key= databaseReference.push().getKey();
            StorageReference storageRef = FirebaseStorage.getInstance().getReference("picLink"+key);
            storageRef.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            pictureLink=uri.toString();
                            imageView.setImageURI(result);
                            confirmButton.setEnabled(true);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Couldn't Upload !!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void myGarageDetailsPost(View view) {
        if(pictureLink.isEmpty()){
            Toast.makeText(MyGarageDetails.this, "Didn't select a picture", Toast.LENGTH_SHORT).show();
            return;
        }
        String name=garageName.getText().toString();
        if(name.isEmpty()){
            garageName.setError("Please enter !");
            garageName.requestFocus();
            return;
        }
        String descriptionString=description.getText().toString();
        if(descriptionString.isEmpty()){
            description.setError("Please enter !");
            description.requestFocus();
            return;
        }
        if(division.isEmpty() || district.isEmpty() )
        {
            Toast.makeText(MyGarageDetails.this, "Please enter every information!", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseReference.child("E-Garage").child(division).child(district).child(SplashScreen.trimmedMail)
                .setValue(new GarageModel(division,district,name,pictureLink,descriptionString,SplashScreen.trimmedMail));
        databaseReference.child("All Garage").child(SplashScreen.trimmedMail).setValue(new GarageModel(division,district,name,pictureLink,descriptionString,SplashScreen.trimmedMail));

        databaseReference.child("Users").child(SplashScreen.trimmedMail).child("garageName")
                .setValue(name);
        databaseReference.child("Users").child(SplashScreen.trimmedMail).child("division")
                .setValue(division);
        databaseReference.child("Users").child(SplashScreen.trimmedMail).child("district")
                .setValue(district);
        databaseReference.child("Users").child(SplashScreen.trimmedMail).child("garagePictureLink")
                .setValue(pictureLink);
        Toast.makeText(this, "Posted !", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MyGarageDetails.this,MainPage.class));
        finish();

    }
}