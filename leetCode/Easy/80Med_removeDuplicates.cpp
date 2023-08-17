#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

vector<int> generateVector(int length)
{

  vector<int> vec;
  vec.reserve(length);

  for (int i = 0; i < length; i++)
  {
    vec.push_back(rand() % 40);
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

class Solution
{
public:
  int removeDuplicates(vector<int> &nums)
  {
    int count = 0;

    for (int i = 0; i < nums.size(); i++)
    {
      if (i < nums.size() - 2 && nums[i] == nums[i + 2])
      {
        continue;
      }
      else
      {
        nums[count] = nums[i];
        count++;
      }
    }

    return count;
  }
};

int main()
{
  // vector<int> vec = generateVector(20);
  // vector<int> vec = {1, 1, 1, 2, 2, 3};
  vector<int> vec = {0, 0, 1, 1, 1, 1, 2, 3, 3};
  sort(vec.begin(), vec.end());
  printVec(vec);

  Solution sol;
  sol.removeDuplicates(vec);

  printVec(vec);

  //
}
