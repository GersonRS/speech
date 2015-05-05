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

/**
 * Represents a phoneme object of a syllable.
 * @author Germano
 */
public class Phoneme {

	private String phoneme;
	private int time;
	private double frequency;
	
	public Phoneme(String phoneme) {
		this.phoneme = phoneme;
		this.time = 0;
		this.frequency = 0;
	}
	
	public String showPhoneme() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.phoneme);
		sb.append(" ");
		sb.append(this.time);
		sb.append(" 100 ");
		sb.append(this.frequency);
		sb.append("\n");
		
		return sb.toString();
	}
	
	public void parsePhoneme(double frequency, int time) {
		this.frequency = frequency;
		this.time = time;
	}

	public String getPhoneme() {
		return phoneme;
	}

	public int getTime() {
		return time;
	}

	public double getFrequency() {
		return frequency;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
}
