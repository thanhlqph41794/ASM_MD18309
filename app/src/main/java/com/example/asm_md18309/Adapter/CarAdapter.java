package com.example.asm_md18309.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asm_md18309.Model.CarModel;
import com.example.asm_md18309.R;

import java.util.List;
    public class CarAdapter extends BaseAdapter {

        List<CarModel> carModelList;

        Context context;
        public interface OnDeleteClickListener {
            void onDeleteClick(int pos);
        }
        private OnDeleteClickListener onDeleteClickListener;
        public void setOnDeleteClickListener(OnDeleteClickListener listener) {
            this.onDeleteClickListener = listener;
        }
        public CarAdapter(Context context, List<CarModel> carModelList) {
            this.context = context;
            this.carModelList = carModelList;
        }

        @Override
        public int getCount() {
            return carModelList.size();
        }

        @Override
        public Object getItem(int i) {
            return carModelList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.item_sp, viewGroup, false);

            TextView tvID = (TextView) rowView.findViewById(R.id.tvId);
            ImageView imgAvatar = (ImageView) rowView.findViewById(R.id.imgAvatatr);
            TextView tvName = (TextView) rowView.findViewById(R.id.tvName);

            TextView tvNamSX = (TextView) rowView.findViewById(R.id.tvNamSX);

            TextView tvHang = (TextView) rowView.findViewById(R.id.tvHang);

            TextView tvGia = (TextView) rowView.findViewById(R.id.tvGia);

//        String imageUrl = mList.get(position).getThumbnailUrl();
//        Picasso.get().load(imageUrl).into(imgAvatar);
////        imgAvatar.setImageResource(imageId[position]);
            tvName.setText(String.valueOf(carModelList.get(position).getTen()));

            tvNamSX.setText(String.valueOf(carModelList.get(position).getNamSX()));

            tvHang.setText(String.valueOf(carModelList.get(position).getHang()));

            tvGia.setText(String.valueOf(carModelList.get(position).getGia()));

            return rowView;
        }


    }

