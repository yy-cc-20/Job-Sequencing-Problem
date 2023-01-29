# Job-Sequencing-Problem
Given a list of exam and assignments with deadlines and marks, arrange the sequence of doing the tasks to get the maximum marks.

#### Sample Data
![image](https://user-images.githubusercontent.com/65067887/215103203-053c831e-cff1-47cd-8021-95403677a4e5.png)

## Assumptions
1. A task can be done within one day.
2. Only able to do one task in a day
3. Every task can start at any day before its deadline.
4. The marks will be earned after the task is completed.

## Solutions
### Approach 1
For each task, choose the day which is the closest to the deadline from list of available days. <br/>

### Approach 2
For each day, choose the task with the highest marks from list of unassigned tasks.

Used binary search tree and greedy algorithm for both approaches to find the optimal choice for each day/task. 
> The time complexity for both algorithm are O(n log(n)). Both approaches give the same result.

<details>
<summary><h3>Test Case 1: Same Deadline Same Profit</h3></summary>

![image](https://user-images.githubusercontent.com/65067887/215104020-d2ba4049-7968-4c45-a58a-9ce63acb0254.png)
</details>

<details>
<summary><h3>Test Case 2: Same Deadline Different Profit</h3></summary>

![image](https://user-images.githubusercontent.com/65067887/215104074-096a2659-53f1-47d8-b4c0-fb8630e73de2.png)
</details>

<details>
<summary><h3>Test Case 3: Different Deadline Same Profit</h3></summary>

![image](https://user-images.githubusercontent.com/65067887/215104176-7ea8b9dd-e290-498d-bb82-3bb1c15b924a.png)
</details>

<details>
<summary><h3>Test Case 4: Different Deadline Different Profit</h3></summary>

![image](https://user-images.githubusercontent.com/65067887/215104239-e604de38-99c4-4661-9c47-e482b7d9dc7c.png)
</details>

<details>
<summary><h3>Test Case 5: Unable To Finish All The Tasks</h3></summary>

![image](https://user-images.githubusercontent.com/65067887/215104313-60a4462a-ad36-4238-b678-ecb865df3309.png)
</details>

<details>
<summary><h3>Test Case 6: Nagtive Values</h3></summary>

![image](https://user-images.githubusercontent.com/65067887/215104379-34a8ef41-0a16-4030-9083-cec20fb48f48.png)
</details>

##### Please ⭐️ this repository if this project helped you!
