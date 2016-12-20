package com.androidbelieve.drawerwithswipetabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdFragment extends Fragment implements AdapterView.OnItemClickListener {
    private CheckBox r1,r2,r3;
    private List<String> categories;
    private List<String> categories2;
    private Spinner spinner,spinner_rent,spinner_subrent,spinner2;
    private EditText inputPname, inputPdesc, inputPage, inputPrent, inputPdeposit,inputPtags;
    private TextInputLayout inputLayoutPname, inputLayoutPdesc, inputLayoutPage, inputLayoutPdeposit, inputLayoutPrent,inputLayoutPtags;
    private TextView city;
    private Fragment fragment;
    private Uri fileUri;
    private Button btnSignUp,location;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private LinearLayout pics;
    private ImageButton btnPhoto,btnGal;
    private Button setasthumb;
    private RelativeLayout rl;
    private int currentpos=0;
    private ArrayList<Bitmap> images = new ArrayList<>();
    private ArrayAdapter<String> dataAdapter;
    private ArrayAdapter<String> dataAdapter4;
    View view;
    private ImageFragmentPagerAdapter imageFragmentPagerAdapter;
    private final int CAMERA_PIC_REQUEST = 10;
    private final int CITY_SEARCH_REQUEST = 123;
    private RecyclerView recyclerView;
    private HorizontalAdapter horizontalAdapter;
    private String item,number,f1="",f2="";

    static Boolean b=Boolean.valueOf(false); //To check if spinner has been selected once
    Boolean a=Boolean.valueOf(false);        //Increasing redundancy
    /**
     * Adding unzoom methods vars here
     * change later to some better code
     */
    boolean imageshown=false;
    boolean selected=false;

    public AdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        FacebookSdk.sdkInitialize(getContext());
        view = inflater.inflate(R.layout.fragment_ad, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        btnPhoto = (ImageButton) view.findViewById(R.id.btn_capture);
        images = new ArrayList<Bitmap>();

        imageFragmentPagerAdapter = new ImageFragmentPagerAdapter(getFragmentManager(), images, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hidePager();
            }
        });
        viewPager.setAdapter(imageFragmentPagerAdapter);
        Log.v("Offscreen",Integer.toString(viewPager.getOffscreenPageLimit()));
        recyclerView=(RecyclerView)view.findViewById(R.id.rr);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        horizontalAdapter=new HorizontalAdapter(getContext(),images);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(horizontalAdapter);
        r1= (CheckBox) view.findViewById(R.id.days);
        r2= (CheckBox) view.findViewById(R.id.weeks);
        r3= (CheckBox) view.findViewById(R.id.month);
        rl=(RelativeLayout)view.findViewById(R.id.rel1);
        setasthumb=(Button)view.findViewById(R.id.thumb_button_1);

        r1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                inputLayoutPrent.setVisibility(View.VISIBLE);
                inputLayoutPdeposit.setVisibility(View.VISIBLE);
                refreshrent();
            }
        });
        r2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                inputLayoutPrent.setVisibility(View.VISIBLE);
                inputLayoutPdeposit.setVisibility(View.VISIBLE);
                refreshrent();
            }
        });
        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                inputLayoutPrent.setVisibility(View.VISIBLE);
                inputLayoutPdeposit.setVisibility(View.VISIBLE);
                refreshrent();
            }
        });
        btnGal=(ImageButton)view.findViewById(R.id.btn_select);
        btnGal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(i,"Select Picture"), 1);*/
                if(images.size()==5) {
                    Toast.makeText(getContext(), "You cant give more images!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ImagePicker.create(fragment).folderMode(true).folderTitle("All pictures").multi().limit(5-images.size()).start(1);

            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addImageView(pics);
               /* Intent data = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(data, CAMERA_PIC_REQUEST);*/
                if(!isCameraStorageAllowed())
                {
                    if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.CAMERA))
                    {
                      //  Toast.makeText(getContext(), "Give Camera Permission", Toast.LENGTH_SHORT).show();
                    }
                    ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE},1234);
                }
                if(isCameraStorageAllowed()) {
                    if (images.size() == 5) {
                        Toast.makeText(getContext(), "You cant give more images!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent data = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri();
                    data.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(data, CAMERA_PIC_REQUEST);
                }
            }
        });

        fragment=this;
        location=(Button)view.findViewById(R.id.btn_location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data= new Intent(getActivity(),SearchActivity.class);
                startActivityForResult(data,CITY_SEARCH_REQUEST);
            }
        });
        city=(TextView)view.findViewById(R.id.tv_city);
        //In onActivityResult method


        spinner = (Spinner) (view).findViewById(R.id.sp_types);
        spinner2 = (Spinner) (view).findViewById(R.id.sp_subtypes);
        spinner_rent = (Spinner) (view).findViewById(R.id.sp_rent_types);
        spinner_subrent = (Spinner) (view).findViewById(R.id.sp_rent_subtypes);
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

        final List<String> rent_types = new ArrayList<>();
        rent_types.add("Select Rent Period");
        rent_types.add("Days");
        rent_types.add("Weeks");
        rent_types.add("Months");

        final List<String> rent_subtypes= new ArrayList<>();
        rent_subtypes.add("Select Time for Rent Period");
        rent_subtypes.add(" ");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, rent_types);
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, rent_subtypes);
        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories2);

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
                    show_day();
                    //rent_subtypes.remove("Select a sub-category");
                } else if (item == "Weeks") {
                    rent_subtypes.clear();
                    rent_subtypes.add("1");
                    rent_subtypes.add("2");
                    rent_subtypes.add("3");
                    rent_types.remove("Select Rent Period");
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

        //Log.v("MAXRENT",f1);
        inputLayoutPname = (TextInputLayout) view.findViewById(R.id.input_layout_pname);
        inputLayoutPdesc = (TextInputLayout) view.findViewById(R.id.input_layout_pdesc);
        inputLayoutPage = (TextInputLayout) view.findViewById(R.id.input_layout_page);
        inputLayoutPrent = (TextInputLayout) view.findViewById(R.id.input_layout_prent);
        inputLayoutPtags = (TextInputLayout) view.findViewById(R.id.input_layout_ptags);
        inputLayoutPdeposit = (TextInputLayout) view.findViewById(R.id.input_layout_pdeposit);
        inputPname = (EditText) view.findViewById(R.id.input_pname);
        inputPdesc = (EditText) view.findViewById(R.id.input_pdesc);
        inputPage = (EditText) view.findViewById(R.id.input_page);
        inputPrent = (EditText) view.findViewById(R.id.input_prent);
        inputPdeposit = (EditText) view.findViewById(R.id.input_pdeposit);
        inputPtags = (EditText) view.findViewById(R.id.input_ptags);
        btnSignUp = (Button) view.findViewById(R.id.btn_signup);

        inputPname.addTextChangedListener(new MyTextWatcher(inputPname));
        inputPdesc.addTextChangedListener(new MyTextWatcher(inputPdesc));
        inputPage.addTextChangedListener(new MyTextWatcher(inputPage));
        inputPage.addTextChangedListener(new MyTextWatcher(inputPage));
        inputPage.addTextChangedListener(new MyTextWatcher(inputPage));

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
                if(inputPtags.getText().equals(""))
                {
                    inputPtags.setError("Please Enter some tags!");
                    inputLayoutPtags.setError("Enter space separated values!");
                    return;
                }
                /*String[] temp=inputPtags.getText().toString().split(" ");
                StringBuffer sbuff=new StringBuffer("");
                for(String x:temp)
                    sbuff.append(x+",");
                String tags= URLEncoder.encode(sbuff.toString());
                */
                String tags=URLEncoder.encode(inputPtags.getText().toString().replace(","," "));
                if(r1.isChecked())
                    f2=f2+"days,";
                if(r2.isChecked())
                    f2=f2+"Weeks,";
                if(r3.isChecked())
                    f2=f2+"Months";
                if(images.size()>=2&&images.size()<=5) {
                    String spinner1item=(String) spinner.getSelectedItem();
                    String spinner2item=(String) spinner2.getSelectedItem();
                    if(!(spinner1item.equals("Select a Category")||(spinner2item.equals("Select a Sub-Category")))) {
                        new Newaddupload(tags, (String) spinner2.getSelectedItem(), getActivity(), AccessToken.getCurrentAccessToken().getUserId(), inputPname.getText().toString(), inputPdesc.getText().toString(), inputPage.getText().toString(), spinner.getSelectedItem().toString(), inputPrent.getText().toString(), inputPdeposit.getText().toString(), images, fragment.getContext(), f1, f2, city.getText().toString()).execute();
                    }
                    else
                    {
                        TextView errorText = (TextView)spinner.getSelectedView();
                        errorText.setError("Please select a category!");
                        errorText.setTextColor(Color.RED);//just to highlight that this is an error
                        errorText.requestFocus();
                        Toast.makeText(getContext(),"Please select a proper category!!",Toast.LENGTH_SHORT);

                    }

                }
                else
                    Toast.makeText(getContext(), "Please select proper number of Images!!", Toast.LENGTH_SHORT).show();
            }
        });


        setasthumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageshown=false;
                Collections.swap(images,0,viewPager.getCurrentItem());
                horizontalAdapter.setPosition(0);
                Toast.makeText(getContext(), "Changed Thumbnail", Toast.LENGTH_SHORT).show();
                hidePager();
                horizontalAdapter.notifyDataSetChanged();
                reInstantiatePager();
            }
        });

        return view;
    }

    public void refreshSpiner()
    {
        selected=false;
        dataAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,categories);
        dataAdapter4=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,categories2);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
        spinner2.setAdapter(dataAdapter4);
    }

    void showall()
    {
        view.findViewById(R.id.weeks).setVisibility(View.VISIBLE);
        view.findViewById(R.id.month).setVisibility(View.VISIBLE);
    }
    void show_week()
    {
        view.findViewById(R.id.month).setVisibility(View.GONE);
        r3.setChecked(false);
        view.findViewById(R.id.weeks).setVisibility(View.VISIBLE);
    }
    void show_day()
    {
        view.findViewById(R.id.weeks).setVisibility(View.GONE);
        view.findViewById(R.id.month).setVisibility(View.GONE);
        r2.setChecked(false);
        r3.setChecked(false);
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
        viewPager.setAdapter(new ImageFragmentPagerAdapter(getFragmentManager(), images, new View.OnClickListener() {
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
        if (!validatePrent()) {
            return;
        }
        if (!validatePdeposit()) {
            return;
        }

        //Toast.makeText(getContext(), "Submitted", Toast.LENGTH_SHORT).show();
    }





    private boolean validatePname() {
        if (inputPname.getText().toString().trim().isEmpty()) {
            inputLayoutPname.setError(getString(R.string.err_msg_name));
            requestFocus(inputPname);
            return false;
        } else {
            inputLayoutPname.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePdesc() {
        if (inputPdesc.getText().toString().trim().isEmpty()) {
            inputLayoutPdesc.setError(getString(R.string.err_msg_desc));
            requestFocus(inputPdesc);
            return false;
        } else {
            inputLayoutPdesc.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePage() {
        if (inputPage.getText().toString().trim().isEmpty()) {
            inputLayoutPage.setError(getString(R.string.err_msg_age));
            requestFocus(inputPage);
            return false;
        } else {
            inputLayoutPage.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePrent() {
        if (inputPrent.getText().toString().trim().isEmpty()) {
            inputLayoutPrent.setError(getString(R.string.err_msg_rent));
            requestFocus(inputPrent);
            return false;
        } else {
            inputLayoutPrent.setErrorEnabled(false);
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
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }



    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_pname:
                    validatePname();
                    break;
                case R.id.input_pdesc:
                    validatePdesc();
                    break;
                case R.id.input_page:
                    validatePage();
                    break;
                case R.id.input_prent:
                    validatePrent();
                    break;
                case R.id.input_pdeposit:
                    validatePdeposit();
                    break;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //
        //super.onActivityResult(requestCode,resultCode,data);
        Log.v("Check", "Checking");

        if (resultCode != Activity.RESULT_OK) {
         Log.v("SOME EROR","LOL");
            return;
        }
            if (requestCode == CAMERA_PIC_REQUEST)
        {
            if (images.size() == 5)
                return;
            Bitmap image = null;
            getActivity().getContentResolver().notifyChange(fileUri, null);
            try {
                Log.v("MN","Pq");

                image = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fileUri);
                int nh = (int) ( image.getHeight() * (720.0 / image.getWidth()) );
                image=Bitmap.createScaledBitmap(image, 720, nh, true);
                images.add(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.v("Bitmap", image.toString());

            horizontalAdapter.notifyDataSetChanged();
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
            horizontalAdapter.notifyDataSetChanged();
            reInstantiatePager();        }
           else if (requestCode==CITY_SEARCH_REQUEST) {
                Log.v("Trying","trying");
                Log.v("data",data.getStringExtra("data"));
                String value = data.getStringExtra("data");
                city.setText(value);
            }
        }


    class HorizontalAdapter  extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {
        private Context mContext;
        private ArrayList<Bitmap> images;
        private int thumbnail=0;
        public HorizontalAdapter(Context mContext, final ArrayList<Bitmap> images) {
            this.mContext = mContext;
            this.images=images;
            Log.v("Adapter created","Created");

        }
        void setPosition(int i)
        {
            thumbnail=i;
        }

        @Override
        public HorizontalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_pic, parent, false);
            Log.v("oncreateViewholder","currect");
            return new MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder,final int position) {
            holder.i.setImageBitmap(images.get(position));
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
                    images.remove(position);
                    if(currentpos==position||thumbnail==position) {
                        currentpos = 0;
                        thumbnail = 0;
                    }
                    notifyDataSetChanged();
                    reInstantiatePager();                }
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
            return images.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            Button set;
            ImageView i;
            ImageButton del;
            RelativeLayout relativeLayout;

            public MyViewHolder(View view) {
                super(view);
                relativeLayout=(RelativeLayout)view.findViewById(R.id.rel_lay);
                del=(ImageButton)view.findViewById(R.id.yes_bt);
                i=(ImageView)view.findViewById(R.id.act_image);
            }
        }
    }
    public boolean isCameraStorageAllowed()
    {
        int res= ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA);
        int res1=ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (res== PackageManager.PERMISSION_GRANTED)
            return true;
        else
            return false;
    }

    public void refreshrent(){
        if(r1.isChecked()){
            inputPrent.setHint("Product Rent per Day");
            return;
        }
        if(r2.isChecked()){
            inputPrent.setHint("Product Rent per Week");
            return;
        }
        if(r3.isChecked()){
            inputPrent.setHint("Product Rent per Month");
            return;
        }
        if(!r1.isChecked()&&!r2.isChecked()&&!r3.isChecked()){
            inputLayoutPdeposit.setVisibility(View.GONE);
            inputLayoutPrent.setVisibility(View.GONE);
        }

    }
}