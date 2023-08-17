#include <iostream>
#include <vector>

using namespace std;

// 1 2 3 4 5 4 2 1
// 1 2 3 4 5 4 2 1
// 1 2 3 4 5 4 2 1
// 1 2 3 4 5 4 2 1
// 1 2 3 4 5 4 2 1
// shifting: 1
// 1 2 3 5 5 4 2 1
// 1 2 3 5 4 4 2 1
// shifting: 2
// remove: 2
// remove: 1
// 1 2 3 5 4 1 2 1

// expect
/*

shifting: 1
1 2 3 5 5 4 2 1 : 5
want to swap 5 with 4, but its a 4 so
-> count++;
-> index 4+count = 4+2 => 2
swap 5 with 2
1 2 3 5 2 4 2 1 : 4
looking at a 4, but it only matters when I look ahead index + count,
which is now
-> 5+2 => 1
1 2 3 5 2 1 2 1 : end
-> 6+2 = 7 => 9-count = 9-2 = 7 => end

remove 2
remove 1

*/

vector<int> generateVector(int length)
{

  vector<int> vec;
  vec.reserve(length);

  for (int i = 0; i < length; i++)
  {
    vec.push_back(rand() % 10);
  }

  return vec;
}

void printVec(vector<int> &nums)
{

  for (int i = 0; i < nums.size(); i++)
  {
    cout << "." << nums[i] << " ";
  }
  cout << endl;
}

// class Solution
// {
// public:
//   int removeElement(vector<int> &nums, int val)
//   {
//     int count = 0; // number of elements == val that are removed

//     int i = 0;
//     while (i + count < nums.size())
//     {

//       while (nums[i + count] == val)
//       {
//         count++;
//         if (i + count >= nums.size())
//           break;
//       }

//       nums[i] = nums[i + count];

//       i++;
//     }

//     nums.erase(nums.begin() + i, nums.begin() + i + count);

//     return nums.size();
//   }
// };

class Solution
{

public:
  int removeElement(vector<int> &nums, int val)
  {

    int index = 0;

    for (int i = 0; i < nums.size(); i++)
    {

      if (nums[i] != val)
      {

        nums[index] = nums[i];

        index++;
      }
    }

    return index;
  }
};

int main()
{

  // vector<int> vec1 = generateVector(10);
  // vector<int> vec1 = {4, 4, 1, 2, 3, 4, 5, 4};
  vector<int> vec1 = {1, 2, 3, 4, 4, 1, 2};
  printVec(vec1);
  cout << endl;

  Solution sol;
  sol.removeElement(vec1, 4);

  printVec(vec1);
}
