package com.example.skinlab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skinlab.databinding.ActivityQuenmatkhauBinding;

public class Quenmatkhau extends AppCompatActivity {
    ActivityQuenmatkhauBinding binding;
    DatabaseHelper dbHelper;
    EditText txtSdtcuaban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuenmatkhauBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DatabaseHelper(this);
        txtSdtcuaban = findViewById(R.id.txtSdtcuaban);

        Button btnguimaxacnhan = findViewById(R.id.btnguimaxacnhan);
        btnguimaxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kiemTraSoDienThoai();
            }
        });
    }

//    private void kiemTraSoDienThoai() {
//        String soDienThoai = txtSdtcuaban.getText().toString().trim();
//        if (soDienThoai.isEmpty()) {
//            Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Kiểm tra xem số điện thoại có trong cơ sở dữ liệu không
//        if (dbHelper.checkSoDienThoaiTonTai(soDienThoai)) {
//            // Số điện thoại đã tồn tại trong cơ sở dữ liệu, chuyển đến màn hình nhập OTP
//            // Truyền số điện thoại qua intent để sử dụng trong màn hình nhập OTP nếu cần
//            intent.putExtra("soDienThoai", soDienThoai);
//            startActivity(intent);
//        } else {
//            // Số điện thoại không tồn tại trong cơ sở dữ liệu
//            Toast.makeText(this, "Số điện thoại chưa được đăng ký", Toast.LENGTH_SHORT).show();
//        }
//    }
private void kiemTraSoDienThoai() {
    String soDienThoai = txtSdtcuaban.getText().toString().trim();
    if (soDienThoai.isEmpty()) {
        Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
        return;
    }

    // Kiểm tra xem số điện thoại có trong cơ sở dữ liệu không
    if (dbHelper.checkSoDienThoaiTonTai(soDienThoai)) {
        // Số điện thoại đã tồn tại trong cơ sở dữ liệu, chuyển đến màn hình nhập OTP
//        Intent intent = new Intent(Quenmatkhau.this, Quenmatkhau_Matkhaumoi.class);
//        // Truyền số điện thoại qua intent để sử dụng trong trang Quenmatkhau_Matkhaumoi
//
//        intent.putExtra("soDienThoai", soDienThoai);
//        startActivity(intent);
        // Chuyển sang trang Quenmatkhau_nhapOTP và truyền số điện thoại
        Intent intent = new Intent(Quenmatkhau.this, Quenmatkhau_nhapOTP.class);
        intent.putExtra("soDienThoai", soDienThoai);
        startActivity(intent);
    } else {
        // Số điện thoại không tồn tại trong cơ sở dữ liệu
        Toast.makeText(this, "Số điện thoại chưa được đăng ký", Toast.LENGTH_SHORT).show();
    }
}

}
