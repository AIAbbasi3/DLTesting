package com.example.arham.dltesting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReadDisplay extends AppCompatActivity {

    TextView textFile, textFileName, textFolder;
    TextView textFileName_WithoutExt, textFileName_Ext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_display);


        Button buttonPick = (Button)findViewById(R.id.buttonpick);
        textFile = (TextView)findViewById(R.id.textfile);
        textFolder = (TextView)findViewById(R.id.textfolder);
        textFileName = (TextView)findViewById(R.id.textfilename);

        textFileName_WithoutExt
                = (TextView)findViewById(R.id.textfilename_withoutext);
        textFileName_Ext
                = (TextView)findViewById(R.id.textfilename_ext);

        buttonPick.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent,PICKFILE_RESULT_CODE);

            }});
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch(requestCode){
            case PICKFILE_RESULT_CODE:
                if(resultCode==RESULT_OK){

                    String FilePath = data.getData().getPath();
                    String FileName = data.getData().getLastPathSegment();
                    int lastPos = FilePath.length() - FileName.length();
                    String Folder = FilePath.substring(0, lastPos);

                    textFile.setText("Full Path: \n" + FilePath + "\n");
                    textFolder.setText("Folder: \n" + Folder + "\n");
                    textFileName.setText("File Name: \n" + FileName + "\n");

                    filename thisFile = new filename(FileName);

                    textFileName_WithoutExt.setText("Filename without Ext: "
                            + thisFile.getFilename_Without_Ext());
                    textFileName_Ext.setText("Ext: " + thisFile.getExt());

                    if(thisFile.getExt().equalsIgnoreCase("zip")){
                        unzip(FilePath, Folder);
                    }

                }
                break;

        }
    }

    private class filename{

        String filename_Without_Ext = "";
        String ext = "";

        filename(String file){
            int dotposition= file.lastIndexOf(".");
            filename_Without_Ext = file.substring(0,dotposition);
            ext = file.substring(dotposition + 1, file.length());
        }

        String getFilename_Without_Ext(){
            return filename_Without_Ext;
        }

        String getExt(){
            return ext;
        }
    }

    private void unzip(String src, String dest){

        final int BUFFER_SIZE = 4096;

        BufferedOutputStream bufferedOutputStream = null;
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(src);
            ZipInputStream zipInputStream
                    = new ZipInputStream(new BufferedInputStream(fileInputStream));
            ZipEntry zipEntry;

            while ((zipEntry = zipInputStream.getNextEntry()) != null){

                String zipEntryName = zipEntry.getName();
                File file = new File(dest + zipEntryName);

                if (file.exists()){

                } else {
                    if(zipEntry.isDirectory()){
                        file.mkdirs();
                    }else{
                        byte buffer[] = new byte[BUFFER_SIZE];
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        bufferedOutputStream
                                = new BufferedOutputStream(fileOutputStream, BUFFER_SIZE);
                        int count;

                        while ((count
                                = zipInputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
                            bufferedOutputStream.write(buffer, 0, count);
                        }

                        bufferedOutputStream.flush();
                        bufferedOutputStream.close();
                    }
                }
            }
            zipInputStream.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
