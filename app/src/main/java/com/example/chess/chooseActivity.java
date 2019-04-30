package com.example.chess;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class chooseActivity extends Activity implements View.OnClickListener {


    private ImageButton Stringhini, Krishna, Josh, Brown, Jared, Natalie;
    private ImageView loadingGif;
    private LinearLayout mainLayout;

    private TextView topText;

    public int avatarIDp1 = 0;
    public int avatarIDp2 = 0;
    boolean player1Select;
    //public chooseActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        Stringhini = findViewById(R.id.avatarStringhini);
        Stringhini.setOnClickListener(this);

        Krishna = findViewById(R.id.avatarKrishna);
        Krishna.setOnClickListener(this);

        Josh = findViewById(R.id.avatarJosh);
        Josh.setOnClickListener(this);

        Brown = findViewById(R.id.avatarBrown);
        Brown.setOnClickListener(this);

        Jared = findViewById(R.id.avatarJared);
        Jared.setOnClickListener(this);

        Natalie = findViewById(R.id.avatarNatalie);
        Natalie.setOnClickListener(this);

        mainLayout = findViewById(R.id.mainLayout);

        loadingGif = findViewById(R.id.loadingGif);

        topText = findViewById(R.id.textChoose);

        player1Select = true;

    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.avatarStringhini: {
                Stringhini.setVisibility(View.GONE);
                if (player1Select) {
                    avatarIDp1 = R.id.avatarStringhini;
                    topText.setText("Great Choice! Now Player 2");
                    player1Select = false;
                }
                else {
                    launchGameActivity(5, true);
                }
                break;
            }


            case R.id.avatarJosh: {
                Josh.setVisibility(View.GONE);
                if (player1Select) {
                    avatarIDp1 = R.id.avatarJosh;
                    topText.setText("Great Choice! Now Player 2");
                    player1Select = false;
                }
                else {
                    avatarIDp2 = R.id.avatarJosh;
                    launchGameActivity(5, true);
                }
                break;
            }

            case R.id.avatarBrown: {
                Brown.setVisibility(View.GONE);
                if (player1Select) {
                    avatarIDp1 = R.id.avatarBrown;
                    topText.setText("Great Choice! Now Player 2");
                    player1Select = false;
                }
                else {
                    avatarIDp2 = R.id.avatarBrown;
                    launchGameActivity(5, true);
                }
                break;
            }
            case R.id.avatarJared: {
                Jared.setVisibility(View.GONE);
                if (player1Select) {
                    avatarIDp1 = R.id.avatarJared;
                    topText.setText("Great Choice! Now Player 2");
                    player1Select = false;
                }
                else {
                    avatarIDp2 = R.id.avatarJared;
                    launchGameActivity(5, true);
                }
                break;
            }
            case R.id.avatarNatalie: {
                Natalie.setVisibility(View.GONE);
                if (player1Select) {
                    avatarIDp1 = R.id.avatarNatalie;
                    topText.setText("Great Choice! Now Player 2");
                    player1Select = false;
                } else {
                    avatarIDp2 = R.id.avatarNatalie;
                    launchGameActivity(5, true);
                }
                break;
            }
            case R.id.avatarKrishna: {
                Krishna.setVisibility(View.GONE);
                if (player1Select) {
                    avatarIDp1 = R.id.avatarKrishna;
                    topText.setText("Great Choice! Now Player 2");
                    player1Select = false;
                } else {
                    avatarIDp2 = R.id.avatarKrishna;
                    launchGameActivity(5, true);
                }
                break;
            }

        }

    }
    private void launchGameActivity(final int rotation, boolean first)
    {
        if  (first) {

            mainLayout.setBackgroundColor(Color.parseColor("#4A0C21"));
            findViewById(R.id.row1).setVisibility(View.GONE);
            findViewById(R.id.row2).setVisibility(View.GONE);
            findViewById(R.id.row3).setVisibility(View.GONE);

            Stringhini.setVisibility(View.GONE);
            Krishna.setVisibility(View.GONE);
            Josh.setVisibility(View.GONE);
            Brown.setVisibility(View.GONE);
            Jared.setVisibility(View.GONE);
            Natalie.setVisibility(View.GONE);
            topText.setVisibility(View.GONE);
            findViewById(R.id.loadingText).setVisibility(View.VISIBLE);
            loadingGif.setVisibility(View.VISIBLE);
            loadingGif.setScaleType(ImageView.ScaleType.FIT_XY);
            avatarIDp2 = R.id.avatarStringhini;

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    player1Select = true;
                    //Launches the new activity
                    Intent gameActivity = new Intent(chooseActivity.this, gameActivity.class);
                    gameActivity.putExtra("p1Avatar", avatarIDp1);
                    gameActivity.putExtra("p1Avatar", avatarIDp2);
                    startActivity(gameActivity);
                }
            }, 5000);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                loadingGif.setRotation(rotation);
                launchGameActivity(rotation + 15, false);
            }
        }, 10);

    }

}
