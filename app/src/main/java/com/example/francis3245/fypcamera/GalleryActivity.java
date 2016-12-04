package com.example.francis3245.fypcamera;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by francis3245 on 27/11/2016.
 */

public class GalleryActivity extends Activity{
    GridView gv;
    ArrayList<File> list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        list = imageReader(Environment.getExternalStorageDirectory());

        gv = (GridView) findViewById(R.id.activity_gallery);
        gv.setAdapter(new GridAdapter());
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

            }
        });
    }

    class GridAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.gallery_grid, parent , false);
            ImageView iv = (ImageView) convertView.findViewById(R.id.grid);
            iv.setImageURI(Uri.parse(getItem(position).toString()));

            return convertView;
        }
    }

    ArrayList<File> imageReader(File root){
        ArrayList<File> imageList = new ArrayList<File>();
        File[] files = root.listFiles();
        for(int i = 0; i<files.length; i++){
            if(files[i].isDirectory()){
                imageList.addAll(imageReader(files[i]));
            }else{
                if(files[i].getName().endsWith(".jpg")){
                    imageList.add(files[i]);
                    TextView name = (TextView)findViewById(R.id.file_name);
                    name.setText(files[i].getName());
                }
            }
        }
        return imageList;
    }
}
