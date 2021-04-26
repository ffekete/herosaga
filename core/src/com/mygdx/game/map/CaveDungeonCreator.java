package com.mygdx.game.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.*;

public class CaveDungeonCreator {

    private final static boolean DEBUG = false;

    private int deathLimit = 5;
    private int birthLimit = 2;
    private float chanceToStartAlive = 43;
    private int width;
    private int height;

    public Dungeon create(int steps, int width, int height, TextureRegion textureRegion) {

        Dungeon cellmap;
        do {
            this.width = width;
            this.height = height;

            long start = System.currentTimeMillis();
            //Create a new map
            cellmap = new Dungeon(width, height, textureRegion);
            //Set up the map with random values
            cellmap = initialiseMap(cellmap);

            //And now update the simulation for a set number of steps
            for (int i = 0; i < steps; i++) {
                cellmap = doSimulationStep(cellmap);
            }
            addFrame(cellmap);
            System.out.println("Elapsed: " + (System.currentTimeMillis() - start) + " ms");
        } while(refine(cellmap) > 2);

        return cellmap;
    }

    private int refine(Dungeon dungeon) {

        int[][] rooms = new int[width][height];
        int nextRoomNumber = 1;

        // repeat until end of dungeon reached
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                // find first tile that is not assigned to a room
                if(dungeon.getTile(i,j) == 0 && rooms[i][j] == 0) {
                    // fill empty tiles with next number
                    explore(dungeon, i,j,rooms, nextRoomNumber);
                    nextRoomNumber++;
                }
            }
        }

        // DEBUG
//        for(int i =0; i< width; i++) {
//            for (int j = 0; j < height; j++) {
//                System.out.print(rooms[i][j] + " ");
//            }
//            System.out.println();
//        }

        // link room with closest empty tile that is different room
        return nextRoomNumber;
    }

    public void explore(Dungeon targetDungeon, int x, int y, int[][] rooms, int value) {
        boolean[][] alreadyChecked = new boolean[targetDungeon.getWidth()][targetDungeon.getHeight()];

        if (x < 0 || y < 0 || x >= targetDungeon.getWidth() || y >= targetDungeon.getHeight() || targetDungeon.getTile(x, y) > 0) {
            return;
        }
        Deque<Vector2> points = new ArrayDeque<>();
        points.add(new Vector2(x, y));

        while (!points.isEmpty()) {
            Vector2 next = points.remove();
            int px = (int)next.x;
            int py = (int)next.y;

            if (px < 0 || py < 0 || px >= targetDungeon.getWidth() || py >= targetDungeon.getHeight() || alreadyChecked[px][py]) {

            } else if(targetDungeon.getTile(px, py) > 0) {
                alreadyChecked[px][py] = true;

            } else {
                alreadyChecked[px][py] = true;

                if (targetDungeon.getTile(px,py) == 0 && rooms[px][py] == 0) {
                    rooms[px][py] = value;
                }

                List<Vector2> newPoints = new ArrayList<>();

                if (py - 1 >= 0 && !alreadyChecked[px][py - 1])
                    newPoints.add(new Vector2(px, py - 1));

                if (py + 1 < targetDungeon.getHeight() - 1 && !alreadyChecked[px][py + 1])
                    newPoints.add(new Vector2(px, py + 1));

                if (px + 1 < targetDungeon.getWidth() - 1 && !alreadyChecked[px + 1][py])
                    newPoints.add(new Vector2(px + 1, py));

                if (px - 1 >= 0 && !alreadyChecked[px - 1][py])
                    newPoints.add(new Vector2(px - 1, py));

                Collections.shuffle(newPoints, new Random(System.currentTimeMillis()));
                points.addAll(newPoints);
            }

        }
        // no more visited area
    }

    private void addFrame(Dungeon map) {
        for(int i = 0; i < width; i++) {
            map.setTile(i, 0, 1);
            map.setTile(i, height - 1, 1);
        }

        for(int i = 0; i < height; i++) {
            map.setTile(0, i, 1);
            map.setTile(width - 1, i, 1);
        }
    }

    private Dungeon initialiseMap(Dungeon map){
        for(int x = 0; x< width; x++){
            for(int y = 0; y< height; y++){
                if(new Random().nextInt(100) < chanceToStartAlive){
                    map.setTile(x, y, 1);
                } else {
                    map.setTile(x,y,0);
                }
            }
        }
        return map;
    }

    private int countAliveNeighbours(Dungeon map, int x, int y){
        int count = 0;
        for(int i=-1; i<2; i++){
            for(int j=-1; j<2; j++){
                int neighbour_x = x+i;
                int neighbour_y = y+j;
                //If we're looking at the middle point
                if(i == 0 && j == 0){
                    //Do nothing, we don't want to add ourselves in!
                }
                //In case the index we're looking at it off the edge of the map
                else if(neighbour_x < 0 || neighbour_y < 0 || neighbour_x >= width || neighbour_y >= height){
                    count = count + 1;
                }
                //Otherwise, a normal check of the neighbour
                else if(map.getTile(neighbour_x, neighbour_y) == 1){
                    count = count + 1;
                }
            }
        }
        return count;
    }

    private Dungeon doSimulationStep(Dungeon oldMap){
        Dungeon newMap = new Dungeon(width, height, oldMap.textureRegion);
        //Loop over each row and column of the map
        for(int x=1; x<oldMap.getWidth() - 1; x++){
            for(int y=1; y<oldMap.getHeight()-1; y++){
                int nbs = countAliveNeighbours(oldMap, x, y);
                //The new value is based on our simulation rules
                //First, if a cell is alive but has too few neighbours, kill it.
                if(oldMap.getTile(x, y) == 0){
                    if(nbs < deathLimit){
                        newMap.setTile(x, y, 0);
                    }
                    else{
                        newMap.setTile(x, y, 1);
                    }
                } //Otherwise, if the cell is dead now, check if it has the right number of neighbours to be 'born'
                else{
                    if(nbs > birthLimit){
                        newMap.setTile(x, y, 1);
                    }
                    else{
                        newMap.setTile(x, y, 0);
                    }
                }
            }
        }
        return newMap;
    }

    private void fill(int x, int y, Dungeon oldmap, int originalValue, int value) {
        if(x >= oldmap.getWidth() || y >= oldmap.getHeight() || x < 0 || y < 0 || oldmap.getTile(x, y) != originalValue || oldmap.getTile(x,y) == value) {
            return;
        }

        oldmap.setTile(x, y, value);

        fill(x-1, y, oldmap, originalValue, value);
        fill(x-1, y-1, oldmap, originalValue, value);
        fill(x-1, y+1, oldmap, originalValue, value);
        fill(x, y-1, oldmap, originalValue, value);
        fill(x, y+1, oldmap, originalValue, value);
        fill(x+1, y-1, oldmap, originalValue, value);
        fill(x+1, y, oldmap, originalValue, value);
        fill(x+1, y+1, oldmap, originalValue, value);
    }
}