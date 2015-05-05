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
package br.furb.api.furbspeech;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import br.furb.api.furbspeech.comp.Text;
import br.furb.api.furbspeech.synth.MBrolaSynthesizer;
import br.furb.api.furbspeech.synth.Synthesizer;
import br.furb.api.furbspeech.synth.SynthesizerFactory;
import br.furb.api.furbspeech.util.ComponentUtils;

/**
 * Main class of this API.<br/><br/>
 * DSL (Domain-Specific-Language) usage:<br/><br/>
 * <code>
 * 	  // basic way<br/>
 *    new FurbSpeech().text("some text in portuguese language").to().speech();<br/><br/>
 *    
 *    // defining the voice<br/>
 *    new FurbSpeech().text("some text in portuguese language").withVoice(filePointingToTheVoice).to().speech();<br/><br/>
 *    
 *    // output to WAV file<br/>
 *    new FurbSpeech().text("some text in portuguese language").to("tts.wav").speech();
 * </code>
 * @author Germano
 */
public class FurbSpeech {
	
	private static final String DEFAULT_OUTPUT_FILENAME = "speech.wav";
	
	private String text;
	private File voiceFile;
	private String fileName;
	
	public FurbSpeech() {
		super();
	}
	
	/**
	 * Defines the text of the operation.
	 * @param text
	 * @return
	 */
	public FurbSpeech text(String text) {
		this.text = text;
		return this;
	}
	
	/**
	 * Defines the voice to be used by sinthesizer application.
	 * @param voiceFile
	 * @return
	 */
	public FurbSpeech withVoice(File voiceFile) {
		this.voiceFile = voiceFile;
		return this;
	}
	
	/**
	 * Defines the default output WAV filename.
	 * @return
	 */
	public FurbSpeech to() {
		fileName = DEFAULT_OUTPUT_FILENAME;
		return this;
	}
	
	/**
	 * Defines the output WAV filename.
	 * @param outputType
	 * @param fileName Relative to the user project binary directory.
	 * @return
	 */
	public FurbSpeech to(String fileName) {
		this.fileName = fileName.startsWith("/") ? fileName.substring(1) : fileName;
		return this;
	}
	
	/**
	 * Speech the text based on previous defined text, output file and voice.
	 */
	public File speech() {
		checkAttributes();
		
		Text text = new Text(this.text);
		text.parsePhrases(ComponentUtils.BASE_FREQUENCY, ComponentUtils.BASE_TIME);
		String output = text.showPhrases();
		
		File synthInput = writeOutputInTheFile(output);
		 
		File outputSpeechFile = new File(ComponentUtils.getClearDirAbsolutePath(FurbSpeech.class.getClassLoader().getResource("output").getFile() + "/" + this.fileName));
		
		Synthesizer synthesizer = SynthesizerFactory.getSynthesizer();
		if (voiceFile != null) {
			synthesizer.setVoice(voiceFile);
		}
		synthesizer.synthesize(synthInput, outputSpeechFile);
		
		return outputSpeechFile;
	}

	private File writeOutputInTheFile(String output) {
		File outputDir = new File(ComponentUtils.getClearDirAbsolutePath(FurbSpeech.class.getClassLoader().getResource(".").getFile()).concat("/output/"));
		outputDir.mkdir();
		File fileOutput = new File(ComponentUtils.getClearDirAbsolutePath(FurbSpeech.class.getClassLoader().getResource("output").getFile()) + "/output.pho");
		try {
			if (!fileOutput.exists()) {
				fileOutput.createNewFile();
			}
			FileWriter fw = new FileWriter(fileOutput);
			fw.write(output);
			fw.close();
			return fileOutput;
		}
		catch (IOException e) {
			return null;
		}
	}
	
	private void checkAttributes() {
		if (this.text == null) {
			throw new IllegalStateException("The text must be previously defined. Before this invocation, call the method text(String)");
		}
	
		if (this.fileName == null) {
			throw new IllegalStateException("The output WAV file name must be previously defined. Before this invocation, call the method to() or to(String)");
		}
	}
	public static void main(String[] args) {
//		Testes de voz
//		new FurbSpeech().text("João Maria josé da Silva Santos").withVoice(MBrolaSynthesizer.VOICE_BR1).to("myFile1.wav").speech();
//		new FurbSpeech().text("João Maria josé da Silva Santos").withVoice(MBrolaSynthesizer.VOICE_BR2).to("myFile2.wav").speech();
//		new FurbSpeech().text("João Maria josé da Silva Santos").withVoice(MBrolaSynthesizer.VOICE_BR3).to("myFile3.wav").speech();
//		new FurbSpeech().text("João Maria josé da Silva Santos").withVoice(MBrolaSynthesizer.VOICE_BR4).to("myFile4.wav").speech();
//		new FurbSpeech().text("João Maria josé da Silva Santos").withVoice(MBrolaSynthesizer.VOICE_PT1).to("myFile5.wav").speech();
	}
}
