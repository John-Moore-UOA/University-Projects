// 14. Longest Common Prefix

#include <iostream>
#include <string>
#include <vector>
using namespace std;

class Solution
{
public:
  string longestCommonPrefix(vector<string> &strs)
  {
    // loop through list of strings
    // check first letter of each, then move one further

    int depth = 0;
    char currentChar = strs[0][0];
    while (true)
    {

      for (int i = 0; i < strs.size(); i++)
      {

        if (currentChar != strs[i][depth] || strs[i][depth] == '\0')
        {
          // return up to depth chars as string
          return strs[0].substr(0, depth);
        }

        currentChar = strs[i][depth];
      }
      depth++;
      currentChar = strs[0][depth];

      // check next character
    }
  }
};

int main()
{

  Solution sol;

  vector<string> vec1 = {"dog", "racecar", "car"};
  vector<string> vec2 = {"flower", "flow", "flight"};
  vector<string> vec3 = {"ab", "a"};

  cout << sol.longestCommonPrefix(vec1) << endl;
  cout << sol.longestCommonPrefix(vec2) << endl;
  cout << sol.longestCommonPrefix(vec3) << endl;
}