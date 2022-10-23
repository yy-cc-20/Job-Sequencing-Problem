package JobSequencingProblem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @description Job Sequencing Problem
 * 
 * Given a list of jobs with deadline and profit, arrange the sequence of doing the jobs to get the 
 * maximum profit.
 * 
 * Assumption: 
 * 1. A job can be done within one day, it should be started at least 1 day before the deadline
 * 2. Only able to do one job in a day, different jobs cannot be doing at the same day 
 * 3. Every job can start at any day before its deadline
 * 4. The profit will be earned after the job is completed
 * 
 * There are two approaches to calculate the maximum profit:  
 * 1. For each job, we choose the day which is the closest to the deadline from list of available days
 * 2. For each day, we choose the job with the highest profit from list of unassigned jobs.
 * 
 * We used TreeSet for both approaches to find the optimal choice for each day/job. The time complexity
 * for both algorithm are O(n log(n)). Both approaches give the same result.
 */

public class SolvingJobSequencingProblem {
	public static void main(String... args) {
		System.out.printf("%nJob Sequencing Problem%n");
		System.out.printf("======================%n");
		System.out.printf("Given a list of exam and assignments with deadlines and marks, arrange the %n"
				+ "sequence of doing the tasks to get the maximum marks.%n");
		
		System.out.printf("%nAssumptions%n"
			+ "===========%n"
			+ "1. A task can be done within one day.%n"
			+ "2. Only able to do one task in a day%n"
			+ "3. Every task can start at any day before its deadline.%n"
			+ "4. The marks will be earned after the task is completed.%n");
		
		System.out.printf("%nThere are two approaches to calculate the maximum marks:%n"
			+ "=========================================================%n"
			+ "1. For each task, we choose the day which is the closest to the %n"
			+ "   deadline from list of available days.%n"
			+ "2. For each day, we choose the task with the highest marks from %n"
			+ "   list of unassigned tasks.%n%n"
			+ "We used TreeSet for both approaches to find the optimal choice %n"
			+ "for each day/task. The time complexity for both algorithm are %n"
			+ "O(n log(n)). Both approaches give the same result.%n%n");
		
		System.out.printf("_______________________________________________________________%n%n");

		List<Job> jobsToDo, jobSequence;
		long startTime, endTime;
		List<String> testCasesName = new ArrayList<>();
	    List<List<Job>> testCases = TestFileInput.readTestInput("testInput.txt", testCasesName);
	    Iterator<List<Job>> testCasesIterator = testCases.iterator();
	    
		for (int i = 0; testCasesIterator.hasNext(); ++i) {
			
			jobsToDo = Collections.unmodifiableList(testCasesIterator.next()); // Read only
			
			System.out.printf("%nTest Case %d: %s %n", i + 1, testCasesName.get(i));
			System.out.println("--------------------------------------");
			Job.displayJobList(jobsToDo);
			
			// Approach 1
			startTime = System.nanoTime();
			jobSequence = JobSequencingAlgorithm.greedyApproach1UsingTreeSet(jobsToDo);
			endTime = System.nanoTime() - startTime;
			System.out.println("Using Approach 1");
			System.out.println("----------------");
			Job.displayJobSequenceAndTotalProfit(jobSequence);
			Job.displayDiscardedJobs(jobsToDo, jobSequence);
			System.out.printf("Execution time: %.8fs%n", endTime / 1000000000.0);
			System.out.println();
			
			// Approach 2
			startTime = System.nanoTime();
			jobSequence = JobSequencingAlgorithm.greedyApproach2UsingTreeSet(jobsToDo);
			endTime = System.nanoTime() - startTime;
			System.out.println();
			System.out.println("Using Approach 2");
			System.out.println("----------------");
			Job.displayJobSequenceAndTotalProfit(jobSequence);
			Job.displayDiscardedJobs(jobsToDo, jobSequence);
			System.out.printf("Execution time: %.8fs%n%n", endTime / 1000000000.0);
			
			System.out.printf("_______________________________________________________________%n%n");
		}
	}
}
