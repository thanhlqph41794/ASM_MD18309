package com.example.asm_md18309;

import static java.nio.file.Files.delete;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_md18309.Adapter.CarAdapter;
import com.example.asm_md18309.Model.CarModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class sanpham extends AppCompatActivity{
    private FloatingActionButton fltadd;
    private ListView lvMain;
    private EditText edt_seach;
    List<CarModel> listCarModel;
    CarAdapter carAdapter;
    Retrofit retrofit;
    ApiService apiService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sanpham);
        fltadd = findViewById(R.id.fltadd);
        edt_seach = findViewById(R.id.edt_seach);
        lvMain = findViewById(R.id.listviewMain);
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         apiService = retrofit.create(ApiService.class);
         edt_seach.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 opendialogSeach();
             }
         });
        fltadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendialogadd();
            }
        });
        Call<List<CarModel>> call= apiService.getCars();
        call.enqueue(new Callback<List<CarModel>>() {
            @Override
            public void onResponse(Call<List<CarModel>> call, Response<List<CarModel>> response) {
                if (response.isSuccessful()) {
                    listCarModel = response.body();
                    carAdapter = new CarAdapter(getApplicationContext(), listCarModel);
                    lvMain.setAdapter(carAdapter);
                    carAdapter.setOnDeleteClickListener(new CarAdapter.OnDeleteClickListener() {
                        @Override
                        public void onDeleteClick(int pos) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(sanpham.this
                            );
                            builder.setIcon(R.drawable.node);
                            builder.setTitle("Thông báo");
                            builder.setMessage("Bạn có muốn xóa sản phẩm " + " không");
                            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    delete(pos);
                                }
                            });
                            builder.setNegativeButton("không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(sanpham.this, "Không xóa", Toast.LENGTH_SHORT).show();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<List<CarModel>> call, Throwable t) {
                Log.e("Main", t.getMessage());
            }
        });
    }

    private void opendialogSeach() {


    }

    //    public void delete(int pos){
//        String id = lvMain.get(pos).get_id();
//        Call<Void> call = apiService.deleteCar(id);
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                    // Xóa sản phẩm thành công, cập nhật lại danh sách sản phẩm và refresh RecyclerView
//                    lvMain.remove(pos);
//                    carAdapter.notifyItemRemoved(pos);
//                    Toast.makeText(sanpham.this, "xoa thanh cong", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Xóa sản phẩm thất bại
//                    Log.e("Delete", "Xóa sản phẩm thất bại: " + response.message());
//                    Toast.makeText(sanpham.this, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Log.e("Delete", t.getMessage());
//                Toast.makeText(sanpham.this, "Lỗi khi xóa sản phẩm", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    public void opendialogadd() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.item_addds, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);
        final TextInputEditText txttenxe = view.findViewById(R.id.edttenxe);
        final TextInputEditText txtloaixe = view.findViewById(R.id.edtloaixe);
        final TextInputEditText txtnsx = view.findViewById(R.id.edtnsx);
        final TextInputEditText txtgiaxe = view.findViewById(R.id.edtgiaxe);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String carName = txttenxe.getText().toString().trim();
                        String carLoai = txtloaixe.getText().toString().trim();
                        int carNSX = Integer.parseInt(txtnsx.getText().toString().trim());
                        double carGia = Double.parseDouble(txtgiaxe.getText().toString().trim());
                        // Tạo một đối tượng CarModel từ thông tin được nhập
                        CarModel newCar = new CarModel(carName,carNSX,carLoai,carGia);

                        // Gọi phương thức API để thêm xe vào MongoDB
                        Call<CarModel> addcall = apiService.addCar(newCar);
                        addcall.enqueue(new Callback<CarModel>() {
                            @Override
                            public void onResponse(Call<CarModel> call, Response<CarModel> response) {
                                if (response.isSuccessful()) {
                                    CarModel carModel = response.body();
                                    listCarModel.add(carModel);
                                    carAdapter.notifyDataSetChanged();
                                    Toast.makeText(sanpham.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
//                                    getCarsFromApi();
                                    dialog.dismiss();
                                }
                            }
                            @Override
                            public void onFailure(Call<CarModel> call, Throwable t) {
                                Log.e("Main", t.getMessage());
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

//    private void getCarsFromApi() {
//        ApiService apiService = retrofit.create(ApiService.class);
//        Call<List<CarModel>> call = apiService.getCars();
//        call.enqueue(new Callback<List<CarModel>>() {
//            @Override
//            public void onResponse(Call<List<CarModel>> call, Response<List<CarModel>> response) {
//                if (response.isSuccessful()) {
//                    listCarModel.clear(); // Xóa danh sách cũ
//                    listCarModel.addAll(response.body()); // Thêm dữ liệu mới
//                    carAdapter.notifyDataSetChanged(); // Thông báo cho Adapter rằng dữ liệu đã thay đổi
//                }
//            }
//            @Override
//            public void onFailure(Call<List<CarModel>> call, Throwable t) {
//                Log.e("Main", t.getMessage());
//            }
//        });
//    }
    
    }


