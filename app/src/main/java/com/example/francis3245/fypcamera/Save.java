package com.example.francis3245.fypcamera;

import android.content.Context;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import android.net.Uri;
import android.media.MediaScannerConnection;

/**
 * Created by francis3245 on 22/10/2016.
 */

public class Save {
    private Context Thethis;
    private String FolderName = "/FYPphoto";
    private String FileName = "photo.";

    public void saveImage(Context context, Bitmap bitmap){
        Thethis = context;
        String FilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + FolderName;
        String DateTime = getCurrentDateAndTime();
        File dir = new File(FilePath);

        if(!dir.exists()){
            dir.mkdirs();
        }

        File file = new File(dir, FileName + DateTime + ".jpg");

        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
            MakeSureFileWasCreatedThenMakeAvabile(file);
            AbleToSave();

        }
        catch (FileNotFoundException e) {UnableToSave();}
        catch (IOException e){UnableToSave();}

    }
    private void MakeSureFileWasCreatedThenMakeAvabile(File file) {
        MediaScannerConnection.scanFile(Thethis,
                new String[] { file.toString() }, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.e("ExternalStorage", "Scanned " + path + ":");
                        Log.e("ExternalStorage", "-> uri=" + uri);

                    }
                });

    }

    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    private void UnableToSave() {
        Toast.makeText(Thethis, "Save Failed", Toast.LENGTH_SHORT).show();

    }

    private void AbleToSave() {
        Toast.makeText(Thethis, "Picture Saved", Toast.LENGTH_SHORT).show();

    }


}
