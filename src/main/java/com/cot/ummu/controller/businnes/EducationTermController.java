package com.cot.ummu.controller.businnes;

import com.cot.ummu.payload.request.bussines.EducationTermRequest;
import com.cot.ummu.payload.response.businnes.EducationTermResponse;
import com.cot.ummu.payload.response.businnes.ResponseMessage;
import com.cot.ummu.service.businnes.EducationTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/educationTerm")
@RequiredArgsConstructor
public class EducationTermController {

    private final EducationTermService educationTermService;


    @PreAuthorize("hasAnyAuthority('Admin','Dean')")
    @PostMapping("/save")
    public ResponseMessage<EducationTermResponse> saveEducationTerm(@Valid @RequestBody EducationTermRequest educationTermRequest){
        return educationTermService.saveEducationTerm(educationTermRequest);
    }


    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
    @PostMapping("/update/{educationTermId}")
    public ResponseMessage<EducationTermResponse> updateEducationTerm(
            @Valid @RequestBody EducationTermRequest educationTermRequest,
            @PathVariable Long educationTermId){
        return educationTermService.updateEducationTerm(educationTermRequest,educationTermId);
    }




   /* //TODO:service ve repository yap
    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
    @PostMapping("/getAll")
    public List<EducationTermResponse> getAllEducationTerms(){
        return educationTermService.getAllEducationTerms();
    }*/

   /* //TODO:esra
    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
    @PostMapping("/{educationTermId}")
    public EducationTermResponse getEducationTerm(Long educationTermId){
        return educationTermService.getEducationTermById(educationTermId);
    }*/

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
    @PostMapping("/getByPage")
    public Page<EducationTermResponse> getByPage(
            @PathVariable String userRole,
            @RequestParam (value = "page",defaultValue = "0") int page,
            @RequestParam (value = "size",defaultValue = "10") int size,
            @RequestParam (value = "sort",defaultValue = "name") String sort,
            @RequestParam (value = "type",defaultValue = "desc") String type){
        return educationTermService.getByPage(page,size,sort,type);
    }


    //  List<String> strings = new ArrayList<>();
    //  List strings = new ArrayList<>();
    //  böylede kullanılıyor bunu göstermek icin deleteById böyle ResponseMessage olarak döndürdük
    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
    @DeleteMapping("/delete/{educationTermId}")
    public ResponseMessage deleteEducationTerm(Long edicationTermId){
        return educationTermService.deleteById(edicationTermId);
    }



}
