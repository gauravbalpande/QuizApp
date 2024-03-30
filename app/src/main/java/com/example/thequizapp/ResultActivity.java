package com.example.thequizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.thequizapp.databinding.ActivityMainBinding;
import com.example.thequizapp.databinding.ActivityResultBinding;

public class ResultActivity extends AppCompatActivity {
    ActivityResultBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_result);
        binding.textView2.setText(
                "your score is :"
                +MainActivity.result+"/"+MainActivity.totalQuestions
        );
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ResultActivity.this,
                        MainActivity.class);
                startActivity(intent);
            }
        });
    }
}