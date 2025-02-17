package com.cot.ummu.service.businnes;

import com.cot.ummu.exception.BadRequestException;
import com.cot.ummu.exception.ConflictException;
import com.cot.ummu.payload.messages.ErrorMessages;
import com.cot.ummu.payload.request.bussines.EducationTermRequest;
import com.cot.ummu.payload.response.businnes.EducationTermResponse;
import com.cot.ummu.payload.response.businnes.ResponseMessage;
import com.cot.ummu.repository.businnes.EducationTermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class EducationTermService {


    private final EducationTermRepository educationTermRepository;

    public ResponseMessage<EducationTermResponse> saveEducationTerm(@Valid EducationTermRequest educationTermRequest) {
        //validation
        validateEducationTermDates(educationTermRequest);
        //TODO
        //write mappers DTO->Entity + Entity ->DTO

    }



    ///validation
    private void validateEducationTermDates(EducationTermRequest educationTermRequest){
        //validate request by reg/start/stop
        validateEducationTermDetasForRequest(educationTermRequest);
        //only one edication term can exist in a year
        if (educationTermRepository.existByTermAndYear(educationTermRequest.getTerm(),educationTermRequest.getStartDate().getYear())){
            throw new ConflictException(ErrorMessages.EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR_MESSAGE);
        }
        //validate not to have any conflict with other education terms
        educationTermRepository.findByYear(educationTermRequest.getStartDate().getYear())
                .forEach(educationTerm ->{
                   if (!educationTerm.getStartDate().isAfter(educationTermRequest.getEndDate()) || educationTerm.getEndDate().isBefore(educationTermRequest.getStartDate())){
                       throw new BadRequestException(ErrorMessages.EDUCATION_TERM_CONFLICT_MESSAGE);
                   }
                });
    }

    //  2025-04-01                        / 2025-09-01 ---> senin enddate ve start daten databasedekinden öncemi sonra mı diye bakıyorsun
    //------------ 25-06-01 / 2025-07-90 -------


    private void validateEducationTermDetasForRequest(EducationTermRequest educationTermRequest){
        //reg<start
        if ((educationTermRequest.getLastRegistrationDate().isAfter(educationTermRequest.getStartDate()))){
            throw new ConflictException(ErrorMessages.EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE);
        }
        //end>start
        if (educationTermRequest.getEndDate().isBefore(educationTermRequest.getStartDate())){
            throw new ConflictException(ErrorMessages.EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE);
        }
    }


}
