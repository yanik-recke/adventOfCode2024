package day_09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


// 89947706548 too low
// 6341711060162
public class Day_09 {

    public static void main(String[] args) {
        System.out.println(part2());
    }

    private static class CharExt {
        public long num;

        private long id;

        public boolean moved = false;

        public CharExt(long i, long id) {
            this.num = i;
            this.id = id;
        }

        public CharExt(long i, long id, boolean moved) {
            this.num = i;
            this.id = id;
            this.moved = moved;
        }
    }

    private static long part1() {
        String input = "";
        try (BufferedReader br = new BufferedReader(new FileReader("src/day_09/input"))) {
            input = br.readLine();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("read input");
        List<CharExt> disk = new ArrayList<>();

        boolean isFile = true;
        long id = 0;
        for (char c : input.toCharArray()) {
            int curr = c - '0';

            while (curr-- > 0) {
                if (isFile) {
                    disk.add(new CharExt(curr, id));
                } else {
                    disk.add(new CharExt(-1, -1));
                }
            }

            if (isFile) {
                id++;
            }

            isFile = !isFile;
        }

        boolean done = false;
        int lastFound = 0;
        int lastIdx = disk.size() - 1;
        CharExt c;

        boolean found = false;

        for (int i = lastIdx; i >= 0 && !done; i--) {
            if (disk.get(i).moved) {
                done = true;
            } else if (disk.get(i).num != -1) {
                c = disk.remove(i);
                c.moved = true;
                disk.add(i, new CharExt(-1, -1));
                found = false;
                for (int j = lastFound; j < disk.size() && !done && !found; j++) {
                    if (disk.get(j).num == -1) {
                        lastFound = j;
                        found = true;
                    } else if (disk.get(j).moved){
                        done = true;
                    }
                }

                if (!done && found) {
                    disk.remove(lastFound);
                    disk.add(lastFound, c);
                    lastFound++;
                }

            }
        }

        long sum = 0;
        for (int i = 0; i < disk.size(); i++) {
            if (disk.get(i).id != -1) {
                System.out.print(disk.get(i).id);
                sum += (long) i * (disk.get(i).id);
            } else {
                System.out.print(".");
            }
        }

        System.out.println();

        return sum;
    }


    private static long part2() {
        String input = "";
        try (BufferedReader br = new BufferedReader(new FileReader("src/day_09/input"))) {
            input = br.readLine();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("read input");
        List<CharExt> disk = new ArrayList<>();

        boolean isFile = true;
        long id = 0;
        for (char c : input.toCharArray()) {
            int curr = c - '0';

            while (curr-- > 0) {
                if (isFile) {
                    disk.add(new CharExt(curr, id));
                } else {
                    disk.add(new CharExt(-1, -1));
                }
            }

            if (isFile) {
                id++;
            }

            isFile = !isFile;
        }

        boolean done = false;
        int lastIdx = disk.size() - 1;
        CharExt c;

        boolean found = false;

        for (int i = lastIdx; i >= 0 && !done; i--) {
            if (disk.get(i).num != -1) {

                int requiredSpace = 0;
                int tempIdx = i;
                c = disk.get(i);

                while (tempIdx >= 0 && disk.get(tempIdx).id == c.id) {
                    requiredSpace++;
                    tempIdx--;
//                    disk.add(i--, new CharExt(-1, -1));
                }

                found = false;
                for (int j = 0; j < disk.size() && !done && !found && j < i; j++) {
                    if (disk.get(j).num == -1) {
                        // check if enough space exists
                        int k = j;
                        int emptySpace = 0;
                        while (disk.get(k).id == -1) {
                            k++;
                            emptySpace++;
                        }

                        if (emptySpace >= requiredSpace) {
                            // move
                            k = k - (emptySpace - requiredSpace);
                            while (j < k) {
                                disk.remove(j);
                                disk.add(j++, c);
                            }

                            while (i > tempIdx) {
                                disk.remove(i);
                                disk.add(i--, new CharExt(-1, -1));
                            }

                            found = true;
                        } else {
                            j = k;
                        }
                    }
                }

                i = tempIdx + 1;
            }
        }

        long sum = 0;
        for (int i = 0; i < disk.size(); i++) {
            if (disk.get(i).id != -1) {
                System.out.print(disk.get(i).id);
                sum += (long) i * (disk.get(i).id);
            } else {
                System.out.print(".");
            }
        }

        System.out.println();

        return sum;
    }

}
