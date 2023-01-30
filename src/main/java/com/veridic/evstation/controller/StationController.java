package com.veridic.evstation.controller;

import com.veridic.evstation.dto.StationDTO;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.veridic.evstation.service.StationService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
public class StationController {

    @Autowired
    StationService stationService;

    //CRUD OPERATIONS
    @GetMapping({"/get_stations", "/"})
    public ResponseEntity<String> getStations(@RequestParam(required = false) Integer limit,
                                              @RequestParam(required = false) String sort,
                                              @RequestParam(required = false) String param) {
        Pageable pageable = PageRequest.ofSize(limit).withSort(Sort.by(param).ascending());
        List<StationDTO> stations = stationService.getStations(pageable);
        return ResponseEntity.ok(stations.toString());
    }

    @PutMapping("/update_station")
    public ResponseEntity<String> updateStation(@RequestBody StationDTO stationDTO) throws Exception {
        if (stationDTO.getId() == null) {
            return ResponseEntity.badRequest().body("Please provide the station id to update it.");
        }
        StationDTO station = stationService.updateStation(stationDTO);
        return ResponseEntity.ok(station.toString());
    }

    @PostMapping({"/create_station", "/"})
    public ResponseEntity<String> createStation(@RequestPart("station") StationDTO stationDTO,
    @RequestPart("image") MultipartFile image) {

        StationDTO station = null;
        try {
            station = stationService.createStation(stationDTO, image);
        } catch (Exception e) {
            ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok("Station created successfully.");
    }

    @DeleteMapping("/delete_station")
    public ResponseEntity<String> deleteStation(@RequestBody StationDTO stationDTO) {
        if (stationDTO.getName() == null) {
            return ResponseEntity.badRequest().body("Please provide required parameters.");
        }
        stationService.deleteStation(stationDTO);
        return ResponseEntity.ok("successfully deleted");
    }

    /**
     *  2ndRequirement -  Get Station by ID
     */
    @GetMapping("show/{id}")
    public ResponseEntity<String> getStationById(@PathParam("id") Long id) {
        StationDTO station = stationService.getStationById(id);
        if (station == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No station with id:" + id);
        return ResponseEntity.ok(station.toString());
    }

    /**
     * 4thRequirement - Put station
     */
    @PutMapping("/{id}/edit")
    public ResponseEntity<String> editStation(@PathParam("id") Long id, @RequestBody StationDTO stationDTO) throws Exception {
        if (id == null) {
            return ResponseEntity.badRequest().body("Please provide the station id to update it.");
        }
        stationDTO.setId(id);
        StationDTO station = stationService.updateStation(stationDTO);
        return ResponseEntity.ok(station.toString());
    }

    /**
     * 5thRequirement - Delete Station
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> removeStation(@PathParam("id") Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().body("Please provide required parameters.");
        }
        stationService.deleteStation(id);
        return ResponseEntity.ok("successfully deleted");
    }
}
