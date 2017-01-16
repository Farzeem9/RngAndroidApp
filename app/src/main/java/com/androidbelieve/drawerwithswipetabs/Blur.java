package com.androidbelieve.drawerwithswipetabs;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

/**
 * Created by karth on 14-12-2016.
 */

public class Blur  {
    private static View rootView;
    private static final String TAG = Blur.class.getSimpleName();

    public static Blur.Composer with(Context context) {
        return new Blur.Composer(context);
    }

    public static void delete(ViewGroup target) {
        View view = target.findViewWithTag(TAG);
        if (view != null) {
            target.removeView(view);
        }
    }

    public static class Composer {

        private View blurredView;
        private Context context;
        private BlurC.BlurFactor factor;
        private boolean async;
        private boolean animate;
        private int duration = 500;
        private Blur.ImageComposer.ImageComposerListener listener;

        public Composer(Context context) {
            this.context = context;
            blurredView = new View(context);
            blurredView.setTag(TAG);
            factor = new BlurC.BlurFactor();
        }

        public Blur.Composer radius(int radius) {
            factor.radius = radius;
            return this;
        }

        public Blur.Composer sampling(int sampling) {
            factor.sampling = sampling;
            return this;
        }

        public Blur.Composer color(int color) {
            factor.color = color;
            return this;
        }

        public Blur.Composer async() {
            async = true;
            return this;
        }

        public Blur.Composer async(Blur.ImageComposer.ImageComposerListener listener) {
            async = true;
            this.listener = listener;
            return this;
        }

        public Blur.Composer animate() {
            animate = true;
            return this;
        }

        public Blur.Composer animate(int duration) {
            animate = true;
            this.duration = duration;
            return this;
        }

        public Blur.ImageComposer capture(View capture) {
            return new Blur.ImageComposer(context, capture, factor, async, listener);
        }

        public void onto(final ViewGroup target) {
            factor.width = target.getMeasuredWidth();
            factor.height = target.getMeasuredHeight();

            if (async) {
                BlurTask task = new BlurTask(target, factor, new BlurTask.Callback() {
                    @Override public void done(BitmapDrawable drawable) {
//                        addView(target, drawable);

                        ((ImageView)rootView.findViewById(R.id.blurimage)).setImageDrawable(drawable);
                        AlphaAnimation alpha = new AlphaAnimation(0f, 1f);
                        alpha.setDuration(500);
                        target.findViewById(R.id.blurimage).startAnimation(alpha);
                        //((ImageView)target.findViewById(R.id.blurimage)).setBackgroundColor(100);
                    }
                });
                task.execute();
            } else {
                Drawable drawable = new BitmapDrawable(context.getResources(), BlurC.of(target, factor));
                ((ImageView)rootView.findViewById(R.id.blurimage)).setImageDrawable(drawable);
                AlphaAnimation alpha = new AlphaAnimation(0f, 1f);
                alpha.setDuration(500);
                rootView.findViewById(R.id.blurimage).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.blurimage).startAnimation(alpha);
                //((ImageView)target.findViewById(R.id.blurimage)).setBackgroundColor(100);
                //addView(target, drawable);
            }
        }

        private void addView(ViewGroup target, Drawable drawable) {
            BlurC.Helper.setBackground(blurredView, drawable);
            target.addView(blurredView);

            if (animate) {
                BlurC.Helper.animate(blurredView, duration);
            }
        }
    }

    public static class ImageComposer {

        private Context context;
        private View capture;
        private BlurC.BlurFactor factor;
        private boolean async;
        private Blur.ImageComposer.ImageComposerListener listener;

        public ImageComposer(Context context, View capture, BlurC.BlurFactor factor, boolean async,
                             Blur.ImageComposer.ImageComposerListener listener) {
            this.context = context;
            this.capture = capture;
            this.factor = factor;
            this.async = async;
            this.listener = listener;
        }

        public void into(final ImageView target) {
            factor.width = capture.getMeasuredWidth();
            factor.height = capture.getMeasuredHeight();

            if (async) {
                BlurTask task = new BlurTask(capture, factor, new BlurTask.Callback() {
                    @Override public void done(BitmapDrawable drawable) {
                        if (listener == null) {
                            target.setImageDrawable(drawable);
                        } else {
                            listener.onImageReady(drawable);
                        }
                    }
                });
                task.execute();
            } else {
                Drawable drawable = new BitmapDrawable(context.getResources(), BlurC.of(capture, factor));
                target.setImageDrawable(drawable);
            }
        }

        public interface ImageComposerListener {
            void onImageReady(BitmapDrawable drawable);
        }
    }


    public static void blur(View rooot, Context c,boolean x) {

            rootView = rooot;
            Log.v("bulrring", "okay");
            Blur.with(c)
                    .radius(15)
                    .sampling(5)
                    .animate(500)
                    .onto((ViewGroup) rooot);

    }



    public static void unBlur(View vi)
    {
        View view=((ViewGroup)rootView).findViewWithTag(Blur.TAG);
        if(view==null)
            Log.v("view null","okay");
        else
        {
            Log.v("view not null","okay");
        }
        Log.v("antibulrring","okay");

        final View v=rootView.findViewById(R.id.blurimage);
        v.setVisibility(View.GONE);
        v.animate().alpha(0f).setDuration(500).setInterpolator(new LinearInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();


    }
    private static Bitmap getScreenshot(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }

}
