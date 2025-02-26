package com.cot.ummu.payload.mappers;

import com.cot.ummu.entity.concretes.business.EducationTerm;
import com.cot.ummu.payload.request.bussines.EducationTermRequest;
import com.cot.ummu.payload.request.bussines.EducationTermUpdateRequest;
import com.cot.ummu.payload.response.businnes.EducationTermResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        //with this parameter, MapStruct will always check source properties if they have null value or not.
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        //If a source bean property equals null, the target bean property will be ignored and retain its existing value. So, we will be able to perform partial update.
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EducationTermMapper {

    EducationTerm mapEducationTermRequestToEducationTerm(EducationTermRequest educationTermRequest);

    EducationTerm updateEducationTermWithEducationTermUpdateRequest(EducationTermUpdateRequest educationTermRequest, @MappingTarget EducationTerm educationTerm);

    EducationTermResponse mapEducationTermToEducationTermResponse( EducationTerm educationTerm);

}



/*
 */
/**
 *
 * @param educationTermRequest DTO from Postman or FE
 * @return  EducationTerm Entity
 *//*

    public EducationTerm mapEducationTermRequestToEducationTerm(EducationTermRequest educationTermRequest){
        return EducationTerm.builder()
                .term(educationTermRequest.getTerm())
                .startDate(educationTermRequest.getStartDate())
                .endDate(educationTermRequest.getEndDate())
                .lastRegistrationDate(educationTermRequest.getLastRegistrationDate())
                .build();
    }

    */
/**
 *
 * @param //educationTerm Entity fetched from DB
 * @return EducationTermResponse DTO
 *//*

    public EducationTermResponse mapEducationTermToEducationTermResponse(EducationTerm educationTerm){
        return EducationTermResponse.builder()
                .id(educationTerm.getId())
                .term(educationTerm.getTerm())
                .startDate(educationTerm.getStartDate())
                .endDate(educationTerm.getEndDate())
                .lastRegistrationDate(educationTerm.getLastRegistrationDate())
                .build();
    }
*/