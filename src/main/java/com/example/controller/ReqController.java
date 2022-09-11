/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.controller;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

@RestController
public class ReqController {

  //@Value("${spring.datasource.url}")
  //private String dbUrl;

  //@Autowired
  //private DataSource dataSource;

  @GetMapping(value="/test/with_body", produces = {"application/json"})
  public List<String> testWithBody(String body) {
      List<String> list = new ArrayList<String>();
      list.add("/test/with_body");
      list.add(body);
      return list;
  }

  @GetMapping(value="/test/without_body", produces = {"application/json"})
  public List<String> testWithoutBody() {
      List<String> list = new ArrayList<String>();
      list.add("/test/without_body");
      list.add("no_body_here");
      return list;
  }

  @GetMapping(value="/hello2", produces = {"application/json"})
  public @ResponseBody List<String> hello2(@RequestBody String body) {
      List<String> list = new ArrayList<String>();
      list.add("hello2");
      list.add(body);
      return list;
  }

  @GetMapping(value="/hello3", produces = {"application/json"})
  public List<String> hello3(@RequestBody String body) {
      List<String> list = new ArrayList<String>();
      list.add("hello3");
      list.add(body);
      return list;
  }

  @GetMapping(value="/hello4", produces = {"application/json"})
  public String hello4(@RequestBody String body) {
      return body+"hello4";
  }  

  @RequestMapping(value="/hello5")
  public String hello5() {
      return "hello5";
  }

  @RequestMapping(value="/data/retrieve")
  public Map<String,String> getData() {
      return getParse();
  }

  private Map<String, String> getParse() {
      Map<String,String> map = new HashMap<String,String>();
      try {
        URL url = new URL("https://www.instagram.com/brandonpike/?hl=en");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        System.out.println(con.getResponseCode());
        System.out.println(con.getResponseMessage());
        System.out.println(con.getHeaderFields());
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
          System.out.println(inputLine);
          int found = inputLine.indexOf("edge_followed_by");  
          //System.out.println(found);  
          if (found>=0) {
            String fby = inputLine.substring(found-1, found+45);
            fby = fby.substring(fby.indexOf("{")+1, fby.indexOf("}"));
            int index = -1;
            for (int i=0; i<fby.length(); i++) {
              if (Character.isDigit(fby.charAt(i))){
                index=i;
                break;
              }
            }
            fby = fby.substring(index).trim();
            //System.out.println(fby);
            //self.data[username]["followers"] = followers
          }
        }
        in.close();
      } catch (Exception e) {
        System.out.println(e);
      }
      return map;
  }

  /*@RequestMapping("/db")
  String db(Map<String, Object> model) {
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
      stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
      ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

      ArrayList<String> output = new ArrayList<String>();
      while (rs.next()) {
        output.add("Read from DB: " + rs.getTimestamp("tick"));
      }

      model.put("records", output);
      return "db";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }

  @Bean
  public DataSource dataSource() throws SQLException {
    if (dbUrl == null || dbUrl.isEmpty()) {
      return new HikariDataSource();
    } else {
      HikariConfig config = new HikariConfig();
      config.setJdbcUrl(dbUrl);
      return new HikariDataSource(config);
    }
  }*/

}
