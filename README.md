# Wasp Nest Simulation

A high-performance command-line simulation built with **Scala 3** that challenges players to neutralise a dynamic wasp nest. This project demonstrates professional engineering principles including Functional Recursion, Polymorphic Design, and Automated Quality Assurance.

**The Objective**: Systematically eliminate wasps from the nest. But beware-the nest is a connected ecosystem. Neutralising the Queen results in an immediate victory, but her guardians won't make it easy.

## Game Rules

The game starts with a nest of 14 wasps: 1 Queen, 5 Workers, and 8 Drones. When you use the fire command, the computer randomly picks a target for you. If you hit the Queen, she loses 7 hit points. Workers lose 10 hit points, and Drones lose 12 hit points. You win by either clearing every wasp one by one or by taking out the Queen, which clears the whole nest instantly.

## Game Overview

Players interact with a live-updating simulation of a 14-wasp nest. Every shot counts, and every victory is logged for historical performance tracking.

**Key Features**

- **Dynamic Nest Logic:** 14 unique entities (Queen, Workers, Drones) with individual health and defense stats.

- **Linked Death System**: Intelligent state synchronisation where the entire nest responds to the Queen's status.

- **Rapid Simulation**: A high-speed auto mode that uses optimised recursive algorithms to play the game instantly.

- **Automated Hall of Fame**: Persistent CSV logging that tracks Player Name, Time Taken, and Shots Fired.

## Technology Stack

- **Language**: Scala 3.8.1

- **Build Tool**: sbt 1.12.3

- **Testing**: MUnit

- **Runtime**: Java 21

## Quick Start

**Prerequisites**

- Java Development Kit (JDK) 21+

- sbt (Scala Build Tool)

## Installation

1. Clone the repository to your local machine.

2. Open your terminal in the wasp-game folder.

3. Compile and Run: sbt run

## Gameplay Commands

- _fire_: Targets a random living wasp and reduces its hit points
- _auto_: Activates a recursive simulation that plays until the Queen is defeated.
- _restart_: Resets the nest and starts a fresh session.
- _quit_: Exits the application and saves your score.

## Extra Features

**Auto-Fire Mode**: Instead of typing every shot, the auto command runs a high-speed simulation that keeps firing until the Queen is defeated. This shows off how fast the code can process the nest logic.

**Score Tracking**: Every time you finish a game, the program saves your performance to a file called results.csv. It tracks your name, how many shots you took, and the total time it took to win.

## Project Logic & Structure

**Entity Modeling**
Instead of writing repetitive code for each insect, I used a Trait called Wasp as a central blueprint. This allowed me to define shared behavior—like taking damage—while assigning unique properties to each type:

- **Queen**: 80 HP | 7 Damage per hit. Neutralising her ends the game.

- **Worker**: 68 HP | 10 Damage per hit.

- **Drone**: 60 HP | 12 Damage per hit.

**Keeping Hit Point at Zero**
To prevent mistakes where a wasp's points might drop into negative values, I used Math.max(0, currentHP - deduction). This forces the Hit Points to stop at exactly 0. If they try to go below 0, the code just keeps them at 0. This keeps the game clean and ensures that once a wasp is out, its hit point stays at a solid zero.

**Connected Death Condition**
The most critical logic is the Global Win Condition. Every wasp in the nest evaluates its "alive" status by checking its own health OR the Queen's health. This architectural choice ensures that the moment the Queen is neutralised, the entire nest state updates instantly without needing to manually loop through every entity.

**Automated Firing Engine**
The auto command utilises Tail-Recursion (@tailrec). This technique allows the computer to fire shots in rapid succession by calling the function repeatedly. By using this approach, the program remains "stack-safe," preventing memory crashes even during long simulations.

## Quality Assurance

Reliability is ensured through a comprehensive MUnit test suite. By automating the verification process, I ensured that new features do not break existing logic:

**Unit Verification**: Confirmed that each wasp type loses the precise amount of hit points intended.

**Boundary Analysis**: Verified that the hit point values are clamped at zero and never become negative.

**Integration Testing**: Validated that the "Queen Death Rule" successfully propagates through the entire list of 14 entities.

**Persistence Validation**: Ensured that the CSV logging system correctly interacts with the local file system.

**Run tests with**: sbt test

## License

© 2026 Desi Lee. Created for www.nology.io
