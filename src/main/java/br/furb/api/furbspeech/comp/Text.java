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

import br.furb.api.furbspeech.util.Extenso;
import br.furb.api.furbspeech.util.WordsReplace;


/**
 * Represents the text object to be parsed.
 * @author Germano
 */
public class Text {

	private String text;
	private int numPhrases;
	private List<Phrase> phrases;
	
	public Text(String text) {
		super();
		this.text = text.toLowerCase();
		this.phrases = splitPhrasesOfThisText();
		this.numPhrases = this.phrases.size();
	}

	private List<Phrase> splitPhrasesOfThisText() {
		List<Phrase> phrases = new ArrayList<Phrase>();
		
		String textCopy = replaceNumericWords(replaceAbbreviations(text));
		String[] phrasesStr = textCopy.split("[.!?]");
		for (int i = 0; i < phrasesStr.length; i++) {
			String p = phrasesStr[i];
			
			if (p.length() < textCopy.length()) {
				int index = p.length();
				p = p.concat(String.valueOf(textCopy.charAt(index)));
				
				if (p.length()+1 <= textCopy.length()) {
					textCopy = textCopy.substring(index+1); // remove this phrase of the text (copy).
				}
			}
			
			if (p.length() != 0) {
				phrases.add(new Phrase(p));
			}
		}
		
		return phrases;
	}

	private String replaceNumericWords(String text) {
		String textToBeParsed = new String(text);
		
		String[] allWordsOfTheText = text.split(" ");
		for (String w : allWordsOfTheText) {
			if (isNumericWord(w)) {
				textToBeParsed = textToBeParsed.replace(w, Extenso.converte(Long.parseLong(w)));
			}
		}
		
		return textToBeParsed;
	}
	
	private boolean isNumericWord(String word) {
		// check if this word isn't a number.
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		
		return true;
	}

	private String replaceAbbreviations(String text) {
		String textToBeParsed = new String(text);
		WordsReplace wordsReplace = WordsReplace.getInstance();
		
		String[] allWordsOfTheText = text.split(" ");
		for (String w : allWordsOfTheText) {
			String replacedWord = wordsReplace.getReplacedWord(w);
			if (replacedWord != null) {
				textToBeParsed = textToBeParsed.replace(w, replacedWord);
			}
		}
		
		return textToBeParsed;
	}

	public String showPhrases() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("_ 300\n");
		for (Phrase p : phrases) {
			sb.append(p.showWords());
		}
		sb.append("_ 300\n");
		sb.append("_ 300\n");
		
		return sb.toString();
	}
	
	public void parsePhrases(double frequency, int time) {
		for (Phrase p : phrases) {
			p.parseWords(frequency, time);
		}
	}
	
	public String getText() {
		return text;
	}

	public int getNumPhrases() {
		return numPhrases;
	}

	public List<Phrase> getPhrases() {
		return phrases;
	}
}
