package com.tenniscourts.guests;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;

    @Transactional
    public GuestDTO registerGuest(RegisterGuestRequestDTO requestDTO) {
        Guest guest = new Guest();
        guest.setName(requestDTO.getName());
        return guestMapper.map(guestRepository.save(guest));
    }

    @Transactional
    public void updateGuestDetails(Long guestId, UpdateGuestRequestDTO updateGuestRequestDTO) {
        Guest guest = guestRepository.findById(guestId).orElseThrow(() -> new EntityNotFoundException("Guest not found!"));
        guestMapper.update(guest, updateGuestRequestDTO);
        guestRepository.save(guest);
    }

    public GuestDTO getGuestDetailsById(Long guestId) {
        return guestRepository.findById(guestId).map(guestMapper::map).orElseThrow(() -> new EntityNotFoundException("Guest not found!"));
    }

    public List<GuestDTO> findGuestsByName(String name) {
        if (StringUtils.isBlank(name)) {
            return guestRepository.findAll().stream().map(guestMapper::map).collect(Collectors.toList());
        } else {
            return guestRepository.findAllByNameContainingIgnoreCase(name).stream().map(guestMapper::map).collect(Collectors.toList());
        }
    }

    @Transactional
    public void deleteGuestById(Long guestId) {
        guestRepository.deleteById(guestId);
    }
}
