package day_11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;


// too low 22938365706844
// 261952051690787
public class Day_11 {

    public static void main(String[] args) {

        System.out.println(part1());
        System.out.println(part2());
    }

    private static class Stone {
        private long value;

        private String valueAsString;

        private final long initialValue;

        public Stone(long value) {
            this.value = value;
            this.initialValue = (int) value;
            this.valueAsString = Long.toString(this.value);
        }

        public Stone(long value, long initialValue) {
            this.value = value;
            this.initialValue = initialValue;
            this.valueAsString = Long.toString(this.value);
        }

        public Stone(Stone stone) {
            this (stone.value, stone.initialValue);
        }

        private void rule1() {
            this.value = 1;
            this.valueAsString = "1";
        }

        private Stone rule2() {
            String temp = this.valueAsString;


            this.value = Long.parseLong(temp.substring(0, temp.length() / 2));
            this.valueAsString = Long.toString(this.value);

            return new Stone(Long.parseLong(temp.substring(temp.length() / 2)), (int) this.initialValue);
        }

        private void rule3() {
            this.value *= 2024;
            valueAsString = Long.toString(this.value);
        }

        @Override
        public int hashCode() {
            return (int) this.initialValue;
        }

        @Override
        public String toString() {
            return valueAsString;
        }
    }

    private static long part1() {
        String input = "";
        try (BufferedReader br = new BufferedReader(new FileReader("src/day_11/input"))) {
            input = br.readLine();

        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] temp = input.split(" ");

        List<Stone> stones = new ArrayList<>();

        for (String stone : temp) {
            stones.add(new Stone(Integer.parseInt(stone)));
        }

        Map<Long, Map<Integer, Integer>> cache = new HashMap<>();

        for (int blink = 0; blink < 25; blink++) {

            for (int i = 0; i < stones.size(); i++) {
                Stone stone = stones.get(i);

                if (stone.value == 0) {
                    stone.rule1();
                } else if(stone.valueAsString.length() % 2 == 0) {
                    Stone newStone = stone.rule2();
                    stones.add(++i, newStone);
                } else {
                    stone.rule3();
                }
            }


        }

        return stones.size();
    }

    private static long part2() {
        String input = "";
        try (BufferedReader br = new BufferedReader(new FileReader("src/day_11/input"))) {
            input = br.readLine();

        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] temp = input.split(" ");

        Map<Stone, Integer> stones = new HashMap<>();

        for (String stone : temp) {
            stones.put(new Stone(Integer.parseInt(stone)), 1);
        }

        long sum = 0;

        Map<Long, Map<Integer, Long>> cache = new HashMap<>();



        for (Stone stone : stones.keySet()) {
            sum += explore(0, stone, 75, cache);
        }

        return sum;
    }

    private static long explore(int blink, Stone stone, int maxBlink, Map<Long, Map<Integer, Long>> cache) {
        if (cache.containsKey(stone.value) && cache.get(stone.value).containsKey(maxBlink - blink)) {
            return cache.get(stone.value).get(maxBlink - blink);
        }

        if (blink == maxBlink) {
            return 1;
        }

        Stone initialStone = new Stone(stone.value, stone.initialValue);

        long sum = 0;

        if (stone.value == 0) {
            stone.rule1();

            sum += explore(blink + 1, stone, maxBlink, cache);
        } else if(stone.valueAsString.length() % 2 == 0) {
            Stone rightStone = stone.rule2();

            sum += explore(blink + 1, new Stone(stone), maxBlink, cache);
            sum += explore(blink + 1, rightStone, maxBlink, cache);
        } else {
            stone.rule3();

            sum += explore(blink + 1, stone, maxBlink, cache);
        }

        if (!cache.containsKey(initialStone.value)) {
            cache.put(initialStone.value, new HashMap<>());
        }

        cache.get(initialStone.value).put(maxBlink - blink, sum);

        return sum;
    }
}
