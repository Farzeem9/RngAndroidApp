package com.androidbelieve.drawerwithswipetabs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class EditAdActivity extends AppCompatActivity {
    private EditText name,desc,age,rent,deposit,duration;
    private TextView city;
    private Spinner spinner_rent,spinner_subrent;
    private String aid;
    private RecyclerView rr;
    private HorizontalAdapter HorizontalAdapter;
    private Animator mCurrentAnimator;
    private Button setasthumb;
    private int mShortAnimationDuration;
    private RelativeLayout rl;
    private View thumbView;
    ArrayList<Bitmap> images;
    int work=0;
    int currentpos=0;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ad);
        images=new ArrayList<>();
        aid=getIntent().getStringExtra("AID");
        name = (EditText)findViewById(R.id.input_pname);
        desc = (EditText)findViewById(R.id.input_pdesc);
        age = (EditText)findViewById(R.id.input_page);
        rent = (EditText)findViewById(R.id.input_prent);
        deposit = (EditText)findViewById(R.id.input_pdeposit);
        city=(TextView)findViewById(R.id.tv_city);
        setasthumb=(Button)findViewById(R.id.thumb_button_1);
        rl=(RelativeLayout)findViewById(R.id.Relativel);
        rr=(RecyclerView)findViewById(R.id.rr);
        thumbView=findViewById(R.id.scrolv);

        setasthumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Exchanged values!","Great");
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
            }
        });

        HorizontalAdapter=new HorizontalAdapter(getApplicationContext(),images);
        rr.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        rr.setAdapter(HorizontalAdapter);
        rr.setVisibility(RecyclerView.VISIBLE);
//        images.add(BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.broly));
        HorizontalAdapter.notifyDataSetChanged();


        progress = new ProgressDialog(this);
        progress.setMessage("Communicating with server..");
        progress.setIndeterminate(true);
        progress.setProgress(0);
        // progress.setCancelable(false);
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
        GenericAsyncTask genericAsyncTask=new GenericAsyncTask(getApplicationContext(), "http://rng.000webhostapp.com/getadforedit.php?aid=" + aid, "Fetched Ad data", new AsyncResponse() {
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
        GenericAsyncTask g=new GenericAsyncTask(this, "http://rng.000webhostapp.com/editad.php", "Uploaded Edited Ad!", new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                finish();
            }
        });
        //g.setPostParams("aid="+aid,"work="+Integer.toString(work),"prod_name="+name.getText(),"description="+desc.getText(),"prod_age="+age.getText(),"category="+"Mobiles","rent="+rent.getText(),"prod_deposit="+deposit.getText());
        g.setPostParams("aid",aid,"work",Integer.toString(work),"prod_name",name.getText().toString(),"description",desc.getText().toString(),"prod_age",age.getText().toString(),"category","Mobiles","rent",rent.getText().toString(),"prod_deposit",deposit.getText().toString());
        g.execute();
    }
    void changethumbnail()
    {
        GenericAsyncTask g=new GenericAsyncTask(this, "http://rng.000webhostapp.com/editad.php", "Uploaded Edited Ad!", new AsyncResponse() {
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
        GenericAsyncTask g=new GenericAsyncTask(this, "http://rng.000webhostapp.com/editad.php", "Uploaded Edited Ad!", new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                finish();
            }
        });
        g.setPostParams("aid",aid,"work",Integer.toString(work),"num",Integer.toString(images.size()),"prod_name",name.getText().toString(),"description",desc.getText().toString(),"prod_age",age.getText().toString(),"category","Mobiles","rent",rent.getText().toString(),"prod_deposit",deposit.getText().toString());
        int i=1;
        for(Bitmap b:images)
        {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            String encodedString = Base64.encodeToString(stream.toByteArray(), 0);
            Log.v("EncodedString", encodedString);
            Log.v("image", "image" + Integer.toString(i));
            g.setExtraPost("image"+Integer.toString(i++),encodedString);
        }
        g.execute();
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

            for(String x:alllinks) {

                Picasso.with(this).load(x).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        length[0]--;
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
    private void zoomImageFromThumb(final View thumbView, Bitmap imageResId,int pos) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        currentpos=pos;
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) findViewById(
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
        findViewById(R.id.container)
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
        rl.setVisibility(View.VISIBLE);
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
        set.play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(500);
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
                final AnimatorSet set = new AnimatorSet();
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
                        setasthumb.setVisibility(View.GONE);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
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
                    zoomImageFromThumb(thumbView,images.get(position),position);
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
