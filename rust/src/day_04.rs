use std::collections::HashSet;
use std::io::{BufRead, BufReader};

fn parse() -> Vec<Vec<char>> {
    BufReader::new(include_str!("../inputs/input").as_bytes())
        .lines()
        .map(|line| line.expect("Failed to read line").chars().collect())
        .collect()
}

const DIRS: [(i32, i32); 8] = [
    (-1, -1),
    (0, -1),
    (1, -1),
    (-1, 0),
    (1, 0),
    (-1, 1),
    (0, 1),
    (1, 1),
];

pub fn part1() {
    let data = parse();
    let height = data.len();
    let width = data[0].len();

    let mut positions: HashSet<Pos> = HashSet::new();
    for (y, row) in data.iter().enumerate() {
        for (x, &ch) in row.iter().enumerate() {
            match ch {
                'X' => DIRS.iter().for_each(|(dx, dy)| {
                    positions.insert(Pos {
                        x,
                        y,
                        dir: (*dx, *dy),
                    });
                }),
                _ => (),
            }
        }
    }

    let g = Grid {
        data: data.into_iter().flatten().collect(),
        height,
        width,
    };

    let positions = ['M', 'A', 'S']
        .iter()
        .fold(positions, |acc, c| g.neighbours(*c, acc));

    println!("{:?}", positions.len());
}

pub fn part2() {
    let data = parse();
    let height = data.len();
    let width = data[0].len();

    let mut new_positions: HashSet<Pos> = HashSet::new();

    for (y, row) in data.iter().enumerate() {
        for (x, ch) in row.iter().enumerate() {
            match ch {
                'A' => {
                    new_positions.insert(Pos { x, y, dir: (0, 0) });
                }
                _ => (),
            }
        }
    }

    let g = Grid {
        data: data.into_iter().flatten().collect(),
        height,
        width,
    };

    println!("{}", g.count_xmas(new_positions))
}

struct Grid {
    data: Vec<char>,
    width: usize,
    height: usize,
}

#[derive(Eq, Hash, PartialEq)]
struct Pos {
    x: usize,
    y: usize,
    dir: (i32, i32),
}

impl Grid {
    fn get(&self, x: usize, y: usize) -> Option<char> {
        if x < self.width && y < self.height {
            Some(self.data[y * self.width + x])
        } else {
            None
        }
    }

    fn neighbours(&self, expected_char: char, positions: HashSet<Pos>) -> HashSet<Pos> {
        positions
            .iter()
            .flat_map(|p| {
                DIRS.into_iter().filter_map(move |(dx, dy)| {
                    let nx = (p.x as i32 + dx) as usize;
                    let ny = (p.y as i32 + dy) as usize;
                    self.get(nx, ny)
                        .filter(|&ch| ch == expected_char && p.dir == (dx, dy))
                        .map(|ch| (nx, ny, ch, p.dir))
                })
            })
            .map(|(x, y, _ch, dir)| Pos { x, y, dir })
            .collect()
    }

    fn count_xmas(&self, a_positions: HashSet<Pos>) -> u32 {
        a_positions
            .iter()
            .map(|pos| {
                let count = [(-1, -1), (1, 1), (-1, 1), (1, -1)]
                    .iter()
                    .enumerate()
                    .fold(['x'; 4], |mut acc, (idx, (dx, dy))| {
                        let nx = (pos.x as i32 + dx) as usize;
                        let ny = (pos.y as i32 + dy) as usize;
                        match self.get(nx, ny) {
                            None => (),
                            Some(c) => acc[idx] = c,
                        }
                        acc
                    });

                match count {
                    ['M', 'S', 'M', 'S']
                    | ['S', 'M', 'S', 'M']
                    | ['S', 'M', 'M', 'S']
                    | ['M', 'S', 'S', 'M'] => 1,
                    _ => 0,
                }
            })
            .sum()
    }
}
