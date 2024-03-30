package com.example.thequizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.thequizapp.databinding.ActivityMainBinding;
import com.example.thequizapp.model.Question;
import com.example.thequizapp.model.QuestionList;
import com.example.thequizapp.viewmodel.QuizViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    QuizViewModel quizViewModel;
    List<Question> questionList;
    static int result=0;
    static int totalQuestions=0;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //binding
        binding= DataBindingUtil.setContentView(
                this,
                R.layout.activity_main
        );
        //resetting
        result=0;
        totalQuestions=0;
        //viewModel
        quizViewModel=new ViewModelProvider(this)
                .get(QuizViewModel.class);
        //displaying first question

        DisplayFirstQuestion();
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayNextQuestion();
            }
        });
    }
    public void DisplayFirstQuestion(){
        quizViewModel.getQuestionListLiveData().observe(this,
                new Observer<QuestionList>() {
                    @Override
                    public void onChanged(QuestionList questions) {
                        questionList=questions;
                        binding.text2.setText("Question 1:"+questions.get(0).getQuestion());
                        binding.radio1.setText(questions.get(0).getOption1());
                        binding.radio2.setText(questions.get(0).getOption2());
                        binding.radio3.setText(questions.get(0).getOption3());
                        binding.radio4.setText(questions.get(0).getOption4());

                    }
                }
        );
    }
    public void DisplayNextQuestion(){

         //directing to result activity
        if(binding.btnNext.getText().equals("finish")){
            Intent i=new Intent(MainActivity.this,
                    ResultActivity.class);
            startActivity(i);
            finish();
        }
        // displaying the question
        int selectedOption=binding.radioGroup.getCheckedRadioButtonId();
        if(selectedOption!=-1){
            RadioButton radioButton=findViewById(selectedOption);
            //more question to display
            if((questionList.size()-i)>0){
                //getting the total number of question
                totalQuestions=questionList.size();
                //check if the choosen option is correct
                if(radioButton.getText().toString().equals(
                        questionList.get(i).getCorrectOption()
                )){
                    result++;
                    binding.txtResult.setText(
                            "correct answers"+result
                    );
                }
                if(i==0){
                    i++;
                }
                //displaying the next question
                binding.text2.setText("Question"+(i=1)+":"
                + questionList.get(i).getQuestion());

             binding.radio1.setText(questionList.get(i).getOption1());
             binding.radio2.setText(questionList.get(i).getOption2());
             binding.radio3.setText(questionList.get(i).getOption3());
             binding.radio4.setText(questionList.get(i).getOption4());
             //check the last question
                if(i==(questionList.size()-1)){
                    binding.btnNext.setText("finish");
                }
                binding.radioGroup.clearCheck();
                i++;
            }else{
                if(radioButton.getText().toString().equals(
                        questionList.get(i-1).getCorrectOption()
                )){
                    result++;
                    binding.txtResult.setText("correct answers :"+result);
                }
            }
        }else{
            Toast.makeText(this, "you need to make a selection", Toast.LENGTH_SHORT).show();
        }
    }
}