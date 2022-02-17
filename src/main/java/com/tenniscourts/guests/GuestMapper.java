package com.tenniscourts.guests;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GuestMapper {

    GuestDTO map(Guest guest);

    void update(@MappingTarget Guest guest, UpdateGuestRequestDTO updateGuestRequestDTO);
}
