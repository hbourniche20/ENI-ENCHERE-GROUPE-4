package fr.eni.enchere.util;

import javax.servlet.http.HttpServletRequest;

import fr.eni.enchere.exception.WrongInputException;


public class TextInputUtil {

	private static final Character[] NOT_ALLOWED_CHARACTERS = {'<', '>', '{', '}', '/'};
	
	public static String getSafeParameter(HttpServletRequest request, String paramLabel) throws WrongInputException {
		String input = request.getParameter(paramLabel);
		for(Character disabledChar: NOT_ALLOWED_CHARACTERS) {
			if(input.contains(disabledChar + "")) {
				throw new WrongInputException(disabledChar);
			}
		}
		return input;
	}

}
