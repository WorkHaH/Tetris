package com.workhah.model;

import java.awt.*;

/**
 * @author WorkHaH
 * @date 2021/12/24
 **/
public class Blocks {
    public Point[] points;

    public Blocks(int[] xs,int[] ys) {
        points = new Point[4];
        for (int i = 0; i < 4; i++) {
            points[i] = new Point(xs[i], ys[i]);
        }
    }

    public Blocks(Blocks blocks) {
        points = new Point[4];
        for (int i = 0; i < 4; i++) {
            points[i] = new Point(blocks.points[i].x, blocks.points[i].y);
        }
    }
}
