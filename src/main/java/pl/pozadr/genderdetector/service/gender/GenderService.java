package pl.pozadr.genderdetector.service.gender;


import pl.pozadr.genderdetector.dto.request.GenderRequestDto;

public interface GenderService {
    String checkGender(GenderRequestDto genderRequestDto);
}
