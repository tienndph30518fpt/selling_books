package tienndph30518.DuAnMau_DuyTien;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.lang.annotation.Annotation;

public class ManHinhChao extends AppCompatActivity {
private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);
        imageView = findViewById(R.id.ivLogo);
//        Glide.with(this).asGif().load(R.mipmap.giphy).into(imageView);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(getApplicationContext(),DangNhap_Activity.class);
//                startActivity(intent);
//            }
//        },2000);
//        Glide.with(this).load(R.mipmap.giphy).into(imageView);

        Animation annotation = AnimationUtils.loadAnimation(ManHinhChao.this,R.anim.img_anition);
        imageView.startAnimation(annotation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),DangNhap_Activity.class);
                startActivity(intent);
            }
        },2000);
    }
}