import java.util.*;
//in this class AN is a shortcut word for ArrayNumber of character.
//and cAN is a shortcut word for ArrayNumber of characters [character class] copy.
//some processes like findingCoordinates, drawingBoard are much easier with [character class] copy of characters.
//thats the reason why we use cAN.

import FileOps.FileRead;
import FileOps.FileWrite;

public class Drawing {
	static FileWrite fw = new FileWrite();
	static FileRead fr = new FileRead();
	static Scanner input = null;

	public static void Process(AllInOne data, String[] args) {
		fw.openWFile(args[2]);
		input = fr.openFile(args[1]);
		boardDrawer(data);
		statusWriter(data);
		while (input.hasNextLine() & isGameFinished(data)) {
			done: {
				switch (commandRider(data)) {
				case 0:
					fw.writeln("Error : Character doesnt exist.");
					break done;
				case 1:
					fw.writeln("Error : Move sequence contains wrong number of move steps. Input line ignored.\n");
					break done;
				case 2:
					fw.writeln("Error : Game board boundaries are exceeded. Input line ignored.\n");
					break done;
				default:
					break;
				}
				boardDrawer(data);
				statusWriter(data);
			}
		}
	}

	public static boolean isGameFinished(AllInOne data) {
		if (data.hmn.size() + data.elf.size() + data.dwf.size() == 0) {
			fw.writeln("\nGame Finished\nZorde Wins\n");
			return false;
		}
		if (data.ork.size() + data.trl.size() + data.gbl.size() == 0) {
			fw.writeln("\nGame Finished\nCalliance Wins\n");
			return false;
		}
		return true;
	}

	// 1.Command-Read Fuction.
	public static int commandRider(AllInOne data) {
		String FullLine = input.nextLine();
		String[] iconNcommands = FullLine.split(" ");
		String[] strCommands = iconNcommands[1].split(";");
		String icon = iconNcommands[0];

		int[] commands = new int[strCommands.length];
		for (int i = 0; i < strCommands.length; i++) {
			commands[i] = Integer.parseInt(strCommands[i]);
		}

		// Now there are 5 basic possibility:
		// char has error -> char movesOutsideOfField(Error), char has
		// invalidMoveSequence(less or more)(error)
		// char movesToSpace -> char attacks(attType: step or final), char
		// doesnt attacks(attType: final), elf may use special.
		// char movesToFriend(takeAStepBack);
		// char movesToEnemy -> attacksOnlyThatSpace and fightToDeath, goblin
		// may use special.
		// char heals -> only orcs(if hasnt error).

		boolean isCharExist = false;
		int charAN = 0; // ArrayNumber of characters [character class] copy.
		for (int i = 0; i < data.chr.size(); i++) {
			if (data.chr.get(i).getIcon().equals(icon)) {
				charAN = i;
				isCharExist = true;
				break;
			}
		}

		// ___ERROR MESSAGES IN THREE LITTLE IF STATEMENT___
		if (!isCharExist) {
			return 0; // ERROR TYPE 0 : Character not found.
		}

		if (data.chr.get(charAN).getMaxMove() != commands.length / 2) {
			return 1; // ERROR TYPE 1 : Move sequence contains wrong number of
						// move steps. Input line ignored.
		}

		int x = data.chr.get(charAN).getX();
		int y = data.chr.get(charAN).getY();
		for (int i = 0; i < commands.length / 2; i += 2) {
			x += commands[i];
			y += commands[i + 1];
			if (  x < 0 | y < 0 | x > data.brd.getX() | y > data.brd.getY()) {
				return 2; // ERROR TYPE 2 : Game board boundaries are exceeded.
							// Input line ignored.
			}
		}
		startTurn(data, charAN, commands);
		return 3;
	}

	// 2.Game-Play Functions
	public static void startTurn(AllInOne data, int cAN, int[] cmd) {

		done: {
			for (int i = 0; i < 2 * data.chr.get(cAN).getMaxMove(); i += 2) {
				int x = data.chr.get(cAN).getX();
				int y = data.chr.get(cAN).getY();
				// 1.first check if character ork, then heal ability must be 
				// activated.
				if (data.chr.get(cAN).getType() == 'o') {
					for (int moveY = -1; moveY < 2; moveY++) {
						for (int moveX = -1; moveX < 2; moveX++) {
							for (int dcAN = 0; dcAN < data.chr.size(); dcAN++) {
								if (x + moveX == data.chr.get(dcAN).getX()
										& y + moveY == data.chr.get(dcAN).getY()
										& data.chr.get(dcAN).getSide() == data.chr
												.get(cAN).getSide()) {
									// healZordes ( data, friendToBeHealTo,
									// healPointsOfOrk.
									healZordes(data, dcAN,
											data.ork.get(findAN(data, cAN))
													.getHealPoint());
								}
							}
						}
					}
				}
				// 2.check if character moves to a friendly characters block.
				for (int dcAN = 0; dcAN < data.chr.size(); dcAN++) {
					if (x + cmd[i] == data.chr.get(dcAN).getX()
							& y + cmd[i + 1] == data.chr.get(dcAN).getY()
							& data.chr.get(dcAN).getSide() == data.chr.get(cAN)
									.getSide()) {
						
						if (data.chr.get(cAN).getType() == 'g' ) {
							boolean isPreviousBlockIsEnemy = false; // if this int is 2, it means 2 char is in one block. this means previous block is enemy.
							int bcAN = 0;
							for (bcAN = 0; bcAN < data.chr.size(); bcAN++) {
								if (x == data.chr.get(bcAN).getX()
										& y == data.chr.get(bcAN).getY() & bcAN != cAN) {
									isPreviousBlockIsEnemy = true;
									break;
								}
							}
							if(isPreviousBlockIsEnemy == true) {
								fightToDeath(data, cAN, bcAN, cmd[i], cmd[i + 1]);
								break done;
							}
						}
						break done;
					}
				}
				// 3.check if character moves to a enemy characters block.
				// dcAN : arrayNumber of defender characters [character class]
				// copy.
				for (int dcAN = 0; dcAN < data.chr.size(); dcAN++) {
					if (x + cmd[i] == data.chr.get(dcAN).getX()
							& y + cmd[i + 1] == data.chr.get(dcAN).getY()
							& data.chr.get(dcAN).getSide() != data.chr.get(cAN)
									.getSide()) {
						// ones HP will decrease and new coordinates will enter,
						// another one will be deleted from both classes.
						// if char is goblin, it can pass through enemies unless
						// this step is its final step.
						if (data.chr.get(cAN).getType() != 'g'
								| data.chr.get(cAN).getMaxMove() == (i + 2) / 2) {
							if(!hitCharacter(data, cAN, dcAN)) {
								if( dcAN < cAN ) {
									cAN--;
								}
								moveCharacter(data, cAN, cmd[i], cmd[i + 1]);
							} else {
							fightToDeath(data, cAN, dcAN, cmd[i], cmd[i + 1]);
							}
							break done;
						} 
					}
				}
				// 4.so only one possibility left: characters moves into a empty
				// block or a goblin attacks.
				moveCharacter(data, cAN, cmd[i], cmd[i + 1]);
				x = data.chr.get(cAN).getX();
				y = data.chr.get(cAN).getY();
				// 4.1.if character is elf and this step is its last step,
				// special ability will activate.
				if (data.chr.get(cAN).getType() == 'e'
						& (data.chr.get(cAN).getMaxMove() == (i + 2) / 2)) {
					for (int moveY = -2; moveY < 3; moveY++) {
						for (int moveX = -2; moveX < 3; moveX++) {
							for (int dcAN = 0; dcAN < data.chr.size(); dcAN++) {
								if (x + moveX == data.chr.get(dcAN).getX()
										& y + moveY == data.chr.get(dcAN)
												.getY()
										& data.chr.get(dcAN).getSide() != data.chr
												.get(cAN).getSide()) {
									if(!hitCharacterWithAP(data, dcAN, data.elf
											.get(findAN(data, cAN))
											.getRangedAP()) && dcAN < cAN ) {
												cAN--;
											}
									break;
								}
							}
						}
					}
					break done;
				}
				// 4.2.if character can attack now, we need to check are there
				// any enemy in surrounding 8 cell.
				// if chars attack type is "attack every step" or
				// this step is its last step and chars attack type
				// "attack only final", char can attack.
				if (data.chr.get(cAN).getAttackType() == 's'
						| (data.chr.get(cAN).getAttackType() == 'f' & (data.chr
								.get(cAN).getMaxMove() == (i + 2) / 2))) {
					for (int moveY = -1; moveY < 2; moveY++) {
						for (int moveX = -1; moveX < 2; moveX++) {
							for (int dcAN = 0; dcAN < data.chr.size(); dcAN++) {
								if (x + moveX == data.chr.get(dcAN).getX()
										& y + moveY == data.chr.get(dcAN)
												.getY()
										& data.chr.get(dcAN).getSide() != data.chr
												.get(cAN).getSide()) {
									if ( !hitCharacter(data, cAN, dcAN) && dcAN < cAN ) {
										cAN--;
									}
									break;
								}
							}
						}
					}
				}
			}
		}
	}

	public static boolean fightToDeath(AllInOne data, int acAN, int dcAN,
			int x, int y) {
		int aHP = data.chr.get(acAN).getHP();
		int dHP = data.chr.get(dcAN).getHP();
		
		if (aHP == dHP) {
			// if their HPs are equal each other, both chars will die and
			// removed from gameField.
			killCharacter(data, dcAN);
			killCharacter(data, acAN);
		} else {
			if (aHP > dHP) {
				// this means attacker wins and lives.
				// attacker will be moved to block and defender will be removed
				// from gameField.
				// attackers hp is decrease an amount equal to (defenders HP)
				moveCharacter(data, acAN, x, y);
				hitCharacterWithAP(data, acAN, dHP);
				killCharacter(data, dcAN);
			} else {
				// this means defender wins and lives.
				// defender will be moved to block and attacker will be removed
				// from gameField.
				// defenders hp is decrease an amount equal to (attackers HP)
				hitCharacterWithAP(data, dcAN, aHP);
				killCharacter(data, acAN);
			}
		}

		return true;
	}

	public static void moveCharacter(AllInOne data, int cAN, int x, int y) {
		data.chr.get(cAN).setX(data.chr.get(cAN).getX() + x);
		data.chr.get(cAN).setY(data.chr.get(cAN).getY() + y);
	}

	public static boolean hitCharacter(AllInOne data, int acAN, int dcAN) {
		int aAP = data.chr.get(acAN).getAP();
		data.chr.get(dcAN).setHP(data.chr.get(dcAN).getHP() - aAP);
		if (data.chr.get(dcAN).getHP() <= 0) {
			killCharacter(data, dcAN);
			return false;
		}
		return true;
	}

	public static boolean hitCharacterWithAP(AllInOne data, int cAN, int AP) {
		data.chr.get(cAN).setHP(data.chr.get(cAN).getHP() - AP);
		if (data.chr.get(cAN).getHP() <= 0) {
			killCharacter(data, cAN);
			return false;
		}
		return true;
	}

	public static void healZordes(AllInOne data, int hcAN, int healPoint) {
		data.chr.get(hcAN).setHP( data.chr.get(hcAN).getHP() + healPoint);
		if (data.chr.get(hcAN).getHP() > data.chr.get(hcAN).getMaxHP()) {
			data.chr.get(hcAN).setHP(data.chr.get(hcAN).getMaxHP());
		}
	}

	public static void killCharacter(AllInOne data, int cAN) {
		int AN = findAN(data, cAN);
		switch (data.chr.get(cAN).getType()) {
		case 'h':
			data.hmn.remove(AN);
			break;
		case 'e':
			data.elf.remove(AN);
			break;
		case 'd':
			data.dwf.remove(AN);
			break;
		case 'o':
			data.ork.remove(AN);
			break;
		case 't':
			data.trl.remove(AN);
			break;
		case 'g':
			data.gbl.remove(AN);
			break;
		default:
			break;
		}
		data.chr.remove(cAN);
		
	}

	public static int findAN(AllInOne data, int cAN) {
		int AN = 0; // ArrayNumber of character.
		boolean isCharExist = false;
		done: {
			for (int i = 0; i < data.hmn.size(); i++) {
				if (data.hmn.get(i).getIcon() == data.chr.get(cAN).getIcon()) {
					AN = i;
					isCharExist = true;
					break done;
				}
			}
			for (int i = 0; i < data.elf.size(); i++) {
				if (data.elf.get(i).getIcon() == data.chr.get(cAN).getIcon()) {
					AN = i;
					isCharExist = true;
					break done;
				}
			}
			for (int i = 0; i < data.dwf.size(); i++) {
				if (data.dwf.get(i).getIcon() == data.chr.get(cAN).getIcon()) {
					AN = i;
					isCharExist = true;
					break done;
				}
			}
			for (int i = 0; i < data.ork.size(); i++) {
				if (data.ork.get(i).getIcon() == data.chr.get(cAN).getIcon()) {
					AN = i;
					isCharExist = true;
					break done;
				}
			}
			for (int i = 0; i < data.trl.size(); i++) {
				if (data.trl.get(i).getIcon() == data.chr.get(cAN).getIcon()) {
					AN = i;
					isCharExist = true;
					break done;
				}
			}
			for (int i = 0; i < data.gbl.size(); i++) {
				if (data.gbl.get(i).getIcon() == data.chr.get(cAN).getIcon()) {
					AN = i;
					isCharExist = true;
					break done;
				}
			}
		}
		if (!isCharExist) {
			return 0; // ERROR TYPE 0 : Character not found.
		}
		return AN;
	}

	// 3.Board-Draw Functions.
	public static void boardDrawer(AllInOne data) {
		drawTopOrBottomOfBoard(data.brd);
		boolean isCharExist = false;
		for (int y = 0; y < data.brd.getY(); y++) {
			fw.write("*");
			for (int x = 0; x < data.brd.getX(); x++) {
				isCharExist = false;
				for (int i = 0; i < data.chr.size(); i++) {
					if (data.chr.get(i).getX() == x
							& data.chr.get(i).getY() == y) {
						fw.write(data.chr.get(i).getIcon());
						isCharExist = true;
					}
				}
				if (isCharExist == false) {
					fw.write("  ");
				}
			}
			fw.writeln("*");
		}
		drawTopOrBottomOfBoard(data.brd);
		fw.write("\n");
	}

	public static void drawTopOrBottomOfBoard(Board board) {
		for (int i = 0; i < 2 * board.getX() + 2; i++) {
			fw.write("*");
		}
		fw.write("\n");
	}

	// 4.Status-Write Function.
	public static void statusWriter(AllInOne data) {
		for (int i = 0; i < data.chr.size(); i++) {
			for (int j = 0; j + 1 < data.chr.size(); j++) {
				if (data.chr.get(j).getIcon()
						.compareTo(data.chr.get(j + 1).getIcon()) > 0) {
					Characters tempChr;
					tempChr = data.chr.get(j);
					data.chr.set(j, data.chr.get(j + 1));
					data.chr.set(j + 1, tempChr);
				}
			}
		}
		for (int i = 0; i < data.chr.size(); i++) {
			fw.writeln(data.chr.get(i).getIcon() + "\t"
					+ data.chr.get(i).getHP() + "\t("
					+ data.chr.get(i).getMaxHP() + ")");
		}
		fw.write("\n");
	}

}
