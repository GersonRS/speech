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
/***************************************************************************
 *   Copyright (C) 2004 by Luiz Angelo Daros de Luca                       *
 *   luizd@inf.ufsc.br                                                     *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 * 
 *   Created on 16/07/2004
 **************************************************************************/

import java.math.BigInteger;

/**
 * @author Luiz Angelo Daros de Luca
 * @version 1.0
 *
 * Esta classe converte numero de 0 ateh 10^55 -1 
 * por extenso para o Português do Brasil. Para o português de Portugal, 
 * deve-se mudar os valores em ordensSingular e ordensPlural a partir de "Bilh(ão|ões)"
 * 
 * Caso encontrem erros, email-me por favor:
 * 
 * luizd@inf.ufsc.br
 */
public class Extenso {

	public static String ZERO = "zero";

	public static String[] unidades = {
		"", "um", "dois", "três", "quatro", "cinco", "seis", "sete", "oito", "nove", 
	    "dez", "onze", "doze", "treze", "quatorze","quinze","dezesseis","dezessete","dezoito","dezenove"
	};

	public static String[] dezenas = {
		"","", "vinte", "trinta", "quarenta", "cinqüenta", "sessenta", "setenta", "oitenta", "noventa", "cem"
	};

	public static String[] centenas = {
		"", "cento", "duzentos", "trezentos", "quatrocentos", "quinhentos", "seiscentos", "setecentos", "oitocentos", "novecentos"
	};

	public static String SEPARADOR_MENOR = " e ";	
	public static String SEPARADOR_MAIOR   = " , "; //2 espacos para ficar igual ao " e "

	public static String[] ordensSingular= {
		"", "mil", "milhão", "bilhão", "trilhão", "quatrilhão", "quintilhão", "sextilhão", "setilhão", "octilhão", "nonilhão", 
	   "decilhão", "undecilhão", "dodecilhão", "tredecilhão", "quatordecilhão", "quindecilhão", "sedecilhão", "septendecilhão"
	};

	public static String[] ordensPlural = {
		"", "mil", "milhões", "bilhões", "trilhões", "quatrilhões", "quintilhões", "sextilhões", "setilhões", "octilhões", 
		"decilhões", "undecilhões", "dodecilhões", "tredecilhões", "quatordecilhões", "quindecilhões", "sedecilhões", "septendecilhões" 
	};
	
	public static BigInteger CEM = BigInteger.valueOf(1000);
	public static BigInteger NUMERO_MAXIMO = new BigInteger("999999999999999999999999999999999999999999999999999999");

	
	private static String unidades(int numero) {
		if (numero == 0)
			return "";	
		return SEPARADOR_MENOR + unidades[numero];		
	}
	
	private static String dezenas(int numero) {
		if (numero == 0)
			return "";
		if (numero < 20)
			return unidades(numero);	
		return SEPARADOR_MENOR + dezenas[numero / 10] + unidades(numero % 10);		
	}

	private static String centenas(int numero) {
		if (numero == 0)
			return "";
		if (numero <= 100)
			return dezenas(numero); 			
		return SEPARADOR_MAIOR + centenas[numero / 100] + dezenas(numero % 100);		
	}
				
	private static String ordens(long numero, int grandeza) {				
		if (numero == 0)
			return "";
		if (numero < 1000)
			return centenas ((int)numero);
		
		int menor = (int)(numero % 1000);			
		long maior  = numero / 1000;
		int proximoMenor = (int)(maior % 1000);		
		
		if (proximoMenor == 0)
			return ordens(maior,grandeza+1) + centenas(menor);		
		if (proximoMenor == 1)
			return ordens(maior,grandeza+1) + " " + ordensSingular[grandeza] + centenas(menor);
		return ordens(maior,grandeza+1) + " " + ordensPlural[grandeza] + centenas(menor);					 			
	}

	private static String ordens(BigInteger numero, int grandeza) {				
		if (numero.equals(BigInteger.ZERO))
			return "";
		if (numero.compareTo(CEM) == -1)
			return centenas ((int)numero.longValue());
		
		BigInteger[] resultado = numero.divideAndRemainder(CEM);
		
		int menor = (int)resultado[1].longValue();			
		BigInteger maior  =  resultado[0];
		int proximoMenor = (int)maior.remainder(CEM).longValue();		
		
		if (proximoMenor == 0)
			return ordens(maior,grandeza+1) + centenas(menor);		
		if (proximoMenor == 1)
			return ordens(maior,grandeza+1) + " " + ordensSingular[grandeza] + centenas(menor);
		return ordens(maior,grandeza+1) + " " + ordensPlural[grandeza] + centenas(menor);					 			
	}
	
	public static String converte(long numero) {
		if (numero == 0) 
			return ZERO;
		return ordens(numero,1).substring(3).replaceAll(" ,",","); // tira o espaco extra
	}

	public static String converte(BigInteger numero) {
		if (numero.equals(BigInteger.ZERO)) 
			return ZERO;
		if (NUMERO_MAXIMO.compareTo(numero) < 0)
			throw new RuntimeException("Numero acima do permitido"); 	
		return ordens(numero,1).substring(3).replaceAll(" ,",","); // tira o espaco extra
	}
	
	

}
