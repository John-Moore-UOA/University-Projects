// 20. Valid Parentheses

/*
Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

An input string is valid if:

    Open brackets must be closed by the same type of brackets.
    Open brackets must be closed in the correct order.
    Every close bracket has a corresponding open bracket of the same type.
*/
#include <iostream>
#include <string>
#include <stack>

using namespace std;

class Solution
{
public:
  bool isValid(string s)
  {
    // stack

    // add opening brakcets to stack
    // closing brackets cancel opening stacks
    // if empty return true else

    if (s.size() % 2 == 1)
    {
      return false;
    }

    stack<char> stack;

    int i = 0;
    while (s[i] != '\0')
    {
      if (s[i] == '(' | s[i] == '[' | s[i] == '{')
      {
        stack.push(s[i]);
      }
      else if (stack.size() > 0)
      {
        if (stack.top() == recip(s[i]))
        {
          stack.pop();
        }
        else
        {
          stack.push(s[i]);
        }
      }
      else
      {
        return false;
      }

      i++;
    }

    return stack.size() == 0;
  }

private:
  char
  recip(char bracket)
  {
    switch (bracket)
    {
    case ')':
      return '(';
      break;
    case ']':
      return '[';
      break;
    case '}':
      return '{';
      break;

    default:
      return 'c';
      break;
    }
  }
};

int main()
{

  Solution sol;
  // cout << sol.isValid("()") << endl;
  // cout << sol.isValid("()[]{}") << endl;
  // cout << sol.isValid("()[{}") << endl;
  // cout << sol.isValid("{[]}") << endl;
  cout << sol.isValid("([}}])") << endl;
}
