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
package br.furb.api.furbspeech.util;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Singleton to manage the words replacing.
 * @author Germano
 */
public class WordsReplace {

	private static WordsReplace instance;
	private Properties wordsReplaceFileProperties;
	
	static {
		if (instance == null) {
			instance = new WordsReplace();
		}
	}
	
	private WordsReplace() {
		URL fileProperties = WordsReplace.class.getClassLoader().getResource("words-replace.properties");
		if (fileProperties != null) {
			wordsReplaceFileProperties = new Properties();
			try {
				wordsReplaceFileProperties.load(fileProperties.openStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getReplacedWord(String keyWord) {
		if (wordsReplaceFileProperties != null) {
			return wordsReplaceFileProperties.getProperty(keyWord);
		}
		return null;
	}
	
	public static WordsReplace getInstance() {
		return instance;
	}
}
