package com.cot.ummu.service.businnes;

import com.cot.ummu.entity.concretes.business.EducationTerm;
import com.cot.ummu.exception.BadRequestException;
import com.cot.ummu.exception.ConflictException;
import com.cot.ummu.exception.ResourceNotFoundException;
import com.cot.ummu.payload.mappers.EducationTermMapper;
import com.cot.ummu.payload.messages.ErrorMessages;
import com.cot.ummu.payload.messages.SuccessMessages;
import com.cot.ummu.payload.request.bussines.EducationTermRequest;
import com.cot.ummu.payload.response.businnes.EducationTermResponse;
import com.cot.ummu.payload.response.businnes.ResponseMessage;
import com.cot.ummu.repository.businnes.EducationTermRepository;
import com.cot.ummu.service.helpar.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class EducationTermService {


    private final EducationTermRepository educationTermRepository;
    private final EducationTermMapper educationTermMapper;
    private final PageableHelper pageableHelper;

    public ResponseMessage<EducationTermResponse> saveEducationTerm(@Valid EducationTermRequest educationTermRequest) {
        //validation
        validateEducationTermDates(educationTermRequest);
        //write mappers DTO->Entity + Entity ->DTO
        EducationTerm educationTerm = educationTermMapper.mapEducationTermRequestToEducationTerm(educationTermRequest);
        EducationTerm savedEducationTerm = educationTermRepository.save(educationTerm);
        return ResponseMessage.<EducationTermResponse>builder()
                .message(SuccessMessages.EDUCATION_TERM_SAVE)
                .returnBody(educationTermMapper.mapEducationTermToEducationTermResponse(savedEducationTerm))
                .httpStatus(HttpStatus.CREATED)
                .build();
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


    public ResponseMessage<EducationTermResponse> updateEducationTerm(@Valid EducationTermRequest educationTermRequest, Long educationTermId) {
        //check if education term exist
        isEducationTermExist(educationTermId);
        //validate dates
        validateEducationTermDetasForRequest(educationTermRequest);
        //mapping
        EducationTerm term = educationTermMapper.mapEducationTermRequestToEducationTerm(educationTermRequest);
/*        //save to DB
        EducationTerm savededucationTerm = educationTermRepository.save(term);
        aşada ekledik.*/
        //return by mapping it to DTO
        return ResponseMessage.<EducationTermResponse>builder()
                .message(SuccessMessages.EDUCATION_TERM_UPDATE)
                .returnBody(educationTermMapper.mapEducationTermToEducationTermResponse(educationTermRepository.save(term)))
                .httpStatus(HttpStatus.OK)
                .build();
    }


    public EducationTerm isEducationTermExist(Long educationTermId){
        return educationTermRepository.findById(educationTermId)
                .orElseThrow(()-> new ResourceNotFoundException(ErrorMessages.EDUCATION_TERM_NOT_FOUND_MESSAGE));
    }


    public Page<EducationTermResponse> getByPage(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageable(page,size,sort,type);
        //fetch paginated and sorted data from DB
        Page<EducationTerm> educationTerms = educationTermRepository.findAll(pageable);
        //use mapper
        return educationTerms.map(educationTermMapper::mapEducationTermToEducationTermResponse);
    }



}
