package com.workhah.view;

import com.workhah.model.GameData;

import javax.swing.*;
import java.awt.*;

/**
 * @author WorkHaH
 * @date 2021/12/25
 **/
public class PlayerPanel extends JPanel {
    GameData gameData;

    public PlayerPanel(GameData gameData) {
        this.gameData = gameData;
        setOpaque(false);
        setBounds(30, 600, 360, 130);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("楷体",Font.BOLD,18));
        g.setColor(new Color(100,100,100));
        int _y = 20;
        for (String nick : gameData.playerData.getNickList()) {
            g.drawString(nick, 60, _y);
            _y += 20;
        }
        _y = 20;
        for (int score : gameData.playerData.getScoreList()) {
            g.drawString("" + score, 200, _y);
            _y += 20;
        }
    }
}
