package com.nissan.template.mapper;

import com.nissan.template.domain.User;
import com.nissan.template.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDTO toDto(User user);
    User toEntity(UserDTO userDto);
}
