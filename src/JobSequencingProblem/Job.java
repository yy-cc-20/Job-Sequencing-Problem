package JobSequencingProblem;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Job implements Comparable<Job>{
	private String name;
	private int deadline; // Days before deadline, can be negative
	private double profit; // Revenue - cost, can be negative, in RM
	
	public Job(String name, int deadline, double profit) {
		setName(name);
		this.deadline = deadline;
		this.profit = profit;
	}
	
	private void setName(String name) {
		if (name == null || name.isBlank())
			throw new IllegalArgumentException("Job name cannot be empty.");
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public double getProfit() {
		return profit;
	}
	
	public int getDeadline() {
		return deadline;
	}
	
	// Compare the priority to do two jobs
	public int compareTo(Job jobB) {
		if (this.profit > jobB.profit)
			return 1;
		else if (this.profit < jobB.profit)
			return -1;
		else {
			if (this.deadline > jobB.deadline)
				return 1;
			else if (this.deadline < jobB.deadline)
				return -1;
			else
				return 0;
		}
	}
	
	public static void displayJobList(List<Job> jobList) {
		if (jobList == null || jobList.isEmpty()) {
			System.out.println("No job to be displayed.");
			return;
		}
		
		System.out.println("Day Job \t\t\t   Deadline  Marks Allocated(%)");
		int num = 1;
		for (Job job : jobList) {
			System.out.printf("%d   %s %8d \t\t % .2f %n", num, job.name, job.deadline, job.profit);
			++num;
		}
		System.out.println();
		System.out.println();
	}

	private static double calculateTotalProfit(List<Job> jobList) {
		double totalProfit = 0;
		for (Job job : jobList)
			totalProfit += job.profit;
		return totalProfit;
	}
	
	public static void displayJobSequenceAndTotalProfit(List<Job> jobSequence) {
		if (jobSequence == null || jobSequence.isEmpty()) {
			System.out.println("No job to be assigned.");
			return;
		}
		
		System.out.println("Day Job \t\t\t   Deadline  Marks Allocated(%)");
		int day = 0;
		for (Job job : jobSequence) { 
			System.out.printf("%d   %s %8d \t\t  %.2f %n", day, job.name, job.deadline, job.profit);
			day++;
		}
		double totalProfit = calculateTotalProfit(jobSequence);
		System.out.printf("Total                                                    %6.2f %n", totalProfit);
	}
	
	public static void displayDiscardedJobs(List<Job> jobList, List<Job> jobsDone) {
		List<Job> discardedJobs = new LinkedList<>(jobList);
		discardedJobs.removeAll(jobsDone);
		if (discardedJobs.isEmpty())
			System.out.println("Discarded Job: None");
		else
			System.out.println("Discarded Job: "); 
		for (Job job : discardedJobs)
			System.out.println("- " + job.name);
	}
}

class ProfitComparator implements Comparator<Job> {
	public int compare(Job j1, Job j2) {
		if (j1.getProfit() > j2.getProfit())
			return 1;
		else if (j1.getProfit() < j2.getProfit())
			return -1;
		else {
			if (j1.getDeadline() > j2.getDeadline())
				return 1;
			else if (j1.getDeadline() < j2.getDeadline())
				return -1;
			else // If 2 jobs have same deadline and profit, TreeSet will think they are the same job, thus ignore one of it
				return j1.getName().compareTo(j2.getName());
		} 
	}
}

class DeadlineComparator implements Comparator<Job> {
	public int compare(Job j1, Job j2) {
		if (j1.getDeadline() > j2.getDeadline())
			return 1;
		else if (j1.getDeadline() < j2.getDeadline())
			return -1;
		else {
			if (j1.getProfit() > j2.getProfit())
				return 1;
			else if (j1.getProfit() < j2.getProfit())
				return -1;
			else
				return 0;
		}
	}
}
