package kr.kro.calcking.calckingwebbe.services.genquestion;

import java.util.HashMap;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class GenQuestionService {

    public HashMap<String, Object> getQuestionAplusB(int cnt) {
        // 몇 + 몇 (합이 9이하)
        HashMap<String, Object> map = new HashMap<>();

        Random random = new Random();
        for (int i = 0; i < cnt; i++) {
            int num1 = random.nextInt(8) + 1;
            int num2 = random.nextInt(9 - num1) + 1;

            boolean swap = random.nextBoolean(); // 랜덤으로 true 또는 false 생성

            if (swap) {
                // 두 숫자의 순서를 바꿈
                int temp = num1;
                num1 = num2;
                num2 = temp;
            }

            String output = num1 + " + " + num2 + " =";
            map.put(i + 1 + "번", output);
        }

        return map;
    }
}