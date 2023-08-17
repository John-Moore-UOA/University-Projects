#include <iostream>
#include <string>
using namespace std;

class Solution
{
public:
  int romanToInt(string s)
  {
    int result = 0;

    for (int i = 0; i < s.length(); i++)
    {
      int current = romanCharToInt(s[i]);
      int next = romanCharToInt(s[i + 1]);

      if (current < next)
      {
        result += next - current;
        i++;
      }
      else
      {
        result += current;
      }
    }

    return result;
  }

private:
  int romanCharToInt(char c)
  {
    switch (c)
    {
    case 'I':
      return 1;
    case 'V':
      return 5;
    case 'X':
      return 10;
    case 'L':
      return 50;
    case 'C':
      return 100;
    case 'D':
      return 500;
    case 'M':
      return 1000;
    default:
      return 0;
    }
  }
};

int main()
{

  Solution sol;
  cout << sol.romanToInt("III") << endl;
  cout << sol.romanToInt("MCLIII") << endl;
  cout << sol.romanToInt("MCMXCIV") << endl;

  return 0;
}