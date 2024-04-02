package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.adapters.QuestionAdapter;
import com.example.models.Question;
import com.example.skinlab.databinding.ActivityAboutskinTestBinding;

import java.util.ArrayList;
import java.util.List;

public class Aboutskin_test extends AppCompatActivity {

    ActivityAboutskinTestBinding binding;

    ListView listView;
    QuestionAdapter adapter;
    List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutskinTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listView = findViewById(R.id.lvTest);
        questionList = initData();
        adapter = new QuestionAdapter(Aboutskin_test.this, questionList);
        listView.setAdapter(adapter);
    }

    private List<Question> initData() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Câu 1", "Văn bản", "A", "B", "C", "D"));
        questions.add(new Question("Câu 2", "Văn bản", "A", "B", "C", "D"));
        questions.add(new Question("Câu 3", "Văn bản", "A", "B", "C", "D"));
        return questions;
    }
}
