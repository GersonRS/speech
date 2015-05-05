/**
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.furb.api.furbspeech.comp;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a phrase object of the text.
 * @author Germano
 */
public class Phrase {

	public enum PhraseType {
		AFIRMATIVA,
		EXCLAMATIVA,
		INTERROGATIVA
	}
	
	private String phrase;
	private PhraseType type;
	private int numWords;
	private List<Word> words;

	public Phrase(String phrase) {
		this.phrase = phrase.trim().toLowerCase();
		this.type = getPhraseType();
		this.words = splitWordsOfThisPhrase();
		this.numWords = words.size();
	}
	
	private List<Word> splitWordsOfThisPhrase() {
		List<Word> words = new ArrayList<Word>();
		
		// remove this items of the phrase copy.
		String phraseCopy = this.phrase.replaceAll("[.!?]", "");
		
		String[] wordsStr = phraseCopy.split(" ");
		for (String w : wordsStr) {
			if (w.length() != 0) {
				words.add(new Word(w));
			}
		}
		
		return words;
	}

	private PhraseType getPhraseType() {
		char lastChar = phrase.charAt(phrase.length()-1);
		switch (lastChar) {
			case '.': return PhraseType.AFIRMATIVA;
			case '!': return PhraseType.EXCLAMATIVA;
			case '?': return PhraseType.INTERROGATIVA;
			default: return PhraseType.AFIRMATIVA;
		}
	}

	public String showWords() {
		StringBuilder sb = new StringBuilder();
		for (Word w : words) {
			sb.append(w.showSyllables());
		}
		sb.append("_ 300\n");
		
		return sb.toString();
	}
	
	public void parseWords(double frequency, int time) {
		double l = 0;
		switch (this.type) {
			case INTERROGATIVA: {
				int count = 0;
				int m = 0;
				for (Word word : this.words) {
					for (Syllable syllable : word.getSyllables()) {
						count += syllable.getNumPhonemes();
					}
				}
				
				for (Word word : this.words) {
					word.parseSyllables(0, time);
					for (Syllable syllable : word.getSyllables()) {
						for (Phoneme phoneme : syllable.getPhonemes()) {
							double frequency1 = 2 * Math.sin(l) + 15;
							double frequency2 = 20 * Math.sin(2 * Math.PI * m/count) + 90;
							frequency = frequency1 + frequency2;
							phoneme.setFrequency(frequency);
							
							l+= 0.25;
							m++;
						}
					}
				}
				
				break;
			}
			
			default: { // EXCLAMATIVA or AFFIRMATIVA
				for (Word word : this.words) {
					word.parseSyllables(frequency, time);
					for (Syllable syllable : word.getSyllables()) {
						for (Phoneme phoneme : syllable.getPhonemes()) {
							frequency = 2 * Math.sin(l) + 100;
							phoneme.setFrequency(frequency);
							
							l+= 0.25;
						}
					}
				}
				break;
			}
		}
	}
 	
	public String getPhrase() {
		return phrase;
	}
	
	public PhraseType getType() {
		return type;
	}
	
	public int getNumWords() {
		return numWords;
	}
	
	public List<Word> getWords() {
		return words;
	}
}
