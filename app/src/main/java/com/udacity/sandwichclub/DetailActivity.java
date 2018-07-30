package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ActivityDetailBinding mDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        @SuppressWarnings("ConstantConditions") int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);

        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        Log.d("Detail Activity", "Position activity is "+json);
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        mDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        setSupportActionBar(mDetailBinding.toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.placeholder)
                .into(mDetailBinding.imageIv);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        setTitle(sandwich.getMainName());
        mDetailBinding.descriptionTv.setText(getFieldValue(sandwich.getDescription()));
        mDetailBinding.originTv.setText(getFieldValue(sandwich.getPlaceOfOrigin()));
        mDetailBinding.alsoKnownTv.setText(getFieldValue(fromArray(sandwich.getAlsoKnownAs())));
        mDetailBinding.ingredientsTv.setText(fromArray(sandwich.getIngredients()));

    }

    /**
     *  Get the field value if available or returns the default text otherwise
     * @param data input data
     * @return the returned text
     */
    private String getFieldValue(String data){
        return TextUtils.isEmpty(data) ? getString(R.string.message_empty_field) : data;
    }

    /**
     *  Gets the data string from array of Strings
     * @param data the list of strings
     * @return a formatted string
     */
    private String fromArray(List<String> data){
        String text="";
        for (String d : data){
            text =  text.isEmpty() ? d : text + "; "+d;
        }
        return getFieldValue(text);
    }
}
