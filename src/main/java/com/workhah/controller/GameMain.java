package com.workhah.controller;

import com.workhah.model.GameData;
import com.workhah.view.AlertDialog;
import com.workhah.view.MainWin;

import javax.management.monitor.GaugeMonitorMBean;

/**
 * @author WorkHaH
 * @date 2021/12/23
 **/
public class GameMain {
    public static void main(String[] args) {
        Operation operation = new Operation();  // 操作
        GameData gameData = new GameData(); // 加载数据
        // 数据和窗口关联
        MainWin mainWin = new MainWin(operation,gameData);
        // 操作区和窗口关联
        operation.setWin(mainWin);
        // 数据和操作区关联
        operation.setData(gameData);
        // 启用线程
        new AutoDown(gameData,mainWin).start();
        mainWin.setVisible(true);
    }
}

class AutoDown extends Thread {
    private GameData gameData;
    private MainWin mainWin;
    public AutoDown(GameData gameData,MainWin mainWin) {
        this.gameData = gameData;
        this.mainWin = mainWin;
    }
    @Override
    public void run() {
        while (true) {
            // 游戏中
            if (gameData.state == 1) {
                if (gameData.move(false, 1)) {
                    // 提示重画
                    mainWin.getScoreNext().repaint();
                }
                mainWin.getGamePanel().repaint();
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (gameData.state == 3){
                gameData.init();
                mainWin.alert(AlertDialog.OVER);
                mainWin.getStart().setText(gameData.stateText[gameData.state]);
                gameData.state = 4;
            } else {
                try {
                    sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
