use smtlib::backend::z3_binary::Z3Binary;
use smtlib::prelude::StaticSorted;
use smtlib::{Int, SatResultWithModel, Solver, Storage};
use std::collections::{HashMap, HashSet};

fn parse() -> (Vec<(u32, u32)>, Vec<Vec<u32>>) {
    let content = include_str!("../inputs/input05");
    let mut sections = content.split("\n\n");

    let rules: Vec<(u32, u32)> = sections
        .next()
        .unwrap()
        .lines()
        .map(|line| {
            let (a, b) = line.split_once("|").unwrap();
            (a.parse().unwrap(), b.parse().unwrap())
        })
        .collect();

    let updates: Vec<Vec<u32>> = sections
        .next()
        .unwrap()
        .lines()
        .map(|line| line.split(",").map(|s| s.parse().unwrap()).collect())
        .collect();

    (rules, updates)
}

pub fn solve(part1: bool) -> Result<u32, Box<dyn std::error::Error>> {
    let tup = parse();
    let rules: Vec<(u32, u32)> = tup.0;

    let updates = &tup.1;
    let mut sum: u32 = 0;

    for update in updates {
        let st = Storage::new();
        let mut solver: Solver<Z3Binary> = Solver::new(
            &st,
            Z3Binary::new("z3").expect("Failed to create Z3 binary"),
        )
        .expect("Failed to create solver");

        let mut all_variables: HashSet<u32> = HashSet::new();

        rules.iter().for_each(|(x, y)| {
            if update.contains(&x) && update.contains(&y) {
                let cx = Int::new_const(&st, &format!("v{}", x));
                let cy = Int::new_const(&st, &format!("v{}", y));
                solver.assert(cx.lt(cy)).expect("Oh no!");
                all_variables.insert(*x);
                all_variables.insert(*y);
            }
        });

        for x in &all_variables {
            let var = Int::new_const(&st, &format!("v{}", x));
            solver.assert(var.ge(0)).unwrap(); // var >= 0
        }

        let temp_sum: u32 = match solver.check_sat_with_model().expect("Error while solving") {
            SatResultWithModel::Sat(model) => {
                let mut var_values: HashMap<String, u32> = HashMap::new();
                for x in &all_variables {
                    let var = Int::new_const(&st, &format!("v{}", x));
                    if let Some(value) = model.eval(*&var) {
                        // Parse the value to u32
                        let val_str = value.to_string();
                        if let Ok(val) = val_str.parse::<u32>() {
                            var_values.insert(x.to_string(), val);
                        }
                    }
                }

                let reverse_var_values: HashMap<u32, String> =
                    var_values.iter().map(|(k, v)| (*v, k.clone())).collect();

                let mut l: Vec<u32> = update
                    .iter()
                    .map(|v| *var_values.get(&v.to_string()).unwrap())
                    .collect();

                if l.is_sorted() && part1 {
                    reverse_var_values
                        .get(&l[l.len() / 2])
                        .unwrap()
                        .parse::<u32>()
                        .unwrap()
                } else if !l.is_sorted() && !part1 {
                    l.sort();
                    reverse_var_values
                        .get(&l[l.len() / 2])
                        .unwrap()
                        .parse::<u32>()
                        .unwrap()
                } else {
                    0
                }
            }
            SatResultWithModel::Unsat => 0,
            SatResultWithModel::Unknown => 0,
        };

        sum += temp_sum;
    }

    Ok(sum)
}
