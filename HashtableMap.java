// --== CS400 File Header Information ==--
// Name: Colin Maggard
// Email: cmaggard@wisc.edu
// Group and Team: G30
// Group TA: Connor Bailey
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import java.util.NoSuchElementException;
import java.util.LinkedList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {

  protected LinkedList<Pair>[] table;
  protected int size;

  protected class Pair {

    public KeyType key;
    public ValueType value;

    public Pair(KeyType key, ValueType value) {
      this.key = key;
      this.value = value;
    }

  }

  @SuppressWarnings("unchecked")
  public HashtableMap(int capacity) {
    table = (LinkedList<Pair>[]) new LinkedList[capacity];
    this.size = 0;
  }

  // with default capacity = 32
  @SuppressWarnings("unchecked")
  public HashtableMap() {
    table = (LinkedList<Pair>[]) new LinkedList[32];
    this.size = 0;
  }

  @Override
  public void put(KeyType key, ValueType value) throws IllegalArgumentException {

    // error handling
    if (key == null || this.containsKey(key)) {
      throw new IllegalArgumentException("Invalid key");
    }

    int hashIndex = (Math.abs(key.hashCode())) % this.getCapacity();
    if (table[hashIndex] == null) {
      table[hashIndex] = new LinkedList<Pair>();
    }
    table[hashIndex].add(new Pair(key, value));
    this.size++;
    if (((double) this.size / getCapacity()) >= 0.75) {
      rehash();
    }
  }

  @Override
  public boolean containsKey(KeyType key) {
    int hashIndex = (Math.abs(key.hashCode())) % getCapacity();
    if (table[hashIndex] == null) {
      return false;
    }
    for (Pair pair : table[hashIndex]) {
      if (pair.key.equals(key)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public ValueType get(KeyType key) throws NoSuchElementException {

    if (!containsKey(key)) {
      throw new NoSuchElementException("Key does not exist.");
    } else {
      int hashIndex = (Math.abs(key.hashCode())) % getCapacity();
      for (Pair pair : table[hashIndex]) {
        if (pair.key.equals(key)) {
          return pair.value;
        }
      }
    }
    return null;
  }

  @Override
  public ValueType remove(KeyType key) throws NoSuchElementException {
    if (!containsKey(key)) {
      throw new NoSuchElementException("Key does not exist.");
    }
    int hashIndex = (Math.abs(key.hashCode())) % getCapacity();
    for (Pair pair : table[hashIndex]) {
      if (pair.key.equals(key)) {
        ValueType returned = pair.value;
        table[hashIndex].remove(pair);
        this.size--;
        return returned;
      }
    }
    return null;
  }

  @Override
  public void clear() {
    for (int i = 0; i < getCapacity(); i++) {
      this.table[i] = null;
    }
    this.size = 0;
  }

  @Override
  public int getSize() {
    return this.size;
  }

  @Override
  public int getCapacity() {
    return this.table.length;
  }

  @SuppressWarnings("unchecked")
  private void rehash() {
    LinkedList<Pair>[] table2 = (LinkedList<Pair>[]) new LinkedList[getCapacity() * 2];
    for (int i = 0; i < getCapacity(); i++) {
      if (table[i] != null) {
        for (Pair pair : table[i]) {
          int newHashIndex = (Math.abs(pair.key.hashCode())) % (getCapacity() * 2);
          if (table2[newHashIndex] == null) {
            table2[newHashIndex] = new LinkedList<Pair>();
          }
          table2[newHashIndex].add(pair);
        }
      }
    }
    table = table2;
  }

}
