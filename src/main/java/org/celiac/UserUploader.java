/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.celiac;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author tyanai
 */
public class UserUploader {

    static final File binaryFile = new File("C:\\Users\\tyanai\\Desktop\\guideusers.xlsx");

    public static void main(String[] args) {

        try {
            URL url = new URL("http://localhost:8080/gfguide/rest/upload_users");

            String encoding = Base64.getEncoder().encodeToString("tal:b1234".getBytes("utf-8"));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + encoding);
            connection.setRequestProperty("Content-Type", "application/octet-stream");

            OutputStream output = connection.getOutputStream();

            InputStream in = new FileInputStream(binaryFile);
            output.write(IOUtils.toByteArray(in));
            output.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            StringBuilder result = new StringBuilder();
            String callResult;
            while ((callResult = br.readLine()) != null) {
                result.append(callResult);
            }

            connection.disconnect();
            
            System.out.println(result.toString());

           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
