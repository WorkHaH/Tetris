package com.workhah.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author WorkHaH
 * @date 2021/12/24
 **/
public abstract class ImgButton extends JButton {
    public ImgButton(ImageIcon imageIcon) {
        // 背景透明
        setContentAreaFilled(false);
        // 更改图片
        setIcon(imageIcon);
        // 去除边框
        setBorder(null);
        // 取消截获按键
        setFocusable(false);
        // 添加按键点击事件
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onClick();
            }
        });
    }

    public abstract void onClick();
}
