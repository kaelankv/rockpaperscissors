import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/**
 * Frame class for RPS.java
 * User will select rock/paper/scissors for a game of Jan Ken Po with the computer
 * Computer will also "select" a button (generates a random integer corresponding to a button)
 * Score is tracked and outputted to a file called "scores.txt"
 *
 * @author Kaelan Valencia
 */
public class JanKenPo extends JFrame {

   // User's buttons
   private JButton userRock = new JButton("ROCK");
   private JButton userPaper = new JButton("PAPER");
   private JButton userScissors = new JButton("SCISSORS");
   private JButton instructions = new JButton("INSTRUCTIONS");
   private JButton endGame = new JButton("END GAME");

   // Game title
   private JLabel title = new JLabel("JAN KEN PO!", JLabel.CENTER);
   // Win/loss label
   private JLabel winOrLoss = new JLabel("", JLabel.CENTER);
   
   // Displays the user's choice
   private JLabel userChoice = new JLabel("YOUR CHOICE: ", JLabel.CENTER);
   private JLabel userChoice2 = new JLabel("", JLabel.CENTER);
   // Shows the user which buttons are their options
   private JLabel userButtonsLabel = new JLabel("PICK ONE", JLabel.CENTER);
   
   // Displays the computer's choice
   private JLabel compChoice = new JLabel("CPU CHOICE: ", JLabel.CENTER);
   private JLabel compChoice2 = new JLabel("", JLabel.CENTER);
   
   // User score label
   private JLabel userScoreLabel = new JLabel("YOUR SCORE: ", JLabel.CENTER);
   // Computer score label
   private JLabel compScoreLabel = new JLabel("CPU SCORE: ", JLabel.CENTER);   
   // Amount of ties
   private JLabel tiesLabel = new JLabel("TIES: ", JLabel.CENTER); 

   // User score
   private int userScore = 0;
   // Computer score
   private int compScore = 0;
   // Number of ties
   private int tiesScore = 0;

   // Will use random numbers between 1 - 3 inclusive for rock, paper, scissors
   private Random randNum = new Random();
   private int compNum = 0;
   
   // JFrame size
   private final int WIDTH = 900;
   private final int HEIGHT = 600;
   
   // Assigns integers to rock, paper and scissors
   // 1 for rock, 2 for paper and 3 for scissors
   private final int ROCK = 1;
   private final int PAPER = 2;
   private final int SCISSORS = 3;
   
   // Keeps track of the amount of matches played when outputting to a file
   private int matchNums = 0;
   
   /**
    * Constructs and initializes the main frame
    * 
    */
   public JanKenPo() {
      
      // Initializes the JFrame
      this.setSize(WIDTH, HEIGHT);
      this.setTitle("JAN KEN PO");
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setVisible(true);
      this.setResizable(false);
   
   } // closes JanKenPo()
   
   /**
    * Initializes GUI
    * Adds elements in the main JFrame to be as user-friendly as possible
    *
    */
   public void startGame() {
   
      // Formatting the labels for user readablity
      title.setFont(new Font("Goudy Stout", Font.PLAIN, 50));
      userButtonsLabel.setFont(new Font("Arial Black", Font.PLAIN, 24));
      
      // Adds ActionListener to the buttons
      ActionListener ears = new MyListener();
      userRock.addActionListener(ears);
      userPaper.addActionListener(ears);
      userScissors.addActionListener(ears);
      instructions.addActionListener(ears);
      endGame.addActionListener(ears);
      
      // Contains the title of the game
      JPanel main = new JPanel();
      main.setLayout(new BorderLayout());
      main.add("North", title);
      
      // Contains win/loss labels and ties counter
      JPanel scores = new JPanel();
      scores.setLayout(new GridLayout(2, 1));
      scores.add(tiesLabel);
      scores.add(winOrLoss);
      tiesLabel.setFont(new Font("Arial Black", Font.BOLD, 29));
      tiesLabel.setForeground(Color.ORANGE);
      main.add("Center", scores);
      
      // Contains user choice and score
      JPanel userScores = new JPanel();
      userScores.setLayout(new GridLayout(3, 1));
      userScores.add(userScoreLabel);
      userScores.add(userChoice);
      userScores.add(userChoice2);
      userChoice.setFont(new Font("Arial Black", Font.BOLD, 29));
      userChoice.setForeground(Color.BLUE);
      userScoreLabel.setFont(new Font("Arial Black", Font.BOLD, 29));
      userScoreLabel.setForeground(Color.BLUE);
      main.add("West", userScores);
      
      // Contains comp choice and scores
      JPanel compScores = new JPanel();
      compScores.setLayout(new GridLayout(3,1));
      compScores.add(compScoreLabel);
      compScores.add(compChoice);
      compScores.add(compChoice2);
      compChoice.setFont(new Font("Arial Black", Font.BOLD, 29));
      compChoice.setForeground(Color.MAGENTA);
      compScoreLabel.setFont(new Font("Arial Black", Font.BOLD, 29));
      compScoreLabel.setForeground(Color.MAGENTA);
      main.add("East", compScores);
      
      // Contains user options
      JPanel userOptions = new JPanel();
      userOptions.add(userButtonsLabel);
      userOptions.add(userRock);
      userOptions.add(userPaper);
      userOptions.add(userScissors);
      userOptions.add(instructions);
      userOptions.add(endGame);      
      main.add("South", userOptions);
      
      // Add main frame
      this.add(main);
      
   } // closes StartGame()
   
   /**
    * Determines the winner of the game
    * 0 = tie, 1 = user wins, 2 = computer wins
    * 
    * @param int comp
    * @param int user
    * @return 0 for a tie
    * @return 1 for a user win
    * @return 2 for a computer win
    *
    */
   public int determineWinner(int comp, int user) {
   
      // Checks for ties
      if (comp == user) {
         return 0;
      } // closes if
      
      // Determines who wins
      // 1 = User wins
      // 2 = Computer wins
      // Paper beats rock
      if (comp == ROCK && user == PAPER) {
         return 1;
      } else if (comp == PAPER && user == ROCK) {
         return 2;
        // Scissors beats paper
      } else if (comp == PAPER && user == SCISSORS) {
         return 1;
      } else if (comp == SCISSORS && user == PAPER) {
         return 2;
        // Rock beats scissors
      } else if (comp == SCISSORS && user == ROCK) {
         return 1;
      } else if (comp == ROCK && user == SCISSORS) {
         return 2;
        // Default
      } else {
         return -1;
      } // closes if-else-if-else
   
   } // closes determineWinner
   
   /**
    * Converts random integers between 1 - 3 inclusive to Strings
    * 1 = ROCK, 2 = PAPER, 3 = SCISSORS
    *
    * @param int num
    * @return ROCK is 1
    * @return PAPER is 2
    * @return SCISSORS is 3
    *
    */
   public String convertInt(int num) {
   
      // Converts the assigned integers back to text
      switch (num) {
         // Rock = 1
         case ROCK:
            // Color to represent rock: gray
            compChoice2.setFont(new Font("Impact", Font.BOLD, 35));
            compChoice2.setForeground(Color.GRAY); 
            return "ROCK";
         // Paper = 2
         case PAPER:
            // Color to represent paper: cyan
            compChoice2.setFont(new Font("Impact", Font.BOLD, 35));
            compChoice2.setForeground(Color.CYAN.darker());
            return "PAPER";
         // Scissors = 3
         case SCISSORS:
            // Color to represent scissors: dark yellow
            compChoice2.setFont(new Font("Impact", Font.BOLD, 35));
            compChoice2.setForeground(Color.YELLOW.darker());
            return "SCISSORS";
         default:
            return "You are not supposed to see this.";
      } // closes switch
   
   } // closes convertInt()
   
   /**
    * Makes the computer choose an option through a random integer between 1 - 3
    * Also responsible for keeping track of score and file output
    *
    * @param int user
    *
    */
   public void randCompChoice(int user) {
   
      // Generate random integers between 1 - 3
      // 1 for rock, 2 for paper, 3 for scissors
      compNum = randNum.nextInt(3) + 1;
      
      try {
         // File handler for file output
         File scores = new File("Scores.txt");
         FileWriter scoresWriter = new FileWriter(scores, true);
         PrintWriter scoresPrinter = new PrintWriter(scoresWriter); 
               
         // Checks to see if random integer = 1, which is a win for the user
         if (determineWinner(compNum, user) == 1) {
            compChoice2.setText(convertInt(compNum));
            
            // Green text indicating a win
            winOrLoss.setText("YOU WIN! +1 POINT (USER)");
            winOrLoss.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
            winOrLoss.setForeground(Color.GREEN);
                     
            // Increment user score by 1
            userScore++;
            userScoreLabel.setText("USER SCORE: " + userScore);
            
            // Increment match numbers by 1 and outputs to a file called "scores.txt"
            matchNums++;
            scoresPrinter.write(matchNums + ". " + winOrLoss.getText() + 
                                "\nCurrent user score: " + userScore + 
                                "\nCurrent CPU score: " + compScore +
                                "\nTies: " + tiesScore + "\n\n");
            // Close scoresPrinter
            scoresPrinter.close();      
         } // closes if
         
         // Checks to see if random integer = 2, which is a loss for the user
         if (determineWinner(compNum, user) == 2) {
            compChoice2.setText(convertInt(compNum));
            
            // Red text indiciating a loss 
            winOrLoss.setText("YOU LOST! +1 POINT (CPU)");
            winOrLoss.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
            winOrLoss.setForeground(Color.RED);
            
            // Increment computer score by 1
            compScore++;
            compScoreLabel.setText("CPU SCORE: " + compScore);
            
            // Increment match numbers by 1 and outputs to a file called "scores.txt"
            matchNums++;
            scoresPrinter.write(matchNums + ". " + winOrLoss.getText() + 
                                "\nCurrent user score: " + userScore + 
                                "\nCurrent CPU score: " + compScore +
                                "\nTies: " + tiesScore + "\n\n");
            // Close scoresPrinter
            scoresPrinter.close();                    
         } // closes if
         
         // Checks to see if random integer = 0, which is a tie
         if (determineWinner(compNum, user) != 1 && determineWinner(compNum, user) != 2) {
            compChoice2.setText(convertInt(compNum));
            
            // Orange indicating a tie
            winOrLoss.setText("IT'S A TIE!");
            winOrLoss.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
            winOrLoss.setForeground(Color.ORANGE);
            
            // Increment ties by 1
            tiesScore++;
            tiesLabel.setText("TIES: " + tiesScore);
            
            // Increment match numbers by 1 and outputs to a file called "scores.txt"
            matchNums++;
            scoresPrinter.write(matchNums + ". " + winOrLoss.getText() + 
                                "\nCurrent user score: " + userScore + 
                                "\nCurrent CPU score: " + compScore +
                                "\nTies: " + tiesScore + "\n\n");
            // Close scoresPrinter
            scoresPrinter.close();                
         } // closes if
         
      } // closes try
      
      // Thrown if input does not exist, output error message
      catch (FileNotFoundException fnf) {
         System.out.println("Input does not exist");
      } // closes catch
      
      catch (IOException ioe) {
         System.out.println("Something went wrong!");;
      } // closes catch
      
   } // closes compChoice
   
   /**
    * Implements action listener for the buttons
    * Buttons include: userRock, userPaper, userScissors, instructions, endGame
    *
    */
   private class MyListener implements ActionListener {
      
      public void actionPerformed(ActionEvent event) {
         
         // If user presses rock, it shows "rock" under user choice label
         if (event.getSource() == userRock) {
            userChoice2.setText(convertInt(ROCK));
            userChoice2.setFont(new Font("Impact", Font.BOLD, 35));
            userChoice2.setForeground(Color.GRAY);
               
            // Display comp choice
            randCompChoice(ROCK);                                         
           // If user presses paper, it shows "paper" under user choice label
         } else if (event.getSource() == userPaper) {
            userChoice2.setText(convertInt(PAPER));
            userChoice2.setFont(new Font("Impact", Font.BOLD, 35));
            userChoice2.setForeground(Color.CYAN.darker());
                              
            // Display comp choice           
            randCompChoice(PAPER);
           // If user presses scissors, it shows "scissors" under user choice label
         } else if (event.getSource() == userScissors) {
            userChoice2.setText(convertInt(SCISSORS));
            userChoice2.setFont(new Font("Impact", Font.BOLD, 35));
            userChoice2.setForeground(Color.YELLOW.darker());
               
            // Display comp choice           
            randCompChoice(SCISSORS);          
         } // closes if-else-if
      
         // Display instructions of the game if the user hits the instructions button
         if (event.getSource() == instructions) {
            JOptionPane.showMessageDialog(new JFrame(),
                                  "Thank you for playing Jan Ken Po!\n" +
                                  "Here's how to play:\n" +
                                  "1. You will click a button labeled: ROCK, PAPER or SCISSORS\n" +
                                  "2. The computer will randomly pick one of these options as well\n" +
                                  "3. A win is determined by what object overpowers the other\n" +
                                  "4. Objects that overpower each other: \n" +
                                  "Rock --> Scissors\n" +
                                  "Scissors --> Paper\n" +
                                  "Paper --> Rock\n" +
                                  "5. If both participants pick the same object, then it is a draw\n" +
                                  "6. Click the \"Play Again\" button to play again",
                                  "How to play",
                                  JOptionPane.PLAIN_MESSAGE);
         } // closes if
         
         // Closes game
         if (event.getSource() == endGame) {
            JOptionPane.showMessageDialog(new JFrame(),
                                  "Thanks for playing! Here are your results!" +
                                  "\nFinal user score: " + userScore +
                                  "\nFinal CPU score: " + compScore +
                                  "\nTies: " + tiesScore +
                                  "\nBe sure to check scores.txt stored in the same folder as this program!",
                                  "Final scores",
                                  JOptionPane.PLAIN_MESSAGE);                                                     
            System.exit(0);                                 
         } // closes if
           
      } // closes actionPerformed
      
   } // closes MyListener
   
} // closes JanKenPo class