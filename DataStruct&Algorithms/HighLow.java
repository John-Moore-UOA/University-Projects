
// 120 ms 160 ms

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
