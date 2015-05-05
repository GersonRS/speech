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
package br.furb.api.furbspeech.synth;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import br.furb.api.furbspeech.FurbSpeech;
import br.furb.api.furbspeech.util.ComponentUtils;
import br.furb.api.furbspeech.util.OSNotSupportedException;

/**
 * Represents the MBrolaSynthesizer. 
 * More informations about it: http://tcts.fpms.ac.be/synthesis/mbrola.html
 * @author Germano
 */
public class MBrolaSynthesizer implements Synthesizer {

	/**
	 * BR1 voice. Downloaded from Mbrola project web site.
	 */
	public static final File VOICE_BR1 = new File(ComponentUtils.getClearDirAbsolutePath(FurbSpeech.class.getClassLoader().getResource("synthesizer/mbrola/br1").getFile()));
	
	/**
	 * BR2 voice. Downloaded from Mbrola project web site.
	 */
	public static final File VOICE_BR2 = new File(ComponentUtils.getClearDirAbsolutePath(FurbSpeech.class.getClassLoader().getResource("synthesizer/mbrola/br2").getFile()));
	
	/**
	 * BR3 voice. Downloaded from Mbrola project web site.
	 */
	public static final File VOICE_BR3 = new File(ComponentUtils.getClearDirAbsolutePath(FurbSpeech.class.getClassLoader().getResource("synthesizer/mbrola/br3").getFile()));

	/**
	 * BR4 voice. Downloaded from Mbrola project web site.
	 */
	public static final File VOICE_BR4 = new File(ComponentUtils.getClearDirAbsolutePath(FurbSpeech.class.getClassLoader().getResource("synthesizer/mbrola/br4").getFile()));

	/**
	 * PT1 voice. Downloaded from Mbrola project web site.
	 */
	public static final File VOICE_PT1 = new File(ComponentUtils.getClearDirAbsolutePath(FurbSpeech.class.getClassLoader().getResource("synthesizer/mbrola/pt1").getFile()));
	
	private File voiceName;
	
	public void setVoice(File voiceName) {
		this.voiceName = voiceName;
	}

	public void synthesize(File inputFile, File outputFile) {
		// should set the default voice?
		if (voiceName == null) {
			voiceName = VOICE_BR1;
		}
		
		String cmdLine = "<mbrola-bin> -e <voice> <input> <output>";
		cmdLine = cmdLine.replace("<mbrola-bin>", getPreparedFilePath(getMBrolaAppAbsolutePath()))
						 .replace("<voice>", getPreparedFilePath(voiceName.getAbsolutePath()))
						 .replace("<input>", getPreparedFilePath(inputFile.getAbsolutePath()))
						 .replace("<output>", getPreparedFilePath(outputFile.getAbsolutePath()));
		try {
			Runtime.getRuntime().exec(cmdLine).waitFor();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private String getPreparedFilePath(String filePath) {
		String osName = (String) System.getProperties().get("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			return "\"".concat(filePath).concat("\"");
		}
		else {
			return filePath.replaceAll(" ", "\\ ");
		}
	}
	
	private String getMBrolaAppAbsolutePath() {
		String bin = null;
		String osName = (String) System.getProperties().get("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			bin = "mbrola.exe";
		}
		else if (osName.toLowerCase().indexOf("linux") > -1) {
			bin = "mbrola-linux-i386";
		}
		else if (osName.toLowerCase().indexOf("mac") > -1) {
			bin = "mbrola-darwin-ppc";
		}
		
		if (bin != null) {
			URL mbrolaBin = MBrolaSynthesizer.class.getClassLoader().getResource("synthesizer/mbrola/" + bin);
			return new File(ComponentUtils.getClearDirAbsolutePath(mbrolaBin.getFile())).getAbsolutePath();
		}
		else {
			throw new OSNotSupportedException();
		}
	}
	
	public File getVoice() {
		return voiceName;
	}
}
