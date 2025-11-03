mod day_04;
mod day_05;
mod day_06;

fn main() {
    day_04::part1();
    day_04::part2();

    println!("D5: {}", day_05::solve(true).expect("Panic"));

    // 5530 too low
    println!("D6: {}", day_06::part1());
}
