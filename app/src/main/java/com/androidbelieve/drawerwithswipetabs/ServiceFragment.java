package com.androidbelieve.drawerwithswipetabs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.facebook.AccessToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static android.graphics.BitmapFactory.decodeFile;

public class ServiceFragment extends Fragment {
    private View view;
    private ArrayList<String>links;
    private boolean imageshown=false;
    private ImageFragmentPagerAdapter imageFragmentPagerAdapter;
    private ViewPager viewPager;
    private Uri fileUri;
    private TextView tel;
    private Spinner spinner;
    private Button location;
    private static final int CITY_SEARCH_REQUEST=123;
    private Spinner spinner2;
    private int CAMERA_PIC_REQUEST = 10;
    private RecyclerView recyclerView;
    private HorizontalAdapter horizontalAdapter;
    private Fragment fragment;
    private int num;
    private ArrayList<Bitmap>images;
    protected ListView lvlinks;
    private ImageButton btnGal,btnPhoto;
    private String item;
    LinksAdapter linksAdapter;
    private EditText inputPname,inputPdesc,inputPrent;
    private TextView city;
    private Button btnSignUp;
    private String subcat;
    private Button setasthumb;
    public ServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_service, container, false);
        city=(TextView)view.findViewById(R.id.tv_city);
        inputPname = (EditText) view.findViewById(R.id.input_pname);
        inputPdesc = (EditText) view.findViewById(R.id.input_pdesc);
        inputPrent = (EditText) view.findViewById(R.id.input_prent);
        btnSignUp=(Button)view.findViewById(R.id.submit);
        setasthumb=(Button)view.findViewById(R.id.thumb_button_1);
        spinner = (Spinner) (view).findViewById(R.id.sp_types);
        final List<String> categories = new ArrayList<String>();
        spinner2 = (Spinner) (view).findViewById(R.id.sp_subtypes);
        final List<String> categories2 = new ArrayList<String>();
        images=new ArrayList<Bitmap>();
        categories.add("Select a Category");
        categories.add("Graphics and Design");
        categories.add("Digital Marketing");
        categories.add("Writing and Translation");
        categories.add("Video and Animation");
        categories.add("Music and Audio");
        categories.add("Programming and Tech");
        categories.add("Advertising");
        categories.add("Business");
        categories.add("Lifestyle");
        categories.add("Gifts");
        categories2.add("Select a Sub-Category");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories2);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner2.setAdapter(dataAdapter2);

        tel=(TextView)view.findViewById(R.id.tv_lll);
        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getContext(),AdLinksActivity.class),22);
            }
        });
        viewPager=(ViewPager)view.findViewById(R.id.pager);
        imageFragmentPagerAdapter = new ImageFragmentPagerAdapter(getFragmentManager(), images, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hidePager();
            }
        });
        viewPager.setAdapter(imageFragmentPagerAdapter);
        recyclerView=(RecyclerView)view.findViewById(R.id.rr);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        horizontalAdapter=new HorizontalAdapter(getContext(),images);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(horizontalAdapter);
        btnGal=(ImageButton)view.findViewById(R.id.btn_select);
        lvlinks=(ListView)view.findViewById(R.id.lv_links);
        setListViewHeightBasedOnChildren(lvlinks);
        linksAdapter=new LinksAdapter();
        btnGal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(images.size()==5) {
                    Toast.makeText(getContext(), "You cant give more images!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ImagePicker.create(fragment).folderMode(true).folderTitle("All pictures").multi().limit(5-images.size()).start(1);


            }
        });
        btnPhoto=(ImageButton)view.findViewById(R.id.btn_capture);
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addImageView(pics);
               /* Intent data = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(data, CAMERA_PIC_REQUEST);*/
                if(images.size()==5) {
                    Toast.makeText(getContext(), "You cant give more images!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent data = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = getOutputMediaFileUri();
                data.putExtra( MediaStore.EXTRA_OUTPUT, fileUri );

                startActivityForResult(data, CAMERA_PIC_REQUEST);

            }
        });

        fragment=this;


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               item = parent.getItemAtPosition(position).toString();
                if (item == "Graphics and Design") {
                    Toast.makeText(getContext(), "Graphics and Design", Toast.LENGTH_SHORT).show();
                    categories2.clear();
                    categories2.add("Logo Design");
                    categories2.add("Business Card & Strategies");
                    categories2.add("Illustration");
                    categories2.add("Cartoons & Caricatures");
                    categories2.add("Flyers & Posters");
                    categories2.add("Book Cover & Packaging");
                    categories2.add("Web & Mobile Design");
                    categories2.add("Social Media Design");
                    categories2.add("Banner Ads");
                    categories2.add("Photoshop Editing");
                    categories2.add("2D & 3D Models");
                    categories2.add("T-Shirts");
                    categories2.add("Presentation Design");
                    categories2.add("Infographics");
                    categories2.add("Vector Tracing");
                    categories2.add("Invitation");
                    categories2.add("Others");
                    categories.remove("Select a Category");
                    categories2.remove("Select a Sub-Category");
                }
                else if (item == "Digital Marketing") {
                    Toast.makeText(getContext(), "Digital Marketing", Toast.LENGTH_SHORT).show();
                    categories2.clear();
                    //spinner2.
                    categories2.add("Social Media Marketing");
                    categories2.add("SEO");
                    categories2.add("Web Traffic");
                    categories2.add("Content Marketing");
                    categories2.add("Video Advertising");
                    categories2.add("Email Marketing");
                    categories2.add("SEM");
                    categories2.add("Marketing Strategy");
                    categories2.add("Web Analytics");
                    categories2.add("Influencer Marketing");
                    categories2.add("Local Listing");
                    categories2.add("Domain Research");
                    categories2.add("Mobile Advertising");
                    categories2.add("Others");
                    categories.remove("Select a Category");
                    categories2.remove("Select a Sub-Category");

                }
                else if (item == "Writing and Translation") {
                    Toast.makeText(getContext(), "Writing and Translation", Toast.LENGTH_SHORT).show();
                    categories2.clear();
                    categories2.add("Resumes & Cover Letters");
                    categories2.add("ProofReading & Editing");
                    categories2.add("Translation");
                    categories2.add("Creative Writing");
                    categories2.add("Business Copywriting");
                    categories2.add("Research & Summaries");
                    categories2.add("Articles & Blog Posts");
                    categories2.add("Press Release");
                    categories2.add("Transcription");
                    categories2.add("Legal Writing");
                    categories2.add("Others");
                    categories.remove("Select a Category");
                    categories2.remove("Select a Sub-Category");
                }
                else if (item == "Video and Animation") {
                    Toast.makeText(getContext(), "Video and Animation", Toast.LENGTH_SHORT).show();
                    categories2.clear();
                    categories2.add("Whiteboard & Explainer Videos");
                    categories2.add("Intros & Animated Logos");
                    categories2.add("Promotional & Brand Videos");
                    categories2.add("Editing & Post Production");
                    categories2.add("Lyrics & Music Videos");
                    categories2.add("Spokesperson & Testimonial");
                    categories2.add("Animated Character & Modeling");
                    categories2.add("Video Greetings");
                    categories2.add("Others");
                    categories.remove("Select a Category");
                    categories2.remove("Select a Sub-Category");
                }
                if (item == "Music and Audio") {
                    Toast.makeText(getContext(), "Music and Audio", Toast.LENGTH_SHORT).show();
                    categories2.clear();
                    categories2.add("Voice Over");
                    categories2.add("Mixing & Mastering");
                    categories2.add("Producers & Consumers");
                    categories2.add("Singer-Songwriters");
                    categories2.add("Session Musicians & Singers");
                    categories2.add("Jingles & Drops");
                    categories2.add("Sound Effects");
                    categories2.add("Others");
                    categories.remove("Select a Category");
                    categories2.remove("Select a Sub-Category");
                }
                else if (item == "Programming and Tech") {
                    Toast.makeText(getContext(), "Programming and Tech", Toast.LENGTH_SHORT).show();
                    categories2.clear();
                    categories2.add("Wordpress");
                    categories2.add("Website Builder & CMS");
                    categories2.add("Website Programming");
                    categories2.add("E-Commerce");
                    categories2.add("Mobile Apps & Web");
                    categories2.add("Desktop Application");
                    categories2.add("Support & IT");
                    categories2.add("Data Analyst & Reports");
                    categories2.add("Convert Files");
                    categories2.add("Databases");
                    categories2.add("User Testing");
                    categories2.add("QA");
                    categories2.add("Others");
                    categories.remove("Select a Category");
                    categories2.remove("Select a Sub-Category");
                }
                else if (item == "Advertising") {
                    Toast.makeText(getContext(), "Advertising", Toast.LENGTH_SHORT).show();
                    categories2.clear();
                    categories2.add("Music Promotion");
                    categories2.add("Radio");
                    categories2.add("Banner Advertising");
                    categories2.add("Outdoor Advertising");
                    categories2.add("Flyer & Handouts");
                    categories2.add("Hold your sign");
                    categories2.add("Human Billboards");
                    categories2.add("Pet Models");
                    categories2.add("Others");
                    categories.remove("Select a Category");
                    categories2.remove("Select a Sub-Category");
                }
                else if (item == "Business") {
                    Toast.makeText(getContext(), "Business", Toast.LENGTH_SHORT).show();
                    categories2.clear();
                    categories2.add("Virtual Assistant");
                    categories2.add("Market Research");
                    categories2.add("Business Plans");
                    categories2.add("Branding Services");
                    categories2.add("Legal Consulting");
                    categories2.add("Financial Consulting");
                    categories2.add("Business Tips");
                    categories2.add("Presentations");
                    categories2.add("Career Advice");
                    categories2.add("Others");
                    categories.remove("Select a Category");
                    categories2.remove("Select a Sub-Category");
                }
                else if (item == "Lifestyle") {
                    Toast.makeText(getContext(), "Lifestyle", Toast.LENGTH_SHORT).show();
                    categories2.clear();
                    categories2.add("Animal Care & Pets");
                    categories2.add("Relationship Advice");
                    categories2.add("Diet & Weight Loss");
                    categories2.add("Health & Fitness");
                    categories2.add("Weddiing Planning");
                    categories2.add("Makeup,Styling & Beauty");
                    categories2.add("Online Private Lessons");
                    categories2.add("Astrology & Fortune Telling");
                    categories2.add("Spiritual & Healing");
                    categories2.add("Cooking Recipes");
                    categories2.add("Parenting Tips");
                    categories2.add("Travel");
                    categories2.add("Others");
                    categories.remove("Select a Category");
                    categories2.remove("Select a Sub-Category");
                }
                else if (item == "Gifts") {
                    Toast.makeText(getContext(), "Gifts", Toast.LENGTH_SHORT).show();

                    categories2.clear();
                    categories2.add("Greeting Cards");
                    categories2.add("Unusual Cards");
                    categories2.add("Arts and Crafts");
                    categories2.add("Handmade Jewellery");
                    categories2.add("Gifts for Geeks");
                    categories2.add("Postcards From");
                    categories2.add("Recycled Crafts");
                    categories2.add("Others");
                    categories.remove("Select a Category");
                    categories2.remove("Select a Sub-Category");
                }
                spinner2.setSelection(0,true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                subcat=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add validation here
                GenericAsyncTask g=new GenericAsyncTask(getContext(), "http://rng.000webhostapp.com/new service.php", "", new AsyncResponse() {
                    @Override
                    public void processFinish(Object output) {
                        if(output!=null) {
                            getActivity().finish();
                        Log.v("result",(String)output);
                        }
                    }
                });
                g.setPostParams("sname",inputPname.getText().toString(),"category",item,"subcat",subcat,"description",inputPdesc.getText().toString(),"startrange",inputPrent.getText().toString(),"city",city.getText().toString(),"pid", AccessToken.getCurrentAccessToken().getUserId(),"num",Integer.toString(images.size()),"numlinks",Integer.toString(links.size()));
                g.setImagePost(images,0);
                int i=0;
                for(String x:links)
                {
                    g.setExtraPost("link"+Integer.toString(i++),x);
                }

                g.execute();
            }
        });
        location=(Button)view.findViewById(R.id.btn_location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data= new Intent(getActivity(),SearchActivity.class);
                startActivityForResult(data,CITY_SEARCH_REQUEST);
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

;

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
        //
        //super.onActivityResult(requestCode,resultCode,data);
        Log.v("Check", "Checking");
        Log.v("Check", "Checking");

        if (data == null || resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == CAMERA_PIC_REQUEST) {
            if (images.size() == 5)
                return;
            Bitmap image = null;
            getActivity().getContentResolver().notifyChange(fileUri, null);
            try {
                image = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fileUri);
                int nh = (int) ( image.getHeight() * (1080.0 / image.getWidth()) );
                image=Bitmap.createScaledBitmap(image, 1080, nh, true);
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
                Bitmap b = decodeFile(picturePath);

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
        if(requestCode == 22)
        {
            links=data.getStringArrayListExtra("links");
            int l = links.size();
            lvlinks.setVisibility(View.VISIBLE);
            setListViewHeightBasedOnChildren(lvlinks);

            lvlinks.setOnTouchListener(new View.OnTouchListener() {
                // Setting on Touch Listener for handling the touch inside ScrollView
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // Disallow the touch request for parent scroll on touch of child view
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });
            lvlinks.setAdapter(linksAdapter);
            int h =  50*l;
            lvlinks.setMinimumHeight(h);
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
                    images.remove(position);
                    if(thumbnail==position) {
                        thumbnail = 0;
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

            holder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    images.remove(position);
                    if(thumbnail==position) {
                        thumbnail = 0;
                    }
                    notifyDataSetChanged();
                    reInstantiatePager();                }
            });
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
                del = (ImageButton) view.findViewById(R.id.yes_bt);
                i = (ImageView) view.findViewById(R.id.act_image);
                relativeLayout=(RelativeLayout)view.findViewById(R.id.rel_lay);
                //set=(Button)view.findViewById(R.id.button);
            }
        }
    }
    class LinksAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return links.size();
        }

        @Override
        public Object getItem(int i) {
            return links.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            LinksHolder holder = null;
            if (convertView == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                convertView = inflater.inflate(R.layout.card_link, null);//Null for whole xml document
                holder = new LinksHolder();
                holder.ltv = (TextView) convertView.findViewById(R.id.tv_link_display);
                holder.itv = (ImageView)convertView.findViewById(R.id.bt_link_remove);
                convertView.setTag(holder);

            } else {
                holder = (LinksHolder) convertView.getTag();
            }
            String cur_link=links.get(i).toString();
            Toast.makeText(getContext(), "1" + cur_link, Toast.LENGTH_SHORT).show();
            Log.v("Current",cur_link);
            holder.ltv.setText(cur_link);
            holder.ltv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String a="http://"+links.get(i).toString();
                    Intent intent=new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(a));
                    startActivity(intent);
                }
            });
            holder.itv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    links.remove(i);
                    lvlinks.setAdapter(linksAdapter);
                }
            });
            return convertView;
        }
    }
    class LinksHolder {
        TextView ltv;
        ImageView itv;
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, RecyclerView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
