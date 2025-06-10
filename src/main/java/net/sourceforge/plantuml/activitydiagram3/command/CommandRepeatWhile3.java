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
package net.sourceforge.plantuml.activitydiagram3.command;

import net.sourceforge.plantuml.activitydiagram3.ActivityDiagram3;
import net.sourceforge.plantuml.command.CommandExecutionResult;
import net.sourceforge.plantuml.command.ParserPass;
import net.sourceforge.plantuml.command.SingleLineCommand2;
import net.sourceforge.plantuml.decoration.Rainbow;
import net.sourceforge.plantuml.descdiagram.command.CommandLinkElement;
import net.sourceforge.plantuml.klimt.color.NoSuchColorException;
import net.sourceforge.plantuml.klimt.creole.Display;
import net.sourceforge.plantuml.regex.IRegex;
import net.sourceforge.plantuml.regex.PatternCacheStrategy;
import net.sourceforge.plantuml.regex.RegexConcat;
import net.sourceforge.plantuml.regex.RegexLeaf;
import net.sourceforge.plantuml.regex.RegexOptional;
import net.sourceforge.plantuml.regex.RegexOr;
import net.sourceforge.plantuml.regex.RegexResult;
import net.sourceforge.plantuml.utils.LineLocation;

public class CommandRepeatWhile3 extends SingleLineCommand2<ActivityDiagram3> {

	public CommandRepeatWhile3() {
		super(getRegexConcat());
	}

	static IRegex getRegexConcat() {
		return RegexConcat.build(CommandRepeatWhile3.class.getName(), //
				RegexLeaf.start(), //
				new RegexLeaf("repeat"), //
				RegexLeaf.spaceZeroOrMore(), //
				new RegexLeaf("while"), //
				RegexLeaf.spaceZeroOrMore(), //
				new RegexOr(//
						new RegexConcat(PatternCacheStrategy.CACHE, //
								new RegexLeaf(1, "TEST3", "\\((.*?)\\)"), //
								RegexLeaf.spaceZeroOrMore(), //
								new RegexLeaf(1, "(is|equals?)"), //
								RegexLeaf.spaceZeroOrMore(), //
								new RegexLeaf(1, "WHEN3", "\\((.+?)\\)"), //
								RegexLeaf.spaceZeroOrMore(), //
								new RegexLeaf(1, "(not)"), //
								RegexLeaf.spaceZeroOrMore(), new RegexLeaf(1, "OUT3", "\\((.+?)\\)")), //
						new RegexConcat(PatternCacheStrategy.CACHE, //
								new RegexLeaf(1, "TEST4", "\\((.*?)\\)"), //
								RegexLeaf.spaceZeroOrMore(), //
								new RegexLeaf(1, "(not)"), //
								RegexLeaf.spaceZeroOrMore(), new RegexLeaf(1, "OUT4", "\\((.+?)\\)")), //
						new RegexConcat(PatternCacheStrategy.CACHE, //
								new RegexLeaf(1, "TEST2", "\\((.*?)\\)"), //
								RegexLeaf.spaceZeroOrMore(), //
								new RegexLeaf(1, "(is|equals?)"), //
								RegexLeaf.spaceZeroOrMore()
, new RegexLeaf(1, "WHEN2", "\\((.+?)\\)") //
						), //
						new RegexOptional(new RegexLeaf(1, "TEST1", "\\((.*)\\)")) //
				), //
				RegexLeaf.spaceZeroOrMore(), //
				new RegexOptional(new RegexConcat( //
						PatternCacheStrategy.CACHE, //
						new RegexOr(//
								new RegexLeaf("->"), //
								new RegexLeaf(1, "COLOR", CommandLinkElement.STYLE_COLORS_MULTIPLES)), //
						RegexLeaf.spaceZeroOrMore()
, new RegexOr(//
								new RegexLeaf(1, "LABEL", "(.*)"), //
								new RegexLeaf("")) //
				)), //
				new RegexLeaf(";?"), //
				RegexLeaf.end());
	}

	@Override
	protected CommandExecutionResult executeArg(ActivityDiagram3 diagram, LineLocation location, RegexResult arg, ParserPass currentPass)
			throws NoSuchColorException {
		final Display test = Display.getWithNewlines(diagram.getPragma(), arg.getLazzy("TEST", 0));
		final Display yes = Display.getWithNewlines(diagram.getPragma(), arg.getLazzy("WHEN", 0));
		final Display out = Display.getWithNewlines(diagram.getPragma(), arg.getLazzy("OUT", 0));

		final String colorString = arg.get("COLOR", 0);
		final Rainbow rainbow;
		if (colorString == null) {
			rainbow = Rainbow.none();
		} else {
			rainbow = Rainbow.build(diagram.getSkinParam(), colorString,
					diagram.getSkinParam().colorArrowSeparationSpace());
		}

		final Display linkLabel = Display.getWithNewlines(diagram.getPragma(), arg.get("LABEL", 0));
		return diagram.repeatWhile(test, yes, out, linkLabel, rainbow);
	}

}
