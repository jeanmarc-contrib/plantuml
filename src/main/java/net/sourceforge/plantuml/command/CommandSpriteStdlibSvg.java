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
package net.sourceforge.plantuml.command;

import java.io.IOException;

import net.sourceforge.plantuml.TitledDiagram;
import net.sourceforge.plantuml.preproc.Stdlib;
import net.sourceforge.plantuml.preproc.StdlibSpriteSvg;
import net.sourceforge.plantuml.regex.IRegex;
import net.sourceforge.plantuml.regex.RegexConcat;
import net.sourceforge.plantuml.regex.RegexLeaf;
import net.sourceforge.plantuml.regex.RegexResult;
import net.sourceforge.plantuml.utils.LineLocation;

public class CommandSpriteStdlibSvg extends SingleLineCommand2<TitledDiagram> {

	public static final CommandSpriteStdlibSvg ME = new CommandSpriteStdlibSvg();

	private CommandSpriteStdlibSvg() {
		super(getRegexConcat());
	}

	private static IRegex getRegexConcat() {
		return RegexConcat.build(CommandSpriteStdlibSvg.class.getName(), RegexLeaf.start(), //
				new RegexLeaf("sprite"), //
				RegexLeaf.spaceOneOrMore(), //
				new RegexLeaf("\\$?"), //
				new RegexLeaf(1, "NAME", "([-%pLN_]+)"), //
				RegexLeaf.spaceZeroOrMore(), //
				new RegexLeaf(2, "FILE", ":([^/]+)/(\\d+)"), //
				RegexLeaf.end());
	}

	@Override
	protected CommandExecutionResult executeArg(TitledDiagram system, LineLocation location, RegexResult arg,
			ParserPass currentPass) {
		try {
			final Stdlib lib = Stdlib.retrieve(arg.get("FILE", 0));
			final int num = Integer.parseInt(arg.get("FILE", 1));
			final String name = arg.get("NAME", 0);
			system.addSprite(name, new StdlibSpriteSvg(lib, num));
		} catch (IOException e) {
			return CommandExecutionResult.error("Cannot read sprite " + e.toString());
		}
		return CommandExecutionResult.ok();
	}

}
