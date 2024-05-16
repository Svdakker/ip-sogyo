[![workflow status](https://github.com/Svdakker/ip-sogyo/actions/workflows/ip-sogyo-ci.yml/badge.svg)](https://github.com/Svdakker/ip-sogyo/actions/workflows/ip-sogyo-ci.yml) [![Coverage](https://github.com/Svdakker/ip-sogyo/blob/main/.github/badges/jacoco.svg)](https://github.com/Svdakker/ip-sogyo/actions/workflows/ip-sogyo-ci.yml)

# Project introduction
This project aims to deliver a web-based application for simulating a cascade of unit operations in a (bio)-process. Users can choose from a set of unit-operations and chain them together in a cascade. Inputs and outputs can be defined, as well as sizes and configurations (microorganism used, pH, temperature, duration, etc.). When running a process, the application will output graphs and tables displaying the predicted events in the unit operations over time. A final predicted cost over the entire process will be calculated and shown as well.

# Project background
When designing a (bio)-process, for example to produce a protein powder from agricultural waste streams, calculation of the economic feasibility and emission greenhouse gasses is extremely important. Using a (simplified) mathematical model to predict product yield, process duration, energy costs and emissions can be used to compare various choices in process setup and configuration. However, creating these models by hand can be quite an extensive process. This project aims to produce an accessible application to generate process models and compare various unit operations and process configurations in one place.

# Installation instructions

To use this project, clone the repository and change into the project root directory:

```
git clone git@github.com:Svdakker/ip-sogyo.git
```

Build the project using the gradle wrapper:

```
./gradlew clean build
```

Make sure Docker is installed and configured using the Docker documentation:

```
https://docs.docker.com/get-docker/
```

Run the following command from the root directory of the project:

```
docker compose up --build
```

Once the front-end and back-end are assembled, the application should be running at http://localhost:4173/

# Learning goals

## Technical
From a technical learning perspective, one of the goals of this project is to practice with writing the back-end domain logic and API in Kotlin. I have chosen to use Kotlin for its combination of Object-Oriented-Programming (OOP) and Functional-Programming (FP) possibilities. The option to work more FP oriented as opposed to languages like Java, might especially come in handy in the quite mathematically focused domain logic.

Furthermore, I would like to focus on using proper feature branches in git and a CI/CD pipeline to automatically integrate completed features into the main branch.

## Personal
On a personal level, I would like to apply SCRUM, to work in an organized and structured fashion. I would like to focus on creating issues for myself and working on only one issue at a time.
