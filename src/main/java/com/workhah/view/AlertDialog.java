package com.workhah.view;

import com.workhah.model.GameData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author WorkHaH
 * @date 2021/12/25
 **/
// 采用单例模式
public class AlertDialog extends JDialog {
    // 弹窗模式
    public static final int OVER = 0;
    public static final int LOGI = 1;
    public static final int SETT = 2;

    static AlertDialog alertDialog = null;
    GameData gameData;
    // 工厂模式 实现父类
    static Changer changer;
    // 弹窗中央画布
    JPanel mainPanel;
    // 弹窗按钮
    JLabel buttonLabel;
    MainWin mainWin;

    private AlertDialog(MainWin mainWin, GameData gameData) {
        super(mainWin, true);
        setSize(450, 330);
        setLocationRelativeTo(mainWin);
        this.gameData = gameData;
        this.mainWin = mainWin;
        // 背景图片
        JLabel bgLabel = new JLabel(new ImageIcon("target/classes/imgs/alert.png"));
        getContentPane().add(bgLabel);
        // 消除头边框
        setUndecorated(true);
        // 设置按钮字体
        buttonLabel = new JLabel("默认",JLabel.CENTER);
        buttonLabel.setFont(new Font("华文彩云", Font.BOLD,25));
        buttonLabel.setForeground(new Color(94,186,251));
        buttonLabel.setBounds(275,250,140,45);
        getLayeredPane().add(buttonLabel);
        // 中央画布
        mainPanel = new JPanel();
        mainPanel.setBounds(0,80,450,160);
        mainPanel.setLayout(null);
        // 设置透明
        mainPanel.setOpaque(false);
        getLayeredPane().add(mainPanel);
        // 添加点击事件
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getX() > 360 && e.getX() < 430) {
                    if (e.getY() > 40 && e.getY() < 80) {
                        closeDialog();
                    }
                }
                if (e.getX() > 280 && e.getX() < 420) {
                    if (e.getY() > 255 && e.getY() < 300) {
                        changer.onclick();
                    }
                }
            }
        });
    }

    public static AlertDialog getInstance(MainWin mainWin, GameData gameData, int model) {
        synchronized (AlertDialog.class) {
            if (alertDialog == null) {
                alertDialog = new AlertDialog(mainWin, gameData);
            }
            switch (model) {
                case OVER:
                    changer = new OverChanger(alertDialog);
                    break;
                case LOGI:
                    changer = new LoginChange(alertDialog);
                    break;
            }
            changer.changeView();
            return alertDialog;
        }
    }

    public void openDialog() {
        setVisible(true);
    }

    public void closeDialog() {
        setVisible(false);
    }
}

interface changeable {
    public void onclick();
    public void changeView();
}

abstract class Changer implements changeable {
}

// 关闭
class OverChanger extends Changer {
    AlertDialog alertDialog;

    OverChanger(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }

    @Override
    public void onclick() {
        alertDialog.gameData.playerData.add(alertDialog.gameData.score);
        alertDialog.gameData.playerData.getInfo();
        alertDialog.gameData.score = 0;
        alertDialog.mainWin.repaint();
        alertDialog.closeDialog();
    }

    @Override
    public void changeView() {
        alertDialog.buttonLabel.setText("确定");
        JLabel overlabel = new JLabel("游戏结束，分数为：" + alertDialog.gameData.score,JLabel.CENTER);
        overlabel.setFont(new Font("华文彩云", Font.BOLD,30));
        overlabel.setForeground(Color.BLACK);
        overlabel.setBounds(0,0,450,160);
        alertDialog.mainPanel.removeAll();
        alertDialog.mainPanel.add(overlabel);
    }
}

// 登录
class LoginChange extends Changer {
    AlertDialog alertDialog;
    JLabel noteLabel;
    JTextField nickField;
    JPasswordField pwdField;

    LoginChange(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }

    @Override
    public void onclick() {
        if (nickField.getText().equals("")) {
            noteLabel.setText("用户名不能为空");
        } else {
            if (alertDialog.gameData.playerData.login(nickField.getText(), String.valueOf(pwdField.getPassword()))) {
                alertDialog.gameData.nick = nickField.getText();
                alertDialog.closeDialog();
            } else {
                noteLabel.setText("该昵称已被注册，你需要输入正确密码");
            }
        }
    }

    @Override
    public void changeView() {
        alertDialog.buttonLabel.setText("登录/注册");
        // 昵称和密码
        JLabel nickLabel = new JLabel("昵称：",JLabel.CENTER);
        nickLabel.setFont(new Font("华文彩云", Font.BOLD,25));
        nickLabel.setBounds(70,20,80,30);
        JLabel pwdLabel = new JLabel("密码：",JLabel.CENTER);
        pwdLabel.setFont(new Font("华文彩云", Font.BOLD,25));
        pwdLabel.setBounds(70,70,80,30);
        // 昵称和密码的输入框
        nickField = new JTextField(alertDialog.gameData.nick, 20);
        pwdField = new JPasswordField(20);
        nickField.setBounds(170,20,170,30);
        pwdField.setBounds(170,70,170,30);
        // 提示框
        noteLabel = new JLabel(alertDialog.gameData.nick.equals("")?"":"你已经登录");
        noteLabel.setFont(new Font("楷体",Font.BOLD,15));
        noteLabel.setForeground(Color.RED);
        noteLabel.setBounds(70,120,300,30);
        // 清除
        alertDialog.mainPanel.removeAll();

        alertDialog.mainPanel.add(nickLabel);
        alertDialog.mainPanel.add(pwdLabel);
        alertDialog.mainPanel.add(nickField);
        alertDialog.mainPanel.add(pwdField);
        alertDialog.mainPanel.add(noteLabel);
    }
}