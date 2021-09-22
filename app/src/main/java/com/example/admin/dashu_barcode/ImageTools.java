package com.example.admin.dashu_barcode;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;

public class ImageTools {
    public static Bitmap getColorImage(Bitmap bitmap, float sx, float bhd, float ld) {// 参数分别是色相，饱和度和亮度
        Bitmap bmp = bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ColorMatrix sxMatrix = new ColorMatrix();// 设置色调
        sxMatrix.setRotate(0, sx);
        sxMatrix.setRotate(1, sx);
        sxMatrix.setRotate(2, sx);
        ColorMatrix bhdMatrix = new ColorMatrix();// 设置饱和度
        bhdMatrix.setSaturation(bhd);
        ColorMatrix ldMatrix = new ColorMatrix();// 设置亮度
        ldMatrix.setScale(ld, ld, ld, 1);
        ColorMatrix mixMatrix = new ColorMatrix();// 设置整体效果
        mixMatrix.postConcat(sxMatrix);
        mixMatrix.postConcat(bhdMatrix);
        mixMatrix.postConcat(ldMatrix);
        paint.setColorFilter(new ColorMatrixColorFilter(mixMatrix));// 用颜色过滤器过滤
        canvas.drawBitmap(bmp, 0, 0, paint);// 重新画图
        return bmp;
    }

    public static Bitmap getGreyImage(Bitmap old) {
        int width, height;
        height = old.getHeight();
        width = old.getWidth();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(old);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmp, 0, 0, paint);
        return bmp;
    }

    //设置图片对比度
    public static Bitmap ContrastPicture(Bitmap bmp,float contrastParm) {
        ColorMatrix cm = new ColorMatrix();
        float brightness = -25;  //亮度
        float contrast = contrastParm;        //对比度 default:2,0:全黑
        cm.set(new float[]{
                contrast, 0, 0, 0, brightness,
                0, contrast, 0, 0, brightness,
                0, 0, contrast, 0, brightness,
                0, 0, 0, contrast, 0
        });
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        //显示图片
        Matrix matrix = new Matrix();
        Bitmap createBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
        Canvas canvas = new Canvas(createBmp);
        canvas.drawBitmap(bmp, matrix, paint);
        return createBmp;
    }
}
