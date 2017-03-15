import java.util.Scanner;

import FileOps.FileRead;
import gameLogic.Constants;

public class Reading {

	static FileRead fr = new FileRead();
	static Scanner input = null;

	public static AllInOne Process (AllInOne data, String[] args) {
		input = fr.openFile(args[0]);
		String fullLine = input.nextLine();
		if ( fullLine.equals("BOARD") ) {
			fullLine = input.nextLine();
			String[] strBoardEdgesLengths = fullLine.split("x");
			data.brd.setX(Integer.parseInt(strBoardEdgesLengths[0]));
			data.brd.setY(Integer.parseInt(strBoardEdgesLengths[1]));
			fullLine = input.next();
			}
		if ( fullLine.equals("CALLIANCE")) {
			while ( input.hasNextLine() & (input.hasNext("HUMAN") | input.hasNext("ELF") | input.hasNext("DWARF")) ) {
				fullLine = input.nextLine();
				String[] splittedLine = fullLine.split(" ");

				if ( splittedLine[0].equals("HUMAN") ) {
					Humans tempHuman = new Humans( Constants.humanHP, Constants.humanHP, Constants.humanAP, Constants.humanMaxMove, 'f', Integer.parseInt(splittedLine[2]), Integer.parseInt(splittedLine[3]), 'c', 'h', splittedLine[1]);
					data.hmn.add(tempHuman);
					data.chr.add(tempHuman);
				}
				if ( splittedLine[0].equals("ELF") ) {
					Elfs tempElf = new Elfs( Constants.elfHP, Constants.elfHP, Constants.elfAP, Constants.elfMaxMove, 's', Integer.parseInt(splittedLine[2]), Integer.parseInt(splittedLine[3]), 'c', 'e', splittedLine[1], Constants.elfRangedAP);
					data.elf.add(tempElf);
					data.chr.add(tempElf);
				}
				if ( splittedLine[0].equals("DWARF") ) {
					Dwarfs tempDwarf = new Dwarfs( Constants.dwarfHP, Constants.dwarfHP, Constants.dwarfAP, Constants.dwarfMaxMove, 's', Integer.parseInt(splittedLine[2]), Integer.parseInt(splittedLine[3]), 'c', 'd', splittedLine[1]);
					data.dwf.add(tempDwarf);
					data.chr.add(tempDwarf);
				}
				
			}
		}
		fullLine = input.next();
		if ( fullLine.equals("ZORDE")) {
			while ( input.hasNextLine() & (input.hasNext("ORK") | input.hasNext("TROLL") | input.hasNext("GOBLIN")) ) {
				fullLine = input.nextLine();
				String[] splittedLine = fullLine.split(" ");

				if ( splittedLine[0].equals("ORK") ) {
					Orks tempOrk = new Orks( Constants.orkHP, Constants.orkHP, Constants.orkAP, Constants.orkMaxMove, 'f', Integer.parseInt(splittedLine[2]), Integer.parseInt(splittedLine[3]), 'z', 'o', splittedLine[1], Constants.orkHealPoints);
					data.ork.add(tempOrk);
					data.chr.add(tempOrk);
				}
				if ( splittedLine[0].equals("TROLL") ) {
					Trolls tempTroll = new Trolls( Constants.trollHP, Constants.trollHP, Constants.trollAP, Constants.trollMaxMove, 'f', Integer.parseInt(splittedLine[2]), Integer.parseInt(splittedLine[3]), 'z', 't', splittedLine[1]);
					data.trl.add(tempTroll);
					data.chr.add(tempTroll);
				}
				if ( splittedLine[0].equals("GOBLIN") ) {
					Goblins tempGoblin = new Goblins( Constants.goblinHP, Constants.goblinHP, Constants.goblinAP, Constants.goblinMaxMove, 's', Integer.parseInt(splittedLine[2]), Integer.parseInt(splittedLine[3]), 'z', 'g', splittedLine[1]);
					data.gbl.add(tempGoblin);
					data.chr.add(tempGoblin);
				}
			}
		}
		return data;
	}
}
