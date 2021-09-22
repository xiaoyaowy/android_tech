package com.example.admin.dashu_barcode;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.text.TextUtils;

public class BitmapPrinter {
    private int  mWidth = 384;
    private int  mHeight = 384;
    //图片
    private  Bitmap b ;
    //画布
    private Canvas canvas;
    //画笔
    private Paint mPaint;


    private Context mContext = null;

    public BitmapPrinter(Context context){
        this.mContext = context;
    }

    /**
     * 设置图片宽高
     * @param width
     * @param height
     */
    public void setBitmapSize(int width,int height){
        this.mHeight = height;
        this.mWidth = width;
        b = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(b);
        canvas.drawRGB(255,255,255);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(25);
    }


    public void addQrcode(Bitmap qrcode,int left,int  top){
        if(qrcode == null) return;
        canvas.drawBitmap(qrcode, left, top, mPaint);
    }

    public void addQrcode(String text,int width,int height,int left,int  top){
        if(TextUtils.isEmpty(text)) return;
        Bitmap mQr= BarcodeCreater.encode2dAsBitmap(text, width, height, 2);
        canvas.drawBitmap(mQr, left, top, mPaint);
    }

    public void addBarcode(Bitmap barcode,int left,int  top){
        if(barcode == null) return;
        canvas.drawBitmap(barcode, left, top, mPaint);
    }

    public void addBarcode(String text,int width,int height,int left,int  top){
        if(TextUtils.isEmpty(text)) return;
        Bitmap mQr= BarcodeCreater.creatBarcode(mContext,text, width, height,true, 1);
        canvas.drawBitmap(mQr, left, top, mPaint);
    }

    public void addText(String text,int textSize,int x,int y){
        if(TextUtils.isEmpty(text)) return;
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        canvas.drawText(text, x, y, paint);
    }


    public Bitmap getBitmap(){
        return b;
    }

    public  Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null){
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m,true);
            return bitmap;
        }
        return bitmap;
    }


    public void print(int concentration,int left){

        //mPrintQueue.addBmp(concentration, mLeft, mBitmap.getWidth(), mBitmap.getHeight(), printData);
        //mPosApi.printImage(concentration,left,b.getWidth(),b.getHeight(),)

    }


    private Bitmap GeneralComposingImage(){
/*
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(25);
        Bitmap mQr= BarcodeCreater.encode2dAsBitmap("1234567890", 160, 160, 2);
        canvas.drawBitmap(mQr, 0, 0, mPaint);
        canvas.drawText("产品名称:光明优酸乳", 160, 40, mPaint);
        canvas.drawText("追溯码:1234567890", 160, 80, mPaint);
        canvas.drawText("联系方式:0755-88888888", 160, 120, mPaint);
        canvas.drawText("上市时间:2018-08-09", 160, 160, mPaint);
        canvas.drawText("生产企业:深圳市光明乳业有限公司", 20, 200, mPaint);
        return  b;
        */
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(25);
        Bitmap mQr= BarcodeCreater.encode2dAsBitmap("1234567890", 160, 160, 2);
        canvas.drawBitmap(mQr, 224, 0, mPaint);
        canvas.drawText("产品名称:光明优酸乳", 0, 40, mPaint);
        canvas.drawText("追溯码:1234567890", 0, 80, mPaint);
        canvas.drawText("联系方式:0755-88888888", 0, 120, mPaint);
        canvas.drawText("上市时间:2018-08-09", 0, 160, mPaint);
        canvas.drawText("生产企业:深圳市光明乳业有限公司", 20, 200, mPaint);
        return  b;
    }


}
