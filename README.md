# Knapsack Solver

This project implements two metaheuristics for the 0/1 Knapsack Problem:
- Genetic Algorithm (GA)
- Iterated Local Search (ILS)

## Compile

From the src directory:

make

## Run

From the src directory:

make run

You will be prompted to enter a seed value.

## Clean

Remove compiled classes:

make clean

## Data Setup

If data is missing, run:

make cleanData

Note: requires 7z installed.

## Structure

- src/ contains all source files
- executable/ contains compiled classes
- Knapsack Instances.7z contains input data

## Notes

- Program is seeded for reproducibility
- Runs without IDE
- Designed to match assignment requirements