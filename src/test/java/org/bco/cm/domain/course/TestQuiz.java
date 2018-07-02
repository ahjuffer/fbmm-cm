/*
 * The MIT License
 *
 * Copyright 2018 André H. Juffer, Biocenter Oulu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.bco.cm.domain.course;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bco.cm.dto.MultipleChoiceQuestionDTO;
import org.bco.cm.dto.QuizDTO;
import org.bco.cm.dto.ChoiceDTO;
/**
 *
 * @author ajuffer
 */
public class TestQuiz {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        QuizDTO spec = new QuizDTO();
        spec.setTitle("Test");
        
        MultipleChoiceQuestionDTO q1 = new MultipleChoiceQuestionDTO();
        q1.setQuestion("Who will win the world cup?");
        List<ChoiceDTO> choices1 = new ArrayList<>();
        choices1.add(new ChoiceDTO("Germany"));
        choices1.add(new ChoiceDTO("Brasil"));
        choices1.add(new ChoiceDTO("Belgium"));
        choices1.add(new ChoiceDTO("Columbia"));
        q1.setChoices(choices1);
        q1.setAnswer(new ChoiceDTO("Columbia"));
       
        MultipleChoiceQuestionDTO q2 = new MultipleChoiceQuestionDTO();
        q2.setQuestion("Who will be third?");
        List<ChoiceDTO> choices2 = new ArrayList<>();
        choices2.add(new ChoiceDTO("Spain"));
        choices2.add(new ChoiceDTO("Portugal"));
        choices2.add(new ChoiceDTO("Croatia"));
        choices2.add(new ChoiceDTO("Russia"));
        q2.setChoices(choices2);
        q2.setAnswer(new ChoiceDTO("Russia"));
       
        List<MultipleChoiceQuestionDTO> qs = new ArrayList<>();
        qs.add(q1);
        qs.add(q2);
       
        spec.setQuestions(qs);
        System.out.println(spec);
       
        Instant now = Instant.now();
        long seed = now.toEpochMilli();
        Random random = new Random(seed);
        Quiz quiz = Quiz.valueOf(spec);
        quiz.getQuestions().forEach(question -> {
            System.out.println(question.getQuestion().getPhrase());
            int index = random.nextInt(4);
            Choice answer = question.getChoices().get(index);
            System.out.println("Selected choice is " + answer.stringValue());
            boolean correct = question.isAnswer(answer);
            while (!correct) {
                index = random.nextInt(4);
                answer = question.getChoices().get(index);
                System.out.println("Selected choice is " + answer.stringValue());
                correct = question.isAnswer(answer);
            }
            System.out.println("Correct answer is " + answer.stringValue());
       });
    }
    
}
