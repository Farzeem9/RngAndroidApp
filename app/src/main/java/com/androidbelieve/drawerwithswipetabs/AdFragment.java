package com.androidbelieve.drawerwithswipetabs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import android.media.Image;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.*;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.esafirm.imagepicker.features.ImagePicker;
//import com.esafirm.imagepicker.model.Image;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.R.attr.data;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdFragment extends Fragment implements AdapterView.OnItemClickListener {
    private CheckBox r1,r2,r3;
    private Spinner spinner,spinner_rent,spinner_subrent;
    private EditText inputPname, inputPdesc, inputPage, inputPrent, inputPdeposit;
    private TextInputLayout inputLayoutPname, inputLayoutPdesc, inputLayoutPage, inputLayoutPdeposit, inputLayoutPrent;
    private TextView city;
    private Fragment fragment;

    private Button btnSignUp,location;
    static int num=0;
    private Animator mCurrentAnimator;
    private LinearLayout pics;
    private ImageButton btnPhoto,btnGal;
    private Button setasthumb;
    private RelativeLayout rl;
    private ScrollView scrollView;
    private int currentpos=0;
    //    private ImageView imageview;
    private ArrayList<Bitmap> images = new ArrayList<>();
    View view;
    private final int CAMERA_PIC_REQUEST = 10;
    private final int CITY_SEARCH_REQUEST = 123;
    private int mShortAnimationDuration;
    private View thumbView;
    private ImageView neversee;
    private RecyclerView recyclerView;
    private HorizontalAdapter horizontalAdapter;
    private String item,number,f1="",f2="";

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
        btnPhoto = (ImageButton) view.findViewById(R.id.btn_capture);
        images = new ArrayList<Bitmap>();
        //imageview = (ImageView)view.findViewById(R.id.iv1);
//        pics = (LinearLayout) view.findViewById(R.id.ll_pics);
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
        scrollView=(ScrollView)view.findViewById(R.id.sc_ad);
        setasthumb=(Button)view.findViewById(R.id.thumb_button_1);

        btnGal=(ImageButton)view.findViewById(R.id.btn_select);
        btnGal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(i,"Select Picture"), 1);*/
                if(num==5) {
                    Toast.makeText(getContext(), "You cant give more images!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ImagePicker.create(fragment).folderMode(true).folderTitle("All pictures").multi().limit(5-num).start(1);

            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addImageView(pics);
               /* Intent data = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(data, CAMERA_PIC_REQUEST);*/
                if(num==5) {
                    Toast.makeText(getContext(), "You cant give more images!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent data = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(data, CAMERA_PIC_REQUEST);

            }
        });

        fragment=this;
        num=0;

        location=(Button)view.findViewById(R.id.btn_location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data= new Intent(getActivity(),SearchActivity.class);
                startActivityForResult(data,123);
            }
        });
        city=(TextView)view.findViewById(R.id.tv_city);
        //In onActivityResult method


        spinner = (Spinner) (view).findViewById(R.id.sp_types);
        spinner_rent = (Spinner) (view).findViewById(R.id.sp_rent_types);
        spinner_subrent = (Spinner) (view).findViewById(R.id.sp_rent_subtypes);
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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, rent_types);
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, rent_subtypes);

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
                                                  //Toast.makeText(NewAdActivity.this, item, Toast.LENGTH_SHORT).show();
                                                  if (item == "Mobiles") {
                                                      Toast.makeText(getContext(), "Mobiles", Toast.LENGTH_SHORT).show();
                                                  }
                                                  if (item == "Cars") {
                                                      Toast.makeText(getContext(), "Cars", Toast.LENGTH_SHORT).show();
                                                  }
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

        //Log.v("MAXRENT",f1);
        inputLayoutPname = (TextInputLayout) view.findViewById(R.id.input_layout_pname);
        inputLayoutPdesc = (TextInputLayout) view.findViewById(R.id.input_layout_pdesc);
        inputLayoutPage = (TextInputLayout) view.findViewById(R.id.input_layout_page);
        inputLayoutPrent = (TextInputLayout) view.findViewById(R.id.input_layout_prent);
        inputLayoutPdeposit = (TextInputLayout) view.findViewById(R.id.input_layout_pdeposit);
        inputPname = (EditText) view.findViewById(R.id.input_pname);
        inputPdesc = (EditText) view.findViewById(R.id.input_pdesc);
        inputPage = (EditText) view.findViewById(R.id.input_page);
        inputPrent = (EditText) view.findViewById(R.id.input_prent);
        inputPdeposit = (EditText) view.findViewById(R.id.input_pdeposit);
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
                if(r1.isChecked())
                    f2=f2+"days,";
                if(r2.isChecked())
                    f2=f2+"Weeks,";
                if(r3.isChecked())
                    f2=f2+"Months";
                if(num>=2&&num<=5)
                    new Newaddupload(AccessToken.getCurrentAccessToken().getUserId(), inputPname.getText().toString(), inputPdesc.getText().toString(), inputPage.getText().toString(), spinner.getSelectedItem().toString(), inputPrent.getText().toString(), inputPdeposit.getText().toString(), images,fragment.getContext(),f1,f2,city.getText().toString()).execute();
                else
                    Toast.makeText(getContext(), "Please select proper number of Images!!", Toast.LENGTH_SHORT).show();
            }
        });


        setasthumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Exchanged values!","Great");

                    Collections.swap(images,0,currentpos);
                    horizontalAdapter.setPosition(0);
                Toast.makeText(getContext(), "Changed Thumbnail", Toast.LENGTH_SHORT).show();
                horizontalAdapter.notifyDataSetChanged();
            }
        });

        return view;
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

        Toast.makeText(getContext(), "Submitted", Toast.LENGTH_SHORT).show();
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

    private void addImageView(Bitmap bm) {
        //ImageView imageView = new ImageView(getContext());
        //imageView.requestLayout();
        //image_view.getLayoutParams().height = 20;
        //LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //imageView.setImageBitmap(Bitmap.createScaledBitmap(bm,220,220,false));
        //lp.setMargins(2,5,2,5);
        //imageView.setLayoutParams(lp);
        //imageView.setMaxHeight(120);
        //imageView.setMaxWidth(160);
        //imageView.setMinimumWidth(160);
        //imageView.setMinimumHeight(AppBarLayout.LayoutParams.MATCH_PARENT);
        //linearLayout.addView(imageView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //
        //super.onActivityResult(requestCode,resultCode,data);
        Log.v("Check", "Checking");
        if (!(num < 5) && (!(images.size() < 5)))
            return;
        if (data == null || resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            Log.v("Bitmap", image.toString());
            //image=Bitmap.createScaledBitmap(image,300,300,false);
            images.add(image);
            num = images.size();
            horizontalAdapter.notifyDataSetChanged();
        }
        if (requestCode == 1) {
            Log.v("Trying", "trying");
            ArrayList<Image> imagesfrompicker = (ArrayList<Image>) ImagePicker.getImages(data);
            for (Image x : imagesfrompicker) {
                String picturePath = x.getPath();
                Bitmap b = BitmapFactory.decodeFile(picturePath);

                images.add(b);
                num = images.size();
            }
            horizontalAdapter.notifyDataSetChanged();
        }
           else if (requestCode==CITY_SEARCH_REQUEST) {
                Log.v("Trying","trying");
                Log.v("data",data.getStringExtra("data"));
                String value = data.getStringExtra("data");
                city.setText(value);
            }
        }



    static class UploadAd extends AsyncTask<String, String, String> {
        ArrayList<Bitmap> images;
        private String aid;

        UploadAd(ArrayList<Bitmap> images, String aid) {
            this.images = images;
            this.aid = aid;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String link = "http://rng.000webhostapp.com/img%20upload.php?num=" + images.size() + "&aid=" + aid;
                Log.v("link", link);
                String data = "";
                for (int i = 0; i < images.size(); i++) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    images.get(i).compress(Bitmap.CompressFormat.PNG, 90, stream);
                    String encodedString = Base64.encodeToString(stream.toByteArray(), 0);
                    Log.v("EncodedString", encodedString);
                    Log.v("image", "image" + Integer.toString(i));
                    data += URLEncoder.encode("image" + Integer.toString(i + 1), "UTF-8") + "=" + URLEncoder.encode(encodedString, "UTF-8") + "&";
                }
                URL url = new URL(link);
                URLConnection con = url.openConnection();
                con.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);

                }
                Log.v("Result", sb.toString());
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }



//onclick me ye daal -->zoomImageFromThumb(thumb1View, R.drawable.image1);

    private void zoomImageFromThumb(final View thumbView, Bitmap imageResId) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) view.findViewById(
                R.id.expanded_image);
        expandedImageView.setImageBitmap(imageResId);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
//        thumbView.getGlobalVisibleRect(startBounds);
        view.findViewById(R.id.container)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust t;he start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        scrollView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);
        setasthumb.setVisibility(View.VISIBLE);
        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        scrollView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        setasthumb.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        scrollView.setAlpha(1f);
                        setasthumb.setVisibility(View.GONE);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
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
                    currentpos=position;
                    zoomImageFromThumb(thumbView,images.get(position));
                }
            });
            holder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    images.remove(position);
                    notifyDataSetChanged();
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
                //set=(Button)view.findViewById(R.id.button);
            }
        }
    }
}