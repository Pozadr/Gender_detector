package pl.pozadr.genderdetector.repository;


import pl.pozadr.genderdetector.util.Gender;

public interface GenderRepository {
    boolean isContaining(String input, Gender gender);
}
