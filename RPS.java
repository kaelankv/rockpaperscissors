/**
 * Driver class for JanKenPo
 * User will select an option for a game of Jan Ken Po
 * Computer will also randomly select an option
 *
 * @author Kaelan Valencia
 */
public class RPS {

   public static void main(String[] args) {
      
      // Declare and initializes frame
      JanKenPo jkp = new JanKenPo();
      jkp.startGame();
      jkp.setVisible(true);
   
   } // closes main

} // closes class