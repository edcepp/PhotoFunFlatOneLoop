package net.zdome.cs371.epp.photofun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.Button;
import android.view.View;


public class PhotoFun extends AppCompatActivity {

    private Bitmap myOriginalBmp;
    private ImageView myNewImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_fun);

        ImageView originalImageView = (ImageView) findViewById(R.id.originalImage);
        BitmapDrawable originalDrawableBmp = (BitmapDrawable) originalImageView.getDrawable();
        myOriginalBmp = originalDrawableBmp.getBitmap();

        myNewImageView = (ImageView) findViewById(R.id.newImage);

        Button grayFilterButton = (Button) findViewById(R.id.grayFilterButton);
        grayFilterButton.setOnClickListener(new grayFilterButtonListener());
        Button brightnessFilterButton = (Button) findViewById(R.id.brightnessFilterButton);
        brightnessFilterButton.setOnClickListener(new brightnessFilterButtonListener());
    }

    public int grayTransform(int inPixel) {
        int intensity = (Color.red(inPixel) + Color.green(inPixel) + Color.blue(inPixel)) / 3;
        return Color.argb(Color.alpha(inPixel), intensity, intensity, intensity);
    }

    private class grayFilterButtonListener implements View.OnClickListener {
        public void onClick(View button) {
            int width = myOriginalBmp.getWidth();
            int height = myOriginalBmp.getHeight();

            Bitmap newBmp = Bitmap.createBitmap(width, height, myOriginalBmp.getConfig());

            for (int w = 0; w < width; w++) {
                for (int h = 0; h < height; h++) {
                    int outPixel = grayTransform(myOriginalBmp.getPixel(w, h));
                    newBmp.setPixel(w, h, outPixel);
                }
            }
            myNewImageView.setImageBitmap(newBmp);
        }
    }

    protected int constrain(int color) {
        if (color > 255)
            return 255;
        else if (color < 0)
            return 0;
        else
            return color;
    }

    private final int ADJUSTMENT = 100;

    public int brightnessTransform(int inPixel) {
        int red = constrain(Color.red(inPixel) + ADJUSTMENT);
        int green = constrain(Color.green(inPixel) + ADJUSTMENT);
        int blue = constrain(Color.blue(inPixel) + ADJUSTMENT);
        return Color.argb(Color.alpha(inPixel), red, green, blue);
    }

    private class brightnessFilterButtonListener implements View.OnClickListener {
        public void onClick(View button) {
            int width = myOriginalBmp.getWidth();
            int height = myOriginalBmp.getHeight();

            Bitmap newBmp = Bitmap.createBitmap(width, height, myOriginalBmp.getConfig());

            for (int w = 0; w < width; w++) {
                for (int h = 0; h < height; h++) {
                    int outPixel = brightnessTransform(myOriginalBmp.getPixel(w, h));
                    newBmp.setPixel(w, h, outPixel);
                }
            }
            myNewImageView.setImageBitmap(newBmp);
        }
    }
}

