// 120 ms 160 ms

//Question. Given a sorted array of integers A and a pair of integers (low, high), how many elements x in A
//satisfy low ≤ x ≤ high?
//Input. The first line contains an integer n which denotes the length of the array A. The next line contains n
//integers a0, . . . , an−1, separated by a single space. The third line contains an integer m. Finally, each of the
//following m lines contains two integers, lowi and highi.
//Output. The output consists of m lines, with the i-th line consisting of a single number denoting the number
//of elements x in A such that lowi ≤ x ≤ highi.
//2
//Example.
//Input:
//10
//1 5 5 5 6 10 25 25 101 101
//5
//1 5
//0 7
//25 101
//8 12
//200 500
//Output:
//4
//5
//4
//1
//0

#include <iostream>

using namespace std;

int binarySearchLow(int nums[], int low, int high, int length)
{
  int left = 0;
  int right = length - 1;

  while (left <= right)
  {
    int mid = left + (int)((right - left) / 2); // does this need flooring?

    if (nums[mid] >= low)
    {
      right = mid - 1;
    }
    else
    {
      left = mid + 1;
    }
  }

  return left;
}

int binarySearchHigh(int nums[], int low, int high, int length)
{
  int left = 0;
  int right = length - 1;

  int iterations = 0;
  while (right > left)
  {

    int mid = left + (right - left) / 2 + 1; // ceiling

    if (nums[mid] <= high)
    {
      left = mid;
    }
    else
    {
      right = mid - 1;
    }
  }

  return right;
}

int binarySearch(int nums[], int low, int high, int length)
{

  int left = binarySearchLow(nums, low, high, length);
  int right = binarySearchHigh(nums, low, high, length);

  // cout << left << " : " << right << " => " << right - left + 1 << endl;
  return right - left + 1;
}

int main()
{
  std::ios_base::sync_with_stdio(false);
  std::cin.tie(NULL);
  std::cout.tie(NULL);

  int length;
  cin >> length;

  int nums[length];
  for (int i = 0; i < length; i++)
  {
    cin >> nums[i];
  }

  int testLength;
  cin >> testLength;

  int low;
  int high;

  for (int i = 0; i < testLength; i++)
  {
    cin >> low >> high;

    cout << binarySearch(nums, low, high, length) << endl;
  }
}
