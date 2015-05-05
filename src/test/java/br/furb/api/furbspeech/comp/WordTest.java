package br.furb.api.furbspeech.comp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.furb.api.furbspeech.comp.Syllable.Tonicity;

public class WordTest {
	
	@Test
	public void testSyllabicSeparation() {
		Word w = new Word("teste");
		assertEquals(w.getNumSyllables(), 2);
		assertEquals(w.getSyllables().get(0).getSyllable(), "tes");
		assertEquals(w.getSyllables().get(1).getSyllable(), "te");
		assertEquals(w.getSyllables().get(0).getTonicity(), Tonicity.TONICA);
		assertEquals(w.getSyllables().get(1).getTonicity(), Tonicity.ATONA);
		
		w = new Word("carro");
		assertEquals(w.getNumSyllables(),  2);
		assertEquals(w.getSyllables().get(0).getSyllable(), "car");
		assertEquals(w.getSyllables().get(1).getSyllable(), "ro");
		assertEquals(w.getSyllables().get(0).getTonicity(), Tonicity.TONICA);
		assertEquals(w.getSyllables().get(1).getTonicity(), Tonicity.ATONA);
		
		w = new Word("rogério");
		assertEquals(w.getNumSyllables(),  4);
		assertEquals(w.getSyllables().get(0).getSyllable(), "ro");
		assertEquals(w.getSyllables().get(1).getSyllable(), "gé");
		assertEquals(w.getSyllables().get(2).getSyllable(), "ri");
		assertEquals(w.getSyllables().get(3).getSyllable(), "o");
		assertEquals(w.getSyllables().get(0).getTonicity(), Tonicity.ATONA);
		assertEquals(w.getSyllables().get(1).getTonicity(), Tonicity.TONICA);
		assertEquals(w.getSyllables().get(2).getTonicity(), Tonicity.ATONA);
		assertEquals(w.getSyllables().get(3).getTonicity(), Tonicity.ATONA);
		
		w = new Word("empregar");
		assertEquals(w.getNumSyllables(),  3);
		assertEquals(w.getSyllables().get(0).getSyllable(), "em");
		assertEquals(w.getSyllables().get(1).getSyllable(), "pre");
		assertEquals(w.getSyllables().get(2).getSyllable(), "gar");
		assertEquals(w.getSyllables().get(0).getTonicity(), Tonicity.ATONA);
		assertEquals(w.getSyllables().get(1).getTonicity(), Tonicity.TONICA);
		assertEquals(w.getSyllables().get(2).getTonicity(), Tonicity.ATONA);
		
		w = new Word("veiga");
		assertEquals(w.getNumSyllables(),  2);
		assertEquals(w.getSyllables().get(0).getSyllable(), "vei");
		assertEquals(w.getSyllables().get(1).getSyllable(), "ga");
		assertEquals(w.getSyllables().get(0).getTonicity(), Tonicity.TONICA);
		assertEquals(w.getSyllables().get(1).getTonicity(), Tonicity.ATONA);
		
		w = new Word("martin");
		assertEquals(w.getNumSyllables(),  2);
		assertEquals(w.getSyllables().get(0).getSyllable(), "mar");
		assertEquals(w.getSyllables().get(1).getSyllable(), "tin");
		assertEquals(w.getSyllables().get(0).getTonicity(), Tonicity.TONICA);
		assertEquals(w.getSyllables().get(1).getTonicity(), Tonicity.ATONA);
		
		w = new Word("perto");
		assertEquals(w.getNumSyllables(),  2);
		assertEquals(w.getSyllables().get(0).getSyllable(), "per");
		assertEquals(w.getSyllables().get(1).getSyllable(), "to");
		assertEquals(w.getSyllables().get(0).getTonicity(), Tonicity.TONICA);
		assertEquals(w.getSyllables().get(1).getTonicity(), Tonicity.ATONA);
		
		w = new Word("hotel");
		assertEquals(w.getNumSyllables(),  2);
		assertEquals(w.getSyllables().get(0).getSyllable(), "ho");
		assertEquals(w.getSyllables().get(1).getSyllable(), "tel");
		assertEquals(w.getSyllables().get(0).getTonicity(), Tonicity.ATONA);
		assertEquals(w.getSyllables().get(1).getTonicity(), Tonicity.TONICA);
		
		w = new Word("discípulo");
		assertEquals(w.getNumSyllables(),  4);
		assertEquals(w.getSyllables().get(0).getSyllable(), "dis");
		assertEquals(w.getSyllables().get(1).getSyllable(), "cí");
		assertEquals(w.getSyllables().get(2).getSyllable(), "pu");
		assertEquals(w.getSyllables().get(3).getSyllable(), "lo");
		assertEquals(w.getSyllables().get(0).getTonicity(), Tonicity.ATONA);
		assertEquals(w.getSyllables().get(1).getTonicity(), Tonicity.TONICA);
		assertEquals(w.getSyllables().get(2).getTonicity(), Tonicity.ATONA);
		assertEquals(w.getSyllables().get(3).getTonicity(), Tonicity.ATONA);
		
		w = new Word("quina");
		assertEquals(w.getNumSyllables(),  2);
		assertEquals(w.getSyllables().get(0).getSyllable(), "qui");
		assertEquals(w.getSyllables().get(1).getSyllable(), "na");
		assertEquals(w.getSyllables().get(0).getTonicity(), Tonicity.TONICA);
		assertEquals(w.getSyllables().get(1).getTonicity(), Tonicity.ATONA);
		
		w = new Word("exceção");
		assertEquals(w.getNumSyllables(),  3);
		assertEquals(w.getSyllables().get(0).getSyllable(), "ex");
		assertEquals(w.getSyllables().get(1).getSyllable(), "ce");
		assertEquals(w.getSyllables().get(2).getSyllable(), "ção");
		assertEquals(w.getSyllables().get(0).getTonicity(), Tonicity.ATONA);
		assertEquals(w.getSyllables().get(1).getTonicity(), Tonicity.ATONA);
		assertEquals(w.getSyllables().get(2).getTonicity(), Tonicity.TONICA);
		
		w = new Word("agosto");
		assertEquals(w.getNumSyllables(), 3);
		assertEquals(w.getSyllables().get(0).getSyllable(), "a");
		assertEquals(w.getSyllables().get(1).getSyllable(), "gos");
		assertEquals(w.getSyllables().get(2).getSyllable(), "to");
		assertEquals(w.getSyllables().get(0).getTonicity(), Tonicity.ATONA);
		assertEquals(w.getSyllables().get(1).getTonicity(), Tonicity.TONICA);
		assertEquals(w.getSyllables().get(2).getTonicity(), Tonicity.ATONA);
	}
}
