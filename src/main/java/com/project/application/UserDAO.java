package com.project.application;

import com.google.gson.Gson;
import com.project.user_database_app.User;
import org.apache.http.message.BasicNameValuePair;
import org.aspectj.apache.bcel.classfile.annotation.NameValuePair;

import java.io.*;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
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

public List<User> getAllUsers(){

        try {
            URL url = new URL("http://localhost:8080/database/all");
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod("GET");
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            Gson g = new Gson();
            User tempUserArray[]= g.fromJson(bufferedReader.readLine(),User[].class);
            List<User> tempUserList = Arrays.asList(tempUserArray);

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.connect();
            System.out.println(httpURLConnection.getResponseMessage() + "\nCode: " + httpURLConnection.getResponseCode() + "\nURL: " + httpURLConnection.getURL());

            return tempUserList;

        }catch(Exception exc){
            exc.printStackTrace();
        }
        return null;
    }

    public List<User> searchByLastName(String lastName){
        try{

            String query="?lastName="+lastName;

            URL url = new URL("http://localhost:8080/database/search"+query);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod("GET");
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            Gson g = new Gson();
            User tempUserArray[]= g.fromJson(bufferedReader.readLine(),User[].class);
            List<User> tempUserList = Arrays.asList(tempUserArray);

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.connect();

            return tempUserList;

        }catch(Exception exc){
            exc.printStackTrace();
        }
        return null;
    }

    public void deleteUserById(Integer id){
        try{
            String query = "?id="+id.toString();
            URL url = new URL("http://localhost:8080/database/delete" + query);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod("DELETE");
            InputStream inputStream = httpURLConnection.getInputStream();
            httpURLConnection.connect();
        }catch(IOException exc){
            exc.printStackTrace();
        }
    }

    public static void main(String[] args) {

    UserDAO userDAO = new UserDAO();

        System.out.println(userDAO.searchByLastName("Zenak").get(0).getFirstName());

    }

}
