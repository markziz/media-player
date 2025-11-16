import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Query {
	
	private static BSTOrderedDictionary dict;

	public Query(String inputFile) {
		dict = new BSTOrderedDictionary();
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
	        String key;
	        String fileName;

	        while ((key = reader.readLine()) != null && (fileName = reader.readLine()) != null) {
	            key = key.toLowerCase();

	            if (fileName.endsWith(".wav") || fileName.endsWith(".mid")) {
	                dict.put(dict.getRoot(), key, fileName, 2);
	            } else if (fileName.endsWith(".jpg") || fileName.endsWith(".gif")) {
	                dict.put(dict.getRoot(), key, fileName, 3);
	            } else if (fileName.endsWith(".html")) {
	                dict.put(dict.getRoot(), key, fileName, 4);
	            } else {
	                dict.put(dict.getRoot(), key, fileName, 1);
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Error reading file: " + inputFile);
	    }
	}
	
	public static String processCommand(String command) {
		StringTokenizer userCommand = new StringTokenizer(command);
		String str = "",k,c;
		int t,d;
		SoundPlayer player = new SoundPlayer();
		PictureViewer viewer = new PictureViewer();
		ShowHTML shower = new ShowHTML();
		String token = userCommand.nextToken();
		ArrayList<MultimediaItem> arr;
		Data succData, preData;
		
		if(token.equals("get") && userCommand.hasMoreTokens()) {
			k = userCommand.nextToken();
			if (userCommand.hasMoreTokens()) return "Invalid command";
			arr = dict.get(dict.getRoot(),k);
			if(arr == null) return "The word " + k + " is not in the ordered dictionary.";
			for (int i = 0; i < arr.size(); i++) {
				if(arr.get(i).getType() == 1) {
					str += arr.get(i).getContent() + "\n";
				}else if(arr.get(i).getType() == 2) {
					try {
					    player.play(arr.get(i).getContent());
					}
					catch (MultimediaException e) {
						str += e.getMessage() + "\n";
					}
				}
				else if(arr.get(i).getType() == 3) {
					try {
						viewer.show(arr.get(i).getContent());
					}
					catch (MultimediaException e) {
						str += e.getMessage() + "\n";
					}
				}
				else {
					try {
						shower.show(arr.get(i).getContent());
					}
					catch (Exception e) {
						str += e.getMessage() + "\n";
					}
				}
			}
			str += "Preceding word: " + (dict.predecessor(dict.getRoot(), k) != null ? dict.predecessor(dict.getRoot(), k).getName() : "") + "\n";
			str += "Following word: " + (dict.successor(dict.getRoot(), k) != null ? dict.successor(dict.getRoot(), k).getName() : "");
			return str;
		}
		else if(token.equals("remove") && userCommand.hasMoreTokens()){
			k = userCommand.nextToken();
			if (userCommand.hasMoreTokens()) return "Invalid command";
			try {
				dict.remove(dict.getRoot(), k);
				return str;
			} catch (DictionaryException e) {
				return "No record in the ordered dictionary has key " + k;
			}
		}
		else if(token.equals("delete") && userCommand.hasMoreTokens()) {
			k = userCommand.nextToken();
			if (!userCommand.hasMoreTokens()) return "Invalid command";
			try {
			t = Integer.parseInt(userCommand.nextToken());
			}catch(Exception e) {
				return "Invalid command";
			}
			if (userCommand.hasMoreTokens()) return "Invalid command";
			try {
				dict.remove(dict.getRoot(), k, t);
				return str;
			} catch (DictionaryException e) {
				return "No record in the ordered dictionary has key " + k;
			}
		}
		else if(token.equals("add") && userCommand.hasMoreTokens()){
			k = userCommand.nextToken();
			if (!userCommand.hasMoreTokens()) return "Invalid command";
			c = userCommand.nextToken();
			if (!userCommand.hasMoreTokens()) return "Invalid command";
			try {
			t = Integer.parseInt(userCommand.nextToken());
			}catch(Exception e) {
				return "Invalid command";
			}
			if (userCommand.hasMoreTokens()) return "Invalid command";
			dict.put(dict.getRoot(), k, c, t);
			return str;
		}
		else if(token.equals("next") && userCommand.hasMoreTokens()) {
			k = userCommand.nextToken();
			if (!userCommand.hasMoreTokens()) return "Invalid command";
			try {
			d = Integer.parseInt(userCommand.nextToken());
			}catch(Exception e) {
				return "Invalid command";
			}
			if (userCommand.hasMoreTokens()) return "Invalid command";
			
			arr = dict.get(dict.getRoot(),k);
			if(arr != null) {
				str += k + " ";
				succData = dict.successor(dict.getRoot(), k);
				if (succData == null) return "There are no keys larger than or equal to " + k;
				for (int i = 0; i < d; i++) {
					str += succData.getName() + " ";
					succData = dict.successor(dict.getRoot(), succData.getName());
					if(succData == null) break;
					
				}
				return str;
			}else {
				dict.put(dict.getRoot(), k, "fake", 1);
				str += k + " ";
				succData = dict.successor(dict.getRoot(), k);
				if (succData == null) {
					try {
						dict.remove(dict.getRoot(), k);
					} catch (DictionaryException e) {}
					return "There are no keys larger than or equal to " + k;
				}
				for (int i = 0; i < d; i++) {
					str += succData.getName() + " ";
					succData = dict.successor(dict.getRoot(), succData.getName());
					if(succData == null) break;
					
				}
				try {
					dict.remove(dict.getRoot(), k);
				} catch (DictionaryException e) {}
				return str;
			}
		}
		else if(token.equals("prev") && userCommand.hasMoreTokens()) {
			k = userCommand.nextToken();
			if (!userCommand.hasMoreTokens()) return "Invalid command";
			try {
			d = Integer.parseInt(userCommand.nextToken());
			}catch(Exception e) {
				return "Invalid command";
			}
			if (userCommand.hasMoreTokens()) return "Invalid command";
			
			arr = dict.get(dict.getRoot(),k);
			if(arr != null) {
				str += k + " ";
				preData = dict.predecessor(dict.getRoot(), k);
				if (preData == null) return "There are no keys smaller than or equal to " + k;
				for (int i = 0; i < d; i++) {
					str += preData.getName() + " ";
					preData = dict.predecessor(dict.getRoot(), preData.getName());
					if(preData == null) break;
				}
				return str;
			}else {
				dict.put(dict.getRoot(), k, "fake", 1);
				str += k + " ";
				preData = dict.predecessor(dict.getRoot(), k);
				if (preData == null) {
					try {
						dict.remove(dict.getRoot(), k);
					} catch (DictionaryException e) {}
					return "There are no keys smaller than or equal to " + k;
				}
				for (int i = 0; i < d; i++) {
					str += preData.getName() + " ";
					preData = dict.predecessor(dict.getRoot(), preData.getName());
					if(preData == null) break;
				}
				
				try {
					dict.remove(dict.getRoot(), k);
				} catch (DictionaryException e) {}
				return str;
			}
		}
		else if(token.equals("first") && !userCommand.hasMoreTokens()) {
			return (dict.smallest(dict.getRoot()) != null) ? dict.smallest(dict.getRoot()).getName() : "The ordered dictionary is empty.";
		}
		else if(token.equals("last") && !userCommand.hasMoreTokens()) {
			return (dict.largest(dict.getRoot()) != null) ? dict.largest(dict.getRoot()).getName() : "The ordered dictionary is empty.";
		}
		else if(token.equals("size") && !userCommand.hasMoreTokens()) {
			return "There are " + dict.getNumInternalNodes() + " keys in the ordered dictionary";
		}
		else if(token.equals("end") && !userCommand.hasMoreTokens()) {
			return str;
		}
		return "Invalid command";
	}
	
	public static void main(String[] args) {
		String result, nextCommand = "";
		StringReader keyboard = new StringReader();
		if (args.length != 1) {
		System.out.println("Usage: java Query fileName");
		System.exit(0);
		}
		Query myProgram = new Query(args[0]);
		while (!nextCommand.equals("end")) {
		nextCommand = keyboard.read("Enter next command: ");
		result = processCommand(nextCommand);
		System.out.println(result);
		}
	}

}
