package com.workhah.model;

import java.awt.*;
import java.util.Random;

/**
 * @author WorkHaH
 * @date 2021/12/24
 **/
public class GameData {
    public Blocks blocks;
    // 偏移量
    public int x = 0;
    public int y = 0;
    // 存放格子数组
    public int[][] existBlocks;
    // 存放删除格子的数组
    public int[] deleteBlocks;
    // 随机因子
    Random random;
    // 下一个方块
    public int next;
    // 现在的格子编号
    public int current;
    // 分数
    public int score;
    // 游戏状态
    public int state;
    // 按钮文本信息
    public String[] stateText = new String[]{"开始", "暂停", "继续", "重来"};
    // 游戏记录
    public PlayerData playerData;
    public String nick;
    /**
     * 常量方块，包含每个方块之间的相对位置信息
     */
    public static Blocks[] BLOCKS = new Blocks[]{
            new Blocks(new int[]{-1, 0, 1, 1}, new int[]{0, 0, 0, 1}),
            new Blocks(new int[]{-1, 0, 1, 2}, new int[]{0, 0, 0, 0}),
            new Blocks(new int[]{-1, -1, 0, 1}, new int[]{0, 1, 0, 0}),
            new Blocks(new int[]{-1, 0, 0, 1}, new int[]{0, 0, 1, 1}),
            new Blocks(new int[]{0, 0, 1, 1}, new int[]{0, 1, 0, 1}),
            new Blocks(new int[]{-1, 0, 0, 1}, new int[]{1, 0, 1, 0}),
            new Blocks(new int[]{-1, 0, 0, 1}, new int[]{0, 0, 1, 0})
    };
    public Color[] colors = new Color[]{
            new Color(0, 0, 0, 100), new Color(255, 0, 0, 100),
            new Color(0, 255, 0, 100), new Color(0, 0, 255, 100), new Color(0, 255, 255, 100),
            new Color(255, 0, 255, 100), new Color(255, 255, 0, 100), new Color(150, 150, 150, 100)

    };

    public GameData() {
       init();
        nick = "";
       playerData = new PlayerData();
    }

    public void init() {
        existBlocks = new int[20][30];
        random = new Random();
        next = random.nextInt(7);
        initBlocks();
    }

    // 方块移动
    public boolean move(boolean isH, int step) {
        boolean isDelete = false;
        if (isH) {
            for (Point point : blocks.points) {
                if (point.x + x + step < 0 || point.x + x + step > 17
                        || existBlocks[point.x + x + step][point.y + y + 2] != 0) {
                    return false;
                }
            }
            x += step;
        } else {
            for (Point point : blocks.points) {
                if (point.y + y + step > 24 || existBlocks[point.x + x][point.y + y + 2 + step] != 0) {
                    saveBlocks();
                    isDelete = deleteTest();
                    if (isDelete) {
                        deleteLine();
                    }
                    if (isDead()) {
                        state = 3;
                    }
                    initBlocks();
                    return true;
                }
            }
            y += step;
        }
        return isDelete;
    }

    // 产生方块
    private void initBlocks() {
        x = 4;
        y = -2;
        deleteBlocks = new int[30];
        blocks = new Blocks(BLOCKS[next]);
        current = next;
        next = random.nextInt(7);
    }

    // 旋转
    public void rotate() {
        for (Point point : blocks.points) {
            int _x = -point.y + x;
            int _y = point.x + y;
            if (_x < 0 || _x > 17) {
                return;
            }
            if (_y > 24 || _y < -2) {
                return;
            }
            // 避免方块覆盖
            if (existBlocks[_x][_y + 2] != 0) {
                return;
            }
            // 正方形方块不旋转
            if (current == 4) {
                return;
            }
        }
        for (Point point : blocks.points) {
            int temp = point.x;
            point.x = -point.y;
            point.y = temp;
        }
    }

    // 保存方块
    public void saveBlocks() {
        for (Point point : blocks.points) {
            existBlocks[point.x + x][point.y + y + 2] = current + 1;
        }
    }

    // 检测是否可消行
    public boolean deleteTest() {
        boolean isDelete = false;
        boolean isEmpty;
        for (int i = 29; i >= 2; i--) {
            isEmpty = false;
            for (int j = 0; j < 18; j++) {
                if (existBlocks[j][i] == 0) {
                    isEmpty = true;
                    break;
                }
            }
            if (!isEmpty) {
                isDelete = true;
                deleteBlocks[i - 1] = deleteBlocks[i] + 1;
            } else {
                deleteBlocks[i - 1] = deleteBlocks[i];
            }
        }
        return isDelete;
    }

    // 消除行
    public void deleteLine() {
        for (int i = 29; i >= 2; i--) {
            for (int j = 0; j < 18; j++) {
                existBlocks[j][i + deleteBlocks[i]] = existBlocks[j][i];
            }
        }
        score += deleteBlocks[2] * 10;
    }

    // 死亡状态
    public boolean isDead() {
        for (int j = 0; j < 18; j++) {
            if (existBlocks[j][2] != 0) {
                return true;
            }
        }
        return false;
    }

    public String getScore() {
        return "" + score;
    }
}
