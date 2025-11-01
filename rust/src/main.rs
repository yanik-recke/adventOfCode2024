mod day_04;
mod day_05;

fn main() {
    day_04::part1();
    day_04::part2();

    println!("{}", day_05::solve(true).expect("Panic"));
}
