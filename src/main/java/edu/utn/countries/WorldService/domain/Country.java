package edu.utn.countries.WorldService.domain;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "countries")
public class Country {

    @Id
    String code;
    @Column
    String name;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    List<State> states;


    public void setStates(List<State> states) {
        this.states = states;
        this.states.forEach(o -> o.setCountry(this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return code.equals(country.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
