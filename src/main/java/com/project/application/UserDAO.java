package com.project.application;

import com.project.user_database_app.User;
import org.apache.http.message.BasicNameValuePair;
import org.aspectj.apache.bcel.classfile.annotation.NameValuePair;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private String getQuery(List<BasicNameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (BasicNameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(String.valueOf(pair.getValue()), "UTF-8"));
        }

        return result.toString();
    }

public void putRequest(User user){

    try {
        URL url = new URL("http://localhost:8080/database/add");
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);

        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

        params.add(new BasicNameValuePair("firstName", user.getFirstName()));
        params.add(new BasicNameValuePair("lastName",user.getLastName()));
        params.add(new BasicNameValuePair("accountType",user.getAccountType()));
        params.add(new BasicNameValuePair("login",user.getLogin()));
        params.add(new BasicNameValuePair("password",user.getPassword()));
        params.add(new BasicNameValuePair("email",user.getEmail()));
        params.add(new BasicNameValuePair("deleteCode", user.getDeleteCode().toString()));
        OutputStream outputStream = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
        bufferedWriter.write(getQuery(params));
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStream.close();
        httpURLConnection.connect();
        System.out.println(httpURLConnection.getResponseMessage() + "\nCode: " + httpURLConnection.getResponseCode() + "\nURL: " + httpURLConnection.getURL());

    }catch(IOException e){
        e.printStackTrace();
    }
}

    public static void main(String[] args) {

    UserDAO userDAO = new UserDAO();

    User user = new User();
    user.setFirstName("Bolek");
    user.setLastName("Kolorek");
    user.setAccountType(UserTypes.ADMIN);
    user.setLogin("admin123");
    user.setPassword("password123");
    user.setEmail("email@wwpdas.com");
    user.setDeleteCode(666);

    userDAO.putRequest(user);

    }

}
