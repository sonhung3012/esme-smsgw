package i18n.com.fis.diction;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;

import com.fis.esme.admin.SessionData;
import com.vaadin.Application;

public class Dictionary implements Serializable {
	protected HashMap<String, Properties> languages = new HashMap();

	protected HashMap<String, Properties> countries = new HashMap();

	private String defaultLanguage = "en";

	public String get(String key) {
		return get(SessionData.getCurrentApplication(), key);
	}

	public String get(Application app, String key) {
		Locale locale = app.getLocale();
		System.out.println("locale=" + locale);
		return get(locale.getLanguage(), locale.getCountry(), key);
	}

	public String get(Locale locale, String key) {
		return get(locale.getLanguage(), locale.getCountry(), key);
	}

	public String get(String lang, String country, String key) {
		Properties bundle = getLanguageBundle(lang, country);
		return bundle.getProperty(
				key,
				getLanguageBundle(getDefaultLanguage(), "").getProperty(key,
						key));
	}

	public void loadWords(String lang, String country, URL resource,
			boolean overwrite) throws IOException {
		Properties language = getLanguageBundle(lang, country);

		if ((!language.containsKey(resource.toString())) || (overwrite)) {
			language.put(resource.toString(), "");
			loadWords(lang, country,
					new InputStreamReader(resource.openStream(), "utf-8"));
		}
	}

	public void loadWords(String lang, String country, InputStream data)
			throws IOException {
		Properties language = getLanguageBundle(lang, country);

		language.load(data);
		try {
			data.close();
		} catch (Throwable err) {
		}
	}

	public void loadWords(String lang, String country, Reader data)
			throws IOException {
		Properties language = getLanguageBundle(lang, country);
		language.load(data);
		try {
			data.close();
		} catch (Throwable err) {
		}
	}

	public void loadWords(String lang, String country, File file,
			boolean overwrite) throws IOException {
		Properties language = getLanguageBundle(lang, country);

		if ((!language.containsKey(file.getAbsolutePath())) || (overwrite)) {
			language.put(file.getAbsolutePath(), "");
			loadWords(lang, country, new InputStreamReader(new FileInputStream(
					file), "utf-8"));
		}
	}

	private Properties getLanguageBundle(String lang, String country) {
		if ((lang == null) || ("".equalsIgnoreCase(lang))) {
			throw new IllegalArgumentException(
					"Language code cannot be null or empty. " + lang);
		}

		String _language = lang.toLowerCase();
		String _country = country == null ? "" : country.toLowerCase();

		Properties bundle = (Properties) this.countries.get(_language + "_"
				+ _country);

		if (bundle == null) {
			bundle = (Properties) this.languages.get(_language);
		}

		if (bundle == null) {
			bundle = new Properties();
			this.languages.put(_language, bundle);
			if (!"".equals(_country)) {
				this.countries.put(_language + "_" + _country, bundle);
			}
		}

		return bundle;
	}

	public String getDefaultLanguage() {
		return this.defaultLanguage;
	}

	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	public void loadTranslationFilesFromThemeFolder(File themeFolder)
			throws IOException {
		File INFolder = new File(themeFolder, "i18n");

		if ((INFolder.exists()) && (INFolder.isDirectory())) {
			File[] dirs = INFolder.listFiles();

			for (File dir : dirs) {
				if (!dir.isDirectory())
					continue;
				loadTranslationsFromLanguageDirectory(dir);
			}
		}
	}

	public void loadTranslationsFromLanguageDirectory(File folder)
			throws IOException {
		File[] files = folder.listFiles();

		String languageCode = extractLanguageCodeFromString(folder.getName());
		String countryCode = extractCountryCodeFromString(folder.getName());

		for (File file : files) {
			if (!file.getName().toLowerCase().endsWith(".properties"))
				continue;
			loadWords(languageCode, countryCode, file, true);
		}
	}

	protected String extractCountryCodeFromString(String name) {
		try {
			StringTokenizer tok = new StringTokenizer(name, "_", false);
			tok.nextToken();
			return tok.nextToken();
		} catch (Throwable err) {
		}
		return null;
	}

	protected String extractLanguageCodeFromString(String name) {
		try {
			StringTokenizer tok = new StringTokenizer(name, "_", false);
			return tok.nextToken();
		} catch (Throwable err) {
		}
		return null;
	}

	public void clear() {
		this.languages.clear();
		this.countries.clear();
	}
}
