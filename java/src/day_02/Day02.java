import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Day02 {

    public static void main(String[] args) {
        long safe = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("src/input"))) {
            String line = br.readLine();

            while (line != null) {
                String[] split = line.split(" ");
                List<Integer> report = new ArrayList<>();

                for (String number : split) {
                    report.add(Integer.parseInt(number));
                }
                boolean done = false;
                boolean descending = false;
                boolean first = true;

                for (int i = 0; i  < report.size() && !done; i++) {
                    if (first) {
                        if (report.get(i) > report.get(i + 1)) {
                            descending = true;
                        }

                        int diff = Math.abs(report.get(i) - report.get(i + 1));
                        if (diff == 0 || diff > 3) {
                            done = true;
                        }

                        first = false;
                    } else {
                        if (i + 1 < report.size() && descending) {
                            int diff = Math.abs(report.get(i) - report.get(i + 1));

                            if (report.get(i) < report.get(i + 1) || diff == 0 || diff > 3 ) {
                                done = true;
                            }
                        } else if (i +  1 < report.size() && !descending) {
                            int diff = Math.abs(report.get(i) - report.get(i + 1));

                            if (report.get(i) > report.get(i + 1) || diff == 0 || diff > 3 ) {
                                done = true;
                            }
                        }
                    }
                }

                if (!done) {
                    safe++;
                }

                line = br.readLine();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(safe);
        System.out.println(part2());
    }


    private static long part2() {
        long safe = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("src/input"))) {
            String line = br.readLine();

            while (line != null) {
                String[] split = line.split(" ");
                List<Integer> temp = new ArrayList<>();

                for (String number : split) {
                    temp.add(Integer.parseInt(number));
                }
                boolean isSafe = false;

                for (int j = 0; j < temp.size() && !isSafe; j++) {
                    boolean done = false;

                    boolean descending = false;
                    boolean first = true;

                    List<Integer> report = new ArrayList<>(temp);
                    report.remove(j);

                    for (int i = 0; i  < report.size() && !done; i++) {
                        if (first) {
                            if (report.get(i) > report.get(i + 1)) {
                                descending = true;
                            }

                            int diff = Math.abs(report.get(i) - report.get(i + 1));
                            if (diff == 0 || diff > 3) {
                                done = true;
                            }

                            first = false;
                        } else {
                            if (i + 1 < report.size() && descending) {
                                int diff = Math.abs(report.get(i) - report.get(i + 1));

                                if (report.get(i) < report.get(i + 1) || diff == 0 || diff > 3 ) {
                                    done = true;
                                }
                            } else if (i +  1 < report.size() && !descending) {
                                int diff = Math.abs(report.get(i) - report.get(i + 1));

                                if (report.get(i) > report.get(i + 1) || diff == 0 || diff > 3 ) {
                                    done = true;
                                }
                            }
                        }
                    }

                    if (!done) {
                        safe++;
                        isSafe = true;
                    }
                }



                line = br.readLine();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        // 437 too  low
        return safe;
    }
}
