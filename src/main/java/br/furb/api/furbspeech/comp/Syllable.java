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

import static br.furb.api.furbspeech.util.ComponentUtils.REGULAR_CONSONANTS;
import static br.furb.api.furbspeech.util.ComponentUtils.convert;
import static br.furb.api.furbspeech.util.ComponentUtils.getVogalsInBasic;
import static br.furb.api.furbspeech.util.ComponentUtils.getVogalsInPhonemes;
import static br.furb.api.furbspeech.util.ComponentUtils.hasAccentuation;
import static br.furb.api.furbspeech.util.ComponentUtils.inArray;
import static br.furb.api.furbspeech.util.ComponentUtils.isNasal;
import static br.furb.api.furbspeech.util.ComponentUtils.isVogal;

import java.util.ArrayList;
import java.util.List;

import br.furb.api.furbspeech.util.ComponentUtils;

/**
 * Represents a syllable object of a word.
 * @author Germano
 */
public class Syllable {

	public enum Tonicity {
		TONICA,
		ATONA
	}
	
	private String syllable;
	private Tonicity tonicity;
	private int numPhonemes;
	private List<Phoneme> phonemes;
	
	public Syllable(String syllable, Tonicity tonicity) {
		this.syllable = syllable;
		this.tonicity = tonicity;
		this.phonemes = splitPhonemesOfThisSyllable();
		this.numPhonemes = phonemes.size();
	}
	
	private List<Phoneme> splitPhonemesOfThisSyllable() {
		List<Phoneme> phonemes = new ArrayList<Phoneme>();
		
		String syllable = " " + this.syllable + " ";
		int syllLenght = syllable.length();
		for (int i = 0; i < syllLenght; i++) {
			String syllChar = String.valueOf(syllable.charAt(i));
			String prevSyllChar = "";
			String nextSyllChar = "";
			String nextNextSyllChar = ""; 
			if (i > 0) {
				prevSyllChar = String.valueOf(syllable.charAt(i-1));
			}
			if (i + 1 < syllLenght) {
				nextSyllChar = String.valueOf(syllable.charAt(i+1));
			}
			if (i + 2 < syllLenght) {
				nextNextSyllChar = String.valueOf(syllable.charAt(i+2));
			}
			
			String sound = "_";
			String next = "";
			boolean shouldAddPhoneme = true;
			
			if (isVogal(syllChar)) {
				String nextLetter;
				sound = convert(syllChar, getVogalsInPhonemes());
				if (inArray(nextSyllChar, new String[] {"m", "n"}) && convert(nextNextSyllChar, getVogalsInBasic()).equals("_") && !nextNextSyllChar.equals("h")) {
					i++;
					if (hasAccentuation(syllChar)) {
						sound = convert(syllChar, getVogalsInBasic());
					}
					sound += "m";
				}
				else if (isNasal(syllChar) && !(nextLetter = convert(nextSyllChar, getVogalsInBasic())).equals("_")) {
					i++;
					next = nextLetter + "m";
				}
			}
			else if (inArray(syllChar, REGULAR_CONSONANTS)) {
				sound = new String(syllChar);
			}
			else if (syllChar.equals(" ") || syllChar.equals("-")) {
				shouldAddPhoneme = false;
			}
			else {
				char letterChar = syllChar.charAt(0); // the only one character of this String.
				switch (letterChar) {
					case 'c': {
						sound = "k";
						if (inArray(convert(nextSyllChar, getVogalsInBasic()), new String[] {"a", "o", "u"})) {
							sound = "k";
						}
						else if (inArray(convert(nextSyllChar, getVogalsInBasic()), new String[] {"e", "i"})) {
							sound = "s";
						} 
						else if (nextSyllChar.equals("h")) {
							sound = "x";
							i++;
						}
						else if (inArray(nextSyllChar, ComponentUtils.CONSONANTS)) {
							sound = "k";
						}
						break;
					}
					case 'g': {
						sound = "g";
						if (inArray(convert(nextSyllChar, getVogalsInBasic()), new String[] {"a", "o"})) {
							sound = "g";
						}
						else if (inArray(convert(nextSyllChar, getVogalsInBasic()), new String[] {"e", "i"})) {
							sound = "j";
						} 
						else if (nextSyllChar.equals("u")) {
							if (inArray(convert(nextNextSyllChar, getVogalsInBasic()), new String[] {"e", "i"})) {
								sound = "g";
								i++;
							} 
						}
						break;
					}
					
					case 'ç': {
						sound = "x";
						break;
					}
					
					case 'h': {
						shouldAddPhoneme = false;
						break;
					}
					
					case 'l': {
						sound = "l";
						if (nextSyllChar.equals("h")) {
							nextSyllChar = "i";
						}
						
						if (inArray(nextSyllChar, ComponentUtils.CONSONANTS) || !ComponentUtils.isLetter(nextSyllChar)) {
							sound = "u";
						}
						break;
					}
					
					case 'n': {
						sound = "n";
						if (nextSyllChar.equals("h")) {
							i++;
							sound = "im";
						}
						
						break;
					}
					
					case 'q': {
						sound = "k";
						if (nextSyllChar.equals("u") && inArray(convert(nextNextSyllChar, getVogalsInBasic()), new String[] {"e", "i"})) {
							i++;
						}
						break;
					}
					
					case 'r': {
						sound = "r2";
						if (nextSyllChar.equals("r")) {
							sound = "rr";
							i++;
						}
						else if (prevSyllChar.equals(" ") && i != 1) {
							sound = "rr";
						}
						else if (nextSyllChar.equals(" ") || inArray(nextSyllChar, ComponentUtils.CONSONANTS)) {
							sound = "r2";
						}
						else if (!convert(nextSyllChar, getVogalsInBasic()).equals("_")) {
							sound = "r";
						}
						break;
					}
					
					case 's': {
						sound = "s2";
						if (nextSyllChar.equals("s")) {
							sound = "s";
							i++;
						}
						else if (nextSyllChar.equals("c") && isVogal(nextNextSyllChar)) {
							if (inArray(convert(nextNextSyllChar, getVogalsInBasic()), new String[] {"a", "o", "u"})) {
								sound = "s2";
							}
							else {
								sound = "s";
								i++;
							}
						}
						else if (prevSyllChar.equals(" ")) {
							sound = "s";
						}
						else if ((nextSyllChar.equals(" ")) || (inArray(nextSyllChar, ComponentUtils.CONSONANTS))) {
							sound = "s2";
						}
						else if (convert(nextSyllChar, getVogalsInBasic()).equals("_")) {
							sound = "z";
						}
						break;
					}
					
					case 'x': {
						sound = "x";
						if (nextSyllChar.equals("c")) {
							sound = "s";
							i++;
						}
						break;
					}
					
					case 'z': {
						sound = "z";
						if ((nextSyllChar.equals(" ")) || (inArray(nextSyllChar, ComponentUtils.CONSONANTS))) {
							sound = "s2";
						}
						break;
					}
				}
			}
			
			if (shouldAddPhoneme) {
				phonemes.add(new Phoneme(sound));
			}
			
			if (!next.equals("")) {
				phonemes.add(new Phoneme(next));
			}
		}
		return phonemes;
	}
	
	public String showPhonemes() {
		StringBuilder sb = new StringBuilder();
		for (Phoneme p : phonemes) {
			sb.append(p.showPhoneme());
		}
		
		return sb.toString();
	}
	
	public void parsePhonemes(double frequency, int time) {
		if (this.tonicity == Tonicity.TONICA) {
			for (Phoneme p : this.phonemes) {
				p.parsePhoneme(frequency, time + 15);
			}
		}
		else
		{
			for (Phoneme p : this.phonemes) {
				p.parsePhoneme(frequency, time);
			}
		}
	}

	public String getSyllable() {
		return syllable;
	}
	
	public Tonicity getTonicity() {
		return tonicity;
	}
	
	public int getNumPhonemes() {
		return numPhonemes;
	}
	
	public List<Phoneme> getPhonemes() {
		return phonemes;
	}	
}
