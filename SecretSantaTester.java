import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.Scanner;
import java.util.List;
import java.util.Objects;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class SecretSantaTester {

  @Test
  public void oneHundredIterationsTest() {

    for (int i = 0; i < 100; i++) {
      Random rand = new Random();
      SecretSanta secretSanta = new SecretSanta();
      secretSanta.participants = new LinkedList<Person>();

      int total = 100;

      for (int j = 1; j < total + 1; j++) {
        secretSanta.participants.add(new Person("" + j, null));
      }

      secretSanta.map = SecretSanta.randomizePeople(secretSanta.participants, rand);
      secretSanta.numberOfParticipants = secretSanta.participants.size();
      System.out.println(secretSanta.printMatches());

      for (Person participant : secretSanta.participants) {
        if (participant.getName().equals(secretSanta.map.get(participant))) {
          Assertions.fail("Someone is matched to themselves.");
        }
      }
    }
  }

}
