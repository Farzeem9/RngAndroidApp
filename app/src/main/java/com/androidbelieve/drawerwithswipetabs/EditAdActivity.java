package com.androidbelieve.drawerwithswipetabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class EditAdActivity extends AppCompatActivity {
    private EditText name,desc,age,inputPdeposit,duration,tags,inputPrentd,inputPrentw,inputPrentm;
    private Toolbar toolbar=null;
    private List<String> categories;
    private List<String> categories2;
    private TextView city;
    private Spinner spinner_rent,spinner_subrent,spinner,spinner2;
    private String aid;
    private CheckBox r1,r2,r3;
    private RecyclerView rr;
    private HorizontalAdapter HorizontalAdapter;
    private Uri fileUri;
    private ArrayAdapter<String> dataAdapter;
    private ArrayAdapter<String> dataAdapter4;
    private Button setasthumb,location;
    private ImageFragmentPagerAdapter imageFragmentPagerAdapter;
    private TextInputLayout inputLayoutPname, inputLayoutPdesc, inputLayoutPage, inputLayoutPdeposit, inputLayoutPrentd, inputLayoutPrentw, inputLayoutPrentm,inputLayoutPtags;
    private RelativeLayout rl;
    private ViewPager viewPager;
    private String item,number,f1="",f2="",tagstring;
    private ImageButton btnPhoto,btnGal;
    private ArrayList<Bitmap> images;
    private boolean imageshown=false;
    private int submit=0;
    private final int CITY_SEARCH_REQUEST = 123;
    int work=0;
    int currentpos=0;
    View view;
    static Boolean b=Boolean.valueOf(false);
    Boolean a=Boolean.valueOf(false);
    boolean selected=false;
    ProgressDialog progress;

    private int CAMERA_PIC_REQUEST = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ad);
        images=new ArrayList<>();
        aid=getIntent().getStringExtra("AID");
        work=2;
        inputLayoutPname = (TextInputLayout) findViewById(R.id.input_layout_pname);
        inputLayoutPdesc = (TextInputLayout) findViewById(R.id.input_layout_pdesc);
        inputLayoutPrentd = (TextInputLayout) findViewById(R.id.input_layout_prentd);
        inputLayoutPrentw = (TextInputLayout) findViewById(R.id.input_layout_prentw);
        inputLayoutPrentm = (TextInputLayout) findViewById(R.id.input_layout_prentm);
        inputLayoutPage = (TextInputLayout) findViewById(R.id.input_layout_page);
        inputLayoutPdeposit = (TextInputLayout) findViewById(R.id.input_layout_pdeposit);
        inputLayoutPtags = (TextInputLayout) findViewById(R.id.input_layout_ptags);
        name = (EditText)findViewById(R.id.input_pname);
        desc = (EditText)findViewById(R.id.input_pdesc);
        age = (EditText)findViewById(R.id.input_page);
        tags = (EditText)findViewById(R.id.input_ptags);
        inputPrentd = (EditText) findViewById(R.id.input_prentd);
        inputPrentw = (EditText) findViewById(R.id.input_prentw);
        inputPrentm = (EditText) findViewById(R.id.input_prentm);
        inputPdeposit = (EditText)findViewById(R.id.input_pdeposit);
        city=(TextView)findViewById(R.id.tv_city);
        viewPager = (ViewPager) findViewById(R.id.pager);
        imageFragmentPagerAdapter = new ImageFragmentPagerAdapter(getSupportFragmentManager(), images, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hidePager();
            }
        });

        viewPager.setAdapter(imageFragmentPagerAdapter);
        setasthumb=(Button)findViewById(R.id.thumb_button_1);
        rl=(RelativeLayout)findViewById(R.id.Relativel);
        rr=(RecyclerView)findViewById(R.id.rr);
        btnGal=(ImageButton)findViewById(R.id.btn_select);
        btnPhoto = (ImageButton)findViewById(R.id.btn_capture);

        btnGal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(images.size()==5) {
                    Toast.makeText(getApplicationContext(), "You cant give more images!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ImagePicker.create(EditAdActivity.this).folderMode(true).folderTitle("All pictures").multi().limit(5-images.size()).start(1);

            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(images.size()==5) {
                    Toast.makeText(getApplicationContext(), "You cant give more images!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent data = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = getOutputMediaFileUri();
                data.putExtra( MediaStore.EXTRA_OUTPUT, fileUri );

                startActivityForResult(data, CAMERA_PIC_REQUEST);

            }
        });


        setasthumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Exchanged values!","Great");
                imageshown=false;
                if(work==0||work==1) {
                    work=1;
                    HorizontalAdapter.setPosition(currentpos);
                }
                else
                {
                    Collections.swap(images,0,currentpos);
                    HorizontalAdapter.setPosition(0);
                }
                HorizontalAdapter.notifyDataSetChanged();
                hidePager();
                reInstantiatePager();

            }
        });


        HorizontalAdapter=new HorizontalAdapter(getApplicationContext(),images);
        rr.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        rr.setAdapter(HorizontalAdapter);
        rr.setVisibility(RecyclerView.VISIBLE);
        HorizontalAdapter.notifyDataSetChanged();

        /**
         * Adding here
         */
        r1= (CheckBox) findViewById(R.id.days);
        r2= (CheckBox) findViewById(R.id.weeks);
        r3= (CheckBox) findViewById(R.id.month);

        r1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //inputLayoutPrentd.setVisibility(View.VISIBLE);
                if(r1.isChecked())
                    inputLayoutPrentd.setVisibility(View.VISIBLE);
                else
                    inputLayoutPrentd.setVisibility(View.GONE);
                inputLayoutPdeposit.setVisibility(View.VISIBLE);
                refreshrent();
            }
        });
        r2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //inputLayoutPrentw.setVisibility(View.VISIBLE);
                if(r2.isChecked())
                    inputLayoutPrentw.setVisibility(View.VISIBLE);
                else
                    inputLayoutPrentw.setVisibility(View.GONE);
                inputLayoutPdeposit.setVisibility(View.VISIBLE);
                refreshrent();
            }
        });
        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //inputLayoutPrentm.setVisibility(View.VISIBLE);
                if(r3.isChecked())
                    inputLayoutPrentm.setVisibility(View.VISIBLE);
                else
                    inputLayoutPrentm.setVisibility(View.GONE);
                inputLayoutPdeposit.setVisibility(View.VISIBLE);
                refreshrent();
            }
        });


        location=(Button)findViewById(R.id.btn_location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data= new Intent(getApplicationContext(),SearchActivity.class);
                startActivityForResult(data,CITY_SEARCH_REQUEST);
            }
        });
        city=(TextView)findViewById(R.id.tv_city);
        //In onActivityResult method


        spinner = (Spinner) findViewById(R.id.sp_types);
        spinner_rent = (Spinner) findViewById(R.id.sp_rent_types);
        spinner_subrent = (Spinner) findViewById(R.id.sp_rent_subtypes);
        spinner2 = (Spinner) findViewById(R.id.sp_subtypes);
        categories = new ArrayList<String>();
        categories2 = new ArrayList<String>();
        categories.add("Select a Category");
        categories.add("Electronics & Appliances");
        categories.add("Cars");
        categories.add("Bikes");
        categories.add("Furniture");
        categories.add("Books, Sports & Hobbies");
        categories.add("Fashion");
        categories.add("Real Estate");
        categories.add("Tools & Equipments");
        categories2.add("Select a Sub-Category");
        categories2.add(" ");

        final List<String> rent_types = new ArrayList<String>();
        //rent_types.add("Select a Category");
        rent_types.add("Select Rent Period");
        rent_types.add("Days");
        rent_types.add("Weeks");
        rent_types.add("Months");

        final List<String> rent_subtypes= new ArrayList<String>();
        rent_subtypes.add("Select Time for Rent Period");
        rent_subtypes.add(" ");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rent_types);
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rent_subtypes);
        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories2);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner_rent.setAdapter(dataAdapter2);
        spinner_subrent.setAdapter(dataAdapter3);
        spinner2.setAdapter(dataAdapter4);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                  String item = parent.getItemAtPosition(position).toString();
                                                  if(item.equals("Electronics & Appliances")&&!b)
                                                  {
                                                      categories.remove("Select a Category");
                                                      categories2.remove("Select a Sub-Category");
                                                      categories.clear();
                                                      categories.add("Electronics & Appliances");
                                                      categories.add("Cars");
                                                      categories.add("Bikes");
                                                      categories.add("Furniture");
                                                      categories.add("Books, Sports & Hobbies");
                                                      categories.add("Fashion");
                                                      categories.add("Real Estate");
                                                      categories.add("Tools & Equipments");
                                                      a=true;
                                                      Log.v("Inside outer if","okay");
                                                  }

                                                  Log.v("item",item);
                                                  Log.v("selected item",(String )spinner.getSelectedItem());
                                                  if(item=="Electronics & Appliances"){
                                                      categories2.clear();
                                                      categories2.add("Mobile Phone");
                                                      categories2.add("Tablet");
                                                      categories2.add("Accessories");
                                                      categories2.add("Computer & Laptop");
                                                      categories2.add("TV, Video-Audio");
                                                      categories2.add("Printer");
                                                      categories2.add("Computer Accessories");
                                                      categories2.add("Camera & Lenses");
                                                      categories2.add("Kitchen Appliance");
                                                      categories2.add("Speakers");
                                                      categories2.add("Projectors");
                                                      categories2.add("Others");
                                                      categories.remove("Select a Category");
                                                      //categories2.remove("Select a Sub-Category");
                                                  }
                                                  else if(item=="Cars"){
                                                      categories2.clear();
                                                      categories2.add("Cars");
                                                      categories2.add("Commerical Vehicle");
                                                      categories2.add("Others");
                                                      categories.remove("Select a Category");
                                                      //categories2.remove("Select a Sub-Category");
                                                  }
                                                  else if(item=="Bikes"){
                                                      categories2.clear();
                                                      categories2.add("Bike");
                                                      categories2.add("Scooter");
                                                      categories2.add("Bicycle");
                                                      categories2.add("Others");
                                                      categories.remove("Select a Category");
                                                      //categories2.remove("Select a Sub-Category");
                                                  }
                                                  else if(item=="Furniture"){
                                                      categories2.clear();
                                                      categories2.add("Sofa");
                                                      categories2.add("Dining");
                                                      categories2.add("Bed");
                                                      categories2.add("Wardrobe");
                                                      categories2.add("Home Décor & Garden");
                                                      categories2.add("Others");
                                                      categories.remove("Select a Category");
                                                      //categories2.remove("Select a Sub-Category");
                                                  }
                                                  else if(item=="Books, Sports & Hobbies"){
                                                      categories2.clear();
                                                      categories2.add("Book");
                                                      categories2.add("Musical Instrument");
                                                      categories2.add("Sports Equipment");
                                                      categories2.add("Travel & Camping");
                                                      categories2.add("Gaming");
                                                      categories2.add("Party Equipment");
                                                      categories2.add("Others");
                                                      categories.remove("Select a Category");
                                                      //categories2.remove("Select a Sub-Category");
                                                  }
                                                  else if(item=="Fashion"){
                                                      categories2.clear();
                                                      categories2.add("Men");
                                                      categories2.add("Women");
                                                      categories2.add("Kids");
                                                      categories.remove("Select a Category");
                                                      //categories2.remove("Select a Sub-Category");
                                                  }
                                                  else if(item=="Real Estate"){
                                                      categories2.clear();
                                                      categories2.add("Residential");
                                                      categories2.add("Commercial");
                                                      categories2.add("Others");
                                                      categories.remove("Select a Category");
                                                      //categories2.remove("Select a Sub-Category");
                                                  }
                                                  else if(item=="Tools & Equipments"){
                                                      categories2.clear();
                                                      categories2.add("Power tool");
                                                      categories2.add("Spanner");
                                                      categories2.add("Others");
                                                  }
                                                  if(selected&&!(item.equals("Select a Category"))) {

                                                      refreshSpiner();
                                                      if(!b)
                                                      {position--;
                                                      }
                                                      spinner.setSelection(position);
                                                      b=true;
                                                  }
                                                  else
                                                  {
                                                      Log.v("inside else","okay");
                                                      selected=true;

                                                  }
                                                  Log.v("item",item);
                                                  Log.v("selected item",(String )spinner.getSelectedItem());
                                              }

                                              @Override
                                              public void onNothingSelected(AdapterView<?> parent) {

                                              }
                                          }
        );



        spinner_rent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();
                //f1=f1+item;
                if (item == "Days") {
                    rent_subtypes.clear();
                    rent_subtypes.add("1");
                    rent_subtypes.add("2");
                    rent_subtypes.add("3");
                    rent_subtypes.add("4");
                    rent_subtypes.add("5");
                    rent_subtypes.add("6");
                    rent_types.remove("Select Rent Period");
                    //rent_types.remove("Select a Category");
                    show_day();
                    //rent_subtypes.remove("Select a sub-category");
                } else if (item == "Weeks") {
                    rent_subtypes.clear();
                    rent_subtypes.add("1");
                    rent_subtypes.add("2");
                    rent_subtypes.add("3");
                    rent_types.remove("Select Rent Period");
                    //rent_types.remove("Select a Category");
                    //rent_subtypes.remove("Select a sub-category");
                    show_week();
                } else if (item == "Months")
                {
                    rent_subtypes.clear();
                    rent_subtypes.add("1");
                    rent_subtypes.add("2");
                    rent_subtypes.add("3");
                    rent_subtypes.add("4");
                    rent_subtypes.add("5");
                    rent_subtypes.add("6");
                    rent_subtypes.add("7");
                    rent_subtypes.add("8");
                    rent_subtypes.add("9");
                    rent_subtypes.add("10");
                    rent_subtypes.add("11");
                    rent_subtypes.add("12");
                    rent_types.remove("Select Rent Period");
                    //rent_types.remove("Select a Category");
                    //rent_subtypes.remove("Select a sub-category");
                    showall();
                }
                spinner_subrent.setSelection(1,true);
                spinner_subrent.setSelection(0, true);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_subrent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                number = parent.getItemAtPosition(position).toString();
                f1=item+number;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        progress = new ProgressDialog(this);
        progress.setMessage("Communicating with server..");
        progress.setIndeterminate(true);
        progress.setProgress(0);
        //progress.setCancelable(false);
        findViewById(R.id.btn_signup).post(new Runnable() {
            @Override
            public void run() {
                progress.show();
            }
        });
        findViewById(R.id.btn_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
                if(submit!=1)
                    return;
                if(tags.getText().equals(""))
                {
                    tags.setError("Please Enter some tags!");
                    inputLayoutPtags.setError("Enter space separated values!");
                    return;
                }
                /*String[] temp=tags.getText().toString().split(" ");
                StringBuffer sbuff=new StringBuffer("");
                for(String x:temp)
                    sbuff.append(x+",");
                tagstring= URLEncoder.encode(sbuff.toString());*/
                tagstring=URLEncoder.encode(tags.getText().toString().replace(","," "));
                if(work==0)
                    nowork();
                else if(work==1)
                    changethumbnail();
                else
                    uploadfullad();
            }
        });
        GenericAsyncTask genericAsyncTask=new GenericAsyncTask(getApplicationContext(), Config.link+"getadforedit.php?aid=" + aid, "Fetched Ad data", new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                String out=(String)output;
                try {
                    progress.setMessage("Fetching Images");
                    fillAdd(new JSONObject(out).getJSONArray("result"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        genericAsyncTask.execute();
    }

    public void refreshrent(){
        if(r1.isChecked()){
            inputPdeposit.setHint("Rent Deposit per Day");
            //inputLayoutPrentd.setVisibility(View.VISIBLE);
            return;
        }
        //else
        // inputLayoutPrentd.setVisibility(View.GONE);
        if(r2.isChecked()){
            inputPdeposit.setHint("Rent Deposit per Week");
            // inputLayoutPrentw.setVisibility(View.VISIBLE);
            return;
        }
        //else
        //  inputLayoutPrentw.setVisibility(View.GONE);
        if(r3.isChecked()){
            inputPdeposit.setHint("Rent Deposit per Month");
            //inputLayoutPrentm.setVisibility(View.VISIBLE);
            return;
        }
        //else
        //inputLayoutPrentm.setVisibility(View.GONE);
        if(!r1.isChecked()&&!r2.isChecked()&&!r3.isChecked()){
            inputLayoutPdeposit.setVisibility(View.GONE);
            inputLayoutPrentd.setVisibility(View.GONE);
            inputLayoutPrentw.setVisibility(View.GONE);
            inputLayoutPrentm.setVisibility(View.GONE);
        }

    }

    private void submitForm()
    {
        if (!validatePname())
        {
            return;
        }

        if (!validatePdesc()) {
            return;
        }

        if (!validatePage()) {
            return;
        }
        if (!validatePrentd()) {
            return;
        }
        if (!validatePrentw()) {
            return;
        }
        if (!validatePrentm()) {
            return;
        }
        if (!validatePdeposit()) {
            return;
        }
        if(city.toString().trim().isEmpty())
        {
            return;
        }
        submit=1;
        //Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show();
    }





    private boolean validatePname() {
        if (name.getText().toString().trim().isEmpty()) {
            inputLayoutPname.setError(getString(R.string.err_msg_name));
            requestFocus(name);
            return false;
        } else {
            inputLayoutPname.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePdesc() {
        if (desc.getText().toString().trim().isEmpty()) {
            inputLayoutPdesc.setError(getString(R.string.err_msg_desc));
            requestFocus(desc);
            return false;
        } else {
            inputLayoutPdesc.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePage() {
        if (age.getText().toString().trim().isEmpty()) {
            inputLayoutPage.setError(getString(R.string.err_msg_age));
            requestFocus(age);
            return false;
        } else {
            inputLayoutPage.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePrentd() {
        if(!r1.isChecked())
            inputPrentd.setText("0");
        if (inputPrentd.getText().toString().trim().isEmpty() && r1.isChecked()) {
            inputLayoutPrentd.setError(getString(R.string.err_msg_rent));
            requestFocus(inputPrentd);
            return false;
        }
        else if (inputPrentd.getText().toString().trim().equals("0") && r1.isChecked()){
            inputLayoutPrentd.setError("Value cannot be 0");
            requestFocus(inputPrentd);
            return false;
        }
        else {
            inputLayoutPrentd.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePrentw() {
        if(!r2.isChecked())
            inputPrentw.setText("0");
        if (inputPrentw.getText().toString().trim().isEmpty() && r2.isChecked()) {
            inputLayoutPrentw.setError(getString(R.string.err_msg_rent));
            requestFocus(inputPrentw);
            return false;
        }
        else if (inputPrentw.getText().toString().trim().equals("0") && r2.isChecked()){
            inputLayoutPrentw.setError("Value cannot be 0");
            requestFocus(inputPrentw);
            return false;
        }
        else {
            inputLayoutPrentw.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatePrentm() {
        if(!r3.isChecked())
            inputPrentm.setText("0");
        if (inputPrentm.getText().toString().trim().isEmpty()  && r3.isChecked()) {
            inputLayoutPrentm.setError(getString(R.string.err_msg_rent));
            requestFocus(inputPrentm);
            return false;
        }
        else if (inputPrentm.getText().toString().trim().equals("0") && r3.isChecked()){
            inputLayoutPrentm.setError("Value cannot be 0");
            requestFocus(inputPrentm);
            return false;
        }
        else {
            inputLayoutPrentm.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatePdeposit() {
        if (inputPdeposit.getText().toString().trim().isEmpty()) {
            inputLayoutPdeposit.setError(getString(R.string.err_msg_deposit));
            requestFocus(inputPdeposit);
            return false;
        } else {
            inputLayoutPdeposit.setErrorEnabled(false);
        }
        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    void hidePager()
    {
        imageshown=false;
        Log.v("Clicked","Hidden?");
        viewPager.setVisibility(View.GONE);
        setasthumb.setVisibility(View.GONE);

    }
    void reInstantiatePager()
    {
        viewPager.setAdapter(null);
        viewPager.setAdapter(new ImageFragmentPagerAdapter(getSupportFragmentManager(), images, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hidePager();
            }
        }));

    }

    private static Uri getOutputMediaFileUri(){
        return Uri.fromFile(getOutputMediaFile());
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "RnG");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");

        return mediaFile;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("Check", "Checking");

        if (data == null || resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == CAMERA_PIC_REQUEST) {
            if (images.size() == 5)
                return;
            Bitmap image = null;
            getContentResolver().notifyChange(fileUri, null);
            try {
                image = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
                int nh = (int) ( image.getHeight() * (720.0 / image.getWidth()) );
                image=Bitmap.createScaledBitmap(image, 720, nh, true);
                images.add(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.v("Bitmap", image.toString());

            HorizontalAdapter.notifyDataSetChanged();
            reInstantiatePager();        }
        if (requestCode == 1) {
            if (images.size() == 5)
                return;
            Log.v("Trying", "trying");
            ArrayList<Image> imagesfrompicker = (ArrayList<Image>) ImagePicker.getImages(data);
            for (Image x : imagesfrompicker) {
                String picturePath = x.getPath();
                Bitmap orig= BitmapFactory.decodeFile(picturePath);
                float div=orig.getWidth()/orig.getHeight();
                int width=720,hieght=1280;
                if(!(div<1))
                {width=1280;hieght=720;}
                Bitmap b=Config.lessResolution(picturePath,width,hieght);
                Log.v("bytecount", Integer.toString(b.getByteCount()));
                images.add(b);
            }
            HorizontalAdapter.notifyDataSetChanged();
            reInstantiatePager();        }
        else if (requestCode==CITY_SEARCH_REQUEST) {
            Log.v("Trying","trying");
            Log.v("data",data.getStringExtra("data"));
            String value = data.getStringExtra("data");
            city.setText(value);
        }

    }





    boolean clickedonce=false;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK&&!clickedonce) {
            clickedonce=true;
            Toast.makeText(this, "Press again to exit Editing Ads", Toast.LENGTH_SHORT).show();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    void nowork()
    {
        GenericAsyncTask g=new GenericAsyncTask(this, Config.link+"editadn.php", "Uploaded Edited Ad!", new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                finish();
            }
        });
        //g.setPostParams("aid="+aid,"work="+Integer.toString(work),"prod_name="+name.getText(),"description="+desc.getText(),"prod_age="+age.getText(),"category="+"Mobiles","rent="+rent.getText(),"prod_deposit="+deposit.getText());
        g.setPostParams("aid",aid,"work",Integer.toString(work),"maxrent",f1,"crent",f2,"city",city.getText().toString(),"prod_name",name.getText().toString(),"description",desc.getText().toString(),"prod_age",age.getText().toString(),"category",(String)spinner.getSelectedItem(),"rent",inputPrentd.getText().toString(),"prod_deposit",inputPdeposit.getText().toString());
        g.execute();
    }
    void changethumbnail()
    {
        GenericAsyncTask g=new GenericAsyncTask(this, Config.link+"editadn.php", "Uploaded Edited Ad!", new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                finish();
            }
        });
        //g.setPostParams("aid="+aid,"work="+Integer.toString(work),"num="+Integer.toString(HorizontalAdapter.getPostion()),"prod_name="+name.getText(),"description="+desc.getText(),"prod_age="+age.getText(),"category="+"Mobiles","rent="+rent.getText(),"prod_deposit="+deposit.getText());
        g.setPostParams("aid",aid,"work",Integer.toString(work),"num",Integer.toString(HorizontalAdapter.getPostion()+1),"prod_name",name.getText().toString(),"description",desc.getText().toString(),"prod_age",age.getText().toString(),"category",(String)spinner.getSelectedItem(),"rent",inputPrentd.getText().toString(),"prod_deposit",inputPdeposit.getText().toString());

        g.execute();
    }
    void uploadfullad()
    {
        progress = new ProgressDialog(this);
        progress.setMessage("Saving changes...");
        progress.setIndeterminate(true);
        progress.setProgress(0);

        Log.v("Work is two","okay");
        GenericAsyncTask g=new GenericAsyncTask(this, Config.link+"editadn.php", "Uploaded Edited Ad!", new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                progress.dismiss();
                AlertDialog.Builder alertbox = new AlertDialog.Builder(EditAdActivity.this);
                if (((String) output).contains("success")) {
                    alertbox.setTitle("Ad edited");
                    alertbox.setMessage("Ad has been edited successfully. Your Ad is pending currently and will be activated within 48 hours");
                    alertbox.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                } else {
                    alertbox.setMessage("There was some error, Please try again");
                }
                alertbox.show();
            }
        });
        g.setPostParams("subcat",(String)spinner2.getSelectedItem(),"tags",tagstring,"aid",aid,"work",Integer.toString(work),"maxrent",f1,"crent",f2,"city",city.getText().toString(),"num",Integer.toString(images.size()),"prod_name",name.getText().toString(),"description",desc.getText().toString(),"prod_age",age.getText().toString(),"category",(String)spinner.getSelectedItem(),"rent",inputPrentd.getText().toString(),"prod_deposit",inputPdeposit.getText().toString(),"rentweek",inputPrentw.getText().toString(),"rentmonth",inputPrentm.getText().toString());
        int i=1;
        g.setImagePost(images,1);
        //g.execute();
    }
    public void refreshSpiner()
    {
        selected=false;
        dataAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categories);
        dataAdapter4=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categories2);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
        spinner2.setAdapter(dataAdapter4);
    }
    void showall()
    {
        findViewById(R.id.weeks).setVisibility(View.VISIBLE);
        findViewById(R.id.month).setVisibility(View.VISIBLE);
    }
    void show_week()
    {
        findViewById(R.id.month).setVisibility(View.GONE);
        r3.setChecked(false);
        findViewById(R.id.weeks).setVisibility(View.VISIBLE);
    }
    void show_day()
    {
        findViewById(R.id.weeks).setVisibility(View.GONE);
        findViewById(R.id.month).setVisibility(View.GONE);
        r2.setChecked(false);
        r3.setChecked(false);
    }


    void fillAdd(JSONArray jarray)
    {
        try {
            JSONObject c = jarray.getJSONObject(0);
            String prod_name=c.getString("PROD_NAME");
            String rent_name=c.getString("RENT");
            String desc_str=c.getString("DESC");
            String prod_age=c.getString("PROD_AGE");
            String sdeposit=c.getString("PROD_DEPOSIT");
            String duration=c.getString("DURATION");
            String rentweek=c.getString("RENTW");
            String rentmonth=c.getString("RENTM");

//            String maxrent=c.getString("maxrent");
//            String crent=c.getString("crent");
            String city=c.getString("CITY");
            this.city.setText(city);
            JSONArray links=c.getJSONArray("LINKS");

            ArrayList<String> alllinks=new ArrayList<>();
            for(int i=0;i<links.length();i++)
                alllinks.add(links.getJSONObject(i).getString("link"));
            final int[] length = {links.length()};
            name.setText(prod_name);
            desc.setText(desc_str);
            inputPrentd.setText(rent_name);
            inputPrentw.setText(rentweek);
            inputPrentm.setText(rentmonth);

            if(Integer.parseInt(rent_name)!=0)
                r1.setChecked(true);
            if(Integer.parseInt(rentweek)!=0)
                r2.setChecked(true);
            if(Integer.parseInt(rentmonth)!=0)
                r3.setChecked(true);
            age.setText(prod_age);
            inputPdeposit.setText(sdeposit);
//            this.duration.setText(duration);
            if(alllinks.size()==0)
                dismissProgress();
            HorizontalAdapter.addLinks(alllinks);
/*            ImageView imageView=(ImageView)findViewById(R.id.noimage);
            Target[] targets=new Target[alllinks.size()>0?alllinks.size():1];
            int i=0;

            for(String x:alllinks) {
                targets[i++]=new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        length[0]--;
                        Log.v("Length",Integer.toString(length[0]));
                        if(length[0]==0)
                            progress.dismiss();
                        images.add(bitmap);
                        HorizontalAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                };
                imageView.setTag(targets[i-1]);
                Picasso.with(this).load(x).into(targets[i-1]);
                Log.v("link in picasso",x);
            }
            */

        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    class HorizontalAdapter  extends RecyclerView.Adapter<EditAdActivity.HorizontalAdapter.MyViewHolder> {
        private Context mContext;
        private ArrayList<Bitmap> images;
        private ArrayList<String> links=new ArrayList<>();
        private int thumbnail=0;
        private boolean imagesloaded=false;
        final int x[]={0};
        public HorizontalAdapter(Context mContext, final ArrayList<Bitmap> images) {
            this.mContext = mContext;
            this.images=images;
            Log.v("Adapter created","Created");

        }

        void setPosition(int i)
        {
            thumbnail=i;
        }
        int getPostion()
        {return thumbnail;}
        @Override
        public EditAdActivity.HorizontalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_pic, parent, false);
            Log.v("oncreateViewholder","currect");
            return new HorizontalAdapter.MyViewHolder(itemView);

        }


        @Override
        public void onBindViewHolder(final EditAdActivity.HorizontalAdapter.MyViewHolder holder, final int position) {

           // holder.i.setImageBitmap(images.get(position));
            if(!imagesloaded) {
                Target t = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        if (position < images.size())
                            images.add(position, bitmap);
                        else
                            images.add(bitmap);
                        holder.i.setImageBitmap(bitmap);
                        holder.i.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                reInstantiatePager();
                                reInstantiatePager();
                                imageshown = true;
                                viewPager.setVisibility(View.VISIBLE);
                                viewPager.setCurrentItem(position);
                            }
                        });
                        holder.del.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                work=2;
                                images.remove(position);
                                if(thumbnail==position) {
                                    thumbnail = 0;
                                    currentpos=0;
                                }
                                notifyDataSetChanged();
                                reInstantiatePager();
                            }
                        });
                        if (x[0]-- == 1) {
                            dismissProgress();

                        }
                        holder.i.setAnimation(null);

                        Log.v("Bitmap set!", "okay");

                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        holder.i.setImageDrawable(errorDrawable);
                        AlertDialog.Builder alertbox = new AlertDialog.Builder(EditAdActivity.this);
                        alertbox.setTitle("Error");
                        alertbox.setMessage("There seems to have been an error while loadingpic the images, Please try again later");
                        alertbox.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        alertbox.show();
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        RotateAnimation anim = new RotateAnimation(0f, 350f, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
                        anim.setInterpolator(new LinearInterpolator());
                        anim.setRepeatCount(Animation.INFINITE);
                        anim.setDuration(700);
                        holder.i.startAnimation(anim);

                    }
                };
                holder.i.setTag(t);
                Picasso.with(mContext).load(links.get(position)).error(R.drawable.car).placeholder(R.drawable.loadingpic).into(t);
            }
            else
            {
                holder.i.setImageBitmap(images.get(position));
            }

            Log.v("inside","holder setting bitmap");
            /**
             *Set all onclicks here
             *
             */
            holder.i.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reInstantiatePager();
                    reInstantiatePager();
                    currentpos=position;
                    imageshown=true;
                    viewPager.setVisibility(View.VISIBLE);
                    setasthumb.setVisibility(View.VISIBLE);
                    viewPager.setCurrentItem(position);

                }
            });
            holder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    work=2;
                    images.remove(position);
              if(thumbnail==position) {
                  thumbnail = 0;
                  currentpos=0;
              }
                    notifyDataSetChanged();
                    reInstantiatePager();
                }
            });
            if(position==thumbnail)
            {
                holder.relativeLayout.setBackgroundResource(R.drawable.background_border);
            }
            else
                holder.relativeLayout.setBackgroundResource(R.drawable.empty);
        }
        @Override
        public int getItemCount() {
            return imagesloaded?images.size():this.links.size();
        }


        class MyViewHolder extends RecyclerView.ViewHolder {
            Button set;
            ImageView i;
            ImageButton del;
            RelativeLayout relativeLayout;
            MyViewHolder(View view) {
                super(view);
                relativeLayout=(RelativeLayout)view.findViewById(R.id.rel_lay);
                del=(ImageButton)view.findViewById(R.id.yes_bt);
                i=(ImageView)view.findViewById(R.id.act_image);
                //set=(Button)view.findViewById(R.id.button);
            }

        }
        public void setImagesloaded(){this.imagesloaded=true;}
        public void addLinks(ArrayList<String> newLinks)
        {
            this.links.addAll(newLinks);
            x[0]=links.size();
            Log.v("links added","okay");
            HorizontalAdapter.notifyDataSetChanged();
        }
    }
    public void dismissProgress() {
        this.progress.dismiss();
        HorizontalAdapter.setImagesloaded();
    }

}
