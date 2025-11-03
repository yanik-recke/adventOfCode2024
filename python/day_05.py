from z3 import *


def parse():
    with open("inputs/input05", "r") as f:
        content = f.read()

    if content == "":
        raise Exception("Empty input, please add inputs/input05 file.")

    sections = content.split("\n\n")

    rules = []
    for line in sections[0].strip().split("\n"):
        a, b = line.split("|")
        rules.append((int(a), int(b)))

    updates = []
    for line in sections[1].strip().split("\n"):
        update = [int(x) for x in line.split(",")]
        updates.append(update)

    return rules, updates


def solve(part1=True):
    rules, updates = parse()
    total_sum = 0

    for update in updates:
        solver = Solver()

        all_variables = set()
        var_map = {}

        for x, y in rules:
            if x in update and y in update:
                if x not in var_map:
                    var_map[x] = Int(f'v{x}')
                if y not in var_map:
                    var_map[y] = Int(f'v{y}')

                # x|y <=> x < y
                solver.add(var_map[x] < var_map[y])
                all_variables.add(x)
                all_variables.add(y)

        for x in all_variables:
            solver.add(var_map[x] >= 0)

        if solver.check() == sat:
            model = solver.model()

            var_values = {}
            for x in all_variables:
                val = model.evaluate(var_map[x])
                var_values[x] = val.as_long()

            reverse_var_values = {v: k for k, v in var_values.items()}

            l = [var_values[v] for v in update]

            is_sorted = all(l[i] <= l[i + 1] for i in range(len(l) - 1))

            if is_sorted and part1:
                # Part 1: sum middle values of already sorted updates
                mid_val = l[len(l) // 2]
                original_num = reverse_var_values[mid_val]
                total_sum += original_num
            elif not is_sorted and not part1:
                # Part 2: sort and sum middle values of incorrectly ordered updates
                l.sort()
                mid_val = l[len(l) // 2]
                original_num = reverse_var_values[mid_val]
                total_sum += original_num

    return total_sum


if __name__ == "__main__":
    print(f"Part 1: {solve(part1=True)}")
    print(f"Part 2: {solve(part1=False)}")