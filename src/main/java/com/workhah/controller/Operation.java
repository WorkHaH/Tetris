package com.workhah.controller;

import com.workhah.model.GameData;
import com.workhah.view.AlertDialog;
import com.workhah.view.ImgButton;
import com.workhah.view.MainWin;

import javax.swing.*;

import com.workhah.view.MainWin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author WorkHaH
 * @date 2021/12/24
 **/
public class Operation {
    private MainWin mainWin;
    private GameData gameData;
    public OpButton left;
    public OpButton right;
    public OpButton down;
    public OpButton rotate;
    public JButton start;
    public ImgButton login;
    public ImgButton setting;

    // 避免暂停时方块可操作
    abstract class OpButton extends ImgButton {
        OpButton(ImageIcon imageIcon) {
            super(imageIcon);
        }
        @Override
        public void onClick() {
            if (gameData.state == 1) {
                doClick();
            }
        }

        @Override
        abstract public void doClick();
    }

    public Operation() {
        left = new OpButton(new ImageIcon("target/classes/imgs/left.png")) {
            @Override
            public void doClick() {
                gameData.move(true, -1);
                mainWin.getGamePanel().repaint();
            }
        };
        right = new OpButton(new ImageIcon("target/classes/imgs/right.png")) {
            @Override
            public void doClick() {
                gameData.move(true, 1);
                mainWin.getGamePanel().repaint();
            }
        };
        down = new OpButton(new ImageIcon("target/classes/imgs/down.png")) {
            @Override
            public void doClick() {
                if (gameData.move(false, 1)) {
                    // 提示重画
                    mainWin.getScoreNext().repaint();
                }
                mainWin.getGamePanel().repaint();
            }
        };
        rotate = new OpButton(new ImageIcon("target/classes/imgs/rotate.png")) {
            @Override
            public void doClick() {
                gameData.rotate();
                mainWin.getGamePanel().repaint();
            }
        };

        start = new JButton("开始");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameData.state == 1) {
                    gameData.state = 2;
                } else {
                    gameData.state = 1;
                }
                start.setText(gameData.stateText[gameData.state]);
            }
        });

        login = new ImgButton(new ImageIcon("target/classes/imgs/login.png")) {
            @Override
            public void onClick() {
                mainWin.alert(AlertDialog.LOGI);
            }
        };
        setting = new ImgButton(new ImageIcon("target/classes/imgs/setting.png")) {
            @Override
            public void onClick() {

            }
        };
    }

    /*
        关联视图
     */
    public void setWin(MainWin mainWin) {
        this.mainWin = mainWin;
        this.mainWin.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    down.onClick();
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    left.onClick();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    right.onClick();
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    rotate.onClick();
                }
            }
        });
    }

    /*
        关联数据
     */
    public void setData(GameData gameData) {
        this.gameData = gameData;
    }
}
