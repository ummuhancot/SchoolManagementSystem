package com.cot.ummu.payload.response.businnes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonResponse {


  private Long lessonId;
  private String lessonName;
  private int creditScore;
  private boolean isCompulsory;

}
