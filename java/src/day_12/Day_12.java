package day_12;

import helpers.Direction;
import helpers.HelperMethods;
import helpers.Position;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Day_12 {

    public static void main(String[] args) {
        System.out.println(part1());
    }

    private static long part1() {
        char[][] map = HelperMethods.getInputAsTwoDimensionalCharArray("src/day_12/input");

        Set<Position> done = new HashSet<>();
        Queue<Position> toDo = new LinkedList<>();

        long price = 0;

        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                Position pos = new Position(x, y);
                int perimeterRegion = 0;

                if (!done.contains(pos)) {
                    toDo.add(pos);
                }

                int size = 0;

                while (!toDo.isEmpty()) {
                    Position curr = toDo.poll();
                    done.add(curr);
                    size++;
                    int perimeterCrop = 4;
                    char currCrop = map[x][y];
                    for (Direction d : Direction.values()) {
                        Position neighbour = curr.getNeighbour(d);

                        if (isInbounds(map.length, map[x].length, neighbour)) {
                            if (map[neighbour.getX()][neighbour.getY()] == currCrop) {
                                perimeterCrop--;

                                if (!done.contains(neighbour) && !toDo.contains(neighbour)) {
                                    toDo.add(neighbour);
                                }
                            }
                        }
                    }

                    perimeterRegion += perimeterCrop;
                }

                price += (long) perimeterRegion * size;

                done.addAll(toDo);
            }
        }

        return price;
    }


    private static long part2() {
        char[][] map = HelperMethods.getInputAsTwoDimensionalCharArray("src/day_12/input");

        Set<Position> done = new HashSet<>();
        Queue<Position> toDo = new LinkedList<>();

        long price = 0;

        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                Position pos = new Position(x, y);
                int perimeterRegion = 0;

                if (!done.contains(pos)) {
                    toDo.add(pos);
                }

                int borders = 0;

                int size = 0;

                while (!toDo.isEmpty()) {
                    Position curr = toDo.poll();
                    done.add(curr);
                    size++;
                    int perimeterCrop = 4;
                    char currCrop = map[x][y];
                    for (Direction d : Direction.values()) {
                        Position neighbour = curr.getNeighbour(d);

                        if (isInbounds(map.length, map[x].length, neighbour)) {
                            if (map[neighbour.getX()][neighbour.getY()] == currCrop) {
                                perimeterCrop--;

                                if (!done.contains(neighbour) && !toDo.contains(neighbour)) {
                                    toDo.add(neighbour);
                                }
                            }
                        }
                    }

                    perimeterRegion += perimeterCrop;
                }

                price += (long) perimeterRegion * size;

                done.addAll(toDo);
            }
        }

        return price;
    }

    private static boolean isInbounds(int length, int height, Position pos) {
        return pos.getX() >= 0 && pos.getY() >= 0 && pos.getX() < length && pos.getY() < height;
    }

}
