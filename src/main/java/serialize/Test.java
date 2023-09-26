package serialize;

import java.io.*;

public class Test {
    public static void main(String[] args) {
        serializePerson();
        final var person = deserializePerson();
        System.out.println(person);
    }

    private static void serializePerson() {
        Person person = new Person();
        person.setAge(25);
        person.setName("name");
        person.setSex(0);
        try (ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream("Person.txt"))) {
            oo.writeObject(person);
            System.out.println("serialize success");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Person deserializePerson() throws RuntimeException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Person.txt"))) {
            return (Person) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
