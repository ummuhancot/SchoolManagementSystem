package com.cot.ummu.payload.request.bussines;

import com.cot.ummu.entity.enums.Term;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EducationTermRequest {
  @NotNull(message = "Education term must not be empty")
  private Term term;

  @NotNull(message = "Start date must not be empty")
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate startDate;

  @NotNull(message = "End date must not be empty")
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate endDate;

  @NotNull(message = "Last registration date must not be empty")
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate lastRegistrationDate;

}
