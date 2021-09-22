package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.zyapi.pos.PosManager;
import android.zyapi.pos.PrinterDevice;
import android.zyapi.pos.interfaces.OnPrintEventListener;
import android.zyapi.pos.utils.BitmapTools;

import com.example.admin.dashu_barcode.BarcodeCreater;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PrintActivity extends AppCompatActivity {


    private PrinterDevice mPrinter = null;

    public static boolean ISFIRSTPRINT = true;

    private Bitmap mBitmap =null;

    Button button;

    String wuliao;

    String description;

    String content;

    String contentDesc;

    private static PrintActivity pThis = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PosManager.get().init(getApplicationContext(),"PDA");
        initViews();
        initPrint();
        String s_model = Build.MODEL;
        Intent intent = getIntent();
//        wuliao = intent.getStringExtra("wuliao");
//        description = intent.getStringExtra("description");
        content = intent.getStringExtra("content");
        contentDesc = intent.getStringExtra("contentDesc");
    }

    private void initViews(){
        setContentView(R.layout.activity_print);
        pThis = this;
        button = findViewById(R.id.pbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printLabelTestNEW();
            }
        });
    }

    private void initPrint() {
        mPrinter = PosManager.get().getPrinterDevice();
        mPrinter.setPrintEventListener(mPrinterListener); // Set up print listening
        //必须初始化
        mPrinter.init();
    }

    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private OnPrintEventListener mPrinterListener = new OnPrintEventListener() {
        @Override
        public void onEvent(int event) {
            switch (event) {
                case EVENT_UNKNOW:
                    showMsg("未知错误");
                    break;
                case  EVENT_NO_PAPER:
                    showMsg("打印机缺纸");
                    break;
                case EVENT_PAPER_JAM:
                    showMsg("打印机卡纸");
                    break;
                case EVENT_PRINT_OK:
                    showMsg("打印完成");
                    //printMix();
                    break;
                case EVENT_HIGH_TEMP:
                    showMsg("机芯温度过热");
                    break;
                case EVENT_LOW_TEMP:
                    showMsg("机芯温度过低");
                    break;
                case EVENT_CONNECTED:
                    showMsg("打印机连接完成");
                    break;
                case EVENT_CONNECT_FAILD:
                    showMsg("打印机连接失败");
                    break;
                case EVENT_STATE_OK:
                    showMsg("打印机状态正常");
                    break;
                case EVENT_CHECKED_BLACKFLAG:
                    showMsg("检测到黑标");
                    break;
                case EVENT_NO_CHECKED_BLACKFLAG:
                    showMsg("未检测到黑标");
                    break;
                case EVENT_TIMEOUT:
                    showMsg("打印机响应超时");
                    break;
                case EVENT_PRINT_FAILD:
                    showMsg("打印失败");
                    break;
                default:
                    showMsg("打印失败:" + event);
                    break;
            }
        }

        @Override
        public void onGetState(int cmd, int state) {

        }

        @Override
        public void onCheckBlack(int event) {
            switch (event) {
                case EVENT_CHECKED_BLACKFLAG:
                    showMsg("检测到黑标");
                    break;
                case EVENT_NO_CHECKED_BLACKFLAG:
                    showMsg("没有检测到黑标");
                    break;
                case EVENT_NO_PAPER:
                    showMsg("检测黑标时缺纸");
                    break;
            }

        }
    };

    private void printLabelTestNEW() {

        int concentration = 25 ;//打印浓度
        PrinterDevice.TextData tData_head = mPrinter.new TextData();//构造TextData实例
        if(ISFIRSTPRINT == false) {
            tData_head.addParam("1B4B15");//退步
        }
        else
        {
            tData_head.addParam("1B4A08");//进步

        }
        mPrinter.addText(concentration,tData_head);

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));

        PrinterDevice.TextData  tData_body =  mPrinter.new TextData();//构造TextData实例
        tData_body.addText("编码："+ content + "\n");
        tData_body.addText("描述：" + contentDesc + "\n");
        tData_body.addText("日期：" + formatter.format(date) + "\n");//添加打印内容
        //tData_body.addParam(mPrinter.PARAM_ALIGN_MIDDLE);
        tData_body.addParam(mPrinter.PARAM_TEXTSIZE_24);//设置两倍字体大小
        //tData_body.addParam(PrintQueue.PARAM_UNDERLINE);//设置两倍字体大小
        mPrinter.addText(concentration,tData_body);//添加到打印队列

        int  mWidth = 300;
        int  mHeight =60;
        mBitmap= BarcodeCreater.creatBarcode(PrintActivity.this,"UC000298012", mWidth, mHeight,true, 1);
        byte[] printData = BitmapTools.bitmap2PrinterBytes(mBitmap);
        mPrinter.addBmp(concentration, 30, mBitmap.getWidth(), mBitmap.getHeight(), printData);

        //添加黑标检测 走纸到黑标处再开始打印下一张数据
        mPrinter.addAction(PrinterDevice.PRINTER_CMD_KEY_CHECKBLACK);
        PrinterDevice.TextData tDataEnter = mPrinter.new TextData();//构造TextData实例
        tDataEnter.addText("\n\n");//多输出到撕纸口(print more paper for paper tearing)
        mPrinter.addText(concentration,tDataEnter);//添加到打印队列(add to print queue)
        mPrinter.printStart();//开始队列打印(begin to print)

        ISFIRSTPRINT = false;//重置首次打印变量值(reset the first printing variable)
    }
}