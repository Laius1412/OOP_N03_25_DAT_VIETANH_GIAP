package Controller;

import Controller.interfaces.CrudPerson;
import Model.Person;
import java.util.ArrayList;

public class PersonService implements CrudPerson<Person, String> {
        private final ArrayList<Person> people = new ArrayList<>();

        @Override
        public void create(Person entity) {
                if (entity != null) {
                        people.add(entity);
                }
        }

        @Override
        public boolean update(String name, Person newData) {
                Person person = findById(name);
                if (person == null) return false;
                if (newData.getAddress() != null) person.setAddress(newData.getAddress());
                if (newData.getPhone() != 0) person.setPhone(newData.getPhone());
                if (newData.getName() != null) person.setName(newData.getName());
                if (newData.getGender() != null) person.setGender(newData.getGender());
                if (newData.getDob() != null) person.setDob(newData.getDob());
                if (newData.getDod() != null) person.setDod(newData.getDod());
                return true;
        }

        @Override
        public boolean delete(String name) {
                Person person = findById(name);
                if (person == null) return false;
                return people.remove(person);
        }

        @Override
        public Person findById(String name) {
                for (Person p : people) {
                        if (p.getName() != null && p.getName().equalsIgnoreCase(name)) {
                                return p;
                        }
                }
                return null;
        }

        @Override
        public ArrayList<Person> findAll() {
                return people;
        }
}


