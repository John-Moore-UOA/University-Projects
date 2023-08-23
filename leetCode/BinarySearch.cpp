#include <iostream>
using namespace std;

class solution
{

  // finds the lowest index of the first instance of an integer.
  // returns the next largest int if no 'search' int exists, or if out of bounds, the first element on either end.
public:
  int binarySearch(int arr[], int length, int search)
  {

    int left = 0, right = length - 1;

    while (left <= right)
    {
      // looking for search
      print(arr, left, right);

      // calculate mid point
      int mid = left + (right - left) / 2;

      // look right or left
      if (search > arr[mid])
      {
        cout << "left from: " << left << " to " << mid + 1 << endl;
        left = mid + 1;
      }
      else
      {
        cout << "right from: " << right << " to " << mid - 1 << endl;
        right = mid - 1;
      }
    }

    return left;
  }

private:
  void print(int arr[], int left, int right)
  {
    cout << "left: " << left << "  right: " << right << "  mid: " << left + (right - left) / 2 << endl
         << "_______________________" << endl;
    for (int i = left; i <= right; i++)
    {
      cout << arr[i] << " ";
    }
    cout << endl
         << endl;
  }
};

int main()
{

  int arr[] = {1, 1, 2, 2, 2, 3, 5, 6, 7, 10, 40, 41, 41};

  solution sol;

  int search = 4;

  cout << "Looking for: " << search << endl;

  int index = sol.binarySearch(arr, sizeof(arr) / 4, search);

  cout << "index: " << index << endl;
}
