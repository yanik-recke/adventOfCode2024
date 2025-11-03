import java.io.*;
import java.util.*;

public class Day01 {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/input"))) {
            String line = br.readLine();

            while (line != null) {
                String[] split = line.split("  ");
                left.add(Integer.parseInt(split[0].strip()));
                right.add(Integer.parseInt(split[1].strip()));
                line = br.readLine();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        left.sort(Comparator.naturalOrder());
        right.sort(Comparator.naturalOrder());

        long distance = 0;

        for (int i = 0; i < left.size(); i++) {
            distance += Math.abs(left.get(i) - right.get(i));
        }

        System.out.println(distance);


        // Part 2

        Map<Integer, Integer> mapRight = new HashMap<>();
        Map<Integer, Integer> mapLeft = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/input"))) {
            String line = br.readLine();

            while (line != null) {
                String[] split = line.split("  ");
                int leftNum = Integer.parseInt(split[0].strip());

                if (mapLeft.containsKey(leftNum)) {
                    mapLeft.put(leftNum, mapLeft.get(leftNum) + 1);
                } else {
                    mapLeft.put(leftNum, 1);
                }

                int rightNum = Integer.parseInt(split[1].strip());

                if (mapRight.containsKey(rightNum) && left.contains(rightNum)) {
                    mapRight.put(rightNum, mapRight.get(rightNum) + 1);
                } else if (left.contains(rightNum)){
                    mapRight.put(rightNum, 1);
                }

                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long amount = 0;

        for (int num : mapRight.keySet()) {
            amount += (mapRight.get(num) * num) * mapLeft.get(num);
        }

        // 18567089
        // 256560295 too high
        System.out.println(amount);
    }
}