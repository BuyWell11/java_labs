package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import org.json.JSONObject;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String str = input.nextLine().replaceAll(" ", "_");
        input.close();
        try {
            URL url = new URL("https://en.wikipedia.org/w/api.php?action=parse&page=" + str + "&prop=text&formatversion=2&format=json");
            System.out.println(url);
            StringBuilder response = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line.trim());
            }
            bufferedReader.close();
            JSONObject json = new JSONObject(response.toString());
            JSONObject parse = json.getJSONObject("parse");
            String page = parse.getString("text").replaceAll("<[^>]*>", "");
            page = page.replaceAll("\\[edit\\]", "");
            System.out.println(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
