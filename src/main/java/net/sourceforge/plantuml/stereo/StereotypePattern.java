/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2024, Arnaud Roques
 *
 * Project Info:  https://plantuml.com
 * 
 * If you like this project or if you find it useful, you can support us at:
 * 
 * https://plantuml.com/patreon (only 1$ per month!)
 * https://plantuml.com/paypal
 * 
 * This file is part of PlantUML.
 *
 * PlantUML is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PlantUML distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public
 * License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 *
 * Original Author:  Arnaud Roques
 * 
 *
 */
package net.sourceforge.plantuml.stereo;

import net.sourceforge.plantuml.regex.IRegex;
import net.sourceforge.plantuml.regex.PatternCacheStrategy;
import net.sourceforge.plantuml.regex.RegexConcat;
import net.sourceforge.plantuml.regex.RegexLeaf;
import net.sourceforge.plantuml.regex.RegexOptional;

public class StereotypePattern {

	public static IRegex optional(String param) {
		return new RegexConcat( //
				PatternCacheStrategy.CACHE, //
				RegexLeaf.spaceZeroOrMore(), //
				new RegexOptional(mandatory(param))
, RegexLeaf.spaceZeroOrMore() //
		);
	}

	public static IRegex mandatory(String param) {
		return new RegexLeaf(1, param, "(\\<\\<.+\\>\\>)");
	}

	public static IRegex optionalArchimate(String param) {
		return new RegexConcat( //
				PatternCacheStrategy.CACHE, //
				RegexLeaf.spaceZeroOrMore(), //
				new RegexOptional(new RegexLeaf(1, param, "(\\<\\<[-\\w]+\\>\\>)"))
, RegexLeaf.spaceZeroOrMore() //
		);
	}

	public static String removeChevronBrackets(String stereo) {
		if (stereo != null && stereo.startsWith("<<") && stereo.endsWith(">>"))
			return stereo.substring(2, stereo.length() - 2);
		return stereo;
	}

}
