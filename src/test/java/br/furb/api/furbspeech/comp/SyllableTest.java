package br.furb.api.furbspeech.comp;

import org.junit.Test;

import br.furb.api.furbspeech.comp.Syllable.Tonicity;
import static org.junit.Assert.*;
public class SyllableTest {

	@Test
	public void testSyllableInstantiation() {
		Syllable s = new Syllable("bor", Tonicity.TONICA);
		assertTrue(s.getNumPhonemes() == 3);
		
		s = new Syllable("sa", Tonicity.ATONA);
		assertTrue(s.getNumPhonemes() == 2);
		
		s = new Syllable("tam", Tonicity.ATONA);
		assertTrue(s.getNumPhonemes() == 2);
		
		s = new Syllable("can", Tonicity.TONICA);
		assertTrue(s.getNumPhonemes() == 2);
		
		s = new Syllable("pá", Tonicity.TONICA);
		assertTrue(s.getNumPhonemes() == 2);
	}
	
}
