package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import com.lux.study.util.TextChecker;
class TastFieldsValidator {

	@Test
	void testcheckTextFildNumbersAndSymbols() {
		boolean requiredOutput = false;
		assertEquals(requiredOutput, TextChecker.checker("478", "*/-"));
	}

	@Test
	void testcheckTextFildNumbersVsLaters()  {
		boolean requiredOutput = true;
		assertEquals(requiredOutput, TextChecker.checker("Anna","45"));
	}

	@Test void testcheckTextFildNumbersVsLatersInGroupName() {
		boolean requiredOutput = true;
		assertEquals(requiredOutput, TextChecker.checker("Boris","45A"));
	}

	@Test void testcheckTextFild1OnlyLaters() {
		boolean requiredOutput = true;
		assertEquals(requiredOutput, TextChecker.checker("Sohatiy","Spec"));
	}

	@Test void testcheckTextFildLatersVsNumbers() {
		boolean requiredOutput = false;
		assertEquals(requiredOutput, TextChecker.checker("Max235","6"));
	}

	@Test void testcheckTextFildOnlyNumbers(){
		boolean requiredOutput = false;
		assertEquals(requiredOutput, TextChecker.checker("45","Buhmiller"));
	}

	@Test void testcheckTextFildNumbers() {
		boolean requiredOutput = false;
		assertEquals(requiredOutput, TextChecker.checker("Marusia123","Kalina"));
	}

	@Test void testcheckTextFildOnePoint() {
		boolean requiredOutput = false;
		assertEquals(requiredOutput, TextChecker.checker(".","1"));
	}

	@Test void testcheckTextFildTwoWords(){
		boolean requiredOutput = true;
		assertEquals(requiredOutput, TextChecker.checker("Toha","Poland.com"));
	}

	@Test void testcheckTextFildTwoMinus(){
		boolean requiredOutput = false;
		assertEquals(requiredOutput, TextChecker.checker("-","Minus"));
	}

	@Test void testcheckTextFildNumbersVsD() {
		boolean requiredOutput = false;
		assertEquals(requiredOutput, TextChecker.checker("2Division","HumpYard"));
	}
}
