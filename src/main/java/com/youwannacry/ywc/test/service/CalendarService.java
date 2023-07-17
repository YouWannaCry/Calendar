package com.youwannacry.ywc.test.service;

import com.youwannacry.ywc.test.dto.ErrorDto;
import com.youwannacry.ywc.test.dto.UserDto;
import com.youwannacry.ywc.test.entities.UserEntity;
import com.youwannacry.ywc.test.repository.CalendarRepository;
import com.youwannacry.ywc.test.utils.mapper.CalendarMapper;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CalendarService {

    CalendarRepository calendarRepository;
    CalendarMapper calendarMapper;

    public CalendarService(CalendarRepository calendarRepository, CalendarMapper calendarMapper){
        this.calendarRepository = calendarRepository;
        this.calendarMapper = calendarMapper;
    }

    public UserDto crearUsuario(UserDto userDto, String secretMessage) {
        List<ErrorDto> errorDtoList = new ArrayList<>();
        ErrorDto errorDto = new ErrorDto();

        if (!secretMessage.equals(this.calendarRepository.checkMessage("CrearUsuario"))){
            errorDto.setKey(secretMessage);
            userDto.setErrors(this.manejarErrores(errorDtoList, 1, errorDto));}

        if(userDto.getAccess() == null){
            userDto.setAccess(0);
        }

        if (this.calendarRepository.findUser(userDto.getUsuario()).getId() != null) {
            errorDto.setKey("Usuario repetido");
            userDto.setErrors(this.manejarErrores(errorDtoList, 2, errorDto)); }
        UserEntity userEntity = this.calendarMapper.fromDtoToEntity(userDto);
        if(errorDtoList.size()>0){
            userDto.setErrors(errorDtoList);
            return userDto;
        }
        this.calendarRepository.save(userEntity);
        return this.calendarMapper.fromEntityToDto(userEntity);
    }

    public List<UserDto> getAll() {
        List<UserEntity> allUserList = this.calendarRepository.findAll();
        return this.calendarMapper.fromEntityListToDtoList(allUserList);
    }

    public UserDto login(UserDto userDto) {
        List<ErrorDto> errorDtoList = new ArrayList<>();
        ErrorDto errorDto = new ErrorDto();

        if (userDto == null){
            this.manejarErrores(errorDtoList, 5, errorDto);
            if(errorDtoList.size()>0){
                userDto = UserDto.builder()
                        .errors(errorDtoList)
                        .build();
            }
            return userDto;
        }

        try{
            Integer id = this.calendarRepository.findUser(userDto.getUsuario()).getId();
            Optional<UserEntity> optionalUserEntity = this.calendarRepository.findById(id);
            if(optionalUserEntity.isPresent()){
                if(!optionalUserEntity.get().getPassword().equals(userDto.getPassword())){
                    errorDto.setKey("Contraseña equivocada");
                    this.manejarErrores(errorDtoList, 4, errorDto); }

                userDto = UserDto.builder()
                        .id(optionalUserEntity.get().getId())
                        .nombre(optionalUserEntity.get().getNombre())
                        .usuario(optionalUserEntity.get().getUsuario())
                        .access(optionalUserEntity.get().getAccess())
                        .build();
            }
            if(errorDtoList.size()>0){
                userDto.setErrors(errorDtoList);
                return userDto;
            }
        } catch (Exception e){
            e.printStackTrace();
            errorDto.setKey("No se encontro usuario");
            userDto.setErrors(this.manejarErrores(errorDtoList, 3, errorDto));
            return userDto;
        }
        return userDto;
    }

    private List<ErrorDto> manejarErrores(List<ErrorDto> errorDtoList, Integer caseCheck, ErrorDto errorDto) {
        ErrorDto newErrorDto;
        if(errorDto.getKey() == null){
            newErrorDto = ErrorDto.builder()
                    .key("Falta key")
                    .error("No hay key para evaluar el error")
                    .build();
            errorDtoList.add(newErrorDto);
            return errorDtoList;
        }

        switch (caseCheck) {
            case 1 -> {
                newErrorDto = ErrorDto.builder()
                        .key("Mensaje: " + errorDto.getKey())
                        .error("El mensaje es incorrecto")
                        .build();
                errorDtoList.add(newErrorDto);
            }
            case 2 -> {
                newErrorDto = ErrorDto.builder()
                        .key("Usuario repetido")
                        .error("Este usuario ya existe en la base de datos")
                        .build();
                errorDtoList.add(newErrorDto);
            }
            case 3 -> {
                newErrorDto = ErrorDto.builder()
                        .key("Usuario incorrecto")
                        .error("Este usuario no existe")
                        .build();
                errorDtoList.add(newErrorDto);
            }

            case 4 -> {
                newErrorDto = ErrorDto.builder()
                        .key("Contraseña incorrecta")
                        .error("La contraseña ingresada no es correcta")
                        .build();
                errorDtoList.add(newErrorDto);
            }

            case 5 -> {
                newErrorDto = ErrorDto.builder()
                        .key("Campo vacio")
                        .error("No puede dejar ningun campo vacio")
                        .build();
                errorDtoList.add(newErrorDto);
            }
        }

        return errorDtoList;
    }
}
