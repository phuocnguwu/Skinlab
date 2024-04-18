package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.skinlab.databinding.ActivityAboutskinIntroTestBinding;

public class Aboutskin_intro_test extends AppCompatActivity {

    ActivityAboutskinIntroTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutskinIntroTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView textView = findViewById(R.id.txtLuuy);

        String fullText = "Đảm bảo bạn thực hiện trả lời đầy đủ các câu hỏi một cách khách quan."
                + "\n\nDựa trên kết quả của test, chọn sản phẩm chăm sóc da phù hợp với tình trạng da của bạn, bao gồm sữa rửa mặt, kem dưỡng, và kem chống nắng."
                + "\n\nTrong môi trường trực tuyến và tùy theo cảm nhận hiện tại của bạn về làn da, việc đánh giá có thể không được nhất quán, vì vậy kết quả mang tính chất tham khảo."
                + "\n\nCó thể thực hiện kiểm tra tình trạng da nhiều lần trong thời gian khác nhau và trong điều kiện da khác nhau để đảm bảo tính chính xác của kết quả."
                + "\n\nNếu bạn có bất kỳ thắc mắc nào khác về tình trạng da của mình, hãy tham khảo ý kiến của một chuyên gia da liễu hoặc chuyên gia làm đẹp. Họ có thể cung cấp lời khuyên và điều trị phù hợp nhất cho da của bạn.";


        textView.setText(fullText);

        addEvents();
    }

    private void addEvents() {
        binding.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent để chuyển từ Main_Aboutskin đến Aboutskin_intro_test
                Intent intent = new Intent(Aboutskin_intro_test.this, Aboutskin_test.class);

                // Khởi chạy Intent
                startActivity(intent);
            }
        });

    }
}