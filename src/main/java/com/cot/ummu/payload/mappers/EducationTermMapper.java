package com.cot.ummu.payload.mappers;

import com.cot.ummu.entity.concretes.business.EducationTerm;
import com.cot.ummu.payload.request.bussines.EducationTermRequest;
import com.cot.ummu.payload.response.businnes.EducationTermResponse;

import java.util.List;
import java.util.stream.Collectors;


public class EducationTermMapper {


    /**
     *
     * @param educationTerm Entity fetched from DB
     * @return EducationTermResponse DTO
     */

    public EducationTermResponse mapEducationTermToEducationTermResponse(EducationTerm educationTerm){

        return EducationTermResponse.builder()
                .term(educationTerm.getTerm())
                .startDate(educationTerm.getStartDate())
                .endDate(educationTerm.getEndDate())
                .lastRegistrationDate(educationTerm.getLastRegistrationDate())
                .build();
    }


    /**
     *
     * @param educationTermRequest DTO from Postman or FE
     * @return  EducationTerm Entity
     */
    public EducationTerm mapEducationTermRequestToEducationTerm(EducationTermRequest educationTermRequest){
        return EducationTerm.builder()
                .term(educationTermRequest.getTerm())
                .startDate(educationTermRequest.getStartDate())
                .endDate(educationTermRequest.getEndDate())
                .lastRegistrationDate(educationTermRequest.getLastRegistrationDate())
                .build();
    }

    public List<EducationTermResponse> mapToResponseList(List<EducationTerm> allEducationTerm){
        return allEducationTerm.stream()
                .map(this::mapEducationTermToEducationTermResponse)
                .collect(Collectors.toList());
    }




}
