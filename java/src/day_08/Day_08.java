package day_08;

import helpers.HelperMethods;
import helpers.Position;

import java.util.*;


// too low 208
// too low 209
public class Day_08 {

    public static void main(String[] args) {
        System.out.println(part1());
    }

    private static int part1() {
        char[][] map = HelperMethods.getInputAsTwoDimensionalCharArray("src/day_08/input");
        Map<Character, List<Position>> antennas = new HashMap<>();
        Set<Position> allAntinodes = new HashSet<>();

        // creating the map
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                char curr = map[x][y];

                if (curr != '.') {
                    if (!antennas.containsKey(curr)) {
                        antennas.put(curr, new ArrayList<>());
                        antennas.get(curr).add(new Position(x, y));
                    } else {
                        antennas.get(curr).add(new Position(x, y));
                    }

                    // remove for part 1
                    allAntinodes.add(new Position(x, y));

                }
            }
        }

        // calculating antinodes for each frequency
        int amount = 0;

        for (List<Position> list : antennas.values()) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    calculateAntinodePart2(list.get(i), list.get(j), map.length, map[0].length, allAntinodes);
                    calculateAntinodePart2(list.get(j), list.get(i), map.length, map[0].length, allAntinodes);
                }
            }
        }

        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                if (allAntinodes.contains(new Position(x, y))) {
                    System.out.print("x");
                } else {
                    System.out.print(map[x][y]);
                }
            }
            System.out.println();
        }


        return allAntinodes.size();
    }

    private static void calculateAntinode(Position pos1, Position pos2, int length, int height, Set<Position> allAntinodes) {
        int x = pos1.getX() - pos2.getX();
        int y = pos1.getY() - pos2.getY();

        Position antinode = new Position(pos1.getX() + x, pos1.getY() + y);
        if (antinode.getX() >= 0 && antinode.getX() < length && antinode.getY() >= 0 && antinode.getY() < height) {
            allAntinodes.add(antinode);
        }

    }

    private static void calculateAntinodePart2(Position pos1, Position pos2, int length, int height, Set<Position> allAntinodes) {
        int xGap = pos1.getX() - pos2.getX();
        int yGap = pos1.getY() - pos2.getY();

        boolean done = false;

        for (int i = 1; !done; i++) {
            int x = pos1.getX() + xGap * i;
            int y = pos1.getY() + yGap * i;
            Position antinode = new Position(x, y);
            if (antinode.getX() >= 0 && antinode.getX() < length && antinode.getY() >= 0 && antinode.getY() < height) {
                System.out.println('a');
                allAntinodes.add(antinode);
            } else {
                done = true;
            }
        }


    }
}
