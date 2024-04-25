package com.example.skinlab;


import static android.app.PendingIntent.getActivity;


import static java.lang.Integer.parseInt;
import static java.security.AccessController.getContext;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;


import com.example.adapters.AddressRecyclerAdapter;
import com.example.adapters.GioHangAdapter;
import com.example.models.Address;
import com.example.models.Order;
import com.example.models.Product;
import com.example.skinlab.databinding.ActivityDialogDathangBinding;
import com.example.skinlab.databinding.ActivityDonhangDathangBinding;
import com.example.skinlab.databinding.ActivityForumDialogSendBinding;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Donhang_dathang extends AppCompatActivity {


    ActivityDonhangDathangBinding binding;
    GioHangAdapter gioHangAdapter;
    private ArrayList<Product> gioHangItemList = new ArrayList<>();
    int receivedTotalPrice = 0;
    ArrayList<Address> addresses;
    DatabaseHelper databaseHelper;

    String name;
    String phone;
    String address;
    String address2;

   AddressRecyclerAdapter addressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonhangDathangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent != null) {
            Log.d("test intent donhang", "chạy nè");
            String name = intent.getStringExtra("name");
            String phone = intent.getStringExtra("phone");
            String address = intent.getStringExtra("address");
            Log.d("test intent donhang", "name: " + name);

            // Hiển thị thông tin địa chỉ trong các TextView tương ứng
            binding.txtName.setText(name);
            binding.txtphone.setText(phone);
            binding.txtaddress.setText(address);
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            receivedTotalPrice = extras.getInt("totalPrice");
        }
        gioHangAdapter = new GioHangAdapter(Donhang_dathang.this, R.layout.dathang_item_list, gioHangItemList);

        binding.lvOrderlist.setAdapter(gioHangAdapter);
        loadCartFromSharedPreferences();
        addEvents();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //loadUserAddresses();
    }

//    private void loadUserAddresses() {
//        String loggedInPhone = getLoggedInPhone();
//        if (loggedInPhone != null && !loggedInPhone.isEmpty()) {
//            addresses = new ArrayList<>();
//            SQLiteDatabase db = databaseHelper.getReadableDatabase();
//            Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.USER +
//                    " WHERE " + DatabaseHelper.COLUMN_USER_PHONE + " = ?", new String[]{loggedInPhone});
//            if (cursor.moveToFirst()) {
//                do {
//                    String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_NAME));
//                    String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_PHONE));
//                    String address1 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ADDRESS));
//                    String address2 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ADDRESS2));
//                    String name2ForAddress2 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_NAME2FORADDRESS2));
//                    String phone2ForAddress2 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_PHONE2FORADDRESS2));
//
//                    // Kiểm tra xem cả address1 và address2 đều có giá trị
//                    if (address1 != null && !address1.isEmpty()) {
//                        Address address = new Address(name, phone, address1, null);
//                        addresses.add(address);
//                    }
//                    // Nếu address2 cũng có giá trị, thêm một địa chỉ mới với address2
//                    if (address2 != null && !address2.isEmpty()) {
//                        Address address2Item = new Address(name2ForAddress2, phone2ForAddress2, address2, null);
//                        addresses.add(address2Item);
//                    }
//                } while (cursor.moveToNext());
//            }
//            cursor.close();
//            db.close();
//            // Kiểm tra xem danh sách địa chỉ có dữ liệu hay không
//            if (addresses.isEmpty()) {
//                Toast.makeText(this, "Không có địa chỉ", Toast.LENGTH_SHORT).show();
//            } else {
//                // Nếu danh sách địa chỉ có dữ liệu, hiển thị RecyclerView và hiển thị các địa chỉ
//                binding.rcvdiachi.setVisibility(View.VISIBLE);
//                displayAddresses();
//            }
//        } else {
//            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public String getLoggedInPhone() {
//        SharedPreferences sharedPreferences = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
//        return sharedPreferences.getString("loggedInPhone", "");
//    }
//
//    private void displayAddresses() {
//        AddressRecyclerAdapter adapter = new AddressRecyclerAdapter(this, addresses,databaseHelper);
//        binding.rcvdiachi.setAdapter(adapter);
//    }


    private void loadCartFromSharedPreferences() {
        SharedPreferences sharedPreferences = Donhang_dathang.this.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        Set<String> productSet = sharedPreferences.getStringSet("cart_items", new HashSet<String>());
        for (String productString : productSet) {
            Product product = parseProductFromString(productString);
            if (product != null) {
                Log.d("Load cart", "product: " + product);
                gioHangItemList.add(product);
                gioHangAdapter.notifyDataSetChanged();
            } else {
                Log.d("Load cart", "product = null");
            }
        }
    }

    private Product parseProductFromString(String productString) {
        if (productString == null || productString.isEmpty()) {
            return null;
        }
        Log.d("Donhang_dathang", "Parsing product string: " + productString);
        String[] parts = productString.split(",");
        if (parts.length < 8) {
            Log.e("Giohang_Fragment", "Invalid product string: " + productString);
            return null;
        }
        String pd_id = parts[0];
        String pd_name = parts[1];
        int pd_price = parseInt(parts[2]);
        int pd_price2 = parseInt(parts[3]);
        String pd_cate = parts[4];
        String pd_brand = parts[5];
        String pd_photo = parts[6];
        String pd_des = parts[7];
        int quantity;
        try {
            quantity = Integer.parseInt(parts[8]);
        } catch (NumberFormatException e) {
            Log.w("Donhang_dathang", "Lỗi format số lượng: " + parts[8]);
            quantity = 0;}
        Product product = new Product(pd_id, pd_name, pd_price, pd_price2, pd_cate, pd_brand, pd_photo, pd_des);
        product.setQuantity(quantity);
        binding.textView37.setText(String.valueOf(receivedTotalPrice));
        binding.textView34.setText(String.valueOf(receivedTotalPrice));

        return product;
    }

    private void addEvents() {
        binding.btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một đối tượng Order từ dữ liệu đã có
                //Order order = new Order("user_001", "product_order1_here", 1, "product_order2_here", 2, "product_order3_here", 3, "Đang xử lý");
                Order order = createOrderFromCart();

                // Chèn đối tượng Order vào cơ sở dữ liệu
                DatabaseHelper dbHelper = new DatabaseHelper(Donhang_dathang.this);
                if (dbHelper.insertOrder(order)) {
                    // Nếu chèn thành công, hiển thị thông báo hoặc thực hiện hành động tiếp theo ở đây
                    Log.d("Database", "Insert successful");
                } else {
                    // Nếu chèn không thành công, hiển thị thông báo hoặc xử lý lỗi ở đây
                    Log.d("Database", "Insert failed");
                }

                // Hiển thị dialog hoặc chuyển sang activity khác ở đây
                showAlerDialog();
                Intent intent = new Intent(Donhang_dathang.this, Donhang_Chitietdonhang.class);
                startActivity(intent);
            }
        });
    }

    private Order createOrderFromCart() {
        List<String> productIds = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        for (Product product : gioHangItemList) {
            productIds.add(product.getPd_id());
            quantities.add(product.getQuantity());
        }

        // Chuyển list thành mảng để truyền vào constructor của Order
        String[] productIdArray = productIds.toArray(new String[0]);
        Integer[] quantityArray = quantities.toArray(new Integer[0]);

        // Tạo một đối tượng Order từ dữ liệu đã có
        Order order = new Order("5", productIdArray[0], quantityArray[0], productIdArray[1], quantityArray[1], productIdArray[2], quantityArray[2], "Đang xử lý");
        return order;
    }


    private void showAlerDialog() {
        ActivityDialogDathangBinding dialogDathangBinding = ActivityDialogDathangBinding.inflate(LayoutInflater.from(Donhang_dathang.this));
        AlertDialog.Builder builder = new AlertDialog.Builder(Donhang_dathang.this)
                .setView(dialogDathangBinding.getRoot())
                .setCancelable(true);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(200, 200);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1000);
    }
}
