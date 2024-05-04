package kr.kro.calcking.calckingwebbe.controllers.genquestion;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.kro.calcking.calckingwebbe.services.genquestion.GenQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/get-question")
public class GenQuestionController {
    private final GenQuestionService genQuestionService;

    @GetMapping
    public Map<String, Object> getQuestion() {
        // 연산 문제 생성 API
        return genQuestionService.getQuestionAplusB(10);
    }

}
