package xyz.calcking.api.controllers.genquestions;

import xyz.calcking.api.annotations.ValidateAccessToken;
import xyz.calcking.api.dtos.genquestions.CreateQuestionsAddDTO;
import xyz.calcking.api.services.genquestions.GenQuestionsAddService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gen-questions/add")
public class GenQuestionsAddController {
  private final GenQuestionsAddService genQuestionsAddService;

  @GetMapping("/units-to-units")
  public ResponseEntity<Map<String, Object>> addUnits2Units(
      @Valid @ModelAttribute CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    return genQuestionsAddService.addUnits2Units(createQuestionsAddDTO, request);
  }

  @GetMapping("/units-to-units-to-units")
  public ResponseEntity<Map<String, Object>> addUnits2Units2Units(
      @Valid @ModelAttribute CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    return genQuestionsAddService.addUnits2Units2Units(createQuestionsAddDTO, request);
  }

  @ValidateAccessToken
  @GetMapping("/tens-to-units")
  public ResponseEntity<Map<String, Object>> addTens2Units(
      @Valid @ModelAttribute CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    return genQuestionsAddService.addTens2Units(createQuestionsAddDTO, request);
  }

  @ValidateAccessToken
  @GetMapping("/tens-units-to-units")
  public ResponseEntity<Map<String, Object>> addTensUnits2Units(
      @Valid @ModelAttribute CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    return genQuestionsAddService.addTensUnits2Units(createQuestionsAddDTO, request);
  }

  @ValidateAccessToken
  @GetMapping("/tens-units-to-tens-units")
  public ResponseEntity<Map<String, Object>> addTenUnits2TensUnits(
      @Valid @ModelAttribute CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    return genQuestionsAddService.addTensUnits2TensUnits(createQuestionsAddDTO, request);
  }

  @ValidateAccessToken
  @GetMapping("/hunds-to-units")
  public ResponseEntity<Map<String, Object>> addHunds2Units(
      @Valid @ModelAttribute CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    return genQuestionsAddService.addHunds2Units(createQuestionsAddDTO, request);
  }

  @ValidateAccessToken
  @GetMapping("/hunds-to-tens-units")
  public ResponseEntity<Map<String, Object>> addHunds2TensUnits(
      @Valid @ModelAttribute CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    return genQuestionsAddService.addHunds2TensUnits(createQuestionsAddDTO, request);
  }

  @ValidateAccessToken
  @GetMapping("/hunds-tens-to-units")
  public ResponseEntity<Map<String, Object>> addHundsTens2Units(
      @Valid @ModelAttribute CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    return genQuestionsAddService.addHundsTens2Units(createQuestionsAddDTO, request);
  }

  @ValidateAccessToken
  @GetMapping("/hunds-tens-to-tens-units")
  public ResponseEntity<Map<String, Object>> addHundsTens2TensUnits(
      @Valid @ModelAttribute CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    return genQuestionsAddService.addHundsTens2TensUnits(createQuestionsAddDTO, request);
  }

  @ValidateAccessToken
  @GetMapping("/hunds-tens-units-to-units")
  public ResponseEntity<Map<String, Object>> addHundsTensUnits2Units(
      @Valid @ModelAttribute CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    return genQuestionsAddService.addHundsTensUnits2Units(createQuestionsAddDTO, request);
  }

  @ValidateAccessToken
  @GetMapping("/hunds-tens-units-to-tens-units")
  public ResponseEntity<Map<String, Object>> addHundsTensUnits2TensUnits(
      @Valid @ModelAttribute CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    return genQuestionsAddService.addHundsTensUnits2TensUnits(createQuestionsAddDTO, request);
  }

  @ValidateAccessToken
  @GetMapping("/hunds-tens-units-to-hunds-tens-units")
  public ResponseEntity<Map<String, Object>> addHundsTensUnits2HundsTensUnits(
      @Valid @ModelAttribute CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    return genQuestionsAddService.addHundsTensUnits2HundsTensUnits(createQuestionsAddDTO, request);
  }
}
