package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final TennisCourtRepository tennisCourtRepository;

    private final ScheduleMapper scheduleMapper;

    @Transactional
    public ScheduleDTO addSchedule(CreateScheduleRequestDTO createScheduleRequestDTO) {
        TennisCourt tennisCourt = tennisCourtRepository.findById(createScheduleRequestDTO.getTennisCourtId()).orElseThrow(() -> new EntityNotFoundException("Tennis court not found!"));
        Schedule schedule = new Schedule();
        schedule.setTennisCourt(tennisCourt);
        schedule.setStartDateTime(createScheduleRequestDTO.getStartDateTime());
        schedule.setEndDateTime(createScheduleRequestDTO.getStartDateTime().plusHours(1));
        scheduleRepository.save(schedule);
        return scheduleMapper.map(schedule);
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleRepository.findAllByStartDateTimeBetween(startDate, endDate).stream().map(scheduleMapper::map).collect(Collectors.toList());
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).map(scheduleMapper::map).orElseThrow(() -> new EntityNotFoundException("Schedule not found!"));
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
