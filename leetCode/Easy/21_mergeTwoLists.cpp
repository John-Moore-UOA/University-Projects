#include <iostream>
#include <cstdlib>
#include <ctime>
using namespace std;

struct ListNode
{
  int val;
  ListNode *next;
  ListNode() : val(0), next(nullptr) {}
  ListNode(int x) : val(x), next(nullptr) {}
  ListNode(int x, ListNode *next) : val(x), next(next) {}
};

ListNode *generateRandomListNode(int len)
{

  ListNode head;
  ListNode *pos = &head;

  for (int i = 0; i < len; i++)
  {
    pos->next = new ListNode(rand() % 100);
    pos = pos->next;
  }

  return head.next;
}

class Solution
{
public:
  ListNode *mergeTwoLists(ListNode *list1, ListNode *list2)
  {
    ListNode head;
    ListNode *tail = &head;

    while (list1 && list2)
    {
      if (list1->val < list2->val)
      {
        tail->next = list1;
        list1 = list1->next;
      }
      else
      {
        tail->next = list2;
        list2 = list2->next;
      }
      tail = tail->next;
    }

    // remaining list fragments

    if (list1)
    {
      tail->next = list1;
    }

    if (list2)
    {
      tail->next = list2;
    }

    return head.next;
  }
};

void printList(ListNode *list)
{

  ListNode *current = list;
  while (current)
  {
    cout << current->val << " ";
    current = current->next;
  }
  cout << endl;
}

void deleteList(ListNode *list)
{
  ListNode *temp;
  while (list)
  {
    temp = list;
    list = list->next;
    delete temp;
  }
}

int main()
{
  srand(time(nullptr)); // Seed the random number generator with the current time

  ListNode *list1 = generateRandomListNode(10);
  ListNode *list2 = generateRandomListNode(12);

  printList(list1);
  printList(list2);

  Solution sol;
  ListNode *solution = sol.mergeTwoLists(list1, list2);
  printList(solution);

  cout << "success" << endl;
  return 0;
}
