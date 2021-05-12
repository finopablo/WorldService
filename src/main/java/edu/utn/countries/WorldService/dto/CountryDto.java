package edu.utn.countries.WorldService.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.utn.countries.WorldService.domain.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryDto {

    String code;
    String name;
    List<StateDto> states;

    public static CountryDto from(Country country) {
        return CountryDto.builder().code(country.getCode()).name(country.getName()).build();
    }
}
