package com.hntxrj.txerp.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringStream {

//String 转流
    public static InputStream getStream(String sInputString){

          if (sInputString != null && !sInputString.trim().equals("")){

          try{

             ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());

              return tInputStringStream;

          }catch (Exception ex){

                   ex.printStackTrace();

             }

            }

           return null;

    }

//流 转String
    public static String getString(InputStream tInputStream){

                     if (tInputStream != null){

                          try{

                               BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(tInputStream));

                              StringBuffer tStringBuffer = new StringBuffer();

                              String sTempOneLine = new String("");

                               while ((sTempOneLine = tBufferedReader.readLine()) != null){

                                   tStringBuffer.append(sTempOneLine);

                               }

                             return tStringBuffer.toString();

                         }catch (Exception ex){

                              ex.printStackTrace();

                         }

                     }

                     return null;

        }
}
