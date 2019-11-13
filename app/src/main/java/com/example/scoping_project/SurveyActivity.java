package com.example.scoping_project;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class SurveyActivity extends AppCompatActivity {
    int questionId=0;
    double score=0.0;
    TextView questionTextView,low,high;
    Button next;
    RadioGroup group;;
    Response[] ans;
    String teamID,token,userID;
    OkHttpClient client;
    TextView teamNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        client =  new OkHttpClient();

        if(getIntent().getExtras()!=null)
        {
            teamID= getIntent().getExtras().getString("TEAM_ID");
            token= getIntent().getExtras().getString("TOKEN");
            userID = getIntent().getExtras().getString("USER_ID");
            getTeamInfo(teamID,token);

        }
   Log.d("User id from Intent",userID);
        ans=new Response[]{
                new Response(R.string.Question1,0),
                new Response(R.string.Question2,0),
                new Response(R.string.Question3,0),
                new Response(R.string.Question4,0),
                new Response(R.string.Question5,0),
                new Response(R.string.Question6,0),
                new Response(R.string.Question7,0),

        };

        questionTextView=findViewById(R.id.question);
        teamNameTextView = findViewById(R.id.teamNametextView);
        low=findViewById(R.id.low);
        high=findViewById(R.id.high);
        next=findViewById(R.id.next);
        group=findViewById(R.id.radioGroup);
        setUI();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedRadioButtonID = group.getCheckedRadioButtonId();

                // If nothing is selected from Radio Group, then it return -1
                if (selectedRadioButtonID != -1) {

                    RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);
                    String selectedRadioButtonText = selectedRadioButton.getText().toString();
                    questionId++;
                    score+=Double.parseDouble(selectedRadioButtonText)+1;
                    if(questionId<=6)
                        setUI();
                    else
                    {


                      //  postScores(score);  // have to test this one
                        Toast.makeText(getApplicationContext(),"Total Score"+score,Toast.LENGTH_SHORT).show();
                        finish();  // i'm not sure if the previous activity will be active or not , if its not we can add intent .
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"No option selected",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public  void getTeamInfo(String teamID, String Token){
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id",teamID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String jsonString=jsonObject.toString();
        RequestBody rbody = RequestBody.create(JSON, jsonString);


        Request request = new Request.Builder()
                .url("http://ec2-3-94-187-73.compute-1.amazonaws.com:5000/support/getTeamById")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .post(rbody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                try {
                    String json = response.body().string();
                    final  JSONObject root = new JSONObject(json);
                    String teamName = root.getString("teamName");
                    teamNameTextView.setText( teamName);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


   public void postScores(String userID, String teamID,Integer score)
    {

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("teamID",teamID);
            jsonObject.put("userID",userID);
            jsonObject.put("scores",score);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String jsonString=jsonObject.toString();
        RequestBody rbody = RequestBody.create(JSON, jsonString);


        Request request = new Request.Builder()
                .url("http://ec2-3-94-187-73.compute-1.amazonaws.com:5000/support/getTeamById")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .post(rbody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
            }
        });


    }
    public void setUI()
    {

        group.clearCheck();

        int Question=ans[questionId].getQuestionId();
        questionTextView.setText(Question);


    }
    }

