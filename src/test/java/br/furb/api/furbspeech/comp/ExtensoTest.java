package br.furb.api.furbspeech.comp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.furb.api.furbspeech.util.Extenso;

public class ExtensoTest {

	@Test
	public void testExtenso() {
		assertEquals("sessenta e quatro", Extenso.converte(64));
		assertEquals("zero", Extenso.converte(0));
		assertEquals("noventa e nove", Extenso.converte(99));
		assertEquals("cem", Extenso.converte(100));
		assertEquals("cento e onze", Extenso.converte(111));
		assertEquals("novecentos e noventa e oito", Extenso.converte(998));
		assertEquals("um mil, oitocentos e noventa e seis", Extenso.converte(1896));
		assertEquals("onze milhões, cento e onze mil, oitocentos e noventa e seis", Extenso.converte(11111896));
	}
	
}
