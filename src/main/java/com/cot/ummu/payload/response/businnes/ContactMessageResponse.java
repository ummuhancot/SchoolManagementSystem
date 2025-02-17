package com.cot.ummu.payload.response.businnes;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactMessageResponse {

	private String name;

	private String email;

	private String subject;

	private String message;

	private LocalDateTime createdAt;

}
