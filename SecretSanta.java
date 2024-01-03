import java.util.Scanner;
import java.util.List;
import java.util.Objects;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class SecretSanta {

  protected MapADT<Person, String> map;
  protected int numberOfParticipants;
  protected List<Person> participants;

  public SecretSanta() {
    this.map = new PlaceholderMap<>();
  }

  public static void main(String[] args) {
    SecretSanta secretSanta = new SecretSanta();
    secretSanta.run();
  }

  public void run() {
    Scanner scnr = new Scanner(System.in);
    Random rand = new Random();

    System.out.println("How many people are participating?");
    numberOfParticipants = scnr.nextInt();
    scnr.nextLine();
    participants = new LinkedList<Person>();

    // create linked list of participants
    for (int i = 0; i < numberOfParticipants; i++) {
      System.out.println("What is the name of participant " + (i + 1) + "?");
      String name = scnr.nextLine();
      // System.out
      // .println("What is the phone number of participant " + (i + 1) + "? (XXXXXXXXXX format)");
      // String number = scnr.nextLine();
      String number = "1";
      participants.add(new Person(name, number));
    }

    map = randomizePeople(participants, rand);

    // Check if 'map' is not null before printing
    if (map != null) {
      System.out.println(printMatches());
    } else {
      System.out.println("Error: Unable to generate Secret Santa assignments.");
    }
  }

  public static MapADT<Person, String> randomizePeople(List<Person> people, Random rand) {
    MapADT<Person, String> map;
    List<String> availableRecipients;

    while (true) {

      // reset data structures
      map = new HashtableMap<>();
      availableRecipients = new LinkedList<>();

      // list will be taken from
      for (Person person : people) {
        availableRecipients.add(person.getName());
      }
      Collections.shuffle(availableRecipients, rand);

      // Assign recipients sequentially
      int recipientIndex = 0;
      boolean validAssignments = true;

      for (Person giver : people) {
        String recipient = availableRecipients.get(recipientIndex);
        if (recipient.equals(giver.getName())) {
          validAssignments = false;
          break;
        }
        map.put(giver, recipient);

        recipientIndex = (recipientIndex + 1) % availableRecipients.size();
      }

      if (validAssignments) {
        break;
      }
    }

    return map;
  }



  /**
   * Used to turn the map into a formatted string
   */
  public String printMatches() {
    String output = "";
    for (Person giver : participants) {
      // the value in it is equal but not the same
      String recipient = map.get(giver);
      output += giver.getName() + "'s recipient: " + recipient + "\n";
    }
    return output;
  }

}


class Person {

  private String name;
  private String number;

  public Person(String name, String number) {
    this.name = name;
    this.number = number;
  }

  public String getName() {
    return this.name;
  }

  public String getNumber() {
    return this.number;
  }

}
