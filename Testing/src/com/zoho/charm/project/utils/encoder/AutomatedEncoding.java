package com.zoho.charm.project.utils.encoder;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutomatedEncoding {

	public static final Logger LOGGER = Logger.getLogger(AutomatedEncoding.class.getName());

	public static Pattern pickScriplets = Pattern.compile("<%=([^<%=]*)%>");
	public static Pattern pickCOutTags = Pattern.compile("<c:out([^c:out]*)/>");

	public static Pattern scriptletValue1 = Pattern.compile("value=\"([^\"]*)\"");
	public static Pattern scriptletValue2 = Pattern.compile("value='([^']*)'");
	public static Pattern scriptletId1 = Pattern.compile("id=\"([^\"]*)\"");
	public static Pattern scriptletId2 = Pattern.compile("id='([^\']*)'");
	public static Pattern scriptletName1 = Pattern.compile("name=\"([^\"]*)\"");
	public static Pattern scriptletName2 = Pattern.compile("name='([^\']*)'");
	public static Pattern scriptletClass1 = Pattern.compile("class=\"([^\"]*)\"");
	public static Pattern scriptletClass2 = Pattern.compile("class='([^\']*)'");
	public static Pattern scriptletStyle1 = Pattern.compile("style=\"([^\"]*)\"");
	public static Pattern scriptletStyle2 = Pattern.compile("style='([^\']*)'");
	public static Pattern scriptletOnclick = Pattern.compile("onclick=\"([^\"]*)\"");
	public static Pattern scriptletOnclick2 = Pattern.compile("onclick='([^\']*)'");
	public static Pattern scriptletLabel1 = Pattern.compile("label=\"([^\"]*)\"");
	public static Pattern scriptletLabel2 = Pattern.compile("label='([^']*)'");
	public static Pattern scriptletFor1 = Pattern.compile("for=\"([^\"]*)\"");
	public static Pattern scriptletFor2 = Pattern.compile("for='([^']*)'");
	public static Pattern scriptletHref1 = Pattern.compile("href=\"([^\"]*)\"");
	public static Pattern scriptletHref2 = Pattern.compile("href='([^']*)'");
	public static Pattern scriptletTitle1 = Pattern.compile("title=\"([^\"]*)\"");
	public static Pattern scriptletTitle2 = Pattern.compile("title='([^']*)'");
	public static Pattern scriptletType1 = Pattern.compile("type=\"([^\"]*)\"");
	public static Pattern scriptletType2 = Pattern.compile("type='([^']*)'");
	public static Pattern scriptletPosition1 = Pattern.compile("position=\"([^\"]*)\"");
	public static Pattern scriptletPosition2 = Pattern.compile("position='([^']*)'");
	public static Pattern scriptletCustomText1 = Pattern.compile("customtext=\"([^\"]*)\"");
	public static Pattern scriptletCustomText2 = Pattern.compile("customtext='([^']*)'");
	// public static Pattern scriptletURL = Pattern.compile("=<%=(.*?)%>");
	public static Pattern scriptletHTML = Pattern.compile(">\\s?<%=(.*?)%>");

	public static Pattern scriptletCSS = Pattern.compile(":<%=([^<%=]*)%>");
	public static Pattern scriptletURL = Pattern.compile("=<%=([^<%=]*)%>");

	public static Pattern coutHTMLPattern1 = Pattern.compile("&nbsp;<c:out(.*?)\\/>");
	public static Pattern coutHTMLPattern2 = Pattern.compile("><c:out(.*?)\\/>");

	public static String doOutputEncoding(String line) {

		if (CheckEncoding.isOutputEncodingNeeded(line)) {

			line = encodeAllTagsInAttribute(line, "id", "encodeHTMLAttribute", Boolean.FALSE);
			line = encodeAllTagsInAttribute(line, "name", "encodeHTMLAttribute", Boolean.FALSE);
			line = encodeAllTagsInAttribute(line, "class", "encodeHTMLAttribute", Boolean.FALSE);
			line = encodeAllTagsInAttribute(line, "label", "encodeHTMLAttribute", Boolean.FALSE);
			line = encodeAllTagsInAttribute(line, "for", "encodeHTMLAttribute", Boolean.FALSE);
			line = encodeAllTagsInAttribute(line, "width", "encodeHTMLAttribute", Boolean.FALSE);
			line = encodeAllTagsInAttribute(line, "height", "encodeHTMLAttribute", Boolean.FALSE);
			line = encodeAllTagsInAttribute(line, "userspace", "encodeHTMLAttribute", Boolean.FALSE);
			line = encodeAllTagsInAttribute(line, "rowspan", "encodeHTMLAttribute", Boolean.FALSE);
			line = encodeAllTagsInAttribute(line, "colspan", "encodeHTMLAttribute", Boolean.FALSE);
			line = encodeAllTagsInAttribute(line, "style", "encodeHTMLAttribute", Boolean.FALSE);
			line = encodeAllTagsInAttribute(line, "data", "encodeHTMLAttribute", Boolean.FALSE);
			line = encodeAllTagsInAttribute(line, "filePath", "encodeHTMLAttribute", Boolean.FALSE);
			line = encodeAllTagsInAttribute(line, "onclick", "encodeJavaScript", Boolean.FALSE);
			line = encodeAllTagsInAttribute(line, "onchange", "encodeJavaScript", Boolean.FALSE);
			line = encodeAllTagsInAttribute(line, "onkeypress", "encodeJavaScript", Boolean.FALSE);
			line = encodeAllTagsInAttribute(line, "onblur", "encodeJavaScript", Boolean.FALSE);
			line = encodeAllTagsInAttribute(line, "action", "encodeHTMLAttribute", Boolean.FALSE);
			line = encodeAllTagsInAttribute(line, "src", "encodeHTMLAttribute", Boolean.FALSE);

			line = encodeLine(line, scriptletValue1, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletValue2, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletId1, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletId2, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletName1, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletName2, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletClass1, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletClass2, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletStyle1, scriptletCSS, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletStyle2, scriptletCSS, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletStyle1, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletStyle2, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletOnclick, scriptletURL, "encodeURL", Boolean.TRUE);
			line = encodeLine(line, scriptletOnclick2, scriptletURL, "encodeURL", Boolean.TRUE);
			line = encodeLine(line, scriptletOnclick, null, "encodeJavaScript", Boolean.TRUE);
			line = encodeLine(line, scriptletOnclick2, null, "encodeJavaScript", Boolean.TRUE);
			line = encodeLine(line, scriptletLabel1, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletLabel2, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletFor1, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletFor2, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletTitle1, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletTitle2, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletType1, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletType2, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletPosition1, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletPosition2, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletCustomText1, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletCustomText2, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletHref1, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletHref2, null, "encodeHTMLAttribute", Boolean.TRUE);
			line = encodeLine(line, scriptletHTML, null, "encodeHTML", Boolean.FALSE);

			line = checkIfOnlyHTML(line);

			if (line.contains("c:out")) {
				line = encodeAllTagsInAttribute(line, "style", "htmlAttr");
				line = encodeAllTagsInAttribute(line, "class", "htmlAttr");
				line = encodeAllTagsInAttribute(line, "id", "htmlAttr");
				line = encodeAllTagsInAttribute(line, "name", "htmlAttr");
				line = encodeAllTagsInAttribute(line, "onclick", "js");
				line = encodeAllTagsInAttribute(line, "onchange", "js");
				line = encodeAllTagsInAttribute(line, "onmouseover", "js");
				line = encodeAllTagsInAttribute(line, "onmouseout", "js");
				line = encodeAllTagsInAttribute(line, "onkeyup", "js");
				line = encodeAllTagsInAttribute(line, "href", "url");
				line = encodeAllTagsInAttribute(line, "src", "url");
				line = encodeAllTagsInAttribute(line, "action", "url");
				line = encodeAllTagsInAttribute(line, "for", "htmlAttr");
				line = encodeAllTagsInAttribute(line, "label", "htmlAttr");
				line = encodeAllTagsInAttribute(line, "title", "htmlAttr");
				line = encodeAllTagsInAttribute(line, "type", "htmlAttr");
				line = encodeAllTagsInAttribute(line, "customtext", "htmlAttr");
				line = encodeAllTagsInAttribute(line, "position", "htmlAttr");
				line = encodeAllTagsInAttribute(line, "placeholder", "htmlAttr");
				line = encodeAllTagsInAttribute(line, "maxlength", "htmlAttr");
				line = encodeAllTagsInAttribute(line, "userspace", "htmlAttr");

			}

			line = encodeValue(line);

			List<String> list = new ArrayList<>();

			list = getCOUTHTMLPatterns1(line);

			for (String s : list) {
				//
				String s1 = s.trim().toLowerCase();
				if (!s1.startsWith("server_path") && !s1.startsWith("iamencoder") && !s1.startsWith("i18n")
						&& !s1.startsWith("resourcepath") && !s1.startsWith("securityutil") && !s1.startsWith("csrf")
						&& !s1.startsWith("dateformat") && !s1.startsWith("charmutil") && !s1.contains(".getmsg")) {
					// System.out.println("8:Before :" + line + "\n" + s);
					line = line.replace("<c:out" + s + "/>", "<enc:html" + s + "/>");
					// System.out.println("After :" + line);
					Encode.includeTld = Boolean.TRUE;
				}

			}
			list = getCOUTHTMLPatterns2(line);

			for (String s : list) {
				//
				String s1 = s.trim().toLowerCase();
				if (!s1.startsWith("server_path") && !s1.startsWith("iamencoder") && !s1.startsWith("i18n")
						&& !s1.startsWith("resourcepath") && !s1.startsWith("securityutil") && !s1.startsWith("csrf")
						&& !s1.startsWith("dateformat") && !s1.startsWith("charmutil") && !s1.contains(".getmsg")) {
					// System.out.println("8:Before :" + str + "\n" + s);
					line = line.replace("<c:out" + s + "/>", "<enc:html" + s + "/>");
					// System.out.println("After :" + str);
					Encode.includeTld = Boolean.TRUE;
				}

			}
		}
		return line;

	}

	private static String checkIfOnlyHTML(String line) {
		if (line.trim().startsWith("<%=") && line.endsWith("%>")) {
			Matcher matcher = pickScriplets.matcher(line);

			while (matcher.find()) {
				line = getEncodedString(matcher.group(1), line, "encodeHTML");
			}
		}
		return line;
	}

	public static ArrayList<String> getCOUTHTMLPatterns1(String line) {
		//
		ArrayList<String> list = new ArrayList<String>();
		Matcher matcher = coutHTMLPattern1.matcher(line);

		while (matcher.find()) {
			list.add(matcher.group(1));
		}

		return list;
	}

	public static ArrayList<String> getCOUTHTMLPatterns2(String line) {
		//
		ArrayList<String> list = new ArrayList<String>();
		Matcher matcher = coutHTMLPattern2.matcher(line);

		while (matcher.find()) {
			list.add(matcher.group(1));
		}

		return list;
	}

	public static String getEncodedString(String matchedText, String attribute, String encodingType) {

		String inputString = matchedText.toLowerCase().trim();
		if (!inputString.contains("server_path") && !inputString.contains("iamencoder")
				&& !inputString.startsWith("i18n") && !inputString.startsWith("resourcepath")
				&& !inputString.contains("securityutil") && !inputString.contains("csrf")
				&& !inputString.contains("dateformat") && !inputString.startsWith("charmutil")
				&& !inputString.contains(".getmsg") && inputString.length() > 1) {

			if(inputString.contains("?") && inputString.contains(":") ) {
				String[] values = matchedText.split("?")[1].split(":");
				String trueValue = values[1];
				String falseValue = values[2];
				if( !(trueValue.trim().startsWith("\"") || trueValue.trim().endsWith("\"")) ) {
					attribute = attribute.replace("?".concat(trueValue), "? <%= IAMEncoder." + encodingType + "( String.valueOf( " + trueValue.trim() + " )) %>");
					Encode.includeIAM = Boolean.TRUE;
				}
				if( !(falseValue.trim().startsWith("\"") || falseValue.trim().endsWith("\"")) ) {
					attribute = attribute.replace(":".concat(falseValue), ": <%= IAMEncoder." + encodingType + "( String.valueOf( " + falseValue.trim() + " )) %>");
					Encode.includeIAM = Boolean.TRUE;
				}
			}else if(inputString.contains("urlencoder")) {
				attribute = attribute.replace("<%=" + matchedText + "%>",
						"<%= IAMEncoder." + encodingType + "( " + matchedText.trim().replace("URLEncoder.encode(", "") + " %>");
				Encode.includeIAM = Boolean.TRUE;
			} else {
				attribute = attribute.replace("<%=" + matchedText + "%>",
						"<%= IAMEncoder." + encodingType + "( String.valueOf( " + matchedText.trim() + " )) %>");
				Encode.includeIAM = Boolean.TRUE;
			}
			
		}
		return attribute;
	}

	public static String encodeScripplets(Pattern pattern, String matchedText, String encodingType) {

		Matcher matcher = null;
		if (pattern != null) {
			matcher = pattern.matcher(matchedText);
		} else {
			matcher = pickScriplets.matcher(matchedText);
		}
		while (matcher.find()) {
			matchedText = getEncodedString(matcher.group(1), matchedText, encodingType);
		}
		return matchedText;
	}

	public static String encodeLine(String line, String pattern, String encodingType, Boolean isEntireAttribute) {

		return encodeLine(line, Pattern.compile(pattern), null, encodingType, isEntireAttribute);
	}

	public static String encodeLine(String line, Pattern pattern, Pattern pattern2, String encodingType,
			Boolean isEntireAttribute) {

		Matcher matcher = pattern.matcher(line);

		while (matcher.find()) {

			if (isEntireAttribute) {
				String result = null;
				result = encodeScripplets(pattern2, matcher.group(1), encodingType);
				line = line.replace(matcher.group(1), result);
			} else {
				line = getEncodedString(matcher.group(1), line, encodingType);
			}

		}

		return line;
	}

	public static String getAttribute(String line, String attribute) {

		Integer startIndex = line.indexOf(attribute);
		if (startIndex != -1) {
			Character terminator = attribute.charAt(attribute.length() - 1);
			Integer endIndex = null;
			for (Integer index = startIndex + attribute.length(); index < line.length(); index++) {
				if (terminator == line.charAt(index)) {
					endIndex = index;
					break;
				}
				if (line.charAt(index) == '<') {
					index = line.substring(index).indexOf(">") + index;
				}
			}
			if (endIndex != null) {
				return line.substring(startIndex + attribute.length(), endIndex);
			} else {
				return line.substring(startIndex + attribute.length());
			}
		}
		return null;

	}

	public static String encodeAllTagsInAttribute(String line, String attribute, String encodingType) {
		return encodeAllTagsInAttribute(line, attribute, encodingType, Boolean.TRUE);
	}

	public static String encodeAllTagsInAttribute(String line, String attribute, String encodingType, Boolean isJSTL) {

		String[] attributeTypes = { " = \"", "=\"", "= \"", " =\"", " = '", "='", "= '", " ='" };

		for (String type : attributeTypes) {
			String result = getAttribute(line, attribute.concat(type));
			if (result != null) {
				String replacedText = null;
				if (isJSTL) {
					replacedText = getReplacedTextForCOut(result, encodingType);
					Encode.includeTld = Boolean.TRUE;
				} else {
					replacedText = getReplacedTextForScripplet(result, encodingType, attribute);
					Encode.includeIAM = Boolean.TRUE;
				}
				line = line.replace(result, replacedText);
			}
		}
		return line;
	}

	public static String getReplacedTextForScripplet(String line, String encType, String attributeName) {

		if (attributeName.contains("onclick") || attributeName.contains("onchange")) {
			line = encodeScripplets(scriptletURL, line, "encodeURL");
		}
		return encodeScripplets(null, line, encType);
	}

	public static String getReplacedTextForCOut(String line, String encType) {

		if (encType.equals("js")) {
			line = line.replaceAll("=<c:out", "=<enc:url");
		}
		return line.replaceAll("c:out", "enc:".concat(encType));
	}

	public static String encodeValue(String line) {

		String[] attributeTypes = { " = \"", "=\"", "= \"", " =\"", " = '", "='", "= '", " ='" };

		for (String type : attributeTypes) {
			String attributeName = "value".concat(type);
			Integer startIndex = line.indexOf(attributeName);
			if (startIndex != -1) {
				Character terminator = attributeName.charAt(attributeName.length() - 1);

				startIndex += attributeName.length();

				Integer endIndex = null;
				for (Integer index = startIndex; index < line.length(); index++) {
					if (terminator == line.charAt(index)) {
						endIndex = index;
						break;
					}
					if (line.charAt(index) == '<') {
						index = line.substring(index).indexOf(">") + index;
					}
				}
				if (endIndex != null) {
					String substring = line.substring(startIndex, endIndex);
					String substring2 = encodeScripplets(null, substring, "encodeHTMLAttribute");
					line = line.replace(substring, substring2.replace("<c:out", "<enc:htmlAttr"));

					line = line.replace(line.substring(endIndex), encodeValue(line.substring(endIndex)));
				}
			}
		}
		return line;
	}
}
