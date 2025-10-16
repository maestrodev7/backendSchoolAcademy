package com.example.school.application.services;

import com.example.school.common.dto.SchoolDto;
import com.example.school.common.dto.UserDto;
import com.example.school.common.dto.UserSchoolDto;
import com.example.school.common.mapper.UserSchoolMapper;
import com.example.school.domain.entities.UserSchool;
import com.example.school.domain.repositories.SchoolRepositoryInterface;
import com.example.school.domain.repositories.UserRepositoryInterface;
import com.example.school.domain.repositories.UserSchoolRepositoryInterface;
import com.example.school.domain.services.UserSchoolServiceInterface;
import com.example.school.presenation.validators.UserSchoolRequestValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserSchoolService implements UserSchoolServiceInterface {

    private final UserSchoolRepositoryInterface repository;
    private final UserRepositoryInterface userRepository;
    private final SchoolRepositoryInterface schoolRepository;

    @Override
    public UserSchoolDto create(@NotNull UserSchoolRequestValidator request) {
        UserSchool entity = new UserSchool();
        entity.setUserId(request.getUserId());
        entity.setSchoolId(request.getSchoolId());
        entity.setRole(request.getRole());

        UserSchool saved = repository.save(entity);
        return UserSchoolMapper.toDto(saved);
    }

    @Override
    public UserSchoolDto getById(UUID id) {
        return repository.findById(id)
                .map(UserSchoolMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Relation User-School non trouvée"));
    }

    @Override
    public List<UserSchoolDto> getSchoolsWithAdmins() {
        return repository.findAll()
                .stream()
                .filter(us -> {
                    String role = us.getRole();
                    return role != null && (
                            role.equalsIgnoreCase("ADMIN") ||
                                    role.equalsIgnoreCase("PROMOTEUR")
                    );
                })
                .map(us -> {
                    UserSchoolDto dto = UserSchoolMapper.toDto(us);

                    userRepository.findById(us.getUserId())
                            .ifPresent(user -> {
                                UserDto userDto = new UserDto();
                                userDto.setId(user.getId());
                                userDto.setUsername(user.getUsername());
                                userDto.setFirstName(user.getFirstName());
                                userDto.setLastName(user.getLastName());
                                userDto.setEmail(user.getEmail());
                                userDto.setPhoneNumber(user.getPhoneNumber());
                                userDto.setRoles(user.getRoles());
                                dto.setUser(userDto);
                            });

                    schoolRepository.findById(us.getSchoolId())
                            .ifPresent(school -> {
                                SchoolDto schoolDto = new SchoolDto();
                                schoolDto.setId(school.getId());
                                schoolDto.setName(school.getName());
                                schoolDto.setAddress(school.getAddress());
                                schoolDto.setPhoneNumber(school.getPhoneNumber());
                                schoolDto.setEmail(school.getEmail());
                                dto.setSchool(schoolDto);
                            });

                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<UserSchoolDto> getSchoolsByUserId(UUID userId) {
        return repository.findByUserId(userId).stream()
                .map(us -> {
                    UserSchoolDto dto = UserSchoolMapper.toDto(us);

                    // Charger l'utilisateur
                    userRepository.findById(us.getUserId())
                            .ifPresent(user -> {
                                UserDto userDto = new UserDto();
                                userDto.setId(user.getId());
                                userDto.setUsername(user.getUsername());
                                userDto.setFirstName(user.getFirstName());
                                userDto.setLastName(user.getLastName());
                                userDto.setEmail(user.getEmail());
                                userDto.setPhoneNumber(user.getPhoneNumber());
                                userDto.setRoles(user.getRoles());
                                dto.setUser(userDto);
                            });

                    // Charger l'école
                    schoolRepository.findById(us.getSchoolId())
                            .ifPresent(school -> {
                                SchoolDto schoolDto = new SchoolDto();
                                schoolDto.setId(school.getId());
                                schoolDto.setName(school.getName());
                                schoolDto.setAddress(school.getAddress());
                                schoolDto.setPhoneNumber(school.getPhoneNumber());
                                schoolDto.setEmail(school.getEmail());
                                dto.setSchool(schoolDto);
                            });

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<UserSchoolDto> getAll() {
        return repository.findAll()
                .stream()
                .map(UserSchoolMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        if (repository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Relation User-School non trouvée");
        }
        repository.deleteById(id);
    }
}
