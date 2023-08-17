#include <iostream>
#include <vector>
using namespace std;

vector<int> generateVector(int length)
{

  vector<int> vec;
  vec.reserve(length);

  for (int i = 0; i < length; i++)
  {
    vec.push_back(rand() % 100);
  }

  return vec;
}

void printVec(vector<int> &nums)
{

  for (int i = 0; i < nums.size(); i++)
  {
    cout << "" << nums[i] << " ";
  }
  cout << endl;
}

void insersionSortVec(vector<int> &nums)
{

  for (int i = 0; i < nums.size(); i++)
  {
    int min = nums[i];
    int index = i;

    for (int j = i; j < nums.size(); j++)
    {
      if (nums[j] < min)
      {
        min = nums[j];
        index = j;
      }
    }

    // swap elements
    int temp = nums[i];
    nums[i] = min;
    nums[index] = temp;
  }
}

class Solution
{
public:
  void merge(vector<int> &nums1, int m, vector<int> &nums2, int n)
  {
    int i = m - 1;
    int j = n - 1;
    int k = m + n - 1;

    while (j >= 0)
    {
      if (i >= 0 && nums1[i] > nums2[j])
      {
        nums1[k--] = nums1[i--];
      }
      else
      {
        nums1[k--] = nums2[j--];
      }
    }
  }
};

int main()
{
  vector<int> vec1 = generateVector(8);
  vector<int> vec2 = generateVector(4);
  insersionSortVec(vec1);
  insersionSortVec(vec2);

  printVec(vec1);
  printVec(vec2);

  cout << "merge" << endl;

  Solution sol;
  sol.merge(vec1, vec1.size(), vec2, vec2.size());

  cout << "result" << endl;
  printVec(vec1);
  //
}
