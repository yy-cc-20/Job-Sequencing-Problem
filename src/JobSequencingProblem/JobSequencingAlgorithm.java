package JobSequencingProblem;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/*
 * There are two approaches to calculate the maximum profit:  
 *
 * 1. For each job, we choose the day which is the closest to the deadline from list of available days
 * 2. For each day, we choose the job with the highest profit from list of unassigned jobs.
 * 
 * We used TreeSet for both approaches to find the optimal choice for each day/job. The time complexity for 
 * both algorithm are O(n log(n)). Both approaches give the same result.
 */

public class JobSequencingAlgorithm {
	// Find the minimum total duration of doing all the job, this can reduce the search range
	private static int minTotalDuration(List<Job> jobList) {
		int max = 0;
		for (Job job : jobList)
			if (job.getDeadline() > max)
				max = job.getDeadline();
		return max > jobList.size() ? jobList.size() : max; // Assume every job can be completed within 1 day
	}
	
	// Remove null elements from the result, convert to List<>
	private static List<Job> removeNull(Job[] jobSequence) {
		List<Job> jobSequenceList = new LinkedList<>();
		for (Job job : jobSequence)
			if (job != null)
				jobSequenceList.add(job);
		
		return jobSequenceList;
	}
	
	// Worst case: O(n log(n))
	// O ( n + nlogn + n + n + nlogn + n(logn + logn) + n) = O (4n + 4nlogn)
	public static List<Job> greedyApproach1UsingTreeSet(List<Job> jobList) {
		if (jobList == null || jobList.isEmpty()) // Parameter check
			return new LinkedList<>();
		
		// 1. Sort the job list from higher profit to lower profit
		List<Job> sortedJobList = new LinkedList<>(jobList); // O(n), add all the elements to the list
		Collections.sort(sortedJobList, new ProfitComparator()); // Using merge sort, O(n log(n))
		Collections.reverse(sortedJobList); // O(n)
		
		int minTotalDuration = minTotalDuration(jobList); // O(n)
		
		// To store the result
		Job[] jobSequence = new Job[minTotalDuration]; 
		TreeSet<Integer> availableDays = new TreeSet<>(); // Self-balancing binary search tree, increase space complexity
		for (int i = 0; i < minTotalDuration; ++i)
			availableDays.add(i); // O(log(n)), adding an element to binary tree
		
		// 2. For every job, choose a day to do it
		for (Job job : sortedJobList) { 
			if (job.getProfit() <= 0 || job.getDeadline() <= 0)
				continue; // skip this job
			
			// 3. For this job, find a day that is closest to the deadline (Assume a job can be completed in 1 day)
			Integer day = availableDays.floor(job.getDeadline() - 1); // Binary search, O(log(n))
			if (day != null) {
				jobSequence[day] = job;
				availableDays.remove(day); // O(log(n)), remove an element from binary tree
			}
		}
				
		return removeNull(jobSequence);
	}
	
	// Worst case: O(n log(n)) 
	// O ( n + nlogn + n + n + n + nlogn + n) = O (5n + 2nlogn)
	public static List<Job> greedyApproach2UsingTreeSet(List<Job> jobList) {
		if (jobList == null || jobList.isEmpty()) // Parameter check
			return new LinkedList<>();
		
		// 1. Sort the job list from latest deadline to earliest deadline
		List<Job> sortedJobList = new LinkedList<>(jobList); // O(n), add all the elements to the list
		Collections.sort(sortedJobList, new DeadlineComparator()); // Using merge sort, O(n log(n))
		Collections.reverse(sortedJobList); // O(n)
		Iterator<Job> sortedJobsIterator = sortedJobList.iterator();
		
		int minTotalDuration = minTotalDuration(jobList); // O(n)

		// To store the result
		Job[] jobSequence = new Job[minTotalDuration]; 
		TreeSet<Job> remainingJobs = new TreeSet<>(new ProfitComparator()); // Jobs that can be done within the deadline but not done yet
		
		// 2. Choose a job for each day, start from the last day (last day == number of jobs - 1, assume a job can be completed in 1 day)
		Job aJob = sortedJobsIterator.next();
		for (int deadline = minTotalDuration - 1; deadline >= 0; --deadline) { 
			
			// 3. Find jobs that can be done within this deadline
			while (aJob != null) { // Total iteration of inner loop == jobList.size(), regardless the number of iteration of the outer loop
				if (aJob.getDeadline() <= deadline)
					break; // No jobs can be done within this deadline
				
				if (aJob.getProfit() <= 0) {
					aJob = sortedJobsIterator.hasNext() ? sortedJobsIterator.next() : null;
					continue; // Skip this job
				}
				remainingJobs.add(aJob); // O(log(n)), insert an element to TreeSet
				aJob = sortedJobsIterator.hasNext() ? sortedJobsIterator.next() : null; // Visit all the jobs only once, will not repeat when the new iteration start
			}
			// 4. Choose the job with the highest profit that be done within this deadline
			if (!remainingJobs.isEmpty()) 
				jobSequence[deadline] = remainingJobs.pollLast(); // O(1)
		}
		
		return removeNull(jobSequence);
	}
	
	// Worst case: O(n^2)
	public static List<Job> greedyApproach1UsingSequentialSearch(List<Job> jobList) {
		if (jobList == null || jobList.isEmpty()) // Parameter check
			return new LinkedList<>();
		
		// Sort the job list from higher profit to lower profit
		List<Job> sortedJobList = new LinkedList<>(jobList);
		Collections.sort(sortedJobList); // Using merge sort, O(n og(n))
		Collections.reverse(sortedJobList); // O(n)
		
		// To store the result
		Job[] jobSequence = new Job[jobList.size()]; 
		
		// For every job, choose a day to do it
		for (Job job : sortedJobList) { 
			if (job.getProfit() <= 0 || job.getDeadline() <= 0)
				continue; // skip this job
			
			// For this job, find a day that is closest to the deadline (Assume a job can be completed in 1 day)
			for (int day = job.getDeadline() - 1 > jobList.size() - 1 ? jobList.size() - 1 : job.getDeadline() - 1; day >= 0; --day) { 
				if (jobSequence[day] == null) { // Sequential search
					jobSequence[day] = job;
					break;
				}
			} // Sequential search
		}
		
		return removeNull(jobSequence);
	}

	// Worst case: O(n^2)
	public static List<Job> greedyApproach2UsingSequentialSearch(List<Job> jobList) {
		if (jobList == null || jobList.isEmpty()) // Parameter check
			return new LinkedList<>();
		
		// Sort the job list from higher profit to lower profit
		List<Job> sortedUnassignedJobs = new LinkedList<>(jobList);
		Collections.sort(sortedUnassignedJobs, new DeadlineComparator()); // Using merge sort, O(n log(n))
		Collections.reverse(sortedUnassignedJobs); // O(n)
		Iterator<Job> sortedUnassignedJobsIterator;
		
		// To store the result
		Job[] jobSequence = new Job[jobList.size()]; 
		
		// Choose a job for each day, start from the last day (last day == number of jobs - 1, assume a job can be completed in 1 day)
		Job aJob;
		for (int day = jobList.size() - 1; day >= 0; --day) { 
			sortedUnassignedJobsIterator = sortedUnassignedJobs.iterator(); // Start from the beginning of list after completed the previous iteration
			
			// For this day, choose the job with the highest profit from unassigned jobs
			while (sortedUnassignedJobsIterator.hasNext()) { // Sequential search, O(n)
				aJob = sortedUnassignedJobsIterator.next();
				
				if (aJob.getDeadline() > day && aJob.getProfit() > 0) { 
					jobSequence[day] = aJob;
					sortedUnassignedJobsIterator.remove();
					break;
				}
			}
		}

		return removeNull(jobSequence);
	}
}
