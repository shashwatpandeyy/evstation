package com.veridic.evstation.service;

import com.veridic.evstation.dto.StationDTO;
import com.veridic.evstation.entity.Station;
import com.veridic.evstation.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StationService {

    @Autowired
    StationRepository stationRepository;
    public List<StationDTO> getStations(Pageable pageable) {
        Page<Station> stations = stationRepository.getStations(pageable);
        return stations.stream().map(this::stationToStationDTO).collect(Collectors.toList());
    }

    private StationDTO stationToStationDTO(Station station) {
        return StationDTO.builder().id(station.getId())
                .name(station.getName())
                .image(station.getImage())
                .pricing(station.getPricing())
                .address(station.getAddress()).build();
    }

    private Station stationDTOToStation(StationDTO stationDTO) {
        return Station.builder().id(stationDTO.getId())
                .name(stationDTO.getName())
                .image(stationDTO.getImage())
                .pricing(stationDTO.getPricing())
                .address(stationDTO.getAddress()).build();
    }
    public StationDTO updateStation(StationDTO stationDTO) throws Exception {
        validateStationDTO(stationDTO);
        return stationToStationDTO(stationRepository.save(stationDTOToStation(stationDTO)));
    }

    public StationDTO createStation(StationDTO stationDTO, MultipartFile image) throws Exception {
        stationDTO.setImage(image.getBytes());
        validateStationDTO(stationDTO);
        return stationToStationDTO(stationRepository.save(stationDTOToStation(stationDTO)));
    }

    private void validateStationDTO(StationDTO stationDTO) throws Exception {
        if (stationDTO.getName() == null || stationDTO.getPricing() == null || stationDTO.getAddress() == null) {
            throw new Exception("Please provide required parameters.");
        }
    }

    public StationDTO getStationById(Long id) {
        Optional<Station> optionalStation = stationRepository.findById(id);
        if (optionalStation.isEmpty()) return null;
        return stationToStationDTO(optionalStation.get());
    }

    public void deleteStation(StationDTO stationDTO) {
        stationRepository.delete(stationDTOToStation(stationDTO));
    }

    public void deleteStation(Long id) {
        stationRepository.deleteById(id);
    }

}
