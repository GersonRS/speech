package br.furb.api.furbspeech.comp;

import org.junit.Test;

import br.furb.api.furbspeech.FurbSpeech;
import br.furb.api.furbspeech.synth.MBrolaSynthesizer;

public class FurbSpeechTest {

	@Test
	public void testSpeechOnlyOnePhrase() {
		new FurbSpeech().text("Vou empregar a pessoa").to().speech();
		new FurbSpeech().text("Olá senhor Germano").to().speech();
		new FurbSpeech().text("Rua martin luther").to().speech();		
		new FurbSpeech().text("Rua antônio da veiga").to().speech();
	}
	
	@Test
	public void testSpeechMorePhrases() {
		new FurbSpeech().text("Esse é um teste da nóva biblioteca. Nova frase para falar").to().speech();
		new FurbSpeech().text("Perfume. Perfúme. Perto. Pérto").to().speech();
	}
	
	@Test
	public void testSpeechPhrasesWithAbbreviations() {
		new FurbSpeech().text("Av. Alameda Rio Branco. Av. Beira Rio").to().speech();
	}
	
	@Test
	public void testSpeechPhrasesWithDigits() {
		new FurbSpeech().text("Av. Alameda Rio Branco nº 64").to().speech();
		new FurbSpeech().text("Av. Alameda Rio Branco nº 1420").to().speech();
	}
	
	@Test
	public void testSpeechInterrogativePhrases() {
		new FurbSpeech().text("O que é isso? Quem manda aqui?").to().speech();
	}
	
	@Test
	public void testSpeechToWavFile() {
		new FurbSpeech().text("Olá mundo!").to("myFile.wav").speech();
	}
	
	@Test
	public void testSpeechWithDifferentVoice() {
		new FurbSpeech().text("Olá mundo!").withVoice(MBrolaSynthesizer.VOICE_BR2).to().speech();
	}
}
