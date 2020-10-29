import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CountryApp {
	private static Path filePath = Paths.get("Country.txt");

	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);

		while (true) {
			System.out.println("Enter a command :" + "\n" + "1.list" + "\n" + "2.add" +"\n"+ "3.quit");

			String command = scnr.nextLine();
			if (command.equals("quit")) {
				break;
			} else if (command.equals("list")) {
				List<Country> mylist = readFile();
				int i = 1;
				for (Country thing : mylist) {
					System.out.println(i++ + ". " + thing);
				}
			} else if (command.equals("add")) {
				Country thing = getThingFromUser(scnr);
				System.out.println("Adding " + thing);
				appendLineToFile(thing);
			} else {
				System.out.println("Invalid command.");
			}
		}
		System.out.println("Goodbye.");
		scnr.close();
	}

	private static Country getThingFromUser(Scanner scnr) {
		// TODO #1 adjust this for your class, not Player
		String name = Validator.getString(scnr, "Enter name of Country: ");
		double population = Validator.getDouble(scnr, "Enter the population: ");

		return new Country(name, population);
	}

	
	public static List<Country> readFile() {
		try {
			List<String> lines = Files.readAllLines(filePath);
			List<Country> things = new ArrayList<>();
			for (String line : lines) {
				String[] parts = line.split("~");
				String name = parts[0];
				double population = Double.parseDouble(parts[1]);

				things.add(new Country(name, population));
			}
			return things;

		} catch (IOException e) {
			System.out.println("Unable to read file.");
			return new ArrayList<>();
		}
	}

	
	public static void appendLineToFile(Country thing) {
		
		String line = thing.getName() + "~" + thing.getPopulation();

		
		List<String> lines = Collections.singletonList(line);
		try {
			Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			System.out.println("Unable to write to file.");
		}
	}

}
