//Question. Given a sorted array of distinct integers A and an integer k, what is the largest value d for which
//there exist k elements x1 < x2 < . . . < xk in A, such that xi − xj ≥ d for every 1 ≤ j < i ≤ k?
//Input. The first line contains an integer t, which denotes the number of test cases. Each test case is described
//as follows: The first line of the test case contains two integers, n and k; the second line contains n integers
//which denote the array A.
//Output. The output consists of t lines, with the i-th line consisting of a single number denoting the value d
//corresponding to the i-th test case.
//Example.
//Input:
//4
//5 2
//1 2 3 4 5
//5 3
//1 2 4 9 10
//8 4
//1 10 20 30 70 110 1000 1001
//10 5
//1 2 3 4 5 6 7 8 9 10
//Output:
//4
//3
//40
//2
//Explanation. In the first test case we can take 1 and 5. In the second test case, one possible solution is to
//take 1, 4, 10. In the third 1, 70, 110, 1001. Finally, in the fourth test case we take 1, 3, 5, 7, 9.


#include <iostream>

bool isPossible(int nums[], int d, int k, int length)
{
  int previous = nums[0];
  int count = 0;

  for (int i = 0; i < length; i++)
  {
    if (nums[i] - previous >= d)
    {
      // count increases, because we found a connection
      // need to find at least k many
      count++;
      previous = nums[i];
      if (count == k - 1)
      {
        // found k-1 many pairs (0 based indexing)
        return true;
      }
    }
  }

  return false; // did not find enough / any pairs up to k
}

// get the difference between the max and min values in the array
int getMaxMinDifference(int nums[], int length)
{
  int max = -1;
  int min = 10000001;
  for (int i = 0; i < length; i++)
  {
    if (nums[i] > max)
    {
      max = nums[i];
    }

    if (nums[i] < min)
    {
      min = nums[i];
    }
  }
  return max - min;
}

// @param nums: array of integers
// @param length: length of array
// @param k: k value
// @return: greatest d value
int calculateMaxDistance(int nums[], int length, int k)
{
  int maxD;
  int low = 0;
  int high = getMaxMinDifference(nums, length); // max - min: nums[length] - nums[0] ???

  while (low <= high)
  {
    // binary search
    int mid = low + (high - low) / 2; // should this be int mid = low + (high - low) / 2; ?

    if (isPossible(nums, mid, k, length))
    {
      maxD = mid;
      low = mid + 1;
    }
    else
    {
      high = mid - 1;
    }
  }

  return maxD;
}

// example
// 5 2
// 1 2 3 4 5
// should output max d value
// d values include
// sub arrays or size 2
// 1,2 2,3 3,4 4,5, 1,3 2,4 3,5 1,4 2,5 1,5 etc
// d value is for this case basically max-min = 5-1 = 4, giving the greatest d value
int main()
{

  std::ios_base::sync_with_stdio(false);
  std::cin.tie(NULL);
  std::cout.tie(NULL);
  // input number of tests
  // input arrayLength
  // input k value
  // input array
  // output max distance calculation

  int numTests;
  std::cin >> numTests;

  for (int round = 0; round < numTests; round++)
  {

    int arrayLength;
    std::cin >> arrayLength;

    int k;
    std::cin >> k;
    int nums[arrayLength];

    for (int i = 0; i < arrayLength; i++)
    {
      // input array
      std::cin >> nums[i];
    }

    // solve
    std::cout << calculateMaxDistance(nums, arrayLength, k) << std::endl;
  }
}
