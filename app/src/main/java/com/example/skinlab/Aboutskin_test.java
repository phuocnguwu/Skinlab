package com.example.skinlab;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapters.QuestionAdapter;
import com.example.models.Question;
import com.example.skinlab.databinding.ActivityAboutskinTestBinding;

import java.util.ArrayList;
import java.util.List;

public class Aboutskin_test extends AppCompatActivity {

    ActivityAboutskinTestBinding binding;

    private boolean allQuestionsAnswered = false;

    RecyclerView recyclerView;
    QuestionAdapter adapter;
    ArrayList<Question> questionList;
    DatabaseHelper databaseHelper;

    public static final String DB_NAME = "Skinlab.db";

    public static SQLiteDatabase db = null;

    public static final String TBL_NAME = "ABOUTSKIN_TEST";
    public static final String COLUMN_STT = "TEST_STT";
    public static final String COLUMN_QUESTION = "TEST_QUESTION";
    public static final String COLUMN_TEST_A = "TEST_A";
    public static final String COLUMN_TEST_B = "TEST_B";
    public static final String COLUMN_TEST_C = "TEST_C";
    public static final String COLUMN_TEST_D = "TEST_D";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutskinTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.lvTest);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addEvents();
        loadQuestion();
        isAllQuestionsAnswered();
    }

    private void loadQuestion() {
        questionList = new ArrayList<>();
        db = SQLiteDatabase.openDatabase(getDatabasePath(DB_NAME).getPath(), null, SQLiteDatabase.OPEN_READONLY);

        Cursor cursor = db.query(TBL_NAME, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int columnIndexSTT = cursor.getColumnIndex(COLUMN_STT);
                int columnIndexQuestion = cursor.getColumnIndex(COLUMN_QUESTION);
                int columnIndexA = cursor.getColumnIndex(COLUMN_TEST_A);
                int columnIndexB = cursor.getColumnIndex(COLUMN_TEST_B);
                int columnIndexC = cursor.getColumnIndex(COLUMN_TEST_C);
                int columnIndexD = cursor.getColumnIndex(COLUMN_TEST_D);

                if (columnIndexSTT != -1 && columnIndexQuestion != -1 &&
                        columnIndexA != -1 && columnIndexB != -1 && columnIndexC != -1
                        && columnIndexD != -1) {

                    String STT = cursor.getString(columnIndexSTT);
                    String Question = cursor.getString(columnIndexQuestion);
                    String A = cursor.getString(columnIndexA);
                    String B = cursor.getString(columnIndexB);
                    String C = cursor.getString(columnIndexC);
                    String D = cursor.getString(columnIndexD);

                    Question question = new Question(STT, Question, A, B, C, D);
                    questionList.add(question);
                }
            } while (cursor.moveToNext());

            cursor.close();
        }

        adapter = new QuestionAdapter(this, questionList);
        recyclerView.setAdapter(adapter);
    }

    private boolean isAllQuestionsAnswered() {
        for (Question question : questionList) {
            if (!question.isCheckedA() && !question.isCheckedB() && !question.isCheckedC() && !question.isCheckedD()) {
                return false; // Nếu có câu hỏi nào chưa được trả lời, trả về false
            }
        }
        return true; // Nếu tất cả các câu hỏi đã được trả lời, trả về true
    }

    private void addEvents() {
        binding.btnclickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAllQuestionsAnswered()) {
                    int totalScore = calculateTotalScore();
                    Intent intent = new Intent(Aboutskin_test.this, Aboutskin_result.class);
                    // Truyền điểm sang màn hình kết quả
                    intent.putExtra("TOTAL_SCORE", totalScore);
                    startActivity(intent);
                } else {
                    Toast.makeText(Aboutskin_test.this, "Vui lòng trả lời đầy đủ", Toast.LENGTH_SHORT).show();
//                    // Hiển thị hộp thoại cảnh báo nếu các câu hỏi chưa được trả lời đầy đủ
//                    AlertDialog.Builder builder = new AlertDialog.Builder(Aboutskin_test.this);
//                    builder.setMessage("Vui lòng trả lời đầy đủ");
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    builder.show();
                }
            }
        });
    }

    private int calculateTotalScore() {
        int totalScore = 0;
        for (Question question : questionList) {
            if (question.isCheckedA()) {
                // Nếu radA được chọn, tổng điểm tăng thêm 1
                totalScore += 1;
            } else if (question.isCheckedB()) {
                // Tương tự cho các trường hợp còn lại
                totalScore += 2;
            } else if (question.isCheckedC()) {
                totalScore += 3;
            } else if (question.isCheckedD()) {
                totalScore += 4;
            }
        }
        return totalScore;
    }
}
