package com.foxlinkimage.alex.facebooksharedemo;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends ActionBarActivity {
    Button btn_share;
    ShareDialog shareDialog;
    final String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/MyPicFolder/1/2.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_share = (Button) findViewById(R.id.btn_share);
        shareDialog = new ShareDialog(this);

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap image = BitmapFactory.decodeFile(path);
                SharePhoto photo = new SharePhoto.Builder().setBitmap(image).build();
                SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
                shareDialog.show(content);
            }
        });


        // get Share Key
                PackageInfo info;
                try {
                    info = getPackageManager().getPackageInfo("com.foxlinkimage.alex.facebooksharedemo", PackageManager.GET_SIGNATURES);
                    for(Signature signature : info.signatures)
                    {      MessageDigest md;
                        md =MessageDigest.getInstance("SHA");
                        md.update(signature.toByteArray());
                        String KeyResult =new String(Base64.encode(md.digest(), 0));//String something = new String(Base64.encodeBytes(md.digest()));
                        Log.e("hash key", KeyResult);
                        Toast.makeText(MainActivity.this, "My FB Key is \n" + KeyResult, Toast.LENGTH_LONG).show();
                    }
                }catch(PackageManager.NameNotFoundException e1){Log.e("name not found", e1.toString());
                }catch(NoSuchAlgorithmException e){Log.e("no such an algorithm", e.toString());
                }catch(Exception e){Log.e("exception", e.toString());}


    }


}
