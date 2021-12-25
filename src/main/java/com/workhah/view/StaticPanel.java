package com.workhah.view;

import com.workhah.controller.Operation;

import javax.swing.*;
import java.awt.*;

/**
 * @author WorkHaH
 * @date 2021/12/23
 **/
public class StaticPanel extends JPanel {
    JButton left;
    JButton right;
    JButton down;
    JButton rotate;
    JButton start;
    JButton login;
    JButton setting;

    public StaticPanel(Operation operation) {
        setBounds(0, 0, MainWin.WIN_WEIGTH, MainWin.WIN_HEIGHR);
        setLayout(null);
        // 设置背景，避免覆盖
        setOpaque(false);
        left = operation.left;
        right = operation.right;
        down = operation.down;
        rotate = operation.rotate;
        start = operation.start;
        login = operation.login;
        setting = operation.setting;
        left.setBounds(415, 370, 50, 50);
        right.setBounds(465, 370, 50, 50);
        down.setBounds(415, 420, 50, 50);
        rotate.setBounds(465, 420, 50, 50);
        setStart();
        start.setBounds(415, 475, 100, 50);
        login.setBounds(410, 640, 50, 50);
        setting.setBounds(460, 640, 50, 50);
        add(left);
        add(right);
        add(down);
        add(rotate);
        add(start);
        add(login);
//        add(setting);
    }

    // 设置开始按钮
    private void setStart() {
        start.setContentAreaFilled(false);
        start.setFocusPainted(false);
        start.setFont(new Font("华文新魏",Font.PLAIN,25));
        start.setForeground(Color.white);
        start.setFocusable(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 设置主屏
        g.setColor(new Color(150, 150, 150, 70));
        // 游戏主屏
        g.fillRect(30, 30, 360, 500);
        // 排名区
        g.fillRect(30, 560, 360, 130);
        // 边框
        g.setColor(new Color(255, 255, 255, 255));
        ((Graphics2D) g).setStroke(new BasicStroke(3L));
        g.drawRect(28, 28, 364, 504);
        g.drawRect(28, 558, 364, 134);

        // 右边排版
        g.setColor(new Color(150, 150, 150, 70));
        g.fillRect(405, 30, 120, 305);
        // 得分区
        g.setColor(new Color(2, 2, 2, 30));
        g.fillRect(410, 35, 110, 100);
        // 提示区
        g.fillRect(410, 165, 110, 165);
        // 操作
        g.fillRect(410, 365, 110, 165);


        ((Graphics2D) g).setStroke(new BasicStroke(1L));
        // 字体
        g.setColor(new Color(255, 255, 255, 255));
        g.setFont(new Font("楷体", Font.PLAIN, 23));
        g.drawString("荣誉榜", 40, 590);
        g.drawString("得分", 420, 65);
        g.drawString("下一个", 420, 195);

    }
}
