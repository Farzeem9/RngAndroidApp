package com.androidbelieve.drawerwithswipetabs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class EditAdActivity extends AppCompatActivity {
    private EditText name,desc,age,rent,deposit,duration;
    private Toolbar toolbar=null;
    private TextView city;
    private Spinner spinner_rent,spinner_subrent,spinner;
    private String aid;
    private CheckBox r1,r2,r3;
    private RecyclerView rr;
    private HorizontalAdapter HorizontalAdapter;
    private Uri fileUri;
    private Button setasthumb,location;
    private ImageFragmentPagerAdapter imageFragmentPagerAdapter;
    private TextInputLayout inputLayoutPname, inputLayoutPdesc, inputLayoutPage, inputLayoutPdeposit, inputLayoutPrent;
    private RelativeLayout rl;
    private ViewPager viewPager;
    private String item,number,f1="",f2="";
    private ImageButton btnPhoto,btnGal;
    private ArrayList<Bitmap> images;
    private boolean imageshown=false;
    private final int CITY_SEARCH_REQUEST = 123;
    int work=0;
    int currentpos=0;
    View view;

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
        inputLayoutPrent = (TextInputLayout) findViewById(R.id.input_layout_prent);
        name = (EditText)findViewById(R.id.input_pname);
        desc = (EditText)findViewById(R.id.input_pdesc);
        age = (EditText)findViewById(R.id.input_page);
        rent = (EditText)findViewById(R.id.input_prent);
        deposit = (EditText)findViewById(R.id.input_pdeposit);
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
        List<String> categories = new ArrayList<String>();
        categories.add("Mobiles");
        categories.add("Cars");
        categories.add("Books");
        categories.add("Pots");
        categories.add("Bikes");
        categories.add("Select a Category");

        final List<String> rent_types = new ArrayList<String>();
        rent_types.add("Select a Category");
        rent_types.add("Days");
        rent_types.add("Weeks");
        rent_types.add("Months");

        final List<String> rent_subtypes= new ArrayList<String>();
        rent_subtypes.add("Select a sub-category");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rent_types);
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rent_subtypes);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner_rent.setAdapter(dataAdapter2);
        spinner_subrent.setAdapter(dataAdapter3);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                  String item = parent.getItemAtPosition(position).toString();

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
                    rent_types.remove("Select a Category");
                    rent_subtypes.remove("Select a sub-category");
                } else if (item == "Weeks") {
                    rent_subtypes.clear();
                    rent_subtypes.add("1");
                    rent_subtypes.add("2");
                    rent_subtypes.add("3");
                    rent_types.remove("Select a Category");
                    rent_subtypes.remove("Select a sub-category");
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
                    rent_types.remove("Select a Category");
                    rent_subtypes.remove("Select a sub-category");
                }
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
        if(city.toString().trim().isEmpty())
        {
            return;
        }
        Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show();
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

    private boolean validatePrent() {
        if (rent.getText().toString().trim().isEmpty()) {
            inputLayoutPrent.setError(getString(R.string.err_msg_rent));
            requestFocus(rent);
            return false;
        } else {
            inputLayoutPrent.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePdeposit() {
        if (deposit.getText().toString().trim().isEmpty()) {
            inputLayoutPdeposit.setError(getString(R.string.err_msg_deposit));
            requestFocus(deposit);
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
                int nh = (int) ( image.getHeight() * (1080.0 / image.getWidth()) );
                image=Bitmap.createScaledBitmap(image, 1080, nh, true);
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
        g.setPostParams("aid",aid,"work",Integer.toString(work),"maxrent",f1,"crent",f2,"city",city.getText().toString(),"prod_name",name.getText().toString(),"description",desc.getText().toString(),"prod_age",age.getText().toString(),"category","Mobiles","rent",rent.getText().toString(),"prod_deposit",deposit.getText().toString());
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
        g.setPostParams("aid",aid,"work",Integer.toString(work),"num",Integer.toString(HorizontalAdapter.getPostion()+1),"prod_name",name.getText().toString(),"description",desc.getText().toString(),"prod_age",age.getText().toString(),"category","Mobiles","rent",rent.getText().toString(),"prod_deposit",deposit.getText().toString());

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
                finish();
            }
        });
        g.setPostParams("aid",aid,"work",Integer.toString(work),"num",Integer.toString(images.size()),"prod_name",name.getText().toString(),"description",desc.getText().toString(),"prod_age",age.getText().toString(),"category","Mobiles","rent",rent.getText().toString(),"prod_deposit",deposit.getText().toString());
        int i=1;
        g.setImagePost(images,1);
        //g.execute();
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

//            String maxrent=c.getString("maxrent");
//            String crent=c.getString("crent");
//            String city=c.getString("CITY");
            JSONArray links=c.getJSONArray("LINKS");

            ArrayList<String> alllinks=new ArrayList<>();
            for(int i=0;i<links.length();i++)
                alllinks.add(links.getJSONObject(i).getString("link"));
            final int[] length = {links.length()};
            name.setText(prod_name);
            desc.setText(desc_str);
            rent.setText(rent_name);
            age.setText(prod_age);
            deposit.setText(sdeposit);
//            this.duration.setText(duration);
            if(alllinks.size()==0)
                progress.dismiss();
            for(String x:alllinks) {

                Picasso.with(this).load(x).into(new Target() {
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
                });
                Log.v("link in picasso",x);
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    class HorizontalAdapter  extends RecyclerView.Adapter<EditAdActivity.HorizontalAdapter.MyViewHolder> {
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
            return images.size();
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
    }
}
