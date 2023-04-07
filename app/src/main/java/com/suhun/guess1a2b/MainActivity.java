package com.suhun.guess1a2b;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public String tag = MainActivity.class.getSimpleName();
    Button guessButton, restartButton, settingButton, exitButton;
    EditText userInputEditText;
    TextView lotTextView;
    private String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initGame();
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(tag, "The answer is " + answer);
                String userInput = userInputEditText.getText().toString();
                String result = checkAB(userInput);
                String message = "";
                if(result.contentEquals("4A0B")){
                    message = "You got it!!!";
                }else{
                    message = userInput + ":" + result + "\n";
                    lotTextView.append(message);
                    userInputEditText.setText("");
                }
                showMessage(message);
            }
        });
    }

    private void initView(){
        guessButton = findViewById(R.id.guess);
        restartButton = findViewById(R.id.restart);
        settingButton = findViewById(R.id.setting);
        exitButton = findViewById(R.id.exit);
        userInputEditText = findViewById(R.id.userInput);
        lotTextView = findViewById(R.id.log);
    }

    private void initGame(){
        answer = createAnswer(4);
    }

    private String createAnswer(int answerLen){
        HashSet<Integer> numSet = new HashSet<Integer>();

        while(numSet.size() < answerLen){
            numSet.add(new Random().nextInt(10));
        }
        List<Integer> numList = new ArrayList<Integer>();
        for(Integer num:numSet){
            numList.add(num);
        }
        Collections.shuffle(numList);
        StringBuffer stringBuffer = new StringBuffer();
        for(Integer num:numList){
            stringBuffer.append(num);
        }
        return stringBuffer.toString();
    }

    private String checkAB(String userInput){
        int a=0,b=0;
        for(int i=0;i<answer.length();i++){
            if(answer.charAt(i)==userInput.charAt(i)){
                a++;
            }else if(answer.contains(""+userInput.charAt(i))){
                b++;
            }
        }
        return String.format("%dA%dB",a,b);
    }

    private void showMessage(String message){
        new AlertDialog.Builder(this)
                .setTitle("Result")
                .setMessage(message)
                .setPositiveButton("Ok", null)
                .show();
    }
}