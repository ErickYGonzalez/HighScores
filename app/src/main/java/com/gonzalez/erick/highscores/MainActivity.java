package com.gonzalez.erick.highscores;

import android.animation.Animator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;


import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import android.widget.TextView;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;
import com.transitionseverywhere.ArcMotion;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private CoordinatorLayout layoutMain;
    private RelativeLayout layoutAdd;
    private RelativeLayout layoutContent;
    private Toolbar toolbar;
    private String[] methods;
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //get layouts
        layoutMain = (CoordinatorLayout) findViewById(R.id.layoutMain);
        layoutAdd = (RelativeLayout) findViewById(R.id.layoutAdd);
        layoutContent = (RelativeLayout) findViewById(R.id.layoutContent);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               viewMenu();
            }
        });

        //get number picker

        //set On click for the buttons
        final Button buttonOk = (Button) findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMenu();
            }
        });

        Button buttonCancel = (Button) findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMenu();
            }
        });

        final TextView methodTextView = (TextView) findViewById(R.id.method_textview);
        final ViewGroup container = (ViewGroup) findViewById(R.id.transitions_container);

        methods = getResources().getStringArray(R.array.methods);

        //all of this can be encompassed by multiple methods
        for (String method : methods)
        {
            final TextView tv = new TextView(this);
            tv.setText(method);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(30);
            tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv.setVisibility(View.GONE);
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(32,32,32,32);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TransitionManager.beginDelayedTransition(container);


                    RelativeLayout.LayoutParams layoutParams =
                            (RelativeLayout.LayoutParams)container.getLayoutParams();
                    layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
                    container.setLayoutParams(layoutParams);

                    //get all children in container
                    final int childCount = container.getChildCount();

                    //set the visibility of all other views except the one that was clicked to GONE
                    for (int i = 0; i < childCount; i++) {
                        View item = container.getChildAt(i);
                        if (item == view) continue;
                        else item.setVisibility(View.GONE);
                    }

                    view.setPadding(0,8,0,0);

                    final LinearLayout amountLayout = (LinearLayout) findViewById(R.id.amount_layout);
                    final NumberPicker npOnes = (NumberPicker) findViewById(R.id.np_ones);
                    final NumberPicker npTenths = (NumberPicker) findViewById(R.id.np_tenths);
                    final NumberPicker npHundredths = (NumberPicker) findViewById(R.id.np_hundredths);
                    final NumberPicker npUnits = (NumberPicker) findViewById(R.id.np_unit);
                    final NumberPicker npMembers = (NumberPicker) findViewById(R.id.np_members);
                    final TextView amountTextview = (TextView) findViewById(R.id.amount_textview);
                    final LinearLayout buttonLayout = (LinearLayout) findViewById(R.id.button_layout);

                    setNumberPickerRange(npOnes,0,99);
                    setNumberPickerRange(npTenths,0,9);
                    setNumberPickerRange(npHundredths,0,9);
                    setNumberPickerRange(npMembers,1,99);




                    npOnes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(npOnes.getValue());
                            sb.append(".");
                            sb.append(npTenths.getValue());
                            sb.append(npHundredths.getValue());
                            if (npUnits.getValue() == 0) sb.append("g");
                            else sb.append("oz");
                            final String number = sb.toString();
                            amountTextview.setText(number);

                            TransitionManager.beginDelayedTransition(buttonLayout);
                            buttonOk.setVisibility(View.VISIBLE);
                            buttonOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    amountLayout.setVisibility(View.GONE);
                                    Toast toast = Toast.makeText(getApplication(), "\"Data saved\"", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            });

                        }
                    });

                    npTenths.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(npOnes.getValue());
                            sb.append(".");
                            sb.append(npTenths.getValue());
                            sb.append(npHundredths.getValue());
                            if (npUnits.getValue() == 0) sb.append("g");
                            else sb.append("oz");
                            String number = sb.toString();
                            amountTextview.setText(number);

                            TransitionManager.beginDelayedTransition(buttonLayout);
                            buttonOk.setVisibility(View.VISIBLE);
                            buttonOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    amountLayout.setVisibility(View.GONE);
                                    Toast toast = Toast.makeText(getApplication(), "\"Data saved\"", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            });
                        }
                    });
                    npHundredths.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(npOnes.getValue());
                            sb.append(".");
                            sb.append(npTenths.getValue());
                            sb.append(npHundredths.getValue());
                            if (npUnits.getValue() == 0) sb.append("g");
                            else sb.append("oz");
                            String number = sb.toString();
                            amountTextview.setText(number);

                            TransitionManager.beginDelayedTransition(buttonLayout);
                            buttonOk.setVisibility(View.VISIBLE);
                            buttonOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    amountLayout.setVisibility(View.GONE);
                                    Toast toast = Toast.makeText(getApplication(), "\"Data saved\"", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            });
                        }
                    });
                    npUnits.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(npOnes.getValue());
                            sb.append(".");
                            sb.append(npTenths.getValue());
                            sb.append(npHundredths.getValue());
                            if (npUnits.getValue() == 0) sb.append("g");
                            else sb.append("oz");
                            String number = sb.toString();
                            amountTextview.setText(number);

                            TransitionManager.beginDelayedTransition(buttonLayout);
                            buttonOk.setVisibility(View.VISIBLE);
                            buttonOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    amountLayout.setVisibility(View.GONE);
                                    Toast toast = Toast.makeText(getApplication(), "\"Data saved\"", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            });
                        }
                    });

                    final String[] units = {"g","oz"};

                    npUnits.setDisplayedValues(units);
                    npUnits.setMinValue(0);
                    npUnits.setMaxValue(units.length - 1);
                    npUnits.setShowDividers(NumberPicker.SHOW_DIVIDER_NONE);
                    npUnits.setWrapSelectorWheel(false);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            amountLayout.setVisibility(View.VISIBLE);

                        }
                    }, 500);

                }
            });
            container.addView(tv);
        }

        //when a smoke method is clicked
        methodTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TransitionManager.beginDelayedTransition(container);


                methodTextView.setText("Choose a method:");

                final int childCount = container.getChildCount();

                //shows the list of smoke method
                for (int i = 0; i < childCount; i++) {
                    View items = container.getChildAt(i);
                    items.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void setNumberPickerRange(NumberPicker np, int min, int max) {
        np.setMinValue(min);
        np.setMaxValue(max);
        np.setWrapSelectorWheel(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            np.setShowDividers(NumberPicker.SHOW_DIVIDER_NONE);
            np.setDividerColor(Color.parseColor("#00ffffff"));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void viewMenu() {

        if (!isOpen) {

            int x = layoutContent.getRight();
            int y = layoutContent.getBottom();

            int startRadius = 0;
            int endRadius = (int) Math.hypot(layoutMain.getWidth(), layoutMain.getHeight());

            fab.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(),android.R.color.white,null)));
            fab.setVisibility(FloatingActionButton.GONE);

            Animator anim = ViewAnimationUtils.createCircularReveal(layoutAdd, x, y, startRadius, endRadius);

            layoutAdd.setVisibility(View.VISIBLE);
            anim.start();

            toolbar.setTitle("Add Session");

            isOpen = true;

        } else {

            int x = layoutAdd.getRight();
            int y = layoutAdd.getBottom();

            int startRadius = Math.max(layoutContent.getWidth(), layoutContent.getHeight());
            int endRadius = 0;

            fab.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(),R.color.colorAccent,null)));
            //fab.setImageResource(R.drawable.ic_plus_white);

            Animator anim = ViewAnimationUtils.createCircularReveal(layoutAdd, x, y, startRadius, endRadius);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    layoutAdd.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            anim.start();

            toolbar.setTitle("High Scores");
            fab.setVisibility(FloatingActionButton.VISIBLE);


            isOpen = false;
        }
    }
}
