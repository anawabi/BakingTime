package com.amannawabi.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amannawabi.bakingtime.Model.Step;
import com.amannawabi.bakingtime.Utils.PlayerFragment;

import java.util.List;

/**
 * A fullscreen activity to play audio or video streams.
 */
public class StepActivity extends AppCompatActivity {

    private static String TAG = StepActivity.class.getSimpleName();
    public static String STEPS_EXTRA = "steps";
    public static String POS_EXTRA = "pos";
    public static String RECIPE_NAME_EXTRA = "name";

    private Toolbar toolBar;
//    private Button nextButton;
//    private Button previousButton;

    private FragmentManager fragmentManager;
    private PlayerFragment playerFragment;
    private String recipeName = "";

    private int currentPos;
    private int defaultPos = 0;
    private List<Step> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_recipe_step);

        toolBar = findViewById(R.id.toolBar);
//        nextButton = findViewById(R.id.forward_button);
//        previousButton = findViewById(R.id.back_button);
        fragmentManager = getSupportFragmentManager();

        Intent intent = getIntent();
        if (intent == null){
            closeOnError();
        }
        steps = intent.getParcelableArrayListExtra(STEPS_EXTRA);
        recipeName = intent.getStringExtra(RECIPE_NAME_EXTRA);
        if (savedInstanceState!= null) {
            currentPos = savedInstanceState.getInt(POS_EXTRA);
        } else {
            currentPos = intent.getIntExtra(POS_EXTRA, defaultPos);
            populatePlayerView();
        }

        toolBar.setTitle(recipeName);
        toolBar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Back button is clicked",Toast.LENGTH_LONG).show();
                finish();
            }
        });

//        setEnablePreviousNextButton();
    }

    private void populatePlayerView(){
        String stepDescription = steps.get(currentPos).getDescription();
        String url0 = steps.get(currentPos).getVideoURL();
        String url1 = steps.get(currentPos).getThumbnailURL();
        String videoUrl = (url1.equals(""))?url0:url1;

        Bundle bundle = new Bundle();
        bundle.putString(PlayerFragment.DESCRIPTION_EXTRA,stepDescription);
        bundle.putString(PlayerFragment.VIDEO_URL_EXTRA,videoUrl);
        playerFragment = new PlayerFragment();
        playerFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.player_fragment, playerFragment)
                .commit();

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, getString(R.string.close_on_intent_error), Toast.LENGTH_SHORT).show();
    }

//    public void nextOnClick(View view) {
//        Toast.makeText(this,"Next",Toast.LENGTH_SHORT).show();
//        currentPos++;
//        setEnablePreviousNextButton();
//        populatePlayerView();
//    }

//    public void previousOnClick(View view) {
//        Toast.makeText(this,"Previous",Toast.LENGTH_SHORT).show();
//        currentPos--;
//        setEnablePreviousNextButton();
//        populatePlayerView();
//    }

//    public void setEnablePreviousNextButton(){
//        if (currentPos == steps.size() - 1) {
//            nextButton.setEnabled(false);
//        } else if (currentPos == 0) {
//            previousButton.setEnabled(false);
//        } else {
//            nextButton.setEnabled(true);
//            previousButton.setEnabled(true);
//        }
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POS_EXTRA,currentPos);
    }
}
