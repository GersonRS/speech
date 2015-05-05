package br.furb.api.furbspeech.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class UtilTests {

	public static boolean sameFileContents(File one, File two) {
		try {
			if (one.length() == two.length()) {
				FileInputStream oneInput = new FileInputStream(one);
				FileInputStream twoInput = new FileInputStream(two);

				for (long index = one.length(); index > 0; index--) {
					boolean same = (oneInput.read() == twoInput.read());
					if (!same) return false;
				}

				oneInput.close();
				twoInput.close();
			}
			else {
				return false;
			}
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return true;
	}
}
