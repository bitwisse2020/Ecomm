package com.example.common.Mapper.service_user;

import com.example.common.Models.service_user.UserPrinciple;
import com.example.common.DTO.service_user.UserResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(UserPrinciple userPrinciple);

    default Page<UserResponse> toResponsePage(Page<? extends UserPrinciple> users){
        return users.map(this::toResponse);
    }


}
