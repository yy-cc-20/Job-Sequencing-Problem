package JobSequencingProblem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TestFileInput {
	public static List<List<Job>> readTestInput(String fileName, List<String> testCaseName) {
		if (fileName == null || fileName.isBlank())
			throw new IllegalArgumentException("Filename cannot be empty.");
		
		List<List<Job>> testCases = new LinkedList<>();
		List<Job> jobList;
		String line;
		
		try {
			Scanner scanner = new Scanner(new File(fileName)); // If no file, throws FileNotFoundException
			Scanner lineScanner = null;
			while (scanner.hasNextLine()) {
				
				testCaseName.add(scanner.nextLine());
				jobList = new LinkedList<>();
				while (scanner.hasNextLine()) {
					
					line = scanner.nextLine();
					
					if (line.isBlank())
						break;
					
					lineScanner = new Scanner(line).useDelimiter(",");
					jobList.add(new Job(lineScanner.next(), lineScanner.nextInt(), lineScanner.nextDouble()));
				}
				testCases.add(jobList);
			}
			scanner.close();
			lineScanner.close();
		} catch (FileNotFoundException  e) {}
		
		return testCases;
	}
}
