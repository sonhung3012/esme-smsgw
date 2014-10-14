package i18n.com.fis.diction;

import java.io.Serializable;
import java.util.HashMap;

import com.fis.esme.admin.SessionData;
import com.vaadin.Application;

public class TM implements Serializable {
	protected static HashMap<String, Dictionary> translationDictionaries = new HashMap();

	public static String get(String key, Object[] params) {

		String phrase = getDictionary().get(key);

		if ((params != null) && (params.length > 0)) {
			return String.format(phrase, params);
		}

		return phrase;
	}

	public static String get(Application app, String key, Object[] params) {
		String phrase = getDictionary(app).get(app, key);
		System.out.println("phrase=" + phrase);
		if ((params != null) && (params.length > 0)) {
			return String.format(phrase, params);
		}

		return phrase;
	}

	public static void setDefaultLanguage(String lang) {
		getDictionary().setDefaultLanguage(lang);
	}

	public static Dictionary getDictionary() {
		return getDictionary(SessionData.getCurrentApplication());
	}

	public static Dictionary getDictionary(Application app) {
		if (!translationDictionaries.containsKey(app.getClass()
				.getCanonicalName())) {
			translationDictionaries.put(app.getClass().getCanonicalName(),
					new Dictionary());
		}
		return (Dictionary) translationDictionaries.get(app.getClass()
				.getCanonicalName());
	}
}
