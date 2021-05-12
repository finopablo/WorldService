package edu.utn.countries.WorldService.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.utn.countries.WorldService.domain.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StateDto {
    Integer id;
    String name;
    CountryDto country;

    public static StateDto from(State state) {
        return StateDto.builder().id(state.getId()).name(state.getName()).country(CountryDto.from(state.getCountry())).build();
    }


}
