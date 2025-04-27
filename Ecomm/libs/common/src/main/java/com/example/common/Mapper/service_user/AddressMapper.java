package com.example.common.Mapper.service_user;

import com.example.common.DTO.service_user.AddressResponse;
import com.example.common.Models.service_user.BaseAddress;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring",
injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AddressMapper {
    AddressResponse toResponse (BaseAddress address);

    default Page<AddressResponse> toResponsePage(Page<? extends BaseAddress> addresses){
        return addresses.map(this::toResponse);
    }
}
