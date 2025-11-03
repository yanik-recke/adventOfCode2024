use crate::day_06::Dir::{DOWN, LEFT, RIGHT, TOP};
use derivative::Derivative;
use std::collections::{HashMap, HashSet};

fn parse() -> LabMap {
    let lines: Vec<&str> = include_str!("../inputs/input06").split("\n").collect();
    let height = lines.len();
    let width = lines.first().map_or(0, |l| l.chars().count());

    LabMap {
        width,
        height,
        grid: lines.iter().map(|l| l.chars()).flatten().collect(),
    }
}

#[derive(Derivative, Eq, Hash, PartialEq)]
struct Pos {
    x: isize,
    y: isize,
    #[derivative(Hash = "ignore")]
    dir: Dir,
}

struct LabMap {
    width: usize,
    height: usize,
    grid: Vec<char>,
}

impl LabMap {
    fn get(&self, x: isize, y: isize) -> Option<char> {
        if x >= 0 && y >= 0 && x < self.width as isize && y < self.height as isize {
            Some(self.grid[y as usize * self.width + x as usize])
        } else {
            None
        }
    }
}

const GUARD: char = '^';

#[derive(Eq, PartialEq, Hash)]
enum Dir {
    TOP,
    RIGHT,
    DOWN,
    LEFT,
}

pub fn part1() -> i32 {
    let lab_map = parse();
    let idx = match lab_map.grid.iter().position(|&c| c == GUARD) {
        Some(i) => i,
        _ => panic!("Guard not found"),
    };

    let mut curr_pos = Pos {
        x: (idx % lab_map.width) as isize,
        y: (idx / lab_map.width) as isize,
        dir: TOP,
    };

    let dir_map: HashMap<Dir, (i8, i8)> = HashMap::from([
        (TOP, (0, -1)),
        (RIGHT, (1, 0)),
        (DOWN, (0, 1)),
        (LEFT, (-1, 0)),
    ]);

    let mut count = 1;

    let mut visited: HashSet<Pos> = HashSet::new();
    visited.insert(Pos {
        x: curr_pos.x,
        y: curr_pos.y,
        dir: TOP,
    });

    loop {
        let (dx, dy) = dir_map.get(&curr_pos.dir).expect("Direction not found");

        match lab_map.get(curr_pos.x + *dx as isize, curr_pos.y + *dy as isize) {
            None => break count,
            Some(c) => {
                if c == '#' {
                    match curr_pos.dir {
                        TOP => curr_pos.dir = RIGHT,
                        RIGHT => curr_pos.dir = DOWN,
                        DOWN => curr_pos.dir = LEFT,
                        LEFT => curr_pos.dir = TOP,
                    }
                } else {
                    curr_pos.x += *dx as isize;
                    curr_pos.y += *dy as isize;
                    match visited.insert(Pos {
                        x: curr_pos.x,
                        y: curr_pos.y,
                        dir: TOP,
                    }) {
                        true => count += 1,
                        _ => (),
                    }
                }
            }
        };
    }
}
