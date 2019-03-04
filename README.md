# Number Guesser
## What is it ?
This is a "four digit number guessing game" that is played between an artificial intelligence and a user. </br>The user and AI chooses a random four digit number and receives a guess every turn. At the end of every turn who receives the guess gives the guesser a hint about the number. If the n digits are in correct place and m digits are in wrong place but exists in the sequence it hints the opponent with a string like +n-m.

## Compile and Run
- Install Java 9
  - On Windows, install the latest JRE 9 from Oracle. Previous versions of Java are not supported.
- Clone the source repository from Github.

  - On the gitbash terminal, enter:
```
git clone https://github.com/orkunsariz/NumberGuesser.git
```
  - You can either copy the repository to and IDE like Eclipse compile and run automatically or open a command prompt
    - Go the project's source folder, compile with javac and run
```
cd NumberGuesser\src
javac Main.java
java Main
```
## How to play ?
- In the beginning choose who goes first.
![Main Menu](https://github.com/orkunsariz/NumberGuesser/blob/master/tutorial_images/main.png)
- If it is your turn; guess the AI's number by typing your digits.
![User Guess](https://github.com/orkunsariz/NumberGuesser/blob/master/tutorial_images/user_guess.png)
- If it is AI's turn; give hint to the AI about its guess.
![User Hint](https://github.com/orkunsariz/NumberGuesser/blob/master/tutorial_images/ai_hint.png)
- If either of the opponents finds the correct number, game ends.
![Win](https://github.com/orkunsariz/NumberGuesser/blob/master/tutorial_images/win_screen.png)
## License
The source code of this project itself is licensed under the Academic Free License v3.0.
