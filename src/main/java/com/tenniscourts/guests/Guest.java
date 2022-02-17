package com.tenniscourts.guests;

import com.tenniscourts.config.persistence.BaseEntity;
import com.tenniscourts.reservations.Reservation;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class Guest extends BaseEntity<Long> {

  @Column
  @NotNull
  private String name;

  @OneToMany
  private List<Reservation> reservations;

  public void addReservation(Reservation reservation) {
    if (this.reservations == null) {
      this.reservations = new ArrayList<>();
    }

    reservation.setGuest(this);
    this.reservations.add(reservation);
  }
}
