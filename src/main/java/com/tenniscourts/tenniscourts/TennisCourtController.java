package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/tennis-courts")
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    @Operation(description = "Add a  new tennis court System ", tags = "tennis-courts")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Created")})
    @PostMapping
    public ResponseEntity<Void> addTennisCourt(@RequestBody CreateTennisCourtRequestDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    @Operation(description = "Get all tennis courts from the System ", tags = "tennis-courts")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success|OK", content = {
            @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = TennisCourtDTO.class))
            )
    })})
    @GetMapping
    public ResponseEntity<List<TennisCourtDTO>> getAllTennisCourts() {
        return ResponseEntity.ok(tennisCourtService.findAllTennisCourts());
    }

    @Operation(summary = "Get a  new tennis court by its id ", tags = "tennis-courts")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success|OK", content = @Content(schema = @Schema(implementation = TennisCourtDTO.class)))})
    @GetMapping("/{tennisCourtId}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @Operation(summary = "Get a  new tennis court by its id together with all the schedule slots", tags = "tennis-courts")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success|OK", content = @Content(schema = @Schema(implementation = TennisCourtDTO.class)))})
    @GetMapping("/{tennisCourtId}/details")
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(@PathVariable Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
}
