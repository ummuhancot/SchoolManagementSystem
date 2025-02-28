package com.cot.ummu.payload.mappers;


import com.cot.ummu.entity.concretes.business.EducationTerm;
import com.cot.ummu.payload.request.bussines.EducationTermRequest;
import com.cot.ummu.payload.request.bussines.EducationTermUpdateRequest;
import com.cot.ummu.payload.response.businnes.EducationTermResponse;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        //with this parameter, MapStruct will always check source properties if they have null value or not.
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        //If a source bean property equals null, the target bean property will be ignored and retain its existing value. So, we will be able to perform partial update.
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EducationTermMapper {

    EducationTerm mapEducationTermRequestToEducationTerm(EducationTermRequest educationTermRequest);

    EducationTerm updateEducationTermWithEducationTermUpdateRequest(EducationTermUpdateRequest educationTermRequest, @MappingTarget EducationTerm educationTerm);

    EducationTermResponse mapEducationTermToEducationTermResponse(EducationTerm educationTerm);
}
