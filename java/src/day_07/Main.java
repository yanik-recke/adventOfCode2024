package day_07;

import helpers.HelperMethods;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println(part1());
    }


    private static long part1() {
        String equation = "";
        List<String> input = HelperMethods.getInputAsListOfString("src/day_07/input");
        long sum = 0;
        for (String line : input) {
            long result = Long.parseLong(line.substring(0, line.indexOf(':')));

            String[] temp = line.substring(line.indexOf(":") + 2).split(" ");
            Queue<Long> remaining = new LinkedList<>();
            for (String number : temp) {
                remaining.add(Long.parseLong(number));
            }

            if (remaining.size() >= 2) {
                long tempResult = explore(remaining.poll(), remaining.poll(), result, new LinkedList<>(remaining));
                if (tempResult != -1) {
                    sum += tempResult;
                    equation = equation.concat(tempResult + " + ");
                }
                System.out.println(result + " --- " + tempResult);
            }

        }

        System.out.println(equation);
        return sum;
    }

    private static long explore(long num1, long num2, long result, Queue<Long> remaining) {
        long addition = exploreAddition(num1, num2, result, new LinkedList<>(remaining));

        if (addition != -1) {
            return addition;
        }

        long multiplication = exploreMultiplication(num1, num2, result, new LinkedList<>(remaining));

        if (multiplication != -1) {
            return multiplication;
        }

        return exploreConcatenation(num1, num2, result, new LinkedList<>(remaining));
    }

    private static long exploreAddition(long num1, long num2, long result, Queue<Long> remaining) {
        if (num1 + num2 == result) {
            // done if no numbers are remaining or last numbers are all 1
            if (remaining.isEmpty()) {
                return num1 + num2;
            } else {
                boolean isOne = true;
                while (!remaining.isEmpty() && isOne) {
                    isOne = remaining.poll() == 1;
                }

                if (isOne) {
                    return num1 + num2;
                }
            }
        } else if (num1 + num2 > result) {
            return -1;
        } else if (!remaining.isEmpty()){
            return explore(num1 + num2, remaining.poll(), result, new LinkedList<>(remaining));
        }

        return -1;
    }

    private static long exploreMultiplication(long num1, long num2, long result, Queue<Long> remaining) {
        if (num1 * num2 == result) {
            // done if no numbers are remaining
            if (remaining.isEmpty()) {
                return num1 * num2;
            } else {
                boolean isOne = true;
                while (!remaining.isEmpty() && isOne) {
                    isOne = remaining.poll() == 1;
                }

                if (isOne) {
                    return num1 * num2;
                }
            }
        } else if (num1 * num2 > result) {
            // womp womp
            return -1;
        } else if (!remaining.isEmpty()){
            return explore(num1 * num2, remaining.poll(), result, new LinkedList<>(remaining));
        }

        return -1;
    }

    private static long exploreConcatenation(long num1, long num2, long result, Queue<Long> remaining) {
        long newNumber = Long.parseLong(Long.toString(num1) + Long.toString(num2));

        if (newNumber == result) {
            if (remaining.isEmpty()) {
                return newNumber;
            } else {
                boolean isOne = true;
                while (!remaining.isEmpty() && isOne) {
                    isOne = remaining.poll() == 1;
                }

                if (isOne) {
                    return newNumber;
                }
            }
        } else if (newNumber > result) {
            return -1;
        } else if (!remaining.isEmpty()) {
            return explore(newNumber, remaining.poll(), result, new LinkedList<>(remaining));
        }


        return -1;
    }


}
