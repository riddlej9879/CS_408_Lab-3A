package com.example.lab_3a_calculator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import android.view.ViewGroup.LayoutParams;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.lab_3a_calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "InitializeLayout()";
    private ActivityMainBinding binding;
    private final int CHAIN_H = 5;
    private final int CHAIN_V = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initializeLayout();
    }
    // Anchor to the bottom of the output
    // button layout width and height set to match_constraint
    // To programmatically set button dimensions import android.view.ViewGroup.LayoutParams
    private void initializeLayout() {
        ConstraintLayout layout = binding.layout;
        TextView output = new TextView(this);
        output.setId(View.generateViewId());
        layout.addView(output);

        String[] btnTxtArr = getResources().getStringArray(R.array.button_text);

        int[][] viewIds = new int[CHAIN_V][CHAIN_H];

        for (int v = 0; v < CHAIN_V; v++) {
            for (int h = 0; h < CHAIN_H; h++) {
                int id = View.generateViewId();
                Button btn = new Button(this);

                btn.setId(id);
                btn.setTag("button" + (v+h));
                btn.setText(btnTxtArr[v+h]);
                btn.setTextSize(24);
                layout.addView(btn);
                viewIds[v][h] = id;
            }
        }

        ConstraintSet set = new ConstraintSet();
        set.clone(layout);
        for (int h = 0; h  < CHAIN_H; h++) {
            for (int v = 0; v < CHAIN_V; v++) {
                //       StartID    StartSide              EndID                   EndSide        Margin
                //set.connect();
                set.connect(viewIds[v][h], ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
                set.connect(viewIds[v][h], ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0);
            }
        }
        for (int h = 0; h < CHAIN_H; h++) {
            for (int v = 0; v < CHAIN_V; v++) {
                set.createHorizontalChain(ConstraintSet.PARENT_ID, ConstraintSet.LEFT, ConstraintSet.PARENT_ID,
                        ConstraintSet.RIGHT, viewIds[0], null, ConstraintSet.CHAIN_PACKED);
                set.applyTo(layout);
            }
            set.createVerticalChain(ConstraintSet.PARENT_ID, ConstraintSet.TOP, ConstraintSet.PARENT_ID,
                    ConstraintSet.BOTTOM, viewIds[0], null, ConstraintSet.CHAIN_PACKED);
            set.applyTo(layout);
        }
    }
}