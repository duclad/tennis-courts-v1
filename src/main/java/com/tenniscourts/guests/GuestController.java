package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
@RequestMapping("/guests")
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    @Operation(summary = "Register new guest", tags = "guests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created")})
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> registerGuest(@RequestBody RegisterGuestRequestDTO requestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.registerGuest(requestDTO).getId())).build();
    }

    @Operation(summary = "Update details for existing guest", tags = "guests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK|Success")})
    @PutMapping("/{guestId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateGuest(@PathVariable Long guestId, @RequestBody UpdateGuestRequestDTO requestDTO) {
        guestService.updateGuestDetails(guestId, requestDTO);
    }

    @Operation(summary = "Find all guests by name if provided or otherwise all guests", tags = "guests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success|OK", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GuestDTO.class))
                    )
            })})
    @GetMapping
    public ResponseEntity<List<GuestDTO>> findAllByName(@RequestParam(required = false) String guestName) {
        return ResponseEntity.ok(guestService.findGuestsByName(guestName));
    }

    @Operation(summary = "Find a guest its id ", tags = "guests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success|OK", content = @Content(schema = @Schema(implementation = GuestDTO.class)))})
    @GetMapping("/{guestId}")
    public ResponseEntity<GuestDTO> findByScheduleId(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.getGuestDetailsById(guestId));
    }

    @Operation(summary = "Delete a guest by its id ", tags = "guests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success|OK")})
    @DeleteMapping("/{guestId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGuest(@PathVariable Long guestId) {
        guestService.deleteGuestById(guestId);
    }
}
