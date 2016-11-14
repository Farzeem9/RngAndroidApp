package com.androidbelieve.drawerwithswipetabs;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFrgment extends Fragment {
            View view;
            private Spinner spinner;
            private Spinner spinner2;
            private EditText inputPname, inputPdesc, inputPage, inputPrent, inputPdeposit;
            private TextInputLayout inputLayoutPname, inputLayoutPdesc, inputLayoutPage, inputLayoutPdeposit, inputLayoutPrent;
            private Button btnSignUp;

            public ServiceFrgment() {
                // Required empty public constructor
            }


            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                    Bundle savedInstanceState) {
                // Inflate the layout for this fragment
                view = inflater.inflate(R.layout.fragment_service_frgment, container, false);
                spinner = (Spinner) (view).findViewById(R.id.sp_types);
                final List<String> categories = new ArrayList<String>();
                spinner2 = (Spinner) (view).findViewById(R.id.sp_subtypes);
                final List<String> categories2 = new ArrayList<String>();
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
               /* inputLayoutPname = (TextInputLayout) view.findViewById(R.id.input_layout_pname);
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

                inputPname.addTextChangedListener(new ServiceFrgment.MyTextWatcher(inputPname));
                inputPdesc.addTextChangedListener(new ServiceFrgment.MyTextWatcher(inputPdesc));
                inputPage.addTextChangedListener(new ServiceFrgment.MyTextWatcher(inputPage));
                inputPage.addTextChangedListener(new ServiceFrgment.MyTextWatcher(inputPage));
                inputPage.addTextChangedListener(new ServiceFrgment.MyTextWatcher(inputPage));

                btnSignUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        submitForm();
                    }
                });*/

                return view;
            }

   /* private void submitForm() {
        if (!validatePname()) {
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
    }*/

    }



