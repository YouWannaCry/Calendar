package com.youwannacry.ywc.test.utils.mapper;


import com.youwannacry.ywc.test.dto.UserDto;
import com.youwannacry.ywc.test.entities.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CalendarMapper {
    @Autowired
    ModelMapper modelMapper;

    public CalendarMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public UserDto fromEntityToDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .nombre(userEntity.getNombre())
                .usuario(userEntity.getUsuario())
                .password(userEntity.getPassword())
                .access(userEntity.getAccess())
                .build();
    }

    public UserEntity fromDtoToEntity(UserDto userDto){
        return UserEntity.builder()
                .nombre(userDto.getNombre())
                .usuario(userDto.getUsuario())
                .password(userDto.getPassword())
                .access(userDto.getAccess())
                .build();
    }

    public List<UserDto> fromEntityListToDtoList(List<UserEntity> allUserList) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (UserEntity userEntity: allUserList
             ) {
            userDtoList.add(UserDto.builder()
                    .id(userEntity.getId())
                    .nombre(userEntity.getNombre())
                    .usuario(userEntity.getUsuario())
                    .access(userEntity.getAccess())
                    .build());
        }
        return userDtoList;
    }
}
