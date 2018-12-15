package com.example.lukas.shellgame;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileHelper
{
    public void WriteToFile(String fileName, String data, Context context)
    {
        File file = new File(fileName + ".txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        try
        {
            OutputStreamWriter writer = new OutputStreamWriter(context.openFileOutput(fileName + ".txt", Context.MODE_APPEND));
            //OutputStreamWriter writer = new OutputStreamWriter(context.openFileOutput(fileName + ".txt", Context.MODE_APPEND));
            writer.write(data);
            writer.close();
            //fileOutputStream.close();
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public boolean ExistFile(String fileName, Context context)
    {
        File file = context.getFileStreamPath(fileName + ".txt");

        return file.exists();

    }
    public String ReadFromFile(String fileName, Context context)
    {
        if(ExistFile(fileName,context))
            return "";

        String ret = "";
        try
        {
            FileInputStream fis = context.openFileInput(fileName + ".txt");
            InputStreamReader isr = new InputStreamReader(fis);
            if(isr != null)
            {
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                isr.close();
                fis.close();
                bufferedReader.close();
                ret = sb.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }
}
