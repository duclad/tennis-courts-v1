package com.tenniscourts.guests;

import com.tenniscourts.reservations.ReservationDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class GuestDTO {
    private Long id;
    private String name;
    List<ReservationDTO> reservations;
}
