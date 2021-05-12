package edu.utn.countries.WorldService.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.utn.countries.WorldService.dto.CountryDto;
import edu.utn.countries.WorldService.dto.StateDto;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Objects;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "states")
public class State {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Integer  id;

    @Column
    String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="country_code", nullable = false)
    Country country;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(id, state.id) && Objects.equals(name, state.name) && Objects.equals(country, state.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country);
    }

}
