package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schedules")
public class ScheduleController extends BaseRestController {

    private final ScheduleService scheduleService;

    @Operation(summary = "Create new schedule slot", tags = "schedules")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created")})
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> addScheduleTennisCourt(@RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(createScheduleRequestDTO).getId())).build();
    }

    @Operation(summary = "Find all schedule slots between given dates ", tags = "schedules")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success|OK", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ScheduleDTO.class))
                    )
            })})
    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(startDate, endDate));
    }

    @Operation(summary = "Find a schedule slot by its id ", tags = "schedules")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success|OK", content = @Content(schema = @Schema(implementation = ScheduleDTO.class)))})
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }
}
