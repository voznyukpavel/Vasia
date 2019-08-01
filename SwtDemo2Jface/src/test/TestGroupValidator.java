package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import com.lux.study.util.TextChecker;
class TestGroupValidator {

    @Test
    void testcheckTextFildNumbersAndSymbols() {
      boolean requiredOutput = true;
      assertEquals(requiredOutput, TextChecker.groupChecker("478_Sohatiy"));
    }

    @Test
    void testcheckTextFildNumbersVsLaters()  {
      boolean requiredOutput = true;
      assertEquals(requiredOutput, TextChecker.groupChecker("1A"));
    }

    @Test 
    void testcheckTextLaters() {
      boolean requiredOutput = true;
      assertEquals(requiredOutput, TextChecker.groupChecker("Boris"));
    }

    @Test
    void testcheckTextFild1OnlyLaters() {
      boolean requiredOutput = true;
      assertEquals(requiredOutput, TextChecker.groupChecker("Sohatiy"));
    }

    @Test 
    void testcheckTextFildLatersVsNumbers() {
      boolean requiredOutput = true;
      assertEquals(requiredOutput, TextChecker.groupChecker("Max235"));
    }

    @Test void testcheckTextFildOnlyNumbers(){
      boolean requiredOutput = true;
      assertEquals(requiredOutput, TextChecker.groupChecker("4.5"));
    }

    @Test void testcheckTextFildNumbers() {
      boolean requiredOutput = true;
      assertEquals(requiredOutput, TextChecker.groupChecker("Marusia123"));
    }

    @Test void testcheckTextFildOnePoint() {
      boolean requiredOutput = false;
      assertEquals(requiredOutput, TextChecker.groupChecker("?"));
    }

    @Test void testcheckTextFildTwoWords(){
      boolean requiredOutput = true;
      assertEquals(requiredOutput, TextChecker.groupChecker("Toha"));
    }

    @Test void testcheckTextFildTwoMinus(){
      boolean requiredOutput = false;
      assertEquals(requiredOutput, TextChecker.groupChecker("-"));
    }

    @Test void testcheckTextFildNumbersVsD() {
      boolean requiredOutput = true;
      assertEquals(requiredOutput, TextChecker.groupChecker("2Division"));
    }

}
