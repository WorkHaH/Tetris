package com.workhah.view;

import com.workhah.model.GameData;

import javax.swing.*;
import java.awt.*;

/**
 * @author WorkHaH
 * @date 2021/12/24
 **/
public class ScoreNext extends JPanel {
    private GameData gameData;
    // 提示方块的偏移
    int[] offset = new int[]{0, -10, 0, 0, -10, 0, 0};

    public ScoreNext(GameData gameData) {
        this.gameData = gameData;
        setOpaque(false);
        setBounds(405, 35, 120, 305);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setFont(new Font("楷体",Font.PLAIN,30));
        g.drawString(gameData.getScore(), 30, 75);
        for (Point p : GameData.BLOCKS[gameData.next].points) {
            g.setColor(gameData.colors[gameData.next]);
            g.fillRect((p.x) * 20 + 50 + offset[gameData.next], (p.y) * 20 + 200, 20, 20);
            g.drawImage(new ImageIcon("target/classes/imgs/mask1.png").getImage(), (p.x) * 20 + 50 + offset[gameData.next], (p.y) * 20 + 200, 20, 20, null);
        }

    }
}
