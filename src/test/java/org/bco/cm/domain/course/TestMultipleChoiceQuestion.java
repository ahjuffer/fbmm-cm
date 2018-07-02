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
import org.bco.cm.dto.ChoiceDTO;

/**
 *
 * @author ajuffer
 */
public class TestMultipleChoiceQuestion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       MultipleChoiceQuestionDTO spec = new MultipleChoiceQuestionDTO();
       spec.setQuestion("Who will win the world cup?");
       List<ChoiceDTO> choices = new ArrayList<>();
       choices.add(new ChoiceDTO("Germany"));
       choices.add(new ChoiceDTO("Brasil"));
       choices.add(new ChoiceDTO("Belgium"));
       choices.add(new ChoiceDTO("Columbia"));
       spec.setChoices(choices);
       spec.setAnswer(new ChoiceDTO("Columbia"));
       MultipleChoiceQuestion mcq = MultipleChoiceQuestion.valueOf(spec);
       
       Instant now = Instant.now();
       long seed = now.toEpochMilli();
       Random random = new Random(seed);
       int index = random.nextInt(4);
       Choice answer = Choice.valueOf(choices.get(index));
       System.out.println("Selected choice is " + answer.stringValue());
       boolean correct = mcq.isAnswer(answer);
       while (!correct) {
           index = random.nextInt(4);
           answer = Choice.valueOf(choices.get(index));
           System.out.println("Selected choice is " + answer.stringValue());
           correct = mcq.isAnswer(answer);
       }
       System.out.println("Correct answer is " + answer.stringValue());
    }
    
}
