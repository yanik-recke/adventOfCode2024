import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {

    private static class Pair {
        private final long left;
        private final long right;

        public Pair(long left, long right) {
            this.left = left;
            this.right = right;
        }

        public long getLeft() {
            return left;
        }

        public long getRight() {
            return right;
        }

        @Override
        public boolean equals(Object obj) {
            Pair p = (Pair) obj;
            return p.left == this.left && p.right == this.right;
        }
    }


    public static void main(String[] args) {

        final var mul = "mul(";
        long result = 0;

        List<Pair> pairs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/input"))) {
            String line = br.readLine();
            List<Character> numbers = new ArrayList<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));

            int lastIdx = 0;
            List<String> allMatches = new ArrayList<String>();
            while (line != null) {
                Matcher m = Pattern.compile("mul\\((\\d{1,3})\\,(\\d{1,3})\\)")
                        .matcher(line);

                while (m.find()) {
                    allMatches.add(m.group());
                }

                line = br.readLine();
            }

//            System.out.println(allMatches.size());

            long sum = 0L;
            for (String match : allMatches) {
                String temp = match.replaceAll("mul\\(", "");
                temp = temp.replaceAll("\\)", "");
                String[] split = temp.split(",");
                pairs.add(new Pair(Long.parseLong(split[0]), Long.parseLong(split[1])));
                sum += Long.parseLong(split[0]) * Long.parseLong(split[1]);
            }

//            System.out.println(sum);

            while (line != null) {
                boolean error = false;
                lastIdx = 0;
                boolean done = false;
                for (int i = 0; i < line.length() && !done; i++) {
                    int idx = line.indexOf(mul, lastIdx);
                    lastIdx = idx + 1;
                    if (idx == -1 || idx + mul.length() + 4 > line.length()) {
                        done = true;
                    } else {
                        boolean foundComma = false;
                        StringBuilder left = new StringBuilder();
                        idx += mul.length();

                        for (int j = 0; j < 4 && !foundComma && !error; j++) {
                            if (line.charAt(idx + j) == ',') {
                                foundComma = true;
                                idx = idx + j + 1;
                            } else if (!numbers.contains(line.charAt(idx + j))) {
                                error = true;
                            } else {
                                left.append(line.charAt(idx + j));
                            }
                        }
                        if (!error && foundComma && left.length() > 0) {
                            StringBuilder right = new StringBuilder();

                            boolean foundEnclosing = false;
                            for (int j = 0; j < 4 && !foundEnclosing && !error; j++) {
                                if (line.charAt(idx + j) == ')') {
                                    foundEnclosing = true;
                                    idx = idx + j + 1;
                                } else if (!numbers.contains(line.charAt(idx + j))) {
                                    error = true;
                                } else {
                                    right.append(line.charAt(idx + j));
                                }
                            }

                            if (!foundEnclosing || right.length() <= 0) {
                                error = true;
                            } else {
                                pairs.add(new Pair(Integer.parseInt(left.toString()), Integer.parseInt(right.toString())));
                            }

                            error = false;
                        }
                    }

                }

                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        part2(pairs);
    }

    private static void part2(List<Pair> list) {
        final var mul = "mul(";
        List<Pair> pairs = new ArrayList<>();
        long result = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("src/input"))) {
            String line = br.readLine();
            List<Character> numbers = new ArrayList<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));

            while (line != null) {
                boolean error = false;
                int lastIdx = 0;
                int currDoIdx = 0;
                int currDontIdx = 0;
                boolean isDo = true;

                boolean done = false;
                for (int i = 0; i < line.length() && !done; i++) {
                    error = false;
                    int idx = line.indexOf(mul, lastIdx);
                    currDoIdx = line.indexOf("do()", currDoIdx + 1);
                    currDontIdx = line.indexOf("don't()", currDontIdx + 1);

                    currDoIdx = currDoIdx == -1 ? Integer.MAX_VALUE : currDoIdx;
                    currDontIdx = currDontIdx == -1 ? Integer.MAX_VALUE : currDontIdx;

                    if (idx < currDoIdx) {
                        if (idx < currDontIdx) {
                            currDoIdx--;
                            currDontIdx--;
                        } else {
                            isDo = false;
                            currDoIdx--;
                        }
                    } else if (currDoIdx < currDontIdx) {
                        isDo = true;
                        currDontIdx--;
                    } else {
                        isDo = false;
                        currDoIdx--;
                    }

                    lastIdx = idx + 1;
                    if (!isDo) {

                    } else if (idx == -1 || idx + mul.length() + 4 > line.length()) {
                        done = true;
                    } else {
                        boolean foundComma = false;
                        StringBuilder left = new StringBuilder();
                        idx += mul.length();

                        for (int j = 0; j < 4 && !foundComma && !error; j++) {
                            if (line.charAt(idx + j) == ',') {
                                foundComma = true;
                                idx = idx + j + 1;
                            } else if (!numbers.contains(line.charAt(idx + j))) {
                                error = true;
                            } else {
                                left.append(line.charAt(idx + j));
                            }
                        }
                        if (!error && foundComma && left.length() > 0) {
                            StringBuilder right = new StringBuilder();

                            boolean foundEnclosing = false;
                            for (int j = 0; j < 4 && !foundEnclosing && !error; j++) {
                                if (line.charAt(idx + j) == ')') {
                                    foundEnclosing = true;
                                    idx = idx + j + 1;
                                } else if (!numbers.contains(line.charAt(idx + j))) {
                                    error = true;
                                } else {
                                    right.append(line.charAt(idx + j));
                                }
                            }

                            if (!foundEnclosing || right.length() <= 0) {
                                error = true;
                            } else {
                                System.out.println("mul(" + left.toString() + "," + right.toString() + ")");
                                pairs.add(new Pair(Integer.parseInt(left.toString()), Integer.parseInt(right.toString())));
                            }

                            error = false;
                        }
                    }

                }

                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Pair pair : pairs) {
//            System.out.println(pair.left + " * " + pair.right);
            result += (long) pair.left * pair.right;
        }

//        for (int i = 0; i < list.size(); i++) {
//            if (!list.get(i).equals(pairs.get(i))) {
//                System.out.println(i);
//            }
//        }

        // too high 105264641
        System.out.println(result);
    }

}
