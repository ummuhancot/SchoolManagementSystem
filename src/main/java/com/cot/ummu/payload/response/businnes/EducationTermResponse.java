package com.cot.ummu.payload.response.businnes;

import com.cot.ummu.entity.enums.Term;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EducationTermResponse {

  private Long id;
  private Term term;
  private LocalDate startDate;
  private LocalDate endDate;
  private LocalDate lastRegistrationDate;


}
