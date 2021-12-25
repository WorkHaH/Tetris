package com.workhah.view;

import com.workhah.controller.Operation;
import com.workhah.model.GameData;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @author WorkHaH
 * @date 2021/12/23
 **/
public class MainWin extends JFrame {
    private GamePanel gamePanel;
    private Container mainpane;
    private Operation operation;
    private GameData gameData;
    private StaticPanel staticPanel;
    private ScoreNext scoreNext;
    private PlayerPanel playerPanel;
    // 静态变量
    static final int WIN_WEIGTH = 560; // 窗口宽度
    static final int WIN_HEIGHR = 800; // 窗口高度

    public MainWin(Operation operation, GameData gameData) {
        this.operation = operation;
        this.gameData = gameData;
        setTitle("俄罗斯方块");
        setSize(WIN_WEIGTH, WIN_HEIGHR);
        setLocationRelativeTo(null);  //设置窗口在屏幕中心
        setLayout(null);
        setResizable(false);  // 窗口固定
        setDefaultCloseOperation(EXIT_ON_CLOSE);  // 关闭
        setBack();  // 设置背景

        mainpane = getLayeredPane();
        staticPanel = new StaticPanel(this.operation);
        mainpane.add(staticPanel);
        // 添加游戏方块
        setGamePanel();
        // 添加分数提示
        setScoreNext();
        // 添加游戏记录
        playerPanel = new PlayerPanel(gameData);
        mainpane.add(playerPanel);
        mainpane.setComponentZOrder(playerPanel, 0);
        // 设置图层顺序
        setZindex();
        // 获取按键权力
        setFocusable(true);
    }

    // 添加分数提示
    private void setScoreNext() {
        scoreNext = new ScoreNext(gameData);
        mainpane.add(scoreNext);
    }

    // 设置图层顺序
    private void setZindex() {
        mainpane.setComponentZOrder(staticPanel, 1);
        mainpane.setComponentZOrder(gamePanel, 0);
        mainpane.setComponentZOrder(scoreNext, 0);
    }

    // 设置背景图
    public void setBack() {
        ImageIcon background = new ImageIcon("target/classes/imgs/background.jpg");
        JLabel label = new JLabel(background);
        label.setBounds(0, 0, 560, 800);
        getContentPane().add(label);
    }

    // 添加游戏方块
    public void setGamePanel() {
        gamePanel = new GamePanel(this.gameData);
        mainpane.add(gamePanel);
    }

    // 获取流程控制按钮
    public JButton getStart() {
        return staticPanel.start;
    }

    // 获取游戏区
    public GamePanel getGamePanel() {
        return gamePanel;
    }

    // 获取分数提示
    public ScoreNext getScoreNext() {
        return scoreNext;
    }

    // 游戏结束后弹窗
    public void alert(int model) {
        // 存储游戏状态
        int _state = gameData.state;
        gameData.state = 2;
        AlertDialog.getInstance(this, gameData, model).openDialog();
        gameData.state = _state;
    }
}
