package com.example.virtual_threads_demo.mapper;
// src/main/java/com/example/vthreadsdemo/mapper/UserMapper.java


import com.example.virtual_threads_demo.dto.AddressDto;
import com.example.virtual_threads_demo.dto.CompanyDto;
import com.example.virtual_threads_demo.dto.GeoDto;
import com.example.virtual_threads_demo.dto.UserDto;
import com.example.virtual_threads_demo.model.AddressEntity;
import com.example.virtual_threads_demo.model.CompanyEntity;
import com.example.virtual_threads_demo.model.GeoEntity;
import com.example.virtual_threads_demo.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // ---------- DTO -> Entity ----------
    public static UserEntity toEntity(UserDto dto) {
        if (dto == null) return null;
        UserEntity entity = new UserEntity();
        entity.setName(dto.getName());
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setWebsite(dto.getWebsite());
        entity.setAddress(toEntity(dto.getAddress()));
        entity.setCompany(toEntity(dto.getCompany()));
        return entity;
    }

    public static AddressEntity toEntity(AddressDto dto) {
        if (dto == null) return null;
        AddressEntity entity = new AddressEntity();
        entity.setStreet(dto.getStreet());
        entity.setSuite(dto.getSuite());
        entity.setCity(dto.getCity());
        entity.setZipcode(dto.getZipcode());
        entity.setGeo(toEntity(dto.getGeo()));
        return entity;
    }

    public static GeoEntity toEntity(GeoDto dto) {
        if (dto == null) return null;
        GeoEntity entity = new GeoEntity();
        entity.setLat(dto.getLat());
        entity.setLng(dto.getLng());
        return entity;
    }

    public static CompanyEntity toEntity(CompanyDto dto) {
        if (dto == null) return null;
        CompanyEntity entity = new CompanyEntity();
        entity.setName(dto.getName());
        entity.setCatchPhrase(dto.getCatchPhrase());
        entity.setBs(dto.getBs());
        return entity;
    }

    // ---------- Entity -> DTO ----------
    public static UserDto toDto(UserEntity entity) {
        if (entity == null) return null;
        UserDto dto = new UserDto();
        dto.setId(entity.getId()); // DB id (or map the remote id if you store it)
        dto.setName(entity.getName());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setWebsite(entity.getWebsite());
        dto.setAddress(toDto(entity.getAddress()));
        dto.setCompany(toDto(entity.getCompany()));
        return dto;
    }

    public static AddressDto toDto(AddressEntity entity) {
        if (entity == null) return null;
        AddressDto dto = new AddressDto();
        dto.setStreet(entity.getStreet());
        dto.setSuite(entity.getSuite());
        dto.setCity(entity.getCity());
        dto.setZipcode(entity.getZipcode());
        dto.setGeo(toDto(entity.getGeo()));
        return dto;
    }

    public static GeoDto toDto(GeoEntity entity) {
        if (entity == null) return null;
        GeoDto dto = new GeoDto();
        dto.setLat(entity.getLat());
        dto.setLng(entity.getLng());
        return dto;
    }

    public static CompanyDto toDto(CompanyEntity entity) {
        if (entity == null) return null;
        CompanyDto dto = new CompanyDto();
        dto.setName(entity.getName());
        dto.setCatchPhrase(entity.getCatchPhrase());
        dto.setBs(entity.getBs());
        return dto;
    }
}
