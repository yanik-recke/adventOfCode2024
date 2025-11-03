package day_10;

import helpers.Direction;
import helpers.HelperMethods;
import helpers.Position;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


// too low 777
// too low 748
// too low 795
public class Day_10 {


    public static void main(String[] args) {
        System.out.println(part1());
    }


    private static int part1() {

        int[][] map = HelperMethods.getInputAsTwoDimensionalArray("src/day_10/input");

        int sum = 0;

        Map<Position, Set<Position>> done = new HashMap<>();
        Set<Position> allPositions = new HashSet<>();
        Map<Position, Integer> branches = new HashMap<>();

        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                if (map[x][y] == 0) {
//                    Set<Position> temp = explore(new Position(x, y), map, done, allPositions, new HashSet<>());
//
//                    if (temp != null) {
//                        sum += temp.size();
//                    }

                    int temp = explorePart2(new Position(x, y), map, done, allPositions, new HashSet<>(), branches);
                    sum += temp;

                    System.out.println(x + "/" + y + " --- " + temp);
                }
            }
        }

        return sum;
    }

    private static Set<Position> explore(Position pos, int[][] map, Map<Position, Set<Position>> done, Set<Position> allPositions, Set<Position> reachedSet) {

        if (allPositions.contains(pos)) {
            return done.get(pos);
        }

        if (map[pos.getX()][pos.getY()] == 9) {
            Set<Position> temp = new HashSet<>();
            temp.add(pos);
            return temp;
        }

        int value = map[pos.getX()][pos.getY()];
        for (Direction d : Direction.values()) {
            Position neighbour = pos.getNeighbour(d);

            // check if inbounds
            if (neighbour.getX() >= 0 && neighbour.getY() >= 0 && neighbour.getX() < map.length && neighbour.getY() < map[0].length) {
                if (map[neighbour.getX()][neighbour.getY()] == value + 1) {
                    Set<Position> temp = explore(neighbour, map, done, allPositions, new HashSet<>());

                    if (temp != null) {
                        reachedSet.addAll(temp);
                    }

                }
            }

        }

        allPositions.add(pos);
        done.put(pos, reachedSet);

        return reachedSet;
    }

    private static int explorePart2(Position pos, int[][] map, Map<Position, Set<Position>> done, Set<Position> allPositions, Set<Position> reachedSet, Map<Position, Integer> branches) {
        int counter = 0;

        if (map[pos.getX()][pos.getY()] == 9) {

            return 1;
        }

        int value = map[pos.getX()][pos.getY()];
        for (Direction d : Direction.values()) {
            Position neighbour = pos.getNeighbour(d);

            // check if inbounds
            if (neighbour.getX() >= 0 && neighbour.getY() >= 0 && neighbour.getX() < map.length && neighbour.getY() < map[0].length) {
                if (map[neighbour.getX()][neighbour.getY()] == value + 1) {
                    counter += explorePart2(neighbour, map, done, allPositions, new HashSet<>(), branches);;
                }
            }

        }

        allPositions.add(pos);
        done.put(pos, reachedSet);
        branches.put(pos, counter);
        return counter;
    }

}
