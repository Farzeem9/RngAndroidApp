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
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends Fragment {
    View view;
    private Spinner spinner;
    private Spinner spinner2;
    private Animator mCurrentAnimator;
    private int CAMERA_PIC_REQUEST = 10;
    private int mShortAnimationDuration;
    private View thumbView;
    private ImageView neversee;
    private RecyclerView recyclerView;
    private HorizontalAdapter horizontalAdapter;
    private Fragment fragment;
    private int num;
    private ArrayList<Bitmap>images;
    private ImageButton btnGal,btnPhoto;
    // private TextInputLayout inputLayoutPname, inputLayoutPdesc, inputLayoutPage, inputLayoutPdeposit, inputLayoutPrent;
    private Button btnSignUp;
    public ServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_service_frgment, container, false);
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

        thumbView=(View)view.findViewById(R.id.thumb_button_1);
        recyclerView=(RecyclerView)view.findViewById(R.id.rr);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        horizontalAdapter=new HorizontalAdapter(getContext(),images);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(horizontalAdapter);
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
                //ImagePicker.create(fragment).folderMode(true).folderTitle("All pictures").multi().limit(5-num).start(1);

            }
        });
        btnPhoto=(ImageButton)view.findViewById(R.id.btn_capture);
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


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                        /*if (item == "Select Category"){
                            categories2.clear();
                            categories2.add("Select Sub-Category");
                        }*/
                //Toast.makeText(NewAdActivity.this, item, Toast.LENGTH_SHORT).show();
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

        return view;
    }
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
        thumbView.getGlobalVisibleRect(startBounds);
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
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

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
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //
        //super.onActivityResult(requestCode,resultCode,data);
        Log.v("Check", "Checking");
        if(!(num<5)&&(!(images.size()<5)))
            return;
        if(data==null||resultCode!= Activity.RESULT_OK)
            return;
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            Log.v("Bitmap", image.toString());
            image=Bitmap.createScaledBitmap(image,300,300,false);
            images.add(image);
            num=images.size();
            horizontalAdapter.notifyDataSetChanged();
        }
        /*if(requestCode== 1)
        {
            /*Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            int count=cursor.getCount();
            if(count+num>=5)
                count=5-num;
            for(int i=0;i<count;i++) {
                cursor.moveToPosition(i);
                String picturePath = cursor.getString(columnIndex);
                Bitmap b=BitmapFactory.decodeFile(picturePath);
                addImageView(pics,b);
                images.add(b);

            }
            num+=count;
            cursor.close();

            ArrayList<Image> imagesfrompicker = (ArrayList<Image>) ImagePicker.getImages(data);
            for(Image x:imagesfrompicker)
            {
                String picturePath = x.getPath();
                Bitmap b=BitmapFactory.decodeFile(picturePath);
                addImageView(pics,b);
                images.add(b);
                num++;
            }
        }*/

    }
    class HorizontalAdapter  extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {
        private Context mContext;
        private ArrayList<Bitmap> images;

        public HorizontalAdapter(Context mContext, final ArrayList<Bitmap> images) {
            this.mContext = mContext;
            this.images=images;
            Log.v("Adapter created","Created");

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
        }

        @Override
        public int getItemCount() {
            return images.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            Button set;
            ImageView i;
            ImageButton del;

            public MyViewHolder(View view) {
                super(view);
                del=(ImageButton)view.findViewById(R.id.yes_bt);
                i=(ImageView)view.findViewById(R.id.act_image);
                //set=(Button)view.findViewById(R.id.button);
            }
        }
    }
}
