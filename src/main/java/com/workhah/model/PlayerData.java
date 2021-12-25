package com.workhah.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WorkHaH
 * @date 2021/12/25
 **/
public class PlayerData {
    Connection connection;
    Statement statement;
    List<String> nickList;
    List<Integer> scoreList;
    String currentNick = "未知玩家";
    String currentPwd = "";

    public PlayerData() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data/player.db");
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            System.out.println("驱动加载失败");
        } catch (SQLException e) {
            System.out.println("数据库连接失败");
        }
        createTable();
        getInfo();
    }

    public List<String> getNickList() {
        return nickList;
    }

    public List<Integer> getScoreList() {
        return scoreList;
    }

    public void add(int score) {
        String sql = "insert into players(nick,pwd,score) values('" + currentNick + "','"  + currentPwd + "'," + score + ")";
        try {
            statement.execute(sql);
            System.out.println("数据插入成功");
        } catch (SQLException e) {
            System.out.println("数据插入失败");
            e.printStackTrace();
        }
    }

    // 创建表
    public void createTable() {
        String sql = "create table players" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nick CHAR(20) NOT NULL," +
                "pwd CHAR(20) NOT NULL," +
                "score INT(5) NOT NULL)";
        try {
            statement.execute(sql);
            System.out.println("数据库创建成功");
        } catch (SQLException e) {
            System.out.println("数据库已经存在");
        }
    }

    public boolean login(String nick, String pwd) {
        String sql = "select pwd  from players where nick = '" + nick + "'";
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                if (!pwd.equals(resultSet.getString("pwd"))) {
                    return false;
                }
            }
            currentNick = nick;
            currentPwd = pwd;
            System.out.println("登陆成功");
        } catch (SQLException e) {
            System.out.println("登录失败");
        }
        return true;
    }

    // 获取荣誉榜信息
    public void getInfo() {
        String sql = "select nick,score from players order by score desc limit 4";
        nickList = new ArrayList<>();
        scoreList = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("nick") + resultSet.getInt("score"));
                nickList.add(resultSet.getString("nick"));
                scoreList.add(resultSet.getInt("score"));
            }
            System.out.println("数据查询成功");
        } catch (SQLException e) {
            System.out.println("数据查询失败");
        }
    }
}
