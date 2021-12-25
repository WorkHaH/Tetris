package com.workhah.view;

import com.workhah.model.GameData;

import javax.swing.*;
import java.awt.*;

/**
 * @author WorkHaH
 * @date 2021/12/24
 **/
public class GamePanel extends JPanel {
    private GameData gameData;

    public GamePanel(GameData gameData) {
        this.gameData = gameData;
        setOpaque(false);
        setBounds(30, 30, 360, 500);
    }

    @Override
    protected void paintComponent(Graphics g) {
        for (Point p : gameData.blocks.points) {
            g.setColor(gameData.colors[gameData.current]);
            g.fillRect((p.x + gameData.x) * 20, (p.y + gameData.y) * 20, 20, 20);
            g.drawImage(new ImageIcon("target/classes/imgs/mask1.png").getImage(), (p.x + gameData.x) * 20, (p.y + gameData.y) * 20, 20, 20, null);
        }
        for (int i = 29; i >= 2; i--) {
            for (int j = 0; j < 20; j++) {
                if (gameData.existBlocks[j][i] != 0) {
                    g.setColor(gameData.colors[gameData.existBlocks[j][i] - 1]);
                    g.fillRect(j * 20, (i - 2) * 20, 20, 20);
                    g.drawImage(new ImageIcon("target/classes/imgs/mask1.png").getImage(), j * 20, (i - 2) * 20, 20, 20, null);
                }
            }
        }
    }
}
