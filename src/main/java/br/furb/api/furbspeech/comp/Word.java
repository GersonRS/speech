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

import static br.furb.api.furbspeech.util.ComponentUtils.hasAccentuation;
import static br.furb.api.furbspeech.util.ComponentUtils.inArray;
import static br.furb.api.furbspeech.util.ComponentUtils.isConsonant;
import static br.furb.api.furbspeech.util.ComponentUtils.isGreater;
import static br.furb.api.furbspeech.util.ComponentUtils.isOxitona;
import static br.furb.api.furbspeech.util.ComponentUtils.isVogal;
import static br.furb.api.furbspeech.util.ComponentUtils.vogal;

import java.util.ArrayList;
import java.util.List;

import br.furb.api.furbspeech.comp.Syllable.Tonicity;

/**
 * Represents a word object of a phrase.
 * @author Germano
 */
public class Word {

	private class SyllabicSeparation {
		private List<String> syllables = new ArrayList<String>();
		private int tonicSyllableIndex;
		
		public void addSyllable(String s) {
			syllables.add(s);
		}
		public List<String> getSyllables() {
			return syllables;
		}
		public int getTonicSyllableIndex() {
			return tonicSyllableIndex;
		}
		public void setTonicSyllableIndex(int tonicSyllableIndex) {
			this.tonicSyllableIndex = tonicSyllableIndex;
		}
	}
	
	private String word;
	private int numSyllables;
	private List<Syllable> syllables;
	
	public Word(String word) {
		this.word = word.trim();
		this.syllables = new ArrayList<Syllable>();
		
		SyllabicSeparation syllabicSeparation = splitSyllablesOfThisWord();		
		List<String> syllables = syllabicSeparation.getSyllables();
		for (int i = 0; i < syllables.size(); i++) {
			// check if this syllable is the tonic syllable.
			if (i == syllabicSeparation.getTonicSyllableIndex()) {
				this.syllables.add(new Syllable(syllables.get(i), Tonicity.TONICA));
			}
			else {
				this.syllables.add(new Syllable(syllables.get(i), Tonicity.ATONA));
			}
		}
		
		this.numSyllables = this.syllables.size();
	}

	private SyllabicSeparation splitSyllablesOfThisWord() {
		SyllabicSeparation ss = new SyllabicSeparation();
		
		int wordLength = this.word.length();
		
		String syllable = "";
		String letter = "";
		String prev = "";
		String post = "";
		
		for (int i = 0; i < wordLength; i++) {
			letter = String.valueOf(this.word.charAt(i));
			post = ((i+1) >= wordLength) ? "" : String.valueOf(this.word.charAt(i+1));
			String prevPrevLetter = ((i-2) < 0) ? "" : String.valueOf(this.word.charAt(i-2));
			String nextPost = ((i+2) >= wordLength) ? "" : String.valueOf(this.word.charAt(i+2));
			
			if (syllable.equals("")) {
				syllable = syllable + letter;
			}
			else if (letter.equals(prev)) {
				ss.addSyllable(syllable);
				syllable = letter;
			}
			else if (isConsonant(letter)) {
				if (isConsonant(prev)) {
					if (isConsonant(post) && !inArray(post.toUpperCase(), new String[] {"L", "R", "H"})) {
						syllable = syllable + letter;
					}
					else if (prev.toUpperCase().equals("D") && !letter.toUpperCase().equals("R")) {
						ss.addSyllable(syllable);
						syllable = letter;
					}
					else if (prev.toUpperCase().equals("N") && letter.toUpperCase().equals("R")) {
						ss.addSyllable(syllable);
						syllable = letter;
					}
					else if (inArray(letter.toUpperCase(), new String[] {"L", "R", "H"})) {
						syllable = syllable + letter;
					}
					else if (i == 1) {
						syllable = syllable + letter;
					}
					else {
						ss.addSyllable(syllable);
						syllable = letter;
					}
				}
				else if (isVogal(prev)) {
					if (isConsonant(post) && (!inArray(post.toUpperCase(), new String[] {"L", "R", "H"}) || (letter.toUpperCase().equals(post.toUpperCase())) || letter.toUpperCase().equals("N"))) {
						if (inArray(letter.toUpperCase(), new String[] {"N"}) && inArray(post.toUpperCase(), new String[] {"H"})) {
							ss.addSyllable(syllable);
							syllable = letter;
						}
						else {
							syllable = syllable + letter;
						}
					}
					else if (isConsonant(post) && letter.toUpperCase().equals("D") && post.toUpperCase().equals("R")) {
						syllable = syllable + letter;
					}
					else if (isVogal(post)) {
						ss.addSyllable(syllable);
						syllable = letter;
					}
					else {
						ss.addSyllable(syllable);
						syllable = letter;
					}
				}
			}
			else if (isVogal(letter)) {
				if (isConsonant(prev)) {
					syllable = syllable + letter;
				}
				else if (isVogal(prev)) {
					if ((vogal(prev).equals("U")) && (i > 1) && (inArray(prevPrevLetter.toUpperCase(), new String[] {"Q", "G"}))) {
						syllable = syllable + letter;
					}
					else if ((vogal(prev).equals("U")) && (vogal(letter).equals("I")) && (isConsonant(post))) {
						syllable = syllable + letter;
					}
					else if (hasAccentuation(letter) || (inArray(post.toUpperCase(), new String[] {"M", "N"}) && !isVogal(nextPost))) {
						ss.addSyllable(syllable);
						syllable = letter;
					}
					else if (!post.equals("") && isVogal(post) && isGreater(letter, post)) {
						ss.addSyllable(syllable);
						syllable = letter;
					}
					else if (isGreater(prev, letter)) {
						syllable = syllable + letter;
					}
					else {
						ss.addSyllable(syllable);
						syllable = letter;
					}
				}
			}
			prev = letter;
		}
		ss.addSyllable(syllable);
		
		int numSyllables = ss.getSyllables().size();
	
		int tonicSyllIndex = 0;
		if (numSyllables != 1) {
			int proparoxitona = numSyllables-3;
			int paroxitona = numSyllables-2;
			int oxitona = numSyllables-1;
			
			tonicSyllIndex = paroxitona;
			if (hasAccentuation(ss.getSyllables().get(oxitona))) {
				tonicSyllIndex = oxitona;
			}
			else if (hasAccentuation(ss.getSyllables().get(paroxitona))) {
				tonicSyllIndex = paroxitona;
			}
			else if (numSyllables > 2 && hasAccentuation(ss.getSyllables().get(proparoxitona))) {
				tonicSyllIndex = proparoxitona;
			}
			else if (isOxitona(ss.getSyllables().get(oxitona))) {
				tonicSyllIndex = oxitona;	
			}
		}
		
		ss.setTonicSyllableIndex(tonicSyllIndex);
		return ss;
	}

	public String showSyllables() {
		
		StringBuilder sb = new StringBuilder();
		StringBuilder sbDebug = new StringBuilder();
		for (Syllable s : syllables) {
			if (s.getTonicity() == Tonicity.TONICA) {
				sbDebug.append("|" + s.getSyllable() + "| ");
			}
			else {
				sbDebug.append(s.getSyllable() + " ");
			}
			
			sb.append(s.showPhonemes());
		}
		
		return sb.toString();
	}
	
	public void parseSyllables(double frequency, int time) {
		for (Syllable s : syllables) {
			s.parsePhonemes(frequency, time);
		}
	}
	
	public String getWord() {
		return word;
	}
	
	public int getNumSyllables() {
		return numSyllables;
	}
	
	public List<Syllable> getSyllables() {
		return syllables;
	}
}
