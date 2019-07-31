package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import com.lux.study.util.TextChecker;
class TestNameValidator {

	@Test
	void testcheckTextFildNumbersAndSymbols() {
		boolean requiredOutput = false;
		assertEquals(requiredOutput, TextChecker.nameChecker("478"));
	}

	@Test
	void testcheckTextFildNumbersVsLaters()  {
		boolean requiredOutput = true;
		assertEquals(requiredOutput, TextChecker.nameChecker("Anna"));
	}

	@Test void testcheckTextFildNumbersVsLatersInGroupName() {
		boolean requiredOutput = true;
		assertEquals(requiredOutput, TextChecker.nameChecker("Boris"));
	}

	@Test void testcheckTextFild1OnlyLaters() {
		boolean requiredOutput = true;
		assertEquals(requiredOutput, TextChecker.nameChecker("Sohatiy"));
	}

	@Test void testcheckTextFildLatersVsNumbers() {
		boolean requiredOutput = false;
		assertEquals(requiredOutput, TextChecker.nameChecker("Max235"));
	}

	@Test void testcheckTextFildOnlyNumbers(){
		boolean requiredOutput = false;
		assertEquals(requiredOutput, TextChecker.nameChecker("45"));
	}

	@Test void testcheckTextFildNumbers() {
		boolean requiredOutput = false;
		assertEquals(requiredOutput, TextChecker.nameChecker("Marusia123"));
	}

	@Test void testcheckTextFildOnePoint() {
		boolean requiredOutput = false;
		assertEquals(requiredOutput, TextChecker.nameChecker("."));
	}

	@Test void testcheckTextFildTwoWords(){
		boolean requiredOutput = true;
		assertEquals(requiredOutput, TextChecker.nameChecker("Toha"));
	}

	@Test void testcheckTextFildTwoMinus(){
		boolean requiredOutput = false;
		assertEquals(requiredOutput, TextChecker.nameChecker("-"));
	}

	@Test void testcheckTextFildNumbersVsD() {
		boolean requiredOutput = false;
		assertEquals(requiredOutput, TextChecker.nameChecker("2Division"));
	}
}
