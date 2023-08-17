#include <vector>
#include <iostream>
using namespace std;

vector<int> generateVector(int length)
{

  vector<int> vec;
  vec.reserve(length);

  for (int i = 0; i < length; i++)
  {
    vec.push_back(rand() % 17);
  }

  return vec;
}

void printVec(vector<int> &nums)
{

  for (int i = 0; i < nums.size(); i++)
  {
    cout << nums[i] << " ";
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
  int removeDuplicates(vector<int> &nums)
  {

    // loop through array
    // copy first instance to new vector
    // nuke old vector
    // copy vector

    vector<int> tempVec;
    int prev;

    int count = 0;

    for (int i = 0; i < nums.size(); i++)
    {
      if (nums[i] != prev)
      {
        tempVec.push_back(nums[i]);
        prev = nums[i];
        count++;
      }
    }

    nums.clear();
    nums.insert(nums.end(), tempVec.begin(), tempVec.end());

    return count;
  }
};

int main()
{

  vector<int> vec1 = generateVector(16);
  printVec(vec1);
  insersionSortVec(vec1);

  Solution sol;
  sol.removeDuplicates(vec1);

  printVec(vec1);
}